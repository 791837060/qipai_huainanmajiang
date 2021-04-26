package com.anbang.qipai.paodekuai.web.controller;

import com.anbang.qipai.paodekuai.cqrs.c.domain.Automatic;
import com.anbang.qipai.paodekuai.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.paodekuai.cqrs.c.service.GameCmdService;
import com.anbang.qipai.paodekuai.cqrs.c.service.PlayerAuthService;
import com.anbang.qipai.paodekuai.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.paodekuai.cqrs.q.dbo.*;
import com.anbang.qipai.paodekuai.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.paodekuai.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.paodekuai.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.paodekuai.msg.msjobj.PukeHistoricalPanResult;
import com.anbang.qipai.paodekuai.msg.service.PaodekuaiGameMsgService;
import com.anbang.qipai.paodekuai.msg.service.PaodekuaiResultMsgService;
import com.anbang.qipai.paodekuai.remote.service.QipaiDalianmengRemoteService;
import com.anbang.qipai.paodekuai.remote.vo.CommonRemoteVO;
import com.anbang.qipai.paodekuai.utils.CommonVoUtil;
import com.anbang.qipai.paodekuai.utils.SpringUtil;
import com.anbang.qipai.paodekuai.web.vo.*;
import com.anbang.qipai.paodekuai.websocket.GamePlayWsNotifier;
import com.anbang.qipai.paodekuai.websocket.QueryScope;
import com.anbang.qipai.paodekuai.websocket.WatchQueryScope;
import com.dml.mpgame.game.Playing;
import com.dml.paodekuai.exception.BiyaException;
import com.dml.paodekuai.pan.PanActionFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    private PaodekuaiResultMsgService paodekuaiResultMsgService;

    @Autowired
    private PaodekuaiGameMsgService gameMsgService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private QipaiDalianmengRemoteService qipaiDalianmengRemoteService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);

    /**
     * 当前盘我应该看到的所有信息
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
                paodekuaiResultMsgService.recordJuResult(juResult);

                gameMsgService.gameFinished(gameId);
                queryScopes.add(QueryScope.juResult.name());
                endFlag = WatchQueryScope.watchEnd.name();
            } else {
                Map<String, String> playerIdGameIdMap = gameCmdService.playLeaveGameHosting(playerId, null, false);
                int tuoguanCount = 0;
                if (playerIdGameIdMap != null) {
                    Set<String> playerIds = playerIdGameIdMap.keySet();//托管玩家集合
                    tuoguanCount = playerIds.size();
                }
                if (pukeGameDbo.getOptionalPlay().isTuoguanjiesan() && tuoguanCount != 0) {
                    try {
                        PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
                        PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
                        paodekuaiResultMsgService.recordPanResult(panResult);
                        gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
                        PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                        pukeGameQueryService.finishGameImmediately(gameValueObject, panResultDbo);
                        gameMsgService.gameFinished(gameId);
                        JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                        PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                        paodekuaiResultMsgService.recordJuResult(juResult);
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
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeActionResult.getPanResult().getPan().getNo());
            PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
            paodekuaiResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
        }
        // 通知其他人
        for (String otherPlayerId : pukeActionResult.getPukeGame().allPlayerIds()) {
            if (!otherPlayerId.equals(playerId)) {
                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeActionResult.getPukeGame().getState(), pukeActionResult.getPukeGame().findPlayerState(otherPlayerId)));
            }
        }

        // 通知观战者
        hintWatcher(pukeActionResult.getPukeGame().getId(), endFlag);

        long endTime = System.currentTimeMillis();
        logger.info("action:da," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "paiIds:" + paiIds
                + "," + "dianshuZuheIdx:" + dianshuZuheIdx + "," + "success:" + vo.isSuccess() + ",msg:" + vo.getMsg()
                + "," + "endTime:" + endTime + "," + "use:" + (endTime - startTime) + "ms");


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
        } catch (BiyaException e) {
            long endTime = System.currentTimeMillis();
            logger.info("action:guo," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "success:"
                    + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:"
                    + (endTime - startTime) + "ms");
            return CommonVoUtil.error(e.getClass().getName());
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
                wsNotifier.notifyToQuery(otherPlayerId,
                        QueryScope.scopesForState(pukeActionResult.getPukeGame().getState(),
                                pukeActionResult.getPukeGame().findPlayerState(otherPlayerId)));
            }
        }

        // 通知观战者
        hintWatcher(pukeActionResult.getPukeGame().getId(), "query");

        long endTime = System.currentTimeMillis();
        logger.info("action:guo," + "startTime:" + startTime + "," + "playerId:" + playerId + "," + "success:"
                + vo.isSuccess() + ",msg:" + vo.getMsg() + "," + "endTime:" + endTime + "," + "use:"
                + (endTime - startTime) + "ms");
        return vo;
    }

    @RequestMapping(value = "/ready_to_next_pan")
    @ResponseBody
    public CommonVO readytonextpan(String token, @RequestParam(required = false) String lianmengId, @RequestParam(required = false) String gameId) {
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

        if (lianmengId != null) {
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeGameDbo.getPanNo());
            CommonRemoteVO commonRemoteVO = qipaiDalianmengRemoteService.nowPowerForRemote(playerId, lianmengId);
            if (commonRemoteVO.isSuccess()) {
                Map powerdata = new HashMap();
                powerdata = (Map) commonRemoteVO.getData();
                double powerbalance = (Double) powerdata.get("powerbalance");
                for (PaodekuaiPanPlayerResultDbo ruianMajiangPanPlayerResultDbo : panResultDbo.getPlayerResultList()) {
                    if (ruianMajiangPanPlayerResultDbo.getPlayerId().equals(playerId)) {
                        if (pukeGameDbo.getOptionalPlay().isJinyuanzi()) {
                            powerbalance -= pukeGameDbo.getOptionalPlay().getYuanzifen();
                        }
                        if (ruianMajiangPanPlayerResultDbo.getPlayerResult().getTotalScore() + powerbalance <= pukeGameDbo.getPowerLimit()) {
                            try {
                                PukeGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                                pukeGameQueryService.finishGameImmediately(gameValueObject, null);
                                gameMsgService.gameFinished(gameId);
                                JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                                paodekuaiResultMsgService.recordJuResult(juResult);
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

            try {
                pukePlayQueryService.readyToNextPan(readyToNextPanResult);
            } catch (Throwable e) {
                vo.setSuccess(false);
                vo.setMsg(e.getMessage());
                return vo;
            }

            // 通知其他人
            for (String otherPlayerId : readyToNextPanResult.getPukeGame().allPlayerIds()) {
                if (!otherPlayerId.equals(playerId)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(readyToNextPanResult.getPukeGame().getState(), readyToNextPanResult.getPukeGame().findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }

            List<QueryScope> queryScopes = new ArrayList<>();
            queryScopes.add(QueryScope.gameInfo);
            if (readyToNextPanResult.getPukeGame().getState().name().equals(Playing.name)) {
                queryScopes.add(QueryScope.panForMe);
            }
            data.put("queryScopes", queryScopes);
        } else {
            ReadyToNextPanResult readyToNextPanResult;
            try {
                readyToNextPanResult = pukePlayCmdService.readyToNextPan(playerId, playerIds);
            } catch (Exception e) {
                vo.setSuccess(false);
                vo.setMsg(e.getClass().getName());
                return vo;
            }

            try {
                pukePlayQueryService.readyToNextPan(readyToNextPanResult);
            } catch (Throwable e) {
                vo.setSuccess(false);
                vo.setMsg(e.getMessage());
                return vo;
            }

            // 通知其他人
            for (String otherPlayerId : readyToNextPanResult.getPukeGame().allPlayerIds()) {
                if (!otherPlayerId.equals(playerId)) {
                    List<QueryScope> scopes = QueryScope.scopesForState(readyToNextPanResult.getPukeGame().getState(), readyToNextPanResult.getPukeGame().findPlayerState(otherPlayerId));
                    scopes.remove(QueryScope.panResult);
                    wsNotifier.notifyToQuery(otherPlayerId, scopes);
                }
            }

            List<QueryScope> queryScopes = new ArrayList<>();
            queryScopes.add(QueryScope.gameInfo);
            if (readyToNextPanResult.getPukeGame().getState().name().equals(Playing.name)) {
                queryScopes.add(QueryScope.panForMe);
            }
            data.put("queryScopes", queryScopes);
        }
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
