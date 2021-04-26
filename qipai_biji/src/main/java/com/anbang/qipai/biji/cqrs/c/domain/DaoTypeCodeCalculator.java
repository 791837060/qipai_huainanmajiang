package com.anbang.qipai.biji.cqrs.c.domain;

import com.dml.shisanshui.pai.DianShu;
import com.dml.shisanshui.pai.HuaSe;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 牌型编码计算器
 *
 * @author lsc
 */
public class DaoTypeCodeCalculator {
    /**
     * 计算当前道牌型
     *
     * @param dao     当前道
     * @param bihuase 是否比花色
     * @param bx      百变类型
     */
    public static void calculateDaoTypeCode(Dao dao, boolean bihuase, BianXingWanFa bx) {
        List<PukePaiMian> paiMianList = new ArrayList<>();//普通牌集合
        for (PukePai pukePai : dao.getPukePaiList()) {
            paiMianList.add(pukePai.getPaiMian());
        }
        calculateDaoTypeCodeWithoutWangpai(dao, paiMianList);
    }

    /**
     * 计算当前道的牌型（无王牌）
     *
     * @param dao         当前道
     * @param paiMianList 普通牌集合
     */
    private static void calculateDaoTypeCodeWithoutWangpai(Dao dao, List<PukePaiMian> paiMianList) {
        long typeCode;
        Paixing paixing = calculatePaixing(paiMianList);//计算当前道的牌型
        typeCode = paixing.ordinal() + 1;

        boolean minShunzi = false;
        if (Paixing.shunzi.equals(paixing) || Paixing.tonghuashun.equals(paixing)) {
            int minShunziCount = 0;
            for (PukePaiMian pukePaiMian : paiMianList) {
                if (pukePaiMian.dianShu().equals(DianShu.A)) minShunziCount++;
                if (pukePaiMian.dianShu().equals(DianShu.er)) minShunziCount++;
                if (pukePaiMian.dianShu().equals(DianShu.san)) minShunziCount++;
            }
            if (minShunziCount == 3) {
                minShunzi = true;
            }
        }

        if (minShunzi) {
            PukePaiMian pukeA = null;
            PukePaiMian pukeEr = null;
            PukePaiMian pukeSan = null;
            for (PukePaiMian pukePaiMian : paiMianList) {
                if (pukePaiMian.dianShu().equals(DianShu.A)) pukeA = pukePaiMian;
                if (pukePaiMian.dianShu().equals(DianShu.er)) pukeEr = pukePaiMian;
                if (pukePaiMian.dianShu().equals(DianShu.san)) pukeSan = pukePaiMian;
            }
            paiMianList.clear();
            paiMianList.add(pukeSan);
            paiMianList.add(pukeEr);
            paiMianList.add(pukeA);
        } else {
            paiMianList = lipai(paiMianList);
        }

        for (PukePaiMian paimian : paiMianList) {
            typeCode = typeCode << 4;
            if (minShunzi && paimian.dianShu().equals(DianShu.A)) {
                typeCode += DianShu.minA.ordinal();
            } else {
                typeCode += paimian.dianShu().ordinal();
            }
            typeCode = typeCode << 2;
        }
        // 如果是三张牌末尾补零，因为有“相公”，所以取最大值
        if (paiMianList.size() == 3) {
            typeCode = (typeCode + 1) << 12;
            typeCode -= 1;
        }
        typeCode = typeCode << 4;
        typeCode += 2;
        typeCode += (paiMianList.get(0).huaSe().ordinal() + 1);
        dao.setTypeCode(typeCode);
        dao.setPaixing(paixing);
        //排序
        List<PukePai> pukepaiList = new ArrayList<>();
        for (PukePaiMian paimian : paiMianList) {
            for (PukePai pukepai : dao.getPukePaiList()) {
                if (paimian.equals(pukepai.getPaiMian())) {
                    pukepaiList.add(pukepai);
                    break;
                }
            }
        }
        dao.setPukePaiList(pukepaiList);
    }

    /**
     * 计算当前道的牌型（有王牌）
     *
     * @param dao         当前道
     * @param paiMianList 普通牌集合
     * @param wangpaiList 大小王集合
     * @param bihuase     是否比花色
     */
    private static void calculateDaoTypeCodeWithWangpai(Dao dao, List<PukePaiMian> paiMianList, List<PukePaiMian> wangpaiList, boolean bihuase) {
        long maxTypeCode = 0;
        Paixing bestPaixing = Paixing.wulong;//默认牌型为乌龙
        List<PukePaiMian> bestPaiMianList = null;
        int wangpaiCount = wangpaiList.size();//王牌数量
        int maxZuheCode = (int) Math.pow(52, wangpaiCount);
        int[] modArray = new int[wangpaiCount];
        for (int i = 0; i < wangpaiCount; i++) {//
            modArray[i] = (int) Math.pow(52, wangpaiCount - 1 - i);
        }
        for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {//遍历所有普通牌型 替换王牌
            WangjiesuanPai[] wangpaiDangPaiArray = new WangjiesuanPai[wangpaiCount];
            int temp = zuheCode;
            int previousGuipaiDangIdx = 0;
            for (int i = 0; i < wangpaiCount; i++) {
                int mod = modArray[i];
                int shang = temp / mod;
                if (shang >= previousGuipaiDangIdx) {
                    int yu = temp % mod;
                    wangpaiDangPaiArray[i] = new WangjiesuanPai(wangpaiList.get(i), PukePaiMian.values()[shang]);
                    temp = yu;
                    previousGuipaiDangIdx = shang;
                } else {
                    wangpaiDangPaiArray = null;
                    break;
                }
            }
            if (wangpaiDangPaiArray != null) {
                long typeCode = 0;
                List<PukePaiMian> pukePaiList = new ArrayList<>(paiMianList);
                for (WangjiesuanPai jiesuanPai : wangpaiDangPaiArray) {
                    pukePaiList.add(jiesuanPai.getJiesuanpai());
                }
                Paixing paixing = calculatePaixing(pukePaiList);//计算牌型
                HashSet<PukePaiMian> hashSet = new HashSet<>(pukePaiList);
                if (paixing.equals(Paixing.tonghua) && pukePaiList.size() != hashSet.size()) {
                    continue;
                }
                typeCode = paixing.ordinal() + 1;
                pukePaiList = lipai(pukePaiList);//理牌
                boolean quanqiutong = false;//全球通A,2,3,4,5
                if (Paixing.tonghuashun.equals(paixing) || Paixing.shunzi.equals(paixing)) {
                    if ((pukePaiList.get(0).dianShu().ordinal() - pukePaiList.get(1).dianShu().ordinal()) > 1) {
                        quanqiutong = true;
                    }
                }
                for (PukePaiMian paimian : pukePaiList) {
                    typeCode = typeCode << 4;
                    if (quanqiutong && paimian.dianShu().equals(DianShu.A)) {
                        typeCode += DianShu.minA.ordinal();
                    } else {
                        typeCode += paimian.dianShu().ordinal();
                    }
                    typeCode = typeCode << 2;
                }
                // 如果是三张牌末尾补零，因为有“相公”，所以取最大值
                if (pukePaiList.size() == 3) {
                    typeCode = (typeCode + 1) << 12;
                    typeCode -= 1;
                }
                if (bihuase) {
                    typeCode = typeCode << 4;
                    if (wangpaiCount == 1 && wangpaiList.get(0).equals(PukePaiMian.dawang)) {
                        typeCode++;
                    }
                }
                if (typeCode > maxTypeCode) {
                    maxTypeCode = typeCode;
                    bestPaixing = paixing;
                    bestPaiMianList = pukePaiList;
                }
            }
        }
        dao.setTypeCode(maxTypeCode);
        dao.setPaixing(bestPaixing);
        //排序
        List<PukePai> pukepaiList = new ArrayList<>();
        List<PukePai> wangList = new ArrayList<>();
        for (PukePai pukepai : dao.getPukePaiList()) {
            if (PukePaiMian.dawang.equals(pukepai.getPaiMian()) || PukePaiMian.xiaowang.equals(pukepai.getPaiMian())) {
                wangList.add(pukepai);
            }
        }
        for (PukePaiMian paimian : bestPaiMianList) {
            boolean has = false;
            int length = dao.getPukePaiList().size();
            for (int i = 0; i < length; i++) {
                PukePai pukepai = dao.getPukePaiList().get(i);
                if (paimian.equals(pukepai.getPaiMian())) {
                    has = true;
                    pukepaiList.add(dao.getPukePaiList().remove(i));
                    break;
                }
            }
            if (!has && !wangList.isEmpty()) {
                pukepaiList.add(wangList.remove(0));
            }
        }
        pukepaiList.addAll(wangList);
        dao.setPukePaiList(pukepaiList);
    }

    /**
     * 计算当前道的牌型
     *
     * @param pukePaiList 当前道牌面
     */
    private static Paixing calculatePaixing(List<PukePaiMian> pukePaiList) {
        int[] dianshuArray = new int[14];//所有牌面数组 将道内所有的牌面点数设置为1 没有默认为0
        for (PukePaiMian paimian : pukePaiList) {
            dianshuArray[paimian.dianShu().ordinal()]++;
        }
        int duizi = countDuizi(dianshuArray);
        boolean shunzi = isShunzi(dianshuArray);
        boolean tonghua = isTonghua(pukePaiList);
        int tongDianshu = countMaxDianshu(dianshuArray);
        Paixing paixing = Paixing.wulong;//默认为乌龙牌型
        if (tongDianshu == 3) {
            paixing = Paixing.santiao;
        } else if (tonghua && shunzi) {
            paixing = Paixing.tonghuashun;
        } else if (tonghua) {
            paixing = Paixing.tonghua;
        } else if (shunzi) {
            paixing = Paixing.shunzi;
        } else if (duizi == 1) {
            paixing = Paixing.duizi;
        }
        return paixing;
    }

    /**
     * 理牌
     * 将当前牌型根据花色和点数进行由大到小排序
     *
     * @param paiMianList 普通牌集合
     * @return
     */
    private static List<PukePaiMian> lipai(List<PukePaiMian> paiMianList) {
        LinkedList<PukePaiMian> sortedList = new LinkedList<>();
        int[] dianshuArray = new int[16];
        for (PukePaiMian paimian : paiMianList) {
            dianshuArray[paimian.dianShu().ordinal()]++;
        }
        LinkedList<PukePaiMian> sortedList1 = new LinkedList<>();
        LinkedList<PukePaiMian> sortedList2 = new LinkedList<>();
        LinkedList<PukePaiMian> sortedList3 = new LinkedList<>();
        for (PukePaiMian paimian : paiMianList) {
            int n = dianshuArray[paimian.dianShu().ordinal()];
            switch (n) {
                case 1:
                    addPaiMian(sortedList1, paimian);
                    break;
                case 2:
                    addPaiMian(sortedList2, paimian);
                    break;
                case 3:
                    addPaiMian(sortedList3, paimian);
                    break;
            }
        }
        sortedList.addAll(sortedList3);
        sortedList.addAll(sortedList2);
        sortedList.addAll(sortedList1);
        return sortedList;
    }

    /**
     * 根据扑克牌的张数添加进不同集合
     *
     * @param sortList 张数集合（1张集合，2张集合...5张集合）
     * @param paimian  单张扑克牌
     */
    private static void addPaiMian(List<PukePaiMian> sortList, PukePaiMian paimian) {
        if (sortList.isEmpty()) {
            sortList.add(paimian);
        } else {
            for (int i = 0; i < sortList.size(); i++) {
                int compare = paimian.compareTo(sortList.get(i));
                if (compare > 0) {
                    sortList.add(i, paimian);
                    return;
                } else {
                    if (i >= sortList.size() - 1) {
                        sortList.add(paimian);
                        return;
                    }
                }
            }
        }

    }

    /**
     * 是否是同花
     *
     * @param pukePaiList 当前道牌面
     */
    private static boolean isTonghua(List<PukePaiMian> pukePaiList) {
        HuaSe huase = pukePaiList.get(0).huaSe();
        for (PukePaiMian paimian : pukePaiList) {
            if (!huase.equals(paimian.huaSe())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是顺子
     *
     * @param dianshuArray 牌面数组
     */
    private static boolean isShunzi(int[] dianshuArray) {
        int firstIndex = 0;
        while (true) {  //获取首个牌面下标
            if (dianshuArray[firstIndex] > 0) {
                break;
            }
            firstIndex++;
            if (firstIndex > dianshuArray.length) {
                break;
            }
        }
        int lianxuCount = 0;
        for (int i = 0; i + firstIndex < dianshuArray.length; i++) {    //计算连续数
            if (dianshuArray[i + firstIndex] > 0) {
                lianxuCount++;
            } else {
                break;
            }
        }
        if (dianshuArray[1] > 0 && dianshuArray[2] > 0 && dianshuArray[13] > 0) {
            return true;
        }

        return lianxuCount == 3;
    }

    /**
     * 计算相同牌面点数数量
     *
     * @param dianshuArray 牌面数组
     */
    private static int countMaxDianshu(int[] dianshuArray) {
        int max = 0;
        for (int i : dianshuArray) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * 计算对子数量
     *
     * @param dianshuArray 判断牌面连续数组
     */
    private static int countDuizi(int[] dianshuArray) {
        int duiziCount = 0;
        for (int i : dianshuArray) {
            if (i > 1) {
                duiziCount++;
            }
        }
        return duiziCount;
    }

}