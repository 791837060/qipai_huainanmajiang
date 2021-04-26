package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.cqrs.c.service.GameCmdService;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.MajiangPlayCmdService;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.impl.CmdServiceBase;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.service.MajiangGameQueryService;
import com.anbang.qipai.maanshanmajiang.cqrs.q.service.MajiangPlayQueryService;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.MajiangHistoricalJuResult;
import com.anbang.qipai.maanshanmajiang.msg.msjobj.MajiangHistoricalPanResult;
import com.anbang.qipai.maanshanmajiang.msg.service.MaanshanMajiangGameMsgService;
import com.anbang.qipai.maanshanmajiang.msg.service.MaanshanMajiangResultMsgService;
import com.anbang.qipai.maanshanmajiang.websocket.GamePlayWsNotifier;
import com.anbang.qipai.maanshanmajiang.websocket.QueryScope;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.guo.MajiangGuoAction;
import com.dml.majiang.player.action.peng.MajiangPengAction;
import com.dml.majiang.player.action.ting.MajiangTingAction;
import com.dml.majiang.player.valueobj.MajiangPlayerValueObject;
import com.dml.mpgame.game.Canceled;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByTuoguan;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
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
public class Automatic extends CmdServiceBase {

    @Autowired
    private MajiangPlayQueryService majiangPlayQueryService;

    @Autowired
    private MajiangGameQueryService majiangGameQueryService;

    @Autowired
    private MaanshanMajiangResultMsgService maanshanMajiangResultMsgService;

    @Autowired
    private MaanshanMajiangGameMsgService gameMsgService;

    @Autowired
    private MajiangPlayCmdService majiangPlayCmdService;

    @Autowired
    private GameCmdService gameCmdService;

    @Autowired
    private GamePlayWsNotifier wsNotifier;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<String> tuoguanPlayerIdSet = new HashSet<>();

    /**
     * 自动出牌
     *
     * @param playerId 玩家ID
     * @param id       动作ID
     * @param gameId   游戏ID
     */
    public void automaticAction(String playerId, Integer id, String gameId) {
        List<QueryScope> queryScopes = new ArrayList<>();
        MajiangActionResult majiangActionResult;
        try {
            majiangActionResult = majiangPlayCmdService.automaticAction(playerId, id, System.currentTimeMillis(), gameId);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        majiangPlayQueryService.action(majiangActionResult);
        if (majiangActionResult.getPanResult() == null) {// 盘没结束
            queryScopes.add(QueryScope.gameInfo);
            queryScopes.add(QueryScope.panForMe);
        } else { //盘结束了
            gameId = majiangActionResult.getMajiangGame().getId();
            MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
            if (majiangActionResult.getJuResult() != null) { //局也结束了
                JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
                MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                maanshanMajiangResultMsgService.recordJuResult(juResult);
                gameMsgService.gameFinished(gameId);
                queryScopes.add(QueryScope.juResult);
            } else {
                int tuoguanCount = 0;
                Map<String, String> playerIdGameIdMap = gameCmdService.playLeaveGameHosting(playerId, gameId, true);
                if (playerIdGameIdMap != null) {
                    Set<String> playerIds = playerIdGameIdMap.keySet();//托管玩家集合
                    tuoguanCount = playerIds.size();
                }
                if (majiangGameDbo.getOptionalPlay().isTuoguanjiesan() && tuoguanCount != 0) {
                    try {
                        PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, majiangActionResult.getPanResult().getPan().getNo());
                        MajiangHistoricalPanResult panResult = new MajiangHistoricalPanResult(panResultDbo, majiangGameDbo);
                        maanshanMajiangResultMsgService.recordPanResult(panResult);
                        gameMsgService.panFinished(majiangActionResult.getMajiangGame(), majiangActionResult.getPanActionFrame().getPanAfterAction());
                        MajiangGameValueObject gameValueObject = gameCmdService.finishGameImmediately(gameId);
                        majiangGameQueryService.finishGameImmediately(gameValueObject, panResultDbo);
                        JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
                        MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                        maanshanMajiangResultMsgService.recordJuResult(juResult);
                        gameMsgService.gameFinished(gameId);
                        queryScopes.add(QueryScope.juResult);
                        for (String otherPlayerId : gameValueObject.allPlayerIds()) {
                            if (!otherPlayerId.equals(playerId)) {
                                wsNotifier.notifyToQuery(otherPlayerId, QueryScope.scopesForState(gameValueObject.getState(), gameValueObject.findPlayerState(otherPlayerId)));
                            }
                        }
                        logger.info("tuoguanjiesan," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                        return;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                        return;
                    }
                } else {
                    queryScopes.add(QueryScope.gameInfo);
                    queryScopes.add(QueryScope.panResult);
                }
            }
            PanResultDbo panResultDbo = majiangPlayQueryService.findPanResultDbo(gameId, majiangActionResult.getPanResult().getPan().getNo());
            MajiangHistoricalPanResult panResult = new MajiangHistoricalPanResult(panResultDbo, majiangGameDbo);
            maanshanMajiangResultMsgService.recordPanResult(panResult);
            gameMsgService.panFinished(majiangActionResult.getMajiangGame(), majiangActionResult.getPanActionFrame().getPanAfterAction());
        }
        List<MajiangPlayerValueObject> playerList = majiangActionResult.getPanActionFrame().getPanAfterAction().getPlayerList();
        List<String> playerIds = new ArrayList<>();
        for (MajiangPlayerValueObject valueObject : playerList) {
            playerIds.add(valueObject.getId());
        }

        for (String player : playerIds) {
            wsNotifier.notifyToQuery(player, queryScopes);
        }

    }

    /**
     * 离线托管
     *
     * @param gameId   游戏ID
     * @param playerId 玩家ID
     */
    public void offlineHosting(String gameId, String playerId) {
        logger.info("玩家离线," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
        MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
        OptionalPlay optionalPlay = majiangGameDbo.getOptionalPlay();
        if (optionalPlay.getTuoguan() == 0 && !optionalPlay.isLixianchengfa()) {
            return;//不托管或着没有离线惩罚直接返回 不再启动线程
        }
        if (majiangGameDbo.getState().name().equals(WaitingStart.name)) {
            logger.info("游戏没有开始," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
            return;//游戏没有开始直接返回
        }
        PanActionFrame panActionFrame = majiangPlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
        long actionTime = 0;//上一动作时间戳
        if (panActionFrame != null) {
            if (panActionFrame.getPanAfterAction().getPublicWaitingPlayerId().equals(playerId)) {
                actionTime = panActionFrame.getActionTime();
            } else {
                actionTime = System.currentTimeMillis();
            }
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
                    Map<String, String> tuoguanPlayerIds = gameCmdService.playLeaveGameHosting(playerId, null, false);//gameId传null返回当前托管玩家集合
                    boolean playerDeposit = false;
                    if (tuoguanPlayerIds != null) {
                        playerDeposit = tuoguanPlayerIds.containsKey(playerId);//当前玩家是否已托管
                    }

                    List<PanActionFrame> panActionFrameList = null;
                    try {
                        panActionFrameList = gameCmdService.getPanActionFrame(gameId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    boolean playerAction = false;
                    if (panActionFrameList != null) {
                        for (PanActionFrame actionFrame : panActionFrameList) {
                            if (actionFrame.getActionTime() > finalActionTime) {
                                if (actionFrame.getAction().getActionPlayerId().equals(playerId)) {
                                    playerAction = true;//玩家在离线后没有动作
                                }
                            }
                        }
                    }
                    boolean playerOnLine = isPlayerOnLine(playerId);
                    logger.info("玩家状态," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId
                            + ",playerAction:" + playerAction + ",playerDeposit:" + playerDeposit + ",isPlayerOnLine:" + playerOnLine);
                    if (!playerAction && !playerDeposit && !playerOnLine) {   //没有出过牌&&没有托管&&没有打到下一盘

                        MajiangGameDbo majiangGameDbo2 = majiangGameQueryService.findMajiangGameDboById(gameId);
                        PanActionFrame panActionFrame2 = majiangPlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
                        if (majiangGameDbo2.getState().name().equals(WaitingNextPan.name)) {  //游戏没有开始 托管玩家自动准备
                            ReadyToNextPanResult readyToNextPanResult = null;
                            Map<String, String> tuoguanzhunbeiPlayers = gameCmdService.playLeaveGameHosting(playerId, gameId, true);//把托管玩家放入托管列表
                            try {
                                readyToNextPanResult = majiangPlayCmdService.autoReadyToNextPan(playerId, tuoguanzhunbeiPlayers.keySet(), gameId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            List<QueryScope> queryScopes = new ArrayList<>();
                            if (readyToNextPanResult != null) {
                                majiangPlayQueryService.readyToNextPan(readyToNextPanResult);
                                if (readyToNextPanResult.getMajiangGame().getState().name().equals(Playing.name)) {
                                    queryScopes.add(QueryScope.panForMe);
                                }
                            }
                            queryScopes.add(QueryScope.gameInfo);
                            for (MajiangPlayerValueObject majiangPlayerValueObject : panActionFrame2.getPanAfterAction().getPlayerList()) {
                                wsNotifier.notifyToQuery(majiangPlayerValueObject.getId(), queryScopes);
                            }
                            logger.info("托管准备," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                            return;
                        }
                        gameCmdService.playLeaveGameHosting(playerId, gameId, true);

                        if (majiangGameDbo2.getState().name().equals("DingqueState")) {
                            automaticDingque(playerId, gameId);
                            logger.info("离线托管定缺," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
//                            PanActionFrame dingquePanActionFrame = majiangPlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
//                            if (dingquePanActionFrame.getPanAfterAction().getPublicWaitingPlayerId().equals(playerId)) {
//                                automaticAction(playerId, 1, gameId);
//                            }
                            return;
                        }

                        automaticAction(playerId, 1, gameId);
                        PanActionFrame panActionFrame3 = majiangPlayQueryService.findAndFilterCurrentPanValueObjectForPlayer(gameId);
                        if (panActionFrame3 != null) {
                            MajiangPlayerAction action = panActionFrame3.getAction();
                            if (action instanceof MajiangPengAction ||
                                    action instanceof MajiangGangAction ||
                                    action instanceof MajiangGuoAction ||
                                    action instanceof MajiangTingAction) {
                                automaticAction(playerId, 1, gameId);
                            }
                        }
                        logger.info("【离线托管," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId + "】");
                    }
                } else if (optionalPlay.isLixianchengfa()) {   //离线惩罚
                    MajiangGameDbo majiangGameDbo2 = majiangGameQueryService.findMajiangGameDboById(gameId);
                    List<MajiangGamePlayerDbo> players = majiangGameDbo2.getPlayers();
                    for (MajiangGamePlayerDbo majiangGamePlayerDbo : players) {
                        if (majiangGamePlayerDbo.getPlayerId().equals(playerId) && majiangGamePlayerDbo.getOnlineState() == GamePlayerOnlineState.offline) {
                            automaticFinish(gameId, playerId, optionalPlay.getLixianchengfaScore(), players);
                            List<QueryScope> scopes = new ArrayList<>();
                            scopes.add(QueryScope.juResult);
                            for (MajiangGamePlayerDbo playerDbo : players) {
                                wsNotifier.notifyToQuery(playerDbo.getPlayerId(), scopes);
                            }
                            logger.info("离线惩罚," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId + ",gameId:" + gameId);
                        }
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
     * @param chengfaScore 惩罚分数
     */
    public void automaticFinish(String gameId, String lixianPlayer, double chengfaScore, List<MajiangGamePlayerDbo> players) {
        MajiangGameValueObject majiangGameValueObject = null;
        try {
            majiangGameValueObject = gameCmdService.automaticFinish(gameId);
            //加入惩罚分
            MaanshanMajiangJuResult juResult = (MaanshanMajiangJuResult) majiangGameValueObject.getJuResult();
            List<MaanshanMajiangJuPlayerResult> playerResultList = juResult.getPlayerResultList();
            if (playerResultList == null) {
                playerResultList = new ArrayList<>();
                juResult.setPlayerResultList(playerResultList);
            }
            switch (playerResultList.size()) {
                case 0://第一局玩家没有juResult 所以集合内没有玩家
                    switch (players.size()) {
                        case 2:
                            for (MajiangGamePlayerDbo majiangGamePlayerDbo : players) {
                                if (majiangGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(majiangGamePlayerDbo.getPlayerId());
                                } else {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDayingjiaId(majiangGamePlayerDbo.getPlayerId());
                                }
                            }
                            break;
                        case 3:
                            double score2 = chengfaScore / 2;
                            for (MajiangGamePlayerDbo majiangGamePlayerDbo : players) {
                                if (majiangGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(majiangGamePlayerDbo.getPlayerId());
                                } else {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(score2);
                                    playerResultList.add(result);
                                    if (juResult.getDayingjiaId() == null) {
                                        juResult.setDayingjiaId(majiangGamePlayerDbo.getPlayerId());
                                    }
                                }
                            }
                            break;
                        case 4:
                            double score3 = chengfaScore / 3;
                            for (MajiangGamePlayerDbo majiangGamePlayerDbo : players) {
                                if (majiangGamePlayerDbo.getPlayerId().equals(lixianPlayer)) {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(-chengfaScore);
                                    playerResultList.add(result);
                                    juResult.setDatuhaoId(majiangGamePlayerDbo.getPlayerId());
                                } else {
                                    MaanshanMajiangJuPlayerResult result = new MaanshanMajiangJuPlayerResult();
                                    result.setPlayerId(majiangGamePlayerDbo.getPlayerId());
                                    result.setTotalScore(score3);
                                    playerResultList.add(result);
                                    if (juResult.getDayingjiaId() == null) {
                                        juResult.setDayingjiaId(majiangGamePlayerDbo.getPlayerId());
                                    }
                                }
                            }
                            break;
                    }
                    break;
                case 2:
                    for (MaanshanMajiangJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + chengfaScore);
                        }
                    }
                    break;
                case 3:
                    double score2 = chengfaScore / 2;
                    for (MaanshanMajiangJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + score2);
                        }
                    }
                    break;
                case 4:
                    double score3 = chengfaScore / 3;
                    for (MaanshanMajiangJuPlayerResult juPlayerResult : playerResultList) {
                        if (juPlayerResult.getPlayerId().equals(lixianPlayer)) {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() - chengfaScore);
                        } else {
                            juPlayerResult.setTotalScore(juPlayerResult.getTotalScore() + score3);
                        }
                    }
                    break;
            }

            majiangGameQueryService.voteToFinish(majiangGameValueObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (majiangGameValueObject.getState().name().equals(FinishedByTuoguan.name) || majiangGameValueObject.getState().name().equals(Canceled.name)) {
            gameMsgService.gameFinished(gameId);
            JuResultDbo juResultDbo = majiangPlayQueryService.findJuResultDbo(gameId);
            // 记录战绩
            if (juResultDbo != null) {
                MajiangGameDbo majiangGameDbo = majiangGameQueryService.findMajiangGameDboById(gameId);
                MajiangHistoricalJuResult juResult = new MajiangHistoricalJuResult(juResultDbo, majiangGameDbo);
                maanshanMajiangResultMsgService.recordJuResult(juResult);
            }
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

    /**
     * 从托管集合中移除
     *
     * @param playerId 玩家ID
     */
    public void removeTuoguanPlayerIdSet(String playerId) {
        boolean remove = tuoguanPlayerIdSet.remove(playerId);
        if (remove) {
            logger.info("将玩家移除托管集合," + "Time:" + System.currentTimeMillis() + ",playerId:" + playerId);
        }
    }

    /**
     * 托管定缺
     */
    public void automaticDingque(String playerId, String gameId) {
        MajiangActionResult majiangActionResult = null;
        try {
            majiangActionResult = majiangPlayCmdService.automaticDingque(playerId, gameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        majiangPlayQueryService.action(majiangActionResult);
        for (String otherPlayerId : majiangActionResult.getMajiangGame().allPlayerIds()) {
            wsNotifier.notifyToQuery(otherPlayerId,
                    QueryScope.scopesForState(majiangActionResult.getMajiangGame().getState(), majiangActionResult.getMajiangGame().findPlayerState(otherPlayerId)));
        }

    }

}
