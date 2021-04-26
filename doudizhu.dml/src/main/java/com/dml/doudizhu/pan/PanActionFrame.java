package com.dml.doudizhu.pan;

import com.dml.doudizhu.player.action.DoudizhuPlayerAction;

public class PanActionFrame {
	private int no;
	private DoudizhuPlayerAction action;
	private PanValueObject panAfterAction;
	private long actionTime;

	public PanActionFrame() {

	}

	public PanActionFrame(DoudizhuPlayerAction action, PanValueObject panAfterAction, long actionTime) {
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

	public DoudizhuPlayerAction getAction() {
		return action;
	}

	public void setAction(DoudizhuPlayerAction action) {
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
