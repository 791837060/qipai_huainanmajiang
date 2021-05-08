package com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao;


import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.dml.majiang.pan.frame.PanActionFrame;

public class XiapiaoResult {
	private MajiangGameValueObject majiangGame;
	private PanActionFrame firstActionFrame;

	public PanActionFrame getFirstActionFrame() {
		return firstActionFrame;
	}

	public void setFirstActionFrame(PanActionFrame firstActionFrame) {
		this.firstActionFrame = firstActionFrame;
	}

	public MajiangGameValueObject getMajiangGame() {
		return majiangGame;
	}

	public void setMajiangGame(MajiangGameValueObject majiangGame) {
		this.majiangGame = majiangGame;
	}

}
