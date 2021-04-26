package com.dml.puke.pai;

/**
 * 一张牌的对象化
 * 
 * @author Neo
 *
 */
public class PukePai {

	private int id;
	private PukePaiMian paiMian;
	private PukePaiMark mark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PukePaiMian getPaiMian() {
		return paiMian;
	}

	public void setPaiMian(PukePaiMian paiMian) {
		this.paiMian = paiMian;
	}

	public PukePaiMark getMark() {
		return mark;
	}

	public void setMark(PukePaiMark mark) {
		this.mark = mark;
	}

}
