package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 带一个单牌的飞机
 * 
 * @author lsc
 *
 */
public class FeijidaiyiDianShuZu extends FeijiDianShuZu {

	private DianShu[] chibangArray;

	public FeijidaiyiDianShuZu() {

	}

	public FeijidaiyiDianShuZu(DianShu[] lianxuDianshuArray, DianShu[] chibangArray) {
		super(lianxuDianshuArray);
		this.chibangArray = chibangArray;
	}

	public DianShu[] getChibangArray() {
		return chibangArray;
	}

	public void setChibangArray(DianShu[] chibangArray) {
		this.chibangArray = chibangArray;
	}

}
