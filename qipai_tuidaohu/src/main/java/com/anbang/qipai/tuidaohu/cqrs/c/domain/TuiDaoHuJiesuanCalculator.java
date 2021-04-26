package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.mo.GanghouBupai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.shoupai.*;
import com.dml.majiang.player.shoupai.gouxing.GouXing;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TuiDaoHuJiesuanCalculator {

    /**
     * 自摸胡
     *
     * @param couldTianhu  是否可以天胡
     * @param gouXingPanHu 胡牌牌型
     * @param player       麻将玩家
     * @param moAction     摸牌动作
     * @return
     */
    public static TuiDaoHuHu calculateBestZimoHu(boolean couldTianhu, GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangMoAction moAction, OptionalPlay optionalPlay) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();//玩家手牌
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        if (!player.gangmoGuipai()) {
            shoupaiCalculator.addPai(player.getGangmoShoupai());
        }
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, player.getGangmoShoupai()); //计算胡牌
        if (!player.gangmoGuipai()) {
            shoupaiCalculator.removePai(player.getGangmoShoupai());
        }
        if (!huPaiShoupaiPaiXingList.isEmpty()) { //有胡牌型
            //要选出分数最高的牌型
            //先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, null);
            TuiDaoHuHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                if (!isValid(shoupaiPaiXing, true)) {
                    continue;
                }
                //计算胡分
                TuiDaoHuHushu hufen = calculateHufen(true, true, moAction.getReason().getName().equals(GanghouBupai.name), false, couldTianhu,
                        false, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null || bestHuShoupaiPaiXing == null) {
                return null;
            }
            return new TuiDaoHuHu(bestHuShoupaiPaiXing, bestHuFen);
        } else { //不成胡
            return null;
        }
    }

    /**
     * 抢杠胡
     *
     * @param gangPai      要杠的牌
     * @param gouXingPanHu 可胡牌牌型
     * @param player       抢杠胡玩家
     * @return
     */
    public static TuiDaoHuHu calculateBestQianggangHu(MajiangPai gangPai, GouXingPanHu gouXingPanHu, MajiangPlayer player, OptionalPlay optionalPlay) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        shoupaiCalculator.addPai(gangPai);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, gangPai);
        shoupaiCalculator.removePai(gangPai);
        if (!huPaiShoupaiPaiXingList.isEmpty()) {// 有胡牌型
            // 要选出分数最高的牌型
            // 先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, gangPai);
            TuiDaoHuHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                if (!isValid(shoupaiPaiXing, false)) {
                    continue;
                }
                TuiDaoHuHushu hufen = calculateHufen(true, false, false, true, false, false, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null || bestHuShoupaiPaiXing == null) {
                return null;
            }
            return new TuiDaoHuHu(bestHuShoupaiPaiXing, bestHuFen);
        } else {// 不成胡
            return null;
        }
    }

    /**
     * 点炮胡
     *
     * @param couldDihu    是否可以地胡
     * @param gouXingPanHu 可胡牌型
     * @param player       胡牌玩家
     * @param hupai        胡的牌
     * @return
     */
    public static TuiDaoHuHu calculateBestDianpaoHu(boolean couldDihu, GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangPai hupai, OptionalPlay optionalPlay) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, hupai);
        if (!huPaiShoupaiPaiXingList.isEmpty()) {// 有胡牌型
            // 要选出分数最高的牌型
            // 先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, hupai);
            TuiDaoHuHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                if (!isValid(shoupaiPaiXing, false)) {
                    continue;
                }
                TuiDaoHuHushu hufen = calculateHufen(true, false, false, false, false, couldDihu, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null || bestHuShoupaiPaiXing == null) {
                return null;
            }
            return new TuiDaoHuHu(bestHuShoupaiPaiXing, bestHuFen);
        } else {// 不成胡
            return null;
        }
    }

    /**
     * 没有胡的人的分
     *
     * @param player 麻将玩家
     * @return
     */
    public static TuiDaoHuHushu calculateBestHuFenForBuhuPlayer(MajiangPlayer player, OptionalPlay optionalPlay) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        List<ShoupaiPaiXing> shoupaiPaiXingList = calculateBuhuShoupaiPaiXingList(guipaiList, shoupaiCalculator);
        // 要选出分数最高的牌型
        // 先计算和手牌型无关的参数
        ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, null);
        TuiDaoHuHushu bestHuFen = null;
        for (ShoupaiPaiXing shoupaiPaiXing : shoupaiPaiXingList) {
            TuiDaoHuHushu hufen = calculateHufen(false, false, false, false, false, false, shoupaixingWuguanJiesuancanshu,
                    shoupaiPaiXing, optionalPlay);
            if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                bestHuFen = hufen;
            }
        }
        return bestHuFen;
    }

    /**
     * 计算胡牌类型
     * <p>
     * 自摸胡
     * 抢杠胡
     * 点炮胡
     *
     * @param hu                             是否胡
     * @param zimoHu                         自摸胡
     * @param gangkaiHu                      杠牌胡
     * @param qianggangHu                    抢杠胡
     * @param couldTianhu                    可以天胡
     * @param couldDihu                      可以地胡
     * @param shoupaixingWuguanJiesuancanshu 手牌型无关结算参数
     * @param shoupaiPaiXing                 手牌牌型
     * @return
     */
    private static TuiDaoHuHushu calculateHufen(boolean hu, boolean zimoHu, boolean gangkaiHu, boolean qianggangHu, boolean couldTianhu, boolean couldDihu,
                                                ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu, ShoupaiPaiXing shoupaiPaiXing, OptionalPlay optionalPlay) {
        TuiDaoHuHushu hufen = new TuiDaoHuHushu();
        if (hu) {
            boolean qingyise = shoupaixingWuguanJiesuancanshu.isQingyise(); //清一色
            boolean qixiaodui = shoupaiPaiXing.getDuiziList().size() == 7;  //七小对
            boolean haohuaqixiaodui = shoupaiPaiXing.getDuiziList().size() == 5 && shoupaiPaiXing.getGangziList().size() == 1; //豪华七小对
//            boolean yitiaolong = isYitiaolong(shoupaiPaiXing);//一条龙
            boolean pengpenghu = shoupaiPaiXing.getDuiziList().size() == 1 && shoupaixingWuguanJiesuancanshu.getChichupaiZuCount() == 0 && shoupaiPaiXing.countShunzi() == 0;//碰碰胡

            hufen.setHu(hu);                                //胡
            if (zimoHu) {                                   //自摸胡
                hufen.setZimoHu(zimoHu);
            }
            if (couldTianhu) {                              //天胡
                hufen.setTianhu(couldTianhu);
            }
            if (couldDihu) {                                //地胡
                hufen.setDihu(couldDihu);
            }
            if (gangkaiHu) {                                //杠上开花
                hufen.setGangshangkaihua(gangkaiHu);
            }
            if (qianggangHu) {                              //抢杠胡
                hufen.setQiangganghu(qianggangHu);
            }
            if (qingyise) {                                 //清一色
                hufen.setQingyise(qingyise);
            }
            if (qixiaodui) {                                //七小对
                hufen.setQixiaodui(qixiaodui);
            }
//            if (yitiaolong) {                               //一条龙
//                hufen.setYitiaolong(yitiaolong);
//            }
            if (pengpenghu) {                               //碰碰胡
                hufen.setPengpenghu(pengpenghu);
            }
            if (haohuaqixiaodui) {                          //豪华七小对
                hufen.setHaohuaqixiaodui(haohuaqixiaodui);
            }

        }
        hufen.calculate();
        if (!optionalPlay.isKeyidahu() && hu) {
            hufen.setValue(2);
        }

        return hufen;
    }

    /**
     * 其实点炮,抢杠胡,也包含自摸的意思，也调用这个
     *
     * @param guipaiList        鬼牌集合
     * @param shoupaiCalculator 玩家手牌
     * @param player            麻将玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             胡牌的牌
     * @return
     */
    private static List<ShoupaiPaiXing> calculateZimoHuPaiShoupaiPaiXingList(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        if (!guipaiList.isEmpty()) { //有鬼牌
            return calculateHuPaiShoupaiPaiXingListWithCaishen(guipaiList, shoupaiCalculator, player, gouXingPanHu, huPai);
        } else { //没鬼牌
            return calculateHuPaiShoupaiPaiXingListWithoutCaishen(shoupaiCalculator, player, gouXingPanHu, huPai);
        }
    }

    private static List<ShoupaiPaiXing> calculateBuhuShoupaiPaiXingList(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator) {
        if (!guipaiList.isEmpty()) {// 有财神
            return calculateBuhuShoupaiPaiXingListWithCaishen(guipaiList, shoupaiCalculator);
        } else {// 没财神
            return calculateBuhuShoupaiPaiXingListWithoutCaishen(shoupaiCalculator);
        }
    }

    /**
     * 没有鬼牌 计算胡牌牌型
     *
     * @param shoupaiCalculator 玩家手牌
     * @param player            麻将玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             胡牌的牌
     * @return
     */
    private static List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithoutCaishen(ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        // 计算构型
        List<GouXing> gouXingList = shoupaiCalculator.calculateAllGouXing();
        int chichuShunziCount = player.countChichupaiZu();//吃牌牌组
        int pengchuKeziCount = player.countPengchupaiZu();//碰牌牌组
        int gangchuGangziCount = player.countGangchupaiZu();//杠牌牌组
        for (GouXing gouXing : gouXingList) {
            boolean hu = gouXingPanHu.panHu(gouXing.getGouXingCode(), chichuShunziCount, pengchuKeziCount, gangchuGangziCount);
            if (hu) {
                // 计算牌型
                List<PaiXing> paiXingList = shoupaiCalculator.calculateAllPaiXingFromGouXing(gouXing);
                for (PaiXing paiXing : paiXingList) {
                    ShoupaiPaiXing shoupaiPaiXing = paiXing.generateAllBenPaiShoupaiPaiXing();
                    // 对ShoupaiPaiXing还要变换最后弄进的牌
                    List<ShoupaiPaiXing> shoupaiPaiXingListWithDifftentLastActionPaiInZu = shoupaiPaiXing.differentiateShoupaiPaiXingByLastActionPai(huPai);
                    huPaiShoupaiPaiXingList.addAll(shoupaiPaiXingListWithDifftentLastActionPaiInZu);
                }
            }
        }
        return huPaiShoupaiPaiXingList;
    }

    /**
     * 有鬼牌 计算胡牌牌型
     *
     * @param guipaiList        玩家鬼牌集合
     * @param shoupaiCalculator 玩家手牌
     * @param player            麻将玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             刚摸入的牌
     * @return
     */
    private static List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithCaishen(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        int chichuShunziCount = player.countChichupaiZu();//以吃牌数量
        int pengchuKeziCount = player.countPengchupaiZu();//已碰牌数量
        int gangchuGangziCount = player.countGangchupaiZu();//以杠牌数量
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        MajiangPai[] paiTypesForGuipaiAct = calculatePaiTypesForGuipaiAct(); //鬼牌可以扮演的牌类（序数牌 没有风中发白）
        // 开始循环财神各种当法，算构型
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = calculateShoupaiWithGuipaiDangGouXingZuList(guipaiList, paiTypesForGuipaiAct, shoupaiCalculator);
        // 对于可胡的构型，计算出所有牌型
        for (ShoupaiWithGuipaiDangGouXingZu shoupaiWithGuipaiDangGouXingZu : shoupaiWithGuipaiDangGouXingZuList) {
            GuipaiDangPai[] guipaiDangPaiArray = shoupaiWithGuipaiDangGouXingZu.getGuipaiDangPaiArray();
            List<GouXing> gouXingList = shoupaiWithGuipaiDangGouXingZu.getGouXingList();
            for (GouXing gouXing : gouXingList) {
                boolean hu = gouXingPanHu.panHu(gouXing.getGouXingCode(), chichuShunziCount, pengchuKeziCount, gangchuGangziCount);
                if (hu) {
                    // 先把所有当的鬼牌加入计算器
                    for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                        shoupaiCalculator.addPai(guipaiDangPaiArray[i].getDangpai());
                    }
                    // 计算牌型
                    huPaiShoupaiPaiXingList.addAll(calculateAllShoupaiPaiXingForGouXingWithHupai(gouXing, shoupaiCalculator, guipaiDangPaiArray, huPai));
                    // 再把所有当的鬼牌移出计算器
                    for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                        shoupaiCalculator.removePai(guipaiDangPaiArray[i].getDangpai());
                    }
                }
            }
        }
        return huPaiShoupaiPaiXingList;
    }

    private static List<ShoupaiPaiXing> calculateBuhuShoupaiPaiXingListWithoutCaishen(ShoupaiCalculator shoupaiCalculator) {
        List<ShoupaiPaiXing> buhuShoupaiPaiXingList = new ArrayList<>();
        // 计算构型
        List<GouXing> gouXingList = shoupaiCalculator.calculateAllGouXing();
        for (GouXing gouXing : gouXingList) {
            // 计算牌型
            List<PaiXing> paiXingList = shoupaiCalculator.calculateAllPaiXingFromGouXing(gouXing);
            for (PaiXing paiXing : paiXingList) {
                ShoupaiPaiXing shoupaiPaiXing = paiXing.generateAllBenPaiShoupaiPaiXing();
                buhuShoupaiPaiXingList.add(shoupaiPaiXing);
            }
        }
        return buhuShoupaiPaiXingList;
    }

    private static List<ShoupaiPaiXing> calculateBuhuShoupaiPaiXingListWithCaishen(List<MajiangPai> guipaiList,
                                                                                   ShoupaiCalculator shoupaiCalculator) {
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        MajiangPai[] paiTypesForGuipaiAct = calculatePaiTypesForGuipaiAct();// 鬼牌可以扮演的牌类
        // 开始循环财神各种当法，算构型
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = calculateShoupaiWithGuipaiDangGouXingZuList(guipaiList, paiTypesForGuipaiAct, shoupaiCalculator);
        // 对构型计算出所有牌型
        for (ShoupaiWithGuipaiDangGouXingZu shoupaiWithGuipaiDangGouXingZu : shoupaiWithGuipaiDangGouXingZuList) {
            GuipaiDangPai[] guipaiDangPaiArray = shoupaiWithGuipaiDangGouXingZu.getGuipaiDangPaiArray();
            List<GouXing> gouXingList = shoupaiWithGuipaiDangGouXingZu.getGouXingList();
            for (GouXing gouXing : gouXingList) {
                // 先把所有当的鬼牌加入计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    shoupaiCalculator.addPai(guipaiDangPaiArray[i].getDangpai());
                }
                // 计算牌型
                huPaiShoupaiPaiXingList.addAll(calculateAllShoupaiPaiXingForGouXingWithoutHupai(gouXing,
                        shoupaiCalculator, guipaiDangPaiArray));
                // 再把所有当的鬼牌移出计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    shoupaiCalculator.removePai(guipaiDangPaiArray[i].getDangpai());
                }
            }
        }
        return huPaiShoupaiPaiXingList;
    }

    /**
     * 只有序数牌，没有特殊玩法的红中
     *
     * @return
     */
    private static MajiangPai[] calculatePaiTypesForGuipaiAct() {
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiAndZipaiArray();
        MajiangPai[] paiTypesForGuipaiAct;
        paiTypesForGuipaiAct = new MajiangPai[xushupaiArray.length];
        System.arraycopy(xushupaiArray, 0, paiTypesForGuipaiAct, 0, xushupaiArray.length);
        return paiTypesForGuipaiAct;
    }

    /**
     * 计算有鬼牌可以胡的牌型
     *
     * @param guipaiList           玩家鬼牌集合
     * @param paiTypesForGuipaiAct 鬼牌可以替代的牌型
     * @param shoupaiCalculator    玩家手牌
     * @return
     */
    private static List<ShoupaiWithGuipaiDangGouXingZu> calculateShoupaiWithGuipaiDangGouXingZuList(List<MajiangPai> guipaiList, MajiangPai[] paiTypesForGuipaiAct, ShoupaiCalculator shoupaiCalculator) {
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = new ArrayList<>();
        int guipaiCount = guipaiList.size();//鬼牌数量
        int maxZuheCode = (int) Math.pow(paiTypesForGuipaiAct.length, guipaiCount);//最大组合量
        int[] modArray = new int[guipaiCount];
        for (int i = 0; i < guipaiCount; i++) {
            modArray[i] = (int) Math.pow(paiTypesForGuipaiAct.length, guipaiCount - 1 - i);
        }
        for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {
            GuipaiDangPai[] guipaiDangPaiArray = new GuipaiDangPai[guipaiCount];
            int temp = zuheCode;
            int previousGuipaiDangIdx = 0;
            for (int i = 0; i < guipaiCount; i++) {
                int mod = modArray[i];
                int shang = temp / mod;
                if (shang >= previousGuipaiDangIdx) {
                    int yu = temp % mod;
                    guipaiDangPaiArray[i] = new GuipaiDangPai(guipaiList.get(i), paiTypesForGuipaiAct[shang]);
                    temp = yu;
                    previousGuipaiDangIdx = shang;
                } else {
                    guipaiDangPaiArray = null;
                    break;
                }
            }
            if (guipaiDangPaiArray != null) {
                // 先把所有当的鬼牌加入计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    shoupaiCalculator.addPai(guipaiDangPaiArray[i].getDangpai());
                }
                // 计算构型
                List<GouXing> gouXingList = shoupaiCalculator.calculateAllGouXing();
                // 再把所有当的鬼牌移出计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    shoupaiCalculator.removePai(guipaiDangPaiArray[i].getDangpai());
                }
                ShoupaiWithGuipaiDangGouXingZu shoupaiWithGuipaiDangGouXingZu = new ShoupaiWithGuipaiDangGouXingZu();
                shoupaiWithGuipaiDangGouXingZu.setGouXingList(gouXingList);
                shoupaiWithGuipaiDangGouXingZu.setGuipaiDangPaiArray(guipaiDangPaiArray);
                shoupaiWithGuipaiDangGouXingZuList.add(shoupaiWithGuipaiDangGouXingZu);
            }
        }
        return shoupaiWithGuipaiDangGouXingZuList;
    }

    private static List<ShoupaiPaiXing> calculateAllShoupaiPaiXingForGouXingWithHupai(GouXing gouXing, ShoupaiCalculator shoupaiCalculator, GuipaiDangPai[] guipaiDangPaiArray, MajiangPai huPai) {
        boolean sancaishen = (guipaiDangPaiArray.length == 3);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        // 计算牌型
        List<PaiXing> paiXingList = shoupaiCalculator.calculateAllPaiXingFromGouXing(gouXing);
        for (PaiXing paiXing : paiXingList) {
            List<ShoupaiPaiXing> shoupaiPaiXingList = paiXing.generateShoupaiPaiXingByDangPai(guipaiDangPaiArray);
            // 过滤暗杠或暗刻有两个财神当的
            Iterator<ShoupaiPaiXing> i = shoupaiPaiXingList.iterator();
            while (i.hasNext()) {
                ShoupaiPaiXing shoupaiPaiXing = i.next();
                for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
                    if (shoupaiKeziZu.countGuipaiDangQitapai() > (sancaishen ? 2 : 1)) {
                        i.remove();
                        break;
                    }
                }
                for (ShoupaiGangziZu shoupaiGangziZu : shoupaiPaiXing.getGangziList()) {
                    if (shoupaiGangziZu.countGuipaiDangQitapai() > (sancaishen ? 2 : 1)) {
                        i.remove();
                        break;
                    }
                }
            }

            // 对于每一个ShoupaiPaiXing还要变换最后弄进的牌
            for (ShoupaiPaiXing shoupaiPaiXing : shoupaiPaiXingList) {
                List<ShoupaiPaiXing> shoupaiPaiXingListWithDifftentLastActionPaiInZu = shoupaiPaiXing.differentiateShoupaiPaiXingByLastActionPai(huPai);
                huPaiShoupaiPaiXingList.addAll(shoupaiPaiXingListWithDifftentLastActionPaiInZu);
            }

        }
        return huPaiShoupaiPaiXingList;
    }

    private static List<ShoupaiPaiXing> calculateAllShoupaiPaiXingForGouXingWithoutHupai(GouXing gouXing, ShoupaiCalculator shoupaiCalculator, GuipaiDangPai[] guipaiDangPaiArray) {
        boolean sancaishen = (guipaiDangPaiArray.length == 3);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        // 计算牌型
        List<PaiXing> paiXingList = shoupaiCalculator.calculateAllPaiXingFromGouXing(gouXing);
        for (PaiXing paiXing : paiXingList) {
            List<ShoupaiPaiXing> shoupaiPaiXingList = paiXing.generateShoupaiPaiXingByDangPai(guipaiDangPaiArray);
            // 过滤暗杠或暗刻有两个财神当的
            Iterator<ShoupaiPaiXing> i = shoupaiPaiXingList.iterator();
            while (i.hasNext()) {
                ShoupaiPaiXing shoupaiPaiXing = i.next();
                for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
                    if (shoupaiKeziZu.countGuipaiDangQitapai() > (sancaishen ? 2 : 1)) {
                        i.remove();
                        break;
                    }
                }
                for (ShoupaiGangziZu shoupaiGangziZu : shoupaiPaiXing.getGangziList()) {
                    if (shoupaiGangziZu.countGuipaiDangQitapai() > (sancaishen ? 2 : 1)) {
                        i.remove();
                        break;
                    }
                }
            }
            huPaiShoupaiPaiXingList.addAll(shoupaiPaiXingList);
        }
        return huPaiShoupaiPaiXingList;
    }

    /**
     * 财神吊将：财神吊将胡牌时，只能自摸胡这个的将牌 。注意：财神在111和123的框架下只能存在一个
     */
    private static boolean isValid(ShoupaiPaiXing shoupaiPaiXing, boolean zimo) {
        ShoupaiDuiziZu duizi = shoupaiPaiXing.findDuiziZuHasLastActionPai();
        if (duizi != null && !duizi.yuanPaiFenZu() && !zimo) {
            return false;
        }
        List<ShoupaiKeziZu> keziList = shoupaiPaiXing.getKeziList();
        for (ShoupaiKeziZu kezi : keziList) {
            if (kezi.countDangPai(GuipaiDangPai.dangType) > 1) {
                return false;
            }
        }
        List<ShoupaiShunziZu> shunziList = shoupaiPaiXing.getShunziList();
        for (ShoupaiShunziZu shunzi : shunziList) {
            if (shunzi.countDangPai(GuipaiDangPai.dangType) > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 一条龙牌型判断
     *
     * @param shoupaiPaiXing 手牌牌型
     * @return
     */
    private static boolean isYitiaolong(ShoupaiPaiXing shoupaiPaiXing) {
        if (shoupaiPaiXing.getShunziList().size() >= 3) {
            int[] shoupaidianshu = new int[9];
            for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
                if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.yiwan)) {
                    shoupaidianshu[0]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.siwan)) {
                    shoupaidianshu[1]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.qiwan)) {
                    shoupaidianshu[2]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.yitong)) {
                    shoupaidianshu[3]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.sitong)) {
                    shoupaidianshu[4]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.qitong)) {
                    shoupaidianshu[5]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.yitiao)) {
                    shoupaidianshu[6]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.sitiao)) {
                    shoupaidianshu[7]++;
                } else if (shoupaiShunziZu.getPai1().getZuoyongPaiType().equals(MajiangPai.qitiao)) {
                    shoupaidianshu[8]++;
                }
            }

            if (shoupaidianshu[0] != 0 && shoupaidianshu[1] != 0 && shoupaidianshu[2] != 0) {
                return true;
            } else if (shoupaidianshu[3] != 0 && shoupaidianshu[4] != 0 && shoupaidianshu[5] != 0) {
                return true;
            } else if (shoupaidianshu[6] != 0 && shoupaidianshu[7] != 0 && shoupaidianshu[8] != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否胡十三幺
     * 判断手牌中十三幺牌型一九万、筒、条、东南西北中发白缺几张
     * 如果缺失手牌小于等于鬼牌数量并且手中有一个对子，为十三幺牌型
     *
     * @return
     */
    public static boolean isShisanyaoPaixing(MajiangPlayer player, MajiangPai pai) {
        List<MajiangPai> shoupai = new ArrayList<>(player.getFangruShoupaiList());
        int guipaiCount = player.getFangruGuipaiList().size();
        Set<MajiangPai> guipaiTypeSet = player.getGuipaiTypeSet();
        if (pai == null) {//自摸胡
            if (guipaiTypeSet.contains(player.getGangmoShoupai())) {
                guipaiCount++;
            } else {
                shoupai.add(player.getGangmoShoupai());
            }
        } else {//听胡
            shoupai.add(pai);
        }

        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int count = 0;//相差数量

        for (int i = 0; i < shoupai.size(); i++) {//计算手牌与十三幺固定牌型相差牌数量
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (shoupai.get(i).ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }

        for (int i = 0; i < shisanyaoPaixin.length; i++) {//如果手中有十三幺的牌 就从集合中移除 没有相差数量加一
            if (shisanyaoPaixin[i] < 0) {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));
            } else {
                count++;
            }
        }

        if (shoupai.size() > 0) {//判断手牌集合内是否还有是手牌
            for (int s : shisanyaoPaixin2) {
                if (s == shoupai.get(0).ordinal() && guipaiCount >= count) {//判断剩余手牌是否可以组成对子并且鬼牌数量大于相差数量
                    return true;
                }
            }
        } else {
            if (guipaiCount - 1 >= count) {//如果没有手牌就判断鬼牌数减去1后大于相差数量
                return true;
            }
        }
        return false;
    }
}
