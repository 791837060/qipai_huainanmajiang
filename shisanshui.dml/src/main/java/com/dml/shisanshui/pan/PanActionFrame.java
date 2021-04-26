package com.dml.shisanshui.pan;

import com.dml.shisanshui.player.action.ShisanshuiChupaiAction;

public class PanActionFrame {
	private int no;
	private ShisanshuiChupaiAction action;
	private PanValueObject panAfterAction;
	private long actionTime;

	public PanActionFrame() {

	}

	public PanActionFrame(ShisanshuiChupaiAction action, PanValueObject panAfterAction, long actionTime) {
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

	public ShisanshuiChupaiAction getAction() {
		return action;
	}

	public void setAction(ShisanshuiChupaiAction action) {
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
