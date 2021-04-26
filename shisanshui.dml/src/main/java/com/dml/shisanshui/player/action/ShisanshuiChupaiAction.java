package com.dml.shisanshui.player.action;

import com.dml.shisanshui.pai.paixing.PaixingSolution;

public class ShisanshuiChupaiAction {

	private String chupaiPlayerId;

	private PaixingSolution solution;

	public ShisanshuiChupaiAction() {

	}

	public ShisanshuiChupaiAction(String chupaiPlayerId, PaixingSolution solution) {
		this.chupaiPlayerId = chupaiPlayerId;
		this.solution = solution;
	}

	public String getChupaiPlayerId() {
		return chupaiPlayerId;
	}

	public void setChupaiPlayerId(String chupaiPlayerId) {
		this.chupaiPlayerId = chupaiPlayerId;
	}

	public PaixingSolution getSolution() {
		return solution;
	}

	public void setSolution(PaixingSolution solution) {
		this.solution = solution;
	}

}
