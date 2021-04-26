package com.anbang.qipai.paodekuai.cqrs.c.domain.test;

import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        test3();
    }

    public static void test3() {
        PukePaiMian[] paiMain2 = {
                PukePaiMian.meihuawu,
                PukePaiMian.heitaoliu,
                PukePaiMian.hongxinliu,
                PukePaiMian.meihualiu,
                PukePaiMian.fangkuailiu,
                PukePaiMian.heitaoba,
                PukePaiMian.hongxinba,
                PukePaiMian.meihuaba,
                PukePaiMian.heitaojiu,
                PukePaiMian.hongxinjiu,
                PukePaiMian.meihuajiu,
                PukePaiMian.heitaoQ,
                PukePaiMian.heitaoK,
                PukePaiMian.hongxinK,
                PukePaiMian.meihuaK,
                PukePaiMian.fangkuaiK,
        };
        int[] paiId2=new int[16];
        for (int i = 0; i <paiMain2.length ; i++) {
            System.out.println(paiMain2[i].dianShu()+"|"+paiMain2[i].huaSe()+"|"+paiMain2[i].ordinal());
            paiId2[i]=paiMain2[i].ordinal();
        }

        System.out.println();
    }

    public static List<PukePai> initPaiList() {
        Set<PukePaiMian> notPlaySet = new HashSet<>();
//            notPlaySet.add(PukePaiMian.dawang);
//            notPlaySet.add(PukePaiMian.xiaowang);
//            notPlaySet.add(PukePaiMian.hongxiner);
//            notPlaySet.add(PukePaiMian.meihuaer);
//            notPlaySet.add(PukePaiMian.fangkuaier);
//            notPlaySet.add(PukePaiMian.hongxinA);
//            notPlaySet.add(PukePaiMian.fangkuaiA);
//            notPlaySet.add(PukePaiMian.meihuaA);
//            notPlaySet.add(PukePaiMian.fangkuaiK);

        notPlaySet.add(PukePaiMian.dawang);
        notPlaySet.add(PukePaiMian.xiaowang);
        notPlaySet.add(PukePaiMian.hongxiner);
        notPlaySet.add(PukePaiMian.meihuaer);
        notPlaySet.add(PukePaiMian.fangkuaier);
        notPlaySet.add(PukePaiMian.heitaoA);
        notPlaySet.add(PukePaiMian.dawang);
//
//            notPlaySet.add(PukePaiMian.xiaowang);
//            notPlaySet.add(PukePaiMian.fangkuaier);
        List<PukePaiMian> playPaiTypeList = new ArrayList<>();
        // 移除不可用牌
        for (PukePaiMian pukePaiMian : PukePaiMian.values()) {
            if (!notPlaySet.contains(pukePaiMian)) {
                playPaiTypeList.add(pukePaiMian);
            }
        }

        List<PukePai> allPaiList = new ArrayList<>();
        // 生成牌
        int id = 0;
        for (PukePaiMian paiType : playPaiTypeList) {
            PukePai pai = new PukePai();
            pai.setId(id);
            pai.setPaiMian(paiType);
            allPaiList.add(pai);
            id++;
        }
        return allPaiList;
    }

    public static void test1() {
        PukePaiMian[] values = PukePaiMian.values();
        for (PukePaiMian pukePaiMian : values) {
            System.out.println(pukePaiMian.dianShu() + "|" + pukePaiMian.huaSe() + "|" + pukePaiMian.ordinal());
        }
    }

    public static void test2() {
        int a = 0;
        while (a < 10) {
            if (a == 5) {
                a++;
                continue;
            }
            System.out.println(a);
            a++;
        }

    }

}
