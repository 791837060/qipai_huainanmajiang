package com.dml.paodekuai.preparedapai.zhuaniao;

import com.dml.puke.pai.PukePaiMark;

/**
 * @Description: 鸟牌标记
 */
public class NiaoPaiMark implements PukePaiMark {

    private String name = "niaoPai";

    @Override
    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
