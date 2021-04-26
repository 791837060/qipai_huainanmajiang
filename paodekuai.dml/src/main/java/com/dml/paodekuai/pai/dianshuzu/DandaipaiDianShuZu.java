package com.dml.paodekuai.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description: 单个牌型带多张单牌的点数组（目前为四带二，四带三）
 */
public abstract class DandaipaiDianShuZu implements DianShuZu {
    private DianShu danpaiDianShu;//
    private DianShu[] daipaiDianShuArray;//带的牌

    public DianShu getDanpaiDianShu() {
        return danpaiDianShu;
    }

    public void setDanpaiDianShu(DianShu danpaiDianShu) {
        this.danpaiDianShu = danpaiDianShu;
    }

    public DianShu[] getDaipaiDianShuArray() {
        return daipaiDianShuArray;
    }

    public void setDaipaiDianShuArray(DianShu[] daipaiDianShuArray) {
        this.daipaiDianShuArray = daipaiDianShuArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DandaipaiDianShuZu that = (DandaipaiDianShuZu) o;
        return danpaiDianShu == that.danpaiDianShu &&
                Arrays.equals(daipaiDianShuArray, that.daipaiDianShuArray);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(danpaiDianShu);
        result = 31 * result + Arrays.hashCode(daipaiDianShuArray);
        return result;
    }
}
