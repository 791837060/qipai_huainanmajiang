package com.anbang.qipai.biji.cqrs.q.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.biji.cqrs.c.domain.PlayerActionFrameFilter;
import com.anbang.qipai.biji.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.biji.cqrs.q.dao.GameLatestPanActionFrameDboDao;
import com.anbang.qipai.biji.cqrs.q.dao.JuResultDboDao;
import com.anbang.qipai.biji.cqrs.q.dao.PanActionFrameDboDao;
import com.anbang.qipai.biji.cqrs.q.dao.PanResultDboDao;
import com.anbang.qipai.biji.cqrs.q.dao.PukeGameDboDao;
import com.anbang.qipai.biji.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PanActionFrameDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.biji.plan.bean.PlayerInfo;
import com.anbang.qipai.biji.plan.dao.PlayerInfoDao;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;
import com.dml.shisanshui.pan.PanActionFrame;

@Service
public class PukePlayQueryService {

	@Autowired
	private PukeGameDboDao pukeGameDboDao;

	@Autowired
	private PlayerInfoDao playerInfoDao;

	@Autowired
	private PanResultDboDao panResultDboDao;

	@Autowired
	private JuResultDboDao juResultDboDao;

	@Autowired
	private PanActionFrameDboDao panActionFrameDboDao;

	@Autowired
	private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

	private PlayerActionFrameFilter pf = new PlayerActionFrameFilter();

	public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId, String playerId) throws Exception {
		PukeGameDbo pukeGameDbo = pukeGameDboDao.findById(gameId);
		if (!(pukeGameDbo.getState().name().equals(Playing.name)
				|| pukeGameDbo.getState().name().equals(VotingWhenPlaying.name)
				|| pukeGameDbo.getState().name().equals(VoteNotPassWhenPlaying.name))) {
			throw new Exception("game not playing");
		}
		GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
		return pf.filter(frame, playerId);
	}

	public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId) {
		GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
		if (frame != null) {
			return frame.getPanActionFrame();
		} else {
			return null;
		}
	}

	public void readyForGame(ReadyForGameResult readyForGameResult) {
		PukeGameValueObject pukeGame = readyForGameResult.getPukeGame();
		Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
		pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
		PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
		pukeGameDboDao.save(pukeGameDbo);

		if (readyForGameResult.getFirstActionFrame() != null) {
			PanActionFrame panActionFrame = readyForGameResult.getFirstActionFrame();
			gameLatestPanActionFrameDboDao.save(pukeGame.getId(), panActionFrame);
			// 记录一条Frame，回放的时候要做
			String gameId = pukeGame.getId();
			int panNo = panActionFrame.getPanAfterAction().getNo();
			int actionNo = panActionFrame.getNo();
			PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
			panActionFrameDbo.setPanActionFrame(panActionFrame);
			panActionFrameDboDao.save(panActionFrameDbo);
		}

	}

	public void action(PukeActionResult pukeActionResult) {
		PukeGameValueObject pukeGame = pukeActionResult.getPukeGame();
		Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
		pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
		PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
		pukeGameDboDao.save(pukeGameDbo);

		String gameId = pukeGameDbo.getId();
		PanActionFrame panActionFrame = pukeActionResult.getPanActionFrame();
		gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
		// 记录一条Frame，回放的时候要做
		int panNo = panActionFrame.getPanAfterAction().getNo();
		int actionNo = panActionFrame.getNo();
		PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
		panActionFrameDbo.setPanActionFrame(panActionFrame);
		panActionFrameDboDao.save(panActionFrameDbo);

		// 盘出结果的话要记录结果
		BijiPanResult bijiPanResult = pukeActionResult.getPanResult();
		if (bijiPanResult != null) {
			PanResultDbo panResultDbo = new PanResultDbo(gameId, bijiPanResult);
			panResultDbo.setPanActionFrame(panActionFrame);
			if (pukeActionResult.getJuResult() != null) {// 一切都结束了
				// 要记录局结果
				JuResultDbo juResultDbo = new JuResultDbo(gameId, panResultDbo, pukeActionResult.getJuResult());
				juResultDboDao.save(juResultDbo);
			}
			panResultDboDao.save(panResultDbo);
		}

	}

	public void readyToNextPan(ReadyToNextPanResult readyToNextPanResult) {
		PukeGameValueObject pukeGame = readyToNextPanResult.getPukeGame();
		Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
		pukeGame.allPlayerIds().forEach((pid) -> playerInfoMap.put(pid, playerInfoDao.findById(pid)));
		PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
		pukeGameDboDao.save(pukeGameDbo);

		if (readyToNextPanResult.getFirstActionFrame() != null) {
			PanActionFrame panActionFrame = readyToNextPanResult.getFirstActionFrame();
			gameLatestPanActionFrameDboDao.save(pukeGame.getId(), panActionFrame);
			// 记录一条Frame，回放的时候要做
			String gameId = pukeGame.getId();
			int panNo = panActionFrame.getPanAfterAction().getNo();
			int actionNo = panActionFrame.getNo();
			PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
			panActionFrameDbo.setPanActionFrame(panActionFrame);
			panActionFrameDboDao.save(panActionFrameDbo);
		}
	}

	public PanResultDbo findPanResultDbo(String gameId, int panNo) {
		return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

	public PanResultDbo findPanResultDboForBackPlay(String gameId, int panNo) {
		return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

	public JuResultDbo findJuResultDbo(String gameId) {
		return juResultDboDao.findByGameId(gameId);
	}

	public List<PanActionFrameDbo> findPanActionFrameDboForBackPlay(String gameId, int panNo) {
		return panActionFrameDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

}
