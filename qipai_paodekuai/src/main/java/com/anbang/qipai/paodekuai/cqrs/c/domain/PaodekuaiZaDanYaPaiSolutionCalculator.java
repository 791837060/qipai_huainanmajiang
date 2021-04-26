package com.anbang.qipai.paodekuai.cqrs.c.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.paodekuai.pai.dianshuzu.*;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.paodekuai.player.action.da.solution.ZaDanYaPaiSolutionCalculator;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;

public class PaodekuaiZaDanYaPaiSolutionCalculator implements ZaDanYaPaiSolutionCalculator {
    private OptionalPlay optionalPlay;
    private ZhadanComparator zhadanComparator;

    /**
     * 炸弹压牌计算
     *
     * @param beiYaDianShuZu     被压点数组
     * @param dianShuAmountArray 手牌点数集合
     */
    @Override
    public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray) {
        int[] dianShuAmount = dianShuAmountArray.clone();
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

        if (optionalPlay.isSanAJiaYiBoom()) {
            //3A加1炸
            List<SanAJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate3AJia1BoomZhadanDianShuZu(dianShuAmount);
            for (SanAJiaYiBoomDianShuZu aBoomDianShuZu : sanAJiaYiBoomDianShuZus) {
                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                solution.setDianShuZu(aBoomDianShuZu);
                DianShu[] dachuDianShuArray = {
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDaipaiDianshu()
                };
                solution.setDachuDianShuArray(dachuDianShuArray);
                solution.calculateDianshuZuheIdx();
                solutionSet.add(solution);
            }
        }
        //3A炸
        List<ABoomDianShuZu> aBoomDianShuZus = DianShuZuCalculator.calculateABoomZhadanDianShuZu(dianShuAmount);
        for (ABoomDianShuZu aBoomDianShuZu : aBoomDianShuZus) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(aBoomDianShuZu);
            DianShu[] dachuDianShuArray = {
                    aBoomDianShuZu.getDianShu(), aBoomDianShuZu.getDianShu(), aBoomDianShuZu.getDianShu()
            };
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionSet.add(solution);
        }
        if (beiYaDianShuZu instanceof DanGeZhadanDianShuZu || beiYaDianShuZu instanceof ABoomDianShuZu || beiYaDianShuZu instanceof SanAJiaYiBoomDianShuZu) {
            //3个3炸弹和3个3加一炸弹是最小的炸弹 只能压普通牌 不能压炸弹
        } else {
            if (optionalPlay.isSan3JiaYiBoom()) {
                //33加1炸
                List<SanSanJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate33Jia1BoomZhadanDianShuZu(dianShuAmount);
                for (SanSanJiaYiBoomDianShuZu aBoomDianShuZu : sanAJiaYiBoomDianShuZus) {
                    DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                    solution.setDianShuZu(aBoomDianShuZu);
                    DianShu[] dachuDianShuArray = {
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu2()
                    };
                    solution.setDachuDianShuArray(dachuDianShuArray);
                    solution.calculateDianshuZuheIdx();
                    solutionSet.add(solution);
                }
            }
            //33炸
            List<SanBoomDianShuZu> san3BoomDianShuZus = DianShuZuCalculator.calculateSanBoomZhadanDianShuZu(dianShuAmount);
            for (SanBoomDianShuZu aBoomDianShuZu : san3BoomDianShuZus) {
                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                solution.setDianShuZu(aBoomDianShuZu);
                DianShu[] dachuDianShuArray = {
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu()
                };
                solution.setDachuDianShuArray(dachuDianShuArray);
                solution.calculateDianshuZuheIdx();
                solutionSet.add(solution);
            }
        }
        //跑的快没有王牌
        calculateDaPaiDianShuSolutionWithoutWangDang(dianShuAmount, beiYaDianShuZu, solutionSet);

        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (daPaiDianShuSolution != null) {
                DianShuZu dianShuZu = daPaiDianShuSolution.getDianShuZu();
                // 有可能出现打出点数相同类型却不同的情况
                if (!solution.getDianShuZu().getClass().equals(dianShuZu.getClass())) {

                } else if (dianShuZu instanceof ABoomDianShuZu) {
                    yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                }
            } else {
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            }
        });
        return yaPaiSolutionCandidates;
    }

    @Override
    public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray, boolean dachuHeitaoSan, boolean dachuHonxinSan, boolean notExistentSan) {
        int[] dianShuAmount = dianShuAmountArray.clone();
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

        if (optionalPlay.isSanAJiaYiBoom()) {
            //3A加1炸
            List<SanAJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate3AJia1BoomZhadanDianShuZu(dianShuAmount);
            for (SanAJiaYiBoomDianShuZu aBoomDianShuZu : sanAJiaYiBoomDianShuZus) {
                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                solution.setDianShuZu(aBoomDianShuZu);
                DianShu[] dachuDianShuArray = {
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDaipaiDianshu()
                };
                solution.setDachuDianShuArray(dachuDianShuArray);
                solution.calculateDianshuZuheIdx();
                solutionSet.add(solution);
            }
        }
        //3A炸
        List<ABoomDianShuZu> aBoomDianShuZus = DianShuZuCalculator.calculateABoomZhadanDianShuZu(dianShuAmount);
        for (ABoomDianShuZu aBoomDianShuZu : aBoomDianShuZus) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(aBoomDianShuZu);
            DianShu[] dachuDianShuArray = {
                    aBoomDianShuZu.getDianShu(), aBoomDianShuZu.getDianShu(), aBoomDianShuZu.getDianShu()
            };
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionSet.add(solution);
        }

        boolean dachuSan = false;
        if (optionalPlay.isBichu() && dachuHeitaoSan) {
            dachuSan = true;
        } else if (optionalPlay.isHongxinsanxianchu() && dachuHonxinSan) {
            dachuSan = true;
        }

        if (!optionalPlay.isBichu() && !optionalPlay.isHongxinsanxianchu()) {
            notExistentSan = false;
        }


        if (dachuSan || notExistentSan) {
            if (optionalPlay.isSan3JiaYiBoom()) {
                //33加1炸
                List<SanSanJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate33Jia1BoomZhadanDianShuZu(dianShuAmount);
                for (SanSanJiaYiBoomDianShuZu aBoomDianShuZu : sanAJiaYiBoomDianShuZus) {
                    DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                    solution.setDianShuZu(aBoomDianShuZu);
                    DianShu[] dachuDianShuArray = {
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu(),
                            aBoomDianShuZu.getDianShu2()
                    };
                    solution.setDachuDianShuArray(dachuDianShuArray);
                    solution.calculateDianshuZuheIdx();
                    if (beiYaDianShuZu instanceof DanGeZhadanDianShuZu ||
                            beiYaDianShuZu instanceof ABoomDianShuZu ||
                            beiYaDianShuZu instanceof SanAJiaYiBoomDianShuZu) {
                    } else {
                        solutionSet.add(solution);
                    }

                }
            }
            //33炸
            List<SanBoomDianShuZu> san3BoomDianShuZus = DianShuZuCalculator.calculateSanBoomZhadanDianShuZu(dianShuAmount);
            for (SanBoomDianShuZu aBoomDianShuZu : san3BoomDianShuZus) {
                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                solution.setDianShuZu(aBoomDianShuZu);
                DianShu[] dachuDianShuArray = {
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu(),
                        aBoomDianShuZu.getDianShu()
                };
                solution.setDachuDianShuArray(dachuDianShuArray);
                solution.calculateDianshuZuheIdx();
                if (beiYaDianShuZu instanceof DanGeZhadanDianShuZu ||
                        beiYaDianShuZu instanceof ABoomDianShuZu ||
                        beiYaDianShuZu instanceof SanAJiaYiBoomDianShuZu) {
                } else {
                    solutionSet.add(solution);
                }
            }
        }

        // 跑的快没有王牌
        calculateDaPaiDianShuSolutionWithoutWangDang(dianShuAmount, beiYaDianShuZu, solutionSet);

        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (daPaiDianShuSolution != null) {
                DianShuZu dianShuZu = daPaiDianShuSolution.getDianShuZu();
                // 有可能出现打出点数相同类型却不同的情况
                if (!solution.getDianShuZu().getClass().equals(dianShuZu.getClass())) {

                } else if (dianShuZu instanceof ABoomDianShuZu) {
                    yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                }
            } else {
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            }
        });
        return yaPaiSolutionCandidates;
    }

    /**
     * 计算没有王牌压牌方案
     *
     * @param dianshuCountArray 手牌点数数组
     * @param beiYaDianShuZu    被压点数组
     * @param solutionSet       出牌方案
     */
    private void calculateDaPaiDianShuSolutionWithoutWangDang(int[] dianshuCountArray, DianShuZu beiYaDianShuZu, Set<DaPaiDianShuSolution> solutionSet) {
        PaiXing paiXing = new PaiXing();
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
        paiXing.setDaipaiZhaDanDianShuZuList(DianShuZuCalculator.calculateDaiPaiZhaDanDianShuZu(dianshuCountArray));
        paiXing = paiXingFilter(paiXing, beiYaDianShuZu);
        solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithoutWangDang(paiXing, optionalPlay.isSidaiyiBoom()));
    }

    /**
     * 计算合理压牌牌型
     *
     * @param paiXing        牌型
     * @param beiYaDianShuZu 被压点数组
     */
    private PaiXing paiXingFilter(PaiXing paiXing, DianShuZu beiYaDianShuZu) {
        PaiXing filtedPaiXing = new PaiXing();
        if (beiYaDianShuZu instanceof ZhadanDianShuZu) {    //被压点数组是炸弹
            if (beiYaDianShuZu instanceof DaiPaiZhaDanDianShuZu) {  //被压点数组是带牌炸弹
                DaiPaiZhaDanDianShuZu beiYaZhadanDianShuZu = (DaiPaiZhaDanDianShuZu) beiYaDianShuZu;
                List<DanGeZhadanDianShuZu> filtedDanGeZhadanDianShuZuList = filtedPaiXing.getDanGeZhadanDianShuZuList();
                List<DanGeZhadanDianShuZu> zhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
                for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanDianShuZuList) {
                    if (zhadanComparator.compare(danGeZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                        filtedDanGeZhadanDianShuZuList.add(danGeZhadanDianShuZu);
                    }
                }
                List<DaiPaiZhaDanDianShuZu> filtedDaipaiZhaDanDianShuZuList = filtedPaiXing.getDaipaiZhaDanDianShuZuList();
                List<DaiPaiZhaDanDianShuZu> daipaiZhaDanDianShuZuList = paiXing.getDaipaiZhaDanDianShuZuList();
                for (DaiPaiZhaDanDianShuZu daiPaiZhaDanDianShuZu : daipaiZhaDanDianShuZuList) {
                    if (zhadanComparator.compare(daiPaiZhaDanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                        filtedDaipaiZhaDanDianShuZuList.add(daiPaiZhaDanDianShuZu);
                    }
                }
            } else {    //被压点数组不是带牌炸弹
                ZhadanDianShuZu beiYaZhadanDianShuZu = (ZhadanDianShuZu) beiYaDianShuZu;
                List<DanGeZhadanDianShuZu> filtedDanGeZhadanDianShuZuList = filtedPaiXing.getDanGeZhadanDianShuZuList();
                List<DanGeZhadanDianShuZu> zhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
                for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanDianShuZuList) {
                    if (zhadanComparator.compare(danGeZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                        filtedDanGeZhadanDianShuZuList.add(danGeZhadanDianShuZu);
                    }
                }
                List<DaiPaiZhaDanDianShuZu> filtedDaipaiZhaDanDianShuZuList = filtedPaiXing.getDaipaiZhaDanDianShuZuList();
                List<DaiPaiZhaDanDianShuZu> daipaiZhaDanDianShuZuList = paiXing.getDaipaiZhaDanDianShuZuList();
                for (DaiPaiZhaDanDianShuZu daiPaiZhaDanDianShuZu : daipaiZhaDanDianShuZuList) {
                    if (zhadanComparator.compare(daiPaiZhaDanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                        filtedDaipaiZhaDanDianShuZuList.add(daiPaiZhaDanDianShuZu);
                    }
                }
            }
        } else {
            filtedPaiXing.setDanGeZhadanDianShuZuList(paiXing.getDanGeZhadanDianShuZuList());
            filtedPaiXing.setDaipaiZhaDanDianShuZuList(paiXing.getDaipaiZhaDanDianShuZuList());
        }
        return filtedPaiXing;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public ZhadanComparator getZhadanComparator() {
        return zhadanComparator;
    }

    public void setZhadanComparator(ZhadanComparator zhadanComparator) {
        this.zhadanComparator = zhadanComparator;
    }

}
