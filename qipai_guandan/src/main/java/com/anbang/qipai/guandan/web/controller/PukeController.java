package com.anbang.qipai.guandan.web.controller;

import com.anbang.qipai.guandan.cqrs.c.domain.Automatic;
import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ChaodiResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.guandan.cqrs.c.domain.state.StartChaodi;
import com.anbang.qipai.guandan.cqrs.c.service.GameCmdService;
import com.anbang.qipai.guandan.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.guandan.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.guandan.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;
import com.anbang.qipai.guandan.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.guandan.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.guandan.msg.msjobj.PukeHistoricalPanResult;
import com.anbang.qipai.guandan.msg.service.GuandanGameMsgService;
import com.anbang.qipai.guandan.msg.service.GuandanResultMsgService;
import com.anbang.qipai.guandan.utils.SpringUtil;
import com.anbang.qipai.guandan.web.vo.*;
import com.anbang.qipai.guandan.websocket.GamePlayWsNotifier;
import com.anbang.qipai.guandan.websocket.QueryScope;
import com.anbang.qipai.guandan.websocket.WatchQueryScope;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.shuangkou.pan.PanActionFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

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
    private GuandanResultMsgService guandanResultMsgService;

    @Autowired
    private GuandanGameMsgService gameMsgService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private GameCmdService gameCmdService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);


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
        PukeGameInfoDbo pukeGameInfoDbo;
        try {
            panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId, playerId);
            pukeGameInfoDbo = pukePlayQueryService.findAndFilterCurrentGameInfoForPlayer(gameId, playerId);
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getMessage());
            return vo;
        }
        data.put("panActionFrame", new PanActionFrameVO(panActionFrame));
        data.put("gameInfo", new GameInfoVO(pukeGameInfoDbo));
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
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        if (panNo == 0) {
            panNo = pukeGameDbo.getPanNo();
        }
        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, panNo);
        data.put("panResult", new PanResultVO(panResultDbo, pukeGameDbo));
        return vo;
    }

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

    @RequestMapping(value = "/chaodi")
    @ResponseBody
    public CommonVO chaodi(String token, boolean chaodi) {
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
            logger.info("action:chaodi," + "startTime:" + startTime + ",playerId:" + playerId + "chaodi:" + chaodi
                    + ",success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + ",endTime:" + endTime + ",use:"
                    + (endTime - startTime) + "ms");
            return vo;
        }

        ChaodiResult chaodiResult;
        String endFlag = "query";
        try {
            chaodiResult = pukePlayCmdService.chaodi(playerId, chaodi, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            long endTime = System.currentTimeMillis();
            logger.info("action:chaodi," + "startTime:" + startTime + ",playerId:" + playerId + "chaodi:" + chaodi
                    + ",success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + ",endTime:" + endTime + ",use:"
                    + (endTime - startTime) + "ms");
            return vo;
        }
        pukePlayQueryService.chaodi(chaodiResult);

        if (chaodiResult.getPanResult() == null) {// 盘没结束
            if (chaodiResult.getPukeGame().getState().name().equals(StartChaodi.name)) {
                queryScopes.add(QueryScope.chaodiInfo.name());
            }
            if (chaodiResult.getPukeGame().getState().name().equals(Playing.name)) {
                queryScopes.add(QueryScope.panForMe.name());
                queryScopes.add(QueryScope.gameInfo.name());
            }
        } else {// 盘结束了
            String gameId = chaodiResult.getPukeGame().getId();
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            if (chaodiResult.getJuResult() != null) {// 局也结束了
                JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                guandanResultMsgService.recordJuResult(juResult);

                gameMsgService.gameFinished(gameId);
                queryScopes.add(QueryScope.juResult.name());
                endFlag = WatchQueryScope.watchEnd.name();
            } else {
                queryScopes.add(QueryScope.panResult.name());
                queryScopes.add(QueryScope.gameInfo.name());
                endFlag = WatchQueryScope.panResult.name();
            }
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId,
                    chaodiResult.getPanResult().getPan().getNo());
            PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
            guandanResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(chaodiResult.getPukeGame(),
                    chaodiResult.getPanActionFrame().getPanAfterAction());

        }
        // 通知其他人
        for (String otherPlayerId : chaodiResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = chaodiResult.getPukeGame().findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId,
                            QueryScope.scopesForState(chaodiResult.getPukeGame().getState(),
                                    chaodiResult.getPukeGame().findPlayerState(otherPlayerId)));
                }
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("action:chaodi," + "startTime:" + startTime + ",playerId:" + playerId + "chaodi:" + chaodi
                + ",success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + ",endTime:" + endTime + ",use:"
                + (endTime - startTime) + "ms");

        hintWatcher(chaodiResult.getPukeGame().getId(), endFlag);
        return vo;
    }

    @RequestMapping(value = "/da")
    @ResponseBody
    public CommonVO da(String token, @RequestBody List<Integer> paiIds, String dianshuZuheIdx) {
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
            logger.info("action:da," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "paiIds:"
                    + paiIds + "," + "dianshuZuheIdx:" + dianshuZuheIdx + "," + "success:" + vo.isSuccess() + ",msg:"
                    + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }

        PukeActionResult pukeActionResult;
        String endFlag = "query";
        try {
            pukeActionResult = pukePlayCmdService.da(playerId, new ArrayList<>(paiIds), dianshuZuheIdx, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            long endTime = System.currentTimeMillis();
            logger.info("action:da," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "paiIds:"
                    + paiIds + "," + "dianshuZuheIdx:" + dianshuZuheIdx + "," + "success:" + vo.isSuccess() + ",msg:"
                    + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
            return vo;
        }
        pukePlayQueryService.action(pukeActionResult);

        automatic.removeTuoguanPlayerIdSet(playerId);//玩家执行动作后就将玩家在托管列表中移除

        if (pukeActionResult.getPanResult() == null) {// 盘没结束
            queryScopes.add(QueryScope.gameInfo.name());
            queryScopes.add(QueryScope.panForMe.name());
        } else {// 盘结束了
            String gameId = pukeActionResult.getPukeGame().getId();
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            if (pukeActionResult.getJuResult() != null) {// 局也结束了
                JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                guandanResultMsgService.recordJuResult(juResult);
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
                if (pukeGameDbo.getOptionalPlay().isTuoguanjiesan() && tuoguanCount != 0) {
                    try {
                        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
                        PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
                        guandanResultMsgService.recordPanResult(panResult);
                        gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
                        PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                        pukeGameQueryService.finishGameImmediately(gameValueObject, panResultDbo);
                        gameMsgService.gameFinished(gameId);
                        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                        PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                        guandanResultMsgService.recordJuResult(juResult);
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
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
            PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
            guandanResultMsgService.recordPanResult(panResult);
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

        hintWatcher(pukeActionResult.getPukeGame().getId(), endFlag);

        long endTime = System.currentTimeMillis();
        logger.info("action:da," + "startTime:" + startTime + "," + "gameId:" + pukeActionResult.getPukeGame().getId()
                + "," + "playerId:" + playerId + "," + "paiIds:" + paiIds + "," + "dianshuZuheIdx:" + dianshuZuheIdx
                + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:"
                + (endTime - startTime) + "ms");
        return vo;
    }

    @RequestMapping(value = "/guo")
    @ResponseBody
    public CommonVO guo(String token) {
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
            logger.info("action:guo," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "success:"
                    + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:"
                    + (endTime - startTime) + "ms");
            return vo;
        }

        PukeActionResult pukeActionResult;
        try {
            pukeActionResult = pukePlayCmdService.guo(playerId, System.currentTimeMillis());
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            long endTime = System.currentTimeMillis();
            logger.info("action:guo," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "success:"
                    + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:"
                    + (endTime - startTime) + "ms");
            return vo;
        }
        pukePlayQueryService.action(pukeActionResult);

        automatic.removeTuoguanPlayerIdSet(playerId);//玩家执行动作后就将玩家在托管列表中移除

        queryScopes.add(QueryScope.panForMe.name());
        queryScopes.add(QueryScope.gameInfo.name());

        // 通知其他人
        for (String otherPlayerId : pukeActionResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                GamePlayerOnlineState onlineState = pukeActionResult.getPukeGame().findPlayerOnlineState(otherPlayerId);
                if (onlineState.equals(GamePlayerOnlineState.online)) {
                    wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeActionResult.getPukeGame().getState(), pukeActionResult.getPukeGame().findPlayerState(otherPlayerId)));
                }
            }
        }

        hintWatcher(pukeActionResult.getPukeGame().getId(), "query");

        long endTime = System.currentTimeMillis();
        logger.info("action:guo," + "startTime:" + startTime + "," + "gameId:" + pukeActionResult.getPukeGame().getId()
                + "," + "playerId:" + playerId + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg() + ","
                + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");
        return vo;
    }

    @RequestMapping(value = "/ready_to_next_pan")
    @ResponseBody
    public CommonVO readytonextpan(String token) {
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
        if (readyToNextPanResult.getPukeGame().getState().name().equals(StartChaodi.name)) {
            queryScopes.add(QueryScope.chaodiInfo);
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
