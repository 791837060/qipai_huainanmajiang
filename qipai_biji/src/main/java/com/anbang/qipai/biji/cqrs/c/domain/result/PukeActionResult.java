package com.anbang.qipai.biji.cqrs.c.domain.result;

import com.anbang.qipai.biji.cqrs.c.domain.PukeGameValueObject;
import com.dml.shisanshui.pan.PanActionFrame;

public class PukeActionResult {
	private PukeGameValueObject pukeGame;
	private PanActionFrame panActionFrame;
	private BijiPanResult panResult;
	private BijiJuResult juResult;

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

	public BijiPanResult getPanResult() {
		return panResult;
	}

	public void setPanResult(BijiPanResult panResult) {
		this.panResult = panResult;
	}

	public BijiJuResult getJuResult() {
		return juResult;
	}

	public void setJuResult(BijiJuResult juResult) {
		this.juResult = juResult;
	}

}
