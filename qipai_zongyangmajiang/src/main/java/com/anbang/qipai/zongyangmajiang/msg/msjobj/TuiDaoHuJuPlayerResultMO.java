package com.anbang.qipai.zongyangmajiang.msg.msjobj;

import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.ZongyangMajiangJuPlayerResult;
import com.anbang.qipai.zongyangmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

public class TuiDaoHuJuPlayerResultMO {

	private String playerId;
	private String nickname;
	private String headimgurl;
	private int huCount;
	private double totalScore;

	public TuiDaoHuJuPlayerResultMO(ZongyangMajiangJuPlayerResult juPlayerResult, MajiangGamePlayerDbo majiangGamePlayerDbo) {
		playerId = majiangGamePlayerDbo.getPlayerId();
		nickname = majiangGamePlayerDbo.getNickname();
		headimgurl = majiangGamePlayerDbo.getHeadimgurl();
		huCount = juPlayerResult.getHuCount();
		totalScore = juPlayerResult.getTotalScore();
	}

	public TuiDaoHuJuPlayerResultMO(MajiangGamePlayerDbo majiangGamePlayerDbo) {
		playerId = majiangGamePlayerDbo.getPlayerId();
		nickname = majiangGamePlayerDbo.getNickname();
		headimgurl = majiangGamePlayerDbo.getHeadimgurl();
		huCount = 0;
		totalScore = 0;
	}

	public TuiDaoHuJuPlayerResultMO() {

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
