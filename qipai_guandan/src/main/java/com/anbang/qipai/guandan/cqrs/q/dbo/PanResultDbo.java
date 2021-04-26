package com.anbang.qipai.guandan.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanPlayerResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanResult;
import com.dml.shuangkou.pan.PanActionFrame;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({ @CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}") })

public class PanResultDbo {
	private String id;
	private String gameId;
	private int panNo;
	private boolean chaodi;
	private List<GuandanPanPlayerResultDbo> playerResultList;
	private long finishTime;
	private PanActionFrame panActionFrame;
	private PukeGameInfoDbo pukeGameInfoDbo;

	public PanResultDbo() {
	}

	public PanResultDbo(String gameId, GuandanPanResult panResult) {
		this.gameId = gameId;
		panNo = panResult.getPan().getNo();
		this.chaodi = panResult.isChaodi();
		playerResultList = new ArrayList<>();
		for (GuandanPanPlayerResult playerResult : panResult.getPanPlayerResultList()) {
			GuandanPanPlayerResultDbo dbo = new GuandanPanPlayerResultDbo();
			dbo.setPlayerId(playerResult.getPlayerId());
			dbo.setPlayerResult(playerResult);
			dbo.setPlayer(panResult.findPlayer(playerResult.getPlayerId()));
			playerResultList.add(dbo);
		}
		finishTime = panResult.getPanFinishTime();
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

	public boolean isChaodi() {
		return chaodi;
	}

	public void setChaodi(boolean chaodi) {
		this.chaodi = chaodi;
	}

	public List<GuandanPanPlayerResultDbo> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<GuandanPanPlayerResultDbo> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanActionFrame getPanActionFrame() {
		return panActionFrame;
	}

	public void setPanActionFrame(PanActionFrame panActionFrame) {
		this.panActionFrame = panActionFrame;
	}

	public PukeGameInfoDbo getPukeGameInfoDbo() {
		return pukeGameInfoDbo;
	}

	public void setPukeGameInfoDbo(PukeGameInfoDbo pukeGameInfoDbo) {
		this.pukeGameInfoDbo = pukeGameInfoDbo;
	}

}
