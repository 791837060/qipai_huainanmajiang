package com.anbang.qipai.guandan.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;

public class GameInfoVO {
	private String gameId;
	private int panNo;
	private int actionNo;
	private List<PukeGamePlayerInfoVO> playerInfos;
	private List<String> chaodiPlayerIdList;

	public GameInfoVO() {

	}

	public GameInfoVO(PukeGameInfoDbo gameInfo) {
		gameId = gameInfo.getGameId();
		panNo = gameInfo.getPanNo();
		actionNo = gameInfo.getActionNo();
		playerInfos = new ArrayList<>();
		gameInfo.getPlayerInfos().forEach((playerInfo) -> playerInfos.add(new PukeGamePlayerInfoVO(playerInfo)));
		chaodiPlayerIdList = gameInfo.getChaodiPlayerIdList();
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public int getActionNo() {
		return actionNo;
	}

	public void setActionNo(int actionNo) {
		this.actionNo = actionNo;
	}

	public List<PukeGamePlayerInfoVO> getPlayerInfos() {
		return playerInfos;
	}

	public void setPlayerInfos(List<PukeGamePlayerInfoVO> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public List<String> getChaodiPlayerIdList() {
		return chaodiPlayerIdList;
	}

	public void setChaodiPlayerIdList(List<String> chaodiPlayerIdList) {
		this.chaodiPlayerIdList = chaodiPlayerIdList;
	}

}
