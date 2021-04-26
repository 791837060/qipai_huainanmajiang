package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianXuDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.CanNotCompareException;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.DanGeDianShuZuComparator;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.LianXuDianShuZuComparator;
import com.dml.shuangkou.pai.dianshuzu.DaipaiComparator;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuCalculator;
import com.dml.shuangkou.pai.dianshuzu.PaiXing;
import com.dml.shuangkou.pai.dianshuzu.SandaierDianShuZu;
import com.dml.shuangkou.pai.jiesuanpai.HongxinErDangPai;
import com.dml.shuangkou.pai.jiesuanpai.ShoupaiJiesuanPai;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.player.action.da.solution.DianShuZuYaPaiSolutionCalculator;
import com.dml.shuangkou.wanfa.BianXingWanFa;

public class GuandanDianShuZuYaPaiSolutionCalculator implements DianShuZuYaPaiSolutionCalculator {
    private BianXingWanFa bx;
    private DanGeDianShuZuComparator danGeDianShuZuComparator;
    private LianXuDianShuZuComparator lianXuDianShuZuComparator;
    private DaipaiComparator daipaiComparator;

    /**
     * 计算点数压牌牌型
     *
     * @param beiYaDianShuZu     要压的牌牌型
     * @param dianShuAmountArray 点数数量数组
     */
    @Override
    public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray) {
        int[] dianShuAmount = dianShuAmountArray.clone();
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

        // 单张
        if (beiYaDianShuZu instanceof DanzhangDianShuZu) {
            DanzhangDianShuZu beiYaDanzhangDianShuZu = (DanzhangDianShuZu) beiYaDianShuZu;
            //大小王做单张牌打出必定是作为本身的牌的点数
            List<DanzhangDianShuZu> danzhangDianShuZuList = DianShuZuCalculator.calculateDanzhangDianShuZu(dianShuAmount);
            for (DanzhangDianShuZu danzhangDianShuZu : danzhangDianShuZuList) {
                try {
                    if (danGeDianShuZuComparator.compare(danzhangDianShuZu, beiYaDanzhangDianShuZu) > 0) {
                        if (beiYaDanzhangDianShuZu.getDianShu().equals(DianShu.xiaowang) || beiYaDanzhangDianShuZu.getDianShu().equals(DianShu.dawang)) {
                            if (danzhangDianShuZu.getDianShu().equals(DianShu.hongxiner2)) {
                                continue;
                            }
                        }
                        DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                        solution.setDianShuZu(danzhangDianShuZu);
                        DianShu[] dachuDianShuArray = {danzhangDianShuZu.getDianShu()};
                        solution.setDachuDianShuArray(dachuDianShuArray);
                        solution.calculateDianshuZuheIdx();
                        solutionSet.add(solution);
                    } else {
                        if (beiYaDanzhangDianShuZu.getDianShu().equals(DianShu.hongxiner2)) {
                            if (danzhangDianShuZu.getDianShu().equals(DianShu.xiaowang) || danzhangDianShuZu.getDianShu().equals(DianShu.dawang)) {
                                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                                solution.setDianShuZu(danzhangDianShuZu);
                                DianShu[] dachuDianShuArray = {danzhangDianShuZu.getDianShu()};
                                solution.setDachuDianShuArray(dachuDianShuArray);
                                solution.calculateDianshuZuheIdx();
                                solutionSet.add(solution);
                            }
                        }
                    }
                } catch (CanNotCompareException e) {

                }
            }
            solutionSet.forEach((solution) -> yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution));
            return yaPaiSolutionCandidates;
        }

//        int xiaowangCount = dianShuAmount[13];
//        int dawangCount = dianShuAmount[14];
        int hongxinErCount = dianShuAmount[15];

        if (beiYaDianShuZu instanceof DuiziDianShuZu) {
            DuiziDianShuZu beiYaDuiziDianShuZu = (DuiziDianShuZu) beiYaDianShuZu;
            try {

//                // 对子，当有大小王个一张时可以当一对大王打出
//                if (xiaowangCount > 0 && dawangCount > 0) {
//                    DuiziDianShuZu duiziDianShuZu = null;
//                    if (BianXingWanFa.qianbian.equals(bx)) {
//                        duiziDianShuZu = new DuiziDianShuZu(DianShu.dawang);
//                    } else {
//                        duiziDianShuZu = new DuiziDianShuZu(DianShu.xiaowang);
//                    }
//                    if (danGeDianShuZuComparator.compare(duiziDianShuZu, beiYaDuiziDianShuZu) > 0) {
//                        DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
//                        solution.setDianShuZu(duiziDianShuZu);
//                        DianShu[] dachuDianShuArray = {DianShu.xiaowang, DianShu.dawang};
//                        solution.setDachuDianShuArray(dachuDianShuArray);
//                        solution.calculateDianshuZuheIdx();
//                        solutionSet.add(solution);
//                    }
//                }
//                if (dawangCount > 1) {
//                    DuiziDianShuZu duiziDianShuZu = new DuiziDianShuZu(DianShu.dawang);
//                    if (danGeDianShuZuComparator.compare(duiziDianShuZu, beiYaDuiziDianShuZu) > 0) {
//                        DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
//                        solution.setDianShuZu(duiziDianShuZu);
//                        DianShu[] dachuDianShuArray = {DianShu.dawang, DianShu.dawang};
//                        solution.setDachuDianShuArray(dachuDianShuArray);
//                        solution.calculateDianshuZuheIdx();
//                        solutionSet.add(solution);
//                    }
//                }
//                if (xiaowangCount > 1) {
//                    DuiziDianShuZu duiziDianShuZu = new DuiziDianShuZu(DianShu.xiaowang);
//                    if (danGeDianShuZuComparator.compare(duiziDianShuZu, beiYaDuiziDianShuZu) > 0) {
//                        DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
//                        solution.setDianShuZu(duiziDianShuZu);
//                        DianShu[] dachuDianShuArray = {DianShu.xiaowang, DianShu.xiaowang};
//                        solution.setDachuDianShuArray(dachuDianShuArray);
//                        solution.calculateDianshuZuheIdx();
//                        solutionSet.add(solution);
//                    }
//                }

                if (hongxinErCount > 1) {
                    DuiziDianShuZu duiziDianShuZu = new DuiziDianShuZu(DianShu.hongxiner2);
                    if (danGeDianShuZuComparator.compare(duiziDianShuZu, beiYaDuiziDianShuZu) > 0) {
                        DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                        solution.setDianShuZu(duiziDianShuZu);
                        DianShu[] dachuDianShuArray = {DianShu.hongxiner2, DianShu.hongxiner2};
                        solution.setDachuDianShuArray(dachuDianShuArray);
                        solution.calculateDianshuZuheIdx();
                        solutionSet.add(solution);
                    }
                }

            } catch (CanNotCompareException e) {

            }
        }

        int wangCount = 0;

//        if (BianXingWanFa.qianbian.equals(bx)) {    //千变
//            wangCount = xiaowangCount + dawangCount;
//            // 减去王牌的数量
//            dianShuAmount[13] = dianShuAmount[13] - xiaowangCount;
//            dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//        } else if (BianXingWanFa.banqianbian.equals(bx)) {  //半千变;
//            wangCount = dawangCount;
//            // 减去王牌的数量
//            if (xiaowangCount > 0 && xiaowangCount % 2 == 0) {
//                wangCount++;
//                dianShuAmount[13] = dianShuAmount[13] - 2;
//            }
//            dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//        } else if (BianXingWanFa.baibian.equals(bx)) {  //百变
//            wangCount = dawangCount;
//            // 减去王牌的数量
//            dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//        } else {
//
//        }

        //减去红心2的数量
        wangCount = hongxinErCount;
        dianShuAmount[15] = dianShuAmount[15] - wangCount;

        if (wangCount > 0) {
            calculateDaPaiDianShuSolutionWithWangDang(wangCount, dianShuAmount, beiYaDianShuZu, solutionSet);//有王牌
        } else {
            calculateDaPaiDianShuSolutionWithoutWangDang(dianShuAmount, beiYaDianShuZu, solutionSet); //没有王牌
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
                }
            } else {
                yaPaiSolutionCandidates.put(solution.getDianshuZuheIdx(), solution);
            }
        });
        return yaPaiSolutionCandidates;
    }

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

    private void calculateDaPaiDianShuSolutionWithWangDang(int wangCount, int[] dianshuCountArray, DianShuZu beiYaDianShuZu, Set<DaPaiDianShuSolution> solutionSet) {
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
                    if (shang >= previousGuipaiDangIdx) {// 计算王的各种当法，排除效果相同的当法
                        int yu = temp % mod;

//						if (BianXingWanFa.qianbian.equals(bx)) {// 千变
//							if (n < dawangCount) {
//								wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//							} else {
//								wangDangPaiArray[n] = new XiaowangDangPai(1, kedangDianShuList.get(shang));
//							}
//						} else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
//							if (n < dawangCount) {
//								wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//							} else {
//								wangDangPaiArray[n] = new XiaowangDangPai(2, kedangDianShuList.get(shang));
//							}
//						} else if (BianXingWanFa.baibian.equals(bx)) {// 百变
//							wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//						} else {
//
//						}

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
                    if (beiYaDianShuZu instanceof DuiziDianShuZu) {
                        paiXing.setDuiziDianShuZuList(DianShuZuCalculator.calculateDuiziDianShuZu(dianshuCountArray));                  //对子
                    }
                    if (beiYaDianShuZu instanceof SanzhangDianShuZu) {
                        paiXing.setSanzhangDianShuZuList(DianShuZuCalculator.calculateSanzhangDianShuZu(dianshuCountArray));            //三张
                    }
                    if (beiYaDianShuZu instanceof ShunziDianShuZu) {
                        paiXing.setShunziDianShuZuList(DianShuZuCalculator.calculateShunziDianShuZu(dianshuCountArray));                //顺子
                    }
                    if (beiYaDianShuZu instanceof LianduiDianShuZu) {
                        paiXing.setLianduiDianShuZuList(DianShuZuCalculator.calculateLianduiDianShuZu(dianshuCountArray));              //连对
                    }
                    if (beiYaDianShuZu instanceof LiansanzhangDianShuZu) {
                        paiXing.setLiansanzhangDianShuZuList(DianShuZuCalculator.calculateLiansanzhangDianShuZu(dianshuCountArray));    //连三张
                    }
                    if (beiYaDianShuZu instanceof SandaierDianShuZu) {
                        paiXing.setSandaierDianShuZuArrayList(DianShuZuCalculator.calculateSandaierDianShuZu(dianshuCountArray));       //三带二
                    }

                    paiXing = paiXingFilter(paiXing, beiYaDianShuZu);
                    solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithWangDang(paiXing, hongxinErDangPaiArray, dianshuCountArray, bx));
                    // 减去当牌的数量
                    for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                        dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]--;
                    }
                }
            }
        }

    }

    private void calculateDaPaiDianShuSolutionWithoutWangDang(int[] dianshuCountArray, DianShuZu beiYaDianShuZu, Set<DaPaiDianShuSolution> solutionSet) {
        PaiXing paiXing = new PaiXing();
        if (beiYaDianShuZu instanceof DuiziDianShuZu) {
            //对子
            paiXing.setDuiziDianShuZuList(DianShuZuCalculator.calculateDuiziDianShuZu(dianshuCountArray));
        }
        if (beiYaDianShuZu instanceof SanzhangDianShuZu) {
            //三张
            paiXing.setSanzhangDianShuZuList(DianShuZuCalculator.calculateSanzhangDianShuZu(dianshuCountArray));
        }
        if (beiYaDianShuZu instanceof ShunziDianShuZu) {
            //顺子
            paiXing.setShunziDianShuZuList(DianShuZuCalculator.calculateShunziDianShuZu(dianshuCountArray));
        }
        if (beiYaDianShuZu instanceof LianduiDianShuZu) {
            //连对
            paiXing.setLianduiDianShuZuList(DianShuZuCalculator.calculateLianduiDianShuZu(dianshuCountArray));
        }
        if (beiYaDianShuZu instanceof LiansanzhangDianShuZu) {
            //连三张
            paiXing.setLiansanzhangDianShuZuList(DianShuZuCalculator.calculateLiansanzhangDianShuZu(dianshuCountArray));
        }
        if (beiYaDianShuZu instanceof SandaierDianShuZu) {
            //三带二
            paiXing.setSandaierDianShuZuArrayList(DianShuZuCalculator.calculateSandaierDianShuZu(dianshuCountArray));
        }
        paiXing = paiXingFilter(paiXing, beiYaDianShuZu);

        solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithoutWangDang(paiXing));
    }

    private PaiXing paiXingFilter(PaiXing paiXing, DianShuZu beiYaDianShuZu) {
        PaiXing filtedPaiXing = new PaiXing();
        if (beiYaDianShuZu instanceof DuiziDianShuZu) {
            DuiziDianShuZu beiYaDuiziDianShuZu = (DuiziDianShuZu) beiYaDianShuZu;
            List<DuiziDianShuZu> filtedDuiziDianShuZuList = filtedPaiXing.getDuiziDianShuZuList();
            List<DuiziDianShuZu> duiziDianShuZuList = paiXing.getDuiziDianShuZuList();
            for (DuiziDianShuZu duiziDianShuZu : duiziDianShuZuList) {
                try {
                    if (danGeDianShuZuComparator.compare(duiziDianShuZu, beiYaDuiziDianShuZu) > 0) {
                        filtedDuiziDianShuZuList.add(duiziDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        if (beiYaDianShuZu instanceof SanzhangDianShuZu) {
            SanzhangDianShuZu beiYaSanzhangDianShuZu = (SanzhangDianShuZu) beiYaDianShuZu;
            List<SanzhangDianShuZu> filtedSanzhangDianShuZu = filtedPaiXing.getSanzhangDianShuZuList();
            List<SanzhangDianShuZu> sanzhangDianShuZuList = paiXing.getSanzhangDianShuZuList();
            for (SanzhangDianShuZu sanzhangDianShuZu : sanzhangDianShuZuList) {
                try {
                    if (danGeDianShuZuComparator.compare(sanzhangDianShuZu, beiYaSanzhangDianShuZu) > 0) {
                        filtedSanzhangDianShuZu.add(sanzhangDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        if (beiYaDianShuZu instanceof ShunziDianShuZu) {
            ShunziDianShuZu beiYaShunziDianShuZu = (ShunziDianShuZu) beiYaDianShuZu;
            List<ShunziDianShuZu> filtedShunziDianShuZu = filtedPaiXing.getShunziDianShuZuList();
            List<ShunziDianShuZu> shunziDianShuZuList = paiXing.getShunziDianShuZuList();
            for (ShunziDianShuZu shunziDianShuZu : shunziDianShuZuList) {
                try {
                    if (lianXuDianShuZuComparator.compare(shunziDianShuZu, beiYaShunziDianShuZu) > 0) {
                        filtedShunziDianShuZu.add(shunziDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        if (beiYaDianShuZu instanceof SandaierDianShuZu) {
            SandaierDianShuZu beiYaShunziDianShuZu = (SandaierDianShuZu) beiYaDianShuZu;
            List<SandaierDianShuZu> filtedShunziDianShuZu = filtedPaiXing.getSandaierDianShuZuArrayList();
            List<SandaierDianShuZu> sandaierDianShuZuArrayList = paiXing.getSandaierDianShuZuArrayList();
            for (SandaierDianShuZu sandaierDianShuZu : sandaierDianShuZuArrayList) {
                try {
                    if (daipaiComparator.compare(sandaierDianShuZu, beiYaShunziDianShuZu) > 0) {
                        filtedShunziDianShuZu.add(sandaierDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        if (beiYaDianShuZu instanceof LianduiDianShuZu) {
            LianduiDianShuZu beiYaLianduiDianShuZu = (LianduiDianShuZu) beiYaDianShuZu;
            List<LianduiDianShuZu> filtedLianduiDianShuZu = filtedPaiXing.getLianduiDianShuZuList();
            List<LianduiDianShuZu> lianduiDianShuZuList = paiXing.getLianduiDianShuZuList();
            for (LianduiDianShuZu lianduiDianShuZu : lianduiDianShuZuList) {
                try {
                    if (lianXuDianShuZuComparator.compare(lianduiDianShuZu, beiYaLianduiDianShuZu) > 0) {
                        filtedLianduiDianShuZu.add(lianduiDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        if (beiYaDianShuZu instanceof LiansanzhangDianShuZu) {
            LiansanzhangDianShuZu beiYaLiansanzhangDianShuZu = (LiansanzhangDianShuZu) beiYaDianShuZu;
            List<LiansanzhangDianShuZu> filtedLiansanzhangDianShuZu = filtedPaiXing.getLiansanzhangDianShuZuList();
            List<LiansanzhangDianShuZu> liansanzhangDianShuZuList = paiXing.getLiansanzhangDianShuZuList();
            for (LiansanzhangDianShuZu liansanzhangDianShuZu : liansanzhangDianShuZuList) {
                try {
                    if (lianXuDianShuZuComparator.compare(liansanzhangDianShuZu, beiYaLiansanzhangDianShuZu) > 0) {
                        filtedLiansanzhangDianShuZu.add(liansanzhangDianShuZu);
                    }
                } catch (CanNotCompareException e) {

                }
            }
            return filtedPaiXing;
        }
        return filtedPaiXing;
    }

    public BianXingWanFa getBx() {
        return bx;
    }

    public void setBx(BianXingWanFa bx) {
        this.bx = bx;
    }

    public DanGeDianShuZuComparator getDanGeDianShuZuComparator() {
        return danGeDianShuZuComparator;
    }

    public void setDanGeDianShuZuComparator(DanGeDianShuZuComparator danGeDianShuZuComparator) {
        this.danGeDianShuZuComparator = danGeDianShuZuComparator;
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
