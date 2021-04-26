package com.anbang.qipai.guandan.cqrs.c.domain;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.preparedapai.luanpai.LuanpaiStrategy;

import java.util.Collections;
import java.util.List;

/**
 * 随机洗牌
 */
public class GuandaiRandomLuanpaiStrategy implements LuanpaiStrategy {

    public GuandaiRandomLuanpaiStrategy() {
    }

    @Override
    public void luanpai(Ju ju) throws Exception {
        List<PukePai> avaliablePaiList = ju.getCurrentPan().getAvaliablePaiList();
        Collections.shuffle(avaliablePaiList);
    }
}
