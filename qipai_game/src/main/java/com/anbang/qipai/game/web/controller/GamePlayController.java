package com.anbang.qipai.game.web.controller;

import com.anbang.qipai.game.cqrs.c.service.GameRoomCmdService;
import com.anbang.qipai.game.msg.service.*;
import com.anbang.qipai.game.plan.bean.games.*;
import com.anbang.qipai.game.plan.bean.members.MemberGoldBalance;
import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.game.plan.service.*;
import com.anbang.qipai.game.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.game.remote.vo.CommonRemoteVO;
import com.anbang.qipai.game.util.IPUtil;
import com.anbang.qipai.game.web.fb.*;
import com.anbang.qipai.game.web.vo.CommonVO;
import com.anbang.qipai.game.web.vo.MemberGameRoomVO;
import com.anbang.qipai.game.web.vo.MemberPlayingRoomVO;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 去玩游戏相关的控制器
 *
 * @author Neo
 */
@RestController
@RequestMapping("/game")
public class GamePlayController {

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MemberGoldBalanceService memberGoldBalanceService;

    @Autowired
    private GameRoomCmdService gameRoomCmdService;

    @Autowired
    private GameServerMsgService gameServerMsgService;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRomoteService;

    @Autowired
    private YangzhouMajiangGameRoomMsgService yangzhouMajiangGameRoomMsgService;

    @Autowired
    private ZongyangMajiangGameRoomMsgService zongyangMajiangGameRoomMsgService;

    @Autowired
    private YizhengMajiangGameRoomMsgService yizhengMajiangGameRoomMsgService;

    @Autowired
    private TaizhouMajiangGameRoomMsgService taizhouMajiangGameRoomMsgService;

    @Autowired
    private TianchangxiaohuaGameRoomMsgService tianchangxiaohuaGameRoomMsgService;

    @Autowired
    private GaoyouMajiangGameRoomMsgService gaoyouMajiangGameRoomMsgService;

    @Autowired
    private HongzhongMajiangGameRoomMsgService hongzhongMajiangGameRoomMsgService;

    @Autowired
    private MaanshanMajiangGameRoomMsgService maanshanMajiangGameRoomMsgService;

    @Autowired
    private GaoyouMajiangGameRoomMsgService taixingMajiangGameRoomMsgService;

    @Autowired
    private PaodekuaiGameRoomMsgService paodekuaiGameRoomMsgService;

    @Autowired
    private HuangshibaGameRoomMsgService huangshibaGameRoomMsgService;

    @Autowired
    private BohuGameRoomMsgService bohuGameRoomMsgService;

    @Autowired
    private GuandanGameRoomMsgService guandanGameRoomMsgService;

    @Autowired
    private BijiGameRoomMsgService bijiGameRoomMsgService;

    @Autowired
    private DoudizhuGameRoomMsgService doudizhuGameRoomMsgService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    @Autowired
    private MemberLoginLimitRecordService memberLoginLimitRecordService;

    @Autowired
    private RoomManageMsgService roomManageMsgService;

    @Autowired
    private HttpClient httpClient;

    private final Gson gson = new Gson();

    /**
     * 创建扬州麻将房间
     *
     * @param token
     * @param lawNames
     * @return
     */
    @RequestMapping(value = "/create_yzmj_room")
    @ResponseBody
    public CommonVO createYzmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.yangzhouMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        ZymjLawsFB fb = new ZymjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;

    }

    /**
     * 创建枞阳麻将房间
     *
     * @param token
     * @param lawNames
     * @return
     */
    @RequestMapping(value = "/create_zymj_room")
    @ResponseBody
    public CommonVO createZymjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.zongyangMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        ZymjLawsFB fb = new ZymjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("kepeng", fb.getKepeng());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;

    }

    /**
     * 创建跑得快房间
     */
    @RequestMapping(value = "/create_pdk_room")
    @ResponseBody
    public CommonVO createPdkRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                  String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.paodekuai, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        PdkLawsFB fb = new PdkLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("bichu", fb.getBichu());
        req.param("biya", fb.getBiya());
        req.param("aBoom", fb.getaBoom());
        req.param("sandaique", fb.getSandaique());
        req.param("feijique", fb.getFeijique());
        req.param("showShoupaiNum", fb.getShowShoupaiNum());
        req.param("zhuaniao", fb.getZhuaniao());
        req.param("sidaier", fb.getSidaier());
        req.param("sidaisan", fb.getSidaisan());
        req.param("shiwuzhang", fb.getShiwuzhang());
        req.param("shiliuzhang", fb.getShiliuzhang());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("zhadanwufen", fb.getZhadanwufen());
        req.param("zhadanshifen", fb.getZhadanshifen());
        req.param("sanAJiaYiBoom", fb.getSanAJiaYiBoom());
        req.param("shouABi2", fb.getShouABi2());
        req.param("A2Xiafang", fb.getA2Xiafang());
        req.param("zhadanfanbei", fb.getZhadanfanbei());
        req.param("yingsuanzha", fb.getYingsuanzha());
        req.param("zhadanbeiyabugeifen", fb.getZhadanbeiyabugeifen());
        req.param("daxiaoguan", fb.getDaxiaoguan());
        req.param("fanguan", fb.getFanguan());
        req.param("sandailiangdan", fb.getSandailiangdan());
        req.param("hongxinsanxianchu", fb.getHongxinsanxianchu());
        req.param("san3JiaYiBoom", fb.getSan3JiaYiBoom());
        req.param("sidaiyiBoom", fb.getSidaiyiBoom());
        req.param("heitaosanXianchuBubichu", fb.getHeitaosanXianchuBubichu());
        req.param("hongtaosanXianchuBubichu", fb.getHongtaosanXianchuBubichu());
        req.param("xiaoguanjiufen", fb.getXiaoguanjiufen());

        req.param("difen", "1");
        req.param("powerLimit", "0");

        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建斗地主房间
     *
     * @param token
     * @param lawNames
     * @return
     */
    @RequestMapping(value = "/create_ddz_room")
    @ResponseBody
    public CommonVO createDdzRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, Boolean zidongkaishi,
                                  Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();


        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.doudizhu, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        DdzLawsFB fb = new DdzLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币
        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("qxp", fb.getQxp());
        req.param("szfbxp", fb.getSzfbxp());
        req.param("gps", fb.getGps());
        req.param("voice", fb.getVoice());
        req.param("fdbs", fb.getFdbs());
        req.param("shuangwangBiqiang", fb.getSwbq());
        req.param("jiaofen", fb.getJfqdz());
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");
        req.param("xianshishoupai", fb.getXianshishoupai());
        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建仪征麻将房间
     *
     * @param token
     * @param lawNames
     * @return
     */
    @RequestMapping(value = "/create_yizhengmj_room")
    @ResponseBody
    public CommonVO createYizhengmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                        String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();


        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.yizhengMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        YizhengmjLawsFB fb = new YizhengmjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("budaihuangzhang", fb.getBudaihuangzhang());
        req.param("daihuangzhuang", fb.getDaihuangzhuang());
        req.param("daisihuang", fb.getDaisihuang());
        req.param("youpeizi", fb.getYoupeizi());
        req.param("shuangpeizi", fb.getShuangpeizi());
        req.param("quanyimen", fb.getQuanyimen());
        req.param("maima", fb.getMaima());
        req.param("minggangchenger", fb.getMinggangchenger());
        req.param("minggangyijiagei", fb.getMinggangyijiagei());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");

        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建比鸡快房间
     */
    @RequestMapping(value = "/create_bj_room")
    @ResponseBody
    public CommonVO createBjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                 String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();


        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();

        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.biji, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        BjLawsFB fb = new BjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("jinzhiyuyin", fb.getJinzhiyuyin());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);
        req.param("quanhonghei", fb.getQuanhonghei());
        req.param("quanshun", fb.getQuanshun());
        req.param("tongguan", fb.getTongguan());
        req.param("sanqing", fb.getSanqing());
        req.param("tonghuadatou", fb.getTonghuadatou());
        req.param("shunqingdatou", fb.getShunqingdatou());
        req.param("shuangsantiao", fb.getShuangsantiao());
        req.param("sizhang", fb.getSizhang());
        req.param("shuangshunqing", fb.getShuangshunqing());
        req.param("quanshunqing", fb.getQuanshunqing());
        req.param("quansantiao", fb.getQuansantiao());
        req.param("qipai", fb.getQipai());
        req.param("xipaigudingfen", fb.getXipaigudingfen());
        req.param("jisumoshi", fb.getJisumoshi());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");

        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping(value = "/create_tcxh_room")
    @ResponseBody
    public CommonVO createTcxhRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();


        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();

        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.tianchangxiaohua, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        TcxhLawsFB fb = new TcxhLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("xiaohua", fb.getXiaohua());
        req.param("quanhua", fb.getQuanhua());
        req.param("zhaozhaohu", fb.getZhaozhaohu());
        req.param("baoting", fb.getBaoting());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping(value = "/create_tzmj_room")
    @ResponseBody
    public CommonVO createTzmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();


        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();

        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.taizhouMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        TaizhoumjLawsFB fb = new TaizhoumjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);

        req.param("pengfang", fb.getPengfang());
        req.param("fengding", fb.getFengding());
        req.param("shukazi", fb.getShukazi());
        req.param("tingpaikejian", fb.getTingpaikejian());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建高邮麻将房间
     */
    @RequestMapping(value = "/create_gymj_room")
    @ResponseBody
    public CommonVO createGymjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.gaoyouMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        GymjLawsFB fb = new GymjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("babashuang", fb.getBabashuang());
        req.param("baosanzui", fb.getBaosanzui());
        req.param("hupaitishi", fb.getHupaitishi());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建掼蛋房间
     */
    @RequestMapping(value = "/create_gd_room")
    @ResponseBody
    public CommonVO createGdRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                 String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.guandan, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        GdLawsFB fb = new GdLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建红中麻将房间
     */
    @RequestMapping(value = "/create_hzmj_room")
    @ResponseBody
    public CommonVO createhzmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.hongzhongMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        TdhLawsFB fb = new TdhLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("qiangganghu", fb.getQiangganghu());
        req.param("qidui", fb.getQidui());
//        req.param("hongzhonglaizi", fb.getHongzhonglaizi());
        req.param("keyidahu", fb.getKeyidahu());
        req.param("niaoshu", fb.getNiaoshu());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建马鞍山麻将房间
     */
    @RequestMapping(value = "/create_masmj_room")
    @ResponseBody
    public CommonVO createMasmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                    String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.maanshanMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        MasmjLawsFB fb = new MasmjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
//        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("daozi", fb.getDaozi());
        req.param("suanfa", fb.getSuanfa());
        req.param("zimohupai", fb.getZimohupai());
        req.param("wufeng", fb.getWufeng());
        req.param("shidianqihu", fb.getShidianqihu());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建黄十八房间
     */
    @RequestMapping(value = "/create_hsb_room")
    @ResponseBody
    public CommonVO createHsbRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                  String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime, String baozipeifu, String ewaijiafendiyu, String ewaijiafenzengjia, String kundou) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.huangshiba, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        HsbLawsFB fb = new HsbLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("jiesuanfangshi", fb.getJiesuanfangshi());
        req.param("pengzhaoTishi", fb.getPengzhaoTishi());
        req.param("youzixianshi", fb.getYouzixianshi());
        req.param("fanxun", fb.getFanxun());
        req.param("xiaoerbusuanfen", fb.getXiaoerbusuanfen());
        req.param("baoziScore", baozipeifu);
        req.param("lowScore", ewaijiafendiyu);
        req.param("addScore", ewaijiafenzengjia);
        req.param("kundou", kundou);
        req.param("tuoguan", fb.getTuoguan());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("difen", "1");
        req.param("powerLimit", "0");

        req.param("tuoguan", fb.getTuoguan());
//        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
//        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
//        req.param("banVoice", fb.getBanVoice());
//        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");
//        req.param("jinyuanzi", fb.getJinyuanzi());
//        req.param("yuanzifen", yuanzifen);

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建博胡房间
     */
    @RequestMapping(value = "/create_bh_room")
    @ResponseBody
    public CommonVO createBhRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                 String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime, String baozipeifu, String ewaijiafendiyu, String ewaijiafenzengjia) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.bohu, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        BhLawsFB fb = new BhLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("jiesuanfangshi", fb.getJiesuanfangshi());
        req.param("pengzhaoTishi", fb.getPengzhaoTishi());
        req.param("youzixianshi", fb.getYouzixianshi());
        req.param("shiwuyouqihu", fb.getShiwuyouqihu());
        req.param("baoziScore", baozipeifu);
        req.param("lowScore", ewaijiafendiyu);
        req.param("addScore", ewaijiafenzengjia);
        req.param("tuoguan", fb.getTuoguan());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("difen", "1");
        req.param("powerLimit", "0");

        req.param("tuoguan", fb.getTuoguan());
//        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
//        req.param("lixianchengfa", fb.getLixianchengfa());
//        req.param("lixianchengfaScore", lixianchengfaScore);
//        req.param("lixianshichang", lixianshichang);
        req.param("zidongzhunbei", "false");
//        req.param("zidongkaishi", zidongkaishi.toString());
//        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
//        req.param("banVoice", fb.getBanVoice());
//        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");
//        req.param("jinyuanzi", fb.getJinyuanzi());
//        req.param("yuanzifen", yuanzifen);

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }

        gameRoom.setNo(roomNo);

        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);

        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建泰兴麻将房间
     */
    @RequestMapping(value = "/create_txmj_room")
    @ResponseBody
    public CommonVO createTxmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.taixingMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        TxmjLawsFB fb = new TxmjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("baosantankehuanpai", fb.getBaosantankehuanpai());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建寿县麻将房间
     */
    @RequestMapping(value = "/create_sxmj_room")
    @ResponseBody
    public CommonVO createsxmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.shouxianMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        SxmjLawsFB fb = new SxmjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("jiaopao", fb.getJiaopao());


        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建淮南麻将房间
     */
    @RequestMapping(value = "/create_hnmj_room")
    @ResponseBody
    public CommonVO createhnmjRoom(HttpServletRequest request, String token, @RequestBody List<String> lawNames, String lat, String lon, String yuanzifen, String lixianchengfaScore,
                                   String lixianshichang, Boolean zidongkaishi, Double zidongkaishiTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        String roomNo = gameRoomCmdService.createRoom(memberId, System.currentTimeMillis());
        if (lawNames.contains("gps")) {
            if (lat == null || lon == null) {
                vo.setMsg("请打开定位");
                vo.setSuccess(false);
                return vo;
            }

        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setId(memberId);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        Map data = new HashMap();
        GameRoom gameRoom;
        try {
            gameRoom = gameService.buildGameRoom(Game.shouxianMajiang, memberId, lawNames);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        SxmjLawsFB fb = new SxmjLawsFB(lawNames);
        // 普通会员每日开房（vip房）金币价格
        int gold = fb.payForCreateRoom();
        // 房主玩家记录
        List<PlayersRecord> playersRecord = new ArrayList<>();
        gameRoom.setPlayersRecord(playersRecord);
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员开vip房扣金币

        GameServer gameServer = gameRoom.getServerGame().getServer();
        // 游戏服务器rpc，需要手动httpclientrpc
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", yuanzifen);
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lixianchengfaScore);
        req.param("lixianshichang", lixianshichang);
        req.param("difen", "1");
        req.param("powerLimit", "0");
        req.param("zidongzhunbei", "false");
        req.param("zidongkaishi", zidongkaishi.toString());
        req.param("zidongkaishiTime", zidongkaishiTime.toString());
        req.param("buzhunbeituichushichang", "0");
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", "false");

        req.param("jiaopao", fb.getJiaopao());


        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameRoom.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 普通会员开vip房扣金币
        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for create room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }
        gameRoom.setNo(roomNo);
        gameService.createGameRoom(gameRoom);
        // 发送房间创建消息
        roomManageMsgService.creatRoom(gameRoom);
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("gameId", gameRoom.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 加入房间。如果加入的是自己暂时离开的房间，那么就变成返回房间
     */
    @RequestMapping(value = "/join_room")
    @ResponseBody
    public CommonVO joinRoom(HttpServletRequest request, String token, String roomNo, String lat, String lon) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        GameRoom gameRoom = gameService.findRoomOpen(roomNo);
        if (gameRoom == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid room number");
            return vo;
        }
        if (gameRoom.isGps()) {
            if (lon == null || lat == null) {
                vo.setSuccess(false);
                vo.setMsg("请打开定位");
                return vo;
            }
        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRoomNo(roomNo);
        memberLatAndLon.setRepIP(realIp);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        String serverGameId = gameRoom.getServerGame().getGameId();

        // 处理如果是自己暂时离开的房间
        MemberGameRoom memberGameRoom = gameService.findMemberGameRoom(memberId, gameRoom.getId());
        if (memberGameRoom != null) {
            // 游戏服务器rpc返回房间
            GameServer gameServer = gameRoom.getServerGame().getServer();
            Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/backtogame");
            req.param("playerId", memberId);
            req.param("gameId", serverGameId);
            Map resData;
            try {
                ContentResponse res = req.send();
                String resJson = new String(res.getContent());
                CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
                if (resVo.isSuccess()) {
                    resData = (Map) resVo.getData();
                } else {
                    vo.setSuccess(false);
                    vo.setMsg(resVo.getMsg());
                    return vo;
                }
            } catch (Exception e) {
                vo.setSuccess(false);
                vo.setMsg("SysException");
                return vo;
            }

            Map data = new HashMap();
            data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
            data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
            data.put("roomNo", gameRoom.getNo());
            data.put("token", resData.get("token"));
            data.put("gameId", serverGameId);
            data.put("game", gameRoom.getGame());
            vo.setData(data);
            return vo;
        }

        // 判断普通会员个人账户的余额能否支付加入房间的费用
        MemberGoldBalance memberGoldBalance = memberGoldBalanceService.findByMemberId(memberId);
        if (memberGoldBalance == null) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        int gold = 0;
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameRoom.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        if (gameRoom.getGame().equals(Game.paodekuai)) {
            gold = new PdkLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.yangzhouMajiang)) {
            gold = new YzmjLawsFB(lawNames).payForJoinRoom();
        }else if (gameRoom.getGame().equals(Game.zongyangMajiang)) {
            gold = new ZymjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.doudizhu)) {
            gold = new DdzLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.yizhengMajiang)) {
            gold = new YizhengmjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.biji)) {
            gold = new BjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.taizhouMajiang)) {
            gold = new TaizhoumjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.tianchangxiaohua)) {
            gold = new TcxhLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.gaoyouMajiang)) {
            gold = new GymjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.guandan)) {
            gold = new GdLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.taixingMajiang)) {
            gold = new TxmjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.hongzhongMajiang)) {
            gold = new TdhLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.maanshanMajiang)) {
            gold = new MasmjLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.huangshiba)) {
            gold = new HsbLawsFB(lawNames).payForJoinRoom();
        } else if (gameRoom.getGame().equals(Game.bohu)) {
            gold = new BhLawsFB(lawNames).payForJoinRoom();
        }

        int balance = memberGoldBalance.getBalanceAfter();
        if (balance < gold) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }

        // 游戏服务器rpc加入房间
        GameServer gameServer = gameRoom.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/joingame");
        req.param("playerId", memberId);
        req.param("gameId", serverGameId);
        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            if (resVo.isSuccess()) {
                resData = (Map) resVo.getData();
            } else {
                vo.setSuccess(false);
                vo.setMsg(resVo.getMsg());
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        // 加入房间玩家记录,列表从第一开始，第0个是房主
        List<PlayersRecord> playersRecord = gameRoom.getPlayersRecord();
        PlayersRecord record = new PlayersRecord();
        record.setPlayerId(memberId);
        record.setPayGold(gold);
        playersRecord.add(record);
        gameService.saveGameRoom(gameRoom);
        // 普通会员加入vip房完成玉石扣除才能加入房间

        CommonRemoteVO rvo = qipaiMembersRomoteService.gold_withdraw(memberId, gold, "pay for join room");
        if (!rvo.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(rvo.getMsg());
            return vo;
        }


        gameService.joinGameRoom(gameRoom, memberId);
        // 发送房间更新消息
        roomManageMsgService.updatePlayer(gameRoom);

        Map data = new HashMap();
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("token", resData.get("token"));
        data.put("gameId", serverGameId);
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;

    }

    /**
     * 游戏服务器上线
     *
     * @param gameServer
     * @return
     */
    @RequestMapping(value = "/game_server_online")
    @ResponseBody
    public CommonVO gameserveronline(@RequestBody GameServer gameServer) {
        CommonVO vo = new CommonVO();
        gameService.onlineServer(gameServer);
        gameServerMsgService.gameServerOnline(gameServer);
        return vo;
    }

    /**
     * 游戏服务器下线
     *
     * @return
     */
    @RequestMapping(value = "/game_server_offline")
    @ResponseBody
    public CommonVO gameserveroffline(@RequestBody String[] gameServerIds) {
        CommonVO vo = new CommonVO();
        gameService.offlineServer(gameServerIds);
        gameServerMsgService.gameServerOffline(gameServerIds);
        return vo;
    }

    /**
     * 添加玩法
     *
     * @return
     */
    @RequestMapping(value = "/add_law")
    @ResponseBody
    public CommonVO addlaw(@RequestBody GameLaw law) {
        CommonVO vo = new CommonVO();
        gameService.createGameLaw(law);
        gameServerMsgService.createGameLaw(law);
        return vo;
    }

    /**
     * 删除玩法
     *
     * @param lawId
     * @return
     */
    @RequestMapping(value = "/remove_law")
    @ResponseBody
    public CommonVO removelaw(String lawId) {
        CommonVO vo = new CommonVO();
        gameService.removeGameLaw(lawId);
        gameServerMsgService.removeGameLaw(lawId);
        return vo;
    }

    /**
     * 编辑玩法
     *
     * @return
     */
    @RequestMapping(value = "/update_law")
    @ResponseBody
    public CommonVO updatelaw(@RequestBody GameLaw law) {
        CommonVO vo = new CommonVO();
        gameService.createGameLaw(law);
        gameServerMsgService.updateGameLaw(law);
        return vo;
    }

    /**
     * 添加玩法互斥组
     *
     * @return
     */
    @RequestMapping(value = "/add_mutexgroup")
    @ResponseBody
    public CommonVO addmutexgroup(@RequestBody LawsMutexGroup lawsMutexGroup) {
        CommonVO vo = new CommonVO();
        gameService.addLawsMutexGroup(lawsMutexGroup);
        gameServerMsgService.addLawsMutexGroup(lawsMutexGroup);
        return vo;
    }

    /**
     * 删除玩法互斥组
     *
     * @return
     */
    @RequestMapping(value = "/remove_mutexgroup")
    @ResponseBody
    public CommonVO removemutexgroup(String groupId) {
        CommonVO vo = new CommonVO();
        gameService.removeLawsMutexGroup(groupId);
        gameServerMsgService.removeLawsMutexGroup(groupId);
        return vo;
    }

    /**
     * 查询玩家当前正在游戏的房间
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/query_membergameroom")
    public CommonVO queryMemberGameRoom(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        List<MemberGameRoomVO> roomList = new ArrayList<>();
        List<MemberGameRoom> rooms = gameService.queryMemberGameRoomForMember(memberId);
        // List<MemberGameRoom> roomList =
        // gameService.queryMemberGameRoomForMember(memberId);
        for (MemberGameRoom room : rooms) {
            GameRoom gameRoom = room.getGameRoom();
            MemberGameRoomVO roomVo = new MemberGameRoomVO(gameRoom.getNo(), gameRoom.getGame(),
                    gameRoom.getPlayersCount(), gameRoom.getCurrentPanNum(), gameRoom.getPanCountPerJu(),
                    gameRoom.getDeadlineTime());
            roomList.add(roomVo);
        }
        vo.setMsg("room list");
        vo.setData(roomList);
        return vo;
    }

    /**
     * 后台rpc查询会员游戏房间
     *
     * @return
     */
    @RequestMapping(value = "/query_memberplayingroom")
    public CommonVO queryMemberPlayingRoom(String memberId) {
        CommonVO vo = new CommonVO();
        List<MemberGameRoom> roomList = gameService.queryMemberGameRoomForMember(memberId);
        List<MemberPlayingRoomVO> gameRoomList = new ArrayList<>();
        roomList.forEach((memberGameRoom) -> {
            gameRoomList.add(new MemberPlayingRoomVO(memberGameRoom.getGameRoom()));
        });
        vo.setMsg("room list");
        vo.setData(gameRoomList);
        return vo;
    }

    /**
     * 按时间删除游戏数据
     *
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/remove")
    public CommonVO removedata(long endTime) {
        CommonVO vo = new CommonVO();
        gameService.removeGameData(endTime);
        return vo;
    }

    /**
     * 房间到时定时器，每小时
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void removeGameRoom() {
        long deadlineTime = System.currentTimeMillis();
        List<GameRoom> roomList = gameService.findExpireGameRoom(deadlineTime);
        Map<Game, List<String>> gameIdMap = new HashMap<>();
        for (Game game : Game.values()) {
            gameIdMap.put(game, new ArrayList<>());
        }
        for (GameRoom room : roomList) {
            Game game = room.getGame();
            String serverGameId = room.getServerGame().getGameId();
            if (!StringUtils.isBlank(serverGameId)) {
                gameIdMap.get(game).add(serverGameId);
            }
        }
        paodekuaiGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.paodekuai));
        yangzhouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.yangzhouMajiang));
        zongyangMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.zongyangMajiang));
        doudizhuGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.doudizhu));
        yizhengMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.yizhengMajiang));
        bijiGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.biji));
        taizhouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.taizhouMajiang));
        tianchangxiaohuaGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.tianchangxiaohua));
        gaoyouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.gaoyouMajiang));
        guandanGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.guandan));
        taixingMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.taixingMajiang));
        hongzhongMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.hongzhongMajiang));
        maanshanMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.maanshanMajiang));
        huangshibaGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.huangshiba));
        bohuGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.bohu));
    }

    /**
     * 加入观战
     */
    @RequestMapping(value = "/joinWatch")
    @ResponseBody
    public CommonVO joinWatch(String token, String roomNo) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLoginLimitRecord loginLimitRecord = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (loginLimitRecord != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }

        GameRoom gameRoom = gameService.findRoomOpen(roomNo);
        if (gameRoom == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid room number");
            return vo;
        }
        String serverGameId = gameRoom.getServerGame().getGameId();
        GameServer gameServer = gameRoom.getServerGame().getServer();

        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/joinwatch");
        req.param("playerId", memberId);
        req.param("gameId", serverGameId);
        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            if (resVo.isSuccess()) {
                resData = (Map) resVo.getData();
            } else {
                vo.setSuccess(false);
                vo.setMsg(resVo.getMsg());
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        Map data = new HashMap();
        data.put("httpUrl", gameRoom.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameRoom.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameRoom.getNo());
        data.put("token", resData.get("token"));
        data.put("gameId", serverGameId);
        data.put("game", gameRoom.getGame());
        vo.setData(data);
        return vo;
    }
}
