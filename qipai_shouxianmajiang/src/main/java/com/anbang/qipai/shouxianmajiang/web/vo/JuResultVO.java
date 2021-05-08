package com.anbang.qipai.shouxianmajiang.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangJuResult;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGameDbo;


public class JuResultVO {

	private String gameId;
	private String dayingjiaId;
	private String datuhaoId;
	private int panshu;
	private int finishedPanCount;
	private List<ShouxianMajiangJuPlayerResultVO> playerResultList;

	private PanResultVO lastPanResult;
	private long finishTime;

	public JuResultVO(JuResultDbo juResultDbo, MajiangGameDbo majiangGameDbo) {
		gameId = juResultDbo.getGameId();
		ShouxianMajiangJuResult ShouxianMajiangJuResult = juResultDbo.getJuResult();
		dayingjiaId = ShouxianMajiangJuResult.getDayingjiaId();
		datuhaoId = ShouxianMajiangJuResult.getDatuhaoId();
		if (juResultDbo.getLastPanResult() != null) {
			lastPanResult = new PanResultVO(juResultDbo.getLastPanResult(), majiangGameDbo);
		}
		finishTime = juResultDbo.getFinishTime();
		this.panshu = majiangGameDbo.getPanshu();
		finishedPanCount = ShouxianMajiangJuResult.getFinishedPanCount();
		playerResultList = new ArrayList<>();
		if (ShouxianMajiangJuResult.getPlayerResultList() != null) {
			ShouxianMajiangJuResult.getPlayerResultList().forEach((juPlayerResult) -> playerResultList.add(new ShouxianMajiangJuPlayerResultVO(juPlayerResult, majiangGameDbo.findPlayer(juPlayerResult.getPlayerId()))));
		} else {
			majiangGameDbo.getPlayers().forEach((majiangGamePlayerDbo) -> playerResultList.add(new ShouxianMajiangJuPlayerResultVO(majiangGamePlayerDbo)));
		}
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

	public List<ShouxianMajiangJuPlayerResultVO> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<ShouxianMajiangJuPlayerResultVO> playerResultList) {
		this.playerResultList = playerResultList;
	}

	public PanResultVO getLastPanResult() {
		return lastPanResult;
	}

	public void setLastPanResult(PanResultVO lastPanResult) {
		this.lastPanResult = lastPanResult;
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

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

}
