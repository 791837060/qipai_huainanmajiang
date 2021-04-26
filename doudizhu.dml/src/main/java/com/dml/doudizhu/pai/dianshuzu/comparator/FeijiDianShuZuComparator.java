package com.dml.doudizhu.pai.dianshuzu.comparator;

import com.dml.doudizhu.pai.dianshuzu.FeijiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

public interface FeijiDianShuZuComparator {

	int compare(FeijiDianShuZu dianShuZu1, FeijiDianShuZu dianShuZu2) throws CanNotCompareException;

}
