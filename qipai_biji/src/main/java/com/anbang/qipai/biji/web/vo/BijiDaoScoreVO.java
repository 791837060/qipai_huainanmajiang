package com.anbang.qipai.biji.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiDaoScore;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;

public class BijiDaoScoreVO {

	private Paixing paixing;

	private List<PukePai> pukePaiList = new ArrayList<>();

	private double score;

	private long typecode;

	public BijiDaoScoreVO() {

	}

	public BijiDaoScoreVO(BijiDaoScore score, Dao dao) {
		paixing = dao.getPaixing();
		pukePaiList = dao.getPukePaiList();
		typecode = dao.getTypeCode();
		this.score = score.getScore();
	}

	public Paixing getPaixing() {
		return paixing;
	}

	public void setPaixing(Paixing paixing) {
		this.paixing = paixing;
	}

	public List<PukePai> getPukePaiList() {
		return pukePaiList;
	}

	public void setPukePaiList(List<PukePai> pukePaiList) {
		this.pukePaiList = pukePaiList;
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
