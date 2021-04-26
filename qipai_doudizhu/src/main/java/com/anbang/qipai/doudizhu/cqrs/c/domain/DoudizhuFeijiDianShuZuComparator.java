package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.doudizhu.pai.dianshuzu.FeijiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.comparator.FeijiDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

/**
 * 飞机比大小
 * 
 * @author lsc
 *
 */
public class DoudizhuFeijiDianShuZuComparator implements FeijiDianShuZuComparator {

	public int compare(FeijiDianShuZu dianShuZu1, FeijiDianShuZu dianShuZu2) throws CanNotCompareException {
		if (!dianShuZu1.getClass().equals(dianShuZu2.getClass())) {
			throw new CanNotCompareException();
		}
		if (dianShuZu1.length() != dianShuZu2.length()) {
			throw new CanNotCompareException();
		}
		return dianShuZu1.getLianxuDianshuArray()[0].compareTo(dianShuZu2.getLianxuDianshuArray()[0]);
	}
}
