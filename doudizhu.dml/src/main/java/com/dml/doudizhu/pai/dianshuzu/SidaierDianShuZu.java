package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 四带二对子:四张相同的牌加两张对子,如四张5+对6+对7. 不算炸弹.
 * 
 * @author lsc
 *
 */
public class SidaierDianShuZu extends ChibangDianShuZu {

	private DianShu chibanger;

	public SidaierDianShuZu() {

	}

	public SidaierDianShuZu(DianShu dianshu, DianShu chibang, DianShu chibanger) {
		super(dianshu, chibang);
		this.chibanger = chibanger;
	}

	public DianShu getChibanger() {
		return chibanger;
	}

	public void setChibanger(DianShu chibanger) {
		this.chibanger = chibanger;
	}

}
