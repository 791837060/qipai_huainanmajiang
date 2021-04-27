package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.c.service.GameTableCmdService;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.*;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengDiamondService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.MemberDiamondService;
import com.anbang.qipai.qinyouquan.msg.service.*;
import com.anbang.qipai.qinyouquan.plan.bean.LianmengWanfa;
import com.anbang.qipai.qinyouquan.plan.bean.MemberLatAndLon;
import com.anbang.qipai.qinyouquan.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.qinyouquan.remote.vo.MemberRemoteVO;
import com.anbang.qipai.qinyouquan.util.IPUtil;
import com.anbang.qipai.qinyouquan.web.fb.*;
import com.anbang.qipai.qinyouquan.web.vo.AllianceVO;
import com.anbang.qipai.qinyouquan.web.vo.CommonVO;
import com.anbang.qipai.qinyouquan.web.vo.GameTableVO;
import com.anbang.qipai.qinyouquan.plan.bean.game.*;
import com.anbang.qipai.qinyouquan.plan.service.*;
import com.google.gson.Gson;
import com.highto.framework.web.page.ListPage;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class GamePlayController {
    @Autowired
    private PlayService playService;

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRomoteService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private YangzhouMajiangGameRoomMsgService yangzhouMajiangGameRoomMsgService;

    @Autowired
    private YizhengMajiangGameRoomMsgService yizhengMajiangGameRoomMsgService;

    @Autowired
    private PaodekuaiGameRoomMsgService paodekuaiGameRoomMsgService;

    @Autowired
    private BijiGameRoomMsgService bijiGameRoomMsgService;

    @Autowired
    private TaizhouMajiangGameRoomMsgService taizhouMajiangGameRoomMsgService;

    @Autowired
    private TianchangxiaohuaGameRoomMsgService tianchangxiaohuaGameRoomMsgService;

    @Autowired
    private DoudizhuGameRoomMsgService doudizhuGameRoomMsgService;

    @Autowired
    private GaoyouMajiangGameRoomMsgService gaoyouMajiangGameRoomMsgService;

    @Autowired
    private HongzhongMajiangGameRoomMsgService hongzhongMajiangGameRoomMsgService;

    @Autowired
    private MaanshanMajiangGameRoomMsgService maanshanMajiangGameRoomMsgService;

    @Autowired
    private GuandanGameRoomMsgService guandanGameRoomMsgService;

    @Autowired
    private MemberDiamondService memberDiamondService;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private LianmengWanfaService lianmengWanfaService;

    @Autowired
    private LianmengDiamondService lianmengDiamondService;

    @Autowired
    private GameTableCmdService gameTableCmdService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;


    private final Gson gson = new Gson();

    @RequestMapping("/findAllWanfa")
    public CommonVO findAllWanfa(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, String token, String lianmengId) {
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

        try {
            List<LianmengWanfa> lianmengWanfaList = lianmengWanfaService.findAllWafabyLianmengId(page, size, lianmengId);
            vo.setMsg("find success");
            vo.setData(lianmengWanfaList);
            return vo;
        } catch (Exception e) {
            vo.setMsg("find error");
            vo.setSuccess(false);
            return vo;
        }
    }

    @RequestMapping("/join_yangzhouMajianggameroom")
    public CommonVO joinyangzhouMajiangGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);


        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.yangzhouMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }

        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        YzmjLawsFB fb = new YzmjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yitiaolongliufen", fb.getYitiaolongliufen());
        req.param("sipeihusibei", fb.getSipeihusibei());
        req.param("budaibanzihu", fb.getBudaibanzihu());
        req.param("peiziwanfa", fb.getPeiziwanfa());
        req.param("qidui", fb.getQidui());
        req.param("fengyise", fb.getFengyise());
        req.param("dihu", fb.getDihu());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());
        req.param("powerLimit", "0");

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        gameTable.setNo(roomNo);

        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_paodekuaigameroom")
    public CommonVO joinPaodekuaiGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.paodekuai, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        PdkLawsFB fb = new PdkLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
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
        req.param("powerLimit", "0");

        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_doudizhugameroom")
    public CommonVO joinDoudizhuGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.doudizhu, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }

        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        DdzLawsFB fb = new DdzLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
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
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");


        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());
        req.param("xianshishoupai", fb.getXianshishoupai());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }

        gameTable.setNo(roomNo);

        playService.createGameTable(gameTable, memberId);

        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_bijigameroom")
    public CommonVO joinBijiGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.biji, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        BjLawsFB fb = new BjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("gps", fb.getGps());
        req.param("voice", fb.getVoice());
        req.param("jinzhiyuyin", fb.getJinzhiyuyin());
        req.param("tuoguan", fb.getTuoguan());
        req.param("quanhonghei", fb.getQuanhonghei());
        req.param("quanshun", fb.getQuanshun());
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
        req.param("tongguan", fb.getTongguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");


        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_taizhouMajianggameroom")
    public CommonVO jointaizhouMajiangGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }

        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.taizhouMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        TzmjLawsFB fb = new TzmjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());

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
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_yizhengMajianggameroom")
    public CommonVO joinyizhengMajiangGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }

        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.yizhengMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        YizhengmjLawsFB fb = new YizhengmjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("budaihuangzhang", fb.getBudaihuangzhang());
        req.param("daihuangzhuang", fb.getDaihuangzhuang());
        req.param("daisihuang", fb.getDaisihuang());
        req.param("youpeizi", fb.getYoupeizi());
        req.param("shuangpeizi", fb.getShuangpeizi());
        req.param("quanyimen", fb.getQuanyimen());
        req.param("maima", fb.getMaima());
        req.param("minggangchenger", fb.getMinggangchenger());
        req.param("minggangyijiagei", fb.getMinggangyijiagei());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");


        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_tianchangxiaohuaGameroom")
    public CommonVO jointianchangxiaohuaGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }

        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.tianchangxiaohua, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        TcxhLawsFB fb = new TcxhLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("xiaohua", fb.getXiaohua());
        req.param("quanhua", fb.getQuanhua());
        req.param("zhaozhaohu", fb.getZhaozhaohu());
        req.param("baoting", fb.getBaoting());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建高邮麻将桌子
     */
    @RequestMapping("/join_gaoyouMajiangGameroom")
    public CommonVO joingaoyouMajiangGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.gaoyouMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        GymjLawsFB fb = new GymjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

        req.param("babashuang", fb.getBabashuang());
        req.param("baosanzui", fb.getBaosanzui());
        req.param("hupaitishi", fb.getHupaitishi());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_guandanGameroom")
    public CommonVO joinguandanGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.guandan, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        GdLawsFB fb = new GdLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());
        req.param("jinyuanzi", fb.getJinyuanzi());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/join_hongzhongMajiangGameroom")
    public CommonVO joinHzmjGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.hongzhongMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        TdhLawsFB fb = new TdhLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("qiangganghu", fb.getQiangganghu());
        req.param("qidui", fb.getQidui());
        req.param("keyidahu", fb.getKeyidahu());
        req.param("niaoshu", fb.getNiaoshu());

        Map resData;
        try {
            ContentResponse res = req.send();
            String resJson = new String(res.getContent());
            CommonVO resVo = gson.fromJson(resJson, CommonVO.class);
            resData = (Map) resVo.getData();
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 创建马鞍山麻将桌子
     */
    @RequestMapping("/join_maanshanMajiangGameroom")
    public CommonVO joinMasMjGameRoom(HttpServletRequest request, String token, String lianmengId, String wanfaId, String lat, String lon) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo.isBanAlliance()) {
            vo.setSuccess(false);
            vo.setMsg("alliance ban");
            return vo;
        }
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
        LianmengWanfa lianmengWanfa = lianmengWanfaService.findLianmengWanfaByWanfaId(wanfaId);
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        //判断用户是否开启gps
        List<String> laws1 = lianmengWanfa.getLaws();
        if (laws1.contains("gps")) {

            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String roomNo = gameTableCmdService.createTable(System.currentTimeMillis());
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }
        memberLatAndLonService.save(memberLatAndLon);
        Map data = new HashMap();
        GameTable gameTable;
        try {
            gameTable = playService.buildGameTable(memberId, lianmengId, Game.maanshanMajiang, lianmengWanfa);
        } catch (IllegalGameLawsException e) {
            vo.setSuccess(false);
            vo.setMsg("IllegalGameLawsException");
            return vo;
        } catch (NoServerAvailableForGameException e) {
            vo.setSuccess(false);
            vo.setMsg("NoServerAvailableForGameException");
            return vo;
        }
        List<String> lawNames = new ArrayList<>();
        List<GameLaw> laws = gameTable.getLaws();
        // 构建list laws
        laws.forEach((law) -> lawNames.add(law.getName()));
        MasmjLawsFB fb = new MasmjLawsFB(lawNames);
        int diamond = fb.payForCreateRoom();
        gameTable.setCost(diamond);
        if (verifyDiamondAccount(lianmengWanfa, diamond, memberId, lianmengId, gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, lianmengId)) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }

        GameServer gameServer = gameTable.getServerGame().getServer();
        Request req = httpClient.newRequest(gameServer.getHttpUrl() + "/game/newgame");
        req.param("playerId", memberId);
        req.param("panshu", fb.getPanshu());
        req.param("renshu", fb.getRenshu());
        req.param("voice", fb.getVoice());
        req.param("gps", fb.getGps());
        req.param("yuanzifen", lianmengWanfa.getYuanzifen());
        req.param("tuoguan", fb.getTuoguan());
        req.param("tuoguanjiesan", fb.getTuoguanjiesan());
        req.param("lixianchengfa", fb.getLixianchengfa());
        req.param("lixianchengfaScore", lianmengWanfa.getLixianchengfaScore());
        req.param("lixianshichang", lianmengWanfa.getLixianshichang());
        req.param("difen", lianmengWanfa.getDifen().toString());
        req.param("powerLimit", "0");
        req.param("buzhunbeituichushichang", allianceDbo.getBuzhunbeituichushichang().toString());
        req.param("zidongzhunbei", allianceDbo.getZidongzhunbei().toString());
        req.param("zidongkaishi", lianmengWanfa.getZidongkaishi().toString());
        req.param("zidongkaishiTime", lianmengWanfa.getZidongkaishiTime().toString());
        req.param("banVoice", fb.getBanVoice());
        req.param("banJiesan", fb.getBanJiesan());
        req.param("dairuzongfen", fb.getDairuzongfen());

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
            gameTable.getServerGame().setGameId((String) resData.get("gameId"));
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg("SysException");
            return vo;
        }
        gameTable.setNo(roomNo);
        playService.createGameTable(gameTable, memberId);
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("gameId", gameTable.getServerGame().getGameId());
        data.put("token", resData.get("token"));
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 加入房间
     */
    @RequestMapping(value = "/join_table")
    public CommonVO joinTable(HttpServletRequest request, String token, String roomNo, String lat, String lon) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        GameTable gameTable = playService.findOpenGameTable(roomNo);
        if (gameTable == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid room number");
            return vo;
        }
        if (gameTable.isGps()) {
            if (lat == null || lon == null) {
                vo.setSuccess(false);
                vo.setMsg("请开启定位");
                return vo;
            }
        }
        String realIp = IPUtil.getRealIp(request);
        MemberLatAndLon memberLatAndLon = new MemberLatAndLon();
        memberLatAndLon.setId(memberId);
        memberLatAndLon.setRepIP(realIp);
        memberLatAndLon.setRoomNo(roomNo);
        if (lat != null && lon != null) {
            memberLatAndLon.setLat(lat);
            memberLatAndLon.setLon(lon);
        }

        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, gameTable.getLianmengId());
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        GameMemberTable byMemberId = playService.findByMemberIdAndLianmengId(memberId, gameTable.getLianmengId());
        if (byMemberId != null && !byMemberId.getGameTable().getNo().equals(gameTable.getNo())) {
            vo.setSuccess(false);
            vo.setMsg("gameplayeralreadyingameexception");
            return vo;
        }
        String serverGameId = gameTable.getServerGame().getGameId();
        // 处理如果是自己暂时离开的房间
        GameMemberTable memberGameTable = playService.findMemberGameTable(memberId, gameTable.getId());
        if (memberGameTable != null) {
            LianmengWanfa lianmengWanfa = gameTable.getWanfa();
            if (lianmengWanfa == null) {
                vo.setSuccess(false);
                vo.setMsg("wanfa not found");
                return vo;
            }
            // 游戏服务器rpc返回房间
            GameServer gameServer = gameTable.getServerGame().getServer();
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
            data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
            data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
            data.put("roomNo", gameTable.getNo());
            data.put("token", resData.get("token"));
            data.put("gameId", serverGameId);
            data.put("game", gameTable.getGame());
            vo.setData(data);
            return vo;
        }
        if (!lianmengMemberService.verifyBanDesk(gameTable, memberId)) {
            vo.setSuccess(false);
            vo.setMsg("banDeskmate");
            return vo;
        }
        //加入房间
        if (gameTable.isGps()) {
            if (!memberLatAndLonService.verifyIp(memberLatAndLon, gameTable)) {
                vo.setSuccess(false);
                vo.setMsg("same ip");
                return vo;
            }
            if (!memberLatAndLonService.verifyGps(memberLatAndLon, gameTable)) {
                vo.setSuccess(false);
                vo.setMsg("distance limit");
                return vo;
            }
        }
        memberLatAndLonService.save(memberLatAndLon);
        LianmengWanfa lianmengWanfa = gameTable.getWanfa();
        if (lianmengWanfa == null) {
            vo.setSuccess(false);
            vo.setMsg("wanfa not found");
            return vo;
        }
        if (verifyDiamondAccount(lianmengWanfa, gameTable.getCost(), memberId, gameTable.getLianmengId(), gameTable.getPlayersCount())) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        }
        if (verifyLimitScore(memberId, gameTable.getLianmengId())) {
            vo.setSuccess(false);
            vo.setMsg("score limit");
            return vo;
        }
        // 游戏服务器rpc加入房间
        GameServer gameServer = gameTable.getServerGame().getServer();
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
        playService.joinGameTable(gameTable, memberId);

        Map data = new HashMap();
        data.put("httpUrl", gameTable.getServerGame().getServer().getHttpUrl());
        data.put("wsUrl", gameTable.getServerGame().getServer().getWsUrl());
        data.put("roomNo", gameTable.getNo());
        data.put("token", resData.get("token"));
        data.put("gameId", serverGameId);
        data.put("game", gameTable.getGame());
        vo.setData(data);
        return vo;
    }

    /**
     * 查询桌子
     */
    @RequestMapping(value = "/query_table")
    public CommonVO queryTable(String token, String lianmengId, @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "50") int size) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("lianmeng not found");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        Map data = new HashMap<>();
        vo.setData(data);
        List<GameTableVO> tableList = playService.findGameTableByLianmengId(lianmengId, page, size, allianceDbo);
        data.put("tableList", tableList);
        AllianceVO allianceVO = new AllianceVO();
        allianceVO.setId(allianceDbo.getId());
        allianceVO.setName(allianceDbo.getName());
        allianceVO.setDesc(allianceDbo.getDesc());
        allianceVO.setOnlineCount((int) lianmengMemberService.countOnlineMemberByLianmengId(lianmengId));
        allianceVO.setRenshu((int) lianmengMemberService.countrenshu(lianmengId));
        allianceVO.setKongzhuoqianzhi(allianceDbo.isKongzhuoqianzhi());
        allianceVO.setRenshuHide(allianceDbo.isRenshuHide());
        allianceVO.setNicknameHide(allianceDbo.isNicknameHide());
        allianceVO.setIdHide(allianceDbo.isIdHide());
        allianceVO.setBanAlliance(allianceDbo.isBanAlliance());
        allianceVO.setZhuomanHide(allianceDbo.isZhuomanHide());
        allianceVO.setBuzhunbeituichushichang(allianceDbo.getBuzhunbeituichushichang());
        allianceVO.setLianmengIdHide(allianceDbo.getLianmengIdHide());
        allianceVO.setZidongzhunbei(allianceDbo.getZidongzhunbei());
        LianmengDiamondAccountDbo lianmengDiamondAccountDbo = lianmengDiamondService.findDiamondAccountDbo(memberId);
        MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(memberId, lianmengId);
        MemberRemoteVO memberRemoteVO = qipaiMembersRomoteService.member_info(memberId);

        if (memberDiamondAccountDbo != null) {
            allianceVO.setDiamondAccount(memberDiamondAccountDbo.getBalance());
        } else {
            if (lianmengDiamondAccountDbo != null) {
                allianceVO.setDiamondAccount(lianmengDiamondAccountDbo.getBalance());
            }
        }
        int gold = Integer.parseInt(memberRemoteVO.getGold());
        allianceVO.setYushiAccount(gold);


        allianceVO.setJoin(lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId) != null);
        data.put("Identity", memberLianmengDbo.getIdentity());
        data.put("lianmeng", allianceVO);
        GameMemberTable memberTable = playService.findByMemberId(memberId);
        if (memberTable != null) {
            data.put("gameRoomNo", memberTable.getGameTable().getNo());
        }
        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            data.put("zhushou", "true");
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId).getIdentity().equals(Identity.MENGZHU)) {
                data.put("mengzhuZhushou", "true");
            }
        }

        return vo;
    }

    /**
     * 大联盟管理-联盟房间管理
     */
    @RequestMapping("/gametable_query")
    public CommonVO queryGameTable(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size,
                                   String memberId, String lianmengId, Game game, Long startTime, Long endTime, String no, String state) {
        CommonVO vo = new CommonVO();
        if (startTime == null) {
            startTime = 0L;
        }
        if (endTime == null) {
            endTime = 0L;
        }
        ListPage listPage = playService.queryGameTableByMemberIdAndLianmengIdAndGameAndTime(page, size, memberId, lianmengId, game, startTime, endTime, no, state);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 房间到时定时器，每小时
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void removeGameRoom() {
        long deadlineTime = System.currentTimeMillis();
        List<GameTable> tableList = playService.findExpireGameTable(deadlineTime);
        Map<Game, List<String>> gameIdMap = new HashMap<>();
        for (Game game : Game.values()) {
            gameIdMap.put(game, new ArrayList<>());
        }
        for (GameTable table : tableList) {
            Game game = table.getGame();
            String serverGameId = table.getServerGame().getGameId();
            if (!StringUtils.isBlank(serverGameId)) {
                gameIdMap.get(game).add(serverGameId);
            }
        }

        yangzhouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.yangzhouMajiang));
        paodekuaiGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.paodekuai));
        doudizhuGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.doudizhu));
        yizhengMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.yizhengMajiang));
        bijiGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.biji));
        taizhouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.taizhouMajiang));
        tianchangxiaohuaGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.tianchangxiaohua));
        gaoyouMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.gaoyouMajiang));
        guandanGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.guandan));
        hongzhongMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.hongzhongMajiang));
        maanshanMajiangGameRoomMsgService.removeGameRoom(gameIdMap.get(Game.maanshanMajiang));
    }

    private boolean verifyDiamondAccount(LianmengWanfa lianmengWanfa, int diamond, String memberId, String lianmengId, int renshu) {
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            LianmengDiamondAccountDbo lianmengDiamondAccountDbo = lianmengDiamondService.findDiamondAccountDbo(memberLianmengDbo.getMemberId());
            if (lianmengDiamondAccountDbo == null) {
                return true;
            }
            if (lianmengWanfa.getPayType().equals(GamePayType.AA)) {
                return diamond / renshu > lianmengDiamondAccountDbo.getBalance();
            } else {
                return diamond > lianmengDiamondAccountDbo.getBalance();
            }
        }

        MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(memberId, lianmengId);
        if (memberDiamondAccountDbo == null) {
            return true;
        }
        if (lianmengWanfa.getPayType().equals(GamePayType.AA)) {
            //余额不足
            return diamond > memberDiamondAccountDbo.getBalance();
        } else {
            if (memberLianmengDbo.isFree()) {
                MemberLianmengDbo memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), memberLianmengDbo.getLianmengId());
                if (memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                    LianmengDiamondAccountDbo lianmengDiamondAccountDbo = lianmengDiamondService.findDiamondAccountDbo(memberLianmengDbo1.getMemberId());
                    return diamond > lianmengDiamondAccountDbo.getBalance();
                } else {
                    MemberDiamondAccountDbo memberDiamondAccountDbo1 = memberDiamondService.findDiamondAccountDbo(memberLianmengDbo1.getMemberId(), memberLianmengDbo1.getLianmengId());
                    if (memberDiamondAccountDbo1 == null) {
                        return true;
                    }
                    //余额不足
                    return diamond > memberDiamondAccountDbo1.getBalance();
                }
            } else {
                //余额不足
                return diamond > memberDiamondAccountDbo.getBalance();
            }
        }
    }

    private boolean verifyLimitScore(String memberId, String lianmengId) {
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo.getMaxScore() == 0 && memberLianmengDbo.getMinScore() == 0) {
            return false;
        }
        return memberLianmengDbo.getMaxScore() < memberLianmengDbo.getScore() || memberLianmengDbo.getMinScore() < -memberLianmengDbo.getScore();
    }

}
