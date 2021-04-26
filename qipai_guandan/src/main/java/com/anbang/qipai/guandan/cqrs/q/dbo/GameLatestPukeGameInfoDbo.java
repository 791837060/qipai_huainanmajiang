package com.anbang.qipai.guandan.cqrs.q.dbo;

public class GameLatestPukeGameInfoDbo {
	private String id;// 就是gameid
	private PukeGameInfoDbo pukeGameInfoDbo;
	private long createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PukeGameInfoDbo getPukeGameInfoDbo() {
		return pukeGameInfoDbo;
	}

	public void setPukeGameInfoDbo(PukeGameInfoDbo pukeGameInfoDbo) {
		this.pukeGameInfoDbo = pukeGameInfoDbo;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
