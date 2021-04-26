package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.dml.doudizhu.pan.PanActionFrame;

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
