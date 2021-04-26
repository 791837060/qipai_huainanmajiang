package com.anbang.qipai.biji.cqrs.c.domain.result;

import com.dml.shisanshui.pai.paixing.Paixing;

/**
 * 每道结算分
 * 
 * @author lsc
 *
 */
public class BijiDaoScore {
	private Paixing paixing;
	private double score;
	private long typecode;

	public void jiesaunScore(double detal) {
		score += detal;
	}

	public Paixing getPaixing() {
		return paixing;
	}

	public void setPaixing(Paixing paixing) {
		this.paixing = paixing;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getTypecode() {
		return typecode;
	}

	public void setTypecode(long typecode) {
		this.typecode = typecode;
	}
}
