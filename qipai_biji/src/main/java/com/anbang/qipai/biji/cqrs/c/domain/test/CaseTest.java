package com.anbang.qipai.biji.cqrs.c.domain.test;

import java.util.*;

import com.dml.shisanshui.pai.DianShu;
import com.dml.shisanshui.pai.HuaSe;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.PukePaiMian;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;

public class CaseTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        List<PukePai> allPaiList = PokerRecognition.CreatePuker();// 生成一副牌

//        List<PukePaiMian> paiMianList = new ArrayList<>();
//        paiMianList.add(PukePaiMian.heitaoQ);
//        paiMianList.add(PukePaiMian.heitaoK);
//        paiMianList.add(PukePaiMian.heitaoA);
//        Dao dao = new Dao();
//        dao.getPukePaiList().add(allPaiList.get(43));
//        dao.getPukePaiList().add(allPaiList.get(47));
//        dao.getPukePaiList().add(allPaiList.get(51));
//        dao.calculateIndex();//拼接牌面字符串 小于10的前一位补0
//        calculateDaoTypeCodeWithoutWangpai(dao, paiMianList);


        List<PukePaiMian> paiMianList2 = new ArrayList<>();
        paiMianList2.add(PukePaiMian.heitaoer);
        paiMianList2.add(PukePaiMian.heitaosan);
        paiMianList2.add(PukePaiMian.heitaoA);
        Dao dao2 = new Dao();
        dao2.getPukePaiList().add(allPaiList.get(3));
        dao2.getPukePaiList().add(allPaiList.get(7));
        dao2.getPukePaiList().add(allPaiList.get(51));
        dao2.calculateIndex();//拼接牌面字符串 小于10的前一位补0
        calculateDaoTypeCodeWithoutWangpai(dao2, paiMianList2);

//        List<PukePaiMian> paiMianList3 = new ArrayList<>();
//        paiMianList3.add(PukePaiMian.heitaoer);
//        paiMianList3.add(PukePaiMian.heitaosan);
//        paiMianList3.add(PukePaiMian.heitaosi);
//        Dao dao3 = new Dao();
//        dao3.getPukePaiList().add(allPaiList.get(3));
//        dao3.getPukePaiList().add(allPaiList.get(7));
//        dao3.getPukePaiList().add(allPaiList.get(11));
//        dao3.calculateIndex();//拼接牌面字符串 小于10的前一位补0
//        calculateDaoTypeCodeWithoutWangpai(dao3, paiMianList3);

        System.out.println("|");
    }

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

    private static int countDuizi(int[] dianshuArray) {
        int duiziCount = 0;
        for (int i : dianshuArray) {
            if (i > 1) {
                duiziCount++;
            }
        }
        return duiziCount;
    }

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

    private static boolean isTonghua(List<PukePaiMian> pukePaiList) {
        HuaSe huase = pukePaiList.get(0).huaSe();
        for (PukePaiMian paimian : pukePaiList) {
            if (!huase.equals(paimian.huaSe())) {
                return false;
            }
        }
        return true;
    }

    private static int countMaxDianshu(int[] dianshuArray) {
        int max = 0;
        for (int i : dianshuArray) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

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

}