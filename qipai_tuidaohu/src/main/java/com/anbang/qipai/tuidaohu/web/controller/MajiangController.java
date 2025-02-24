package com.anbang.qipai.tuidaohu.web.controller;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.Automatic;
import com.anbang.qipai.tuidaohu.cqrs.c.domain.MajiangActionResult;
import com.anbang.qipai.tuidaohu.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.tuidaohu.cqrs.c.domain.ReadyToNextPanResult;
import com.anbang.qipai.tuidaohu.cqrs.c.service.GameCmdService;
import com.anbang.qipai.tuidaohu.cqrs.c.service.MajiangPlayCmdService;
import com.anbang.qipai.tuidaohu.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.tuidaohu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.tuidaohu.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.tuidaohu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.tuidaohu.cqrs.q.dbo.TuiDaoHuPanPlayerResultDbo;
import com.anbang.qipai.tuidaohu.cqrs.q.service.MajiangGameQueryService;
import com.anbang.qipai.tuidaohu.cqrs.q.service.MajiangPlayQueryService;
import com.anbang.qipai.tuidaohu.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.tuidaohu.msg.msjobj.MajiangHistoricalPanResult;
import com.anbang.qipai.tuidaohu.msg.service.MemberGoldsMsgService;
import com.anbang.qipai.tuidaohu.msg.service.TuiDaoHuGameMsgService;
import com.anbang.qipai.tuidaohu.msg.service.TuiDaoHuResultMsgService;
import com.anbang.qipai.tuidaohu.plan.bean.MemberGoldBalance;
import com.anbang.qipai.tuidaohu.plan.bean.PlayerInfo;
import com.anbang.qipai.tuidaohu.plan.service.MemberGoldBalanceService;
import com.anbang.qipai.tuidaohu.plan.service.PlayerInfoService;
import com.anbang.qipai.tuidaohu.remote.service.QipaiDalianmengRemoteService;
import com.anbang.qipai.tuidaohu.remote.vo.CommonRemoteVO;
import com.anbang.qipai.tuidaohu.utils.SpringUtil;
import com.anbang.qipai.tuidaohu.web.vo.CommonVO;
import com.anbang.qipai.tuidaohu.web.vo.JuResultVO;
import com.anbang.qipai.tuidaohu.web.vo.PanActionFrameVO;
import com.anbang.qipai.tuidaohu.web.vo.PanResultVO;
import com.anbang.qipai.tuidaohu.websocket.GamePlayWsNotifier;
import com.anbang.qipai.tuidaohu.websocket.QueryScope;
import com.anbang.qipai.tuidaohu.websocket.WatchQueryScope;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.google.gson.Gson;
import org.eclipse.jetty.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mj")
public class MajiangController {

    @Autowired
    private MajiangPlayCmdService majiangPlayCmdService;

    @Autowired
    private MajiangPlayQueryService majiangPlayQueryService;

    @Autowired
    private MajiangGameQueryService majiangGameQueryService;

    @Autowired
    private PlayerAuthService playerAuthService;

    @Autowired
    private PlayerInfoService playerInfoService;

    @Autowired
    private MemberGoldBalanceService memberGoldBalanceService;

    @Autowired
    private MemberGoldsMsgService memberGoldsMsgService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private TuiDaoHuResultMsgService tuidaohuResultMsgService;

    @Autowired
    private TuiDaoHuGameMsgService gameMsgService;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private QipaiDalianmengRemoteService qipaiDalianmengRemoteService;

    @Autowired
    private HttpClient httpClient;

    private final Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);

    /**
     * 当前盘我应该看到的所有信息 行牌的通知
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/pan_action_frame_for_me")
    @ResponseBody
    public CommonVO panactionframeforme(String token, String gameId) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        PanActionFrame panActionFrame;
        try {
            panActionFrame = majiangPlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId, playerId);
        } catch (Exception e) {
            e.printStackTrace();
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        data.put("panActionFrame", new PanActionFrameVO(panActionFrame));
        return vo;
    }

    /**
     * @param gameId
     * @param panNo  0代表不知道盘号，那么就取最新的一盘
     * @return
     */
    @RequestMapping(value = "/pan_result")
    @ResponseBody
    public CommonVO panresult(String gameId, int panNo) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
        if (panNo == 0) {
            panNo = majiangGameDbo.getPanNo();
        }
        PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, panNo);
        data.put("panResult", new PanResultVO(panResultDbo, majiangGameDbo));
        return vo;
    }

    @RequestMapping(value = "/ju_result")
    @ResponseBody
    public CommonVO juresult(String gameId) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
        JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
        if (juResultDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("not find juresult");
            return vo;
        }
        data.put("juResult", new JuResultVO(juResultDbo, majiangGameDbo));
        return vo;
    }

    @RequestMapping(value = "/xipai")
    @ResponseBody
    public CommonVO xipai(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        List<String> queryScopes = new ArrayList<>();
        data.put("queryScopes", queryScopes);
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        PlayerInfo playerInfo = playerInfoService.findPlayerInfoById(playerId);
        if (!playerInfo.isVip()) {
            MemberGoldBalance account = memberGoldBalanceService.findByMemberId(playerId);
            if (account.getBalanceAfter() < 20) {
                vo.setSuccess(false);
                vo.setMsg("InsufficientBalanceException");
                return vo;
            }
            memberGoldsMsgService.withdraw(playerId, 20, "xipai");
        }
        MajiangGameValueObject majiangGameValueObject = null;
        try {
            majiangGameValueObject = majiangPlayCmdService.xipai(playerId);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        majiangPlayQueryService.xipai(majiangGameValueObject);
        queryScopes.add(QueryScope.gameInfo.name());
        // 通知其他人
        for (String otherPlayerId : majiangGameValueObject.allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(majiangGameValueObject.getState(), majiangGameValueObject.findPlayerState(otherPlayerId)));
            }
        }
        return vo;
    }

    /**
     * 麻将行牌 出牌
     *
     * @param token    令牌
     * @param id       玩家ID
     * @param actionNo 动作编号
     * @param gameId   游戏ID
     * @return
     */
    @RequestMapping(value = "/action")
    @ResponseBody
    public CommonVO action(String token, int id, int actionNo, String gameId) {
        long startTime = System.currentTimeMillis();
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        List<String> queryScopes = new ArrayList<>();
        data.put("queryScopes", queryScopes);
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            long endTime = System.currentTimeMillis();
            logger.info("action," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "id:" + id + "," + "actionNo:" + actionNo + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }
        MajiangActionResult majiangActionResult;
        String endFlag = "query";
        try {
            majiangActionResult = majiangPlayCmdService.action(playerId, id, actionNo, System.currentTimeMillis());
        } catch (Exception e) {
            data.put("actionNo", majiangPlayQueryService.findCurrentPanLastestActionNo(gameId));
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            long endTime = System.currentTimeMillis();
            logger.info("action," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "id:" + id + "," + "actionNo:" + actionNo + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }
        majiangPlayQueryService.action(majiangActionResult);

        automatic.removeTuoguanPlayerIdSet(playerId);//玩家执行动作后就将玩家在托管列表中移除

        if (majiangActionResult.getPanResult() == null) { //盘没结束
            queryScopes.add(QueryScope.panForMe.name());
        } else { //盘结束了
            gameId = majiangActionResult.getMajiangGame().getId();
            MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
            if (majiangActionResult.getJuResult() != null) { //局也结束了
                JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
                MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                tuidaohuResultMsgService.recordJuResult(juResult);
                gameMsgService.gameFinished(gameId);
                queryScopes.add(QueryScope.juResult.name());
                endFlag = WatchQueryScope.watchEnd.name();
            } else {
                int tuoguanCount = 0;
                Map<String, String> playerIdGameIdMap = gameCmdService.playLeaveGameHosting(playerId, null, false);
                if (playerIdGameIdMap != null) {
                    Set<String> playerIds = playerIdGameIdMap.keySet();//托管玩家集合
                    tuoguanCount = playerIds.size();
                }
                if (majiangGameDbo.getOptionalPlay().isTuoguanjiesan() && tuoguanCount != 0) {
                    try {
                        PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, majiangActionResult.getPanResult().getPan().getNo());
                        MajiangHistoricalPanResult panResult = new MajiangHistoricalPanResult(panResultDbo, majiangGameDbo);
                        tuidaohuResultMsgService.recordPanResult(panResult);
                        gameMsgService.panFinished(majiangActionResult.getMajiangGame(), majiangActionResult.getPanActionFrame().getPanAfterAction());
                        MajiangGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                        majiangGameQueryService.finishGameImmediately(gameValueObject, panResultDbo);
                        JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
                        MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                        tuidaohuResultMsgService.recordJuResult(juResult);
                        gameMsgService.gameFinished(gameId);
                        queryScopes.add(QueryScope.juResult.name());
                        for (String otherPlayerId : gameValueObject.allPlayerIds()) {
                            if (!otherPlayerId.equals(playerId)) {
                                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(gameValueObject.getState(), gameValueObject.findPlayerState(otherPlayerId)));
                            }
                        }
                        data.put("queryScopes", queryScopes);
                        vo.setData(data);
                        return vo;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        vo.setSuccess(false);
                        vo.setMsg(throwable.getClass().getName());
                        return vo;
                    }
                } else {
                    queryScopes.add(QueryScope.gameInfo.name());
                    queryScopes.add(QueryScope.panResult.name());
                    endFlag = WatchQueryScope.panResult.name();
                }
            }
            PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, majiangActionResult.getPanResult().getPan().getNo());
            MajiangHistoricalPanResult panResult = new MajiangHistoricalPanResult(panResultDbo, majiangGameDbo);
            tuidaohuResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(majiangActionResult.getMajiangGame(), majiangActionResult.getPanActionFrame().getPanAfterAction());
        }
        // 通知其他人
        for (String otherPlayerId : majiangActionResult.getMajiangGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(majiangActionResult.getMajiangGame().getState(), majiangActionResult.getMajiangGame().findPlayerState(otherPlayerId)));
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("action," + "startTime:" + startTime + "," + "gameId:" + majiangActionResult.getMajiangGame().getId() + "," + "playerId:" + playerId + "," + "id:" + id + "," + "actionNo:" + actionNo + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
        hintWatcher(majiangActionResult.getMajiangGame().getId(), endFlag);
        return vo;
    }

    @RequestMapping(value = "/ready_to_next_pan")
    @ResponseBody
    public CommonVO readytonextpan(String token, @RequestParam(required = false) String gameId) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }

        Map<String, String> playerIdGameIdMap = gameCmdService.playLeaveGameHosting(playerId, null, false);
        Set<String> playerIds = null;
        if (playerIdGameIdMap != null) {
            playerIds = playerIdGameIdMap.keySet();//托管玩家集合
        }
        if (gameId != null) {
            MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
            String lianmengId = majiangGameDbo.getLianmengId();
            if (lianmengId != null) {
                PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, majiangGameDbo.getPanNo());
                for (TuiDaoHuPanPlayerResultDbo tuiDaoHuPanPlayerResultDbo : panResultDbo.getPlayerResultList()) {
                    CommonRemoteVO resVo = qipaiDalianmengRemoteService.nowPowerForRemote(tuiDaoHuPanPlayerResultDbo.getPlayerId(), lianmengId);
                    if (resVo.isSuccess()) {
                        Map powerdata = (Map) resVo.getData();
                        double powerbalance = (Double) powerdata.get("powerbalance");
                        if (tuiDaoHuPanPlayerResultDbo.getPlayerResult().getTotalScore() + powerbalance <= majiangGameDbo.getPowerLimit()) {
                            try {
                                MajiangGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                                majiangGameQueryService.finishGameImmediately(gameValueObject);
                                gameMsgService.gameFinished(gameId);
                                JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
                                MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                                tuidaohuResultMsgService.recordJuResult(juResult);
                                List<String> queryScopes = new ArrayList<>();
                                gameMsgService.gameFinished(gameId);
                                queryScopes.add(QueryScope.juResult.name());
                                for (String otherPlayerId : gameValueObject.allPlayerIds()) {
                                    if (!otherPlayerId.equals(playerId)) {
                                        wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(gameValueObject.getState(), gameValueObject.findPlayerState(otherPlayerId)));
                                    }
                                }
                                data.put("queryScopes", queryScopes);
                                vo.setData(data);
                                return vo;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                                vo.setSuccess(false);
                                vo.setMsg(throwable.getClass().getName());
                                return vo;
                            }
                        }
                    }

                }
            }
        }
        ReadyToNextPanResult readyToNextPanResult;
        try {
            readyToNextPanResult = majiangPlayCmdService.readyToNextPan(playerId, playerIds);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        majiangPlayQueryService.readyToNextPan(readyToNextPanResult);

        // 通知其他人
        PanActionFrame firstActionFrame = readyToNextPanResult.getFirstActionFrame();
        List<QueryScope> queryScopes = new ArrayList<>();
        queryScopes.add(QueryScope.gameInfo);
        if (firstActionFrame != null) {
            queryScopes.add(QueryScope.panForMe);
        }
        // 通知其他人
        for (String otherPlayerId : readyToNextPanResult.getMajiangGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                List<QueryScope> scopes = QueryScope.scopesForState(readyToNextPanResult.getMajiangGame().getState(), readyToNextPanResult.getMajiangGame().findPlayerState(otherPlayerId));
                scopes.remove(QueryScope.panResult);
                wsNotifier.notifyToQuery(otherPlayerId, scopes);
            }
        }
        data.put("queryScopes", queryScopes);
        return vo;
    }

    /**
     * 通知观战者
     */
    private void hintWatcher(String gameId, String flag) {
        Map<String, Object> map = gameCmdService.getwatch(gameId);
        if (!CollectionUtils.isEmpty(map)) {
            List<String> playerIds = map.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
            wsNotifier.notifyToWatchQuery(playerIds, flag);
            if (WatchQueryScope.watchEnd.name().equals(flag)) {
                gameCmdService.recycleWatch(gameId);
            }
        }
    }
}
