package com.anbang.qipai.guandan.web.vo;

import com.anbang.qipai.guandan.cqrs.c.domain.GuandanMingcifen;

public class GuandanMingcifenVO {
	private int mingci;
	private boolean ying;
	private boolean shuangkou;
	private boolean dankou;
	private boolean pingkou;
	private int value;

	public GuandanMingcifenVO() {

	}

	public GuandanMingcifenVO(GuandanMingcifen mingcifen) {
		mingci = mingcifen.getMingci();
		ying = mingcifen.isYing();
		if (mingcifen.isShuangkou()) {
			shuangkou = true;
		} else if (mingcifen.isDankou()) {
			dankou = true;
		} else if (mingcifen.isPingkou()) {
			pingkou = true;
		}
		value = mingcifen.getValue();
	}

	public int getMingci() {
		return mingci;
	}

	public void setMingci(int mingci) {
		this.mingci = mingci;
	}

	public boolean isYing() {
		return ying;
	}

	public void setYing(boolean ying) {
		this.ying = ying;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isShuangkou() {
		return shuangkou;
	}

	public void setShuangkou(boolean shuangkou) {
		this.shuangkou = shuangkou;
	}

	public boolean isDankou() {
		return dankou;
	}

	public void setDankou(boolean dankou) {
		this.dankou = dankou;
	}

	public boolean isPingkou() {
		return pingkou;
	}

	public void setPingkou(boolean pingkou) {
		this.pingkou = pingkou;
	}

}
