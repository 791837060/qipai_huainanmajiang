package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.doudizhu.pai.dianshuzu.ChibangDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.comparator.ChibangDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

/**
 * 带翅膀的牌比大小
 * 
 * @author lsc
 *
 */
public class DoudizhuChibangDianShuZuComparator implements ChibangDianShuZuComparator {

	public int compare(ChibangDianShuZu dianShuZu1, ChibangDianShuZu dianShuZu2) throws CanNotCompareException {
		if (!dianShuZu1.getClass().equals(dianShuZu2.getClass())) {
			throw new CanNotCompareException();
		}
		return dianShuZu1.getDianshu().compareTo(dianShuZu2.getDianshu());
	}

}
