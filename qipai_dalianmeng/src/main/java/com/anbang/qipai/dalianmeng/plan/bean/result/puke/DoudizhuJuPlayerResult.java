package com.anbang.qipai.dalianmeng.plan.bean.result.puke;

import com.anbang.qipai.dalianmeng.plan.bean.result.GameJuPlayerResult;

import java.util.Map;

public class DoudizhuJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int yingCount;
	private int fanchuntianCount;
	private int chuntianCount;
	private int maxBeishu;
	private double totalScore;

	public DoudizhuJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.yingCount = ((Double) juPlayerResult.get("yingCount")).intValue();
		this.fanchuntianCount = ((Double) juPlayerResult.get("fanchuntianCount")).intValue();
		this.chuntianCount = ((Double) juPlayerResult.get("chuntianCount")).intValue();
		this.maxBeishu = ((Double) juPlayerResult.get("maxBeishu")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore"));
	}

	public DoudizhuJuPlayerResult() {

	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getYingCount() {
		return yingCount;
	}

	public void setYingCount(int yingCount) {
		this.yingCount = yingCount;
	}

	public int getFanchuntianCount() {
		return fanchuntianCount;
	}

	public void setFanchuntianCount(int fanchuntianCount) {
		this.fanchuntianCount = fanchuntianCount;
	}

	public int getChuntianCount() {
		return chuntianCount;
	}

	public void setChuntianCount(int chuntianCount) {
		this.chuntianCount = chuntianCount;
	}

	public int getMaxBeishu() {
		return maxBeishu;
	}

	public void setMaxBeishu(int maxBeishu) {
		this.maxBeishu = maxBeishu;
	}

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
	public String playerId() {
		return playerId;
	}

	@Override
	public String nickname() {
		return nickname;
	}

	@Override
	public String headimgurl() {
		return headimgurl;
	}

	@Override
	public double totalScore() {
		return totalScore;
	}
}
