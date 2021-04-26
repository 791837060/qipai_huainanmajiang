package com.dml.puke.wanfa.dianshu.dianshuzu;

import java.util.ArrayList;
import java.util.List;

import com.dml.puke.pai.DianShu;

/**
 * 通过点数数量数组生成相关的所有可能点数组
 *
 * @author Neo
 */
public class DianShuZuGenerator {

    /**
     * 单张点数数组
     *
     * @param dianShuAmountArray 点数数量数组
     */
    public static List<DanzhangDianShuZu> generateAllDanzhangDianShuZu(int[] dianShuAmountArray) {
        List<DanzhangDianShuZu> danzhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 1) {
                DanzhangDianShuZu danzhangDianShuZu = new DanzhangDianShuZu(DianShu.getDianShuByOrdinal(i));
                danzhangList.add(danzhangDianShuZu);
            }
        }
        return danzhangList;
    }

    public static List<DuiziDianShuZu> generateAllDuiziDianShuZu(int[] dianShuAmountArray) {
        List<DuiziDianShuZu> duiziList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 2) {
                DuiziDianShuZu duiziDianShuZu = new DuiziDianShuZu(DianShu.getDianShuByOrdinal(i));
                duiziList.add(duiziDianShuZu);
            }
        }
        return duiziList;
    }

    public static List<SanzhangDianShuZu> generateAllSanzhangDianShuZu(int[] dianShuAmountArray) {
        List<SanzhangDianShuZu> sanzhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 3) {
                SanzhangDianShuZu sanzhangDianShuZu = new SanzhangDianShuZu(DianShu.getDianShuByOrdinal(i));
                sanzhangList.add(sanzhangDianShuZu);
            }
        }
        return sanzhangList;
    }

    public static List<ShunziDianShuZu> generateAllShunziDianShuZu(int[] dianShuAmountArray, int length) {
        List<ShunziDianShuZu> shunziList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 1) {// 无2和大、小王
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i + k);
                }
                ShunziDianShuZu shunzi = new ShunziDianShuZu(lianXuDianShuArray);
                shunziList.add(shunzi);
            }
        }
        return shunziList;
    }

    public static List<LianduiDianShuZu> generateAllLianduiDianShuZu(int[] dianShuAmountArray, int length) {
        List<LianduiDianShuZu> lianduiList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 2) {// 2和大、小王无法参与
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i + k);
                }
                LianduiDianShuZu liandui = new LianduiDianShuZu(lianXuDianShuArray);
                lianduiList.add(liandui);
            }
        }
        return lianduiList;
    }

    public static List<LiansanzhangDianShuZu> generateAllLiansanzhangDianShuZu(int[] dianShuAmountArray, int length) {
        List<LiansanzhangDianShuZu> lianSanZhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 3) {// 2和大、小王无法参与
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i + k);
                }
                LiansanzhangDianShuZu lianSanZhang = new LiansanzhangDianShuZu(lianXuDianShuArray);
                lianSanZhangList.add(lianSanZhang);
            }
        }
        return lianSanZhangList;
    }

    public static List<ShunziDianShuZu> generateAllShunziDianShuZu(int[] dianShuAmountArray, int length, boolean A2xiafang) {
        List<ShunziDianShuZu> shunziList = new ArrayList<>();
        int[] dianShuAmountArray2;
        int index;
        if (A2xiafang) {
            dianShuAmountArray2 = new int[14];
            dianShuAmountArray2[0] = dianShuAmountArray[11];
            dianShuAmountArray2[1] = dianShuAmountArray[12];
            for (int i = 2; i < dianShuAmountArray2.length; i++) {
                dianShuAmountArray2[i] = dianShuAmountArray[i - 2];
            }
            dianShuAmountArray2[13] = dianShuAmountArray[11];
            index = 14;
        } else {
            dianShuAmountArray2 = dianShuAmountArray;
            index = 12;
        }

        for (int i = 0; i < dianShuAmountArray2.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < index && dianShuAmountArray2[j] >= 1) { //大、小王
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    int dianshu = i + k;
                    if (A2xiafang) {
                        dianshu -= 2;
                        if (dianshu == -2) {
                            dianshu = 11;
                        } else if (dianshu == -1) {
                            dianshu = 12;
                        }
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(dianshu);
                }
                ShunziDianShuZu shunzi = new ShunziDianShuZu(lianXuDianShuArray);
                shunziList.add(shunzi);
            }
        }
        return shunziList;
    }

    public static List<LianduiDianShuZu> generateAllLianduiDianShuZu(int[] dianShuAmountArray, int length, boolean A2xiafang) {
        List<LianduiDianShuZu> lianduiList = new ArrayList<>();
        int[] dianShuAmountArray2;
        int index;
        if (A2xiafang) {
            dianShuAmountArray2 = new int[14];
            dianShuAmountArray2[0] = dianShuAmountArray[11];
            dianShuAmountArray2[1] = dianShuAmountArray[12];
            for (int i = 2; i < dianShuAmountArray2.length; i++) {
                dianShuAmountArray2[i] = dianShuAmountArray[i - 2];
            }
            dianShuAmountArray2[13] = dianShuAmountArray[11];
            index = 14;
        } else {
            dianShuAmountArray2 = dianShuAmountArray;
            index = 12;
        }
        for (int i = 0; i < dianShuAmountArray2.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < index && dianShuAmountArray2[j] >= 2) { //大、小王无法参与
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    int dianshu = i + k;
                    if (A2xiafang) {
                        dianshu -= 2;
                        if (dianshu == -2) {
                            dianshu = 11;
                        } else if (dianshu == -1) {
                            dianshu = 12;
                        }
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(dianshu);
                }
                LianduiDianShuZu liandui = new LianduiDianShuZu(lianXuDianShuArray);
                lianduiList.add(liandui);
            }
        }
        return lianduiList;
    }

    public static List<LiansanzhangDianShuZu> generateAllLiansanzhangDianShuZu(int[] dianShuAmountArray, int length, boolean A2xiafang) {
        List<LiansanzhangDianShuZu> lianSanZhangList = new ArrayList<>();
        int[] dianShuAmountArray2;
        if (A2xiafang) {
            dianShuAmountArray2 = new int[13];
            dianShuAmountArray2[0] = dianShuAmountArray[11];
            dianShuAmountArray2[1] = dianShuAmountArray[12];
            for (int i = 2; i < dianShuAmountArray2.length; i++) {
                dianShuAmountArray2[i] = dianShuAmountArray[i - 2];
            }
        } else {
            dianShuAmountArray2 = dianShuAmountArray;
        }
        for (int i = 0; i < dianShuAmountArray2.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray2[j] >= 3) {// 2和大、小王无法参与
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    int i1 = i + k - 2;
                    if (i1 == -2) {
                        i1 = 11;
                    } else if (i1 == -1) {
                        i1 = 12;
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i1);
                }
                LiansanzhangDianShuZu lianSanZhang = new LiansanzhangDianShuZu(lianXuDianShuArray);
                lianSanZhangList.add(lianSanZhang);
            }
        }
        return lianSanZhangList;
    }

    public static List<DanGeZhadanDianShuZu> generateAllZhadanDianShuZu(int[] dianShuAmountArray) {
        List<DanGeZhadanDianShuZu> zhadanList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 4) {
                for (int j = 4; j <= dianshuCount; j++) {
                    DanGeZhadanDianShuZu zhadanDianShuZu = new DanGeZhadanDianShuZu(DianShu.getDianShuByOrdinal(i), j);
                    zhadanList.add(zhadanDianShuZu);
                }
            }
        }
        return zhadanList;
    }

}
