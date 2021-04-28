package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.pai.fenzu.Shunzi;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.mo.GanghouBupai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.chupaizu.GangchuPaiZu;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;
import com.dml.majiang.player.shoupai.*;
import com.dml.majiang.player.shoupai.gouxing.GouXing;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.*;

public class MaanshanMajiangJiesuanCalculator {

    /**
     * 自摸胡
     *
     * @param couldTianhu  是否可以天胡
     * @param gouXingPanHu 胡牌牌型
     * @param player       麻将玩家
     * @param moAction     摸牌动作
     */
    public static MaanshanMajiangHu calculateBestZimoHu(boolean couldTianhu, GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangMoAction moAction, OptionalPlay optionalPlay, Pan currentPan) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        if (!player.gangmoGuipai()) {
            shoupaiCalculator.addPai(player.getGangmoShoupai());
        }
        MajiangPai gangmoShoupai = player.getGangmoShoupai();
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, gangmoShoupai); //计算胡牌
        if (!player.gangmoGuipai()) {
            shoupaiCalculator.removePai(player.getGangmoShoupai());
        }
        if (!huPaiShoupaiPaiXingList.isEmpty()) { //有胡牌型
            //要选出分数最高的牌型
            //先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, player.getGangmoShoupai());
            MaanshanMajiangHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                //计算胡分
                MaanshanMajiangHushu hufen = calculateHufen(true, moAction.getReason().getName().equals(GanghouBupai.name) || moAction.getReason().getName().equals(MaanshanMajiangBupai.name),
                        false, couldTianhu, false, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, player, currentPan, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null) {
                return null;
            }
            return new MaanshanMajiangHu(bestHuShoupaiPaiXing, bestHuFen);
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
     */
    public static MaanshanMajiangHu calculateBestQianggangHu(MajiangPai gangPai, GouXingPanHu gouXingPanHu, MajiangPlayer player, OptionalPlay optionalPlay, Pan currentPan) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        shoupaiCalculator.addPai(gangPai);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, gangPai);
        shoupaiCalculator.removePai(gangPai);

        if (!huPaiShoupaiPaiXingList.isEmpty()) {// 有胡牌型
            // 要选出分数最高的牌型
            // 先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, gangPai);
            MaanshanMajiangHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                MaanshanMajiangHushu hufen = calculateHufen(false, false, true, false, false, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing,
                        player, currentPan, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null) {
                return null;
            }
            return new MaanshanMajiangHu(bestHuShoupaiPaiXing, bestHuFen);
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
     */
    public static MaanshanMajiangHu calculateBestDianpaoHu(boolean couldDihu, GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangPai hupai, OptionalPlay optionalPlay, Pan currentPan) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, hupai);
        if (!huPaiShoupaiPaiXingList.isEmpty()) {   //有胡牌型
            // 要选出分数最高的牌型
            // 先计算和手牌型无关的参数
            ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, hupai);
            MaanshanMajiangHushu bestHuFen = null;
            ShoupaiPaiXing bestHuShoupaiPaiXing = null;
            for (ShoupaiPaiXing shoupaiPaiXing : huPaiShoupaiPaiXingList) {
                MaanshanMajiangHushu hufen = calculateHufen(false, false, false, false, couldDihu, shoupaixingWuguanJiesuancanshu, shoupaiPaiXing,
                        player, currentPan, optionalPlay);
                if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
                    bestHuFen = hufen;
                    bestHuShoupaiPaiXing = shoupaiPaiXing;
                }
            }
            if (bestHuFen == null || bestHuShoupaiPaiXing == null) {
                return null;
            }
            return new MaanshanMajiangHu(bestHuShoupaiPaiXing, bestHuFen);
        } else {    //不成胡
            return null;
        }
    }

//    /**
//     * 没有胡的人的分
//     *
//     * @param player 麻将玩家
//     */
//    public static MaanshanMajiangHushu calculateBestHuFenForBuhuPlayer(MajiangPlayer player, OptionalPlay optionalPlay) {
//        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
//        List<MajiangPai> guipaiList = player.findGuipaiList();// TODO 也可以用统计器做
//        List<ShoupaiPaiXing> shoupaiPaiXingList = calculateBuhuShoupaiPaiXingList(guipaiList, shoupaiCalculator, player);
//        // 要选出分数最高的牌型
//        // 先计算和手牌型无关的参数
//        ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu = new ShoupaixingWuguanJiesuancanshu(player, null);
//        MaanshanMajiangHushu bestHuFen = null;
//        for (ShoupaiPaiXing shoupaiPaiXing : shoupaiPaiXingList) {
//            MaanshanMajiangHushu hufen = calculateHufen(false, false, false, false, false, false,
//                    shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, player, null);
//            if (bestHuFen == null || bestHuFen.getValue() < hufen.getValue()) {
//                bestHuFen = hufen;
//            }
//        }
//        return bestHuFen;
//    }

    /**
     * 计算胡牌类型
     */
    private static MaanshanMajiangHushu calculateHufen(boolean zimoHu, boolean gangkaiHu, boolean qianggangHu, boolean couldTianhu, boolean couldDihu, ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu,
                                                       ShoupaiPaiXing shoupaiPaiXing, MajiangPlayer player, Pan currentPan, OptionalPlay optionalPlay) {
        MaanshanMajiangHushu hufen = new MaanshanMajiangHushu();

        List<MajiangPai> playerPaiList = buildPlayerAllPai(shoupaiPaiXing, player);
        if (!zimoHu) {
            playerPaiList.add(shoupaixingWuguanJiesuancanshu.getHupai());
        }

        int bazhi = isBazhi(playerPaiList);//八支
        boolean shuangbazhi = isShuangbazhi(playerPaiList);//双八支

        boolean fengyise = shoupaixingWuguanJiesuancanshu.isFengyise();//凤一色
        boolean qingyise = shoupaixingWuguanJiesuancanshu.isQingyise();//清一色
        boolean hunyise = shoupaixingWuguanJiesuancanshu.isHunyise();//混一色
        boolean duiduihu = shoupaiPaiXing.getDuiziList().size() == 1 && shoupaiPaiXing.getShunziList().size() == 0 && shoupaixingWuguanJiesuancanshu.getChichupaiZuCount() == 0;//对对胡
        boolean dadiaoche = shoupaiPaiXing.getDuiziList().size() == 1 && shoupaiPaiXing.getShunziList().size() == 0 && shoupaiPaiXing.getKeziList().size() == 0 && shoupaiPaiXing.getGangziList().size() == 0;//大吊车
        int minggangCount = 0, angangCount = 0;//明杠 暗杠
        for (GangchuPaiZu gangchuPaiZu : player.getGangchupaiZuList()) {
            if (gangchuPaiZu.getGangType().equals(GangType.gangdachu)) {
                minggangCount++;
            } else if (gangchuPaiZu.getGangType().equals(GangType.kezigangmo) || gangchuPaiZu.getGangType().equals(GangType.kezigangshoupai)) {
                minggangCount++;
            } else if (gangchuPaiZu.getGangType().equals(GangType.gangsigeshoupai) || gangchuPaiZu.getGangType().equals(GangType.shoupaigangmo)) {
                angangCount++;
            }
        }
        boolean tongtian = isYitiaolong(shoupaiPaiXing);//通天(一条龙)
        int[] xushuPaiArr = playerXushuPai(playerPaiList);

        int liulianCount = isLiulianCount(shoupaiPaiXing);
        boolean liulian = false, shuangliulian = false;//六连 双六连
        if (liulianCount == 1) {
            liulian = true;
        } else if (liulianCount >= 2) {
            shuangliulian = true;
        }

        int wutong = isWutongCount(xushuPaiArr);//五同
        int shuangwutong = isShuangwutongCount(xushuPaiArr);//双五同

        int siheCount = isSiheCount(shoupaiPaiXing, player);
        boolean sihe = false, shuangsihe = false;//四核 双四核
        if (siheCount == 1) {
            sihe = true;
        } else if (siheCount >= 2) {
            shuangsihe = true;
        }

        int sanzhangpengchu = player.getPengchupaiZuList().size();//三张碰出
        int sanzhangzaishou = shoupaiPaiXing.getKeziList().size() * 2;//三张在手
        MajiangPai hupai = shoupaixingWuguanJiesuancanshu.getHupai();
        for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
            if (shoupaiKeziZu.getKezi().getPaiType().equals(hupai) && !zimoHu) {
                sanzhangzaishou--;//点的那张三张在手只算1分
            }
        }

        boolean yadang = isYadang(shoupaixingWuguanJiesuancanshu, shoupaiPaiXing, player);
        boolean kuyazhi = isKuyazhi(shoupaixingWuguanJiesuancanshu, currentPan, zimoHu) && yadang;

        boolean shixiao = false, shilao = false, quanxiao = false, quanlao = false;//十小 十老 全小 全老
        int xiaoCount = 0, laoCount = 0, allCount = 0;
        for (int i = 0; i < xushuPaiArr.length; i++) {
            if (i <= 4) {
                xiaoCount += xushuPaiArr[i];
            }
            if (i >= 4) {
                laoCount += xushuPaiArr[i];
            }
            allCount += xushuPaiArr[i];
        }
        if (xiaoCount >= 10) {
            shixiao = true;
        }
        if (laoCount >= 10) {
            shilao = true;
        }
        if (allCount != 0 && !shoupaixingWuguanJiesuancanshu.isHasZipai()) {
            if (allCount == xiaoCount) {
                quanxiao = true;
            } else if (allCount == laoCount) {
                quanlao = true;
            }
        }
        boolean menqing = shoupaixingWuguanJiesuancanshu.getPengchuPaizuCount() == 0 && shoupaixingWuguanJiesuancanshu.getChichupaiZuCount() == 0 && !shoupaixingWuguanJiesuancanshu.isHasMinggang();

        int shuangpuzi = isShuangpuzi(shoupaiPaiXing);

        hufen.setHu(true);                          //胡
        hufen.setZimoHu(zimoHu);                    //自摸
        hufen.setBazhi(bazhi);                      //八支
        hufen.setShuangbazhi(shuangbazhi);          //双八支
        hufen.setFengyise(fengyise);                //凤一色
        hufen.setQingyise(qingyise);                //清一色
        hufen.setHunyise(hunyise);                  //混一色
        hufen.setDuiduihu(duiduihu);                //对对胡
        hufen.setDadiaoche(dadiaoche);              //大吊车
        hufen.setMinggang(minggangCount);           //明杠
        hufen.setAngang(angangCount);               //暗杠
        hufen.setTongtian(tongtian);                //通天
        hufen.setLiulian(liulian);                  //六连
        hufen.setShuangliulian(shuangliulian);      //双六连
        hufen.setWutong(wutong);                    //五同
        hufen.setShuangwutong(shuangwutong);        //双五同
        hufen.setSihe(sihe);                        //四核
        hufen.setShuangsihe(shuangsihe);            //双四核
        hufen.setKuyazhi(kuyazhi);                  //枯支压
        hufen.setSanzhangzaishou(sanzhangzaishou);  //三张在手
        hufen.setSanzhangpengchu(sanzhangpengchu);  //三张碰出
        hufen.setYadang(yadang);                    //压档
        hufen.setShixiao(shixiao);                  //十小
        hufen.setShilao(shilao);                    //十老
        hufen.setQuanxiao(quanxiao);                //全小
        hufen.setQuanlao(quanlao);                  //全老
        hufen.setBudongshou(menqing);               //不动手
        hufen.setShuangpuzi(shuangpuzi);            //双铺子

        boolean couldHu = hufen.calculate(currentPan, optionalPlay.isShidianqihu());
        if (!couldHu) {
            return null;
        }

        return hufen;
    }

    private static int isShuangpuzi(ShoupaiPaiXing shoupaiPaiXing) {
        List<ShoupaiShunziZu> shunziList = shoupaiPaiXing.getShunziList();
        Set<MajiangPai> majiangPais = new HashSet<>();
        int sameShunzi = 0;
        for (int i = 0; i < shunziList.size(); i++) {
            for (int j = i + 1; j < shunziList.size(); j++) {
                ShoupaiShunziZu shoupaiShunziZu = shunziList.get(i);
                ShoupaiShunziZu shoupaiShunziZu1 = shunziList.get(j);
                if (shoupaiShunziZu.getShunzi().getPai1().equals(shoupaiShunziZu1.getShunzi().getPai1()) &&
                        shoupaiShunziZu.getShunzi().getPai2().equals(shoupaiShunziZu1.getShunzi().getPai2()) &&
                        shoupaiShunziZu.getShunzi().getPai3().equals(shoupaiShunziZu1.getShunzi().getPai3()) &&
                        !majiangPais.contains(shoupaiShunziZu1.getShunzi().getPai1())) {
                    sameShunzi++;
                    majiangPais.add(shoupaiShunziZu1.getShunzi().getPai1());
                }
            }
        }
        return sameShunzi;
    }

    private static boolean isYadang(ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu, ShoupaiPaiXing shoupaiPaiXing, MajiangPlayer player) {
        MajiangPai hupai = shoupaixingWuguanJiesuancanshu.getHupai();

        Map<MajiangPai, List<MajiangPai>> hupaiCandidates = player.getHupaiCandidates();
        List<MajiangPai> kehuCandidates = player.getKehuCandidates();
        boolean onlyOneHu = false;
        if (hupaiCandidates.size() > 0) {
            for (List<MajiangPai> value : hupaiCandidates.values()) {
                if (value.size() == 1) {
                    if (value.get(0).equals(hupai)) {
                        onlyOneHu = true;
                        break;
                    }
                }
            }
        } else if (kehuCandidates.size() == 1) {
            if (kehuCandidates.get(0).equals(hupai)) {
                onlyOneHu = true;
            }
        }

        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
            if ((shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.sanwan) && hupai.equals(MajiangPai.sanwan)) ||
                    (shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.qiwan) && hupai.equals(MajiangPai.qiwan)) ||
                    (shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.santong) && hupai.equals(MajiangPai.santong)) ||
                    (shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.qitong) && hupai.equals(MajiangPai.qitong)) ||
                    (shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.santiao) && hupai.equals(MajiangPai.santiao)) ||
                    (shoupaiShunziZu.getShunzi().getPai1().equals(MajiangPai.qitiao) && hupai.equals(MajiangPai.qitiao))) {
                if (onlyOneHu) {
                    return true;
                }
            } else if ((shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.sanwan) && hupai.equals(MajiangPai.sanwan)) ||
                    (shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.qiwan) && hupai.equals(MajiangPai.qiwan)) ||
                    (shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.santong) && hupai.equals(MajiangPai.santong)) ||
                    (shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.qitong) && hupai.equals(MajiangPai.qitong)) ||
                    (shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.santiao) && hupai.equals(MajiangPai.santiao)) ||
                    (shoupaiShunziZu.getShunzi().getPai3().equals(MajiangPai.qitiao) && hupai.equals(MajiangPai.qitiao))) {
                if (onlyOneHu) {
                    return true;
                }
            }
            if (shoupaiShunziZu.getShunzi().getPai2().equals(hupai) && onlyOneHu) {
                return true;//胡卡
            }
        }
        return false;
    }

    private static int isSiheCount(ShoupaiPaiXing shoupaiPaiXing, MajiangPlayer player) {
        int siheCount = 0;
        List<ShoupaiShunziZu> shunziList = shoupaiPaiXing.getShunziList();
        for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
            MajiangPai keziPai = shoupaiKeziZu.getKezi().getPaiType();
            for (ShoupaiShunziZu shoupaiShunziZu : shunziList) {
                if (keziPai.equals(shoupaiShunziZu.getShunzi().getPai1()) || keziPai.equals(shoupaiShunziZu.getShunzi().getPai2()) || keziPai.equals(shoupaiShunziZu.getShunzi().getPai3())) {
                    siheCount++;
                }
            }
        }
        for (PengchuPaiZu pengchuPaiZu : player.getPengchupaiZuList()) {
            for (ShoupaiShunziZu shoupaiShunziZu : shunziList) {
                if (pengchuPaiZu.getKezi().getPaiType().equals(shoupaiShunziZu.getShunzi().getPai1())
                        || pengchuPaiZu.getKezi().getPaiType().equals(shoupaiShunziZu.getShunzi().getPai2())
                        || pengchuPaiZu.getKezi().getPaiType().equals(shoupaiShunziZu.getShunzi().getPai3())) {
                    siheCount++;
                }
            }
        }

        return siheCount;
    }

    private static int isWutongCount(int[] xushuPaiArr) {
        int wutongScore = 0;
        for (int count : xushuPaiArr) {
            if (count >= 5) {
                wutongScore += count;
            }
        }
        return wutongScore;
    }

    private static int isShuangwutongCount(int[] xushuPaiArr) {
        int wutongCount = 0;
        int shuangwutongScore = 0;
        for (int count : xushuPaiArr) {
            if (count >= 5) {
                wutongCount++;
                shuangwutongScore += count;
            }
        }
        if (wutongCount >= 2) {
            return shuangwutongScore;
        } else {
            return 0;
        }
    }

    private static int isLiulianCount(ShoupaiPaiXing shoupaiPaiXing) {
        int liulianCount = 0;
        List<int[]> shunziArrList = new ArrayList<>();
        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
            Shunzi shunzi = shoupaiShunziZu.getShunzi();
            int[] arr = new int[4];
            arr[0] = shunzi.getPai1().ordinal();
            arr[1] = shunzi.getPai2().ordinal();
            arr[2] = shunzi.getPai3().ordinal();
            arr[3] = 0;
            shunziArrList.add(arr);
        }

        for (int i = 0; i < shunziArrList.size(); i++) {
            int[] ints = shunziArrList.get(i);
            for (int j = i + 1; j < shunziArrList.size(); j++) {
                int[] ints2 = shunziArrList.get(j);
                if ((ints[0] - 1 != 8 && ints[0] - 1 != 17 && ints[0] - 1 != 23) || (ints[2] + 1 != 9 && ints[2] + 1 != 18)) {
                    if (ints[0] - 1 == ints2[2] || ints[2] + 1 == ints2[0]) {
                        if (ints[3] == 0 && ints2[3] == 0) {
                            ints[3] = -1;
                            ints2[3] = -1;
                            liulianCount++;
                        }
                    }
                }
            }
        }
        return liulianCount;
    }

    private static int isLiulianCount2(ShoupaiPaiXing shoupaiPaiXing) {
        List<MajiangPai> playerPaiList = new ArrayList<>();
        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
            Shunzi shunzi = shoupaiShunziZu.getShunzi();
            playerPaiList.add(shunzi.getPai1());
            playerPaiList.add(shunzi.getPai2());
            playerPaiList.add(shunzi.getPai3());
        }

        int liulianCount = 0;
        int[] wanArr = new int[9];
        int[] tongArr = new int[9];
        int[] tiaoArr = new int[9];
        for (MajiangPai majiangPai : playerPaiList) {
            if (majiangPai.ordinal() <= 8) {
                wanArr[majiangPai.ordinal()]++;
            } else if (majiangPai.ordinal() <= 17) {
                tongArr[majiangPai.ordinal() - 9]++;
            } else if (majiangPai.ordinal() <= 26) {
                tiaoArr[majiangPai.ordinal() - 18]++;
            }
        }
        for (int i = 0; i < 4; i++) {
            int lian = 0;
            for (int j = i; j < wanArr.length; j++) {
                if (wanArr[j] > 0) {
                    lian++;
                    wanArr[j]--;
                } else {
                    break;
                }
            }
            if (lian >= 6) {
                liulianCount++;
            }
        }
        for (int i = 0; i < 4; i++) {
            int lian = 0;
            for (int j = i; j < tongArr.length; j++) {
                if (tongArr[j] > 0) {
                    lian++;
                    tongArr[j]--;
                } else {
                    break;
                }
            }
            if (lian >= 6) {
                liulianCount++;
            }
        }
        for (int i = 0; i < 4; i++) {
            int lian = 0;
            for (int j = i; j < tiaoArr.length; j++) {
                if (tiaoArr[j] > 0) {
                    lian++;
                    tiaoArr[j]--;
                } else {
                    break;
                }
            }
            if (lian >= 6) {
                liulianCount++;
            }
        }
        return liulianCount;
    }

    private static int[] playerXushuPai(List<MajiangPai> playerPaiList) {
        int[] paiarr = new int[9];
        for (MajiangPai majiangPai : playerPaiList) {
            if (majiangPai.ordinal() <= 8) {
                paiarr[majiangPai.ordinal()]++;
            } else if (majiangPai.ordinal() <= 17) {
                paiarr[majiangPai.ordinal() - 9]++;
            } else if (majiangPai.ordinal() <= 26) {
                paiarr[majiangPai.ordinal() - 18]++;
            }
        }
        return paiarr;
    }

    private static int isBazhi(List<MajiangPai> playerPaiList) {
        int wanCount = 0, tongCount = 0, tiaoCount = 0, zipaiCount = 0;
        for (MajiangPai majiangPai : playerPaiList) {
            XushupaiCategory categoryforXushupai = XushupaiCategory.getCategoryforXushupai(majiangPai);
            if (categoryforXushupai != null) {
                switch (categoryforXushupai) {
                    case wan:
                        wanCount++;
                        break;
                    case tong:
                        tongCount++;
                        break;
                    case tiao:
                        tiaoCount++;
                        break;
                    case zipai:
                        zipaiCount++;
                        break;
                }
            }
        }
        if (wanCount >= 8) {
            return wanCount - 3;
        }
        if (tongCount >= 8) {
            return tongCount - 3;
        }
        if (tiaoCount >= 8) {
            return tiaoCount - 3;
        }
        if (zipaiCount >= 8) {
            return zipaiCount - 3;
        }
        return 0;
    }

    private static boolean isShuangbazhi(List<MajiangPai> playerPaiList) {
        int wanCount = 0, tongCount = 0, tiaoCount = 0, zipaiCount = 0;
        int bazhiCount = 0;
        for (MajiangPai majiangPai : playerPaiList) {
            XushupaiCategory categoryforXushupai = XushupaiCategory.getCategoryforXushupai(majiangPai);
            if (categoryforXushupai != null) {
                switch (categoryforXushupai) {
                    case wan:
                        wanCount++;
                        break;
                    case tong:
                        tongCount++;
                        break;
                    case tiao:
                        tiaoCount++;
                        break;
                    case zipai:
                        zipaiCount++;
                        break;
                }
            }
        }
        if (wanCount >= 8) {
            bazhiCount++;
        }
        if (tongCount >= 8) {
            bazhiCount++;
        }
        if (tiaoCount >= 8) {
            bazhiCount++;
        }
        if (zipaiCount >= 8) {
            bazhiCount++;
        }
        return bazhiCount >= 2;
    }

    /**
     * 玩家所有牌 包括手牌、碰出牌、杠出牌
     */
    private static List<MajiangPai> buildPlayerAllPai(ShoupaiPaiXing shoupaiPaiXing, MajiangPlayer player) {
        List<MajiangPai> playerPai = new ArrayList<>();
//        for (ShoupaiDuiziZu shoupaiDuiziZu : shoupaiPaiXing.getDuiziList()) {
//            playerPai.add(shoupaiDuiziZu.getDuiziType());
//            playerPai.add(shoupaiDuiziZu.getDuiziType());
//        }
//        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
//            playerPai.add(shoupaiShunziZu.getShunzi().getPai1());
//            playerPai.add(shoupaiShunziZu.getShunzi().getPai2());
//            playerPai.add(shoupaiShunziZu.getShunzi().getPai3());
//        }
//        for (ShoupaiKeziZu shoupaiKeziZu : shoupaiPaiXing.getKeziList()) {
//            playerPai.add(shoupaiKeziZu.getKezi().getPaiType());
//            playerPai.add(shoupaiKeziZu.getKezi().getPaiType());
//            playerPai.add(shoupaiKeziZu.getKezi().getPaiType());
//        }
//        for (ShoupaiGangziZu shoupaiGangziZu : shoupaiPaiXing.getGangziList()) {
//            playerPai.add(shoupaiGangziZu.getGangzi().getPaiType());
//            playerPai.add(shoupaiGangziZu.getGangzi().getPaiType());
//            playerPai.add(shoupaiGangziZu.getGangzi().getPaiType());
//            playerPai.add(shoupaiGangziZu.getGangzi().getPaiType());
//        }
        playerPai.addAll(player.getFangruShoupaiList());
        if (player.getGangmoShoupai() != null) {
            playerPai.add(player.getGangmoShoupai());
        }
        for (PengchuPaiZu pengchuPaiZu : player.getPengchupaiZuList()) {
            playerPai.add(pengchuPaiZu.getKezi().getPaiType());
            playerPai.add(pengchuPaiZu.getKezi().getPaiType());
            playerPai.add(pengchuPaiZu.getKezi().getPaiType());
        }
        for (GangchuPaiZu gangchuPaiZu : player.getGangchupaiZuList()) {
            playerPai.add(gangchuPaiZu.getGangzi().getPaiType());
            playerPai.add(gangchuPaiZu.getGangzi().getPaiType());
            playerPai.add(gangchuPaiZu.getGangzi().getPaiType());
            playerPai.add(gangchuPaiZu.getGangzi().getPaiType());
        }
        return playerPai;
    }

//    /**
//     * 是否胡单吊
//     *
//     * @param shoupaixingWuguanJiesuancanshu 手牌型无关结算
//     * @param shoupaiPaiXing                 手牌牌型
//     * @param player                         玩家
//     */
//    private static boolean isDandiao(ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu, ShoupaiPaiXing shoupaiPaiXing, MajiangPlayer player, List<MajiangPai> kehuList) {
//        if (kehuList.size() == 1) {
//            MajiangPai hupai = shoupaixingWuguanJiesuancanshu.getHupai();
//            List<ShoupaiDuiziZu> duiziList = shoupaiPaiXing.getDuiziList();
//            for (ShoupaiDuiziZu duiziZu : duiziList) {
//                if (duiziZu.getDuiziType().equals(hupai)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 胡的牌被碰过或者已经打出去3张
     */
    private static boolean isKuyazhi(ShoupaixingWuguanJiesuancanshu shoupaixingWuguanJiesuancanshu, Pan currentPan, boolean zimoHu) {
        MajiangPai hupai = shoupaixingWuguanJiesuancanshu.getHupai();
        long dachuCount = 0;
        Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap = currentPan.getMajiangPlayerIdMajiangPlayerMap();
        for (MajiangPlayer player : majiangPlayerIdMajiangPlayerMap.values()) {
            for (PengchuPaiZu pengchuPaiZu : player.getPengchupaiZuList()) {
                if (pengchuPaiZu.getKezi().getPaiType().equals(hupai)) {
                    return true;//胡的这张牌已经碰过
                }
            }
            dachuCount += player.getDachupaiList().stream().filter(hupai::equals).count();
        }

        if (zimoHu) {
            return dachuCount == 3;
        } else {
            return dachuCount == 4;
        }

    }

    /**
     * 一条龙牌型判断
     *
     * @param shoupaiPaiXing 手牌牌型
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
     * 其实点炮,抢杠胡,也包含自摸的意思，也调用这个
     *
     * @param guipaiList        鬼牌集合
     * @param shoupaiCalculator 玩家手牌
     * @param player            麻将玩家
     * @param gouXingPanHu      可胡牌牌型
     * @param huPai             胡牌的牌
     */
    private static List<ShoupaiPaiXing> calculateZimoHuPaiShoupaiPaiXingList(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        if (!guipaiList.isEmpty()) { //有鬼牌
            return calculateHuPaiShoupaiPaiXingListWithCaishen(guipaiList, shoupaiCalculator, player, gouXingPanHu, huPai);
        } else { //没鬼牌
            return calculateHuPaiShoupaiPaiXingListWithoutCaishen(shoupaiCalculator, player, gouXingPanHu, huPai);
        }
    }

    private static List<ShoupaiPaiXing> calculateBuhuShoupaiPaiXingList(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player) {
        if (!guipaiList.isEmpty()) {// 有财神
            return calculateBuhuShoupaiPaiXingListWithCaishen(guipaiList, shoupaiCalculator, player);
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

//                    ShoupaiPaiXing huShoupaiPaiXing = null;
//                    for (ShoupaiPaiXing huShouPaiXing : shoupaiPaiXingListWithDifftentLastActionPaiInZu) {
//                        List<ShoupaiShunziZu> shunziList = huShouPaiXing.getShunziList();
//                        for (ShoupaiShunziZu shoupaiShunziZu : shunziList) {
//                            if (shoupaiShunziZu.getPai2().isLastActionPai()) {
//                                huShoupaiPaiXing = huShouPaiXing;
//                                break;
//                            }
//                        }
//                    }
//                    if (huShoupaiPaiXing != null) {
//                        huPaiShoupaiPaiXingList.add(huShoupaiPaiXing);
//                    }

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
        MajiangPai[] paiTypesForGuipaiAct = calculatePaiTypesForGuipaiAct(); //鬼牌可以扮演的牌类
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

    private static List<ShoupaiPaiXing> calculateBuhuShoupaiPaiXingListWithCaishen(List<MajiangPai> guipaiList, ShoupaiCalculator shoupaiCalculator, MajiangPlayer player) {
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
     */
    private static MajiangPai[] calculatePaiTypesForGuipaiAct() {
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiAndZipaiArray();
        MajiangPai[] paiTypesForGuipaiAct;
        paiTypesForGuipaiAct = new MajiangPai[xushupaiArray.length];
        System.arraycopy(xushupaiArray, 0, paiTypesForGuipaiAct, 0, xushupaiArray.length);
        return paiTypesForGuipaiAct;
    }

    /**
     * 鬼牌可当牌牌型
     * 只包含手中的牌以及前一张和后一张牌型
     *
     * @param player 玩家
     */
    private static MajiangPai[] calculatePaiTypesForGuipaiAct(MajiangPlayer player) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        int[] paiQuantityArray = shoupaiCalculator.getPaiQuantityArray();
        if (player.getGangmoShoupai() != null) {
            shoupaiCalculator.addPai(player.getGangmoShoupai());
        }
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiAndZipaiArray();
        Set<MajiangPai> set = new HashSet<>();
        for (int i = 0; i < xushupaiArray.length; i++) {
            if (paiQuantityArray[i] != 0) {
                if (i != 0 && i != 9 && i != 18 && i != 27) {
                    set.add(xushupaiArray[i - 1]);
                }
                set.add(xushupaiArray[i]);
                if (i != 8 && i != 17 && i != 26 && i != 33) {
                    set.add(xushupaiArray[i + 1]);
                }
            }
        }
        MajiangPai[] paiTypesForGuipaiAct = new MajiangPai[set.size()];
        set.toArray(paiTypesForGuipaiAct);
        if (player.getGangmoShoupai() != null) {
            shoupaiCalculator.removePai(player.getGangmoShoupai());
        }
        return paiTypesForGuipaiAct;
    }

    /**
     * 计算有鬼牌可以胡的牌型
     *
     * @param guipaiList           玩家鬼牌集合
     * @param paiTypesForGuipaiAct 鬼牌可以替代的牌型
     * @param shoupaiCalculator    玩家手牌计算器
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

    public static boolean calculateHu(GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangPai hupai) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.getFangruGuipaiList();// TODO 也可以用统计器做
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, hupai);
        return !huPaiShoupaiPaiXingList.isEmpty();
    }

    public static List<ShoupaiPaiXing> calculateHuPaixing(GouXingPanHu gouXingPanHu, MajiangPlayer player, MajiangPai hupai) {
        ShoupaiCalculator shoupaiCalculator = player.getShoupaiCalculator();
        List<MajiangPai> guipaiList = player.getFangruGuipaiList();// TODO 也可以用统计器做
        return calculateZimoHuPaiShoupaiPaiXingList(guipaiList, shoupaiCalculator, player, gouXingPanHu, hupai);
    }

    private static boolean generateKehuKa(ShoupaiPaiXing shoupaiPaiXing, MajiangPai pai) {
        for (ShoupaiShunziZu shoupaiShunziZu : shoupaiPaiXing.getShunziList()) {
            if (shoupaiShunziZu.getShunzi().getPai2().equals(pai)) {
                if ((pai.ordinal() >= 2 && pai.ordinal() <= 7) || (pai.ordinal() >= 11 && pai.ordinal() <= 16) || (pai.ordinal() >= 20 && pai.ordinal() <= 25)) {
                    return true;
                }
            }
        }
        return false;
    }

}
