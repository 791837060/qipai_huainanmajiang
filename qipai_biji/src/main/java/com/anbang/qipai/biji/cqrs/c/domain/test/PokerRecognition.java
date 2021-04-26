package com.anbang.qipai.biji.cqrs.c.domain.test;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;

import java.util.ArrayList;
import java.util.List;

public class PokerRecognition {
    public static void main(String[] args) {
        List<PukePai> pukePais = CreatePuker();
        getPokerStr(pukePais, "36495007313543471617181926");
        System.out.println();
//        getPokerStr(pukePais, "10115003161920250728303845");
//        System.out.println();
//        getPokerStr(pukePais, "24254619282938420711154849");
    }

    public static List<PukePai> CreatePuker() {
        List<PukePai> allPaiList = new ArrayList<>();//牌库
        int id = 0;
        for (PukePaiMian paiType : PukePaiMian.values()) {
            if (!paiType.equals(PukePaiMian.dawang) && !paiType.equals(PukePaiMian.xiaowang)) {
                PukePai pai = new PukePai();
                pai.setId(id);
                pai.setPaiMian(paiType);
                allPaiList.add(pai);
                id++;
            }
        }
        return allPaiList;
    }

    public static void getPokerStr(List<PukePai> pukePais, String index) {
        for (int i = 0; i < index.length(); i += 2) {
            String substring = index.substring(i, i + 2);
            PukePaiMian paiMian = pukePais.get(Integer.valueOf(substring)).getPaiMian();
            if (i == 6 || i == 16) {
                System.out.print(" | ");
            }
            System.out.print(" " + paiMian.huaSe() + "-" + paiMian.dianShu());
        }
    }
}
