package com.dml.shisanshui.pai;

import java.util.ArrayList;
import java.util.List;

public class PaiListValueObject {
	private List<PukePai> paiList;
	private int paiCount;

	public PaiListValueObject() {
	}

	public PaiListValueObject(List<PukePai> avaliablePaiList) {
		paiList = new ArrayList<>(avaliablePaiList);
		paiCount = avaliablePaiList.size();
	}

	public List<PukePai> getPaiList() {
		return paiList;
	}

	public void setPaiList(List<PukePai> paiList) {
		this.paiList = paiList;
	}

	public int getPaiCount() {
		return paiCount;
	}

	public void setPaiCount(int paiCount) {
		this.paiCount = paiCount;
	}

}
