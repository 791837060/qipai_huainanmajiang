package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.test;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.*;
import com.anbang.qipai.maanshanmajiang.utils.BigDecimalUtil;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.pai.fenzu.Gangzi;
import com.dml.majiang.pai.fenzu.Kezi;
import com.dml.majiang.pai.fenzu.Shunzi;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.mo.LundaoMopai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.chupaizu.GangchuPaiZu;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;
import com.dml.majiang.player.shoupai.ShoupaiShunziZu;
import com.dml.majiang.player.shoupai.gouxing.GouXingCalculator;
import com.dml.majiang.player.shoupai.gouxing.GouXingCalculatorHelper;
import com.dml.majiang.player.shoupaisort.MajiangPaiOrderShoupaiSortComparator;

import java.math.BigDecimal;
import java.util.*;


public class MyTest {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {

        MajiangPai[] allMajiangPaiArray = MajiangPai.values();
        List<MajiangPai> playPaiTypeList = new ArrayList<>(Arrays.asList(allMajiangPaiArray));
        List<MajiangPai> allPaiList = new ArrayList<>();
        playPaiTypeList.forEach((paiType) -> {
            if (paiType.ordinal() >= MajiangPai.chun.ordinal()) {
                for (int i = 0; i < 1; i++) {
                    allPaiList.add(paiType);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    allPaiList.add(paiType);
                }
            }
        });

        MaanshanMajiangGouXingPanHu gouXingPanHu = new MaanshanMajiangGouXingPanHu();

        MajiangPlayer majiangPlayer = new MajiangPlayer();

        majiangPlayer.addShoupai(MajiangPai.yitong);
        majiangPlayer.addShoupai(MajiangPai.yitong);
        majiangPlayer.addShoupai(MajiangPai.yitong);
        majiangPlayer.addShoupai(MajiangPai.yitong);
        majiangPlayer.addShoupai(MajiangPai.ertong);
        majiangPlayer.addShoupai(MajiangPai.ertong);
        majiangPlayer.addShoupai(MajiangPai.ertong);
        majiangPlayer.addShoupai(MajiangPai.ertong);
        majiangPlayer.addShoupai(MajiangPai.santong);
        majiangPlayer.addShoupai(MajiangPai.santong);
        majiangPlayer.addShoupai(MajiangPai.santong);
        majiangPlayer.addShoupai(MajiangPai.santong);
        majiangPlayer.addShoupai(MajiangPai.dongfeng);

        majiangPlayer.setGangmoShoupai(MajiangPai.dongfeng);


//        PengchuPaiZu pengchuPaiZu = new PengchuPaiZu();
//        Kezi kezi = new Kezi();
//        kezi.setPaiType(MajiangPai.sitong);
//        pengchuPaiZu.setKezi(kezi);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu);

//        PengchuPaiZu pengchuPaiZu2 = new PengchuPaiZu();
//        Kezi kezi2 = new Kezi();
//        kezi2.setPaiType(MajiangPai.santong);
//        pengchuPaiZu2.setKezi(kezi2);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu2);

//        PengchuPaiZu pengchuPaiZu3 = new PengchuPaiZu();
//        Kezi kezi3 = new Kezi();
//        kezi3.setPaiType(MajiangPai.qiwan);
//        pengchuPaiZu3.setKezi(kezi3);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu3);

//        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu();
//        Gangzi gangzi = new Gangzi();
//        gangzi.setPaiType(MajiangPai.dongfeng);
//        gangchuPaiZu.setGangzi(gangzi);
//        gangchuPaiZu.setGangType(GangType.gangdachu);
//        majiangPlayer.getGangchupaiZuList().add(gangchuPaiZu);

//        GangchuPaiZu gangchuPaiZu2 = new GangchuPaiZu();
//        Gangzi gangzi2 = new Gangzi();
//        gangzi2.setPaiType(MajiangPai.sanwan);
//        gangchuPaiZu2.setGangzi(gangzi2);
//        gangchuPaiZu2.setGangType(GangType.shoupaigangmo);
//        majiangPlayer.getGangchupaiZuList().add(gangchuPaiZu2);

//        GangchuPaiZu gangchuPaiZu3 = new GangchuPaiZu();
//        Gangzi gangzi3 = new Gangzi();
//        gangzi3.setPaiType(MajiangPai.yitong);
//        gangchuPaiZu3.setGangzi(gangzi3);
//        gangchuPaiZu3.setGangType(GangType.shoupaigangmo);
//        majiangPlayer.getGangchupaiZuList().add(gangchuPaiZu3);

//        GangchuPaiZu gangchuPaiZu4 = new GangchuPaiZu();
//        Gangzi gangzi4 = new Gangzi();
//        gangzi4.setPaiType(MajiangPai.santong);
//        gangchuPaiZu4.setGangzi(gangzi4);
//        gangchuPaiZu4.setGangType(GangType.shoupaigangmo);
//        majiangPlayer.getGangchupaiZuList().add(gangchuPaiZu4);

        majiangPlayer.getDachupaiList().add(MajiangPai.yiwan);
        majiangPlayer.getDachupaiList().add(MajiangPai.yiwan);
        majiangPlayer.getDachupaiList().add(MajiangPai.yiwan);
        majiangPlayer.getDachupaiList().add(MajiangPai.yiwan);

        GouXingCalculatorHelper.gouXingCalculator = new GouXingCalculator(14, 0);//最大手牌数 最大鬼牌数
        MajiangMoAction majiangMoAction = new MajiangMoAction();
        majiangMoAction.setReason(new LundaoMopai());
//        majiangMoAction.setReason(new MaanshanMajiangBupai());

        OptionalPlay optionalPlay = new OptionalPlay();

        MaanshanMajiangHuPaiSolutionsTipsFilter huPaiSolutionsTipsFilter = new MaanshanMajiangHuPaiSolutionsTipsFilter();
        huPaiSolutionsTipsFilter.setOptionalPlay(optionalPlay);

        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = huPaiSolutionsTipsFilter.hupaiFilter(majiangPlayer, gouXingPanHu);
        majiangPlayer.setHupaiCandidates(majiangPaiListMap);

//        MajiangPai guipai = null;
//        if (majiangPlayer.gangmoGuipai()) {
//            guipai = majiangPlayer.getFangruGuipaiList().remove(majiangPlayer.getFangruGuipaiList().size() - 1);
//        }
//        List<MajiangPai> majiangPais = huPaiSolutionsTipsFilter.kehuFilter(majiangPlayer, gouXingPanHu);
//        if (majiangPlayer.gangmoGuipai()) {
//            majiangPlayer.getFangruGuipaiList().add(guipai);
//        }

//        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = huPaiSolutionsTipsFilter.pengHupaiFilter(majiangPlayer, gouXingPanHu);

        Pan pan = new Pan();
        pan.setAvaliablePaiList(allPaiList);
        pan.getMajiangPlayerIdMajiangPlayerMap().put("A", majiangPlayer);
        MajiangPlayer majiangPlayer2 = new MajiangPlayer();
        pan.getMajiangPlayerIdMajiangPlayerMap().put("B", majiangPlayer2);

        PengchuPaiZu pengchuPaiZu2 = new PengchuPaiZu();
        Kezi kezi2 = new Kezi();
        kezi2.setPaiType(MajiangPai.santong);
        pengchuPaiZu2.setKezi(kezi2);
        majiangPlayer2.getPengchupaiZuList().add(pengchuPaiZu2);

//        List<MajiangPai> avaliablelaiList = new ArrayList<>();
//        avaliablelaiList.add(MajiangPai.yiwan);
//        avaliablelaiList.add(MajiangPai.yiwan);
//        avaliablelaiList.add(MajiangPai.yiwan);
//        avaliablelaiList.add(MajiangPai.yiwan);
//        avaliablelaiList.add(MajiangPai.erwan);

//        pan.setAvaliablePaiList(avaliablelaiList);
//        pan.getMajiangPlayerIdMajiangPlayerMap().put("A", majiangPlayer);
//        pan.getMajiangPlayerIdMajiangPlayerMap().put("B", new MajiangPlayer());

//        majiangPlayer.setHupaiCandidates(majiangPaiListMap);

        MaanshanMajiangHu hu = MaanshanMajiangJiesuanCalculator.calculateBestZimoHu(false, gouXingPanHu, majiangPlayer, majiangMoAction, optionalPlay, pan);

//        majiangPlayer.getShoupaiCalculator().addPai(MajiangPai.nanfeng);
//        MaanshanMajiangHu hu = MaanshanMajiangJiesuanCalculator.calculateBestDianpaoHu(false, gouXingPanHu, majiangPlayer, MajiangPai.nanfeng, optionalPlay, pan);
//        majiangPlayer.getShoupaiCalculator().removePai(MajiangPai.nanfeng);

        assert hu != null;
        System.out.println("结束！" + "|" + hu.getHufen().getValue());
    }

    public static void test2() {
        Double d1 = 0.1;
        Double d2 = 0.2;
        Double d3 = d1 + d2;
        System.out.println(d1 + d2);
        System.out.println(d3);

        double add = BigDecimalUtil.add(d1, d2);
        System.out.println(add);

        double d4 = 0.01;
        double d5 = 0.02;
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(d4));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(d5));
        double v = bigDecimal1.add(bigDecimal2).doubleValue();
        System.out.println(v);

    }

    public static void test3() {
        MajiangPai[] values = MajiangPai.values();
        for (MajiangPai value : values) {
            System.out.println(value.name() + "|" + value.ordinal());
        }
    }

}
