package com.anbang.qipai.biji.cqrs.c.domain.test;

import com.anbang.qipai.biji.cqrs.c.domain.BianXingWanFa;
import com.anbang.qipai.biji.cqrs.c.domain.BijiChupaiDaoCalculator;
import com.anbang.qipai.biji.cqrs.c.domain.BijiChupaiPaixingSolutionFilter;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJiesuanScore;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.dml.shisanshui.pai.paixing.comparator.PukeComparator;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.pai.paixing.comparator.TypeCodeDaoComparator;
import com.google.common.collect.ImmutableMap;
//import org.junit.jupiter.api.Test;

import java.util.*;

public class MyTest {

    public static void main(String[] args) {
//        test5();

        Map<Double,String> map=new TreeMap();

        map.put(1.1,"A");

    }

    public static void test5() {

        List<BijiPanPlayerResult> panPlayerResultList = new ArrayList<>();

        BijiPanPlayerResult result1 = new BijiPanPlayerResult();
        BijiJiesuanScore bijiJiesuanScore1 = new BijiJiesuanScore();
        bijiJiesuanScore1.setValue(10);
        result1.setJiesuanScore(bijiJiesuanScore1);
        result1.setTotalScore(330);
        result1.setPlayerId("1");
        panPlayerResultList.add(result1);

        BijiPanPlayerResult result2 = new BijiPanPlayerResult();
        BijiJiesuanScore bijiJiesuanScore2 = new BijiJiesuanScore();
        bijiJiesuanScore2.setValue(20);
        result2.setJiesuanScore(bijiJiesuanScore2);
        result2.setTotalScore(0);
        result2.setPlayerId("2");
        panPlayerResultList.add(result2);

        BijiPanPlayerResult result3 = new BijiPanPlayerResult();
        BijiJiesuanScore bijiJiesuanScore3 = new BijiJiesuanScore();
        bijiJiesuanScore3.setValue(20);
        result3.setJiesuanScore(bijiJiesuanScore3);
        result3.setTotalScore(70);
        result3.setPlayerId("3");
        panPlayerResultList.add(result3);

        BijiPanPlayerResult result4 = new BijiPanPlayerResult();
        BijiJiesuanScore bijiJiesuanScore4 = new BijiJiesuanScore();
        bijiJiesuanScore4.setValue(-50);
        result4.setJiesuanScore(bijiJiesuanScore4);
        result4.setTotalScore(0);
        result4.setPlayerId("4");
        panPlayerResultList.add(result4);


        Map<Double, BijiPanPlayerResult> map = new TreeMap<>(Double::compareTo);
        double chaScore = 0;
        for (BijiPanPlayerResult playerResult : panPlayerResultList) {
            if (playerResult.getJiesuanScore().getValue() <= 0) {
                if (playerResult.getTotalScore() <= 0) {
                    chaScore += playerResult.getTotalScore();
                    playerResult.setTotalScore(0d);
                }
            } else {
                map.put(playerResult.getJiesuanScore().getValue(), playerResult);

            }
        }

        for (Map.Entry<Double, BijiPanPlayerResult> entry : map.entrySet()) {
            if (chaScore < 0) {
                double totalScore = entry.getValue().getTotalScore();
                if (Math.abs(chaScore) > entry.getKey()) {
                    entry.getValue().setTotalScore(totalScore - entry.getKey());
                } else {
                    entry.getValue().setTotalScore(totalScore + chaScore);
                }
                chaScore += entry.getKey();
            } else {
                break;
            }
        }

    }

    private static void test1() {
        Map<Integer, PukePai> playerHand = new HashMap<>();
        Random random = new Random();
        List<PukePai> allPaiList = PokerRecognition.CreatePuker();// 生成一副牌

        while (playerHand.size() < 9) {
            int randomI = random.nextInt(allPaiList.size());
            PukePai pukePai = allPaiList.get(randomI);
            if (!playerHand.containsKey(randomI)) {
                playerHand.put(pukePai.getId(), pukePai);
            }
        }

        BijiChupaiDaoCalculator bijiChupaiDaoCalculator = new BijiChupaiDaoCalculator();
        List<Dao> daoList = bijiChupaiDaoCalculator.generateAllPaixingSolution(playerHand);//计算当前手牌可打出所有牌型

        BijiChupaiPaixingSolutionFilter filter = new BijiChupaiPaixingSolutionFilter();
        filter.setDaoComparator(new TypeCodeDaoComparator());//根据点数花色比大小
        List<PaixingSolution> solutions1 = filter.filter(playerHand, daoList);//计算牌型的优先度

        for (PukePai pukePai : playerHand.values()) {
            System.out.println(pukePai.getPaiMian());
        }

        for (Dao dao : daoList) {
            System.out.println(dao.getPaixing() + "|" + dao.getPukePaiList().get(0).getPaiMian() + " " + dao.getPukePaiList().get(1).getPaiMian() + " " + dao.getPukePaiList().get(2).getPaiMian());
        }

        //输出手牌
        for (PaixingSolution p : solutions1) {
            System.out.println(p.getToudao().getPaixing().name() + "|" + p.getZhongdao().getPaixing().name() + "|" + p.getWeidao().getPaixing().name() + "---" +
                    p.getToudao().getPukePaiList().get(0).getPaiMian() + " " + p.getToudao().getPukePaiList().get(1).getPaiMian() + " " + p.getToudao().getPukePaiList().get(2).getPaiMian() + "|" +
                    p.getZhongdao().getPukePaiList().get(0).getPaiMian() + " " + p.getZhongdao().getPukePaiList().get(1).getPaiMian() + " " + p.getZhongdao().getPukePaiList().get(2).getPaiMian() + "|" +
                    p.getWeidao().getPukePaiList().get(0).getPaiMian() + " " + p.getWeidao().getPukePaiList().get(1).getPaiMian() + " " + p.getWeidao().getPukePaiList().get(2).getPaiMian());
        }
    }

    private static void test2() {
        boolean flag = true;
        int count = 0;
        while (flag && count <= 100000) {
            System.out.println(count);
            count++;
            Map<Integer, PukePai> playerHand = new HashMap<>();
            Random random = new Random();
            List<PukePai> allPaiList = PokerRecognition.CreatePuker();// 生成一副牌

            //给一个玩家发牌 手牌不能重复
            while (playerHand.size() < 9) {
                int randomI = random.nextInt(allPaiList.size());
                PukePai pukePai = allPaiList.get(randomI);
                if (!playerHand.containsKey(randomI)) {
                    playerHand.put(pukePai.getId(), pukePai);
                }
            }

            BijiChupaiDaoCalculator bijiChupaiDaoCalculator = new BijiChupaiDaoCalculator();
            bijiChupaiDaoCalculator.setBihuase(true);//比花色 开
            bijiChupaiDaoCalculator.setBx(BianXingWanFa.baibian);//变形 开

            List<Dao> daoList = bijiChupaiDaoCalculator.generateAllPaixingSolution(playerHand);//根据手牌算出所有可出方案

            BijiChupaiPaixingSolutionFilter filter = new BijiChupaiPaixingSolutionFilter();
            filter.setDaoComparator(new TypeCodeDaoComparator());//根据点数花色比大小
            List<PaixingSolution> solutions1 = filter.filter(playerHand, daoList);//计算牌型的优先度

            for (PaixingSolution paixingSolution : solutions1) {
                Dao toudao = paixingSolution.getToudao();
                Dao zhongdao = paixingSolution.getZhongdao();
                Dao weidao = paixingSolution.getWeidao();

                int[] dianshuArray = new int[14];
                for (PukePai pukePai : playerHand.values()) {
                    PukePaiMian paiMian = pukePai.getPaiMian();
                    dianshuArray[paiMian.dianShu().ordinal()]++;
                }
                int index = 0;
                while (true) {
                    if (dianshuArray[index] != 0) {
                        break;
                    } else {
                        index++;
                    }
                }
                int lianxu = 0;
                for (; index < dianshuArray.length; index++) {
                    if (dianshuArray[index] == 1) {
                        lianxu++;
                    } else {
                        break;
                    }
                }

//                if ((toudao.getPaixing().equals(Paixing.shunzi) || toudao.getPaixing().equals(Paixing.tonghuashun))
//                        && (zhongdao.getPaixing().equals(Paixing.shunzi) || zhongdao.getPaixing().equals(Paixing.tonghuashun))
//                        && (weidao.getPaixing().equals(Paixing.shunzi) || weidao.getPaixing().equals(Paixing.tonghuashun))) {

//                if ((toudao.getPaixing().equals(Paixing.tonghuashun)) && (zhongdao.getPaixing().equals(Paixing.tonghuashun)) && (weidao.getPaixing().equals(Paixing.tonghuashun))) {

                if (lianxu == 9) {

                    System.out.println("玩家手牌：");
                    for (PukePai pukePai : playerHand.values()) {
                        System.out.println(pukePai.getPaiMian());
                    }
                    System.out.println("牌型：");
                    System.out.println(toudao.getPaixing() + "|" + zhongdao.getPaixing() + "|" + weidao.getPaixing());
                    System.out.println("扑克牌：");
                    System.out.println(toudao.getPukePaiList().get(0).getPaiMian() + " " + toudao.getPukePaiList().get(1).getPaiMian() + " " + toudao.getPukePaiList().get(2).getPaiMian() + "|" +
                            zhongdao.getPukePaiList().get(0).getPaiMian() + " " + zhongdao.getPukePaiList().get(1).getPaiMian() + " " + zhongdao.getPukePaiList().get(2).getPaiMian() + "|" +
                            weidao.getPukePaiList().get(0).getPaiMian() + " " + weidao.getPukePaiList().get(1).getPaiMian() + " " + weidao.getPukePaiList().get(2).getPaiMian());

                    flag = false;
                }
            }
        }

    }

    public static void test3() {
        List<PukePai> allPaiList = PokerRecognition.CreatePuker();// 生成一副牌
        Map<Integer, PukePai> allShoupai = new HashMap<>();

        PukePai pukePai = allPaiList.get(1);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(7);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(12);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(19);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(23);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(26);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(29);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(33);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(47);
        allShoupai.put(pukePai.getId(), pukePai);


        BijiChupaiDaoCalculator bijiChupaiDaoCalculator = new BijiChupaiDaoCalculator();

        List<Dao> daoList = bijiChupaiDaoCalculator.generateAllPaixingSolution(allShoupai);

        BijiChupaiPaixingSolutionFilter filter = new BijiChupaiPaixingSolutionFilter();
        filter.setDaoComparator(new TypeCodeDaoComparator());
        List<PaixingSolution> solutions = filter.filter(allShoupai, daoList);

        for (PukePai pai : allShoupai.values()) {
            System.out.println(pai.getPaiMian());
        }

        for (Dao dao : daoList) {
            System.out.println(dao.getPaixing() + "|" + dao.getPukePaiList().get(0).getPaiMian() + " " + dao.getPukePaiList().get(1).getPaiMian() + " " + dao.getPukePaiList().get(2).getPaiMian());
        }

        //输出手牌
        for (PaixingSolution p : solutions) {
            System.out.println(p.getToudao().getPaixing().name() + "|" + p.getZhongdao().getPaixing().name() + "|" + p.getWeidao().getPaixing().name() + "---" +
                    p.getToudao().getPukePaiList().get(0).getPaiMian() + " " + p.getToudao().getPukePaiList().get(1).getPaiMian() + " " + p.getToudao().getPukePaiList().get(2).getPaiMian() + "|" +
                    p.getZhongdao().getPukePaiList().get(0).getPaiMian() + " " + p.getZhongdao().getPukePaiList().get(1).getPaiMian() + " " + p.getZhongdao().getPukePaiList().get(2).getPaiMian() + "|" +
                    p.getWeidao().getPukePaiList().get(0).getPaiMian() + " " + p.getWeidao().getPukePaiList().get(1).getPaiMian() + " " + p.getWeidao().getPukePaiList().get(2).getPaiMian());
        }

        int[] dianshuArray = new int[14];
        for (PukePai pai : allShoupai.values()) {
            PukePaiMian paiMian = pai.getPaiMian();
            dianshuArray[paiMian.dianShu().ordinal()]++;
        }
        int index = 0;
        while (true) {
            if (dianshuArray[index] != 0) {
                break;
            } else {
                index++;
            }
        }
        int lianxu = 0;
        for (; index < dianshuArray.length; index++) {
            if (dianshuArray[index] == 1) {
                lianxu++;
            } else {
                break;
            }
        }
        if (lianxu == 9) {
            System.out.println("全顺");
        }


    }

    public static void test4() {
        List<PukePai> allPaiList = PokerRecognition.CreatePuker();// 生成一副牌
        Map<Integer, PukePai> allShoupai = new HashMap<>();

        PukePai pukePai = allPaiList.get(1);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(7);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(12);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(19);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(23);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(26);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(29);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(33);
        allShoupai.put(pukePai.getId(), pukePai);
        pukePai = allPaiList.get(47);
        allShoupai.put(pukePai.getId(), pukePai);

        List<PukePai> huaseSortList = new LinkedList<>();
        addPukePaiByHuase(huaseSortList, allShoupai);

    }

    private static void addPukePaiByHuase(List<PukePai> sortList, Map<Integer, PukePai> allShoupai) {
        List<PukePai> heitaoList = new ArrayList<>();
        List<PukePai> hongxinList = new ArrayList<>();
        List<PukePai> meihuaList = new ArrayList<>();
        List<PukePai> fangkuaiList = new ArrayList<>();

        for (PukePai pukePai : allShoupai.values()) {
            switch (pukePai.getPaiMian().huaSe()) {
                case fangkuai:
                    fangkuaiList.add(pukePai);
                    break;
                case meihua:
                    meihuaList.add(pukePai);
                    break;
                case hongxin:
                    hongxinList.add(pukePai);
                    break;
                case heitao:
                    heitaoList.add(pukePai);
                    break;
            }
        }

        PukeComparator pukeCompare = new PukeComparator();

        fangkuaiList.sort(pukeCompare::compare);
        meihuaList.sort(pukeCompare::compare);
        hongxinList.sort(pukeCompare::compare);
        heitaoList.sort(pukeCompare::compare);

        sortList.addAll(heitaoList);
        sortList.addAll(hongxinList);
        sortList.addAll(meihuaList);
        sortList.addAll(fangkuaiList);

    }

}
