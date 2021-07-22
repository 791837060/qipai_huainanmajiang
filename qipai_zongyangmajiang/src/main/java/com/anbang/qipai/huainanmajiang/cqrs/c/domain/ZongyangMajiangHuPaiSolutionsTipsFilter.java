package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.hu.HupaiPaixingSolutionFilter;
import com.dml.majiang.player.shoupai.*;
import com.dml.majiang.player.shoupai.gouxing.GouXing;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.*;

/**
 * 胡牌提示
 */
public class ZongyangMajiangHuPaiSolutionsTipsFilter implements HupaiPaixingSolutionFilter {
    private static OptionalPlay optionalPlay;

    /**
     * 胡牌提示 摸牌后
     *
     * @param majiangPlayer 麻将玩家
     * @param gouXingPanHu  可胡牌型
     * @return
     */
    public Map<MajiangPai, List<MajiangPai>> hupaiFilter(MajiangPlayer majiangPlayer, GouXingPanHu gouXingPanHu) {
        Map<MajiangPai, List<MajiangPai>> hupaiList = new HashMap<>();
        MajiangPai guipai = MajiangPai.hongzhong;//如果有鬼牌 鬼牌为红中 没有则就没有红中
        List<MajiangPai> guipaiList = majiangPlayer.findGuipaiList(); //鬼牌集合

        for (MajiangPai majiangPai : majiangPlayer.getFangruShoupaiList()) {
            ShoupaiCalculator shoupaiCalculator = majiangPlayer.getShoupaiCalculator();
            List<ShoupaiPaiXing> huPaiShoupaiPaiXingList;
            shoupaiCalculator.removePai(majiangPai);
            if (!majiangPlayer.gangmoGuipai()) { //剔除鬼牌
                shoupaiCalculator.addPai(majiangPlayer.getGangmoShoupai());
            }
            guipaiList.add(guipai);//将鬼牌放入鬼牌集合内
            huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, majiangPlayer, gouXingPanHu, guipai);
            guipaiList.remove(guipaiList.size() - 1);
            shoupaiCalculator.addPai(majiangPai);
            if (!majiangPlayer.gangmoGuipai()) {
                shoupaiCalculator.removePai(majiangPlayer.getGangmoShoupai());
            }
            if (!huPaiShoupaiPaiXingList.isEmpty()) {
                List<MajiangPai> kehupai = kehupai(huPaiShoupaiPaiXingList, majiangPlayer, guipai);
                if (!kehupai.isEmpty()) {
                    hupaiList.put(majiangPai, kehupai);
                }
            }
        }

        ShoupaiCalculator shoupaiCalculator = majiangPlayer.getShoupaiCalculator();
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList;
        if (!majiangPlayer.gangmoGuipai()) {
            guipaiList.add(guipai);
        }
        huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, majiangPlayer, gouXingPanHu, guipai);
        if (!majiangPlayer.gangmoGuipai()) {
            guipaiList.remove(guipaiList.size() - 1);
        }
        if (!huPaiShoupaiPaiXingList.isEmpty()) {
            List<MajiangPai> kehupai = kehupai(huPaiShoupaiPaiXingList, majiangPlayer, guipai);
            if (!kehupai.isEmpty()) {
                hupaiList.put(majiangPlayer.getGangmoShoupai(), kehupai);
            }
        }
        if (guipaiList.size() != 0) {
            if (!majiangPlayer.gangmoGuipai()) {
                shoupaiCalculator.addPai(majiangPlayer.getGangmoShoupai());
            }
            huPaiShoupaiPaiXingList = (calculateZimoHuPaiShoupaiPaiXingList(guipaiList,
                    shoupaiCalculator, majiangPlayer, gouXingPanHu, guipai));
            if (!majiangPlayer.gangmoGuipai()) {
                shoupaiCalculator.removePai(majiangPlayer.getGangmoShoupai());
            }
            if (!huPaiShoupaiPaiXingList.isEmpty()) {
                List<MajiangPai> kehupai = kehupai(huPaiShoupaiPaiXingList, majiangPlayer, guipai);
                if (!kehupai.isEmpty()) {
                    hupaiList.put(guipaiList.get(0), kehupai);
                }
            }
        }
        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = calculateShisanyaoMoPaiHupaiTips(majiangPlayer);
        if (majiangPaiListMap != null) {
            hupaiList.putAll(majiangPaiListMap);
        }
        return hupaiList;
    }

    /**
     * 胡牌提示 可以胡牌
     *
     * @param majiangPlayer 玩家
     * @param gouXingPanHu  可胡牌型
     * @return
     */
    public List<MajiangPai> kehuFilter(MajiangPlayer majiangPlayer, GouXingPanHu gouXingPanHu) {
        List<MajiangPai> hupaiList = new ArrayList<>();
        MajiangPai guipai = MajiangPai.hongzhong;
        List<MajiangPai> guipaiList = majiangPlayer.findGuipaiList();
        ShoupaiCalculator shoupaiCalculator = majiangPlayer.getShoupaiCalculator();//玩家未公开的手牌
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList;
        guipaiList.add(guipai);
        huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, majiangPlayer, gouXingPanHu, guipai);
        guipaiList.remove(guipaiList.size() - 1);
        if (!huPaiShoupaiPaiXingList.isEmpty()) {
            List<MajiangPai> kehupai = kehupai(huPaiShoupaiPaiXingList, majiangPlayer, guipai);
            if (!kehupai.isEmpty()) {
                hupaiList.addAll(kehupai);
            }
        }
        List<MajiangPai> majiangPais = calculateShisanyaoHupaiTips(majiangPlayer);
        if (majiangPais != null) {
            hupaiList.addAll(majiangPais);
        }
        return hupaiList;
    }

    /**
     * 胡牌提示 碰牌后
     *
     * @param majiangPlayer
     * @param gouXingPanHu
     * @return
     */
    public Map<MajiangPai, List<MajiangPai>> pengHupaiFilter(MajiangPlayer majiangPlayer, GouXingPanHu gouXingPanHu) {
        Map<MajiangPai, List<MajiangPai>> hupaiList = new HashMap<>();
        MajiangPai guipai = MajiangPai.hongzhong;
        List<MajiangPai> guipaiList = majiangPlayer.findGuipaiList();
        for (MajiangPai majiangPai : majiangPlayer.getFangruShoupaiList()) {
            ShoupaiCalculator shoupaiCalculator = majiangPlayer.getShoupaiCalculator();
            List<ShoupaiPaiXing> huPaiShoupaiPaiXingList;
            shoupaiCalculator.removePai(majiangPai);
            guipaiList.add(guipai);
            huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, majiangPlayer, gouXingPanHu, guipai);
            guipaiList.remove(guipaiList.size() - 1);
            shoupaiCalculator.addPai(majiangPai);
            if (!huPaiShoupaiPaiXingList.isEmpty()) {
                List<MajiangPai> kehupai = kehupai(huPaiShoupaiPaiXingList, majiangPlayer, guipai);
                if (!kehupai.isEmpty()) {
                    hupaiList.put(majiangPai, kehupai);
                }
            }
        }
        return hupaiList;
    }

    /**
     * 2 5 8将
     *
     * @param duiziZuList
     * @return
     */
    private static boolean erwubajiang(List<ShoupaiDuiziZu> duiziZuList) {
        for (ShoupaiDuiziZu shoupaiDuiziZu : duiziZuList) {
            if (shoupaiDuiziZu.getDuiziType().equals(MajiangPai.ertiao) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.wutiao) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.batiao) ||
                    shoupaiDuiziZu.getDuiziType().equals(MajiangPai.erwan) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.wuwan) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.bawan) ||
                    shoupaiDuiziZu.getDuiziType().equals(MajiangPai.ertong) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.wutong) || shoupaiDuiziZu.getDuiziType().equals(MajiangPai.batong)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 胡牌提示胡哪些牌
     *
     * @param huPaiShoupaiPaiXingList 胡牌牌型
     * @param majiangPlayer           麻将玩家
     * @param guipai                  鬼牌
     * @return
     */
    private List<MajiangPai> kehupai(List<ShoupaiPaiXing> huPaiShoupaiPaiXingList, MajiangPlayer majiangPlayer, MajiangPai guipai) {
        List<MajiangPai> kehuPai = new ArrayList<>();
        for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
//            if (shoupaiPaiXing.getDuiziList().size() == 1 || shoupaiPaiXing.getDuiziList().size() == 2) {
//                if (erwubajiang(shoupaiPaiXing.getDuiziList())) {
//                    generateKehu(majiangPlayer, guipai, kehuPai, shoupaiPaiXing);
//                }
//                if (majiangPlayer.allXushupaiWithoutGuipaiInSameCategory(null)) {
//                    generateKehu(majiangPlayer, guipai, kehuPai, shoupaiPaiXing);
//                }
//                if (shoupaiPaiXing.getDuiziList().size() == 1 && majiangPlayer.countChichupaiZu() == 0 && shoupaiPaiXing.countShunzi() == 0) {
//                    generateKehu(majiangPlayer, guipai, kehuPai, shoupaiPaiXing);
//                }
//            } else {
//                generateKehu(majiangPlayer, guipai, kehuPai, shoupaiPaiXing);
//            }
            generateKehu(majiangPlayer, guipai, kehuPai, shoupaiPaiXing);
        }
        return kehuPai;
    }

    /**
     * 可胡的牌
     *
     * @param majiangPlayer  麻将玩家
     * @param guipai         鬼牌
     * @param kehuPai        可胡麻将牌
     * @param shoupaiPaiXing 手牌牌型
     */
    private void generateKehu(MajiangPlayer majiangPlayer, MajiangPai guipai, List<MajiangPai> kehuPai, ShoupaiPaiXing shoupaiPaiXing) {
        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
            if (shoupaiShunziZu.getPai1().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiShunziZu.getPai1().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiShunziZu.getPai1().getZuoyongPaiType());
                }
            }
            if (shoupaiShunziZu.getPai2().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiShunziZu.getPai2().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiShunziZu.getPai2().getZuoyongPaiType());
                }
            }
            if (shoupaiShunziZu.getPai3().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiShunziZu.getPai3().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiShunziZu.getPai3().getZuoyongPaiType());
                }
            }
        }

        for (ShoupaiDuiziZu shoupaiDuiziZu : shoupaiPaiXing.getDuiziList()) {
            if (shoupaiDuiziZu.getPai1().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiDuiziZu.getPai1().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiDuiziZu.getPai1().getZuoyongPaiType());
                }
            }
            if (shoupaiDuiziZu.getPai2().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiDuiziZu.getPai2().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiDuiziZu.getPai2().getZuoyongPaiType());
                }
            }
        }

        for (ShoupaiGangziZu shoupaiGangziZu : shoupaiPaiXing.getGangziList()) {
            if (shoupaiGangziZu.getPai1().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiGangziZu.getPai1().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiGangziZu.getPai1().getZuoyongPaiType());
                }
            }
            if (shoupaiGangziZu.getPai2().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiGangziZu.getPai2().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiGangziZu.getPai2().getZuoyongPaiType());
                }
            }
            if (shoupaiGangziZu.getPai3().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiGangziZu.getPai3().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiGangziZu.getPai3().getZuoyongPaiType());
                }
            }
            if (shoupaiGangziZu.getPai4().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiGangziZu.getPai4().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiGangziZu.getPai4().getZuoyongPaiType());
                }
            }
        }

        for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
            if (shoupaiKeziZu.getPai1().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiKeziZu.getPai1().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiKeziZu.getPai1().getZuoyongPaiType());
                }
            }
            if (shoupaiKeziZu.getPai2().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiKeziZu.getPai2().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiKeziZu.getPai2().getZuoyongPaiType());
                }
            }
            if (shoupaiKeziZu.getPai3().getYuanPaiType().equals(guipai)) {
                if (!kehuPai.contains(shoupaiKeziZu.getPai3().getZuoyongPaiType())) {
                    kehuPai.add(shoupaiKeziZu.getPai3().getZuoyongPaiType());
                }
            }
        }

        if (!majiangPlayer.getGuipaiTypeSet().isEmpty() && !kehuPai.contains((MajiangPai) majiangPlayer.getGuipaiTypeSet().toArray()[0])) {
            kehuPai.add((MajiangPai) majiangPlayer.getGuipaiTypeSet().toArray()[0]);
        }
    }

    /**
     * 如果摸牌后听牌 计算胡牌提示
     *
     * @param guipaiList        鬼牌集合
     * @param shoupaiCalculator 手牌计算器
     * @param player            麻将玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             胡的牌
     * @return
     */
    private static List<ShoupaiPaiXing> calculateZimoHuPaiShoupaiPaiXingList(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        return calculateHuPaiShoupaiPaiXingListWithCaishen(guipaiList, shoupaiCalculator, player, gouXingPanHu, huPai);
    }

    /**
     * 如果摸牌后听牌 计算胡牌提示
     *
     * @param guipaiList        鬼牌集合
     * @param shoupaiCalculator 手牌计算器
     * @param player            玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             胡牌
     * @return
     */
    private static List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithCaishen(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        int chichuShunziCount = player.countChichupaiZu();  //吃出数量
        int pengchuKeziCount = player.countPengchupaiZu();  //碰出数量
        int gangchuGangziCount = player.countGangchupaiZu();//杠出数量
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        MajiangPai[] paiTypesForGuipaiAct = calculatePaiTypesForGuipaiAct(); //鬼牌可以替代的牌（序数牌）
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = calculateShoupaiWithGuipaiDangGouXingZuList(guipaiList, paiTypesForGuipaiAct, shoupaiCalculator); //开始循环鬼牌各种当法，算构型
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

    /**
     * 计算鬼牌可以替代的牌型
     *
     * @param guipaiList           鬼牌集合
     * @param paiTypesForGuipaiAct 鬼牌可以替代的牌型
     * @param shoupaiCalculator    手牌计算器
     * @return
     */
    private static List<ShoupaiWithGuipaiDangGouXingZu> calculateShoupaiWithGuipaiDangGouXingZuList(List<MajiangPai> guipaiList, MajiangPai[] paiTypesForGuipaiAct, ShoupaiCalculator shoupaiCalculator) {
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = new ArrayList<>();

        // 两个鬼牌只要套两层循环分别遍历所有的当法，三个鬼牌套三层循环，更多个鬼牌以此类推。
        // 出于通用考虑，这样写死几层循环的算法实现起来代码会很长，很不合理。
        // 这里改用一个面向通用的n个鬼牌的算法：
        // n个鬼牌，每个鬼牌都有不同的变化，这样组合起来最终就有很多种组合法。
        // 比如第一个鬼牌当一筒，第二个鬼牌当六万是一种。第一个鬼牌当九条，第二个鬼牌当南风又是一种。所以(假设总共一万种组合)可以给每种组合编号0,1,2,3,4,......,9998,9999
        // 我们的思路是，不管几个鬼牌，这种编号是扁平的（不需要考虑套几层循环），只是最大编号不同而已。最大编号问题就是总共几种组合法的问题。
        // 解总共几种组合法的问题非常简单，假设一个鬼牌有14种当法（扮演14种不同的牌），那两个鬼牌就是14*14种组合法，三个就是14*14*14种，n个就是14的n次方种。
        // 所以我们可以一趟循环走组合编号。那么现在唯一可以利用的就是编号值本身，我们需要从编号值推断出具体的组合方案。
        // 我们考虑人工罗列这些方案是怎么做的。假设两个鬼牌，可以扮演一万到九万。那第一个鬼牌先取一万，第二个鬼牌从一万开始一个个按顺序取过来取到九万，
        // 接着就是第一个鬼牌取二万，第二个鬼牌再次从一万开始一个个按顺序取过来取到九万，然后第一个鬼牌取三万......
        // 我们想想这不就是进位翻牌器吗？翻牌器也就是一个计数器，能覆盖0到n的所有数字。
        // 翻牌器的原理其实就是10进制。所以我们利用进制来实现 从编号值推断出具体的组合方案。
        // 我们还是来看下10进制的情况，假设三个鬼牌，一个组合编码，也就是一个数字,x,那他的百位的数值可以用来代表第一个鬼牌的当法，十位的数值可以用来代表第二个鬼牌的当法，
        // 个位的数值可以用来代表第三个鬼牌的当法。
        // 比如编码123,那就意味着 第一个鬼牌当二万，第二个鬼牌当三万，第三个鬼牌当四万。所以现在的问题是从一个数字中取出它百位的数值，十位的数值和个位的数值。
        // 我们来解这个问题，要知道123的百位数值也就是要知道123里面有几个100（这个100是事先算好的模），所以123除以100得到的商是1，这个1就是结果了，
        // 然后余数是23，这个23不要丢掉，这个余数去除以10得到的商恰好就是十位的值，2，个位以此类推......
        // 当然麻将他不是10进制的，不管几进制，可以证明此算法是通用的。

        int guipaiCount = guipaiList.size();
        int maxZuheCode = (int) Math.pow(paiTypesForGuipaiAct.length, guipaiCount);
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

    /**
     * 鬼牌可以替代的牌
     *
     * @return
     */
    private static MajiangPai[] calculatePaiTypesForGuipaiAct() {
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiArray();
        List<MajiangPai> majiangPais = new ArrayList<>();
        Collections.addAll(majiangPais, xushupaiArray);
        for (int i = 32; i < 34; i++) {
            majiangPais.add(MajiangPai.valueOf(i));
        }
        MajiangPai[] paiTypesForGuipaiAct = new MajiangPai[majiangPais.size()];
        majiangPais.toArray(paiTypesForGuipaiAct);
        return paiTypesForGuipaiAct;
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

    /**
     * 计算十三幺胡牌提示
     *
     * @param majiangPlayer 麻将玩家
     * @return
     */
    public static List<MajiangPai> calculateShisanyaoHupaiTips(MajiangPlayer majiangPlayer) {
        List<MajiangPai> hupaiList = new ArrayList<>();
        List<MajiangPai> shoupai = new ArrayList<>(majiangPlayer.getFangruShoupaiList());
        int guipaiCount = majiangPlayer.getFangruGuipaiList().size();
        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        int count = 0;//相差数量
        for (int i = 0; i < shoupai.size(); i++) {//剔除已有麻将牌
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (shoupai.get(i).ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }
        for (int i = 0; i < shisanyaoPaixin.length; i++) {//计算相差牌数
            if (shisanyaoPaixin[i] >= 0) {
                hupaiList.add(MajiangPai.valueOf(shisanyaoPaixin[i]));
                count++;
            } else {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));
            }
        }
        if (shoupai.size() > 0) {
            if (hupaiList.size() - guipaiCount <= 1) {
                return hupaiList;
            }
        } else {
            if (guipaiCount >= count) {//如果十三幺牌型都有 返回所有可胡牌型（差一张牌凑成对子）
                hupaiList.clear();
                for (int i : shisanyaoPaixin2) {
                    hupaiList.add(MajiangPai.valueOf(i));
                }
                return hupaiList;
            }
        }
        return null;
    }

    /**
     * 计算摸牌后十三幺胡牌提示
     *
     * @param majiangPlayer 麻将玩家
     * @return
     */
    public static Map<MajiangPai, List<MajiangPai>> calculateShisanyaoMoPaiHupaiTips(MajiangPlayer majiangPlayer) {
        List<MajiangPai> shoupai = new ArrayList<>(majiangPlayer.getFangruShoupaiList());
        MajiangPai gangmoShoupai = majiangPlayer.getGangmoShoupai();
        shoupai.add(gangmoShoupai);
        List<MajiangPai> hupaiList = new ArrayList<>();
        int[] shisanyaoPaixin = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        Integer[] shisanyaoPaixin2 = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};
        for (int i = 0; i < shoupai.size(); i++) {//剔除已有麻将牌
            for (int j = 0; j < shisanyaoPaixin.length; j++) {
                if (shoupai.get(i).ordinal() == shisanyaoPaixin[j]) {
                    shisanyaoPaixin[j] = -1;
                }
            }
        }
        for (int i = 0; i < shisanyaoPaixin.length; i++) {//计算相差牌数
            if (shisanyaoPaixin[i] >= 0) {
                hupaiList.add(MajiangPai.valueOf(shisanyaoPaixin[i]));//提示缺少手牌集合
            } else {
                shoupai.remove(MajiangPai.valueOf(shisanyaoPaixin2[i]));//剩余手牌
            }
        }
        Map<MajiangPai, List<MajiangPai>> tips = new HashMap<>();
        if (shoupai.size() == 2) {
            List<Integer> ints = Arrays.asList(shisanyaoPaixin2);
            for (MajiangPai m : shoupai) {
                if (ints.contains(shoupai.get(0).ordinal()) && ints.contains(shoupai.get(1).ordinal())) {
                    tips.put(m, hupaiList);
                } else {
                    if (ints.contains(shoupai.get(0).ordinal())) {
                        tips.put(shoupai.get(1), hupaiList);
                    } else if (ints.contains(shoupai.get(1).ordinal())) {
                        tips.put(shoupai.get(0), hupaiList);
                    }
                }
            }
            return tips;
        }
        return null;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
