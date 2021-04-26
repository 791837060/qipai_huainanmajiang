package com.anbang.qipai.admin.plan.bean.historicalresult.majiang;

import com.anbang.qipai.admin.plan.bean.historicalresult.GameJuPlayerResult;

import java.util.Map;

public class GuandanJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int shuangkouCount;
	private int dankouCount;
	private int pingkouCount;
	private double totalScore;

	public GuandanJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.shuangkouCount = ((Double) juPlayerResult.get("shuangkouCount")).intValue();
		this.dankouCount = ((Double) juPlayerResult.get("dankouCount")).intValue();
		this.pingkouCount = ((Double) juPlayerResult.get("pingkouCount")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore"));
	}

	public GuandanJuPlayerResult() {

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

	public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

	public String playerId() {
		return playerId;
	}

	public String nickname() {
		return nickname;
	}

	public String headimgurl() {
		return headimgurl;
	}

	public double totalScore() {
		return totalScore;
	}

	public int getShuangkouCount() {
		return shuangkouCount;
	}

	public void setShuangkouCount(int shuangkouCount) {
		this.shuangkouCount = shuangkouCount;
	}

	public int getDankouCount() {
		return dankouCount;
	}

	public void setDankouCount(int dankouCount) {
		this.dankouCount = dankouCount;
	}

	public int getPingkouCount() {
		return pingkouCount;
	}

	public void setPingkouCount(int pingkouCount) {
		this.pingkouCount = pingkouCount;
	}
}
