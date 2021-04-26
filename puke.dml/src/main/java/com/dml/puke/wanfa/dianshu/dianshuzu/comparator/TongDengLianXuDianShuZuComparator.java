package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.LianXuDianShuZu;

/**
 * 最常用的“同等”的连续点数组按争上游序比大小。“同等”指的是必须类型相‘同’，连续长度相‘等’。
 *
 * @author Neo
 */
public class TongDengLianXuDianShuZuComparator implements LianXuDianShuZuComparator {

    @Override
    public int compare(LianXuDianShuZu paiZu1, LianXuDianShuZu paiZu2) throws CanNotCompareException {
        if (!paiZu1.getClass().equals(paiZu2.getClass())) {
            throw new CanNotCompareException();
        }
        if (paiZu1.length() != paiZu2.length()) {
            throw new CanNotCompareException();
        }
//        return paiZu1.getLianXuDianShuArray()[0].compareTo(paiZu2.getLianXuDianShuArray()[0]);
        //因为A 2 下放玩法的原因，修改为比顺子牌的最后那张的大小
        return paiZu1.getLianXuDianShuArray()[paiZu1.getLianXuDianShuArray().length - 1].compareTo(paiZu2.getLianXuDianShuArray()[paiZu2.getLianXuDianShuArray().length - 1]);
    }

}
