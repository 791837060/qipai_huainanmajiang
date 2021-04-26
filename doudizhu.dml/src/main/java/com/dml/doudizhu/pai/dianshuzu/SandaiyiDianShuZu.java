package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 三带:三张点数相同的牌加一张单牌,如三张6+单5.
 * 
 * @author lsc
 *
 */
public class SandaiyiDianShuZu extends ChibangDianShuZu {

	public SandaiyiDianShuZu() {

	}

	public SandaiyiDianShuZu(DianShu dianshu, DianShu chibang) {
		super(dianshu, chibang);
	}
}
