package com.anbang.qipai.guandan.cqrs.q.dbo;

import com.anbang.qipai.guandan.cqrs.c.domain.GuandanGongxianFen;

public class PukeGamePlayerInfoDbo {
	private String playerId;
	private int gongxianfen;
	private int detal;
	private int maxXianshu;
	private int otherMaxXianshu;
	private GuandanGongxianFen totalGongxianfen;
	private boolean nopai;
	private int mingci;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getGongxianfen() {
		return gongxianfen;
	}

	public void setGongxianfen(int gongxianfen) {
		this.gongxianfen = gongxianfen;
	}

	public int getMaxXianshu() {
		return maxXianshu;
	}

	public void setMaxXianshu(int maxXianshu) {
		this.maxXianshu = maxXianshu;
	}

	public int getOtherMaxXianshu() {
		return otherMaxXianshu;
	}

	public void setOtherMaxXianshu(int otherMaxXianshu) {
		this.otherMaxXianshu = otherMaxXianshu;
	}

	public GuandanGongxianFen getTotalGongxianfen() {
		return totalGongxianfen;
	}

	public void setTotalGongxianfen(GuandanGongxianFen totalGongxianfen) {
		this.totalGongxianfen = totalGongxianfen;
	}

	public boolean isNopai() {
		return nopai;
	}

	public void setNopai(boolean nopai) {
		this.nopai = nopai;
	}

	public int getMingci() {
		return mingci;
	}

	public void setMingci(int mingci) {
		this.mingci = mingci;
	}

	public int getDetal() {
		return detal;
	}

	public void setDetal(int detal) {
		this.detal = detal;
	}

}
