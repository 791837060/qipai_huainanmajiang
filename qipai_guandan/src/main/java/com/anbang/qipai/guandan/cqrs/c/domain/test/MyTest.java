package com.anbang.qipai.guandan.cqrs.c.domain.test;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.TonghuashunDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuGenerator;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;

import java.util.*;

public class MyTest {

    public static void main(String[] args) {
        ShuangkouPlayer shuangkouPlayer = test4();
        Map<Integer, PukePai> allShoupai = shuangkouPlayer.getAllShoupai();
        Map<String, List<DaPaiDianShuSolution>> tonghuashunSolution = calculateTonghuashunSolution(allShoupai);

//        int[] arr=new int[]{3,2,0,0,0,1,1,1,3,1,2,1,2,0,0,0};
//        List<LianduiDianShuZu> lianduiDianShuZus = DianShuZuGenerator.generateAllLianduiDianShuZu(arr, 3);

        System.out.println(1);
    }

    public static List<PukePai> test1() {
        PukePaiMian[] allPukePaiMianArray = PukePaiMian.values();
        List<PukePaiMian> playPaiTypeList = new ArrayList<>(Arrays.asList(allPukePaiMianArray));
        List<PukePai> allPaiList = new ArrayList<>();
        // 生成两副牌
        int id = 0;
        for (PukePaiMian paiType : playPaiTypeList) {
            for (int i = 0; i < 2; i++) {
                PukePai pai = new PukePai();
                pai.setId(id);
                pai.setPaiMian(paiType);
                allPaiList.add(pai);
                id++;
            }
        }
        return allPaiList;
    }

    public static void test2() {
        List<PukePai> pukePais = test1();
        int[] dianshu = new int[]{106, 101, 99, 94, 92, 88, 76, 73, 72, 57, 52, 51, 50, 43, 42, 35, 33, 32, 31, 28, 27, 26, 25, 18, 12, 11, 8};
        int[] huase = new int[]{106, 105, 88, 89, 65, 57, 48, 40, 33, 25, 98, 82, 75, 59, 42, 43, 11, 68, 36, 102, 94, 79, 54, 46, 38, 31, 22};
        int[] shuliang = new int[]{106, 31, 28, 27, 26, 25, 94, 92, 88, 76, 73, 72, 52, 51, 50, 35, 33, 32, 12, 11, 8, 101, 99, 43, 42, 57, 18};
        for (int i : huase) {
            for (PukePai pukePai : pukePais) {
                if (pukePai.getId() == i) {
                    System.out.println(pukePai.getPaiMian().huaSe() + "|" + pukePai.getPaiMian().dianShu());
                }
            }
        }

    }

    public static ShuangkouPlayer test3() {
        PukePaiMian[] paiMain = {
                PukePaiMian.heitaoer,
                PukePaiMian.hongxiner2,
                PukePaiMian.meihuaer,
                PukePaiMian.fangkuaier,
                PukePaiMian.heitaoA,
                PukePaiMian.hongxinA,
                PukePaiMian.meihuaA,
                PukePaiMian.fangkuaiA,
                PukePaiMian.heitaoK,
                PukePaiMian.hongxinK,
                PukePaiMian.meihuaK,
                PukePaiMian.fangkuaiK,
                PukePaiMian.heitaoQ,
                PukePaiMian.hongxinQ,
                PukePaiMian.meihuaQ,
                PukePaiMian.heitaoJ,
                PukePaiMian.hongxinJ,
                PukePaiMian.meihuaJ,
                PukePaiMian.heitaoshi,
                PukePaiMian.heitaosan,
                PukePaiMian.hongxinsan,
        };
        ShuangkouPlayer shuangkouPlayer = new ShuangkouPlayer();
        for (int i = 0; i < paiMain.length; i++) {
            PukePai pai = new PukePai();
            pai.setId(paiMain[i].ordinal());
            pai.setPaiMian(paiMain[i]);
            shuangkouPlayer.addShouPai(pai);
        }
        PukePai pai = new PukePai();
        pai.setId(97);
        pai.setPaiMian(PukePaiMian.heitaoer);
        shuangkouPlayer.addShouPai(pai);

        PukePai pai5 = new PukePai();
        pai5.setId(99);
        pai5.setPaiMian(PukePaiMian.hongxiner2);
        shuangkouPlayer.addShouPai(pai5);

        PukePai pai1 = new PukePai();
        pai1.setId(104);
        pai1.setPaiMian(PukePaiMian.xiaowang);
        shuangkouPlayer.addShouPai(pai1);

        PukePai pai2 = new PukePai();
        pai2.setId(105);
        pai2.setPaiMian(PukePaiMian.xiaowang);
        shuangkouPlayer.addShouPai(pai2);

        PukePai pai3 = new PukePai();
        pai3.setId(106);
        pai3.setPaiMian(PukePaiMian.dawang);
        shuangkouPlayer.addShouPai(pai3);

        PukePai pai4 = new PukePai();
        pai4.setId(107);
        pai4.setPaiMian(PukePaiMian.dawang);
        shuangkouPlayer.addShouPai(pai4);

        return shuangkouPlayer;
    }

    public static ShuangkouPlayer test4() {
        PukePaiMian[] paiMain = {
                PukePaiMian.hongxiner2,
                PukePaiMian.heitaoer,
                PukePaiMian.heitaosan,
                PukePaiMian.heitaowu,
                PukePaiMian.heitaoqi,
        };

        ShuangkouPlayer shuangkouPlayer = new ShuangkouPlayer();

//        PukePai pai5 = new PukePai();
//        pai5.setId(99);
//        pai5.setPaiMian(PukePaiMian.hongxiner2);
//        shuangkouPlayer.addShouPai(pai5);

        for (int i = 0; i < paiMain.length; i++) {
            PukePai pai = new PukePai();
            pai.setId(paiMain[i].ordinal());
            pai.setPaiMian(paiMain[i]);
            shuangkouPlayer.addShouPai(pai);
        }

        PukePai pai = new PukePai();
        pai.setId(99);
        pai.setPaiMian(PukePaiMian.hongxiner2);
        shuangkouPlayer.addShouPai(pai);

        return shuangkouPlayer;
    }


    /**
     * 计算同花顺出牌方案
     */
    public static Map<String, List<DaPaiDianShuSolution>> calculateTonghuashunSolution(Map<Integer, PukePai> allShoupai) {
        int hongxinEr = 0;
        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getPaiMian().dianShu().equals(DianShu.hongxiner2) && pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                hongxinEr++;
            }
        }
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
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                heitaoDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : hongxinPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                hongxinDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : meihuaPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                meihuaDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : fangkuaiPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                fangkuaiDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        List<TonghuashunDianShuZu> heitaoTonghuashun = generateAllHuaseShunziDianShuZu(heitaoDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> hongxinTonghuashun = generateAllHuaseShunziDianShuZu(hongxinDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> meihuaTonghuashun = generateAllHuaseShunziDianShuZu(meihuaDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> fangkuaiTonghuashun = generateAllHuaseShunziDianShuZu(fangkuaiDianshuAmountArray, 5, hongxinEr);

        Map<String, List<DaPaiDianShuSolution>> solutionList = new HashMap<>();
        solutionList.put("heitao", calculateHuaseSolution(heitaoTonghuashun));
        solutionList.put("hongxin", calculateHuaseSolution(hongxinTonghuashun));
        solutionList.put("meihua", calculateHuaseSolution(meihuaTonghuashun));
        solutionList.put("fangkuai", calculateHuaseSolution(fangkuaiTonghuashun));
        return solutionList;
    }

    /**
     * 计算单个花色的同花顺
     *
     * @param dianShuAmountArray 点数数量数组
     * @param length             顺子长度
     * @param hongxinErs         红心二数量
     */
    public static List<TonghuashunDianShuZu> generateAllHuaseShunziDianShuZu(int[] dianShuAmountArray, int length, int hongxinErs) {
        List<TonghuashunDianShuZu> shunziList = new ArrayList<>();
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            int guipaiCount = hongxinErs;
            List<Integer> dangpaiList = new ArrayList<>();
            while (danzhangLianXuCount < length && j <= 13) {   //无2和大、小王
                if (aerDianshuAmountArray[j] >= 1) {
                    danzhangLianXuCount++;
                    j++;
                } else if (guipaiCount > 0) {
                    if (j == 0) {
                        dangpaiList.add(11);
                    } else if (j == 1) {
                        dangpaiList.add(12);
                    } else {
                        dangpaiList.add(j - 2);
                    }
                    danzhangLianXuCount++;
                    j++;
                    guipaiCount--;
                } else {
                    break;
                }
            }
            if (danzhangLianXuCount >= length && i <= 9) {
                if (i != 1) {
                    DianShu[] lianXuDianShuArray = new DianShu[length];
                    for (int k = 0; k < length; k++) {
                        //重新定义的数组0处是A，而扑克点数0处是3 所以所有数组索引-2
                        int index = i;
                        if (index == 0) {
                            index = 11;
                        } else {
                            index -= 2;
                        }

                        int ordinal;
                        if (index + k >= 13) {
                            ordinal = index + k - 13;
                        } else {
                            ordinal = index + k;
                        }

                        if (index + k >= 13) {
                            index -= 13;
                        }
                        if (dangpaiList.contains(index + k)) {
                            ordinal = 15;
                        }

                        lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
                    }
                    TonghuashunDianShuZu tonghuashunDianShuZu = new TonghuashunDianShuZu(lianXuDianShuArray);
                    shunziList.add(tonghuashunDianShuZu);
                }
            }
        }

        List<TonghuashunDianShuZu> shunziList2 = new ArrayList<>();
        for (TonghuashunDianShuZu tonghuashunDianShuZu : shunziList) {
            int alreadyUsedHongxinEr = 0;
            DianShu[] lianXuDianShuArray = tonghuashunDianShuZu.getLianXuDianShuArray();
            for (DianShu dianShu : lianXuDianShuArray) {
                if (dianShu.equals(DianShu.hongxiner2)) {
                    alreadyUsedHongxinEr++;
                }
            }
            if (alreadyUsedHongxinEr == 0 && hongxinErs > 0) {
                for (int i = 0; i < lianXuDianShuArray.length; i++) {
                    DianShu[] cloneLianXuDianShuArray = lianXuDianShuArray.clone();
                    cloneLianXuDianShuArray[i] = DianShu.hongxiner2;
                    TonghuashunDianShuZu tonghuashunDianShuZu1 = new TonghuashunDianShuZu(cloneLianXuDianShuArray);
                    shunziList2.add(tonghuashunDianShuZu1);
                }
            }
        }
        shunziList.addAll(shunziList2);

        return shunziList;
    }

    /**
     * 重新计算从A开始的点数数量数组
     *
     * @param dianShuAmountArray 点数数量数组
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

    /**
     * 生成单个花色同花顺出牌方案
     *
     * @param heitaoTonghuashun 同花顺数组
     */
    private static List<DaPaiDianShuSolution> calculateHuaseSolution(List<TonghuashunDianShuZu> heitaoTonghuashun) {
        List<DaPaiDianShuSolution> shuSolutions = new ArrayList<>();
        for (TonghuashunDianShuZu tonghuashunDianShuZu : heitaoTonghuashun) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(tonghuashunDianShuZu);
            DianShu[] lianXuDianShuArray = tonghuashunDianShuZu.getLianXuDianShuArray();
            DianShu[] dachuDianShuArray = new DianShu[lianXuDianShuArray.length];
            for (int i = 0; i < lianXuDianShuArray.length; i++) {
                dachuDianShuArray[i] = lianXuDianShuArray[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            shuSolutions.add(solution);
        }
        return shuSolutions;
    }

}
