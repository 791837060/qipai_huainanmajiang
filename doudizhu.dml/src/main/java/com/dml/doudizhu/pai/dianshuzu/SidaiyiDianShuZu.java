package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 四带二单张:四张相同的牌加两张单牌,如四张5+单6+单7. 不算炸弹.
 * 
 * @author lsc
 *
 */
public class SidaiyiDianShuZu extends ChibangDianShuZu {

	private DianShu chibanger;

	public SidaiyiDianShuZu() {

	}

	public SidaiyiDianShuZu(DianShu dianshu, DianShu chibang, DianShu chibanger) {
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
