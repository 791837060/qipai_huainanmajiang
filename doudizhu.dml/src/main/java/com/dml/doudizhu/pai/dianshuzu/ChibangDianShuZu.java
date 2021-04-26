package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 带翅膀的点数组
 * 
 * @author lsc
 *
 */
public abstract class ChibangDianShuZu implements DianShuZu {
	private DianShu dianshu;
	private DianShu chibang;

	public ChibangDianShuZu() {

	}

	public ChibangDianShuZu(DianShu dianshu, DianShu chibang) {
		this.dianshu = dianshu;
		this.chibang = chibang;
	}

	public DianShu getDianshu() {
		return dianshu;
	}

	public void setDianshu(DianShu dianshu) {
		this.dianshu = dianshu;
	}

	public DianShu getChibang() {
		return chibang;
	}

	public void setChibang(DianShu chibang) {
		this.chibang = chibang;
	}

}
