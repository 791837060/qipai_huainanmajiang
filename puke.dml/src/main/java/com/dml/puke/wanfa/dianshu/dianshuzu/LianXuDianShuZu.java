package com.dml.puke.wanfa.dianshu.dianshuzu;

import java.util.Arrays;

import com.dml.puke.pai.DianShu;

/**
 * 连续点数组。顺子、连对、连三张 等。
 * 
 * @author Neo
 *
 */
public abstract class LianXuDianShuZu implements DianShuZu {

	private DianShu[] lianXuDianShuArray;

	public LianXuDianShuZu() {
	}

	public LianXuDianShuZu(DianShu[] lianXuDianShuArray) {
		this.lianXuDianShuArray = lianXuDianShuArray;
	}

	public int length() {
		return lianXuDianShuArray.length;
	}

	public DianShu[] getLianXuDianShuArray() {
		return lianXuDianShuArray;
	}

	public void setLianXuDianShuArray(DianShu[] lianXuDianShuArray) {
		this.lianXuDianShuArray = lianXuDianShuArray;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(lianXuDianShuArray);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LianXuDianShuZu other = (LianXuDianShuZu) obj;
		if (!Arrays.equals(lianXuDianShuArray, other.lianXuDianShuArray))
			return false;
		return true;
	}
}
