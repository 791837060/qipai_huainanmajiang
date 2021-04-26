package com.dml.shuangkou.pai.dianshuzu;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过点数数量数组生成相关的所有可能点数组
 *
 * @author Neo
 */
public class DianShuZuGenerator {

    /**
     * 0---A
     * 1---2
     * 2---3
     * ...
     * 13--K
     * 14--A
     * 15--2
     * 由于扑克点数是从3开始，到大王小王。
     * 现要求顺子、连对、两个三连可以从A开始，所以重新设置点数数量数组，从A开始，到2结束。
     * A 2 3 4 5 6 7 8 9 10 J Q K A 2
     */
    private static int[] getAerDianshuAmountArray(int[] dianShuAmountArray) {
        int[] dianShuAmountArray2 = new int[15];
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            if (i + 2 < dianShuAmountArray2.length) {
                dianShuAmountArray2[i + 2] = dianShuAmountArray[i];
            }
        }
        dianShuAmountArray2[0] = dianShuAmountArray[11];
        dianShuAmountArray2[1] = dianShuAmountArray[12];
        return dianShuAmountArray2;
    }

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

    /**
     * 构建顺子可打数组
     *
     * @param dianShuAmountArray 点数大小数组
     * @param length             顺子连续数
     */
    public static List<ShunziDianShuZu> generateAllShunziDianShuZu(int[] dianShuAmountArray, int length) {
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        List<ShunziDianShuZu> shunziList = new ArrayList<>();
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            if (j != 1) {   //不能从2开始连，只能从1开始，到1结束
                while (danzhangLianXuCount < length && j <= 13 && aerDianshuAmountArray[j] >= 1) {   //无2和大、小王
                    danzhangLianXuCount++;
                    j++;
                }
            }
            if (danzhangLianXuCount >= length && i <= 9) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    //重新定义的数组0处是A，而扑克点数0处是3 所以所有数组索引-2
                    int index = i;
                    if (index == 0) {
                        index = 11;
                    } else if (index >= 2) {
                        index -= 2;
                    }
                    int ordinal;
                    if (index + k >= 13) {
                        ordinal = index + k - 13;
                    } else {
                        ordinal = index + k;
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
                }
                ShunziDianShuZu shunzi = new ShunziDianShuZu(lianXuDianShuArray);
                shunziList.add(shunzi);
            }
        }
        return shunziList;
    }

    public static List<List<TonghuashunDianShuZu>> generateAllTonghuashunDianShuZu(int[] dianShuAmountArray, int length, Map<Integer, PukePai> allShoupai) {
        List<PukePai> heitaoPaiList = new ArrayList<>();
        List<PukePai> hongxinPaiList = new ArrayList<>();
        List<PukePai> meihuaPaiList = new ArrayList<>();
        List<PukePai> fangkuaiPaiList = new ArrayList<>();
        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getPaiMian().huaSe() != null) {
                switch (pukePai.getPaiMian().huaSe()) {
                    case heitao:
                        heitaoPaiList.add(pukePai);
                        break;
                    case hongxin:
                        hongxinPaiList.add(pukePai);
                        break;
                    case meihua:
                        meihuaPaiList.add(pukePai);
                        break;
                    case fangkuai:
                        fangkuaiPaiList.add(pukePai);
                        break;
                }
            }
        }
        int[] heitaoDianshuAmountArray = new int[15];
        int[] hongxinDianshuAmountArray = new int[15];
        int[] meihuaDianshuAmountArray = new int[15];
        int[] fangkuaiDianshuAmountArray = new int[15];
        for (PukePai pukePai : heitaoPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() == 15) {
                heitaoDianshuAmountArray[1]++;
                heitaoDianshuAmountArray[14]++;
            } else {
                heitaoDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : hongxinPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() == 15) {
                hongxinDianshuAmountArray[1]++;
                hongxinDianshuAmountArray[14]++;
            } else {
                hongxinDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : meihuaPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() == 15) {
                meihuaDianshuAmountArray[1]++;
                meihuaDianshuAmountArray[14]++;
            } else {
                meihuaDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : fangkuaiPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() == 15) {
                fangkuaiDianshuAmountArray[1]++;
                fangkuaiDianshuAmountArray[14]++;
            } else {
                fangkuaiDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        List<TonghuashunDianShuZu> heitaoTonghuashun = generateAllHuaseShunziDianShuZu(heitaoDianshuAmountArray, 5);
        List<TonghuashunDianShuZu> hongxinTonghuashun = generateAllHuaseShunziDianShuZu(hongxinDianshuAmountArray, 5);
        List<TonghuashunDianShuZu> meihuaTonghuashun = generateAllHuaseShunziDianShuZu(meihuaDianshuAmountArray, 5);
        List<TonghuashunDianShuZu> fangkuaiTonghuashun = generateAllHuaseShunziDianShuZu(fangkuaiDianshuAmountArray, 5);

        List<List<TonghuashunDianShuZu>> tonghuashunList = new ArrayList<>();
        tonghuashunList.add(heitaoTonghuashun);
        tonghuashunList.add(hongxinTonghuashun);
        tonghuashunList.add(meihuaTonghuashun);
        tonghuashunList.add(fangkuaiTonghuashun);

        return tonghuashunList;
    }

    public static List<TonghuashunDianShuZu> generateAllHuaseShunziDianShuZu(int[] dianShuAmountArray, int length) {
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        List<TonghuashunDianShuZu> tonghuashunDianShuZus = new ArrayList<>();
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            if (j != 1) {   //不能从2开始连，只能从1开始，到1结束
                while (danzhangLianXuCount < length && j <= 13 && aerDianshuAmountArray[j] >= 1) {   //无2和大、小王
                    danzhangLianXuCount++;
                    j++;
                }
            }
            if (danzhangLianXuCount >= length && i <= 9) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    //重新定义的数组0处是A，而扑克点数0处是3 所以所有数组索引-2
                    int index = i;
                    if (index == 0) {
                        index = 11;
                    } else if (index >= 2) {
                        index -= 2;
                    }
                    int ordinal;
                    if (index + k >= 13) {
                        ordinal = index + k - 13;
                    } else {
                        ordinal = index + k;
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
                }
                TonghuashunDianShuZu shunzi = new TonghuashunDianShuZu(lianXuDianShuArray);
                tonghuashunDianShuZus.add(shunzi);
            }
        }
        return tonghuashunDianShuZus;
    }

    /**
     * 构建连对可打数组
     *
     * @param dianShuAmountArray 点数大小数组
     * @param length             连对连续数
     */
    public static List<LianduiDianShuZu> generateAllLianduiDianShuZu(int[] dianShuAmountArray, int length) {
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        List<LianduiDianShuZu> lianduiList = new ArrayList<>();
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            if (j != 1) {   //不能从2开始连，只能从1开始，到1结束
                while (danzhangLianXuCount < length && j <= 13 && aerDianshuAmountArray[j] >= 2) {  //2和大、小王无法参与
                    danzhangLianXuCount++;
                    j++;
                }
            }
            if (danzhangLianXuCount >= length && i <= 11) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    int index = i;
                    if (index == 0) {
                        index = 11;
                    } else if (index >= 2) {
                        index -= 2;
                    }
                    int ordinal;
                    if (index + k >= 13) {
                        ordinal = index + k - 13;
                    } else {
                        ordinal = index + k;
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
                }
                LianduiDianShuZu liandui = new LianduiDianShuZu(lianXuDianShuArray);
                lianduiList.add(liandui);
            }
        }
        return lianduiList;
    }

    /**
     * 构建连续三张数组
     *
     * @param dianShuAmountArray 点数大小数组
     * @param length             连续三张数
     */
    public static List<LiansanzhangDianShuZu> generateAllLiansanzhangDianShuZu(int[] dianShuAmountArray, int length) {
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        List<LiansanzhangDianShuZu> lianSanZhangList = new ArrayList<>();
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j <= 13 && aerDianshuAmountArray[j] >= 3) {// 2和大、小王无法参与
                danzhangLianXuCount++;
                j++;
            }
            if (danzhangLianXuCount >= length && i <= 12) {
                DianShu[] lianXuDianShuArray = new DianShu[length];
                for (int k = 0; k < length; k++) {
                    int index = i;
                    if (index == 0) {
                        index = 11;
                    } else if (index >= 2) {
                        index -= 2;
                    }
                    int ordinal;
                    if (index + k >= 13) {
                        ordinal = index + k - 13;
                    } else {
                        ordinal = index + k;
                    }
                    lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
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
