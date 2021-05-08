package com.anbang.qipai.game.plan.bean.historicalresult.majiang;

import com.anbang.qipai.game.plan.bean.historicalresult.GameJuPlayerResult;

import java.util.Map;

public class ShouxianMajiangJuPlayerResult implements GameJuPlayerResult {
	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private double totalScore;
//	private int caishenCount;
//	private int zimoCount;
//	private int fangPaoCount;

	public ShouxianMajiangJuPlayerResult(Map juPlayerResult) {
		this.playerId = (String) juPlayerResult.get("playerId");
		this.nickname = (String) juPlayerResult.get("nickname");
		this.headimgurl = (String) juPlayerResult.get("headimgurl");
		this.huCount = ((Double) juPlayerResult.get("huCount")).intValue();
		this.totalScore = ((Double) juPlayerResult.get("totalScore"));
//		this.caishenCount = ((Double) juPlayerResult.get("caishenCount")).intValue();
//		this.zimoCount = ((Double) juPlayerResult.get("zimoCount")).intValue();
//		this.fangPaoCount = ((Double) juPlayerResult.get("fangPaoCount")).intValue();
	}

	public ShouxianMajiangJuPlayerResult() {

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

//	public int getCaishenCount() {
//		return caishenCount;
//	}
//
//	public void setCaishenCount(int caishenCount) {
//		this.caishenCount = caishenCount;
//	}
//
//	public int getZimoCount() {
//		return zimoCount;
//	}
//
//	public void setZimoCount(int zimoCount) {
//		this.zimoCount = zimoCount;
//	}
//
//	public int getFangPaoCount() {
//		return fangPaoCount;
//	}
//
//	public void setFangPaoCount(int fangPaoCount) {
//		this.fangPaoCount = fangPaoCount;
//	}

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
