package com.anbang.qipai.biji.cqrs.c.domain.result;

import java.util.List;

import com.dml.shisanshui.pan.PanResult;

public class BijiPanResult extends PanResult {

	private List<BijiPanPlayerResult> panPlayerResultList;

	public List<BijiPanPlayerResult> getPanPlayerResultList() {
		return panPlayerResultList;
	}

	public void setPanPlayerResultList(List<BijiPanPlayerResult> panPlayerResultList) {
		this.panPlayerResultList = panPlayerResultList;
	}

}
