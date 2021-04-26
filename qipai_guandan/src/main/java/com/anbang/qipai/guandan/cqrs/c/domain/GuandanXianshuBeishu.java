package com.anbang.qipai.guandan.cqrs.c.domain;

import com.anbang.qipai.guandan.init.XianshuCalculatorHelper;

public class GuandanXianshuBeishu {
	private int sixian;
	private int wuxian;
	private int liuxian;
	private int qixian;
	private int baxian;
	private int jiuxian;
	private int shixian;
	private int shiyixian;
	private int shierxian;
	private int value;// 单人线数倍数

	public GuandanXianshuBeishu() {

	}

	public GuandanXianshuBeishu(int[] xianshuCount) {
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

	public void calculate(boolean bxfd, boolean jxfd, boolean sxfd) {
		int beishu = 1;
		String key = "" + sixian + wuxian + liuxian + qixian + baxian + jiuxian + shixian + shiyixian + shierxian;
		Integer score = XianshuCalculatorHelper.getXianshuCountMap().get(key);
		if (score != null && score > 4) {
			if (sxfd && score > 10) {
				beishu = 2 << 5;
			} else if (jxfd && score > 9) {
				beishu = 2 << 4;
			} else if (bxfd && score > 8) {
				beishu = 2 << 3;
			} else {
				beishu = 2 << (score - 5);
			}
		}
		value = beishu;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
