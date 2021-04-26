package com.anbang.qipai.admin.plan.bean.historicalresult.majiang;

import com.anbang.qipai.admin.plan.bean.historicalresult.GameJuPlayerResult;

import java.util.Map;

public class YizhengMajiangJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private int caishenCount;
	private double totalScore;
	private int zimoCount;
	private int fangPaoCount;

	public YizhengMajiangJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.huCount = ((Double) juPlayerResult.get("huCount")).intValue();
		this.caishenCount = ((Double) juPlayerResult.get("caishenCount")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore"));
		this.zimoCount = ((Double) juPlayerResult.get("zimoCount")).intValue();
		this.fangPaoCount = ((Double) juPlayerResult.get("fangPaoCount")).intValue();
	}

	public YizhengMajiangJuPlayerResult() {

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

	public int getHuCount() {
		return huCount;
	}

	public void setHuCount(int huCount) {
		this.huCount = huCount;
	}

	public int getCaishenCount() {
		return caishenCount;
	}

	public void setCaishenCount(int caishenCount) {
		this.caishenCount = caishenCount;
	}

	public int getZimoCount() {
		return zimoCount;
	}

	public void setZimoCount(int zimoCount) {
		this.zimoCount = zimoCount;
	}

	public int getFangPaoCount() {
		return fangPaoCount;
	}

	public void setFangPaoCount(int fangPaoCount) {
		this.fangPaoCount = fangPaoCount;
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
}
