package com.anbang.qipai.maanshanmajiang.cqrs.c.service.impl;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangGame;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.ReadyForGameResult;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque.DingqueState;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.GameCmdService;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.fpmpv.back.OnlineGameBackStrategy;
import com.dml.mpgame.game.extend.vote.*;
import com.dml.mpgame.game.join.FixedNumberOfPlayersGameJoinStrategy;
import com.dml.mpgame.game.leave.*;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerFinished;
import com.dml.mpgame.game.ready.FixedNumberOfPlayersGameReadyStrategy;
import com.dml.mpgame.game.watch.WatcherMap;
import com.dml.mpgame.server.GameServer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GameCmdServiceImpl extends CmdServiceBase implements GameCmdService {
    /**
     * 创建房间
     *
     * @param gameId   游戏ID
     * @param playerId 玩家ID
     * @param panshu   盘数
     * @param renshu   人数
     * @return
     */
    @Override
    public MajiangGameValueObject newMajiangGame(String gameId, String playerId, Integer panshu, Integer renshu, Double difen, Integer powerLimit, OptionalPlay optionalPlay) {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame newGame = new MajiangGame();
        newGame.setFixedPlayerCount(renshu);
        newGame.setPanshu(panshu);
        newGame.setRenshu(renshu);
        newGame.setDifen(difen);
        newGame.setOptionalPlay(optionalPlay);
        newGame.setPowerLimit(powerLimit);
        newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());
        newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
        newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));
        newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
        newGame.setLeaveByOfflineStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());
        newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
        newGame.setLeaveByHangupStrategyBeforeStart(new OfflineAndNotReadyGameLeaveStrategy());
        newGame.setLeaveByPlayerStrategyAfterStart(new OfflineGameLeaveStrategy());
        newGame.setLeaveByPlayerStrategyBeforeStart(new OfflineGameLeaveStrategy());
        newGame.setBackStrategy(new OnlineGameBackStrategy());
        newGame.create(gameId, playerId);
        gameServer.playerCreateGame(newGame, playerId);
        return new MajiangGameValueObject(newGame);
    }

//    @Override
//    public MajiangGameValueObject newMajiangGameLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu,Double difen, Integer powerLimit,
//                                                             OptionalPlay optionalPlay) {
//        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
//        MajiangGame newGame = new MajiangGame();
//        newGame.setPanshu(panshu);
//        newGame.setRenshu(renshu);
//        newGame.setFixedPlayerCount(renshu);
//
//        newGame.setOptionalPlay(optionalPlay);
//        newGame.setDifen(difen);
//        newGame.setPowerLimit(powerLimit);
//        newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());
//        newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
//        newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));
//        newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
//        newGame.setLeaveByOfflineStrategyBeforeStart(new PlayerGameLeaveStrategy());
//        newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
//        newGame.setLeaveByHangupStrategyBeforeStart(new PlayerGameLeaveStrategy());
//        newGame.setLeaveByPlayerStrategyAfterStart(new PlayerLeaveCancelGameGameLeaveStrategy());
//        newGame.setLeaveByPlayerStrategyBeforeStart(new PlayerGameLeaveStrategy());
//        newGame.setBackStrategy(new OnlineGameBackStrategy());
//        newGame.create(gameId, playerId);
//        gameServer.playerCreateGame(newGame, playerId);
//        return new MajiangGameValueObject(newGame);
//    }

//    @Override
//    public MajiangGameValueObject newMajiangGamePlayerLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu, Double difen, Integer powerLimit, OptionalPlay optionalPlay, MajiangGameQueryService gameQueryService, TianchangxiaohuaGameMsgService gameMsgService, GamePlayWsNotifier wsNotifier) {
//        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
//        MajiangGame newGame = new MajiangGame();
//        newGame.setPanshu(panshu);
//        newGame.setRenshu(renshu);
//        newGame.setFixedPlayerCount(renshu);
//        newGame.setOptionalPlay(optionalPlay);
//        newGame.setDifen(difen);
//        newGame.setPowerLimit(powerLimit);
//        newGame.setVotePlayersFilter(new OnlineVotePlayersFilter());
//        newGame.setJoinStrategy(new FixedNumberOfPlayersGameJoinStrategy(renshu));
//        newGame.setReadyStrategy(new FixedNumberOfPlayersGameReadyStrategy(renshu));
//        newGame.setLeaveByOfflineStrategyAfterStart(new OfflineGameLeaveStrategy());
//        newGame.setLeaveByOfflineStrategyBeforeStart(new TimeoverQuitGameLeaveStrategy(300000, gameQueryService, gameMsgService, wsNotifier));
//        newGame.setLeaveByHangupStrategyAfterStart(new OfflineGameLeaveStrategy());
//        newGame.setLeaveByHangupStrategyBeforeStart(new TimeoverQuitGameLeaveStrategy(300000, gameQueryService, gameMsgService, wsNotifier));
//        newGame.setLeaveByPlayerStrategyAfterStart(new OfflineGameLeaveStrategy());
//        newGame.setLeaveByPlayerStrategyBeforeStart(new NoPlayerCancelGameGameLeaveStrategy());
//        newGame.setBackStrategy(new OnlineGameBackStrategy());
//        newGame.create(gameId, playerId);
//        gameServer.playerCreateGame(newGame, playerId);
//        return new MajiangGameValueObject(newGame);
//    }

    @Override
    public MajiangGameValueObject joinGame(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        return gameServer.join(playerId, gameId);
    }

    @Override
    public MajiangGameValueObject leaveGame(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        MajiangGameValueObject majiangGameValueObject = gameServer.leaveByPlayer(playerId);
        if (game.getState().name().equals(FinishedByVote.name) || game.getState().name().equals(Finished.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return majiangGameValueObject;
    }

    @Override
    public MajiangGameValueObject leaveGameByOffline(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        MajiangGameValueObject majiangGameValueObject = gameServer.leaveByOffline(playerId);
        if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return majiangGameValueObject;
    }

    @Override
    public MajiangGameValueObject leaveGameByHangup(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        MajiangGameValueObject majiangGameValueObject = gameServer.leaveByHangup(playerId);
        if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return majiangGameValueObject;
    }

    @Override
    public MajiangGameValueObject backToGame(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        return gameServer.back(playerId, gameId);
    }

    /**
     * 准备开始游戏
     *
     * @param playerId    玩家ID
     * @param currentTime 时间戳
     * @return
     * @throws Exception
     */
    @Override
    public ReadyForGameResult readyForGame(String playerId, Long currentTime) throws Exception {
        ReadyForGameResult result = new ReadyForGameResult();
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGameValueObject majiangGameValueObject = gameServer.ready(playerId, currentTime);
        result.setMajiangGame(majiangGameValueObject);
        if (majiangGameValueObject.getState().name().equals(Playing.name) || majiangGameValueObject.getState().name().equals(DingqueState.name)) {
            MajiangGame majiangGame = (MajiangGame) gameServer.findGamePlayerPlaying(playerId);
            PanActionFrame firstActionFrame = majiangGame.createJuAndStartFirstPan(currentTime);
            result.setFirstActionFrame(firstActionFrame);
        }
        return result;
    }

    @Override
    public MajiangGameValueObject finish(String playerId, Long currentTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGamePlayerPlaying(playerId);
        if (majiangGame.getState().name().equals(WaitingStart.name)) {
            // 是主机的话直接解散，不是的话自己走人
            return new MajiangGameValueObject(majiangGame);
        } else {
            if (majiangGame.getOptionalPlay().isBanJiesan()) {
                return new MajiangGameValueObject(majiangGame);
            }
            majiangGame.launchVoteToFinish(playerId, new MostPlayersWinVoteCalculator(), currentTime, 99000);
            majiangGame.voteToFinish(playerId, VoteOption.yes);
        }

        if (majiangGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(majiangGame.getId());
        }
        return new MajiangGameValueObject(majiangGame);
    }

    @Override
    public MajiangGameValueObject quit(String playerId, Long currentTime, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        if (gameServer.getPlayerIdGameIdMap().get(playerId) != null) {
            MajiangGame majiangGame = (MajiangGame) gameServer.findGamePlayerPlaying(playerId);
            if (majiangGame.getState().name().equals(WaitingStart.name)) {
                gameServer.quit(playerId);
                if (majiangGame.getIdPlayerMap().size() == 0) {
                    gameServer.finishGame(majiangGame.getId());
                }
            }
            return new MajiangGameValueObject(majiangGame);
        } else {
            MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
            majiangGame.quit(playerId);
            if (majiangGame.getState().name().equals(WaitingStart.name)) {
                if (majiangGame.getIdPlayerMap().size() == 0) {
                    gameServer.finishGame(majiangGame.getId());
                }
            }
            return new MajiangGameValueObject(majiangGame);
        }

    }

    @Override
    public MajiangGameValueObject voteToFinish(String playerId, Boolean yes) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGamePlayerPlaying(playerId);
        if (yes) {
            majiangGame.voteToFinish(playerId, VoteOption.yes);
        } else {
            majiangGame.voteToFinish(playerId, VoteOption.no);
        }

        if (majiangGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(majiangGame.getId());
        }
        return new MajiangGameValueObject(majiangGame);
    }

    @Override
    public void bindPlayer(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        gameServer.bindPlayer(playerId, gameId);
    }

    @Override
    public MajiangGameValueObject finishGameImmediately(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        majiangGame.finish();
        majiangGame.setState(new Finished());
        majiangGame.updateAllPlayersState(new PlayerFinished());
        gameServer.finishGame(gameId);
        return new MajiangGameValueObject(majiangGame);
    }

    @Override
    public MajiangGameValueObject voteToFinishByTimeOver(String playerId, Long currentTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGamePlayerPlaying(playerId);
        majiangGame.voteToFinishByTimeOver(currentTime);

        if (majiangGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(majiangGame.getId());
        }
        return new MajiangGameValueObject(majiangGame);
    }

    @Override
    public ReadyForGameResult cancelReadyForGame(String playerId, Long currentTime) throws Exception {
        ReadyForGameResult result = new ReadyForGameResult();
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGameValueObject majiangGameValueObject = gameServer.cancelReady(playerId, currentTime);
        result.setMajiangGame(majiangGameValueObject);

        return result;
    }

    @Override
    public MajiangGameValueObject joinWatch(String playerId, String nickName, String headimgurl, String gameId)
            throws Exception {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        watcherMap.join(playerId, nickName, headimgurl, gameId);
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGameValueObject majiangGameValueObject = gameServer.getInfo(playerId, gameId);
        return majiangGameValueObject;
    }

    @Override
    public MajiangGameValueObject leaveWatch(String playerId, String gameId) throws Exception {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        watcherMap.leave(playerId, gameId);
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGameValueObject majiangGameValueObject = gameServer.getInfo(playerId, gameId);
        return majiangGameValueObject;
    }

    @Override
    public Map getwatch(String gameId) {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        return watcherMap.getWatch(gameId);
    }

    @Override
    public void recycleWatch(String gameId) {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        watcherMap.recycleWatch(gameId);
    }

    @Override
    public Set<String> listGameId() {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        if (!CollectionUtils.isEmpty(gameServer.getGameIdGameMap())) {
            Set<String> stringSet = gameServer.getGameIdGameMap().keySet();
            Set<String> gameIdSet = new HashSet<>();
            for (String gameId : stringSet) {
                gameIdSet.add(gameId);
            }
            return gameIdSet;
        }
        return new HashSet<>();
    }

    @Override
    public Map<String, String> playLeaveGameHosting(String playerId, String gameId, boolean isLeave) {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameID;
        if (gameId == null) {
            gameID = gameServer.findBindGameId(playerId);
        } else {
            gameID = gameId;
        }
        if (gameID == null) return null;
        MajiangGame majiangGame = null;
        try {
            majiangGame = (MajiangGame) gameServer.findGame(gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> depositPlayerList = null;
        if (majiangGame != null) {
            Ju ju = majiangGame.getJu();
            if (ju != null) {
                depositPlayerList = ju.getDepositPlayerList();
                if (gameId == null) {
                    return depositPlayerList;
                }

                if (!isLeave) {
                    depositPlayerList.remove(playerId);
                } else {
                    depositPlayerList.put(playerId, gameID);
                }
                if (playerId != null && ju.getCurrentPan() != null) {
                    MajiangPlayer player = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap().get(playerId);
                    player.setTuoguanStatus(isLeave);
                }
            }
        }
        return depositPlayerList;
    }

    @Override
    public MajiangGameValueObject automaticFinish(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        majiangGame.automaticToFinish();
        if (majiangGame.getState().name().equals(FinishedByTuoguan.name)) {
            gameServer.finishGame(majiangGame.getId());
        }
        return new MajiangGameValueObject(majiangGame);
    }

    @Override
    public List<PanActionFrame> getPanActionFrame(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        Ju ju = majiangGame.getJu();
        if (ju != null) {
            Pan currentPan = ju.getCurrentPan();
            if (currentPan != null) {
                return currentPan.getActionFrameList();
            }
        }
        return null;
    }

    @Override
    public String getGameIdByPlayerId(String playerId) {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Map<String, Game> gameIdGameMap = gameServer.getGameIdGameMap();
        for (Game game : gameIdGameMap.values()) {
            MajiangGame majiangGame = (MajiangGame) game;
            Map<String, GamePlayer> idPlayerMap = majiangGame.getIdPlayerMap();
            if (idPlayerMap.containsKey(playerId)) {
                return majiangGame.getId();
            }
        }
        return null;
    }

}
