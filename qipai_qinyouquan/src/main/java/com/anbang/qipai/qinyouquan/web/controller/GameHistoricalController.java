package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.c.service.PlayBackCodeCmdService;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.AllianceDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.PlayBackDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.PlayBackDboService;
import com.anbang.qipai.qinyouquan.plan.bean.GameDataReport;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameServer;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.bean.game.NoServerAvailableForGameException;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;

import com.anbang.qipai.qinyouquan.util.TimeUtil;
import com.anbang.qipai.qinyouquan.web.vo.CommonVO;
import com.anbang.qipai.qinyouquan.web.vo.JuResultVO;
import com.anbang.qipai.qinyouquan.plan.service.*;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class GameHistoricalController {
    @Autowired
    private GameHistoricalJuResultService gameHistoricalJuResultService;
    @Autowired
    private MemberAuthService memberAuthService;
    @Autowired
    private LianmengService lianmengService;
    @Autowired
    private GameReportService gameReportService;

    @Autowired
    private PlayService playService;

    @Autowired
    private PlayBackCodeCmdService playBackCodeCmdService;

    @Autowired
    private PlayBackDboService playBackDboService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @RequestMapping("/ju_info")
    public CommonVO ju_info(String gameId) {
        CommonVO vo = new CommonVO();
        JuResultVO juResultVO = new JuResultVO();
        GameHistoricalJuResult juResult = gameHistoricalJuResultService.getJuResultByGameId(gameId);
        juResultVO.setJuResult(juResult);
        if (juResult != null) {
            AllianceDbo alliance = lianmengService.findAllianceDboById(juResult.getLianmengId());
            juResultVO.setAlliance(alliance);
            juResultVO.setJuResult(juResult);
            vo.setSuccess(true);
            vo.setData(juResultVO);
            return vo;
        }
        vo.setMsg("qurey error");
        vo.setSuccess(false);
        return vo;
    }

    /**
     * 验证身份
     */
    @RequestMapping("/verify")
    public CommonVO verify(String token, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("not join");
            return vo;
        }
        Map data = new HashMap<>();
        data.put("identity", memberLianmengDbo.getIdentity());
        vo.setData(data);
        return vo;
    }

    /**
     * 查询系统时间
     *
     * @param token 令牌
     */
    @RequestMapping(value = "/query_sys_time")
    public CommonVO querySystemTime(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        long currentTime = System.currentTimeMillis();
        Map data = new HashMap();
        data.put("currentTime", currentTime);
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }

    /**
     * 我的战绩
     */
    @RequestMapping("/myresult")
    public CommonVO queryResultByMemberId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, @RequestParam(required = false) Game game, String lianmengId,
                                          String token, long currentTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        long startTime = TimeUtil.getDayStartTime(currentTime);
        int dayingjiaCount = (int) gameHistoricalJuResultService.countDayingjiaByMemberIdAndTime(memberId, lianmengId, startTime, currentTime);
        int power = gameHistoricalJuResultService.countPower(game, lianmengId, memberId, startTime, currentTime);
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengId(page, size, game, lianmengId, memberId, startTime, currentTime);
        Map data = new HashMap();
        data.put("dayingjiaCount", dayingjiaCount);
        data.put("power", power);
        data.put("juCount", listPage.getTotalItemsCount());
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 战绩详情
     */
    @RequestMapping("/detail")
    public CommonVO queryResultDetail(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String memberId, long dayTime) {
        CommonVO vo = new CommonVO();
        long startTime = TimeUtil.getDayStartTime(dayTime);
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengId(page, size, null, lianmengId, memberId, startTime, dayTime);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 战绩查询
     */
    @RequestMapping("/result_query")
    public CommonVO queryResult(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String memberId, long dayTime) {
        CommonVO vo = new CommonVO();
        long startTime = TimeUtil.getDayStartTime(dayTime);
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengId(page, size, null, lianmengId, memberId, startTime, dayTime);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }


    /**
     * 大联盟战绩-战绩详情
     */
    @RequestMapping("/detail_dalianmeng")
    public CommonVO detail_dalianmeng(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String memberId, long dayTime) {
        CommonVO vo = new CommonVO();
        long startTime = TimeUtil.getDayStartTime(dayTime);
        long endTime = TimeUtil.getDayEndTime(dayTime);
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengId(page, size, null, lianmengId, memberId, startTime, endTime);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 房间管理-查看
     */
    @RequestMapping(value = "/queryroomdetail")
    public CommonVO queryRoomDetail(String gameId) {
        CommonVO vo = new CommonVO();
        Map map = gameHistoricalJuResultService.queryRoomDetail(gameId);
        vo.setSuccess(true);
        vo.setData(map);
        return vo;
    }

    @RequestMapping(value = "/playback_self")
    public CommonVO playback(Game game, String gameId, int panNo) {
        CommonVO vo = new CommonVO();
        GameServer gameServer;
        try {
            gameServer = playService.getRandomGameServer(game);
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        GameTable table = playService.findTableByGameAndServerGameGameId(game, gameId);
        Map data = new HashMap();
        data.put("httpUrl", gameServer.getHttpUrl());
        data.put("gameId", gameId);
        data.put("panNo", panNo);
        data.put("roomNo", table.getNo());
        data.put("game", table.getGame());
        vo.setSuccess(true);
        vo.setMsg("playback");
        vo.setData(data);
        return vo;
    }

    /**
     * 生成回放码
     */
    @RequestMapping(value = "/shareplayback")
    public CommonVO sharePlayback(Game game, String gameId, int panNo) {
        CommonVO vo = new CommonVO();
        PlayBackDbo playBackDbo = playBackDboService.findByGameAndGameIdAndPanNo(game, gameId, panNo);
        if (playBackDbo != null) {
            Map data = new HashMap();
            data.put("code", playBackDbo.getId());
            vo.setSuccess(true);
            vo.setMsg("playbackcode");
            vo.setData(data);
            return vo;
        }
        Integer code = playBackCodeCmdService.getPlayBackCode();
        int size = code.toString().length();
        String newCode = "";
        int i = 6 - size;
        while (i > 0) {
            newCode += "0";
            i--;
        }
        newCode += code.toString();
        PlayBackDbo dbo = new PlayBackDbo();
        dbo.setId(newCode);
        dbo.setGame(game);
        dbo.setGameId(gameId);
        dbo.setPanNo(panNo);
        playBackDboService.save(dbo);
        Map data = new HashMap();
        data.put("code", newCode);
        vo.setSuccess(true);
        vo.setMsg("playbackcode");
        vo.setData(data);
        return vo;
    }

    @RequestMapping(value = "/playback_code")
    public CommonVO playbackCode(String token, String code) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        PlayBackDbo dbo = playBackDboService.findById(code);
        if (dbo == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid code");
            return vo;
        }
        GameServer gameServer;
        try {
            gameServer = playService.getRandomGameServer(dbo.getGame());
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        GameTable table = playService.findTableByGameAndServerGameGameId(dbo.getGame(), dbo.getGameId());
        Map data = new HashMap();
        data.put("httpUrl", gameServer.getHttpUrl());
        data.put("gameId", dbo.getGameId());
        data.put("panNo", dbo.getPanNo());
        data.put("roomNo", table.getNo());
        data.put("game", table.getGame());
        Map map = gameHistoricalJuResultService.queryRoomDetail(dbo.getGameId());
        data.put("roomDetail",map);
        vo.setSuccess(true);
        vo.setMsg("playback");
        vo.setData(data);
        return vo;
    }

    /**
     * 联盟后台-联盟数据日报
     */
    @RequestMapping("/gamereport")
    public CommonVO gameReport(@RequestParam(required = true) Long startTime, @RequestParam(required = true) Long endTime, @RequestParam(required = true) Game game) {
        CommonVO vo = new CommonVO();
        List<GameDataReport> reportList = gameReportService.findGameReportByTimeAndGame(startTime, endTime, game);
        vo.setSuccess(true);
        vo.setMsg("gameDataList");
        vo.setData(reportList);
        return vo;
    }

    /**
     * 每日游戏数据生成
     */
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点
    @RequestMapping(value = "/creategamedatareport")
    private void createGameDataReport() {
        Game[] games = Game.values();
        long oneDay = 3600000 * 24;
        // 当日凌晨2点
        long endTime = System.currentTimeMillis();
        // 昨日凌晨2点
        long startTime = endTime - oneDay;
        for (Game game : games) {
            int currentMember = 0;// 进入游戏的当日会员人数
            int gameNum = gameHistoricalJuResultService.countGameNumByGameAndTime(game, startTime, endTime);// 游戏总局数
            int loginMember = 0;// 独立玩家
            GameDataReport report = new GameDataReport(game, endTime, currentMember, gameNum, loginMember);
            gameReportService.addGameReport(report);
        }
    }

    @RequestMapping("/resultStatSistics")
    public CommonVO resultStatSistics(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String token, long startTime, long endTime,
                                      @RequestParam(required = false) boolean queryXiaji, @RequestParam(required = false) String searchMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("member not found");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (searchMemberId != null) {
            MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (handleMemberLianmengDbo == null ) {
                vo.setSuccess(false);
                vo.setMsg("searchMember not found");
                return vo;
            }
            if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                    vo.setMsg("member can not search handleMember");
                    vo.setSuccess(false);
                    return vo;
                }
            }
        }
        Map data = new HashMap();
        ListPage listPage = memberDayResultDataService.findByLianmengIdAndSuperiorId(page, size, memberLianmengDbo1.getMemberId(), memberLianmengDbo1.getLianmengId(), startTime, endTime, queryXiaji, searchMemberId);
        data.put("listPage", listPage);
        vo.setSuccess(true);
        vo.setMsg("resultStatSistics");
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/memberDayJuResultFinish")
    public CommonVO memberDayJuResultFinish(String lianmengId, String token, String handleMemberId, long startTime, long endTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }

        if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
            vo.setMsg("member can not finish handleMember");
            vo.setSuccess(false);
            return vo;
        }
        memberDayResultDataService.updateFinishCount(handleMemberId, lianmengId);
        gameHistoricalJuResultService.memberJuResultFinish(handleMemberId, lianmengId, startTime, endTime);
        return vo;
    }


    /**
     * 下级未完成战绩查询
     */
    @RequestMapping("/queryXiajiJuresult")
    public CommonVO queryXiajiJuresult(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String token, long startTime,
                                       long endTime, @RequestParam(required = false) String searchMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not found");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (searchMemberId != null) {
            MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (handleMemberLianmengDbo == null ) {
                vo.setSuccess(false);
                vo.setMsg("searchMember not found");
                return vo;
            }
            if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setMsg("member can not search handleMember");
                vo.setSuccess(false);
                return vo;
            }
        }
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengIdAndSuperiorId(page, size, lianmengId,
                memberLianmengDbo1.getMemberId(), startTime, endTime, false, searchMemberId);
        Map data = new HashMap();
        data.put("juCount", listPage.getTotalItemsCount());
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }


    /**
     * 下级已完成战绩查询
     */
    @RequestMapping("/queryXiajiJuresultFinish")
    public CommonVO queryXiajiJuresultFinish(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String token, long startTime,
                                             long endTime, @RequestParam(required = false) String searchMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (searchMemberId != null) {
            MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
                vo.setSuccess(false);
                vo.setMsg("ban");
                return vo;
            }
            if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setMsg("member can not search handleMember");
                vo.setSuccess(false);
                return vo;
            }
        }
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengIdAndSuperiorId(page, size, lianmengId, memberLianmengDbo1.getMemberId(), startTime, endTime, true, searchMemberId);
        Map data = new HashMap();
        data.put("juCount", listPage.getTotalItemsCount());
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/memberJuResultFinish")
    public CommonVO memberJuResultFinish(String lianmengId, String token, String gameId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        GameHistoricalJuResult gameHistoricalResultById = gameHistoricalJuResultService.getJuResultByGameId(gameId);
        String handleMemberId = gameHistoricalResultById.getDayingjiaId();
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
            vo.setMsg("member can not give to handleMember");
            vo.setSuccess(false);
            return vo;
        }
        gameHistoricalJuResultService.juResultFinish(gameId, handleMemberId, lianmengId);
        return vo;
    }

    /**
     * 圈子战绩查询
     */
    @RequestMapping("/queryQuanziJuresult")
    public CommonVO queryQuanziJuresult(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String token, long startTime,
                                        long endTime, @RequestParam(required = false) String searchMemberId, String superiorMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            superiorMemberId = memberLianmengDbo1.getMemberId();
        }
        ListPage listPage = gameHistoricalJuResultService.findGameHistoricalResultByLianmengIdAndSuperiorIdAndMemberId(
                page, size, lianmengId, searchMemberId, superiorMemberId, startTime, endTime);
        Map data = new HashMap();
        data.put("juCount", listPage.getTotalItemsCount());
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

}
