package com.dml.shuangkou.player.action;

public abstract class ShuangkouPlayerAction {

	private ShuangkouPlayerActionType type;

	private String actionPlayerId;

	public ShuangkouPlayerAction() {

	}

	public ShuangkouPlayerAction(ShuangkouPlayerActionType type, String actionPlayerId) {
		this.type = type;
		this.actionPlayerId = actionPlayerId;
	}

	public ShuangkouPlayerActionType getType() {
		return type;
	}

	public void setType(ShuangkouPlayerActionType type) {
		this.type = type;
	}

	public String getActionPlayerId() {
		return actionPlayerId;
	}

	public void setActionPlayerId(String actionPlayerId) {
		this.actionPlayerId = actionPlayerId;
	}

}
