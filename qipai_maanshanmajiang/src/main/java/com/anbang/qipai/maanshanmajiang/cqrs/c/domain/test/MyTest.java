package com.anbang.qipai.maanshanmajiang.cqrs.c.domain.test;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.*;
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
import com.dml.majiang.player.shoupai.gouxing.GouXingCalculator;
import com.dml.majiang.player.shoupai.gouxing.GouXingCalculatorHelper;
import com.dml.majiang.player.shoupaisort.MajiangPaiOrderShoupaiSortComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MyTest {

    public static void main(String[] args) {
        test7();
    }

    public static void test7() {

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

        majiangPlayer.addShoupai(MajiangPai.erwan);
        majiangPlayer.addShoupai(MajiangPai.sanwan);
        majiangPlayer.addShoupai(MajiangPai.siwan);
        majiangPlayer.addShoupai(MajiangPai.wuwan);
        majiangPlayer.addShoupai(MajiangPai.liuwan);
        majiangPlayer.addShoupai(MajiangPai.qiwan);
        majiangPlayer.addShoupai(MajiangPai.bawan);
        majiangPlayer.addShoupai(MajiangPai.jiuwan);
        majiangPlayer.addShoupai(MajiangPai.baiban);
        majiangPlayer.addShoupai(MajiangPai.baiban);
//        majiangPlayer.addShoupai(MajiangPai.qiwan);
//        majiangPlayer.addShoupai(MajiangPai.yiwan);
//        majiangPlayer.addShoupai(MajiangPai.jiuwan);

//        majiangPlayer.setGangmoShoupai(MajiangPai.jiuwan);

//        ChichuPaiZu chichuPaiZu = new ChichuPaiZu();
//        Shunzi shunzi = new Shunzi();
//        shunzi.setPai1(MajiangPai.yitiao);
//        shunzi.setPai2(MajiangPai.ertiao);
//        shunzi.setPai3(MajiangPai.santiao);
//        chichuPaiZu.setShunzi(shunzi);
//        majiangPlayer.getChichupaiZuList().add(chichuPaiZu);

//        ChichuPaiZu chichuPaiZu2 = new ChichuPaiZu();
//        Shunzi shunzi2 = new Shunzi();
//        shunzi2.setPai1(MajiangPai.sitiao);
//        shunzi2.setPai2(MajiangPai.wutiao);
//        shunzi2.setPai3(MajiangPai.liutiao);
//        chichuPaiZu2.setShunzi(shunzi2);
//        majiangPlayer.getChichupaiZuList().add(chichuPaiZu2);

//        ChichuPaiZu chichuPaiZu3 = new ChichuPaiZu();
//        Shunzi shunzi3 = new Shunzi();
//        shunzi3.setPai1(MajiangPai.qitong);
//        shunzi3.setPai2(MajiangPai.batong);
//        shunzi3.setPai3(MajiangPai.jiutong);
//        chichuPaiZu3.setShunzi(shunzi3);
//        majiangPlayer.getChichupaiZuList().add(chichuPaiZu3);

//        PengchuPaiZu pengchuPaiZu = new PengchuPaiZu();
//        Kezi kezi = new Kezi();
//        kezi.setPaiType(MajiangPai.ertiao);
//        pengchuPaiZu.setKezi(kezi);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu);

//        PengchuPaiZu pengchuPaiZu2 = new PengchuPaiZu();
//        Kezi kezi2 = new Kezi();
//        kezi2.setPaiType(MajiangPai.jiutiao);
//        pengchuPaiZu2.setKezi(kezi2);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu2);

//        PengchuPaiZu pengchuPaiZu3 = new PengchuPaiZu();
//        Kezi kezi3 = new Kezi();
//        kezi3.setPaiType(MajiangPai.qiwan);
//        pengchuPaiZu3.setKezi(kezi3);
//        majiangPlayer.getPengchupaiZuList().add(pengchuPaiZu3);

        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu();
        Gangzi gangzi = new Gangzi();
        gangzi.setPaiType(MajiangPai.dongfeng);
        gangchuPaiZu.setGangzi(gangzi);
        gangchuPaiZu.setGangType(GangType.gangdachu);
        majiangPlayer.getGangchupaiZuList().add(gangchuPaiZu);

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

//        Map<MajiangPai, List<MajiangPai>> majiangPaiListMap = huPaiSolutionsTipsFilter.hupaiFilter(majiangPlayer, gouXingPanHu);

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

//        MaanshanMajiangHu hu = MaanshanMajiangJiesuanCalculator.calculateBestZimoHu(false, gouXingPanHu, majiangPlayer, majiangMoAction, optionalPlay, pan);

        majiangPlayer.getShoupaiCalculator().addPai(MajiangPai.yiwan);
        MaanshanMajiangHu hu = MaanshanMajiangJiesuanCalculator.calculateBestDianpaoHu(false, gouXingPanHu, majiangPlayer, MajiangPai.yiwan, optionalPlay, pan);
        majiangPlayer.getShoupaiCalculator().removePai(MajiangPai.yiwan);

        System.out.println("结束！" + "|" + hu.getHufen().getValue());
    }

    public static void test1() {
//        List<MajiangPai> list=new ArrayList<>();
//
//        list.add(MajiangPai.yiwan);
//        list.add(MajiangPai.wuwan);
//        list.add(MajiangPai.qiwan);
//        list.add(MajiangPai.yitong);
//        list.add(MajiangPai.sanwan);
//        list.add(MajiangPai.qiwan);
//        list.add(MajiangPai.wuwan);
//        list.add(MajiangPai.yiwan);
//        list.add(MajiangPai.yitiao);
//        list.add(MajiangPai.wuwan);
//        list.add(MajiangPai.qiwan);
//        list.add(MajiangPai.yiwan);
//        list.add(MajiangPai.sanwan);
//
//        list.sort(new MaanshanMajiangPaiOrderShoupaiSortComparator());
//
//        System.out.println(1);

    }

}
