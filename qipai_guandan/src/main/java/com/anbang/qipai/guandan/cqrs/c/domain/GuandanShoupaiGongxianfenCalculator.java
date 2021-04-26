package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pai.dianshuzu.DianShuZuCalculator;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.PaiXing;
import com.dml.shuangkou.pai.jiesuanpai.DawangDangPai;
import com.dml.shuangkou.pai.jiesuanpai.HongxinErDangPai;
import com.dml.shuangkou.pai.jiesuanpai.ShoupaiJiesuanPai;
import com.dml.shuangkou.pai.jiesuanpai.XiaowangDangPai;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.wanfa.BianXingWanFa;

/**
 * 玩家贡献分计算器
 */
public class GuandanShoupaiGongxianfenCalculator {

    /**
     * 计算玩家总贡献分
     */
    public static GuandanGongxianFen calculateTotalGongxianfenWithShouPaiForPlayer(String playerId, Ju ju, BianXingWanFa bx, int[] xianshuArray, boolean gxjb) {
        GuandanGongxianFen bestGongxianfen = new GuandanGongxianFen(xianshuArray);
        bestGongxianfen.calculateXianshu(gxjb);
        Pan currentPan = ju.getCurrentPan();
        ShuangkouPlayer player = currentPan.findPlayer(playerId);
        Map<Integer, PukePai> allShoupai = player.getAllShoupai();
        int[] dianshuCountArray = new int[16];
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
        }
        int xiaowangCount = dianshuCountArray[13];
        int dawangCount = dianshuCountArray[14];
        if (xiaowangCount + dawangCount == 4) {// 有天王炸
            int[] dianshuAmountArray = dianshuCountArray.clone();
            dianshuAmountArray[13] = 0;
            dianshuAmountArray[14] = 0;
            int[] xianshuCount1 = xianshuArray.clone();
            xianshuCount1[2] += 1;
            GuandanGongxianFen gongxianfen = calculatePaiXingWithWangDang(dianshuAmountArray.clone(), 1, 0, 1,
                    bx, xianshuCount1, gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                bestGongxianfen = gongxianfen;
            }
            int[] xianshuCount2 = xianshuArray.clone();
            xianshuCount2[3] += 1;
            GuandanGongxianFen gongxianfen1 = calculatePaiXingWithoutWangDang(dianshuAmountArray, bx,
                    xianshuCount2, gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen1.getValue()) {
                bestGongxianfen = gongxianfen1;
            }
        }
        if (xiaowangCount + dawangCount == 3) {// 有三王炸
            int[] dianshuAmountArray = dianshuCountArray.clone();
            dianshuAmountArray[13] = 0;
            dianshuAmountArray[14] = 0;
            int[] xianshuCount3 = xianshuArray.clone();
            xianshuCount3[2] += 1;
            GuandanGongxianFen gongxianfen = calculatePaiXingWithoutWangDang(dianshuAmountArray, bx,
                    xianshuCount3, gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                bestGongxianfen = gongxianfen;
            }
        }
        int wangCount = 0;
        if (BianXingWanFa.qianbian.equals(bx)) {// 千变
            wangCount = xiaowangCount + dawangCount;
            // 减去王牌的数量
            dianshuCountArray[13] = dianshuCountArray[13] - xiaowangCount;
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
            wangCount = dawangCount;
            // 减去王牌的数量
            if (xiaowangCount > 0 && xiaowangCount % 2 == 0) {
                wangCount++;
                dianshuCountArray[13] = dianshuCountArray[13] - 2;
            }
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else if (BianXingWanFa.baibian.equals(bx)) {// 百变
            wangCount = dawangCount;
            // 减去王牌的数量
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else {

        }
        if (wangCount > 0) {
            // 有王牌
            GuandanGongxianFen gongxianfen = calculatePaiXingWithWangDang(dianshuCountArray, wangCount,
                    xiaowangCount, dawangCount, bx, xianshuArray, gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                bestGongxianfen = gongxianfen;
            }
        } else {
            // 没有王牌
            GuandanGongxianFen gongxianfen = calculatePaiXingWithoutWangDang(dianshuCountArray, bx,
                    xianshuArray, gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                bestGongxianfen = gongxianfen;
            }
        }
        return bestGongxianfen;
    }

    private static GuandanGongxianFen calculatePaiXingWithoutWangDang(int[] dianshuCountArray, BianXingWanFa bx, int[] xianshuArray, boolean gxjb) {
        PaiXing paiXing = new PaiXing();
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
        // 连续炸弹
        paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
        return calculateBestGongxianfen(dianshuCountArray.clone(), bx, paiXing, xianshuArray, gxjb);
    }

    private static List<DianShu> verifyDangFa(int wangCount, int[] dianshuCountArray) {
        List<DianShu> kedangDianShuList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (dianshuCountArray[i] > 0 && dianshuCountArray[i] + wangCount >= 4) {
                kedangDianShuList.add(DianShu.getDianShuByOrdinal(i));
            }
        }
        return kedangDianShuList;
    }

    private static GuandanGongxianFen calculatePaiXingWithWangDang(int[] dianshuCountArray, int wangCount,
                                                                   int xiaowangCount, int dawangCount, BianXingWanFa bx, int[] xianshuArray, boolean gxjb) {
        GuandanGongxianFen bestGongxianfen = new GuandanGongxianFen(xianshuArray);
        bestGongxianfen.calculateXianshu(gxjb);
        // 计算王可以当哪些牌，提高性能
        List<DianShu> kedangDianShuList = verifyDangFa(wangCount, dianshuCountArray);
        // 循环王的各种当法
        if (kedangDianShuList.isEmpty()) {
            return bestGongxianfen;
        }
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

//					if (BianXingWanFa.qianbian.equals(bx)) {// 千变
//						if (n < dawangCount) {
//							hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//						} else {
//							hongxinErDangPaiArray[n] = new XiaowangDangPai(1, kedangDianShuList.get(shang));
//						}
//					} else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
//						if (n < dawangCount) {
//							hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//						} else {
//							hongxinErDangPaiArray[n] = new XiaowangDangPai(2, kedangDianShuList.get(shang));
//						}
//					} else if (BianXingWanFa.baibian.equals(bx)) {// 百变
//						hongxinErDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//					} else {
//
//					}

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
                paiXing.setDanGeZhadanDianShuZuList(
                        DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
                // 连续炸弹
                paiXing.setLianXuZhadanDianShuZuList(
                        DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
                GuandanGongxianFen gongxianfen = calculateBestGongxianfen(dianshuCountArray.clone(), bx,
                        paiXing, xianshuArray.clone(), gxjb);
                if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                    bestGongxianfen = gongxianfen;
                }
                // 减去当牌的数量
                for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                    dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]--;
                }
            }
        }
        return bestGongxianfen;
    }

    /**
     * 计算炸弹线数，没有王炸
     */
    private static int calculateXianShu(ZhadanDianShuZu zhadan) {
        if (zhadan instanceof DanGeZhadanDianShuZu) {
            DanGeZhadanDianShuZu danGeZhadan = (DanGeZhadanDianShuZu) zhadan;
            return danGeZhadan.getSize();
        } else if (zhadan instanceof LianXuZhadanDianShuZu) {
            LianXuZhadanDianShuZu lianXuZhadan = (LianXuZhadanDianShuZu) zhadan;
            return lianXuZhadan.getXianShu();
        } else {
            return 0;
        }
    }

    /**
     * 根据牌型和牌数计算最佳贡献分
     */
    private static GuandanGongxianFen calculateBestGongxianfen(int[] dianshuCountArray, BianXingWanFa bx,
                                                               PaiXing paixing, int[] xianshuArray, boolean gxjb) {
        List<ZhadanDianShuZu> zhadanDianShuZuList = new ArrayList<>();
        List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = paixing.getDanGeZhadanDianShuZuList();
        zhadanDianShuZuList.addAll(danGeZhadanDianShuZuList);
        List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList = paixing.getLianXuZhadanDianShuZuList();
        zhadanDianShuZuList.addAll(lianXuZhadanDianShuZuList);

        GuandanGongxianFen bestGongxianfen = new GuandanGongxianFen();
        bestGongxianfen.calculateXianshu(gxjb);
        calculateGongxianfen(dianshuCountArray, bx, xianshuArray, zhadanDianShuZuList, bestGongxianfen, gxjb);

        return bestGongxianfen;
    }

    private static void calculateGongxianfen(int[] dianshuCountArray, BianXingWanFa bx, int[] xianshuArray,
                                             List<ZhadanDianShuZu> zhadanDianShuZuList, GuandanGongxianFen bestGongxianfen, boolean gxjb) {
        if (zhadanDianShuZuList.isEmpty()) {
            GuandanGongxianFen gongxianfen = new GuandanGongxianFen(xianshuArray);
            gongxianfen.calculateXianshu(gxjb);
            if (bestGongxianfen == null || bestGongxianfen.getValue() < gongxianfen.getValue()) {
                bestGongxianfen.setSixian(gongxianfen.getSixian());
                bestGongxianfen.setWuxian(gongxianfen.getWuxian());
                bestGongxianfen.setLiuxian(gongxianfen.getLiuxian());
                bestGongxianfen.setQixian(gongxianfen.getQixian());
                bestGongxianfen.setBaxian(gongxianfen.getBaxian());
                bestGongxianfen.setJiuxian(gongxianfen.getJiuxian());
                bestGongxianfen.setShixian(gongxianfen.getShixian());
                bestGongxianfen.setShiyixian(gongxianfen.getShiyixian());
                bestGongxianfen.setShierxian(gongxianfen.getShierxian());
                bestGongxianfen.setValue(gongxianfen.getValue());
                bestGongxianfen.setTotalscore(gongxianfen.getTotalscore());
            }
        } else {
            for (int i = 0; i < zhadanDianShuZuList.size(); i++) {
                int[] xianshuArray1 = xianshuArray.clone();
                ZhadanDianShuZu zhadanDianShuZu = zhadanDianShuZuList.get(i);
                int xianshu = calculateXianShu(zhadanDianShuZu);
                xianshuArray1[xianshu - 4]++;
                int[] dianshuCountArray1 = removeZhadan(dianshuCountArray.clone(), zhadanDianShuZu);
                List<ZhadanDianShuZu> zhadanDianShuZuList1 = calculateTotalGongxianfenForDianshuCountArray(
                        dianshuCountArray1, bx);
                calculateGongxianfen(dianshuCountArray1, bx, xianshuArray1, zhadanDianShuZuList1, bestGongxianfen,
                        gxjb);
            }
        }
    }

    private static List<ZhadanDianShuZu> calculateTotalGongxianfenForDianshuCountArray(int[] dianshuCountArray,
                                                                                       BianXingWanFa bx) {
        int xiaowangCount = dianshuCountArray[13];
        int dawangCount = dianshuCountArray[14];
        int wangCount = 0;
        if (BianXingWanFa.qianbian.equals(bx)) {// 千变
            wangCount = xiaowangCount + dawangCount;
            // 减去王牌的数量
            dianshuCountArray[13] = dianshuCountArray[13] - xiaowangCount;
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
            wangCount = dawangCount;
            // 减去王牌的数量
            if (xiaowangCount > 0 && xiaowangCount % 2 == 0) {
                wangCount++;
                dianshuCountArray[13] = dianshuCountArray[13] - 2;
            }
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else if (BianXingWanFa.baibian.equals(bx)) {// 百变
            wangCount = dawangCount;
            // 减去王牌的数量
            dianshuCountArray[14] = dianshuCountArray[14] - dawangCount;
        } else {

        }
        if (wangCount > 0) {
            // 有王牌
            return calculateZhadanDianShuZuWithWangDang(wangCount, dianshuCountArray, xiaowangCount, dawangCount, bx);
        } else {
            // 没有王牌
            return calculateListZhadanDianShuZuWithoutWangDang(dianshuCountArray);
        }
    }

    private static List<ZhadanDianShuZu> calculateListZhadanDianShuZuWithoutWangDang(int[] dianshuCountArray) {
        PaiXing paiXing = new PaiXing();
        // 普通炸弹
        paiXing.setDanGeZhadanDianShuZuList(DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
        // 连续炸弹
        paiXing.setLianXuZhadanDianShuZuList(DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
        List<ZhadanDianShuZu> zhadanDianShuZuList = new ArrayList<>();
        List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
        zhadanDianShuZuList.addAll(danGeZhadanDianShuZuList);
        List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList = paiXing.getLianXuZhadanDianShuZuList();
        zhadanDianShuZuList.addAll(lianXuZhadanDianShuZuList);
        return zhadanDianShuZuList;
    }

    private static List<ZhadanDianShuZu> calculateZhadanDianShuZuWithWangDang(int wangCount, int[] dianshuCountArray, int xiaowangCount, int dawangCount, BianXingWanFa bx) {
        List<ZhadanDianShuZu> zhadanDianShuZuList = new ArrayList<>();
        // 计算王可以当哪些牌，提高性能
        List<DianShu> kedangDianShuList = verifyDangFa(dawangCount, dianshuCountArray);
        // 循环王的各种当法
        if (kedangDianShuList.isEmpty()) {
            return zhadanDianShuZuList;
        }
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

//					if (BianXingWanFa.qianbian.equals(bx)) {// 千变
//						if (n < dawangCount) {
//							wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//						} else {
//							wangDangPaiArray[n] = new XiaowangDangPai(1, kedangDianShuList.get(shang));
//						}
//					} else if (BianXingWanFa.banqianbian.equals(bx)) {// 半千变;
//						if (n < dawangCount) {
//							wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//						} else {
//							wangDangPaiArray[n] = new XiaowangDangPai(2, kedangDianShuList.get(shang));
//						}
//					} else if (BianXingWanFa.baibian.equals(bx)) {// 百变
//						wangDangPaiArray[n] = new DawangDangPai(kedangDianShuList.get(shang));
//					} else {
//
//					}

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
                paiXing.setDanGeZhadanDianShuZuList(
                        DianShuZuCalculator.calculateDanGeZhadanDianShuZu(dianshuCountArray));
                // 连续炸弹
                paiXing.setLianXuZhadanDianShuZuList(
                        DianShuZuCalculator.calculateLianXuZhadanDianShuZu(dianshuCountArray));
                List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = paiXing.getDanGeZhadanDianShuZuList();
                zhadanDianShuZuList.addAll(danGeZhadanDianShuZuList);
                List<LianXuZhadanDianShuZu> lianXuZhadanDianShuZuList = paiXing.getLianXuZhadanDianShuZuList();
                zhadanDianShuZuList.addAll(lianXuZhadanDianShuZuList);
                // 减去当牌的数量
                for (ShoupaiJiesuanPai jiesuanPai : hongxinErDangPaiArray) {
                    dianshuCountArray[jiesuanPai.getDangPaiType().ordinal()]--;
                }
            }
        }
        return zhadanDianShuZuList;
    }

    /**
     * 从手牌中移除打出的炸弹,没有王炸
     */
    private static int[] removeZhadan(int[] dianshuCountArray, ZhadanDianShuZu zhadanDianShuZu) {
        if (zhadanDianShuZu instanceof DanGeZhadanDianShuZu) {
            DanGeZhadanDianShuZu danGeZhadanDianShuZu = (DanGeZhadanDianShuZu) zhadanDianShuZu;
            dianshuCountArray[danGeZhadanDianShuZu.getDianShu().ordinal()] -= danGeZhadanDianShuZu.getSize();
        }
        if (zhadanDianShuZu instanceof LianXuZhadanDianShuZu) {
            LianXuZhadanDianShuZu lianXuZhadanDianShuZu = (LianXuZhadanDianShuZu) zhadanDianShuZu;
            DianShu[] lianXuDianShuArray = lianXuZhadanDianShuZu.getLianXuDianShuArray();
            int[] lianXuDianShuSizeArray = lianXuZhadanDianShuZu.getLianXuDianShuSizeArray();
            for (int i = 0; i < lianXuDianShuArray.length; i++) {
                dianshuCountArray[lianXuDianShuArray[i].ordinal()] -= lianXuDianShuSizeArray[i];
            }
        }
        return dianshuCountArray;
    }

}
