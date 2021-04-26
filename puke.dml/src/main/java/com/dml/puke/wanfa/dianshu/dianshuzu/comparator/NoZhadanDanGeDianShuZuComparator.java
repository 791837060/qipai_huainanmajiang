package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeDianShuZu;

/**
 * 最常见的上游牌点数牌组比大小，不管炸弹
 * 
 * @author Neo
 *
 */
public class NoZhadanDanGeDianShuZuComparator implements DanGeDianShuZuComparator {

	@Override
	public int compare(DanGeDianShuZu dianShuZu1, DanGeDianShuZu dianShuZu2) throws CanNotCompareException {
		if (!dianShuZu1.getClass().equals(dianShuZu2.getClass())) {
			throw new CanNotCompareException();
		}
		return dianShuZu1.getDianShu().compareTo(dianShuZu2.getDianShu());
	}

}
