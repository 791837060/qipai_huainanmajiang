package com.dml.shisanshui.preparedapai.lipai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.comparator.PukeComparator;

/**
 * 以点数或者花色为主的理牌策略
 *
 * @author lsc
 */
public class DianshuOrHuaseShoupaiSortStrategy implements ShoupaiSortStrategy {

    @Override
    public List<List<Integer>> sortShoupai(Map<Integer, PukePai> allShoupai) {
        List<List<Integer>> sortlists = new ArrayList<>();
        // 以点数为主的理牌
        List<Integer> dianshuSort = new ArrayList<>();
        sortlists.add(dianshuSort);
        List<PukePai> dianshuSortList = new LinkedList<>();
        for (PukePai pukePai : allShoupai.values()) {
            addPukePaiByDianshu(dianshuSortList, pukePai);
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
        return sortlists;
    }

    private void addPukePaiByDianshu(List<PukePai> sortList, PukePai pai) {
        if (sortList.isEmpty()) {
            sortList.add(pai);
        } else {
            for (int i = 0; i < sortList.size(); i++) {
                int compare = comparePaimian(pai, sortList.get(i));
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

    private int comparePaimian(PukePai pai1, PukePai pai2) {
        int compare = pai1.getPaiMian().compareTo(pai2.getPaiMian());
        if (compare == 0) {
            return pai1.getId() - pai2.getId();
        } else {
            return compare;
        }
    }

    private void addPukePaiByHuase(List<PukePai> sortList, PukePai pai) {
        if (sortList.isEmpty()) {
            sortList.add(pai);
        } else {
            for (int i = 0; i < sortList.size(); i++) {
                int compare = compareHuase(pai, sortList.get(i));
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

    private int compareHuase(PukePai pai1, PukePai pai2) {
        int compare = pai1.getPaiMian().huaSe().compareTo(pai2.getPaiMian().huaSe());
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
