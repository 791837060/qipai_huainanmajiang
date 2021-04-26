package com.dml.shuangkou.preparedapai.lipai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.shuangkou.pai.paixing.comparator.PukeComparator;

/**
 * 以点数、牌数为主次的理牌策略
 *
 * @author lsc
 */
public class DianshuOrPaishuShoupaiSortStrategy implements ShoupaiSortStrategy {

    @Override
    public List<List<Integer>> sortShoupai(Map<Integer, PukePai> allShoupai) {
        List<List<Integer>> sortlists = new ArrayList<>();
        //以点数为主的理牌
        List<Integer> dianshuSort = new ArrayList<>();
        sortlists.add(dianshuSort);
        List<PukePai> dianshuSortList = new LinkedList<>();
        for (PukePai pukePai : allShoupai.values()) {
            addPukePai(dianshuSortList, pukePai);
        }
        for (PukePai pukePai : dianshuSortList) {
            dianshuSort.add(pukePai.getId());
        }
        Collections.reverse(dianshuSort);

        // 以花色为主的理牌
        List<PukePai> huaseSortList = new LinkedList<>();
        addPukePaiByHuase(huaseSortList, allShoupai);
        List<Integer> huaseSort = new ArrayList<>();
        for (PukePai pukePai : huaseSortList) {
            huaseSort.add(pukePai.getId());
        }
        sortlists.add(huaseSort);

        //以牌数为主的理牌
        List<Integer> zhangshuSort = new ArrayList<>();
        sortlists.add(zhangshuSort);
        Map<Integer, LinkedList<PukePai>> numberGroupMap = new TreeMap<>();
        LinkedList<PukePai> yiZhangList = new LinkedList<>();
        numberGroupMap.put(1, yiZhangList);
        LinkedList<PukePai> erZhangList = new LinkedList<>();
        numberGroupMap.put(2, erZhangList);
        LinkedList<PukePai> sanZhangList = new LinkedList<>();
        numberGroupMap.put(3, sanZhangList);
        LinkedList<PukePai> siZhangList = new LinkedList<>();
        numberGroupMap.put(4, siZhangList);
        LinkedList<PukePai> wuZhangList = new LinkedList<>();
        numberGroupMap.put(5, wuZhangList);
        LinkedList<PukePai> liuZhangList = new LinkedList<>();
        numberGroupMap.put(6, liuZhangList);
        LinkedList<PukePai> qiZhangList = new LinkedList<>();
        numberGroupMap.put(7, qiZhangList);
        LinkedList<PukePai> baZhangList = new LinkedList<>();
        numberGroupMap.put(8, baZhangList);
        List<PukePai> temList = new ArrayList<>();
        DianShu dianshu = null;
        for (PukePai pukePai : dianshuSortList) {
            if (dianshu == null) {
                dianshu = pukePai.getPaiMian().dianShu();
                temList.add(pukePai);
            } else if (dianshu.equals(pukePai.getPaiMian().dianShu())) {
                temList.add(pukePai);
            } else {
                List<PukePai> zhangshuList = numberGroupMap.get(temList.size());
                for (PukePai pai : temList) {
                    addPukePai(zhangshuList, pai);
                }
                temList = new ArrayList<>();
                dianshu = pukePai.getPaiMian().dianShu();
                temList.add(pukePai);
            }
        }
        List<PukePai> zhangshuList = numberGroupMap.get(temList.size());
        for (PukePai pai : temList) {
            addPukePai(zhangshuList, pai);
        }
        LinkedList<PukePai> wangpaiList = new LinkedList<>();
        for (List<PukePai> zhangshuList1 : numberGroupMap.values()) {
            for (PukePai pukePai : zhangshuList1) {
                if (pukePai.getPaiMian().equals(PukePaiMian.dawang)) {
                    wangpaiList.addLast(pukePai);
                } else if (pukePai.getPaiMian().equals(PukePaiMian.xiaowang)) {
                    wangpaiList.addFirst(pukePai);
                } else {
                    zhangshuSort.add(pukePai.getId());
                }
            }
        }
        for (PukePai pukePai : wangpaiList) {
            zhangshuSort.add(pukePai.getId());
        }
        Collections.reverse(zhangshuSort);
        return sortlists;
    }

    private void addPukePai(List<PukePai> sortList, PukePai pai) {
        if (sortList.isEmpty()) {
            sortList.add(pai);
        } else {
            for (int i = 0; i < sortList.size(); i++) {
                int compare = compare(pai, sortList.get(i));
                if (compare > 0) {
                    if (i >= sortList.size() - 1) {
                        sortList.add(pai);
                        return;
                    } else {
                        continue;
                    }
                } else {
                    sortList.add(i, pai);
                    return;
                }
            }
        }
    }

    private int compare(PukePai pai1, PukePai pai2) {
        int compare = pai1.getPaiMian().compareTo(pai2.getPaiMian());
        if (compare == 0) {
            return pai1.getId() - pai2.getId();
        } else {
            return compare;
        }
    }

    private static void addPukePaiByHuase(List<PukePai> sortList, Map<Integer, PukePai> allShoupai) {
        List<PukePai> heitaoList = new ArrayList<>();
        List<PukePai> hongxinList = new ArrayList<>();
        List<PukePai> meihuaList = new ArrayList<>();
        List<PukePai> fangkuaiList = new ArrayList<>();
        List<PukePai> wangList = new ArrayList<>();

        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getPaiMian().huaSe() != null) {
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
            } else {
                wangList.add(pukePai);
            }
        }

        PukeComparator pukeCompare = new PukeComparator();

        wangList.sort(pukeCompare::compare);
        fangkuaiList.sort(pukeCompare::compare);
        meihuaList.sort(pukeCompare::compare);
        hongxinList.sort(pukeCompare::compare);
        heitaoList.sort(pukeCompare::compare);

        sortList.addAll(wangList);
        sortList.addAll(heitaoList);
        sortList.addAll(hongxinList);
        sortList.addAll(meihuaList);
        sortList.addAll(fangkuaiList);

    }

}
