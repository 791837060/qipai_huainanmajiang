package com.anbang.qipai.biji.cqrs.c.domain;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.biji.cqrs.c.service.GameCmdService;
import com.anbang.qipai.biji.cqrs.c.service.PukePlayCmdService;
import com.anbang.qipai.biji.cqrs.q.dbo.*;
import com.anbang.qipai.biji.cqrs.q.service.PukeGameQueryService;
import com.anbang.qipai.biji.cqrs.q.service.PukePlayQueryService;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalJuResult;
import com.anbang.qipai.biji.msg.msjobj.PukeHistoricalPanResult;
import com.anbang.qipai.biji.msg.service.BijiGameMsgService;
import com.anbang.qipai.biji.msg.service.BijiResultMsgService;
import com.anbang.qipai.biji.remote.service.QipaiDalianmengRemoteService;
import com.anbang.qipai.biji.remote.vo.CommonRemoteVO;
import com.anbang.qipai.biji.websocket.GamePlayWsNotifier;
import com.anbang.qipai.biji.websocket.QueryScope;
import com.dml.mpgame.game.Canceled;
import com.dml.mpgame.game.GamePlayerValueObject;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByTuoguan;
import com.dml.shisanshui.pan.PanActionFrame;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 托管
 */
@Component
public class Automatic {

    @Autowired
    private PukePlayCmdService pukePlayCmdService;

    @Autowired
    private PukePlayQueryService pukePlayQueryService;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private PukeGameQueryService pukeGameQueryService;

    @Autowired
    private BijiResultMsgService bijiResultMsgService;

    @Autowired
    private BijiGameMsgService gameMsgService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    @Autowired
    private QipaiDalianmengRemoteService qipaiDalianmengRemoteService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<String> tuoguanPlayerIdSet = new HashSet<>();

    /**
     * 离线托管
     *
     * @param gameId   游戏ID
     * @param playerId 玩家ID
     */
    public void offlineHosting(String gameId, String playerId) {
        logger.info("玩家离线," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
        PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
        OptionalPlay optionalPlay = pukeGameDbo.getOptionalPlay();
        if (optionalPlay.getTuoguan() == 0 && !optionalPlay.isLixianchengfa()) {
            logger.info("没有托管和离线惩罚," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
            return;//不托管或着没有离线惩罚直接返回
        }
        if (pukeGameDbo.getState().name().equals(WaitingStart.name)) {
            logger.info("游戏没有开始," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
            return;//游戏没有开始直接返回
        }
        PanActionFrame panActionFrame = pukePlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
        long actionTime = 0;//上一动作时间戳
        if (panActionFrame != null) {
            actionTime = panActionFrame.getActionTime();//上一动作时间戳
        }
        long finalActionTime = actionTime;
        if (!tuoguanPlayerIdSet.contains(playerId)) {
            tuoguanPlayerIdSet.add(playerId);
            logger.info("玩家:" + playerId + "进入托管集合," + "GameID:" + gameId);
            executorService.submit(() -> {
                try {
                    int sleepTime = 0;
                    if (optionalPlay.getTuoguan() != 0) {
                        sleepTime = optionalPlay.getTuoguan();
                    } else if (optionalPlay.isLixianchengfa()) {
                        sleepTime = optionalPlay.getLixianshichang();
                    }
                    long tuoguanTime = finalActionTime + (sleepTime * 1000) - System.currentTimeMillis();//上一动作时间戳+进入托管时间-当前时间戳=剩余计时时间
                    if (tuoguanTime > 0) {
                        Thread.sleep(tuoguanTime);
                    }
                    tuoguanPlayerIdSet.remove(playerId);
                    logger.info("玩家:" + playerId + "移除托管集合," + "GameID:" + gameId + ",离线托管计时" + tuoguanTime + "毫秒");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (optionalPlay.getTuoguan() != 0) {   //离线托管
                    Map<String, String> tuoguanPlayerIds = gameCmdService.playLeaveGameHosting(playerId, gameId, false);//gameId传null返回当前托管玩家集合
                    boolean playerDeposit = false;
                    if (tuoguanPlayerIds != null) {
                        playerDeposit = tuoguanPlayerIds.containsKey(playerId);//当前玩家是否已托管
                    }

                    if (!playerDeposit && !isPlayerOnLine(playerId)) { //没有托管&&没有打到下一盘
                        PukeGameDbo pukeGameDbo2 = pukeGameQueryService.findPukeGameDboById(gameId);
                        if (pukeGameDbo2.getState().name().equals(WaitingNextPan.name)) {  //游戏没有开始
                            ReadyToNextPanResult readyToNextPanResult = null;
                            Map<String, String> tuoguanzhunbeiPlayers = gameCmdService.playLeaveGameHosting(playerId, gameId, true);
                            if (pukeGameDbo2.getLianmengId() != null) {
                                PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameId, pukeGameDbo.getPanNo());
                                for (BijiPanPlayerResultDbo bijiPanPlayerResultDbo : panResultDbo.getPlayerResultList()) {
                                    if (tuoguanzhunbeiPlayers.containsKey(bijiPanPlayerResultDbo.getPlayerId())) {
                                        CommonRemoteVO commonRemoteVO = qipaiDalianmengRemoteService.nowPowerForRemote(playerId, pukeGameDbo2.getLianmengId());
                                        if (commonRemoteVO.isSuccess()) {
                                            Map powerdata = new HashMap();
                                            powerdata = (Map) commonRemoteVO.getData();
                                            double powerbalance = (Double) powerdata.get("powerbalance");
                                            if (bijiPanPlayerResultDbo.getPlayerResult().getTotalScore() + powerbalance <= pukeGameDbo.getPowerLimit()) {
                                                PukeGameValueObject gameValueObject = null;
                                                try {
                                                    gameValueObject = gameCmdService.finishGameImmediately(gameId);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                List<QueryScope> queryScopes = new ArrayList<>();
                                                if (gameValueObject != null) {
                                                    pukeGameQueryService.finishGameImmediately(gameValueObject, null);
                                                    gameMsgService.gameFinished(gameId);
                                                    JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
                                                    PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo,
                                                            pukeGameDbo);
                                                    bijiResultMsgService.recordJuResult(juResult);
                                                    gameMsgService.gameFinished(gameId);
                                                    queryScopes.add(QueryScope.juResult);
                                                    for (GamePlayerValueObject player : gameValueObject.getPlayers()) {
                                                        wsNotifier.notifyToQuery(player.getId(), queryScopes);
                                                    }
                                                    logger.info("开始下一局解散," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                                                }
                                                return;
                                            }

                                        }
                                    }
                                }
                            }
                            try {
                                readyToNextPanResult = pukePlayCmdService.autoReadyToNextPan(playerId, tuoguanzhunbeiPlayers.keySet(), gameId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            List<QueryScope> queryScopes = new ArrayList<>();
                            if (readyToNextPanResult != null) {
                                pukePlayQueryService.readyToNextPan(readyToNextPanResult);
                                if (readyToNextPanResult.getPukeGame().getState().name().equals(Playing.name)) {
                                    queryScopes.add(QueryScope.panForMe);
                                }
                            }
                            queryScopes.add(QueryScope.gameInfo);
                            List<ShisanshuiPlayerValueObject> bijiPlayerList = readyToNextPanResult.getFirstActionFrame().getPanAfterAction().getPlayerList();
                            for (ShisanshuiPlayerValueObject bijiPlayerValueObject : bijiPlayerList) {
                                wsNotifier.notifyToQuery(bijiPlayerValueObject.getId(), queryScopes);
                            }
                            logger.info("托管准备," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                            return;
                        }

                        List<PukeGamePlayerDbo> players = pukeGameDbo2.getPlayers();
                        for (PukeGamePlayerDbo pukeGamePlayerDbo : players) {
                            if (pukeGamePlayerDbo.getPlayerId().equals(playerId) && !isPlayerOnLine(playerId)) {
                                gameCmdService.playLeaveGameHosting(playerId, gameId, true);
                                autoChupai(playerId, gameId);
                                logger.info("离线托管," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                            }
                        }

                    }
                } else if (optionalPlay.isLixianchengfa()) {   //离线惩罚
                    PukeGameDbo pukeGameDbo2 = pukeGameQueryService.findPukeGameDboById(gameId);
                    List<PukeGamePlayerDbo> players = pukeGameDbo2.getPlayers();
                    if (!isPlayerOnLine(playerId)) {
                        automaticFinish(gameId, playerId, optionalPlay.getLixianchengfaScore(), players);
                        List<QueryScope> scopes = new ArrayList<>();
                        scopes.add(QueryScope.juResult);
                        for (PukeGamePlayerDbo playerDbo : players) {
                            wsNotifier.notifyToQuery(playerDbo.getPlayerId(), scopes);
                        }
                        logger.info("离线惩罚," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                    }
                }

            });
        }
    }

    /**
     * 离线惩罚结束游戏
     *
     * @param gameId       游戏ID
     * @param lixianPlayer 离线玩家
     * @param chengfaScore 离线惩罚分
     */
    public void automaticFinish(String gameId, String lixianPlayer, double chengfaScore, List<PukeGamePlayerDbo> players) {
        PukeGameValueObject pukeGameValueObject = null;
        try {
            pukeGameValueObject = gameCmdService.automaticVoteToFinish(gameId);
            //加入惩罚分
            BijiJuResult juResult = (BijiJuResult) pukeGameValueObject.getJuResult();
            List<BijiJuPlayerResult> playerResultList = juResult.getPlayerResultList();
            if (playerResultList == null) {
                playerResultList = new ArrayList<>();
            }
            switch (playerResultList.size()) {
                case 0://第一局玩家没有juResult
                    switch (players.size()) {
                        case 2:
                            for (PukeGamePlayerDbo pukeGamePlayerDbo : players) {
                                if (pukeGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(pukeGamePlayerDbo.getPlayerId());
                                } else {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDayingjiaId(pukeGamePlayerDbo.getPlayerId());
                                }
                            }
                            break;
                        case 3:
                            double score2 = chengfaScore / 2;
                            for (PukeGamePlayerDbo pukeGamePlayerDbo : players) {
                                if (pukeGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(pukeGamePlayerDbo.getPlayerId());
                                } else {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(score2);
                                    playerResultList.add(result);
                                    if (juResult.getDayingjiaId() == null) {
                                        juResult.setDayingjiaId(pukeGamePlayerDbo.getPlayerId());
                                    }
                                }
                            }
                            break;
                        case 4:
                            double score3 = chengfaScore / 3;
                            for (PukeGamePlayerDbo pukeGamePlayerDbo : players) {
                                if (pukeGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(pukeGamePlayerDbo.getPlayerId());
                                } else {
                                    BijiJuPlayerResult result = new BijiJuPlayerResult();
                                    result.setPlayerId(pukeGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(score3);
                                    playerResultList.add(result);
                                    if (juResult.getDayingjiaId() == null) {
                                        juResult.setDayingjiaId(pukeGamePlayerDbo.getPlayerId());
                                    }
                                }
                            }
                            break;
                    }
                    ((BijiJuResult) pukeGameValueObject.getJuResult()).setPlayerResultList(playerResultList);
                    break;
                case 2:
                    for (BijiJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + chengfaScore);
                        }
                    }
                    break;
                case 3:
                    double score2 = chengfaScore / 2;
                    for (BijiJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + score2);
                        }
                    }
                    break;
                case 4:
                    double score3 = chengfaScore / 3;
                    for (BijiJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + score3);
                        }
                    }
                    break;
            }
            pukeGameQueryService.voteToFinish(pukeGameValueObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pukeGameValueObject.getState().name().equals(FinishedByTuoguan.name) || pukeGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameId);
            // 记录战绩
            if (juResultDbo != null) {
                PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameId);
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);

                bijiResultMsgService.recordJuResult(juResult);
            }
        }

    }

    /**
     * 自动出牌
     *
     * @param playerId 玩家ID
     */
    public void autoChupai(String playerId, String gameId) {
        Map data = new HashMap();
        List<String> queryScopes = new ArrayList<>();
        data.put("queryScopes", queryScopes);

        PukeActionResult pukeActionResult = null;
        try {
            pukeActionResult = pukePlayCmdService.autoChupai(playerId, System.currentTimeMillis(), gameId);
            pukePlayQueryService.action(pukeActionResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pukeActionResult.getPanResult() == null) {// 盘没结束
            queryScopes.add(QueryScope.gameInfo.name());
            queryScopes.add(QueryScope.panForMe.name());
        } else {// 盘结束了
            String gameID = pukeActionResult.getPukeGame().getId();
            PukeGameDbo pukeGameDbo = pukeGameQueryService.findPukeGameDboById(gameID);
            if (pukeActionResult.getJuResult() != null) {// 局也结束了
                JuResultDbo juResultDbo = pukePlayQueryService.findJuResultDbo(gameID);
                PukeHistoricalJuResult juResult = new PukeHistoricalJuResult(juResultDbo, pukeGameDbo);
                bijiResultMsgService.recordJuResult(juResult);
                gameMsgService.gameFinished(gameID);
                queryScopes.add(QueryScope.juResult.name());
            } else {
                if (pukeGameDbo.getOptionalPlay().isTuoguanjiesan()) {  //托管盘解散
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
                        return;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                } else {
                    queryScopes.add(QueryScope.gameInfo.name());
                    queryScopes.add(QueryScope.panResult.name());
                }
            }
            PanResultDbo panResultDbo = pukePlayQueryService.findPanResultDbo(gameID, pukeActionResult.getPanResult().getPan().getNo());
            PukeHistoricalPanResult panResult = new PukeHistoricalPanResult(panResultDbo, pukeGameDbo);
            bijiResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(pukeActionResult.getPukeGame(), pukeActionResult.getPanActionFrame().getPanAfterAction());
        }
        // 通知其他人
        for (String otherPlayerId : pukeActionResult.getPukeGame().allPlayerIds()) {
            wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(pukeActionResult.getPukeGame().getState(), pukeActionResult.getPukeGame().findPlayerState(otherPlayerId)));
        }

    }

    /**
     * 玩家是否在线   Socket
     *
     * @param playerId 玩家ID
     */
    public boolean isPlayerOnLine(String playerId) {
        return wsNotifier.hasSessionForPlayer(playerId);
    }

}
