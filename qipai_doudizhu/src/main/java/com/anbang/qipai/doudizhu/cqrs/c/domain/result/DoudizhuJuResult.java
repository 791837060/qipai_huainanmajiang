package com.anbang.qipai.doudizhu.cqrs.c.domain.result;

import java.util.List;

import com.dml.doudizhu.ju.JuResult;

public class DoudizhuJuResult implements JuResult {
	private int finishedPanCount;

	private List<DoudizhuJuPlayerResult> playerResultList;

	private String dayingjiaId;

	private String datuhaoId;

	public int getFinishedPanCount() {
		return finishedPanCount;
	}

	public void setFinishedPanCount(int finishedPanCount) {
		this.finishedPanCount = finishedPanCount;
	}

	public List<DoudizhuJuPlayerResult> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<DoudizhuJuPlayerResult> playerResultList) {
		this.playerResultList = playerResultList;
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

}
