package com.dml.puke.wanfa.dianshu.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 炸弹。四个及以上点数一样的。
 * 
 * @author Neo
 *
 */
public class DanGeZhadanDianShuZu extends ZhadanDianShuZu {
	private DianShu dianShu;
	private int size;

	public DanGeZhadanDianShuZu() {
	}

	public DanGeZhadanDianShuZu(DianShu dianShu, int size) {
		this.dianShu = dianShu;
		this.size = size;
	}

	public DianShu getDianShu() {
		return dianShu;
	}

	public void setDianShu(DianShu dianShu) {
		this.dianShu = dianShu;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		return dianShu.hashCode() + size;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DanGeZhadanDianShuZu other = (DanGeZhadanDianShuZu) obj;
		if (dianShu != other.dianShu)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

}
