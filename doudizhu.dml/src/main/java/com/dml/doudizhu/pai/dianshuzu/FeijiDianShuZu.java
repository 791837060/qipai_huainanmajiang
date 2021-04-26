package com.dml.doudizhu.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 飞机:两个或更多的连续三张牌,如三张J+三张Q.
 * 
 * @author lsc
 *
 */
public abstract class FeijiDianShuZu implements DianShuZu {

	private DianShu[] lianxuDianshuArray;

	public FeijiDianShuZu() {

	}

	public FeijiDianShuZu(DianShu[] lianxuDianshuArray) {
		this.lianxuDianshuArray = lianxuDianshuArray;
	}

	public int length() {
		return lianxuDianshuArray.length;
	}

	public DianShu[] getLianxuDianshuArray() {
		return lianxuDianshuArray;
	}

	public void setLianxuDianshuArray(DianShu[] lianxuDianshuArray) {
		this.lianxuDianshuArray = lianxuDianshuArray;
	}

}
