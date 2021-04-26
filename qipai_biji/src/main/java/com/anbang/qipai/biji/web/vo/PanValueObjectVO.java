package com.anbang.qipai.biji.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.dml.shisanshui.pai.PaiListValueObject;
import com.dml.shisanshui.pan.PanValueObject;

public class PanValueObjectVO {
	private int no;
	private List<BijiPlayerValueObjectVO> bijiPlayerList;
	private PaiListValueObject paiListValueObject;

	public PanValueObjectVO() {

	}

	public PanValueObjectVO(PanValueObject panValueObject) {
		no = panValueObject.getNo();
		bijiPlayerList = new ArrayList<>();
		panValueObject.getPlayerList().forEach((doudizhuPlayer) -> bijiPlayerList.add(new BijiPlayerValueObjectVO(doudizhuPlayer)));
		paiListValueObject = panValueObject.getPaiListValueObject();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public List<BijiPlayerValueObjectVO> getBijiPlayerList() {
		return bijiPlayerList;
	}

	public void setBijiPlayerList(List<BijiPlayerValueObjectVO> bijiPlayerList) {
		this.bijiPlayerList = bijiPlayerList;
	}

	public PaiListValueObject getPaiListValueObject() {
		return paiListValueObject;
	}

	public void setPaiListValueObject(PaiListValueObject paiListValueObject) {
		this.paiListValueObject = paiListValueObject;
	}

}
