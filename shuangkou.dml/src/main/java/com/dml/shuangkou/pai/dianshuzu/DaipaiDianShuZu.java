package com.dml.shuangkou.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

import java.util.Arrays;

/**
 * 带牌点数组（包含三带二，飞机等牌型）
 */
public abstract class DaipaiDianShuZu implements DianShuZu {
    private DianShu[] sanzhangDianShuArray;//三张的牌型
    private DianShu[] daipaiDianShuArray;//带的牌
    private boolean missingType;//是否为带牌缺失的牌型

    public void judgeMissingType() {
        if (daipaiDianShuArray != null) {
            double dainpaiCount = daipaiDianShuArray.length / 2;
            double sanzhangCount = sanzhangDianShuArray.length / 3;
            if (dainpaiCount == sanzhangCount) {
                missingType = false;
            }
        }
        missingType = true;
    }

    public DaipaiDianShuZu() {

    }

    public DaipaiDianShuZu(DianShu[] sanzhangDianShuArray, DianShu[] daipaiDianShuArray) {
        this.sanzhangDianShuArray = sanzhangDianShuArray;
        this.daipaiDianShuArray = daipaiDianShuArray;
    }

    public DianShu[] getSanzhangDianShuArray() {
        return sanzhangDianShuArray;
    }

    public void setSanzhangDianShuArray(DianShu[] sanzhangDianShuArray) {
        this.sanzhangDianShuArray = sanzhangDianShuArray;
    }

    public DianShu[] getDaipaiDianShuArray() {
        return daipaiDianShuArray;
    }

    public void setDaipaiDianShuArray(DianShu[] daipaiDianShuArray) {
        this.daipaiDianShuArray = daipaiDianShuArray;
    }

    public boolean isMissingType() {
        return missingType;
    }

    public void setMissingType(boolean missingType) {
        this.missingType = missingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DaipaiDianShuZu that = (DaipaiDianShuZu) o;
        return Arrays.equals(sanzhangDianShuArray, that.sanzhangDianShuArray) &&
                Arrays.equals(daipaiDianShuArray, that.daipaiDianShuArray);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(sanzhangDianShuArray);
        result = 31 * result + Arrays.hashCode(daipaiDianShuArray);
        return result;
    }
}
