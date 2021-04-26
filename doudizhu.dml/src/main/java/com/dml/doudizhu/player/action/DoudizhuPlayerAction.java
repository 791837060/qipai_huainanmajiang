package com.dml.doudizhu.player.action;

public abstract class DoudizhuPlayerAction {

	private DoudizhuPlayerActionType type;

	private String actionPlayerId;

	public DoudizhuPlayerAction() {

	}

	public DoudizhuPlayerAction(DoudizhuPlayerActionType da, String actionPlayerId) {
		type = da;
		this.actionPlayerId = actionPlayerId;
	}

	public DoudizhuPlayerActionType getType() {
		return type;
	}

	public void setType(DoudizhuPlayerActionType type) {
		this.type = type;
	}

	public String getActionPlayerId() {
		return actionPlayerId;
	}

	public void setActionPlayerId(String actionPlayerId) {
		this.actionPlayerId = actionPlayerId;
	}

}
