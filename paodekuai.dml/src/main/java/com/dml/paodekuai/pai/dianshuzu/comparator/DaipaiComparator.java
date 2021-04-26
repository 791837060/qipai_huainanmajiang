package com.dml.paodekuai.pai.dianshuzu.comparator;

import com.dml.paodekuai.pai.dianshuzu.DaipaiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

public interface DaipaiComparator {
    /**
     * @return 大于等于小于 >> 1,0,-1
     */
    int compare(DaipaiDianShuZu dianShuZu1, DaipaiDianShuZu dianShuZu2) throws CanNotCompareException;
}
