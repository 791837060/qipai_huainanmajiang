package com.anbang.qipai.biji.cqrs.c.domain.result;

import java.util.List;

import com.dml.shisanshui.ju.JuResult;

public class BijiJuResult implements JuResult {
	private int finishedPanCount;

	private List<BijiJuPlayerResult> playerResultList;

	private String dayingjiaId;

	private String datuhaoId;

	public int getFinishedPanCount() {
		return finishedPanCount;
	}

	public void setFinishedPanCount(int finishedPanCount) {
		this.finishedPanCount = finishedPanCount;
	}

	public List<BijiJuPlayerResult> getPlayerResultList() {
		return playerResultList;
	}

	public void setPlayerResultList(List<BijiJuPlayerResult> playerResultList) {
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
