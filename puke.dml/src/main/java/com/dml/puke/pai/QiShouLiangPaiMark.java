package com.dml.puke.pai;

/**
 * 起手亮牌标记
 * 
 * @author lsc
 *
 */
public class QiShouLiangPaiMark implements PukePaiMark {
	private String name = "qishouLiangPai";

	@Override
	public String name() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
