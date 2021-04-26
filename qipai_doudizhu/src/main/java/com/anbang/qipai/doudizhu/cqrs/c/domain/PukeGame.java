package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.anbang.qipai.doudizhu.cqrs.c.domain.jiaofen.DoudizhuJiaofenDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ChuntainAndFanchuntianOpportunityDetector;
import com.anbang.qipai.doudizhu.cqrs.c.domain.listener.ZhadanDaActionStatisticsListener;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.CannotQiangdizhuException;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.QiangdizhuDizhuDeterminer;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.*;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.*;
import com.dml.doudizhu.gameprocess.FixedPanNumbersJuFinishiDeterminer;
import com.dml.doudizhu.gameprocess.OnePlayerHasNoPaiPanFinishiDeterminer;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanActionFrame;
import com.dml.doudizhu.preparedapai.avaliablepai.OneAvaliablePaiFiller;
import com.dml.doudizhu.preparedapai.dipai.SanzhangDipaiDeterminer;
import com.dml.doudizhu.preparedapai.lipai.DianshuOrPaishuShoupaiSortStrategy;
import com.dml.doudizhu.preparedapai.luanpai.LastPanChuPaiOrdinalLuanpaiStrategy;
import com.dml.doudizhu.preparedapai.luanpai.RandomLuanPaiStrategy;
import com.dml.doudizhu.preparedapai.position.MustHasDongMenfengDeterminer;
import com.dml.doudizhu.preparedapai.position.YingjiaDongMenfengDeterminer;
import com.dml.doudizhu.preparedapai.xianda.DizhuXiandaDeterminer;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.NoZhadanDanGeDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.TongDengLianXuDianShuZuComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PukeGame extends FixedPlayersMultipanAndVotetofinishGame {
	private int panshu;
	private int renshu;
	private double difen;// 底分
	private Ju ju;
	private Map<String, Double> playerTotalScoreMap = new HashMap<>();
    private int powerLimit;
    private OptionalPlay optionalPlay;

	public QiangdizhuResult qiangdizhu(String playerId, boolean qiang, long currentTime) throws Exception {
		if (state.name().equals(VoteNotPassWhenQiangdizhu.name)) {
			state = new Qiangdizhu();
		}
		if (!state.name().equals(Qiangdizhu.name)) {
			throw new CannotQiangdizhuException();
		}
		QiangdizhuResult result = new QiangdizhuResult();
		GameInfo gameInfo = new GameInfo();
		gameInfo.setActionTime(currentTime);
		result.setGameInfo(gameInfo);
		Pan currentPan = ju.getCurrentPan();
		QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
        String dizhu=null;
		if (qiang){
             dizhu = qiangdizhuDizhuDeterminer.determineToDizhu(ju, playerId, 1);
        }else {
             dizhu = qiangdizhuDizhuDeterminer.determineToDizhu(ju, playerId, 0);
        }
		updatePlayerState(playerId, new PlayerAfterQiangdizhu());
		DoudizhuBeishu beishu = new DoudizhuBeishu();
		beishu.setRenshu(renshu);
		beishu.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
		gameInfo.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
		if (dizhu != null) {
			state = new Playing();
			updateAllPlayersState(new PlayerPlaying());
			currentPan.setDizhuPlayerId(dizhu);
			ju.getDipaiDeterminer().fadipai(ju);
			SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
			gameInfo.setDipaiList(new ArrayList<>(sanzhangDipaiDeterminer.getDipaiList()));
			ju.startPlaying(currentTime);
			PanActionFrame frame = currentPan.findLatestActionFrame();
			result.setPanActionFrame(frame);
		} else {
            PanActionFrame frame = currentPan.recordPanActionFrame(null, currentTime);
            result.setPanActionFrame(frame);
        }
        beishu.setFdbs(optionalPlay.getFdbs());
        beishu.calculate();
        gameInfo.setBeishu(beishu);
        result.setPukeGame(new PukeGameValueObject(this));
        Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
        playerQiangdizhuMap.putAll(qiangdizhuDizhuDeterminer.getPlayerQiangdizhuMap());
        result.setPlayerQiangdizhuMap(playerQiangdizhuMap);
        return result;
    }

    public QiangdizhuResult qiangdizhu(String playerId, int qiang, long currentTime) throws Exception {
        if (state.name().equals(VoteNotPassWhenQiangdizhu.name)) {
            state = new Qiangdizhu();
        }
        if (!state.name().equals(Qiangdizhu.name)) {
            throw new CannotQiangdizhuException();
        }
        QiangdizhuResult result = new QiangdizhuResult();
        GameInfo gameInfo = new GameInfo();
        gameInfo.setActionTime(currentTime);
        result.setGameInfo(gameInfo);
        Pan currentPan = ju.getCurrentPan();
        DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
        String dizhu = jiaofenDizhuDeterminer.determineToDizhu(ju, playerId, qiang);
        updatePlayerState(playerId, new PlayerAfterQiangdizhu());
        DoudizhuBeishu beishu = new DoudizhuBeishu();
        beishu.setJiaofen(jiaofenDizhuDeterminer.getScore());
        beishu.setRenshu(renshu);
        if (dizhu != null) {
            state = new Playing();
            updateAllPlayersState(new PlayerPlaying());
            currentPan.setDizhuPlayerId(dizhu);
            ju.getDipaiDeterminer().fadipai(ju);
            SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
            gameInfo.setDipaiList(new ArrayList<>(sanzhangDipaiDeterminer.getDipaiList()));
            ju.startPlaying(currentTime);
            PanActionFrame frame = currentPan.findLatestActionFrame();
            result.setPanActionFrame(frame);
        } else {
            PanActionFrame frame = currentPan.recordPanActionFrame(null, currentTime);
            result.setPanActionFrame(frame);
        }
        beishu.setFdbs(optionalPlay.getFdbs());
        beishu.calculate();
        gameInfo.setBeishu(beishu);
        result.setPukeGame(new PukeGameValueObject(this));
        Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>(jiaofenDizhuDeterminer.getPlayerQiangdizhuMap());
        result.setPlayerQiangdizhuMap(playerQiangdizhuMap);
        Map<String, Integer> playerJiaofenMap = new HashMap<>(jiaofenDizhuDeterminer.getPlayerJiaofenMap());
        result.setPlayerJiaofenMap(playerJiaofenMap);
        return result;
    }


    public Map<String, PlayerQiangdizhuState> getQiangdizhuInfo() {
        Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
	    if (optionalPlay.isJiaofen()){
            DoudizhuJiaofenDizhuDeterminer qiangdizhuDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
            playerQiangdizhuMap.putAll(qiangdizhuDizhuDeterminer.getPlayerQiangdizhuMap());
        }else {
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            playerQiangdizhuMap.putAll(qiangdizhuDizhuDeterminer.getPlayerQiangdizhuMap());
        }

        return playerQiangdizhuMap;
    }

    public Map<String, Integer> getJiaofenInfo() {
	    if (!optionalPlay.isJiaofen()){
	        return new HashMap<>();
        }

        DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
        return new HashMap<>(jiaofenDizhuDeterminer.getPlayerJiaofenMap());
    }

    private void createJuAndStartFirstPan(long startTime) throws Exception {
        ju = new Ju();
        if (renshu == 2) {
            ju.setPanFinishiDeterminer(new RangpaiCurrentPanFinishiDeterminer());
        } else {
            ju.setPanFinishiDeterminer(new OnePlayerHasNoPaiPanFinishiDeterminer());
        }
        ju.setJuFinishiDeterminer(new FixedPanNumbersJuFinishiDeterminer(panshu));
        ju.setAvaliablePaiFiller(new OneAvaliablePaiFiller());
		ju.setLuanPaiStrategyForFirstPan(new RandomLuanPaiStrategy(startTime));
        if (optionalPlay.isSzfbxp()) {
            ju.setLuanPaiStrategyForNextPan(new LastPanChuPaiOrdinalLuanpaiStrategy());
        } else {
            ju.setLuanPaiStrategyForNextPan(new RandomLuanPaiStrategy(startTime + 1));
        }
        OnePlayerShiqizhangpaiFaPaiStrategy onePlayerShiqizhangpaiFaPaiStrategy = new OnePlayerShiqizhangpaiFaPaiStrategy(renshu, optionalPlay.isQxp(), optionalPlay.isSzfbxp());
        onePlayerShiqizhangpaiFaPaiStrategy.setRenshu(renshu);
        onePlayerShiqizhangpaiFaPaiStrategy.setQxp(optionalPlay.isQxp());
        ju.setFaPaiStrategy(onePlayerShiqizhangpaiFaPaiStrategy);
        if (optionalPlay.isJiaofen()) {
            DoudizhuJiaofenDizhuDeterminer doudizhuJiaofenDizhuDeterminer = new DoudizhuJiaofenDizhuDeterminer();
            doudizhuJiaofenDizhuDeterminer.setOptionalPlay(optionalPlay);
            doudizhuJiaofenDizhuDeterminer.setRenshu(renshu);
            ju.setDizhuDeterminer(doudizhuJiaofenDizhuDeterminer);
        } else {
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = new QiangdizhuDizhuDeterminer();
            qiangdizhuDizhuDeterminer.setRenshu(renshu);
            ju.setDizhuDeterminer(qiangdizhuDizhuDeterminer);
        }
        ju.setMenfengDeterminerForFirstPan(new MustHasDongMenfengDeterminer());
        ju.setMenfengDeterminerForNextPan(new YingjiaDongMenfengDeterminer());
        ju.setXiandaDeterminer(new DizhuXiandaDeterminer());
        ju.setShoupaiSortStrategy(new DianshuOrPaishuShoupaiSortStrategy());
		ju.setWaihaoGenerator(new DoudizhuWaihaoGenerator());

		DoudizhuCurrentPanResultBuilder doudizhuCurrentPanResultBuilder = new DoudizhuCurrentPanResultBuilder();
		doudizhuCurrentPanResultBuilder.setRenshu(renshu);
		doudizhuCurrentPanResultBuilder.setDifen(difen);
		doudizhuCurrentPanResultBuilder.setFdbs(optionalPlay.getFdbs());
		doudizhuCurrentPanResultBuilder.setJiaofen(optionalPlay.isJiaofen());
		ju.setCurrentPanResultBuilder(doudizhuCurrentPanResultBuilder);
		ju.setJuResultBuilder(new DoudizhuJuResultBuilder());
		ju.setDipaiDeterminer(new SanzhangDipaiDeterminer());

		ju.setAllKedaPaiSolutionsGenerator(new DoudizhuAllKedaPaiSolutionsGenerator());
		DoudizhuDianShuZuYaPaiSolutionCalculator doudizhuDianShuZuYaPaiSolutionCalculator = new DoudizhuDianShuZuYaPaiSolutionCalculator();
		doudizhuDianShuZuYaPaiSolutionCalculator.setDanGeDianShuZuComparator(new NoZhadanDanGeDianShuZuComparator());
		doudizhuDianShuZuYaPaiSolutionCalculator.setLianXuDianShuZuComparator(new TongDengLianXuDianShuZuComparator());
		doudizhuDianShuZuYaPaiSolutionCalculator
				.setChibangDianShuZuComparator(new DoudizhuChibangDianShuZuComparator());
		doudizhuDianShuZuYaPaiSolutionCalculator.setFeijiDianShuZuComparator(new DoudizhuFeijiDianShuZuComparator());
		ju.setDianShuZuYaPaiSolutionCalculator(doudizhuDianShuZuYaPaiSolutionCalculator);

		DoudizhuZaDanYaPaiSolutionCalculator doudizhuZaDanYaPaiSolutionCalculator = new DoudizhuZaDanYaPaiSolutionCalculator();
        doudizhuZaDanYaPaiSolutionCalculator.setZhadanComparator(new DoudizhuZhadanComparator());
        ju.setZaDanYaPaiSolutionCalculator(doudizhuZaDanYaPaiSolutionCalculator);

        ju.setDaPaiSolutionsTipsFilter(new DoudizhuDaPaiSolutionsTipsFilter());
        ju.setYaPaiSolutionsTipsFilter(new DoudizhuYaPaiSolutionsTipsFilter());

        ju.addDaListener(new ZhadanDaActionStatisticsListener());
        ju.addDaListener(new ChuntainAndFanchuntianOpportunityDetector());

        ju.startFirstPan(allPlayerIds(), startTime);
        if (optionalPlay.isJiaofen()) {
            DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
            jiaofenDizhuDeterminer.init(ju);
        } else {
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            qiangdizhuDizhuDeterminer.init(ju);
        }

	}

	public PukeActionResult da(String playerId, List<Integer> paiIds, String dianshuZuheIdx, long actionTime)
			throws Exception {
		PanActionFrame panActionFrame = ju.da(playerId, paiIds, dianshuZuheIdx, actionTime);
		PukeActionResult result = new PukeActionResult();
		result.setPanActionFrame(panActionFrame);
		GameInfo gameInfo = new GameInfo();
		gameInfo.setActionTime(actionTime);
		result.setGameInfo(gameInfo);
		ZhadanDaActionStatisticsListener zhadanDaActionStatisticsListener = ju.getActionStatisticsListenerManager()
				.findDaListener(ZhadanDaActionStatisticsListener.class);
		DoudizhuBeishu beishu = new DoudizhuBeishu();
		beishu.setRenshu(renshu);

        if (!optionalPlay.isJiaofen()){
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
            gameInfo.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
        }else {
            DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setJiaofen(jiaofenDizhuDeterminer.getScore());
            beishu.setQiangdizhuCount(0);
            gameInfo.setQiangdizhuCount(0);
        }
		SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
        beishu.setDachuZhadanCount(zhadanDaActionStatisticsListener.getDachuZhadanCount());
        beishu.setFdbs(optionalPlay.getFdbs());
        beishu.calculate();
        gameInfo.setBeishu(beishu);
        gameInfo.setDipaiList(new ArrayList<>(sanzhangDipaiDeterminer.getDipaiList()));
        Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
        result.setPlayerQiangdizhuMap(playerQiangdizhuMap);
        result.setPlayerJiaofenMap(new HashMap<>());
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        checkAndFinishPan();
        if (state.name().equals(WaitingNextPan.name) || state.name().equals(Finished.name)) {// 盘结束了
            DoudizhuPanResult panResult = (DoudizhuPanResult) ju.findLatestFinishedPanResult();
            for (DoudizhuPanPlayerResult doudizhuPanPlayerResult : panResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(doudizhuPanPlayerResult.getPlayerId(), doudizhuPanPlayerResult.getTotalScore());
            }
            result.setPanResult(panResult);
			if (state.name().equals(Finished.name)) {// 局结束了
				result.setJuResult((DoudizhuJuResult) ju.getJuResult());
			}
		}
		result.setPukeGame(new PukeGameValueObject(this));
		return result;
	}

	public PukeActionResult guo(String playerId, long actionTime) throws Exception {
		PanActionFrame panActionFrame = ju.guo(playerId, actionTime);
		PukeActionResult result = new PukeActionResult();
		result.setPanActionFrame(panActionFrame);
		GameInfo gameInfo = new GameInfo();
		gameInfo.setActionTime(actionTime);
		result.setGameInfo(gameInfo);

		ZhadanDaActionStatisticsListener zhadanDaActionStatisticsListener = ju.getActionStatisticsListenerManager()
				.findDaListener(ZhadanDaActionStatisticsListener.class);
		DoudizhuBeishu beishu = new DoudizhuBeishu();
		beishu.setRenshu(renshu);
        if (!optionalPlay.isJiaofen()){
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
            gameInfo.setQiangdizhuCount(qiangdizhuDizhuDeterminer.getQiangdizhuCount());
        }else {
            DoudizhuJiaofenDizhuDeterminer doudizhuJiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
            beishu.setJiaofen(doudizhuJiaofenDizhuDeterminer.getScore());
            beishu.setQiangdizhuCount(0);
            gameInfo.setQiangdizhuCount(0);
        }
		SanzhangDipaiDeterminer sanzhangDipaiDeterminer = (SanzhangDipaiDeterminer) ju.getDipaiDeterminer();
        beishu.setDachuZhadanCount(zhadanDaActionStatisticsListener.getDachuZhadanCount());
        beishu.setFdbs(optionalPlay.getFdbs());
        beishu.calculate();
        gameInfo.setBeishu(beishu);
        gameInfo.setDipaiList(new ArrayList<>(sanzhangDipaiDeterminer.getDipaiList()));
        Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
        result.setPlayerQiangdizhuMap(playerQiangdizhuMap);
        result.setPlayerJiaofenMap(new HashMap<>());
        if (state.name().equals(VoteNotPassWhenPlaying.name)) {
            state = new Playing();
        }
        result.setPukeGame(new PukeGameValueObject(this));
        return result;
    }

	public PanActionFrame findFirstPanActionFrame() {
		return ju.getCurrentPan().findLatestActionFrame();
	}

	@Override
	protected boolean checkToFinishGame() throws Exception {
		return ju.getJuResult() != null;
	}

	@Override
	protected boolean checkToFinishCurrentPan() throws Exception {
		return ju.getCurrentPan() == null;
	}

	@Override
	protected void startNextPan() throws Exception {
		ju.startNextPan();
		if (optionalPlay.isJiaofen()){
		    DoudizhuJiaofenDizhuDeterminer jiaofenDizhuDeterminer = (DoudizhuJiaofenDizhuDeterminer) ju.getDizhuDeterminer();
		    jiaofenDizhuDeterminer.init(ju);
        }else {
            QiangdizhuDizhuDeterminer qiangdizhuDizhuDeterminer = (QiangdizhuDizhuDeterminer) ju.getDizhuDeterminer();
            qiangdizhuDizhuDeterminer.init(ju);
        }
		state = new Qiangdizhu();
		updateAllPlayersState(new PlayerQiangdizhu());
	}

	@Override
	protected void updatePlayerToExtendedVotingState(GamePlayer player) {
		if (player.getState().name().equals(PlayerQiangdizhu.name)) {
			player.setState(new PlayerVotingWhenQiangdizhu());
		} else if (player.getState().name().equals(PlayerAfterQiangdizhu.name)) {
			player.setState(new PlayerVotingWhenAfterQiangdizhu());
		}
	}

	@Override
	protected void updateToExtendedVotingState() {
		if (state.name().equals(Qiangdizhu.name) || state.name().equals(VoteNotPassWhenQiangdizhu.name)) {
			state = new VotingWhenQiangdizhu();
		}
	}

	@Override
	protected void updatePlayerToExtendedVotedState(GamePlayer player) {
		String stateName = player.getState().name();
		if (stateName.equals(PlayerVotingWhenQiangdizhu.name)) {
			player.setState(new PlayerVotedWhenQiangdizhu());
		} else if (player.getState().name().equals(PlayerVotingWhenAfterQiangdizhu.name)) {
			player.setState(new PlayerVotedWhenAfterQiangdizhu());
		}
	}

	@Override
	protected void recoveryPlayersStateFromExtendedVoting() throws Exception {
		if (state.name().equals(VoteNotPassWhenQiangdizhu.name)) {
			for (GamePlayer player : idPlayerMap.values()) {
				if (player.getState().name().equals(PlayerVotingWhenQiangdizhu.name)
						|| player.getState().name().equals(PlayerVotedWhenQiangdizhu.name)) {
					updatePlayerState(player.getId(), new PlayerQiangdizhu());
				} else if (player.getState().name().equals(PlayerVotingWhenAfterQiangdizhu.name)
						|| player.getState().name().equals(PlayerVotedWhenAfterQiangdizhu.name)) {
					updatePlayerState(player.getId(), new PlayerAfterQiangdizhu());
				}
			}
		}
	}

	@Override
	protected void updateToVoteNotPassStateFromExtendedVoting() throws Exception {
		if (state.name().equals(VotingWhenQiangdizhu.name)) {
			state = new VoteNotPassWhenQiangdizhu();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PukeGameValueObject toValueObject() {
		return new PukeGameValueObject(this);
	}

	@Override
	public void start(long currentTime) throws Exception {
		createJuAndStartFirstPan(currentTime);
		state = new Qiangdizhu();
		updateAllPlayersState(new PlayerQiangdizhu());
	}

	@Override
	public void finish() throws Exception {
		if (ju != null) {
			ju.finish();
		}
	}

	public int getPanshu() {
		return panshu;
	}

	public void setPanshu(int panshu) {
		this.panshu = panshu;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public Ju getJu() {
		return ju;
	}

	public void setJu(Ju ju) {
		this.ju = ju;
	}

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public Map<String, Double> getPlayerTotalScoreMap() {
        return playerTotalScoreMap;
    }

    public void setPlayerTotalScoreMap(Map<String, Double> playerTotalScoreMap) {
        this.playerTotalScoreMap = playerTotalScoreMap;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
