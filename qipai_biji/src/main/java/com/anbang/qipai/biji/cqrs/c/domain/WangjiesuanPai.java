package com.anbang.qipai.biji.cqrs.c.domain;

import com.dml.shisanshui.pai.PukePaiMian;

/**
 * 王牌结算牌
 * 
 * @author lsc
 *
 */
public class WangjiesuanPai {

	private PukePaiMian wangpai;

	private PukePaiMian jiesuanpai;

	public WangjiesuanPai(PukePaiMian wangpai, PukePaiMian jiesuanpai) {
		this.wangpai = wangpai;
		this.jiesuanpai = jiesuanpai;
	}

	public PukePaiMian getWangpai() {
		return wangpai;
	}

	public void setWangpai(PukePaiMian wangpai) {
		this.wangpai = wangpai;
	}

	public PukePaiMian getJiesuanpai() {
		return jiesuanpai;
	}

	public void setJiesuanpai(PukePaiMian jiesuanpai) {
		this.jiesuanpai = jiesuanpai;
	}

}
