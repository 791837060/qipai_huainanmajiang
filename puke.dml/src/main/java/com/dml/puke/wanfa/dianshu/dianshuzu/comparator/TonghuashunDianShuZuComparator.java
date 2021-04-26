package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianXuDianShuZu;

/**
 * 最常用的“同等”的连续点数组按争上游序比大小。“同等”指的是必须类型相‘同’，连续长度相‘等’。
 *
 * @author Neo
 */
public class TonghuashunDianShuZuComparator implements LianXuDianShuZuComparator {

    @Override
    public int compare(LianXuDianShuZu paiZu1, LianXuDianShuZu paiZu2) throws CanNotCompareException {
        if (!paiZu1.getClass().equals(paiZu2.getClass())) {
            throw new CanNotCompareException();
        }
        if (paiZu1.length() != paiZu2.length()) {
            throw new CanNotCompareException();
        }

        DianShu dianshu1, dianshu2;
        int index = 1;
        while (true) {
            dianshu1 = paiZu1.getLianXuDianShuArray()[paiZu1.getLianXuDianShuArray().length - index];
            dianshu2 = paiZu2.getLianXuDianShuArray()[paiZu2.getLianXuDianShuArray().length - index];
            if (dianshu1.equals(DianShu.hongxiner2) || dianshu2.equals(DianShu.hongxiner2)) {
                if (index == 5) {
                    break;
                }
                index++;
            } else {
                break;
            }
        }

        return dianshu1.compareTo(dianshu2);
    }

}
