package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.cqrs.c.domain.state.PukeGamePlayerChaodiState;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;
import com.dml.shuangkou.ju.JuResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {

	private int panshu;
	private int renshu;
	private OptionalPlay optionalPlay;
	private int powerLimit;
	private double difen;
	private Map<String, Double> playeTotalScoreMap = new HashMap<>();
	private Map<String, Integer> playeGongxianfenMap = new HashMap<>();
	private Map<String, Integer> playerGongxianfenDetalMap = new HashMap<>();
	private Map<String, GuandanGongxianFen> playeTotalGongxianfenMap = new HashMap<>();
	private Map<String, Integer> playerMaxXianshuMap = new HashMap<>();
	private Map<String, Integer> playerOtherMaxXianshuMap = new HashMap<>();
	private Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap = new HashMap<>();
	private Map<String, Integer> playerMingciMap = new HashMap<>();
	private List<String> chaodiPlayerIdList = new ArrayList<>();
	private JuResult juResult;

	public PukeGameValueObject(PukeGame pukeGame) {
		super(pukeGame);
		panshu = pukeGame.getPanshu();
		renshu = pukeGame.getRenshu();
		optionalPlay=pukeGame.getOptionalPlay();
		difen=pukeGame.getDifen();
		playeTotalScoreMap.putAll(pukeGame.getPlayerTotalScoreMap());
		playeGongxianfenMap.putAll(pukeGame.getPlayerGongxianfenMap());
		playerGongxianfenDetalMap.putAll(pukeGame.getPlayerGongxianfenDetalMap());
		playerChaodiStateMap.putAll(pukeGame.getPlayerChaodiStateMap());
		playeTotalGongxianfenMap.putAll(pukeGame.getPlayerTotalGongxianfenMap());
		playerMaxXianshuMap.putAll(pukeGame.getPlayerMaxXianshuMap());
		playerOtherMaxXianshuMap.putAll(pukeGame.getPlayerOtherMaxXianshuMap());
		chaodiPlayerIdList = new ArrayList<>(pukeGame.getChaodiPlayerIdList());
		playerMingciMap.putAll(pukeGame.getPlayerMingciMap());
		if (pukeGame.getJu() != null) {
			juResult = pukeGame.getJu().getJuResult();
		}
		powerLimit = pukeGame.getPowerLimit();
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

	public OptionalPlay getOptionalPlay() {
		return optionalPlay;
	}

	public void setOptionalPlay(OptionalPlay optionalPlay) {
		this.optionalPlay = optionalPlay;
	}

	public double getDifen() {
		return difen;
	}

	public void setDifen(double difen) {
		this.difen = difen;
	}

    public Map<String, Double> getPlayeTotalScoreMap() {
        return playeTotalScoreMap;
    }

    public void setPlayeTotalScoreMap(Map<String, Double> playeTotalScoreMap) {
        this.playeTotalScoreMap = playeTotalScoreMap;
    }

    public Map<String, Integer> getPlayeGongxianfenMap() {
		return playeGongxianfenMap;
	}

	public void setPlayeGongxianfenMap(Map<String, Integer> playeGongxianfenMap) {
		this.playeGongxianfenMap = playeGongxianfenMap;
	}

	public Map<String, Integer> getPlayerGongxianfenDetalMap() {
		return playerGongxianfenDetalMap;
	}

	public void setPlayerGongxianfenDetalMap(Map<String, Integer> playerGongxianfenDetalMap) {
		this.playerGongxianfenDetalMap = playerGongxianfenDetalMap;
	}

	public Map<String, GuandanGongxianFen> getPlayeTotalGongxianfenMap() {
		return playeTotalGongxianfenMap;
	}

	public void setPlayeTotalGongxianfenMap(Map<String, GuandanGongxianFen> playeTotalGongxianfenMap) {
		this.playeTotalGongxianfenMap = playeTotalGongxianfenMap;
	}

	public Map<String, Integer> getPlayerMaxXianshuMap() {
		return playerMaxXianshuMap;
	}

	public void setPlayerMaxXianshuMap(Map<String, Integer> playerMaxXianshuMap) {
		this.playerMaxXianshuMap = playerMaxXianshuMap;
	}

	public Map<String, Integer> getPlayerOtherMaxXianshuMap() {
		return playerOtherMaxXianshuMap;
	}

	public void setPlayerOtherMaxXianshuMap(Map<String, Integer> playerOtherMaxXianshuMap) {
		this.playerOtherMaxXianshuMap = playerOtherMaxXianshuMap;
	}

	public Map<String, PukeGamePlayerChaodiState> getPlayerChaodiStateMap() {
		return playerChaodiStateMap;
	}

	public void setPlayerChaodiStateMap(Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap) {
		this.playerChaodiStateMap = playerChaodiStateMap;
	}

	public Map<String, Integer> getPlayerMingciMap() {
		return playerMingciMap;
	}

	public void setPlayerMingciMap(Map<String, Integer> playerMingciMap) {
		this.playerMingciMap = playerMingciMap;
	}

	public List<String> getChaodiPlayerIdList() {
		return chaodiPlayerIdList;
	}

	public void setChaodiPlayerIdList(List<String> chaodiPlayerIdList) {
		this.chaodiPlayerIdList = chaodiPlayerIdList;
	}

	public JuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(JuResult juResult) {
		this.juResult = juResult;
	}

	public int getPowerLimit() {
		return powerLimit;
	}

	public void setPowerLimit(int powerLimit) {
		this.powerLimit = powerLimit;
	}
}
