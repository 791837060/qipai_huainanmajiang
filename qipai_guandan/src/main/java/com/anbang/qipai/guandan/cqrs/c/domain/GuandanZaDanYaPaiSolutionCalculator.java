package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.TonghuashunDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuCalculator;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.PaiXing;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;
import com.dml.shuangkou.pai.jiesuanpai.DawangDangPai;
import com.dml.shuangkou.pai.jiesuanpai.HongxinErDangPai;
import com.dml.shuangkou.pai.jiesuanpai.ShoupaiJiesuanPai;
import com.dml.shuangkou.pai.jiesuanpai.XiaowangDangPai;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.player.action.da.solution.ZaDanYaPaiSolutionCalculator;
import com.dml.shuangkou.wanfa.BianXingWanFa;

public class GuandanZaDanYaPaiSolutionCalculator implements ZaDanYaPaiSolutionCalculator {
    private BianXingWanFa bx;
    private ZhadanComparator zhadanComparator;

    @Override
    public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray) {
        int[] dianShuAmount = dianShuAmountArray.clone();
        Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();
        Set<DaPaiDianShuSolution> solutionSet = new HashSet<>();

//		int xiaowangCount = dianShuAmount[13];
//		int dawangCount = dianShuAmount[14];
        int hongxinErCount = dianShuAmount[15];
        // 王炸
        List<WangZhadanDianShuZu> wangZhadanDianShuZuList = DianShuZuCalculator.calculateWangZhadanDianShuZu(dianShuAmount);

        if (beiYaDianShuZu instanceof ZhadanDianShuZu) {
            for (WangZhadanDianShuZu wangZhadanDianShuZu : wangZhadanDianShuZuList) {
                ZhadanDianShuZu beiYaZhadanDianShuZu = (ZhadanDianShuZu) beiYaDianShuZu;
                if (zhadanComparator.compare(wangZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                    DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                    solution.setDianShuZu(wangZhadanDianShuZu);
                    List<DianShu> dachuDianShuList = new ArrayList<>();
                    DianShu[] lianXuDianShuArray = wangZhadanDianShuZu.getDianShuZu();
                    for (int count = 0; count < wangZhadanDianShuZu.getXiaowangCount(); count++) {
                        dachuDianShuList.add(lianXuDianShuArray[0]);
                    }
                    for (int count = 0; count < wangZhadanDianShuZu.getDawangCount(); count++) {
                        dachuDianShuList.add(lianXuDianShuArray[1]);
                    }
                    DianShu[] dachuDianShuArray = dachuDianShuList.toArray(new DianShu[dachuDianShuList.size()]);
                    solution.setDachuDianShuArray(dachuDianShuArray);
                    solution.calculateDianshuZuheIdx();
                    solutionSet.add(solution);
                }
            }
        } else {
            solutionSet.addAll(DianShuZuCalculator.generateAllWangZhadanDianShuZu(wangZhadanDianShuZuList));
        }
        int wangCount = 0;

//		if (BianXingWanFa.qianbian.equals(bx)) {// 千变
//			wangCount = xiaowangCount + dawangCount;
//			// 减去王牌的数量
//			dianShuAmount[13] = dianShuAmount[13] - xiaowangCount;
//			dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//		} else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
//			wangCount = dawangCount;
//			// 减去王牌的数量
//			if (xiaowangCount > 0 && xiaowangCount % 2 == 0) {
//				wangCount++;
//				dianShuAmount[13] = dianShuAmount[13] - 2;
//			}
//			dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//		} else if (BianXingWanFa.baibian.equals(bx)) {// 百变
//			wangCount = dawangCount;
//			// 减去王牌的数量
//			dianShuAmount[14] = dianShuAmount[14] - dawangCount;
//		} else {
//
//		}

        //减去红心2的数量

        wangCount = hongxinErCount;
        dianShuAmount[15] = dianShuAmount[15] - wangCount;

        if (wangCount > 0) {
            // 有王牌
            calculateDaPaiDianShuSolutionWithWangDang(wangCount, dianShuAmount, 0, 0, beiYaDianShuZu, solutionSet);
        } else {
            // 没有王牌
            calculateDaPaiDianShuSolutionWithoutWangDang(dianShuAmount, beiYaDianShuZu, solutionSet);
        }

        solutionSet.forEach((solution) -> {
            DaPaiDianShuSolution daPaiDianShuSolution = yaPaiSolutionCandidates.get(solution.getDianshuZuheIdx());
            if (daPaiDianShuSolution != null) {
                DianShuZu dianShuZu = daPaiDianShuSolution.getDianShuZu();
                // 有可能出现打出点数相同类型却不同的情况
                if (!solution.getDianShuZu().getClass().equals(dianShuZu.getClass())) {

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

    private List<DianShu> verifyDangFa(int wangCount, int[] dianshuCountArray) {
        Set<DianShu> kedangDianShuSet = new HashSet<>();
        for (int i = 0; i < 13; i++) {
            if (dianshuCountArray[i] > 0) {
                kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i));
                if (i - 1 >= 0) {
                    kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i - 1));
                }
                if (i + 1 < 13) {
                    kedangDianShuSet.add(DianShu.getDianShuByOrdinal(i + 1));
                }
            }
        }
        return new ArrayList<>(kedangDianShuSet);
    }

    private void calculateDaPaiDianShuSolutionWithWangDang(int wangCount, int[] dianshuCountArray, int xiaowangCount, int dawangCount, DianShuZu beiYaDianShuZu, Set<DaPaiDianShuSolution> solutionSet) {
        // 计算可以当的牌，提高性能
        List<DianShu> kedangDianShuList = verifyDangFa(dawangCount, dianshuCountArray);
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
//								hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//							} else {
//								hongxinErDangPaiArray[n] = new XiaowangDangPai(1, kedangDianShuList.get(shang));
//							}
//						} else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
//							if (n < dawangCount) {
//								hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//							} else {
//								hongxinErDangPaiArray[n] = new XiaowangDangPai(2, kedangDianShuList.get(shang));
//							}
//						} else if (BianXingWanFa.baibian.equals(bx)) {// 百变
//							hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
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
                    // 普通炸弹
                    paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
//					// 连续炸弹
//					paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));

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
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
//		// 连续炸弹
//		paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));

        paiXing = paiXingFilter(paiXing, beiYaDianShuZu);

        solutionSet.addAll(DianShuZuCalculator.calculateAllDaPaiDianShuSolutionWithoutWangDang(paiXing));
    }

    private PaiXing paiXingFilter(PaiXing paiXing, DianShuZu beiYaDianShuZu) {
        PaiXing filtedPaiXing = new PaiXing();
        if (beiYaDianShuZu instanceof ZhadanDianShuZu) {
            ZhadanDianShuZu beiYaZhadanDianShuZu = (ZhadanDianShuZu) beiYaDianShuZu;
            List<DanGeZhadanDianShuZu> filtedDanGeZhadanDianShuZuList = filtedPaiXing.getDanGeZhadanDianShuZuList();
            List<DanGeZhadanDianShuZu> zhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
            for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanDianShuZuList) {
                if (zhadanComparator.compare(danGeZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
                    filtedDanGeZhadanDianShuZuList.add(danGeZhadanDianShuZu);
                }
            }
//			List<LianXuZhadanDianShuZu> filtedLianXuZhadanDianShuZuList = filtedPaiXing.getLianXuZhadanDianShuZuList();
//			List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList = paiXing.getLianXuZhadanDianShuZuList();
//			for (LianXuZhadanDianShuZu lianXuZhadanDianShuZu : lianXuZhadanDianShuZuList) {
//				if (zhadanComparator.compare(lianXuZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
//					filtedLianXuZhadanDianShuZuList.add(lianXuZhadanDianShuZu);
//				}
//			}
        } else if (beiYaDianShuZu instanceof TonghuashunDianShuZu) {    //同花顺只能被5张以上的炸弹压
            List<DanGeZhadanDianShuZu> filtedDanGeZhadanDianShuZuList = filtedPaiXing.getDanGeZhadanDianShuZuList();
            List<DanGeZhadanDianShuZu> zhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
            for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanDianShuZuList) {
                if (danGeZhadanDianShuZu.getSize() > 5) {
                    filtedDanGeZhadanDianShuZuList.add(danGeZhadanDianShuZu);
                }
            }
        } else {
            filtedPaiXing.setDanGeZhadanDianShuZuList(paiXing.getDanGeZhadanDianShuZuList());
            filtedPaiXing.setLianXuZhadanDianShuZuList(paiXing.getLianXuZhadanDianShuZuList());
        }
        return filtedPaiXing;
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

}
