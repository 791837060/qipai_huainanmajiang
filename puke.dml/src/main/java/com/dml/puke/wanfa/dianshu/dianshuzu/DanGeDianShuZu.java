package com.dml.puke.wanfa.dianshu.dianshuzu;

import com.dml.puke.pai.DianShu;

/**
 * 单个点数比大小相关玩法关注的组。对子、三张 等。
 * 
 * @author Neo
 *
 */
public abstract class DanGeDianShuZu implements DianShuZu {

	private DianShu dianShu;

	public DanGeDianShuZu() {
	}

	public DanGeDianShuZu(DianShu dianShu) {
		this.dianShu = dianShu;
	}

	public DianShu getDianShu() {
		return dianShu;
	}

	public void setDianShu(DianShu dianShu) {
		this.dianShu = dianShu;
	}

	@Override
	public int hashCode() {
		return dianShu.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DanGeDianShuZu other = (DanGeDianShuZu) obj;
		if (dianShu != other.dianShu)
			return false;
		return true;
	}

}
