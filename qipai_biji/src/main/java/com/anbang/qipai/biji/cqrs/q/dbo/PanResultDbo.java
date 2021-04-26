package com.anbang.qipai.biji.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanResult;
import com.dml.shisanshui.pan.PanActionFrame;

@Document
@CompoundIndexes({ @CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}") })
public class PanResultDbo {
	private String id;
	private String gameId;
	private int panNo;
	private List<BijiPanPlayerResultDbo> playerResultList;
	private long finishTime;
	private PanActionFrame panActionFrame;

	public PanResultDbo() {

	}

	public PanResultDbo(String gameId, BijiPanResult panResult) {
		this.gameId = gameId;
		panNo = panResult.getPan().getNo();
		playerResultList = new ArrayList<>();
		for (BijiPanPlayerResult playerResult : panResult.getPanPlayerResultList()) {
			BijiPanPlayerResultDbo dbo = new BijiPanPlayerResultDbo();
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

	public List<BijiPanPlayerResultDbo> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<BijiPanPlayerResultDbo> playerResultList) {
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

}
