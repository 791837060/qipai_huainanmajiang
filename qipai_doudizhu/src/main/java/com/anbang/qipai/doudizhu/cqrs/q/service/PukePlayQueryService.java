package com.anbang.qipai.doudizhu.cqrs.q.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PanActionFramePlayerViewFilter;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.PukeActionResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.QiangdizhuResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyForGameResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.ReadyToNextPanResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.Qiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.VoteNotPassWhenQiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.VotingWhenQiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.q.dao.GameInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.GameLatestInfoDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.GameLatestPanActionFrameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.JuResultDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PanActionFrameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PanResultDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dao.PukeGameDboDao;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanActionFrameDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;
import com.anbang.qipai.doudizhu.plan.dao.PlayerInfoDao;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;

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
	private GameLatestInfoDboDao gameLatestInfoDboDao;

	@Autowired
	private GameInfoDboDao gameInfoDboDao;

	@Autowired
	private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

	private PanActionFramePlayerViewFilter pv = new PanActionFramePlayerViewFilter();

	public PanActionFrame findAndFilterCurrentPanValueObjectForPlayer(String gameId, String playerId) throws Exception {
		PukeGameDbo pukeGameDbo = pukeGameDboDao.findById(gameId);
		if (!(pukeGameDbo.getState().name().equals(Playing.name)
				|| pukeGameDbo.getState().name().equals(VotingWhenPlaying.name)
				|| pukeGameDbo.getState().name().equals(VoteNotPassWhenPlaying.name)
				|| pukeGameDbo.getState().name().equals(Qiangdizhu.name)
				|| pukeGameDbo.getState().name().equals(VotingWhenQiangdizhu.name)
				|| pukeGameDbo.getState().name().equals(VoteNotPassWhenQiangdizhu.name))) {
			throw new Exception("game not playing");
		}
		GameLatestPanActionFrameDbo frame = gameLatestPanActionFrameDboDao.findById(gameId);
		PanActionFrame panActionFrame = pv.filter(frame, playerId);
		return panActionFrame;
	}

	public GameLatestInfoDbo findGameLatestInfoDboById(String gameId) {
		return gameLatestInfoDboDao.findById(gameId);
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

			GameInfo gameInfo = readyForGameResult.getGameInfo();
			GameInfoDbo gameInfoDbo = new GameInfoDbo(pukeGame, readyForGameResult.getPlayerQiangdizhuMap(), readyForGameResult.getPlayerJiaofenMap(),gameInfo,
					panActionFrame.getNo());
			gameInfoDboDao.save(gameInfoDbo);
			GameLatestInfoDbo gameLatestInfoDbo = new GameLatestInfoDbo(pukeGame,
					readyForGameResult.getPlayerQiangdizhuMap(),readyForGameResult.getPlayerJiaofenMap(), gameInfo);
			gameLatestInfoDboDao.save(gameLatestInfoDbo);
		}
	}

	public void qiangdizhu(QiangdizhuResult qiangdizhuResult) {
		PukeGameValueObject pukeGame = qiangdizhuResult.getPukeGame();
		Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
		pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
		PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
		pukeGameDboDao.save(pukeGameDbo);

		String gameId = pukeGameDbo.getId();
		PanActionFrame panActionFrame = qiangdizhuResult.getPanActionFrame();

		gameLatestPanActionFrameDboDao.save(gameId, panActionFrame);
		// 记录一条Frame，回放的时候要做
		int panNo = panActionFrame.getPanAfterAction().getNo();
		int actionNo = panActionFrame.getNo();
		PanActionFrameDbo panActionFrameDbo = new PanActionFrameDbo(gameId, panNo, actionNo);
		panActionFrameDbo.setPanActionFrame(panActionFrame);
		panActionFrameDboDao.save(panActionFrameDbo);

		GameInfo gameInfo = qiangdizhuResult.getGameInfo();
		GameInfoDbo gameInfoDbo = new GameInfoDbo(pukeGame, qiangdizhuResult.getPlayerQiangdizhuMap(),qiangdizhuResult.getPlayerJiaofenMap(), gameInfo,
				actionNo);
		gameInfoDboDao.save(gameInfoDbo);
		GameLatestInfoDbo gameLatestInfoDbo = new GameLatestInfoDbo(pukeGame, qiangdizhuResult.getPlayerQiangdizhuMap(),qiangdizhuResult.getPlayerJiaofenMap(),
				gameInfo);
		gameLatestInfoDboDao.save(gameLatestInfoDbo);
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

		GameInfo gameInfo = pukeActionResult.getGameInfo();
		GameInfoDbo gameInfoDbo = new GameInfoDbo(pukeGame, pukeActionResult.getPlayerQiangdizhuMap(),pukeActionResult.getPlayerJiaofenMap(), gameInfo,
				actionNo);
		gameInfoDboDao.save(gameInfoDbo);
		GameLatestInfoDbo gameLatestInfoDbo = new GameLatestInfoDbo(pukeGame, pukeActionResult.getPlayerQiangdizhuMap(),pukeActionResult.getPlayerJiaofenMap(),
				gameInfo);
		gameLatestInfoDboDao.save(gameLatestInfoDbo);

		// 盘出结果的话要记录结果
		DoudizhuPanResult doudizhuPanResult = pukeActionResult.getPanResult();
		if (doudizhuPanResult != null) {
			PanResultDbo panResultDbo = new PanResultDbo(gameId, doudizhuPanResult);
			panResultDbo.setPanActionFrame(panActionFrame);
			panResultDbo.setGameLatestInfoDbo(gameLatestInfoDbo);
			panResultDboDao.save(panResultDbo);
			if (pukeActionResult.getJuResult() != null) {// 一切都结束了
				// 要记录局结果
				JuResultDbo juResultDbo = new JuResultDbo(gameId, panResultDbo, pukeActionResult.getJuResult());
				juResultDboDao.save(juResultDbo);
			}
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

			GameInfo gameInfo = readyToNextPanResult.getGameInfo();
			GameInfoDbo gameInfoDbo = new GameInfoDbo(pukeGame, readyToNextPanResult.getPlayerQiangdizhuMap(),readyToNextPanResult.getPlayerJiaofenMap(), gameInfo,
					panActionFrame.getNo());
			gameInfoDboDao.save(gameInfoDbo);
			GameLatestInfoDbo gameLatestInfoDbo = new GameLatestInfoDbo(pukeGame,
					readyToNextPanResult.getPlayerQiangdizhuMap(),readyToNextPanResult.getPlayerJiaofenMap(), gameInfo);
			gameLatestInfoDboDao.save(gameLatestInfoDbo);
		}
	}

	public PanResultDbo findPanResultDbo(String gameId, int panNo) {
		return panResultDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

	public JuResultDbo findJuResultDbo(String gameId) {
		return juResultDboDao.findByGameId(gameId);
	}

	public List<PanActionFrameDbo> findPanActionFrameDboForBackPlay(String gameId, int panNo) {
		return panActionFrameDboDao.findByGameIdAndPanNo(gameId, panNo);
	}

	public List<GameInfoDbo> findGameInfoDboForBackPlay(String gameId, int panNo) {
		return gameInfoDboDao.findByGameIdAndPanNo(gameId, panNo);
	}
}
