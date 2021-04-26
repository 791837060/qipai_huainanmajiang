package com.dml.paodekuai.pai.dianshuzu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;

/**
 * 通过点数数量数组生成相关的所有可能点数组:跑得快特有三带二、飞机
 */
public class PaodekuaiDianShuZuGenerator {

    /**
     * 取出所有牌中最大的单张
     */
    public static List<DanzhangDianShuZu> largestDanzhangDianshuzu(int[] dianShuAmountArray) {
        List<DanzhangDianShuZu> danzhangList = new ArrayList<>();
        for (int i = dianShuAmountArray.length - 1; i >= 0; i--) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 1) {
                DanzhangDianShuZu danzhangDianShuZu = new DanzhangDianShuZu(DianShu.getDianShuByOrdinal(i));
                danzhangList.add(danzhangDianShuZu);
                break;
            }
        }
        return danzhangList;
    }

    /**
     * 三带一
     */
    public static List<SandaiyiDianShuZu> generateAllSandaiyiDianShuZu(int[] dianShuAmountArray, boolean sandaique) {
        List<SandaiyiDianShuZu> sanzhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            tempDinashu[11] = 0;//A不用3带1
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 3) {
                // 三张点数组
                DianShu[] sanzhangDianShuArray = {DianShu.getDianShuByOrdinal(i)};
                // 取出除去三张之外的所有余牌,置0，防止与炸弹带牌牌型重复
                tempDinashu[i] = 0;
                List<DianShu> daipaiList = leftHand(tempDinashu);
                if (sandaique && daipaiList.size() < 2 && daipaiList.size() != 0) {
                    DianShu[] dianShus = new DianShu[daipaiList.size()];
                    daipaiList.toArray(dianShus);
                    SandaiyiDianShuZu sandaierDianShuZu = new SandaiyiDianShuZu();
                    sandaierDianShuZu.setSanzhangDianShuArray(sanzhangDianShuArray);
                    sandaierDianShuZu.setDaipaiDianShuArray(dianShus);
                    sandaierDianShuZu.setMissingType(true);
                    sanzhangList.add(sandaierDianShuZu);
                    continue;
                }

                int[] temp = Arrays.copyOf(dianShuAmountArray, 13);
                temp[i] = 0;
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j] >= 1) {
                        DianShu[] daipaiDianshuzu2 = {DianShu.getDianShuByOrdinal(j)};
                        SandaiyiDianShuZu sandaierDianShuZu2 = new SandaiyiDianShuZu();
                        sandaierDianShuZu2.setSanzhangDianShuArray(sanzhangDianShuArray);
                        sandaierDianShuZu2.setDaipaiDianShuArray(daipaiDianshuzu2);
                        sanzhangList.add(sandaierDianShuZu2);
                    }
                }

            }
        }

        return sanzhangList;
    }

    /**
     * 三带二
     */
    public static List<SandaierDianShuZu> generateAllSandaierDianShuZu(int[] dianShuAmountArray, boolean sandaique, boolean couldDaidan) {
        List<SandaierDianShuZu> sanzhangList = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount >= 3) {
                // 三张点数组
                DianShu[] sanzhangDianShuArray = {DianShu.getDianShuByOrdinal(i)};
                // 取出除去三张之外的所有余牌,置0，防止与炸弹带牌牌型重复
                tempDinashu[i] = 0;
                List<DianShu> daipaiList = leftHand(tempDinashu);
                if (sandaique && daipaiList.size() < 2 && daipaiList.size() != 0) {
                    DianShu[] dianShus = new DianShu[daipaiList.size()];
                    daipaiList.toArray(dianShus);
                    SandaierDianShuZu sandaierDianShuZu = new SandaierDianShuZu();
                    sandaierDianShuZu.setSanzhangDianShuArray(sanzhangDianShuArray);
                    sandaierDianShuZu.setDaipaiDianShuArray(dianShus);
                    sandaierDianShuZu.setMissingType(true);
                    sanzhangList.add(sandaierDianShuZu);
                    continue;
                }

                if (couldDaidan) {
                    //三带二的单牌计算 有重复
                    for (int a = 0; a < daipaiList.size() - 1; a++) {
                        for (int b = a + 1; b < daipaiList.size(); b++) {
                            DianShu[] daipaiDianshuzu = {daipaiList.get(a), daipaiList.get(b)};
                            SandaierDianShuZu sandaierDianShuZu = new SandaierDianShuZu();
                            sandaierDianShuZu.setSanzhangDianShuArray(sanzhangDianShuArray);
                            sandaierDianShuZu.setDaipaiDianShuArray(daipaiDianshuzu);
                            sanzhangList.add(sandaierDianShuZu);
                        }
                    }
                } else {
                    int[] temp = Arrays.copyOf(dianShuAmountArray, 13);
                    temp[i] = 0;
                    for (int j = 0; j < temp.length; j++) {
                        if (temp[j] >= 2) {
                            DianShu[] daipaiDianshuzu2 = {DianShu.getDianShuByOrdinal(j), DianShu.getDianShuByOrdinal(j)};
                            SandaierDianShuZu sandaierDianShuZu2 = new SandaierDianShuZu();
                            sandaierDianShuZu2.setSanzhangDianShuArray(sanzhangDianShuArray);
                            sandaierDianShuZu2.setDaipaiDianShuArray(daipaiDianshuzu2);
                            sanzhangList.add(sandaierDianShuZu2);
                        }
                    }
                }

            }
        }

        return sanzhangList;
    }

    /**
     * 飞机
     */
    public static List<FeijiDianShuZu> generateAllFeijiDianShuZu(int[] dianShuAmountArray, int length, boolean feijique, boolean sanDaiLiangdan) {
        List<FeijiDianShuZu> feijiDianShuZus = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int danzhangLianXuCount = 0;
            int j = i;
            while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 3) {// 3个或者3个以上的点数相连的3张牌，如555666777；2和大、小王无法参与
                danzhangLianXuCount++;
                tempDinashu[j] = tempDinashu[j] - 3;
                j++;
            }

            if (danzhangLianXuCount >= length) {
                // 取出余牌
                List<DianShu> yupaiList = leftHand(tempDinashu);
                DianShu[] daipaiDianshu;

                // 先取出缺牌情形的牌组(五连必缺牌)
                if (feijique && yupaiList.size() < length * 2) {
                    daipaiDianshu = new DianShu[yupaiList.size()];
                    yupaiList.toArray(daipaiDianshu);

                    FeijiDianShuZu feijiDianShuZu = new FeijiDianShuZu();
                    if (length == 2) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 3) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1), DianShu.getDianShuByOrdinal(i + 2)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 4) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1), DianShu.getDianShuByOrdinal(i + 2), DianShu.getDianShuByOrdinal(i + 3)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    } else if (length == 5) {
                        DianShu[] sanzhangDianshuzu = {DianShu.getDianShuByOrdinal(i), DianShu.getDianShuByOrdinal(i + 1), DianShu.getDianShuByOrdinal(i + 2), DianShu.getDianShuByOrdinal(i + 3), DianShu.getDianShuByOrdinal(i + 4)};
                        feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                    }
                    feijiDianShuZu.setDaipaiDianShuArray(daipaiDianshu);
                    feijiDianShuZus.add(feijiDianShuZu);
                    continue;
                }

                // 不缺牌的2-4连的飞机
                if (yupaiList.size() >= length) {
                    // 三张
                    DianShu[] sanzhangDianshuzu = new DianShu[length];
                    for (int a = 0; a < length; a++) {
                        sanzhangDianshuzu[a] = DianShu.getDianShuByOrdinal(i + a);
                    }

                    // 带牌
                    List<DianShu[]> daipaiList = new ArrayList<>();

                    quPai(daipaiList, yupaiList, new DianShu[0], 0, 0, 0);
                    quPai(daipaiList, yupaiList, new DianShu[length], length, 0, 0);
                    quPai(daipaiList, yupaiList, new DianShu[length * 2], length * 2, 0, 0);

                    for (DianShu[] daipaiDianshuzu : daipaiList) {
                        boolean couldDaiDan = true;//是否可以带两张单牌
                        if (!sanDaiLiangdan && (sanzhangDianshuzu.length * 2) == daipaiDianshuzu.length) {
                            for (int k = 0; k < daipaiDianshuzu.length; k += 2) {
                                int k2 = k + 1;
                                if (daipaiDianshuzu[k].ordinal() != daipaiDianshuzu[k2].ordinal()) {
                                    couldDaiDan = false;
                                    break;
                                }
                            }
                        }
                        if (couldDaiDan) {
                            FeijiDianShuZu feijiDianShuZu = new FeijiDianShuZu();
                            feijiDianShuZu.setSanzhangDianShuArray(sanzhangDianshuzu);
                            feijiDianShuZu.setDaipaiDianShuArray(daipaiDianshuzu);
                            feijiDianShuZus.add(feijiDianShuZu);
                        }
                    }
                }

            }
        }
        return feijiDianShuZus;
    }

    /**
     * aaa
     */
    public static List<ABoomDianShuZu> generateAllABoomDianShuZu(int[] dianShuAmountArray) {
        List<ABoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[11] == 3) {
            ABoomDianShuZu aBoomDianShuZu = new ABoomDianShuZu();
            aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(11));
            aBoomDianShuZus.add(aBoomDianShuZu);
        }
        return aBoomDianShuZus;
    }

    /**
     * 计算AAA+1炸弹点数组
     */
    public static List<SanAJiaYiBoomDianShuZu> generateAll3AJia1BoomDianShuZu(int[] dianShuAmountArray) {
        List<SanAJiaYiBoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[11] == 3) {
            for (int i = 0; i < dianShuAmountArray.length; i++) {
                if (dianShuAmountArray[i] != 0 && i != 11) {
                    SanAJiaYiBoomDianShuZu aBoomDianShuZu = new SanAJiaYiBoomDianShuZu();
                    aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(11));
                    aBoomDianShuZu.setDaipaiDianshu(DianShu.getDianShuByOrdinal(i));
                    aBoomDianShuZus.add(aBoomDianShuZu);
                }
            }

        }
        return aBoomDianShuZus;
    }

    /**
     * 计算3个3炸弹点数组
     */
    public static List<SanBoomDianShuZu> generateSanBoomDianShuZu(int[] dianShuAmountArray) {
        List<SanBoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[0] >= 3) {
            SanBoomDianShuZu aBoomDianShuZu = new SanBoomDianShuZu();
            aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(0));
            aBoomDianShuZus.add(aBoomDianShuZu);
        }
        return aBoomDianShuZus;
    }

    /**
     * 计算333+1炸弹点数组
     */
    public static List<SanSanJiaYiBoomDianShuZu> generate33Jia1BoomDianShuZu(int[] dianShuAmountArray) {
        List<SanSanJiaYiBoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[0] >= 3) {
            for (int i = 1; i < dianShuAmountArray.length; i++) {
                if (dianShuAmountArray[i] != 0) {
                    SanSanJiaYiBoomDianShuZu aBoomDianShuZu = new SanSanJiaYiBoomDianShuZu();
                    aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(0));
                    aBoomDianShuZu.setDianShu2(DianShu.getDianShuByOrdinal(i));
                    aBoomDianShuZus.add(aBoomDianShuZu);
                }
            }
        }
        return aBoomDianShuZus;
    }

    /**
     * aaa
     */
    public static List<ABoomDianShuZu> generateAllKBoomDianShuZu(int[] dianShuAmountArray) {
        List<ABoomDianShuZu> aBoomDianShuZus = new ArrayList<>();
        if (dianShuAmountArray[10] == 3) {
            ABoomDianShuZu aBoomDianShuZu = new ABoomDianShuZu();
            aBoomDianShuZu.setDianShu(DianShu.getDianShuByOrdinal(10));
            aBoomDianShuZus.add(aBoomDianShuZu);
        }
        return aBoomDianShuZus;
    }

    /**
     * 四带一的炸弹
     */
    public static List<DaiPaiZhaDanDianShuZu> generateAllDaiPaiZhaDanDianShuZu(int[] dianShuAmountArray) {
        List<DaiPaiZhaDanDianShuZu> daiPaiZhaDanDianShuZus = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount == 4) {

                // 取出除去四张之外的所有余牌
                for (int j = 0; j < 13; j++) {
                    if (dianShuAmountArray[j] > 0 && i != j) {
                        DaiPaiZhaDanDianShuZu daiPaiZhaDanDianShuZu = new DaiPaiZhaDanDianShuZu();
                        daiPaiZhaDanDianShuZu.setZhadanDian(DianShu.getDianShuByOrdinal(i));
                        daiPaiZhaDanDianShuZu.setDaipaiDian(DianShu.getDianShuByOrdinal(j));
                        daiPaiZhaDanDianShuZus.add(daiPaiZhaDanDianShuZu);
                    }
                }
            }
        }
        return daiPaiZhaDanDianShuZus;
    }

    /**
     * 四带二
     */
    public static List<SidaierDianShuZu> generateAllSidaierDianShuZu(int[] dianShuAmountArray) {
        List<SidaierDianShuZu> sidaierDianShuZus = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount == 4) {
                // 四张的牌
                DianShu danpaiDianShu = DianShu.getDianShuByOrdinal(i);

                // 取出除去四张之外的所有余牌
                tempDinashu[i] = tempDinashu[i] - 4;
                List<DianShu> yupaiList = leftHand(tempDinashu);

                if (yupaiList.size() < 2) {
                    continue;
                }
                List<DianShu[]> daipaiList = new ArrayList<>();
                quPai(daipaiList, yupaiList, new DianShu[2], 2, 0, 0);
                for (DianShu[] dianShus : daipaiList) {
                    SidaierDianShuZu sidaierDianShuZu = new SidaierDianShuZu();
                    sidaierDianShuZu.setDanpaiDianShu(danpaiDianShu);
                    sidaierDianShuZu.setDaipaiDianShuArray(dianShus);
                    sidaierDianShuZus.add(sidaierDianShuZu);
                }
            }
        }
        return sidaierDianShuZus;
    }

    /**
     * 四带三
     */
    public static List<SidaisanDianShuZu> generateAllSidaisanDianShuZu(int[] dianShuAmountArray) {
        List<SidaisanDianShuZu> sidaisanDianShuZus = new ArrayList<>();
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            int[] tempDinashu = Arrays.copyOf(dianShuAmountArray, 13);
            int dianshuCount = dianShuAmountArray[i];
            if (dianshuCount == 4) {
                // 四张的牌
                DianShu danpaiDianShu = DianShu.getDianShuByOrdinal(i);

                // 取出除去四张之外的所有余牌
                tempDinashu[i] = tempDinashu[i] - 4;
                List<DianShu> yupaiList = leftHand(tempDinashu);

                if (yupaiList.size() < 3) {
                    continue;
                }
                List<DianShu[]> daipaiList = new ArrayList<>();
                quPai(daipaiList, yupaiList, new DianShu[3], 3, 0, 0);
                for (DianShu[] dianShus : daipaiList) {
                    SidaisanDianShuZu sidaisanDianShuZu = new SidaisanDianShuZu();
                    sidaisanDianShuZu.setDanpaiDianShu(danpaiDianShu);
                    sidaisanDianShuZu.setDaipaiDianShuArray(dianShus);
                    sidaisanDianShuZus.add(sidaisanDianShuZu);
                }
            }
        }
        return sidaisanDianShuZus;
    }

    /**
     * 从余牌中取出带牌list
     *
     * @param diapaiList      所有飞机带的牌组
     * @param daipaiDianshuzu 飞机带牌的点数
     * @param yuPaiList       剩余手牌
     * @param length          取出的带牌点数数组长度
     * @param index           取牌起点下标
     * @param time            取牌次数
     */
    private static void quPai(List<DianShu[]> diapaiList, List<DianShu> yuPaiList, DianShu[] daipaiDianshuzu, int length, int index, int time) {
        if (time < length) { // 2 < 3
            for (int i = index; i < yuPaiList.size(); i++) {
                daipaiDianshuzu[time] = yuPaiList.get(i);
                quPai(diapaiList, yuPaiList, daipaiDianshuzu, length, i + 1, time + 1);
            }
        } else {
            DianShu[] dianShus = daipaiDianshuzu.clone();
            diapaiList.add(dianShus);
        }

    }

    // void feijiTest() {
    //
    // List<DianShu> yuPaiList = new ArrayList<>();
    // yuPaiList.add(DianShu.getDianShuByOrdinal(0));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(1));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(2));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(3));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(4));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(5));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(6));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(7));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(8));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(9));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(10));
    // yuPaiList.add(DianShu.getDianShuByOrdinal(11));
    // DianShu[] daipai = new DianShu[8];
    // List<DianShu[]> result = new ArrayList<>();
    // quPai(result,daipai, yuPaiList,8, 0 ,0);
    // System.out.println(JSON.toJSONString(result));
    // System.out.println(result.size());
    // }

    // public static void main(String[] args) {
    // int[] first = {1, 3, 1, 1, 0, 2, 1, 1, 2, 0, 2, 1, 1, 0, 0};
    // List<SandaierDianShuZu> dianShuZus =
    // generateAllSandaierDianShuZu(first,false);
    // System.out.println(JSON.toJSONString(dianShuZus));
    // System.out.println(dianShuZus.size());
    // }

    // 取出余牌
    private static List<DianShu> leftHand(int[] tempDainshu) {
        List<DianShu> dianShus = new ArrayList<>();
        for (int i = 0; i < tempDainshu.length; i++) {
            for (int j = 0; j < tempDainshu[i]; j++) {
                dianShus.add(DianShu.getDianShuByOrdinal(i));
            }
        }
        return dianShus;
    }
}
