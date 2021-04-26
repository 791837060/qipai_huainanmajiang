package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

/**
 * 火箭:大王加小王,也称王炸.最大的牌.
 * 
 * @author lsc
 *
 */
public class HuojianDianShuZu extends ZhadanDianShuZu {
	private DianShu[] dianshuArray = { DianShu.xiaowang, DianShu.dawang };

	public DianShu[] getDianshuArray() {
		return dianshuArray;
	}

	public void setDianshuArray(DianShu[] dianshuArray) {
		this.dianshuArray = dianshuArray;
	}

}
