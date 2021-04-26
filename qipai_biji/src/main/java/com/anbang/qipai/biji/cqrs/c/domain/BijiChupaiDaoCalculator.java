package com.anbang.qipai.biji.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.player.action.ChupaiDaoCalculator;

/**
 * 大菠萝牌型计算器
 *
 * @author lsc
 */
public class BijiChupaiDaoCalculator implements ChupaiDaoCalculator {

    private boolean bihuase = true;                     //是否比花色大小
    private BianXingWanFa bx = BianXingWanFa.bubian;    //是否变形（大小王）

    /**
     * 生成所有牌型方案
     *
     * @param allShoupai 玩家手牌
     */
    @Override
    public List<Dao> generateAllPaixingSolution(Map<Integer, PukePai> allShoupai) {
        Set<Integer> integers = allShoupai.keySet();
        List<PukePai> handList = new ArrayList<>();
        for (Integer j : integers) {
            handList.add(allShoupai.get(j));
        }

        List<Dao> daoList = new ArrayList<>();
        calculateDao3(handList, daoList);
        return daoList;
    }

    /**
     * 计算3张牌可出的牌型
     *
     * @param handList 手牌集合
     * @param daoList  所有可出的牌型
     */
    private void calculateDao3(List<PukePai> handList, List<Dao> daoList) {
        for (int i = 0; i < handList.size() - 2; i++) {
            for (int j = i + 1; j < handList.size() - 1; j++) {
                for (int k = j + 1; k < handList.size(); k++) {
                    Dao dao = new Dao();
                    dao.getPukePaiList().add(handList.get(i));
                    dao.getPukePaiList().add(handList.get(j));
                    dao.getPukePaiList().add(handList.get(k));
                    dao.calculateIndex();//拼接牌面字符串 小于10的前一位补0
                    DaoTypeCodeCalculator.calculateDaoTypeCode(dao, bihuase, bx);//确定牌型
                    daoList.add(dao);
                }
            }
        }
    }

    /**
     * 计算5张牌可出的牌型
     *
     * @param handList 手牌集合
     * @param daoList  所有可出的牌型
     */
    private void calculateDao5(List<PukePai> handList, List<Dao> daoList) {
        for (int i = 0; i < handList.size() - 4; i++) {
            for (int j = i + 1; j < handList.size() - 3; j++) {
                for (int k = j + 1; k < handList.size() - 2; k++) {
                    for (int l = k + 1; l < handList.size() - 1; l++) {
                        for (int m = l + 1; m < handList.size(); m++) {
                            Dao dao = new Dao();
                            dao.getPukePaiList().add(handList.get(i));
                            dao.getPukePaiList().add(handList.get(j));
                            dao.getPukePaiList().add(handList.get(k));
                            dao.getPukePaiList().add(handList.get(l));
                            dao.getPukePaiList().add(handList.get(m));
                            dao.calculateIndex();//拼接牌面字符串 小于10的前一位补0
                            DaoTypeCodeCalculator.calculateDaoTypeCode(dao, bihuase, bx);//确定牌型
                            daoList.add(dao);
                        }
                    }
                }
            }
        }
    }

    private void calculateSanDun(List<Dao> daoList, List<Integer> paiIdList, Set<Integer> removed, int length, int index, Dao dao, Map<Integer, PukePai> allShoupai) {
        if (length < 3) {
            for (int i = 0 + index; i < paiIdList.size() + length - 2; i++) {
                Dao newDao = new Dao();
                newDao.getPukePaiList().addAll(dao.getPukePaiList());
                Set<Integer> newRemoved = new HashSet<>(removed);
                newRemoved.add(paiIdList.get(i));
                newDao.getPukePaiList().add(allShoupai.get(paiIdList.get(i)));
                calculateSanDun(daoList, paiIdList, newRemoved, length + 1, i + 1, newDao, allShoupai);
            }
        } else {
            dao.calculateIndex();
            DaoTypeCodeCalculator.calculateDaoTypeCode(dao, bihuase, bx);
            daoList.add(dao);
        }
    }

    private void calculateWuDun(List<Dao> daoList, List<Integer> paiIdList, Set<Integer> removed, int length, int index, Dao dao, Map<Integer, PukePai> allShoupai) {
        if (length < 5) {
            for (int i = 0 + index; i < paiIdList.size() + length - 4; i++) {
                Dao newDao = new Dao();
                newDao.getPukePaiList().addAll(dao.getPukePaiList());
                Set<Integer> newRemoved = new HashSet<>(removed);
                newRemoved.add(paiIdList.get(i));
                newDao.getPukePaiList().add(allShoupai.get(paiIdList.get(i)));
                calculateWuDun(daoList, paiIdList, newRemoved, length + 1, i + 1, newDao, allShoupai);
            }
        } else {
            dao.calculateIndex();
            DaoTypeCodeCalculator.calculateDaoTypeCode(dao, bihuase, bx);
            daoList.add(dao);
        }
    }

    public boolean isBihuase() {
        return bihuase;
    }

    public void setBihuase(boolean bihuase) {
        this.bihuase = bihuase;
    }

    public BianXingWanFa getBx() {
        return bx;
    }

    public void setBx(BianXingWanFa bx) {
        this.bx = bx;
    }

}
