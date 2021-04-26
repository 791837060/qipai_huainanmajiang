package com.dml.paodekuai.pan;

import com.dml.paodekuai.player.action.PaodekuaiPlayerAction;

public class PanActionFrame {
	private int no;
	private PaodekuaiPlayerAction action;
	private PanValueObject panAfterAction;
	private long actionTime;

	public PanActionFrame() {
	}

	public PanActionFrame(PaodekuaiPlayerAction action, PanValueObject panAfterAction, long actionTime) {
		this.action = action;
		this.panAfterAction = panAfterAction;
		this.actionTime = actionTime;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public PaodekuaiPlayerAction getAction() {
		return action;
	}

	public void setAction(PaodekuaiPlayerAction action) {
		this.action = action;
	}

	public PanValueObject getPanAfterAction() {
		return panAfterAction;
	}

	public void setPanAfterAction(PanValueObject panAfterAction) {
		this.panAfterAction = panAfterAction;
	}

	public long getActionTime() {
		return actionTime;
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
	}

}
