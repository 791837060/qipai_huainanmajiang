package com.anbang.qipai.paodekuai.cqrs.c.domain;

import java.util.*;

import com.dml.paodekuai.pai.dianshuzu.*;
import org.apache.commons.lang.ArrayUtils;

import com.dml.paodekuai.pai.dianshuzu.comparator.DaipaiComparator;
import com.dml.paodekuai.player.action.da.AllKedaPaiSolutionsGenerator;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZuGenerator;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.LianXuDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;

public class PaodekuaiAllKedaPaiSolutionsGenerator implements AllKedaPaiSolutionsGenerator {
    private OptionalPlay optionalPlay;

    private ZhadanComparator zhadanComparator;

    private LianXuDianShuZuComparator lianXuDianShuZuComparator;

    private DaipaiComparator daipaiComparator;

    @Override
    public Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean baodan) {
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

        // 所有手牌的点数数量数组
        int[] dianshuCountArray = new int[15];
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
        }

        // 可打的单张牌
        if (baodan) {
            List<DanzhangDianShuZu> danzhangDianShuZuList = PaodekuaiDianShuZuGenerator.largestDanzhangDianshuzu(dianshuCountArray);
            solutionSet.addAll(DianShuZuCalculator.generateAllDanzhangDaPaiDianShuSolution(danzhangDianShuZuList));
        } else {
            List<DanzhangDianShuZu> danzhangDianShuZuList = DianShuZuGenerator.generateAllDanzhangDianShuZu(dianshuCountArray);
            solutionSet.addAll(DianShuZuCalculator.generateAllDanzhangDaPaiDianShuSolution(danzhangDianShuZuList));
        }

        if (optionalPlay.isSanAJiaYiBoom()) {
            //3A加1炸
            List<SanAJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate3AJia1BoomZhadanDianShuZu(dianshuCountArray);
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
        List<ABoomDianShuZu> aBoomDianShuZus = DianShuZuCalculator.calculateABoomZhadanDianShuZu(dianshuCountArray);
        for (ABoomDianShuZu aBoomDianShuZu : aBoomDianShuZus) {
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

        if (optionalPlay.isSan3JiaYiBoom()) {
            //33加1炸
            List<SanSanJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate33Jia1BoomZhadanDianShuZu(dianshuCountArray);
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
        List<SanBoomDianShuZu> san3BoomDianShuZus = DianShuZuCalculator.calculateSanBoomZhadanDianShuZu(dianshuCountArray);
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

        // 其他牌型的出牌方案
        calculateDaPaiDianShuSolutionWithoutWangDang(dianshuCountArray, solutionSet, allShoupai.size());
        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (solution.getDianShuZu() instanceof ZhadanDianShuZu) {// 炸弹最大
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            } else {
                if (daPaiDianShuSolution == null) {
                    yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                }
            }
        });

        return yaPaiSolutionCandidates;
    }

    @Override
    public Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean baodan, boolean dachuHeitaoSan, boolean dachuHonxinSan, boolean notExistentSan) {
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

        // 所有手牌的点数数量数组
        int[] dianshuCountArray = new int[15];
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
        }

        // 可打的单张牌
        if (baodan) {
            List<DanzhangDianShuZu> danzhangDianShuZuList = PaodekuaiDianShuZuGenerator.largestDanzhangDianshuzu(dianshuCountArray);
            solutionSet.addAll(DianShuZuCalculator.generateAllDanzhangDaPaiDianShuSolution(danzhangDianShuZuList));
        } else {
            List<DanzhangDianShuZu> danzhangDianShuZuList = DianShuZuGenerator.generateAllDanzhangDianShuZu(dianshuCountArray);
            solutionSet.addAll(DianShuZuCalculator.generateAllDanzhangDaPaiDianShuSolution(danzhangDianShuZuList));
        }

        if (optionalPlay.isSanAJiaYiBoom()) {
            //3A加1炸
            List<SanAJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate3AJia1BoomZhadanDianShuZu(dianshuCountArray);
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
        List<ABoomDianShuZu> aBoomDianShuZus = DianShuZuCalculator.calculateABoomZhadanDianShuZu(dianshuCountArray);
        for (ABoomDianShuZu aBoomDianShuZu : aBoomDianShuZus) {
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
                List<SanSanJiaYiBoomDianShuZu> sanAJiaYiBoomDianShuZus = DianShuZuCalculator.calculate33Jia1BoomZhadanDianShuZu(dianshuCountArray);
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
            List<SanBoomDianShuZu> san3BoomDianShuZus = DianShuZuCalculator.calculateSanBoomZhadanDianShuZu(dianshuCountArray);
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

        // 其他牌型的出牌方案
        calculateDaPaiDianShuSolutionWithoutWangDang(dianshuCountArray, solutionSet, allShoupai.size());
        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (solution.getDianShuZu() instanceof ZhadanDianShuZu) {// 炸弹最大
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            } else {
                if (daPaiDianShuSolution == null) {
                    yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
                }
            }
        });

        return yaPaiSolutionCandidates;
    }

    @Override
    public Map<String, DaPaiDianShuSolution> firstAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai, boolean notExistentSan) {
        // 不是首张必出黑桃三即为一般方法
        if (!optionalPlay.isBichu() && !optionalPlay.isHongxinsanxianchu()) {
            return generateAllKedaPaiSolutions(allShoupai, false, false, false, notExistentSan);
        }

        // 黑桃三必出玩法 红心三
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Map<String, DaPaiDianShuSolution> allPaiSolutionCandidates = generateAllKedaPaiSolutions(allShoupai, false, false, false, notExistentSan);
        PukePaiMian bichupai = null;
        boolean hasBichuPai = false;
        PukePaiMian[] values = PukePaiMian.values();

        int index = 0;
        if (optionalPlay.isHongxinsanxianchu()) {
            index = 1;
        }

        for (int i = index; i < 52; i += 4) {
            PukePaiMian pukePaiMian = values[i];
            if (!hasBichuPai) {
                for (PukePai pukePai : allShoupai.values()) {
                    if (pukePai.getPaiMian().equals(pukePaiMian)) {
                        bichupai = pukePaiMian;
                        hasBichuPai = true;
                        break;
                    }
                }
            }
        }
        PukePaiMian finalBichupai = bichupai;
        allPaiSolutionCandidates.forEach((k, v) -> {
            if (ArrayUtils.contains(v.getDachuDianShuArray(), finalBichupai.dianShu())) {
                v.getBichuPai().add(finalBichupai);
                yaPaiSolutionCandidates.put(k, v);
            }
        });
        return yaPaiSolutionCandidates;
    }

    private void calculateDaPaiDianShuSolutionWithoutWangDang(int[] dianshuCountArray, Set<DaPaiDianShuSolution> solutionSet, int shoupaiCount) {
        PaiXing paiXing = new PaiXing();
        // 对子
        paiXing.setDuiziDianShuZuList(DianShuZuCalculator.calculateDuiziDianShuZu(dianshuCountArray));
        // 顺子
        paiXing.setShunziDianShuZuList(DianShuZuCalculator.calculateShunziDianShuZu(dianshuCountArray, optionalPlay.isA2Xiafang()));
        // 连对
        paiXing.setLianduiDianShuZuList(DianShuZuCalculator.calculateLianduiDianShuZu(dianshuCountArray, optionalPlay.isA2Xiafang()));
        // 三张
        paiXing.setSanzhangDianShuList(DianShuZuCalculator.calculateSanzhangDianShuZu(dianshuCountArray));
        // 四带二
        if (optionalPlay.isSidaier()) {
            paiXing.setSidaierDianShuZulist(DianShuZuCalculator.calculateSidaierDianShuZu(dianshuCountArray));
        } else {
            paiXing.setSidaierDianShuZulist(new ArrayList<>());
        }
        // 四带三
        if (optionalPlay.isSidaisan()) {
            paiXing.setSidaisanDianShuZuList(DianShuZuCalculator.calculateSidaisanDianShuZu(dianshuCountArray));
        } else {
            paiXing.setSidaisanDianShuZuList(new ArrayList<>());
        }
        // 三带一
        paiXing.setSandaiyiDianShuZuArrayList(DianShuZuCalculator.calculateSandaiyiDianShuZu(dianshuCountArray, shoupaiCount, optionalPlay.isSandaique()));
        // 三带二
        paiXing.setSandaierDianShuZuArrayList(DianShuZuCalculator.calculateSandaierDianShuZu(dianshuCountArray, shoupaiCount, optionalPlay.isSandaique(), optionalPlay.isSandailiangdan()));
        // 飞机
        paiXing.setFeijiDianShuZuArrayList(DianShuZuCalculator.calculateFeijiDianShuZu(dianshuCountArray, shoupaiCount, optionalPlay.isFeijique(), optionalPlay.isSandailiangdan()));
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
        if (optionalPlay.isSidaiyiBoom()) {
            //带牌炸弹（四带一）
            paiXing.setDaipaiZhaDanDianShuZuList((DianShuZuCalculator.calculateDaiPaiZhaDanDianShuZu(dianshuCountArray)));
        }
        solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithoutWangDang(paiXing, optionalPlay.isSidaiyiBoom()));
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

    public LianXuDianShuZuComparator getLianXuDianShuZuComparator() {
        return lianXuDianShuZuComparator;
    }

    public void setLianXuDianShuZuComparator(LianXuDianShuZuComparator lianXuDianShuZuComparator) {
        this.lianXuDianShuZuComparator = lianXuDianShuZuComparator;
    }

    public DaipaiComparator getDaipaiComparator() {
        return daipaiComparator;
    }

    public void setDaipaiComparator(DaipaiComparator daipaiComparator) {
        this.daipaiComparator = daipaiComparator;
    }
}
