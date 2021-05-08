package com.anbang.qipai.shouxianmajiang.msg.msjobj;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangJuPlayerResult;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangJuPlayerResult;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

public class ShouxianMajiangJuPlayerResultMO {

	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private double totalScore;

	public ShouxianMajiangJuPlayerResultMO(ShouxianMajiangJuPlayerResult juPlayerResult, MajiangGamePlayerDbo majiangGamePlayerDbo) {
		playerId = majiangGamePlayerDbo.getPlayerId();
		nickname = majiangGamePlayerDbo.getNickname();
		headimgurl = majiangGamePlayerDbo.getHeadimgurl();
		huCount = juPlayerResult.getHuCount();
		totalScore = juPlayerResult.getTotalScore();
	}

	public ShouxianMajiangJuPlayerResultMO(MajiangGamePlayerDbo majiangGamePlayerDbo) {
		playerId = majiangGamePlayerDbo.getPlayerId();
		nickname = majiangGamePlayerDbo.getNickname();
		headimgurl = majiangGamePlayerDbo.getHeadimgurl();
		huCount = 0;
		totalScore = 0;
	}

	public ShouxianMajiangJuPlayerResultMO() {

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

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

}
