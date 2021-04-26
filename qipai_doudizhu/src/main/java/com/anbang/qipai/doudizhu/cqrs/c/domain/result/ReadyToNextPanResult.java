package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.doudizhu.pan.PanActionFrame;

public class ReadyToNextPanResult {
	private PukeGameValueObject pukeGame;

	private PanActionFrame firstActionFrame;

	private GameInfo gameInfo;

	private Map<String, PlayerQiangdizhuState> playerQiangdizhuMap;
    private Map<String,Integer> playerJiaofenMap;

	public PukeGameValueObject getPukeGame() {
		return pukeGame;
	}

	public void setPukeGame(PukeGameValueObject pukeGame) {
		this.pukeGame = pukeGame;
	}

	public PanActionFrame getFirstActionFrame() {
		return firstActionFrame;
	}

	public void setFirstActionFrame(PanActionFrame firstActionFrame) {
		this.firstActionFrame = firstActionFrame;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public Map<String, PlayerQiangdizhuState> getPlayerQiangdizhuMap() {
		return playerQiangdizhuMap;
	}

	public void setPlayerQiangdizhuMap(Map<String, PlayerQiangdizhuState> playerQiangdizhuMap) {
		this.playerQiangdizhuMap = playerQiangdizhuMap;
	}

    public Map<String, Integer> getPlayerJiaofenMap() {
        return playerJiaofenMap;
    }

    public void setPlayerJiaofenMap(Map<String, Integer> playerJiaofenMap) {
        this.playerJiaofenMap = playerJiaofenMap;
    }
}
