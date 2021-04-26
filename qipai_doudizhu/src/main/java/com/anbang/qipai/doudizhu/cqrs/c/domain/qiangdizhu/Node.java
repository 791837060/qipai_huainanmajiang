package com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu;

import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;

public class Node {

	private Node leftChildren;
	private Node rightChildren;
	private PlayerQiangdizhuState state;
	private String dizhuId;

	public void createLeftChildren(PlayerQiangdizhuState state, String dizhuId) {
		leftChildren = new Node();
		leftChildren.setState(state);
		leftChildren.setDizhuId(dizhuId);
	}

	public void createRightChildren(PlayerQiangdizhuState state, String dizhuId) {
		rightChildren = new Node();
		rightChildren.setState(state);
		rightChildren.setDizhuId(dizhuId);
	}

	public Node getLeftChildren() {
		return leftChildren;
	}

	public void setLeftChildren(Node leftChildren) {
		this.leftChildren = leftChildren;
	}

	public Node getRightChildren() {
		return rightChildren;
	}

	public void setRightChildren(Node rightChildren) {
		this.rightChildren = rightChildren;
	}

	public String getDizhuId() {
		return dizhuId;
	}

	public void setDizhuId(String dizhuId) {
		this.dizhuId = dizhuId;
	}

	public PlayerQiangdizhuState getState() {
		return state;
	}

	public void setState(PlayerQiangdizhuState state) {
		this.state = state;
	}

}
