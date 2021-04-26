package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.init.XianshuCalculatorHelper;

/**
 * 贡献分
 * 
 * @author lsc
 *
 */
public class GuandanGongxianFen {
	private int sixian;
	private int wuxian;
	private int liuxian;
	private int qixian;
	private int baxian;
	private int jiuxian;
	private int shixian;
	private int shiyixian;
	private int shierxian;
	private int totalscore;// 总分
	private int value;// 单人结算分

	public GuandanGongxianFen() {

	}

	public GuandanGongxianFen(int[] xianshuCount) {
		if (xianshuCount == null) {
			xianshuCount = new int[9];
		}
		sixian = xianshuCount[0];
		wuxian = xianshuCount[1];
		liuxian = xianshuCount[2];
		qixian = xianshuCount[3];
		baxian = xianshuCount[4];
		jiuxian = xianshuCount[5];
		shixian = xianshuCount[6];
		shiyixian = xianshuCount[7];
		shierxian = xianshuCount[8];
	}

	public void calculateXianshu(boolean gxjb) {
		String key = "" + sixian + wuxian + liuxian + qixian + baxian + jiuxian + shixian + shiyixian + shierxian;
		Integer score = XianshuCalculatorHelper.getGongxianFenCountMap().get(key);
		if (score != null) {
			value = score;
		}
		if (gxjb) {
			value /= 2;
		}
	}

	public void calculate(int renshu) {
		totalscore = value * (renshu - 1);
	}

	public int jiesuan(int delta) {
		return totalscore += delta;
	}

	public int getSixian() {
		return sixian;
	}

	public void setSixian(int sixian) {
		this.sixian = sixian;
	}

	public int getWuxian() {
		return wuxian;
	}

	public void setWuxian(int wuxian) {
		this.wuxian = wuxian;
	}

	public int getLiuxian() {
		return liuxian;
	}

	public void setLiuxian(int liuxian) {
		this.liuxian = liuxian;
	}

	public int getQixian() {
		return qixian;
	}

	public void setQixian(int qixian) {
		this.qixian = qixian;
	}

	public int getBaxian() {
		return baxian;
	}

	public void setBaxian(int baxian) {
		this.baxian = baxian;
	}

	public int getJiuxian() {
		return jiuxian;
	}

	public void setJiuxian(int jiuxian) {
		this.jiuxian = jiuxian;
	}

	public int getShixian() {
		return shixian;
	}

	public void setShixian(int shixian) {
		this.shixian = shixian;
	}

	public int getShiyixian() {
		return shiyixian;
	}

	public void setShiyixian(int shiyixian) {
		this.shiyixian = shiyixian;
	}

	public int getShierxian() {
		return shierxian;
	}

	public void setShierxian(int shierxian) {
		this.shierxian = shierxian;
	}

	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
