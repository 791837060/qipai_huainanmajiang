package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 带一个对子的飞机
 * 
 * @author lsc
 *
 */
public class FeijidaierDianShuZu extends FeijiDianShuZu {

	private DianShu[] chibangArray;

	public FeijidaierDianShuZu() {

	}

	public FeijidaierDianShuZu(DianShu[] lianxuDianshuArray, DianShu[] chibangArray) {
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
