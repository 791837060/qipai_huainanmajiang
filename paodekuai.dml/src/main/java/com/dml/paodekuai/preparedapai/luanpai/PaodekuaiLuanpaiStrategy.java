package com.dml.paodekuai.preparedapai.luanpai;

import com.dml.paodekuai.ju.Ju;
import com.dml.puke.pai.PukePai;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 随机洗牌
 */
public class PaodekuaiLuanpaiStrategy implements LuanpaiStrategy {

    public PaodekuaiLuanpaiStrategy() {
    }

    @Override
    public void luanpai(Ju ju) throws Exception {
        List<PukePai> avaliablePaiList = ju.getCurrentPan().getAvaliablePaiList();
        Collections.shuffle(avaliablePaiList);
    }
}
