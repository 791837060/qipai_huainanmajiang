package com.dml.shuangkou.pai.jiesuanpai;

import com.dml.puke.pai.DianShu;

/**
 * 结算牌，包括原牌和当的类型
 * 
 * @author lsc
 *
 */
public abstract class ShoupaiJiesuanPai {
	public abstract String dangType();// 当的类型，大王当牌、小王当牌

	public abstract DianShu[] getAllYuanPai();// 原牌，半千变时两张小王当做一张牌

	public abstract DianShu getDangPaiType();// 当的牌
}
