package com.dml.paodekuai.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

import java.util.Objects;

/**
 * @Description: aaa当做炸弹的点数组
 */
public class SanAJiaYiBoomDianShuZu extends ZhadanDianShuZu {
    private DianShu dianShu;
    private DianShu daipaiDianshu;

    public SanAJiaYiBoomDianShuZu() {

    }

    public SanAJiaYiBoomDianShuZu(DianShu dianShu) {
        this.dianShu = dianShu;
    }

    public DianShu getDianShu() {
        return dianShu;
    }

    public void setDianShu(DianShu dianShu) {
        this.dianShu = dianShu;
    }

    public DianShu getDaipaiDianshu() {
        return daipaiDianshu;
    }

    public void setDaipaiDianshu(DianShu daipaiDianshu) {
        this.daipaiDianshu = daipaiDianshu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanAJiaYiBoomDianShuZu that = (SanAJiaYiBoomDianShuZu) o;
        return dianShu == that.dianShu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dianShu);
    }
}
