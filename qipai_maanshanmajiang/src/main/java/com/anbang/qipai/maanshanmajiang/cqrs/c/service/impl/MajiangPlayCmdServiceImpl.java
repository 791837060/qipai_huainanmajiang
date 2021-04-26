package com.anbang.qipai.maanshanmajiang.cqrs.c.service.impl;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque.DingqueState;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.MajiangPlayCmdService;
import com.dml.majiang.pai.XushupaiCategory;
import org.springframework.stereotype.Component;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.CannotXipaiException;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangActionResult;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangGame;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.ReadyToNextPanResult;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.VoteNotPassWhenWaitingNextPan;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.player.PlayerNotInGameException;
import com.dml.mpgame.server.GameServer;

import java.util.Set;

@Component
public class MajiangPlayCmdServiceImpl extends CmdServiceBase implements MajiangPlayCmdService {

    @Override
    public MajiangActionResult action(String playerId, Integer actionId, Integer actionNo, Long actionTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        MajiangActionResult majiangActionResult = majiangGame.action(playerId, actionId, actionNo, actionTime);
        if (majiangActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }
        return majiangActionResult;
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        majiangGame.readyToNextPan(playerId);
        if (majiangGame.getState().name().equals(Playing.name) || majiangGame.getState().name().equals(DingqueState.name)) {    //开始下一盘了
            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setMajiangGame(new MajiangGameValueObject(majiangGame));
        return readyToNextPanResult;
    }

    @Override
    public MajiangGameValueObject xipai(String playerId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        if (!(majiangGame.getState().name().equals(WaitingNextPan.name) || majiangGame.getState().name().equals(VoteNotPassWhenWaitingNextPan.name))) { //准备下一盘
            throw new CannotXipaiException();
        }
        return majiangGame.xipai(playerId);
    }

    @Override
    public MajiangActionResult automaticAction(String playerId, Integer actionId, Long actionTime, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        MajiangActionResult majiangActionResult = majiangGame.automaticAction(playerId, actionId, actionTime);
        if (majiangActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }
        return majiangActionResult;
    }

    @Override
    public ReadyToNextPanResult readyToNextPan(String playerId, Set<String> playerIds) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        majiangGame.readyToNextPan(playerId, playerIds);
        if (majiangGame.getState().name().equals(Playing.name) || majiangGame.getState().name().equals(DingqueState.name)) {    //开始下一盘了
            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setMajiangGame(new MajiangGameValueObject(majiangGame));
        return readyToNextPanResult;
    }

    @Override
    public ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
        majiangGame.readyToNextPan(playerId, playerIds);
        if (majiangGame.getState().name().equals(Playing.name)) {// 开始下一盘了
            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
            readyToNextPanResult.setFirstActionFrame(firstActionFrame);
        }
        readyToNextPanResult.setMajiangGame(new MajiangGameValueObject(majiangGame));
        return readyToNextPanResult;
    }

    @Override
    public MajiangActionResult dingque(String playerId, XushupaiCategory quemen) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        MajiangActionResult majiangActionResult = majiangGame.dingque(playerId, quemen);

        if (majiangActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }
        return majiangActionResult;
    }

    @Override
    public MajiangActionResult automaticDingque(String playerId, String gameId) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        MajiangGame majiangGame = (MajiangGame) gameServer.findGame(gameId);
        MajiangActionResult majiangActionResult = majiangGame.automaticDingque(playerId);
        if (majiangActionResult.getJuResult() != null) {// 全部结束
            gameServer.finishGame(gameId);
        }
        return majiangActionResult;
    }

}
