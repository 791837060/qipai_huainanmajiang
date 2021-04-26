package com.anbang.qipai.biji.cqrs.c.service.impl;

import com.anbang.qipai.biji.cqrs.c.domain.OptionalPlay;
import com.dml.shisanshui.player.ShisanshuiPlayer;
import org.springframework.stereotype.Component;

import com.anbang.qipai.biji.cqrs.c.domain.PukeGame;
import com.anbang.qipai.biji.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.biji.cqrs.c.service.PukePlayCmdService;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.player.PlayerNotInGameException;
import com.dml.mpgame.server.GameServer;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pan.PanActionFrame;

import java.util.*;

@Component
public class PukePlayCmdServiceImpl extends CmdServiceBase implements PukePlayCmdService {

    public Dao findDaoByGameIdAndPlayerIdAndIndex(String gameId, String playerId, String index) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        return pukeGame.findDaoByPlayerIdAndIndex(playerId, index);
    }

    @Override
    public PukeActionResult chupai(String playerId, String toudaoIndex, String zhongdaoIndex, String weidaoIndex,
                                   Long actionTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }

        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        PukeActionResult pukeActionResult = pukeGame.chupai(playerId, toudaoIndex, zhongdaoIndex, weidaoIndex,
                actionTime);

        if (pukeActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }

        return pukeActionResult;
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);

        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        pukeGame.readyToNextPan(playerId);
        if (pukeGame.getState().name().equals(Playing.name)) {// 开始下一盘了
            PanActionFrame firstActionFrame = pukeGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setPukeGame(new PukeGameValueObject(pukeGame));
        return readyToNextPanResult;
    }

    @Override
    public Map<String, Boolean> qipai(String playerId, String gameID) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (playerId == null) {
            gameId = gameID;
        }
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        OptionalPlay optionalPlay = pukeGame.getOptionalPlay();
        if (optionalPlay.isQipai()) {
            Map<String, ShisanshuiPlayer> playerIdPlayerMap = pukeGame.getJu().getCurrentPan().getPlayerIdPlayerMap();
            if (playerId != null) playerIdPlayerMap.get(playerId).setQipai(true);
            Map<String, Boolean> map = new HashMap<>();
            for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
                map.put(player.getId(), player.isQipai());
            }
            return map;

        } else return null;

    }

    @Override
    public PukeActionResult autoChupai(String playerId, Long actionTime, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        PukeActionResult pukeActionResult = pukeGame.autoChupai(playerId, actionTime);
        if (pukeActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }
        return pukeActionResult;
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        PukeGame majiangGame = (PukeGame) gameServer.findGame(gameId);
        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        majiangGame.readyToNextPan(playerId, playerIds);
        if (majiangGame.getState().name().equals(Playing.name)) {// 开始下一盘了
            autoChupai(playerId, System.currentTimeMillis(), gameId);
            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setPukeGame(new PukeGameValueObject(majiangGame));
        return readyToNextPanResult;
    }

    @Override
    public ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds,String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        PukeGame majiangGame = (PukeGame) gameServer.findGame(gameId);
        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        majiangGame.readyToNextPan(playerId, playerIds);
        if (majiangGame.getState().name().equals(Playing.name)) {// 开始下一盘了
            autoChupai(playerId, System.currentTimeMillis(), gameId);
            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setPukeGame(new PukeGameValueObject(majiangGame));
        return readyToNextPanResult;
    }

}
