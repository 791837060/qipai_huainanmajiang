package com.dml.puke.pai;

/**
 * 组队亮牌标记
 * 
 * @author lsc
 *
 */
public class ZuDuiLiangPaiMark implements PukePaiMark {
	private String name = "zuduiLiangPai";

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
