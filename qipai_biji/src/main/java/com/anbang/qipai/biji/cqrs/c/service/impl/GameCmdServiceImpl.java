package com.anbang.qipai.biji.cqrs.c.service.impl;

import com.anbang.qipai.biji.cqrs.c.domain.*;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.biji.cqrs.c.service.GameCmdService;
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
import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.pan.PanActionFrame;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GameCmdServiceImpl extends CmdServiceBase implements GameCmdService {

    @Override
    public PukeGameValueObject newPukeGame(String gameId, String playerId, Integer panshu, Integer renshu, Double difen, OptionalPlay optionalPlay, Integer powerLimit) {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame newGame = new PukeGame();
        newGame.setPanshu(panshu);
        newGame.setRenshu(renshu);
        newGame.setFixedPlayerCount(renshu);
        newGame.setDifen(difen);
        newGame.setPowerLimit(powerLimit);
        newGame.setOptionalPlay(optionalPlay);
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
        newGame.updatePlayerPosition(playerId);
        gameServer.playerCreateGame(newGame, playerId);
        return new PukeGameValueObject(newGame);
    }

//    @Override
//    public PukeGameValueObject newPukeGameLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu, Double difen, OptionalPlay optionalPlay, Integer powerLimit) {
//        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
//        PukeGame newGame = new PukeGame();
//        newGame.setPanshu(panshu);
//        newGame.setRenshu(renshu);
//        newGame.setFixedPlayerCount(renshu);
//        newGame.setDifen(difen);
//        newGame.setPowerLimit(powerLimit);
//        newGame.setOptionalPlay(optionalPlay);
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
//        newGame.updatePlayerPosition(playerId);
//        gameServer.playerCreateGame(newGame, playerId);
//
//        return new PukeGameValueObject(newGame);
//    }
//
//    @Override
//    public PukeGameValueObject newPukeGamePlayerLeaveAndQuit(String gameId, String playerId, Integer panshu, Integer renshu, Double difen, OptionalPlay optionalPlay, Integer powerLimit,
//                                                                PukeGameQueryService gameQueryService, BijiGameMsgService gameMsgService, GamePlayWsNotifier wsNotifier) {
//        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
//        PukeGame newGame = new PukeGame();
//        newGame.setPanshu(panshu);
//        newGame.setRenshu(renshu);
//        newGame.setFixedPlayerCount(renshu);
//        newGame.setDifen(difen);
//        newGame.setPowerLimit(powerLimit);
//        newGame.setOptionalPlay(optionalPlay);
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
//        newGame.updatePlayerPosition(playerId);
//        gameServer.playerCreateGame(newGame, playerId);
//        return new PukeGameValueObject(newGame);
//    }

    @Override
    public PukeGameValueObject joinGame(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        gameServer.join(playerId, gameId);
        PukeGame newGame = (PukeGame) gameServer.findGame(gameId);
        newGame.updatePlayerPosition(playerId);
        return new PukeGameValueObject(newGame);
    }

    @Override
    public PukeGameValueObject leaveGameByHangup(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        ((PukeGame) game).updatePlayerPosition(playerId);
        PukeGameValueObject pukeGameValueObject = gameServer.leaveByHangup(playerId);
        if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return pukeGameValueObject;
    }

    @Override
    public PukeGameValueObject leaveGame(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        ((PukeGame) game).updatePlayerPosition(playerId);
        PukeGameValueObject pukeGameValueObject = gameServer.leaveByPlayer(playerId);
        if (game.getState().name().equals(FinishedByVote.name) || game.getState().name().equals(Finished.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return pukeGameValueObject;
    }

    @Override
    public PukeGameValueObject leaveGameByOffline(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Game game = gameServer.findGamePlayerPlaying(playerId);
        ((PukeGame) game).updatePlayerPosition(playerId);
        PukeGameValueObject pukeGameValueObject = gameServer.leaveByOffline(playerId);
        if (game.getState().name().equals(FinishedByVote.name)) {// 有可能离开的时候正在投票，由于离开自动投弃权最终导致游戏结束
            gameServer.finishGame(game.getId());
        }
        return pukeGameValueObject;
    }

    @Override
    public PukeGameValueObject backToGame(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        return gameServer.back(playerId, gameId);
    }

    @Override
    public ReadyForGameResult readyForGame(String playerId, Long currentTime) throws Exception {
        ReadyForGameResult result = new ReadyForGameResult();
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGameValueObject pukeGameValueObject = gameServer.ready(playerId, currentTime);
        result.setPukeGame(pukeGameValueObject);
        if (pukeGameValueObject.getState().name().equals(Playing.name)) {
            PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
            PanActionFrame firstActionFrame = pukeGame.createJuAndStartFirstPan(currentTime);
            result.setFirstActionFrame(firstActionFrame);
        }
        return result;
    }

    @Override
    public ReadyForGameResult cancelReadyForGame(String playerId, Long currentTime) throws Exception {
        ReadyForGameResult result = new ReadyForGameResult();
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGameValueObject pukeGameValueObject = gameServer.cancelReady(playerId, currentTime);
        result.setPukeGame(pukeGameValueObject);
        return result;
    }

    @Override
    public PukeGameValueObject finish(String playerId, Long currentTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
        if (pukeGame.getState().name().equals(WaitingStart.name)) {
            return new PukeGameValueObject(pukeGame);
        } else {
            if (pukeGame.getOptionalPlay().isBanJiesan()){
                return new PukeGameValueObject(pukeGame);
            }
            pukeGame.launchVoteToFinish(playerId, new MostPlayersWinVoteCalculator(), currentTime, 99000);
            pukeGame.voteToFinish(playerId, VoteOption.yes);
        }

        if (pukeGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(pukeGame.getId());
        }
        return new PukeGameValueObject(pukeGame);
    }

    @Override
    public PukeGameValueObject quit(String playerId, Long currentTime,String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        if (gameServer.getPlayerIdGameIdMap().get(playerId)!=null) {
            PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
            if (pukeGame.getState().name().equals(WaitingStart.name)) {
                gameServer.quit(playerId);
                if (pukeGame.getIdPlayerMap().size()==0){
                    gameServer.finishGame(pukeGame.getId());
                }
            }
            return new PukeGameValueObject(pukeGame);
        }else {
            PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
            pukeGame.quit(playerId);
            if (pukeGame.getState().name().equals(WaitingStart.name)) {
                if (pukeGame.getIdPlayerMap().size()==0){
                    gameServer.finishGame(pukeGame.getId());
                }
            }
            return new PukeGameValueObject(pukeGame);
        }

    }

    @Override
    public PukeGameValueObject voteToFinish(String playerId, Boolean yes) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
        if (yes) {
            pukeGame.voteToFinish(playerId, VoteOption.yes);
        } else {
            pukeGame.voteToFinish(playerId, VoteOption.no);
        }

        if (pukeGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(pukeGame.getId());
        }
        return new PukeGameValueObject(pukeGame);
    }

    @Override
    public PukeGameValueObject voteToFinishByTimeOver(String playerId, Long currentTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGamePlayerPlaying(playerId);
        pukeGame.voteToFinishByTimeOver(currentTime);

        if (pukeGame.getState().name().equals(FinishedByVote.name)) {
            gameServer.finishGame(pukeGame.getId());
        }
        return new PukeGameValueObject(pukeGame);
    }

    @Override
    public PukeGameValueObject finishGameImmediately(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        pukeGame.finish();
        pukeGame.setState(new Finished());
        pukeGame.updateAllPlayersState(new PlayerFinished());
        gameServer.finishGame(gameId);
        return new PukeGameValueObject(pukeGame);
    }

    @Override
    public void bindPlayer(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        gameServer.bindPlayer(playerId, gameId);
    }

    @Override
    public PukeGameValueObject joinWatch(String playerId, String nickName, String headimgurl, String gameId)
            throws Exception {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        watcherMap.join(playerId, nickName, headimgurl, gameId);
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGameValueObject pukeGameValueObject = gameServer.getInfo(playerId, gameId);
        return pukeGameValueObject;
    }

    @Override
    public PukeGameValueObject leaveWatch(String playerId, String gameId) throws Exception {
        WatcherMap watcherMap = singletonEntityRepository.getEntity(WatcherMap.class);
        watcherMap.leave(playerId, gameId);
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGameValueObject pukeGameValueObject = gameServer.getInfo(playerId, gameId);
        return pukeGameValueObject;
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
    public Map<String, String> playLeaveGameHosting(String playerId, String gameId, boolean isLeave){
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameID;
        if (gameId == null) {
            gameID = gameServer.findBindGameId(playerId);
        } else {
            gameID = gameId;
        }
        if (gameID == null) return null;
        PukeGame pukeGame = null;
        try {
            pukeGame = (PukeGame) gameServer.findGame(gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> depositPlayerList = null;
        if (pukeGame != null) {
            Ju ju = pukeGame.getJu();
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
            }
        }
        return depositPlayerList;
    }

    @Override
    public PukeGameValueObject automaticVoteToFinish(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame)gameServer.findGame(gameId);
        pukeGame.automaticToFinish();
        if (pukeGame.getState().name().equals(FinishedByTuoguan.name)) {
            gameServer.finishGame(pukeGame.getId());
        }
        return new PukeGameValueObject(pukeGame);
    }

    @Override
    public List<PanActionFrame> getPanActionFrame(String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        Pan currentPan = pukeGame.getJu().getCurrentPan();
        return currentPan.getActionFrameList();
    }

    @Override
    public String getGameIdByPlayerId(String playerId) {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        Map<String, Game> gameIdGameMap = gameServer.getGameIdGameMap();
        for (Game game : gameIdGameMap.values()) {
            PukeGame pukeGame = (PukeGame) game;
            Map<String, GamePlayer> idPlayerMap = pukeGame.getIdPlayerMap();
            if (idPlayerMap.containsKey(playerId)) {
                return pukeGame.getId();
            }
        }
        return null;
    }

}
