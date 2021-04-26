package com.dml.doudizhu.pai.dianshuzu.comparator;

import com.dml.doudizhu.pai.dianshuzu.ChibangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

public interface ChibangDianShuZuComparator {

	int compare(ChibangDianShuZu dianShuZu1, ChibangDianShuZu dianShuZu2) throws CanNotCompareException;
}
