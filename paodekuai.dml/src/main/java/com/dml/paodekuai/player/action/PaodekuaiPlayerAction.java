package com.dml.paodekuai.player.action;

public abstract class PaodekuaiPlayerAction {

	private PaodekuaiPlayerActionType type;

	private String actionPlayerId;

	public PaodekuaiPlayerAction() {

	}

	public PaodekuaiPlayerAction(PaodekuaiPlayerActionType type, String actionPlayerId) {
		this.type = type;
		this.actionPlayerId = actionPlayerId;
	}

	public PaodekuaiPlayerActionType getType() {
		return type;
	}

	public void setType(PaodekuaiPlayerActionType type) {
		this.type = type;
	}

	public String getActionPlayerId() {
		return actionPlayerId;
	}

	public void setActionPlayerId(String actionPlayerId) {
		this.actionPlayerId = actionPlayerId;
	}

}
