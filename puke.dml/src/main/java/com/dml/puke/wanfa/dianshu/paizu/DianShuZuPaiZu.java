package com.dml.puke.wanfa.dianshu.paizu;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 点数组牌组。包含点数组和实际的牌
 * 
 * @author Neo
 *
 */
public class DianShuZuPaiZu {
	private DianShuZu dianShuZu;
	private PukePai[] paiArray;
	/**
	 * 外号
	 */
	private String waihao;

	public DianShuZu getDianShuZu() {
		return dianShuZu;
	}

	public void setDianShuZu(DianShuZu dianShuZu) {
		this.dianShuZu = dianShuZu;
	}

	public PukePai[] getPaiArray() {
		return paiArray;
	}

	public void setPaiArray(PukePai[] paiArray) {
		this.paiArray = paiArray;
	}

	public String getWaihao() {
		return waihao;
	}

	public void setWaihao(String waihao) {
		this.waihao = waihao;
	}

}
