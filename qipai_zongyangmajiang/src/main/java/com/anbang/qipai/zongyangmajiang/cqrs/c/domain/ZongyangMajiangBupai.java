package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.player.action.mo.MopaiReason;

public class ZongyangMajiangBupai implements MopaiReason {

	public static final String name = "bupai";

	@Override
	public String getName() {
		return name;
	}
}
