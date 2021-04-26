package com.anbang.qipai.guandan.cqrs.q.dbo;

import com.dml.shuangkou.pan.PanActionFrame;

public class GameLatestPanActionFrameDbo {
	private String id;// 就是gameid
	private PanActionFrame panActionFrame;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PanActionFrame getPanActionFrame() {
		return panActionFrame;
	}

	public void setPanActionFrame(PanActionFrame panActionFrame) {
		this.panActionFrame = panActionFrame;
	}

}
