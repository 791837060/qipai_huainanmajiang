package com.anbang.qipai.doudizhu.cqrs.c.service.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGame;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.QiangdizhuResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.Qiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.service.PukePlayCmdService;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.mpgame.game.player.PlayerNotInGameException;
import com.dml.mpgame.server.GameServer;

@Component
public class PukePlayCmdServiceImpl extends CmdServiceBase implements PukePlayCmdService {

	@Override
	public PukeActionResult da(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime)
			throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		String gameId = gameServer.findBindGameId(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}

		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		PukeActionResult pukeActionResult = pukeGame.da(playerId, paiIds, dianshuZuheIdx, actionTime);

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
		if (pukeGame.getState().name().equals(Qiangdizhu.name)) {// 开始下一盘了
			PanActionFrame firstActionFrame = pukeGame.getJu().getCurrentPan().findLatestActionFrame();
			readyToNextPanResult.setFirstActionFrame(firstActionFrame);
			readyToNextPanResult.setPlayerQiangdizhuMap(pukeGame.getQiangdizhuInfo());
            readyToNextPanResult.setPlayerJiaofenMap(pukeGame.getJiaofenInfo());
			GameInfo gameInfo = new GameInfo();
			gameInfo.setBeishu(new DoudizhuBeishu());
			gameInfo.getBeishu().setRenshu(pukeGame.getRenshu());
			gameInfo.setActionTime(System.currentTimeMillis());
			readyToNextPanResult.setGameInfo(gameInfo);
		}
		readyToNextPanResult.setPukeGame(new PukeGameValueObject(pukeGame));
		return readyToNextPanResult;
	}

	@Override
	public PukeActionResult guo(String playerId, Long actionTime) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		String gameId = gameServer.findBindGameId(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		PukeActionResult pukeActionResult = pukeGame.guo(playerId, actionTime);
		return pukeActionResult;
	}

	@Override
	public QiangdizhuResult qiangdizhu(String playerId, Boolean qiang, Long currentTime) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		String gameId = gameServer.findBindGameId(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		QiangdizhuResult result = pukeGame.qiangdizhu(playerId, qiang, currentTime);
		return result;
	}

    @Override
    public QiangdizhuResult jiaofenQiangdizhu(String playerId, Integer score, Long currentTime) throws Exception {
        GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
        String gameId = gameServer.findBindGameId(playerId);
        if (gameId == null) {
            throw new PlayerNotInGameException();
        }
        PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
        QiangdizhuResult result = pukeGame.qiangdizhu(playerId, score, currentTime);
        return result;
    }

}
