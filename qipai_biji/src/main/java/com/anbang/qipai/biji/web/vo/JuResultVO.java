package com.anbang.qipai.biji.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuResult;
import com.anbang.qipai.biji.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.biji.cqrs.q.dbo.PukeGameDbo;

public class JuResultVO {
	private String gameId;
	private String dayingjiaId;
	private String datuhaoId;
	private int panshu;
	private int finishedPanCount;
	private List<BijiJuPlayerResultVO> playerResultList;
	private PanResultVO lastPanResult;
	private long finishTime;

	public JuResultVO() {

	}

	public JuResultVO(JuResultDbo juResultDbo, PukeGameDbo pukeGameDbo) {
		gameId = juResultDbo.getGameId();
		BijiJuResult juResult = juResultDbo.getJuResult();
		dayingjiaId = juResult.getDayingjiaId();
		datuhaoId = juResult.getDatuhaoId();
		if (juResultDbo.getLastPanResult() != null) {
			lastPanResult = new PanResultVO(juResultDbo.getLastPanResult(), pukeGameDbo);
		}
		finishTime = juResultDbo.getFinishTime();
		this.panshu = pukeGameDbo.getPanshu();
		finishedPanCount = juResult.getFinishedPanCount();
		playerResultList = new ArrayList<>();
		if (juResult.getPlayerResultList() != null) {
			juResult.getPlayerResultList().forEach((juPlayerResult) -> playerResultList.add(new BijiJuPlayerResultVO(juPlayerResult, pukeGameDbo.findPlayer(juPlayerResult.getPlayerId()))));
		} else {
			pukeGameDbo.getPlayers().forEach((pukeGamePlayerDbo) -> playerResultList.add(new BijiJuPlayerResultVO(pukeGamePlayerDbo)));
		}
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getDayingjiaId() {
		return dayingjiaId;
	}

	public void setDayingjiaId(String dayingjiaId) {
		this.dayingjiaId = dayingjiaId;
	}

	public String getDatuhaoId() {
		return datuhaoId;
	}

	public void setDatuhaoId(String datuhaoId) {
		this.datuhaoId = datuhaoId;
	}

	public int getPanshu() {
		return panshu;
	}

	public void setPanshu(int panshu) {
		this.panshu = panshu;
	}

	public int getFinishedPanCount() {
		return finishedPanCount;
	}

	public void setFinishedPanCount(int finishedPanCount) {
		this.finishedPanCount = finishedPanCount;
	}

	public List<BijiJuPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<BijiJuPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public PanResultVO getLastPanResult() {
		return lastPanResult;
	}

	public void setLastPanResult(PanResultVO lastPanResult) {
		this.lastPanResult = lastPanResult;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

}
