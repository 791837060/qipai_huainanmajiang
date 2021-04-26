package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.player.action.mo.MopaiReason;

public class MaanshanMajiangBupai implements MopaiReason {

	public static final String name = "bupai";

	@Override
	public String getName() {
		return name;
	}

}
