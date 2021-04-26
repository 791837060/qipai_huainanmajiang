package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.dml.doudizhu.pan.PanActionFrame;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({ @CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}") })
public class PanResultDbo {
	private String id;
	private String gameId;
	private int panNo;
	private boolean dizhuying;
	private String yingjiaPlayerId;// 赢家id
	private List<DoudizhuPanPlayerResultDbo> playerResultList;
	private long finishTime;
	private PanActionFrame panActionFrame;
	private GameLatestInfoDbo gameLatestInfoDbo;

	public PanResultDbo() {

	}

	public PanResultDbo(String gameId, DoudizhuPanResult panResult) {
		this.gameId = gameId;
		panNo = panResult.getPan().getNo();
		dizhuying = panResult.isDizhuying();
		yingjiaPlayerId = panResult.getYingjiaPlayerId();
		playerResultList = new ArrayList<>();
		for (DoudizhuPanPlayerResult playerResult : panResult.getPanPlayerResultList()) {
			DoudizhuPanPlayerResultDbo dbo = new DoudizhuPanPlayerResultDbo();
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

	public boolean isDizhuying() {
		return dizhuying;
	}

	public void setDizhuying(boolean dizhuying) {
		this.dizhuying = dizhuying;
	}

	public List<DoudizhuPanPlayerResultDbo> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuPanPlayerResultDbo> playerResultList) {
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

	public GameLatestInfoDbo getGameLatestInfoDbo() {
		return gameLatestInfoDbo;
	}

	public void setGameLatestInfoDbo(GameLatestInfoDbo gameLatestInfoDbo) {
		this.gameLatestInfoDbo = gameLatestInfoDbo;
	}

	public String getYingjiaPlayerId() {
		return yingjiaPlayerId;
	}

	public void setYingjiaPlayerId(String yingjiaPlayerId) {
		this.yingjiaPlayerId = yingjiaPlayerId;
	}

}
