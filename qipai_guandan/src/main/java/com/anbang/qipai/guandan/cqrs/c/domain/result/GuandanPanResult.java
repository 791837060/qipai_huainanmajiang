package com.anbang.qipai.guandan.cqrs.c.domain.result;

import java.util.List;

import com.dml.shuangkou.pan.PanResult;

public class GuandanPanResult extends PanResult {
	private boolean chaodi;
	private List<GuandanPanPlayerResult> panPlayerResultList;

	public boolean isChaodi() {
		return chaodi;
	}

	public void setChaodi(boolean chaodi) {
		this.chaodi = chaodi;
	}

	public List<GuandanPanPlayerResult> getPanPlayerResultList() {
		return panPlayerResultList;
	}

	public void setPanPlayerResultList(List<GuandanPanPlayerResult> panPlayerResultList) {
		this.panPlayerResultList = panPlayerResultList;
	}

}
