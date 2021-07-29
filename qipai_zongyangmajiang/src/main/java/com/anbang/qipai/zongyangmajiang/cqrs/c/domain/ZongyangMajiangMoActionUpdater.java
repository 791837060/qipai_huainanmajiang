package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.anbang.qipai.zongyangmajiang.utils.SpringUtil;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.da.MajiangDaAction;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.listener.gang.GuoGangBuGangStatisticsListener;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.MajiangPlayerMoActionUpdater;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZongyangMajiangMoActionUpdater implements MajiangPlayerMoActionUpdater {
    @Override
    public void updateActions(MajiangMoAction moAction, Ju ju) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer player = currentPan.findPlayerById(moAction.getActionPlayerId());
        currentPan.clearAllPlayersActionCandidates(); //清理所有玩家的动作缓存

        List<MajiangPai> fangruShoupaiList = player.getFangruShoupaiList(); //手牌集合
        Set<MajiangPai> guipaiTypeSet = player.getGuipaiTypeSet();          //鬼牌类型
        MajiangPai gangmoShoupai = player.getGangmoShoupai();               //刚没入手牌
        if (gangmoShoupai.ordinal() >= MajiangPai.chun.ordinal()) {
            //花牌补花
            player.addActionCandidate(new MajiangMoAction(player.getId(), new ZongyangMajiangBupai()));
            return;
        }
        GuoGangBuGangStatisticsListener guoGangBuGangStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoGangBuGangStatisticsListener.class);//过杠不杠检测器
        Set<String> canNotGangPlayers = guoGangBuGangStatisticsListener.getCanNotGangPlayers(); //过杠不杠玩家集合

        player.tryShoupaigangmoAndGenerateCandidateAction();       //暗杠
        if (!canNotGangPlayers.contains(player.getId())) {
            player.tryKezigangmoAndGenerateCandidateAction();      //明杠
            player.tryKezigangshoupaiAndGenerateCandidateAction(); //明杠
        }
        player.normalTryGangsigeshoupaiAndGenerateCandidateAction();     //杠四个手牌

        GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();          //可胡牌型
        boolean couldTianhu = false;
        ZongyangMajiangPanResultBuilder tuidaohuPanResultBuilder = (ZongyangMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
        OptionalPlay optionalPlay = tuidaohuPanResultBuilder.getOptionalPlay();
        int guipaiCount = player.countGuipai();
        if (guipaiCount == 4) {
            ZongyangMajiangHushu hufen = new ZongyangMajiangHushu();
            hufen.setZimoHu(true);
            hufen.setHu(true);
            hufen.calculate(player.getPublicPaiList().size(),player);
            ZongyangMajiangHuHu sancaishenHu = new ZongyangMajiangHuHu(hufen);
            sancaishenHu.setZimo(true);
            player.addActionCandidate(new MajiangHuAction(player.getId(), sancaishenHu));
        }else {
            ZongyangMajiangHuHu bestHu = ZongyangMajiangJiesuanCalculator.calculateBestZimoHu(gouXingPanHu, player, moAction,optionalPlay,player.getGangmoShoupai());//计算时候胡牌
            if (bestHu != null) {
                bestHu.setZimo(true);
                player.addActionCandidate(new MajiangHuAction(player.getId(), bestHu));
            }else {
                //非胡牌型特殊胡---十三烂
                boolean shisanlan = ZongyangMajiangJiesuanCalculator.isshisanlan(player,gangmoShoupai);
                if (shisanlan){
                    ZongyangMajiangHushu hufen = new ZongyangMajiangHushu();
                    hufen.setHu(true);
                    hufen.setShisanlan(true);
                    hufen.calculate(player.getPublicPaiList().size(),player);
                    ZongyangMajiangHuHu zongyangMajiangHuHu = new ZongyangMajiangHuHu(hufen);
                    zongyangMajiangHuHu.setDianpao(true);
                    zongyangMajiangHuHu.setDianpaoPlayerId(player.getId());
                    player.addActionCandidate(new MajiangHuAction(player.getId(), zongyangMajiangHuHu));
                }
            }
        }


        if (guipaiTypeSet.contains(gangmoShoupai) && fangruShoupaiList.size() == 0) { //当手上没有除鬼牌之外的牌时不能过

        } else {
            player.checkAndGenerateGuoCandidateAction(); //需要有“过”
        }
        if (player.getActionCandidates().isEmpty()) { //啥也不能干，那只能打出牌
            player.generateDaActions();
        }
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        if (depositPlayerList.containsKey(player.getId())) {    //如果下家离线自动出牌
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                autoDapai(player, depositPlayerList);
            });
        }
    }
    private final Automatic automatic = SpringUtil.getBean(Automatic.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void autoDapai(MajiangPlayer player, Map<String, String> playerGameHostingList) {
        Map<Integer, MajiangPlayerAction> actionCandidates = player.getActionCandidates();
        if (actionCandidates.size() > 0) {
            MajiangPlayerAction action = actionCandidates.get(1);
            if (!(action instanceof MajiangDaAction) && !(action instanceof MajiangHuAction)) {
                player.clearActionCandidates();
                player.generateDaActions();
            }
            String gameId = playerGameHostingList.get(player.getId());
            automatic.automaticAction(player.getId(), 1, gameId);
        }

    }

}