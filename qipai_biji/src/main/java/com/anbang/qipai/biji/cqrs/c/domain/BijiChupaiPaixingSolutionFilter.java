package com.anbang.qipai.biji.cqrs.c.domain;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;

import java.util.*;

public class BijiChupaiPaixingSolutionFilter implements ChupaiPaixingSolutionFilter {

    private DaoComparator daoComparator;    //道比较器
    private boolean yitiaolong = false;     // 一条龙

    @Override
    public List<PaixingSolution> filter(Map<Integer, PukePai> allShoupai, List<Dao> daoList) {
        List<PaixingSolution> solutionList = new ArrayList<>();
        List<PaixingSolution> solutionList1 = new ArrayList<>();
        List<PaixingSolution> solutionList2 = new ArrayList<>();

        daoList.sort((dao1, dao2) -> {
            if (dao1.getTypeCode() > dao2.getTypeCode()) {
                return -1;
            } else if (dao1.getTypeCode() == dao2.getTypeCode()) {
                return 0;
            } else {
                return 1;
            }
        });

        List<Dao> dao3List = new ArrayList<>(daoList);

        PaixingSolution solution;
        Dao weiDao, zhongDao, toudaoDao;
        //计算提示出牌
        for (int i = 0; i < dao3List.size(); i++) {
            weiDao = dao3List.get(i);
            if (verify(null, null, weiDao)) {   //验证尾道
                for (int j = i + 1; j < dao3List.size(); j++) {
                    zhongDao = dao3List.get(j);
                    if (verify(null, zhongDao, weiDao)) {   //验证中道
                        for (int k = 0; k < dao3List.size(); k++) {
                            toudaoDao = dao3List.get(k);
                            if (verify(toudaoDao, zhongDao, weiDao)) {  //验证首道
                                solution = new PaixingSolution();
                                solution.setWeidao(weiDao);
                                solution.setZhongdao(zhongDao);
                                solution.setToudao(toudaoDao);
                                solutionList.add(solution);
                                break;
                            }
                        }
                    }
                }
            }
        }
        PaixingSolution indexSolution;
        while (!solutionList.isEmpty()) {//获取当前最大道的牌型 将比这个牌型小的选择剔除
            int i = 0;
            indexSolution = solutionList.get(0);
            for (; i < solutionList.size(); i++) {
                if (solutionList.get(i).getWeidao().getPaixing() == indexSolution.getWeidao().getPaixing()
                        && solutionList.get(i).getZhongdao().getPaixing() == indexSolution.getZhongdao().getPaixing()
                        && solutionList.get(i).getToudao().getPaixing() == indexSolution.getToudao().getPaixing()) {
                    solutionList1.add(solutionList.get(i));
                    solutionList.remove(i--);
                }
            }
            maxSolution(solutionList1, solutionList2, indexSolution);
        }
        //对推荐组合排序 优先尾道大的排前边 尾道相同时中道大的排前边 尾道中道都相同时 首道大的排前边
        solutionList2.sort((paixingSolution1, paixingSolution2) -> {
            if (paixingSolution1.getWeidao().getPaixing().ordinal() > paixingSolution2.getWeidao().getPaixing().ordinal()) {
                return -1;
            } else if (paixingSolution1.getWeidao().getPaixing().ordinal() == paixingSolution2.getWeidao().getPaixing().ordinal()) {
                if (paixingSolution1.getZhongdao().getPaixing().ordinal() > paixingSolution2.getZhongdao().getPaixing().ordinal()) {
                    return -1;
                } else if (paixingSolution1.getZhongdao().getPaixing().ordinal() == paixingSolution2.getZhongdao().getPaixing().ordinal()) {
                    if (paixingSolution1.getToudao().getPaixing().ordinal() > paixingSolution2.getToudao().getPaixing().ordinal()) {
                        return -1;
                    } else return 1;
                } else return 1;
            } else return 1;

        });
        for (int i = 1; i < solutionList2.size(); i++) {
            if (solutionList2.get(i - 1).getWeidao().getPaixing().equals(solutionList2.get(i).getWeidao().getPaixing())//如果头道、中道牌型是乌龙 尾道与集合中上一个牌型相同就移除上一个牌型
                    && solutionList2.get(i).getToudao().getPaixing().equals(Paixing.wulong)
                    && solutionList2.get(i).getZhongdao().getPaixing().equals(Paixing.wulong)) {
                solutionList2.remove(i--);
            } else if (solutionList2.get(i - 1).getWeidao().getPaixing().equals(solutionList2.get(i).getWeidao().getPaixing())//如果头道、中道牌型相同 尾道是乌龙 移除
                    && solutionList2.get(i).getToudao().getPaixing().equals(Paixing.wulong)) {
                solutionList2.remove(i--);
            }
        }
        return solutionList2;
    }

    /**
     * 计算推荐组合
     * 集合内每个道牌的型都一样，判断顺序是头、中、尾，如果集合内有其中一道比当前牌型最大组合大，就替换最大组合
     *
     * @param solutionList      推荐组合中每道牌型都相同的集合
     * @param finalsolutionList 最终推荐组合
     * @param indexSolution     推荐组合中当前牌型最大的组合
     */
    private void maxSolution(List<PaixingSolution> solutionList, List<PaixingSolution> finalsolutionList, PaixingSolution indexSolution) {
        for (int i = 0; i < solutionList.size(); i++) {
            if (solutionList.get(i).getToudao().getTypeCode() > indexSolution.getToudao().getTypeCode()) {//先从头道开始 获取当前牌型头道最大的牌
                indexSolution = solutionList.get(i);
            } else if (solutionList.get(i).getToudao().getTypeCode() == indexSolution.getToudao().getTypeCode()) {//其次中道
                if (solutionList.get(i).getZhongdao().getTypeCode() > indexSolution.getZhongdao().getTypeCode()) {
                    indexSolution = solutionList.get(i);
                } else if (solutionList.get(i).getZhongdao().getTypeCode() == indexSolution.getZhongdao().getTypeCode()) {//最后尾道
                    if (solutionList.get(i).getWeidao().getTypeCode() > indexSolution.getWeidao().getTypeCode()) {
                        indexSolution = solutionList.get(i);
                    } else continue;
                } else continue;
            } else continue;
        }
        solutionList.clear();
        finalsolutionList.add(indexSolution);
    }

    /**
     * 校验道
     * 检查道里牌的数量和不同道里是否有重复的牌
     *
     * @param toudao   头道
     * @param zhongdao 中道
     * @param weidao   尾道
     * @return
     */
    private boolean verify(Dao toudao, Dao zhongdao, Dao weidao) {
        Map<Integer, PukePai> allPai = new HashMap<>();
        if (toudao != null) {
            if (toudao.getPukePaiList().size() != 3 || daoComparator.compare(toudao, zhongdao) > 0) {
                return false;
            }
            for (PukePai pukePai : toudao.getPukePaiList()) {
                if (allPai.containsKey(pukePai.getId())) {
                    return false;
                }
                allPai.put(pukePai.getId(), pukePai);
            }
        }
        if (zhongdao != null) {
            if (zhongdao.getPukePaiList().size() != 3 || daoComparator.compare(zhongdao, weidao) > 0) {
                return false;
            }
            for (PukePai pukePai : zhongdao.getPukePaiList()) {
                if (allPai.containsKey(pukePai.getId())) {
                    return false;
                }
                allPai.put(pukePai.getId(), pukePai);
            }
        }
        if (weidao != null) {
            if (weidao.getPukePaiList().size() != 3) {
                return false;
            }
            for (PukePai pukePai : weidao.getPukePaiList()) {
                if (allPai.containsKey(pukePai.getId())) {
                    return false;
                }
                allPai.put(pukePai.getId(), pukePai);
            }
        }
        return true;
    }

    public DaoComparator getDaoComparator() {
        return daoComparator;
    }

    public void setDaoComparator(DaoComparator daoComparator) {
        this.daoComparator = daoComparator;
    }

    public boolean isYitiaolong() {
        return yitiaolong;
    }

    public void setYitiaolong(boolean yitiaolong) {
        this.yitiaolong = yitiaolong;
    }

}
