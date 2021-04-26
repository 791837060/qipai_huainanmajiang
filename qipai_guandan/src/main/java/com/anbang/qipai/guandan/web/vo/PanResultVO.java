package com.anbang.qipai.guandan.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.guandan.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.GuandanPanPlayerResultDbo;

public class PanResultVO {

	private List<GuandanPanPlayerResultVO> playerResultList;

	private boolean chaodi;

	private int panNo;

	private long finishTime;

	private PanActionFrameVO lastPanActionFrame;

	private GameInfoVO gameInfoVO;

	public PanResultVO() {

	}

	public PanResultVO(PanResultDbo panResultDbo, PukeGameDbo pukeGameDbo) {
		List<GuandanPanPlayerResultDbo> list = panResultDbo.getPlayerResultList();
		playerResultList = new ArrayList<>();
		if (list != null) {
			list.forEach((panPlayerResult) -> {
				playerResultList.add(new GuandanPanPlayerResultVO(pukeGameDbo.findPlayer(panPlayerResult.getPlayerId()), panPlayerResult));
			});
		}
		chaodi = panResultDbo.isChaodi();
		panNo = panResultDbo.getPanNo();
		finishTime = panResultDbo.getFinishTime();
		lastPanActionFrame = new PanActionFrameVO(panResultDbo.getPanActionFrame());
		gameInfoVO = new GameInfoVO(panResultDbo.getPukeGameInfoDbo());
	}

	public List<GuandanPanPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<GuandanPanPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public boolean isChaodi() {
		return chaodi;
	}

	public void setChaodi(boolean chaodi) {
		this.chaodi = chaodi;
	}

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public PanActionFrameVO getLastPanActionFrame() {
		return lastPanActionFrame;
	}

	public void setLastPanActionFrame(PanActionFrameVO lastPanActionFrame) {
		this.lastPanActionFrame = lastPanActionFrame;
	}

	public GameInfoVO getGameInfoVO() {
		return gameInfoVO;
	}

	public void setGameInfoVO(GameInfoVO gameInfoVO) {
		this.gameInfoVO = gameInfoVO;
	}

}
