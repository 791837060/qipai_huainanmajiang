package com.dml.paodekuai.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

import java.util.Objects;

/**
 * 3个3加一张当做炸弹的点数组
 */
public class SanSanJiaYiBoomDianShuZu extends ZhadanDianShuZu {
    private DianShu dianShu;
    private DianShu dianShu2;

    public SanSanJiaYiBoomDianShuZu() {

    }

    public SanSanJiaYiBoomDianShuZu(DianShu dianShu) {
        this.dianShu = dianShu;
    }

    public DianShu getDianShu() {
        return dianShu;
    }

    public void setDianShu(DianShu dianShu) {
        this.dianShu = dianShu;
    }

    public DianShu getDianShu2() {
        return dianShu2;
    }

    public void setDianShu2(DianShu dianShu2) {
        this.dianShu2 = dianShu2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanSanJiaYiBoomDianShuZu that = (SanSanJiaYiBoomDianShuZu) o;
        return dianShu == that.dianShu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dianShu);
    }
}
