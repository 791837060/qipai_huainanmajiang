package com.anbang.qipai.biji.web.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.anbang.qipai.biji.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.biji.cqrs.q.dbo.BijiPanPlayerResultDbo;
import com.anbang.qipai.biji.remote.service.QipaiDalianmengRemoteService;
import com.anbang.qipai.biji.remote.vo.CommonRemoteVO;
import com.anbang.qipai.biji.web.vo.*;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.biji.cqrs.c.service.GameCmdService;
import com.anbang.qipai.biji.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.biji.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.biji.cqrs.c.service.impl.PukePlayCmdServiceImpl;
import com.anbang.qipai.biji.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.biji.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.biji.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalPanResult;
import com.anbang.qipai.biji.msg.service.BijiGameMsgService;
import com.anbang.qipai.biji.msg.service.BijiResultMsgService;
import com.anbang.qipai.biji.websocket.GamePlayWsNotifier;
import com.anbang.qipai.biji.websocket.QueryScope;
import com.anbang.qipai.biji.websocket.WatchQueryScope;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pan.PanActionFrame;

@RestController
@RequestMapping("/pk")
public class PukeController {

    @Autowired
    private PlayerAuthService playerAuthService;

    @Autowired
    private PukePlayCmdService pukePlayCmdService;

    @Autowired
    private PukePlayQueryService pukePlayQueryService;

    @Autowired
    private PukeGameQueryService pukeGameQueryService;

    @Autowired
    private BijiResultMsgService bijiResultMsgService;

    @Autowired
    private BijiGameMsgService gameMsgService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private PukePlayCmdServiceImpl pukePlayCmdServiceImpl;

    @Autowired
    private QipaiDalianmengRemoteService qipaiDalianmengRemoteService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 当前盘我应该看到的所有信息
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
            panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId, playerId);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        data.put("panActionFrame", new PanActionFrameVO(panActionFrame));
        return vo;

    }

    /**
     * 当前盘我应该有的出牌组合
     */
    @RequestMapping(value = "/pan_combine_for_me")
    @ResponseBody
    public CommonVO pancombineforme(String token, String gameId, String playId) {
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
            panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId, playerId);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        List<ShisanshuiPlayerValueObject> objList = panActionFrame.getPanAfterAction().getPlayerList();
        for (ShisanshuiPlayerValueObject obj : objList) {
            if (obj.getId().equals(playerId)) {
                data.put("panZuHe", obj.getChupaiSolutionForTips());
            }
        }
        return vo;
    }

    /**
     * 查询玩家手里的牌
     *
     * @param gameId   游戏ID
     * @param playerId 玩家ID
     */
    @RequestMapping(value = "/query_shoupai")
    @ResponseBody
    public CommonVO queryshoupai(String gameId, String playerId) {
        CommonVO vo = new CommonVO();
        PanActionFrame panActionFrame;
        try {
            panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId, playerId);
            vo.setData(panActionFrame);
            return vo;
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
    }

    /**
     * 查询一道信息
     */
    @RequestMapping(value = "/query_dao")
    @ResponseBody
    public CommonVO querydao(String token, String gameId, String index) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        Dao dao;
        try {
            dao = pukePlayCmdServiceImpl.findDaoByGameIdAndPlayerIdAndIndex(gameId, playerId, index);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        data.put("dao", dao);
        return vo;
    }

    /**
     * 盘结果
     *
     * @param gameId 游戏ID
     * @param panNo  0代表不知道盘号，那么就取最新的一盘
     */
    @RequestMapping(value = "/pan_result")
    @ResponseBody
    public CommonVO panresult(String gameId, int panNo) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        if (panNo == 0) {
            panNo = pukeGameDbo.getPanNo();
        }
        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, panNo);
        data.put("panResult", new PanResultVO(panResultDbo, pukeGameDbo));
        return vo;
    }

    /**
     * 局结果
     *
     * @param gameId 游戏ID
     */
    @RequestMapping(value = "/ju_result")
    @ResponseBody
    public CommonVO juresult(String gameId) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
        if (juResultDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("not find juresult");
            return vo;
        }
        data.put("juResult", new JuResultVO(juResultDbo, pukeGameDbo));
        return vo;
    }

    /**
     * 出牌
     *
     * @param token         玩家token
     * @param toudaoIndex   头道索引
     * @param zhongdaoIndex 中道索引
     * @param weidaoIndex   尾道索引
     */
    @RequestMapping(value = "/chupai")
    @ResponseBody
    public CommonVO chupai(String token, String toudaoIndex, String zhongdaoIndex, String weidaoIndex) {
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
            logger.info("action:chupai," + "startTime:" + startTime + "," + "playerId:" + playerId + ","
                    + "dianshuZuheIdx:" + toudaoIndex + zhongdaoIndex + weidaoIndex + "," + "success:" + vo.isSuccess()
                    + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }

        PukeActionResult pukeActionResult;
        String endFlag = "query";
        try {
            pukeActionResult = pukePlayCmdService.chupai(playerId, toudaoIndex, zhongdaoIndex, weidaoIndex,
                    System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            long endTime = System.currentTimeMillis();
            logger.info("action:chupai," + "startTime:" + startTime + "," + "playerId:" + playerId + ","
                    + "dianshuZuheIdx:" + toudaoIndex + zhongdaoIndex + weidaoIndex + "," + "success:" + vo.isSuccess()
                    + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }
        pukePlayQueryService.action(pukeActionResult);

        if (pukeActionResult.getPanResult() == null) {// 盘没结束
            queryScopes.add(QueryScope.gameInfo.name());
            queryScopes.add(QueryScope.panForMe.name());
        } else {// 盘结束了
            String gameId = pukeActionResult.getPukeGame().getId();
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            if (pukeActionResult.getJuResult() != null) {// 局也结束了
                JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                bijiResultMsgService.recordJuResult(juResult);
                gameMsgService.gameFinished(gameId);
                queryScopes.add(QueryScope.juResult.name());
                endFlag = WatchQueryScope.watchEnd.name();
            } else {
                Map<String, String> playerIdGameIdMap;
                int tuoguanCount = 0;
                try {
                    playerIdGameIdMap = gameCmdService.playLeaveGameHosting(playerId, null, false);
                    if (playerIdGameIdMap != null) {
                        Set<String> playerIds = playerIdGameIdMap.keySet();//托管玩家集合
                        tuoguanCount = playerIds.size();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pukeGameDbo.getOptionalPlay().isTuoguanjiesan() && tuoguanCount != 0) {  //托管盘解散
                    try {
                        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
                        PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
                        bijiResultMsgService.recordPanResult(panResult);
                        gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
                        PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                        pukeGameQueryService.finishGameImmediately(gameValueObject, panResultDbo);
                        gameMsgService.gameFinished(gameId);
                        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                        PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                        bijiResultMsgService.recordJuResult(juResult);
                        gameMsgService.gameFinished(gameId);
                        queryScopes.add(QueryScope.juResult.name());
                        for (String otherPlayerId : gameValueObject.allPlayerIds()) {
                            wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(gameValueObject.getState(), gameValueObject.findPlayerState(otherPlayerId)));
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
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
            PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
            bijiResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
        }
        // 通知其他人
        for (String otherPlayerId : pukeActionResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeActionResult.getPukeGame().findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeActionResult.getPukeGame().getState(), pukeActionResult.getPukeGame().findPlayerState(otherPlayerId)));
                }
            }
        }

        // 通知观战者
        hintWatcher(pukeActionResult.getPukeGame().getId(), endFlag);

        long endTime = System.currentTimeMillis();
        logger.info("action:chupai," + "startTime:" + startTime + "," + "gameId:"
                + pukeActionResult.getPukeGame().getId() + "," + "playerId:" + playerId + "," + "dianshuZuheIdx:"
                + toudaoIndex + zhongdaoIndex + weidaoIndex + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg()
                + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
        return vo;
    }

    /**
     * 弃牌
     */
    @RequestMapping(value = "/qipai")
    @ResponseBody
    public CommonVO qipai(String token) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        vo.setData(data);
        String playerId = playerAuthService.getPlayerIdByToken(token);
        if (playerId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        Map<String, Boolean> qipaiMap;
        try {
            qipaiMap = pukePlayCmdServiceImpl.qipai(playerId, null);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }

        data.put("qipaiMap", qipaiMap.get(playerId));
        return vo;
    }

    /**
     * 准备开始下一盘
     *
     * @param token      玩家token
     * @param gameId     游戏ID
     */
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
        PukeGameDbo pukeGameDboById = pukeGameQueryService.findPukeGameDboById(gameId);
        String lianmengId = pukeGameDboById.getLianmengId();
        if (lianmengId != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeGameDbo.getPanNo());
            for (BijiPanPlayerResultDbo bijiPanPlayerResultDbo : panResultDbo.getPlayerResultList()) {
                CommonRemoteVO commonRemoteVO = qipaiDalianmengRemoteService.nowPowerForRemote(bijiPanPlayerResultDbo.getPlayerId(), lianmengId);
                if (commonRemoteVO.isSuccess()){
                    Map powerdata = new HashMap();
                    powerdata = (Map) commonRemoteVO.getData();
                    double powerbalance = (Double) powerdata.get("powerbalance");
//                    if (pukeGameDbo.getOptionalPlay().isJinyuanzi()) {
//                        powerbalance -= pukeGameDbo.getOptionalPlay().getYuanzifen();
//                    }
                    if (bijiPanPlayerResultDbo.getPlayerResult().getTotalScore() + powerbalance <= pukeGameDbo.getPowerLimit()) {
                        try {
                            PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                            pukeGameQueryService.finishGameImmediately(gameValueObject);
                            gameMsgService.gameFinished(gameId);
                            JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                            PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                            bijiResultMsgService.recordJuResult(juResult);
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
        ReadyToNextPanResult readyToNextPanResult;
        try {
            readyToNextPanResult = pukePlayCmdService.readyToNextPan(playerId, playerIds);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        pukePlayQueryService.readyToNextPan(readyToNextPanResult);
        // 通知其他人
        for (String otherPlayerId : readyToNextPanResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = readyToNextPanResult.getPukeGame().findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(readyToNextPanResult.getPukeGame().getState(), readyToNextPanResult.getPukeGame().findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }
        }
        List<QueryScope> queryScopes = new ArrayList<>();
        queryScopes.add(QueryScope.gameInfo);
        if (readyToNextPanResult.getPukeGame().getState().name().equals(Playing.name)) {
            queryScopes.add(QueryScope.panForMe);
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
