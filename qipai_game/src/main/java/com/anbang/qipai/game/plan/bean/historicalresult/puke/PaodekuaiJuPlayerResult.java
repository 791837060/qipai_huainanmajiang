package com.anbang.qipai.game.plan.bean.historicalresult.puke;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;

import java.util.Map;

public class PaodekuaiJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int danguanCount;
	private int shuangguanCount;
	private int boomCount;
	private double maxScore;
	private double totalScore;

	public PaodekuaiJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.danguanCount = ((Double) juPlayerResult.get("danguanCount")).intValue();
		this.shuangguanCount = ((Double) juPlayerResult.get("shuangguanCount")).intValue();
		this.boomCount = ((Double) juPlayerResult.get("boomCount")).intValue();
		this.maxScore = ((Double) juPlayerResult.get("maxScore"));
		this.totalScore = ((Double) juPlayerResult.get("totalScore"));
	}

	public PaodekuaiJuPlayerResult() {

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

	public int getDanguanCount() {
		return danguanCount;
	}

	public void setDanguanCount(int danguanCount) {
		this.danguanCount = danguanCount;
	}

	public int getShuangguanCount() {
		return shuangguanCount;
	}

	public void setShuangguanCount(int shuangguanCount) {
		this.shuangguanCount = shuangguanCount;
	}

	public int getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(int boomCount) {
		this.boomCount = boomCount;
	}

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
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
