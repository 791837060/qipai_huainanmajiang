package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZuGenerator;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianXuDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.LianXuDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuCalculator;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.PaiXing;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;
import com.dml.shuangkou.pai.jiesuanpai.HongxinErDangPai;
import com.dml.shuangkou.pai.jiesuanpai.ShoupaiJiesuanPai;
import com.dml.shuangkou.player.action.da.AllKedaPaiSolutionsGenerator;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.wanfa.BianXingWanFa;

public class GuandanAllKedaPaiSolutionsGenerator implements AllKedaPaiSolutionsGenerator {

    private BianXingWanFa bx;

    private ZhadanComparator zhadanComparator;

    private LianXuDianShuZuComparator lianXuDianShuZuComparator;

    /**
     * 所有可打方案
     *
     * @param allShoupai 手牌集合
     */
    @Override
    public Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai) {
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        int[] dianshuCountArray = new int[16];

        int hongxinEr = 0;
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
            if (pukePai.getPaiMian().dianShu().equals(DianShu.hongxiner2) && pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                hongxinEr++;
            }
        }

        //大小王做单张牌打出必定是作为本身的牌的点数
        List<DanzhangDianShuZu> danzhangDianShuZuList = DianShuZuGenerator.generateAllDanzhangDianShuZu(dianshuCountArray);
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>(DianShuZuCalculator.generateAllDanzhangDaPaiDianShuSolution(danzhangDianShuZuList));

        //王炸
        List<WangZhadanDianShuZu> wangZhadanDianShuZuList = DianShuZuCalculator.calculateWangZhadanDianShuZu(dianshuCountArray);
        solutionSet.addAll(DianShuZuCalculator.generateAllWangZhadanDianShuZu(wangZhadanDianShuZuList));

        if (hongxinEr > 0) {
            calculateDaPaiDianShuSolutionWithWangDang(hongxinEr, dianshuCountArray, solutionSet);   //计有王牌出牌方案
        } else {
            calculateDaPaiDianShuSolutionWithoutWangDang(dianshuCountArray, solutionSet);           //计算没有王牌出牌方案
        }

        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (daPaiDianShuSolution != null) {
                DianShuZu dianShuZu = daPaiDianShuSolution.getDianShuZu();
                // 有可能出现打出点数相同类型却不同的情况
                if (!solution.getDianShuZu().getClass().equals(dianShuZu.getClass())) {
                } else if (dianShuZu instanceof LianXuDianShuZu) {
                    try {
                        if (lianXuDianShuZuComparator.compare((LianXuDianShuZu) solution.getDianShuZu(), (LianXuDianShuZu) dianShuZu) > 0) {
                            yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                        }
                    } catch (CanNotCompareException e) {
                    }
                } else if (dianShuZu instanceof LianXuZhadanDianShuZu) {
                    if (zhadanComparator.compare((ZhadanDianShuZu) solution.getDianShuZu(), (ZhadanDianShuZu) dianShuZu) > 0) {
                        yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                    }
                }
            } else {
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            }
        });

        return yaPaiSolutionCandidates;
    }

    /**
     * 根据玩家拥有王牌的数量计算王牌可以当的牌
     *
     * @param wangCount         王牌数量
     * @param dianshuCountArray 牌面点数数组
     */
    private List<DianShu> verifyDangFa(int wangCount, int[] dianshuCountArray) {
        Set<DianShu> kedangDianShuSet = new HashSet<>();
        for (int i = 0; i < 13; i++) {
            if (dianshuCountArray[i] > 0) {
                kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i));
                for (int j = 1; j <= wangCount; j++) {
                    if (i - j >= 0) {
                        kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i - j));
                    }
                }
                for (int j = 1; j <= wangCount; j++) {
                    if (i + j < 13) {
                        kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i + j));
                    }
                }
            }
        }
        return new ArrayList<>(kedangDianShuSet);
    }

    /**
     * 计算有王牌的出牌方案
     *
     * @param wangCount         王牌数量
     * @param dianshuCountArray 点数数组
     * @param solutionSet       出牌方案
     */
    private void calculateDaPaiDianShuSolutionWithWangDang(int wangCount, int[] dianshuCountArray, Set<DaPaiDianShuSolution> solutionSet) {
        // 计算可以当的牌，提高性能
        List<DianShu> kedangDianShuList = verifyDangFa(wangCount, dianshuCountArray);
        if (!kedangDianShuList.isEmpty()) {
            // 循环王的各种当法
            int size = kedangDianShuList.size();
            int maxZuheCode = (int) Math.pow(size, wangCount);
            int[] modArray = new int[wangCount];
            for (int m = 0; m < wangCount; m++) {
                modArray[m] = (int) Math.pow(size, wangCount - 1 - m);
            }
            for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {
                ShoupaiJiesuanPai[] hongxinErDangPaiArray = new ShoupaiJiesuanPai[wangCount];
                int temp = zuheCode;
                int previousGuipaiDangIdx = 0;
                for (int n = 0; n < wangCount; n++) {
                    int mod = modArray[n];
                    int shang = temp / mod;
                    if (shang >= previousGuipaiDangIdx) {   //计算王的各种当法，排除效果相同的当法
                        int yu = temp % mod;
                        hongxinErDangPaiArray[n] = new HongxinErDangPai(kedangDianShuList.get(shang));

                        temp = yu;
                        previousGuipaiDangIdx = shang;
                    } else {
                        hongxinErDangPaiArray = null;
                        break;
                    }
                }
                if (hongxinErDangPaiArray != null) {
                    // 加上当牌的数量
                    for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                        dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]++;
                    }
                    PaiXing paiXing = new PaiXing();
                    //对子
                    paiXing.setDuiziDianShuZuList(DianShuZuCalculator.calculateDuiziDianShuZu(dianshuCountArray));
                    //三张
                    paiXing.setSanzhangDianShuZuList(DianShuZuCalculator.calculateSanzhangDianShuZu(dianshuCountArray));
                    //顺子
                    paiXing.setShunziDianShuZuList(DianShuZuCalculator.calculateShunziDianShuZu(dianshuCountArray));
                    //连对
                    paiXing.setLianduiDianShuZuList(DianShuZuCalculator.calculateLianduiDianShuZu(dianshuCountArray));
                    //连三张
                    paiXing.setLiansanzhangDianShuZuList(DianShuZuCalculator.calculateLiansanzhangDianShuZu(dianshuCountArray));
                    //三带二
                    paiXing.setSandaierDianShuZuArrayList(DianShuZuCalculator.calculateSandaierDianShuZu(dianshuCountArray));
                    //普通炸弹
                    paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
//                    //连续炸弹
//                    paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));

                    solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithWangDang(paiXing, hongxinErDangPaiArray, dianshuCountArray, bx));
                    // 减去当牌的数量
                    for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                        dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]--;
                    }
                }
            }
        }

    }

    /**
     * 计算没有王牌的出牌方案
     *
     * @param dianshuCountArray 点数数组
     * @param solutionSet       出牌方案
     */
    private void calculateDaPaiDianShuSolutionWithoutWangDang(int[] dianshuCountArray, Set<DaPaiDianShuSolution> solutionSet) {
        PaiXing paiXing = new PaiXing();
        //对子
        paiXing.setDuiziDianShuZuList(DianShuZuCalculator.calculateDuiziDianShuZu(dianshuCountArray));
        //三张
        paiXing.setSanzhangDianShuZuList(DianShuZuCalculator.calculateSanzhangDianShuZu(dianshuCountArray));
        //顺子
        paiXing.setShunziDianShuZuList(DianShuZuCalculator.calculateShunziDianShuZu(dianshuCountArray));
        //连对
        paiXing.setLianduiDianShuZuList(DianShuZuCalculator.calculateLianduiDianShuZu(dianshuCountArray));
        //连三张
        paiXing.setLiansanzhangDianShuZuList(DianShuZuCalculator.calculateLiansanzhangDianShuZu(dianshuCountArray));
        //三带二
        paiXing.setSandaierDianShuZuArrayList(DianShuZuCalculator.calculateSandaierDianShuZu(dianshuCountArray));
        //普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
//        //连续炸弹
//        paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));

        solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithoutWangDang(paiXing));
    }

    public BianXingWanFa getBx() {
        return bx;
    }

    public void setBx(BianXingWanFa bx) {
        this.bx = bx;
    }

    public ZhadanComparator getZhadanComparator() {
        return zhadanComparator;
    }

    public void setZhadanComparator(ZhadanComparator zhadanComparator) {
        this.zhadanComparator = zhadanComparator;
    }

    public LianXuDianShuZuComparator getLianXuDianShuZuComparator() {
        return lianXuDianShuZuComparator;
    }

    public void setLianXuDianShuZuComparator(LianXuDianShuZuComparator lianXuDianShuZuComparator) {
        this.lianXuDianShuZuComparator = lianXuDianShuZuComparator;
    }

}
