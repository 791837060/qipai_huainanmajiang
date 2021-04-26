package com.dml.paodekuai.pai.dianshuzu;

import java.util.ArrayList;
import java.util.List;

import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZuGenerator;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 通过点数数量数组产生打牌方案
 */
public class DianShuZuCalculator {

    /**
     * 计算单张点数组
     */
    public static List<DanzhangDianShuZu> calculateDanzhangDianShuZu(int[] dianshuCountArray) {
        // 单张
        List<DanzhangDianShuZu> danzhangDianShuZuList = DianShuZuGenerator
                .generateAllDanzhangDianShuZu(dianshuCountArray);
        return danzhangDianShuZuList;
    }

    /**
     * 根据单张点数组计算所有单张打法
     */
    public static List<DaPaiDianShuSolution> generateAllDanzhangDaPaiDianShuSolution(
            List<DanzhangDianShuZu> danzhangDianShuZuList) {
        List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
        for (DanzhangDianShuZu danzhangDianShuZu : danzhangDianShuZuList) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(danzhangDianShuZu);
            DianShu[] dachuDianShuArray = {danzhangDianShuZu.getDianShu()};
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        return solutionList;
    }

    /**
     * 计算对子点数组
     */
    public static List<DuiziDianShuZu> calculateDuiziDianShuZu(int[] dianshuCountArray) {
        // 对子
        List<DuiziDianShuZu> duiziDianShuZuList = DianShuZuGenerator.generateAllDuiziDianShuZu(dianshuCountArray);
        return duiziDianShuZuList;
    }

    /**
     * 计算顺子点数组
     */
    public static List<ShunziDianShuZu> calculateShunziDianShuZu(int[] dianshuCountArray) {
        // 顺子
        List<ShunziDianShuZu> shunziDianShuZuList = new ArrayList<>();
        for (int k = 5; k < 13; k++) {// 最小5连，小于13连
            shunziDianShuZuList.addAll(DianShuZuGenerator.generateAllShunziDianShuZu(dianshuCountArray, k));
        }
        return shunziDianShuZuList;
    }

    /**
     * 计算顺子点数组
     */
    public static List<ShunziDianShuZu> calculateShunziDianShuZu(int[] dianshuCountArray, boolean A2xiafang) {
        // 顺子
        List<ShunziDianShuZu> shunziDianShuZuList = new ArrayList<>();
        for (int k = 5; k <= 13; k++) {// 最小5连，小于13连
            shunziDianShuZuList.addAll(DianShuZuGenerator.generateAllShunziDianShuZu(dianshuCountArray, k, A2xiafang));
        }
        return shunziDianShuZuList;
    }

    /**
     * 计算连对点数组
     */
    public static List<LianduiDianShuZu> calculateLianduiDianShuZu(int[] dianshuCountArray) {
        // 连对
        List<LianduiDianShuZu> lianduiDianShuZuList = new ArrayList<>();
        for (int k = 2; k < 9; k++) {// 最小2连，最大8连
            lianduiDianShuZuList.addAll(DianShuZuGenerator.generateAllLianduiDianShuZu(dianshuCountArray, k));
        }
        return lianduiDianShuZuList;
    }

    /**
     * 计算连对点数组
     */
    public static List<LianduiDianShuZu> calculateLianduiDianShuZu(int[] dianshuCountArray, boolean A2xiafang) {
        // 连对
        List<LianduiDianShuZu> lianduiDianShuZuList = new ArrayList<>();
        for (int k = 2; k < 9; k++) {// 最小2连，最大8连
            lianduiDianShuZuList.addAll(DianShuZuGenerator.generateAllLianduiDianShuZu(dianshuCountArray, k, A2xiafang));
        }
        return lianduiDianShuZuList;
    }

    /**
     * 计算三张点数组
     */
    public static List<SanzhangDianShuZu> calculateSanzhangDianShuZu(int[] dianshuCountArray) {
        // 三张牌
        List<SanzhangDianShuZu> sanzhangDianShuZuList = DianShuZuGenerator
                .generateAllSanzhangDianShuZu(dianshuCountArray);
        return sanzhangDianShuZuList;
    }

    /**
     * 计算四带二点数组
     */
    public static List<SidaierDianShuZu> calculateSidaierDianShuZu(int[] dianshuCountArray) {
        // 三张牌
        List<SidaierDianShuZu> sidaierDianShuZus = PaodekuaiDianShuZuGenerator
                .generateAllSidaierDianShuZu(dianshuCountArray);
        return sidaierDianShuZus;
    }

    /**
     * 计算四带三点数组
     */
    public static List<SidaisanDianShuZu> calculateSidaisanDianShuZu(int[] dianshuCountArray) {
        // 三张牌
        List<SidaisanDianShuZu> sidaisanDianShuZus = PaodekuaiDianShuZuGenerator
                .generateAllSidaisanDianShuZu(dianshuCountArray);
        return sidaisanDianShuZus;
    }

    /**
     * 计算三带一点数组
     */
    public static List<SandaiyiDianShuZu> calculateSandaiyiDianShuZu(int[] dianshuCountArray, int shoupaiCount, boolean isSandaique) {
        boolean sandaique = (shoupaiCount < 4) && isSandaique;
        //三张牌
        List<SandaiyiDianShuZu> sandaiyiDianShuZus = PaodekuaiDianShuZuGenerator.generateAllSandaiyiDianShuZu(dianshuCountArray, sandaique);
        return sandaiyiDianShuZus;
    }

    /**
     * 计算三带二点数组
     */
    public static List<SandaierDianShuZu> calculateSandaierDianShuZu(int[] dianshuCountArray, int shoupaiCount, boolean isSandaique, boolean couldDaidan) {
        boolean sandaique = (shoupaiCount < 5) && isSandaique;
        //三张牌
        List<SandaierDianShuZu> sandaierDianShuZus = PaodekuaiDianShuZuGenerator.generateAllSandaierDianShuZu(dianshuCountArray, sandaique, couldDaidan);
        return sandaierDianShuZus;
    }

    /**
     * 计算飞机点数组
     */
    public static List<FeijiDianShuZu> calculateFeijiDianShuZu(int[] dianshuCountArray, int shoupaiCount, boolean isFeijique, boolean sanDaiLiangdan) {
        // 连三张
        List<FeijiDianShuZu> feijiDianShuZus = new ArrayList<>();
        for (int k = 2; k < 5; k++) {// 最小2连，最大5连
            boolean feijique = (k * 5 > shoupaiCount) && isFeijique;
            feijiDianShuZus.addAll(PaodekuaiDianShuZuGenerator.generateAllFeijiDianShuZu(dianshuCountArray, k, feijique, sanDaiLiangdan));
        }
        return feijiDianShuZus;
    }

    /**
     * 计算普通炸弹点数组
     */
    public static List<DanGeZhadanDianShuZu> calculateDanGeZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = DianShuZuGenerator.generateAllZhadanDianShuZu(dianshuCountArray);
        return danGeZhadanDianShuZuList;
    }

    /**
     * 计算带一张牌的普通炸弹点数组
     */
    public static List<DaiPaiZhaDanDianShuZu> calculateDaiPaiZhaDanDianShuZu(int[] dianshuCountArray) {
        // 三张牌
        List<DaiPaiZhaDanDianShuZu> daiPaiZhaDanDianShuZus = PaodekuaiDianShuZuGenerator
                .generateAllDaiPaiZhaDanDianShuZu(dianshuCountArray);
        return daiPaiZhaDanDianShuZus;
    }

    /**
     * 计算aaa炸弹点数组
     */
    public static List<ABoomDianShuZu> calculateABoomZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        List<ABoomDianShuZu> aBoomDianShuZus = PaodekuaiDianShuZuGenerator.generateAllABoomDianShuZu(dianshuCountArray);
        return aBoomDianShuZus;
    }

    /**
     * 计算AAA+1炸弹点数组
     */
    public static List<SanAJiaYiBoomDianShuZu> calculate3AJia1BoomZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        List<SanAJiaYiBoomDianShuZu> aBoomDianShuZus = PaodekuaiDianShuZuGenerator.generateAll3AJia1BoomDianShuZu(dianshuCountArray);
        return aBoomDianShuZus;
    }

    /**
     * 计算3个3炸弹点数组
     */
    public static List<SanBoomDianShuZu> calculateSanBoomZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        return PaodekuaiDianShuZuGenerator.generateSanBoomDianShuZu(dianshuCountArray);
    }

    /**
     * 计算333+1炸弹点数组
     */
    public static List<SanSanJiaYiBoomDianShuZu> calculate33Jia1BoomZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        return PaodekuaiDianShuZuGenerator.generate33Jia1BoomDianShuZu(dianshuCountArray);
    }

    /**
     * 计算KKK炸弹点数组
     */
    public static List<ABoomDianShuZu> calculateKBoomZhadanDianShuZu(int[] dianshuCountArray) {
        // 炸弹
        List<ABoomDianShuZu> aBoomDianShuZus = PaodekuaiDianShuZuGenerator.generateAllKBoomDianShuZu(dianshuCountArray);
        return aBoomDianShuZus;
    }


    /**
     * 没有王的情况下生成打牌方案
     */
    public static List<DaPaiDianShuSolution> calculateAllDaPaiDianShuSolutionWithoutWangDang(PaiXing paiXing, boolean couldSidaiyiBoom) {
        List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
        // 对子
        for (DuiziDianShuZu duiziDianShuZu : paiXing.getDuiziDianShuZuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(duiziDianShuZu);
            DianShu[] dachuDianShuArray = {duiziDianShuZu.getDianShu(), duiziDianShuZu.getDianShu()};
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 连对
        for (LianduiDianShuZu lianduiDianShuZu : paiXing.getLianduiDianShuZuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(lianduiDianShuZu);
            DianShu[] lianXuDianShuArray = lianduiDianShuZu.getLianXuDianShuArray();
            DianShu[] dachuDianShuArray = new DianShu[2 * lianXuDianShuArray.length];
            for (int i = 0; i < lianXuDianShuArray.length; i++) {//转换为实际牌数数组
                dachuDianShuArray[i * 2] = lianXuDianShuArray[i];
                dachuDianShuArray[i * 2 + 1] = lianXuDianShuArray[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 三带一
        for (SandaiyiDianShuZu sandaiyiDianShuZu : paiXing.getSandaiyiDianShuZuArrayList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(sandaiyiDianShuZu);
            int dachuDianShuLength = 4;
            DianShu[] dachuDianShuArray = new DianShu[dachuDianShuLength];
            DianShu[] daipaiDianshuzu = sandaiyiDianShuZu.getDaipaiDianShuArray();
            dachuDianShuArray[0] = sandaiyiDianShuZu.getSanzhangDianShuArray()[0];
            dachuDianShuArray[1] = sandaiyiDianShuZu.getSanzhangDianShuArray()[0];
            dachuDianShuArray[2] = sandaiyiDianShuZu.getSanzhangDianShuArray()[0];
            for (int i = 0; i < daipaiDianshuzu.length; i++) {
                dachuDianShuArray[3 + i] = daipaiDianshuzu[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 三带二
        for (SandaierDianShuZu sandaierDianShuZu : paiXing.getSandaierDianShuZuArrayList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(sandaierDianShuZu);
            int dachuDianShuLength = 3 + sandaierDianShuZu.getDaipaiDianShuArray().length;
            DianShu[] dachuDianShuArray = new DianShu[dachuDianShuLength];
            DianShu[] daipaiDianshuzu = sandaierDianShuZu.getDaipaiDianShuArray();
            dachuDianShuArray[0] = sandaierDianShuZu.getSanzhangDianShuArray()[0];
            dachuDianShuArray[1] = sandaierDianShuZu.getSanzhangDianShuArray()[0];
            dachuDianShuArray[2] = sandaierDianShuZu.getSanzhangDianShuArray()[0];
            for (int i = 0; i < daipaiDianshuzu.length; i++) {
                dachuDianShuArray[3 + i] = daipaiDianshuzu[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 三张
        for (SanzhangDianShuZu dianShuZu : paiXing.getSanzhangDianShuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(dianShuZu);
            DianShu[] dachuDianShuArray = {dianShuZu.getDianShu(), dianShuZu.getDianShu(), dianShuZu.getDianShu()};
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 四带二
        for (SidaierDianShuZu dianShuZu : paiXing.getSidaierDianShuZulist()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(dianShuZu);
            int dachuDianShuLength = 4 + dianShuZu.getDaipaiDianShuArray().length;
            DianShu[] dachuDianShuArray = new DianShu[dachuDianShuLength];
            DianShu[] daipaiDianshuzu = dianShuZu.getDaipaiDianShuArray();
            dachuDianShuArray[0] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[1] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[2] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[3] = dianShuZu.getDanpaiDianShu();
            for (int i = 0; i < daipaiDianshuzu.length; i++) {
                dachuDianShuArray[4 + i] = daipaiDianshuzu[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 四带三
        for (SidaisanDianShuZu dianShuZu : paiXing.getSidaisanDianShuZuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(dianShuZu);
            int dachuDianShuLength = 4 + dianShuZu.getDaipaiDianShuArray().length;
            DianShu[] dachuDianShuArray = new DianShu[dachuDianShuLength];
            DianShu[] daipaiDianshuzu = dianShuZu.getDaipaiDianShuArray();
            dachuDianShuArray[0] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[1] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[2] = dianShuZu.getDanpaiDianShu();
            dachuDianShuArray[3] = dianShuZu.getDanpaiDianShu();
            for (int i = 0; i < daipaiDianshuzu.length; i++) {
                dachuDianShuArray[4 + i] = daipaiDianshuzu[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 飞机
        for (FeijiDianShuZu feijiDianShuZu : paiXing.getFeijiDianShuZuArrayList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(feijiDianShuZu);

            DianShu[] sanzhangArray = feijiDianShuZu.getSanzhangDianShuArray();
            DianShu[] daipaiArray = feijiDianShuZu.getDaipaiDianShuArray();
            int dachuSanzhangLength = 3 * sanzhangArray.length;
            int dachuDianshuLength = dachuSanzhangLength + daipaiArray.length;
            DianShu[] dachuDianShuArray = new DianShu[dachuDianshuLength];

            for (int i = 0; i < sanzhangArray.length; i++) {
                dachuDianShuArray[i * 3] = sanzhangArray[i];
                dachuDianShuArray[i * 3 + 1] = sanzhangArray[i];
                dachuDianShuArray[i * 3 + 2] = sanzhangArray[i];
            }
            for (int j = 0; j < daipaiArray.length; j++) {
                dachuDianShuArray[dachuSanzhangLength + j] = daipaiArray[j];
            }

            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 顺子
        for (ShunziDianShuZu shunziDianShuZu : paiXing.getShunziDianShuZuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(shunziDianShuZu);
            DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
            DianShu[] dachuDianShuArray = new DianShu[lianXuDianShuArray.length];
            for (int i = 0; i < lianXuDianShuArray.length; i++) {
                dachuDianShuArray[i] = lianXuDianShuArray[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        // 炸弹
        for (DanGeZhadanDianShuZu zhadanDianShuZu : paiXing.getDanGeZhadanDianShuZuList()) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(zhadanDianShuZu);
            DianShu[] dachuDianShuArray = new DianShu[zhadanDianShuZu.getSize()];
            for (int i = 0; i < zhadanDianShuZu.getSize(); i++) {
                dachuDianShuArray[i] = zhadanDianShuZu.getDianShu();
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            solutionList.add(solution);
        }
        if (couldSidaiyiBoom) {
            // 带牌炸弹（四带一）
            for (DaiPaiZhaDanDianShuZu daiPaiZhaDanDianShuZu : paiXing.getDaipaiZhaDanDianShuZuList()) {
                DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
                solution.setDianShuZu(daiPaiZhaDanDianShuZu);
                DianShu zhadanDian = daiPaiZhaDanDianShuZu.getZhadanDian();
                DianShu daipaiDian = daiPaiZhaDanDianShuZu.getDaipaiDian();
                DianShu[] dachuDianShuArray = {zhadanDian, zhadanDian, zhadanDian, zhadanDian, daipaiDian};
                solution.setDachuDianShuArray(dachuDianShuArray);
                solution.calculateDianshuZuheIdx();
                solutionList.add(solution);
            }
        }

        return solutionList;
    }

}
