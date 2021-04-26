package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.doudizhu.ju.JuResult;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGameValueObject;

import java.util.HashMap;
import java.util.Map;

public class PukeGameValueObject extends FixedPlayersMultipanAndVotetofinishGameValueObject {

	private int panshu;
	private int renshu;
	private double difen;
    private Map<String, Double> playerTotalScoreMap = new HashMap<>();
	private JuResult juResult;
    private int powerLimit;
    private OptionalPlay optionalPlay;

	public PukeGameValueObject(PukeGame pukeGame) {
        super(pukeGame);
        panshu = pukeGame.getPanshu();
        renshu = pukeGame.getRenshu();
        powerLimit = pukeGame.getPowerLimit();
        optionalPlay = pukeGame.getOptionalPlay();
        difen = pukeGame.getDifen();
        playerTotalScoreMap.putAll(pukeGame.getPlayerTotalScoreMap());
        if (pukeGame.getJu() != null) {
            juResult = pukeGame.getJu().getJuResult();
        }
    }



	public JuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(JuResult juResult) {
		this.juResult = juResult;
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

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
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

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
