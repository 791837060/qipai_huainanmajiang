package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 三带二:三张点数相同的牌加一个对子,如三张6+对5.
 * 
 * @author lsc
 *
 */
public class SandaierDianShuZu extends ChibangDianShuZu {

	public SandaierDianShuZu() {

	}

	public SandaierDianShuZu(DianShu dianshu, DianShu chibang) {
		super(dianshu, chibang);
	}
}
