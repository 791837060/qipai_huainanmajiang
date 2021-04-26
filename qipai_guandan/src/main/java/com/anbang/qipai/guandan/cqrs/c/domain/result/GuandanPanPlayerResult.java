package com.anbang.qipai.guandan.cqrs.c.domain.result;

import com.anbang.qipai.guandan.cqrs.c.domain.GuandanChaixianbufen;
import com.anbang.qipai.guandan.cqrs.c.domain.GuandanGongxianFen;
import com.anbang.qipai.guandan.cqrs.c.domain.GuandanMingcifen;

public class GuandanPanPlayerResult {
	private String playerId;
	private boolean chaodi;
	private GuandanMingcifen mingcifen;
//	private int xianshubeishu;
//	private GuandanGongxianFen gongxianfen;
//	private GuandanChaixianbufen bufen;
	private double score;					//一盘结算分
	private double totalScore;				//总分

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public boolean isChaodi() {
		return chaodi;
	}

	public void setChaodi(boolean chaodi) {
		this.chaodi = chaodi;
	}

	public GuandanMingcifen getMingcifen() {
		return mingcifen;
	}

	public void setMingcifen(GuandanMingcifen mingcifen) {
		this.mingcifen = mingcifen;
	}
//
//	public int getXianshubeishu() {
//		return xianshubeishu;
//	}
//
//	public void setXianshubeishu(int xianshubeishu) {
//		this.xianshubeishu = xianshubeishu;
//	}
//
//	public GuandanGongxianFen getGongxianfen() {
//		return gongxianfen;
//	}
//
//	public void setGongxianfen(GuandanGongxianFen gongxianfen) {
//		this.gongxianfen = gongxianfen;
//	}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

//    public GuandanChaixianbufen getBufen() {
//		return bufen;
//	}
//
//	public void setBufen(GuandanChaixianbufen bufen) {
//		this.bufen = bufen;
//	}

}
