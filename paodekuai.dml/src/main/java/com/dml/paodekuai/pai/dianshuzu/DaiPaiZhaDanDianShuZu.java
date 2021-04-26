package com.dml.paodekuai.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;

/**
 * @Description: 可带一张牌的炸弹(33334)
 */
public class DaiPaiZhaDanDianShuZu extends DanGeZhadanDianShuZu {
	private DianShu zhadanDian;
	private DianShu daipaiDian;

	public DianShu getZhadanDian() {
		return zhadanDian;
	}

	public void setZhadanDian(DianShu zhadanDian) {
		this.zhadanDian = zhadanDian;
	}

	public DianShu getDaipaiDian() {
		return daipaiDian;
	}

	public void setDaipaiDian(DianShu daipaiDian) {
		this.daipaiDian = daipaiDian;
	}

	@Override
	public int hashCode() {
		return zhadanDian.hashCode() + daipaiDian.hashCode() * 10;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DaiPaiZhaDanDianShuZu other = (DaiPaiZhaDanDianShuZu) obj;
		if (daipaiDian != other.daipaiDian)
			return false;
		if (zhadanDian != other.zhadanDian)
			return false;
		return true;
	}
}
