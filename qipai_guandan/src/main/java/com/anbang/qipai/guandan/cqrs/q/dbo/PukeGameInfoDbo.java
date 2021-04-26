package com.anbang.qipai.guandan.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.guandan.cqrs.c.domain.GuandanGongxianFen;
import com.anbang.qipai.guandan.plan.bean.PlayerInfo;
import com.dml.mpgame.game.GamePlayerValueObject;

@Document
@CompoundIndexes({ @CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}") })
public class PukeGameInfoDbo {
	private String id;
	private String gameId;
	private int panNo;
	private int actionNo;
	private List<PukeGamePlayerInfoDbo> playerInfos;
	private List<String> chaodiPlayerIdList;
	private long createTime;

	public PukeGameInfoDbo() {
	}

	public PukeGameInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerInfo> playerInfoMap, int actionNo) {
		gameId = pukeGame.getId();
		panNo = pukeGame.getPanNo();
		this.actionNo = actionNo;
		chaodiPlayerIdList = new ArrayList<>(pukeGame.getChaodiPlayerIdList());
		playerInfos = new ArrayList<>();
		Map<String, Integer> playerMaxXianshuMap = pukeGame.getPlayerMaxXianshuMap();
		Map<String, Integer> playerOtherMaxXianshuMap = pukeGame.getPlayerOtherMaxXianshuMap();
		Map<String, Integer> playeGongxianfenMap = pukeGame.getPlayeGongxianfenMap();
		Map<String, Integer> playeGongxianfenDetalMap = pukeGame.getPlayerGongxianfenDetalMap();
		Map<String, GuandanGongxianFen> playeTotalGongxianfenMap = pukeGame.getPlayeTotalGongxianfenMap();
		Map<String, Integer> playerMingciMap = pukeGame.getPlayerMingciMap();
		for (GamePlayerValueObject playerValueObject : pukeGame.getPlayers()) {
			String playerId = playerValueObject.getId();
			PukeGamePlayerInfoDbo playerInfoDbo = new PukeGamePlayerInfoDbo();
			playerInfoDbo.setPlayerId(playerId);
			if (playerMingciMap.get(playerId) != null) {
				playerInfoDbo.setMingci(playerMingciMap.get(playerId));
				playerInfoDbo.setNopai(true);
			}
			if (playeGongxianfenMap.get(playerId) != null) {
				playerInfoDbo.setGongxianfen(playeGongxianfenMap.get(playerId));
			}
			if (playeGongxianfenDetalMap.get(playerId) != null) {
				playerInfoDbo.setDetal(playeGongxianfenDetalMap.get(playerId));
			}
			if (playeTotalGongxianfenMap.get(playerId) != null) {
				playerInfoDbo.setTotalGongxianfen(playeTotalGongxianfenMap.get(playerId));
			}
			if (playerMaxXianshuMap.get(playerId) != null) {
				playerInfoDbo.setMaxXianshu(playerMaxXianshuMap.get(playerId));
			}
			if (playerOtherMaxXianshuMap.get(playerId) != null) {
				playerInfoDbo.setOtherMaxXianshu(playerOtherMaxXianshuMap.get(playerId));
			}
			playerInfos.add(playerInfoDbo);
		}
		createTime=System.currentTimeMillis();
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<PukeGamePlayerInfoDbo> getPlayerInfos() {
		return playerInfos;
	}

	public void setPlayerInfos(List<PukeGamePlayerInfoDbo> playerInfos) {
		this.playerInfos = playerInfos;
	}

	public List<String> getChaodiPlayerIdList() {
		return chaodiPlayerIdList;
	}

	public void setChaodiPlayerIdList(List<String> chaodiPlayerIdList) {
		this.chaodiPlayerIdList = chaodiPlayerIdList;
	}

}
