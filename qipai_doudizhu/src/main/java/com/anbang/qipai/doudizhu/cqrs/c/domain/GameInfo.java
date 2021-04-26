package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.List;

import com.dml.puke.pai.PukePai;

public class GameInfo {

	private DoudizhuBeishu beishu;
	private int qiangdizhuCount;
	private List<PukePai> dipaiList = new ArrayList<>();
	private long actionTime;

	public DoudizhuBeishu getBeishu() {
		return beishu;
	}

	public void setBeishu(DoudizhuBeishu beishu) {
		this.beishu = beishu;
	}

	public int getQiangdizhuCount() {
		return qiangdizhuCount;
	}

	public void setQiangdizhuCount(int qiangdizhuCount) {
		this.qiangdizhuCount = qiangdizhuCount;
	}

	public List<PukePai> getDipaiList() {
		return dipaiList;
	}

	public void setDipaiList(List<PukePai> dipaiList) {
		this.dipaiList = dipaiList;
	}

	public long getActionTime() {
		return actionTime;
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
	}

}
