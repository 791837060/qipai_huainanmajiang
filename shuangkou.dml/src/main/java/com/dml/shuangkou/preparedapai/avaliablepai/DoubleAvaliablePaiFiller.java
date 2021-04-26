package com.dml.shuangkou.preparedapai.avaliablepai;

import java.util.*;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.shuangkou.ju.Ju;

/**
 * 最普通的加入两副牌
 *
 * @author lsc
 */
public class DoubleAvaliablePaiFiller implements AvaliablePaiFiller {

    public DoubleAvaliablePaiFiller() {
    }

    @Override
    public void fillAvaliablePai(Ju ju) {
        PukePaiMian[] allPukePaiMianArray = PukePaiMian.values();
        List<PukePaiMian> playPaiTypeList = new ArrayList<>(Arrays.asList(allPukePaiMianArray));
        List<PukePai> allPaiList = new ArrayList<>();
        // 生成两副牌
        int id = 0;
        for (PukePaiMian paiType : playPaiTypeList) {
            if (!paiType.equals(PukePaiMian.hongxiner2)) {
                for (int i = 0; i < 2; i++) {
                    PukePai pai = new PukePai();
                    pai.setId(id);
                    pai.setPaiMian(paiType);
                    allPaiList.add(pai);
                    id++;
                }
            }
        }
        ju.getCurrentPan().setAvaliablePaiList(allPaiList);
    }

}
