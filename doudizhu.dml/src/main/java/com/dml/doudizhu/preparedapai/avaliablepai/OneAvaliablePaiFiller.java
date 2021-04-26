package com.dml.doudizhu.preparedapai.avaliablepai;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.ju.Ju;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;

/**
 * 最普通的一副牌
 *
 * @author lsc
 */
public class OneAvaliablePaiFiller implements AvaliablePaiFiller {

    @Override
    public void fillAvaliablePai(Ju ju) throws Exception {
        List<PukePai> allPaiList = new ArrayList<>();
        // 生成一副牌
        int id = 0;
        for (PukePaiMian paiType : PukePaiMian.values()) {
            if (!paiType.equals(PukePaiMian.hongxiner2)) {
                PukePai pai = new PukePai();
                pai.setId(id);
                pai.setPaiMian(paiType);
                allPaiList.add(pai);
                id++;
            }
        }

        ju.getCurrentPan().setAvaliablePaiList(allPaiList);
    }

}
