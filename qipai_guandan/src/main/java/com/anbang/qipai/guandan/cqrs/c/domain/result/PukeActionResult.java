package com.anbang.qipai.guandan.cqrs.c.domain.result;

import com.anbang.qipai.guandan.cqrs.c.domain.PukeGameValueObject;
import com.dml.shuangkou.pan.PanActionFrame;

public class PukeActionResult {
	private PukeGameValueObject pukeGame;
	private PanActionFrame panActionFrame;
	private GuandanPanResult panResult;
	private GuandanJuResult juResult;

	public PukeGameValueObject getPukeGame() {
		return pukeGame;
	}

	public void setPukeGame(PukeGameValueObject pukeGame) {
		this.pukeGame = pukeGame;
	}

	public PanActionFrame getPanActionFrame() {
		return panActionFrame;
	}

	public void setPanActionFrame(PanActionFrame panActionFrame) {
		this.panActionFrame = panActionFrame;
	}

	public GuandanPanResult getPanResult() {
		return panResult;
	}

	public void setPanResult(GuandanPanResult panResult) {
		this.panResult = panResult;
	}

	public GuandanJuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(GuandanJuResult juResult) {
		this.juResult = juResult;
	}

}
