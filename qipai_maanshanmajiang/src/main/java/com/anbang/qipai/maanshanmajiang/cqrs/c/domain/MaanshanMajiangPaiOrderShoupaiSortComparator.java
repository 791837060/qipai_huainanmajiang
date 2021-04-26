package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;

import java.util.Comparator;

/**
 * 缺一门类型靠后排序
 */
public class MaanshanMajiangPaiOrderShoupaiSortComparator implements Comparator<MajiangPai> {

    XushupaiCategory xushupaiCategory;

    @Override
    public int compare(MajiangPai pai1, MajiangPai pai2) {
        int pai1Ordinal, pai2Ordinal;
        if (xushupaiCategory.equals(XushupaiCategory.getCategoryforXushupai(pai1))) {
            pai1Ordinal = pai1.ordinal() + 99;
        } else {
            pai1Ordinal = pai1.ordinal();
        }
        if (xushupaiCategory.equals(XushupaiCategory.getCategoryforXushupai(pai2))) {
            pai2Ordinal = pai2.ordinal() + 99;
        } else {
            pai2Ordinal = pai2.ordinal();
        }
        return pai1Ordinal - pai2Ordinal;
    }

    public MaanshanMajiangPaiOrderShoupaiSortComparator(XushupaiCategory xushupaiCategory) {
        this.xushupaiCategory = xushupaiCategory;
    }

    public XushupaiCategory getXushupaiCategory() {
        return xushupaiCategory;
    }

    public void setXushupaiCategory(XushupaiCategory xushupaiCategory) {
        this.xushupaiCategory = xushupaiCategory;
    }
}
