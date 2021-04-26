package com.dml.shuangkou.pai.dianshuzu;

import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;

/**
 * @Description: 长度相同时比上游牌大小
 */
public class TongDengDaiPaiComparator implements DaipaiComparator {

    @Override
    public int compare(DaipaiDianShuZu dianShuZu1, DaipaiDianShuZu dianShuZu2) throws CanNotCompareException {
        if (!dianShuZu1.getClass().equals(dianShuZu2.getClass())) {
            throw new CanNotCompareException();
        }
        if (dianShuZu1.getSanzhangDianShuArray().length != dianShuZu2.getSanzhangDianShuArray().length) {
            throw new CanNotCompareException();
        }
        return dianShuZu1.getSanzhangDianShuArray()[0].compareTo(dianShuZu2.getSanzhangDianShuArray()[0]);
    }
}
