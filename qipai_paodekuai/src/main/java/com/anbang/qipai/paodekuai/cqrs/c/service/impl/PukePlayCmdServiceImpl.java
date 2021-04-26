package com.anbang.qipai.paodekuai.cqrs.c.service.impl;

import com.anbang.qipai.paodekuai.cqrs.c.domain.PukeGame;
import com.anbang.qipai.paodekuai.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.paodekuai.cqrs.c.service.PukePlayCmdService;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.player.PlayerNotInGameException;
import com.dml.mpgame.server.GameServer;
import com.dml.paodekuai.pan.PanActionFrame;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import org.springframework.stereotype.Component;

import java.util.*;

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
	public PukeActionResult autoDa(String playerId, ArrayList<Integer> paiIds, String dianshuZuheIdx, Long actionTime,String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
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
		if (pukeGame.getState().name().equals(Playing.name)) {// 开始下一盘了
			PanActionFrame firstActionFrame = pukeGame.getJu().getCurrentPan().findLatestActionFrame();
			readyToNextPanResult.setFirstActionFrame(firstActionFrame);
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
	public PukeActionResult autoGuo(String playerId, Long actionTime,String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame pukeGame = (PukeGame) gameServer.findGame(gameId);
		PukeActionResult pukeActionResult = pukeGame.guo(playerId, actionTime);
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
            Map<String, String> depositPlayerMap = new HashMap<>(majiangGame.getJu().getDepositPlayerList());
            boolean botWin=false;
            String winPlayerId=null;
            for (Map.Entry<String, String> stringStringEntry : depositPlayerMap.entrySet()) {
                PaodekuaiPlayer player = majiangGame.getJu().getCurrentPan().findPlayer(stringStringEntry.getKey());
                if (!player.getYaPaiSolutionCandidates().isEmpty()){
                    botWin=true;
                    winPlayerId=stringStringEntry.getKey();
                    break;
                }
            }
            if (botWin){
                PaodekuaiPlayer player = majiangGame.getJu().getCurrentPan().findPlayer(winPlayerId);
                List<PukePai> chupaiList=new ArrayList<>();
                List<DaPaiDianShuSolution> yaPaiSolutionsForTips = player.getYaPaiSolutionsForTips();
                DaPaiDianShuSolution chupaiSolution=null;
                for (DaPaiDianShuSolution yaPaiSolutionsForTip : yaPaiSolutionsForTips) {
                    if (chupaiSolution==null){
                        chupaiSolution=yaPaiSolutionsForTip;
                    }else {
                        if (Long.parseLong(yaPaiSolutionsForTip.getDianshuZuheIdx())<=Long.parseLong(chupaiSolution.getDianshuZuheIdx())){
                            chupaiSolution=yaPaiSolutionsForTip;
                        }
                    }
                }
                Map<Integer, PukePai> allShoupai = new HashMap<>(player.getAllShoupai());
                DianShu[] dachuDianShuArray = chupaiSolution.getDachuDianShuArray();
                List<DianShu> dachuDianShuList=new ArrayList<>(Arrays.asList(dachuDianShuArray));
                for (int i = 0; i < dachuDianShuList.size(); i++) {
                    for (PukePai value : allShoupai.values()) {
                        if (value.getPaiMian().dianShu().equals(dachuDianShuList.get(i))){
                            dachuDianShuList.remove(i);
                            i--;
                            chupaiList.add(value);
                            allShoupai.remove(value.getId());
                            break;
                        }
                    }
                }
                List<Integer> chupaiIdList=new ArrayList<>();
                for (PukePai pukePai : chupaiList) {
                    chupaiIdList.add(pukePai.getId());
                }
                autoDa(player.getId(), new ArrayList<>(chupaiIdList), chupaiSolution.getDianshuZuheIdx(),System.currentTimeMillis(), gameId);
            }

            PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
			readyToNextPanResult.setFirstActionFrame(firstActionFrame);
		}
		readyToNextPanResult.setPukeGame(new PukeGameValueObject(majiangGame));
		return readyToNextPanResult;
	}

	@Override
	public ReadyToNextPanResult autoReadyToNextPan(String playerId, Set<String> playerIds, String gameId) throws Exception {
		GameServer gameServer = singletonEntityRepository.getEntity(GameServer.class);
		PukeGame majiangGame = (PukeGame) gameServer.findGame(gameId);
		ReadyToNextPanResult readyToNextPanResult = new ReadyToNextPanResult();
		majiangGame.readyToNextPan(playerId, playerIds);
		if (majiangGame.getState().name().equals(Playing.name)) {// 开始下一盘了
			Map<String, String> depositPlayerMap = new HashMap<>(majiangGame.getJu().getDepositPlayerList());
			boolean botWin = false;
			String winPlayerId = null;
			for (Map.Entry<String, String> stringStringEntry : depositPlayerMap.entrySet()) {
				PaodekuaiPlayer player = majiangGame.getJu().getCurrentPan().findPlayer(stringStringEntry.getKey());
				if (!player.getYaPaiSolutionCandidates().isEmpty()) {
					botWin = true;
					winPlayerId = stringStringEntry.getKey();
					break;
				}
			}
			if (botWin) {
				PaodekuaiPlayer player = majiangGame.getJu().getCurrentPan().findPlayer(winPlayerId);
				List<PukePai> chupaiList = new ArrayList<>();
				List<DaPaiDianShuSolution> yaPaiSolutionsForTips = player.getYaPaiSolutionsForTips();
				DaPaiDianShuSolution chupaiSolution = null;
				for (DaPaiDianShuSolution yaPaiSolutionsForTip : yaPaiSolutionsForTips) {
					if (chupaiSolution == null) {
						chupaiSolution = yaPaiSolutionsForTip;
					} else {
						if (Long.parseLong(yaPaiSolutionsForTip.getDianshuZuheIdx()) <= Long.parseLong(chupaiSolution.getDianshuZuheIdx())) {
							chupaiSolution = yaPaiSolutionsForTip;
						}
					}
				}
				Map<Integer, PukePai> allShoupai = new HashMap<>(player.getAllShoupai());
				DianShu[] dachuDianShuArray = chupaiSolution.getDachuDianShuArray();
				List<DianShu> dachuDianShuList = new ArrayList<>(Arrays.asList(dachuDianShuArray));
				for (int i = 0; i < dachuDianShuList.size(); i++) {
					for (PukePai value : allShoupai.values()) {
						if (value.getPaiMian().dianShu().equals(dachuDianShuList.get(i))) {
							dachuDianShuList.remove(i);
							i--;
							chupaiList.add(value);
							allShoupai.remove(value.getId());
							break;
						}
					}
				}
				List<Integer> chupaiIdList = new ArrayList<>();
				for (PukePai pukePai : chupaiList) {
					chupaiIdList.add(pukePai.getId());
				}
				autoDa(player.getId(), new ArrayList<>(chupaiIdList), chupaiSolution.getDianshuZuheIdx(), System.currentTimeMillis(), gameId);
			}

			PanActionFrame firstActionFrame = majiangGame.getJu().getCurrentPan().findLatestActionFrame();
			readyToNextPanResult.setFirstActionFrame(firstActionFrame);
		}
		readyToNextPanResult.setPukeGame(new PukeGameValueObject(majiangGame));
		return readyToNextPanResult;
	}

}
