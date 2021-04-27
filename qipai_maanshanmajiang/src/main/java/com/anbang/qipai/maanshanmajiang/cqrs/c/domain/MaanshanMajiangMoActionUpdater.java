package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.utils.SpringUtil;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.guo.MajiangGuoAction;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.listener.comprehensive.TianHuAndDihuOpportunityDetector;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.MajiangPlayerMoActionUpdater;
import com.dml.majiang.player.action.mo.QishouMopai;
import com.dml.majiang.player.action.peng.MajiangPengAction;
import com.dml.majiang.player.action.ting.MajiangTingAction;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MaanshanMajiangMoActionUpdater implements MajiangPlayerMoActionUpdater {
    @Override
    public void updateActions(MajiangMoAction moAction, Ju ju) throws Exception {
        if (!moAction.getReason().getName().equals(QishouMopai.name)) {
            MaanshanMajiangPanResultBuilder tianchangxiaohuaPanResultBuilder = (MaanshanMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
            OptionalPlay optionalPlay = tianchangxiaohuaPanResultBuilder.getOptionalPlay();
            GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();//可胡牌型

            Pan currentPan = ju.getCurrentPan();
            MajiangPlayer player = currentPan.findPlayerById(moAction.getActionPlayerId());
            currentPan.clearAllPlayersActionCandidates();//清理所有玩家的动作缓存

//        TianHuAndDihuOpportunityDetector tianHuAndDihuOpportunityDetector = ju.getActionStatisticsListenerManager().findListener(TianHuAndDihuOpportunityDetector.class);//天胡监测器
//        boolean couldTianhu = false;
//        if (currentPan.getZhuangPlayerId().equals(player.getId())) {        //摸牌玩家是庄家
//            if (tianHuAndDihuOpportunityDetector.ifTianhuOpportunity()) {   //是否是天胡
//                couldTianhu = true;
//            }
//        }

            List<MajiangPai> fangruShoupaiList = player.getFangruShoupaiList(); //手牌集合
            Set<MajiangPai> guipaiTypeSet = player.getGuipaiTypeSet();          //鬼牌类型
            MajiangPai gangmoShoupai = player.getGangmoShoupai();               //刚摸入手牌

            Map<String, String> depositPlayerList = ju.getDepositPlayerList();

//        GuoGangBuGangStatisticsListener guoGangBuGangStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoGangBuGangStatisticsListener.class);//过杠不杠检测器
//        Set<String> canNotGangPlayers = guoGangBuGangStatisticsListener.getCanNotGangPlayers(); //过杠不杠玩家集合

            player.normalTryKezigangshoupaiAndGenerateCandidateAction();//明杠(手牌中有和碰出的刻子组成杠)
            player.maanshanMajiangTryGangsigeshoupaiAndGenerateCandidateAction();//暗杠(手牌中有杠)

            if (!player.getQuemen().equals(XushupaiCategory.getCategoryforXushupai(player.getGangmoShoupai()))) {
                player.normalTryShoupaigangmoAndGenerateCandidateAction();  //暗杠(刚摸手牌与手中的牌组成杠)
                player.normalTryKezigangmoAndGenerateCandidateAction();     //明杠(刚摸手牌和碰出的刻子组成杠)
            }

            XushupaiCategory quemen = player.getQuemen();
            boolean hasQuemen = false;
            if (quemen.equals(XushupaiCategory.getCategoryforXushupai(player.getGangmoShoupai()))) {
                hasQuemen = true;
            }
            for (MajiangPai majiangPai : fangruShoupaiList) {
                if (quemen.equals(XushupaiCategory.getCategoryforXushupai(majiangPai))) {
                    hasQuemen = true;
                }
            }

            MaanshanMajiangHu bestHu = MaanshanMajiangJiesuanCalculator.calculateBestZimoHu(false, gouXingPanHu, player, moAction, optionalPlay, currentPan);//计算时候胡牌
            if (bestHu != null && !hasQuemen) {
                bestHu.setZimo(true);
                player.addActionCandidate(new MajiangHuAction(player.getId(), bestHu));
            }


            if (guipaiTypeSet.contains(gangmoShoupai) && fangruShoupaiList.size() == 0) { //当手上没有除鬼牌之外的牌时不能过

            } else {
                player.checkAndGenerateGuoCandidateAction();//需要有“过”
            }

            if (player.getActionCandidates().isEmpty()) { //啥也不能干，那只能打出牌
                player.generateDaQuepaiActions();
                if (player.getActionCandidates().isEmpty()) {
                    player.generateDaActions();
                }
            }

            //托管
            if (depositPlayerList.containsKey(player.getId())) {    //如果玩家离线自动出牌
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
    }

    /**
     * 听牌后是否能暗杠
     *
     * @param ju           局
     * @param gouXingPanHu 胡牌构型
     * @param player       玩家
     */
    private void shoupaiAnGang(Ju ju, GouXingPanHu gouXingPanHu, MajiangPlayer player) {
        long shoupaiHupaiCount = 0;
        if (player.getGangmoShoupai() != null) {
            shoupaiHupaiCount = player.getFangruShoupaiList().stream().filter(player.getGangmoShoupai()::equals).count();
        }
        if (shoupaiHupaiCount == 3) {
            List<MajiangPai> majiangPais = ju.getHupaiPaixingSolutionFilter().kehuFilter(player, gouXingPanHu);
            player.getShoupaiCalculator().removePai(player.getGangmoShoupai(), 3);
            List<MajiangPai> afterList = ju.getHupaiPaixingSolutionFilter().kehuFilter(player, gouXingPanHu);
            boolean allowGang = true;
            for (MajiangPai tingpai : majiangPais) {
                if (!majiangPais.contains(tingpai)) {
                    allowGang = false;
                    break;
                }
            }
            if (majiangPais.size() != afterList.size()) {
                allowGang = false;
            }
            player.getShoupaiCalculator().addPai(player.getGangmoShoupai());
            player.getShoupaiCalculator().addPai(player.getGangmoShoupai());
            player.getShoupaiCalculator().addPai(player.getGangmoShoupai());
            if (allowGang) {
                player.normalTryShoupaigangmoAndGenerateCandidateAction();
            }
        }

    }

    /**
     * 听牌后是否能明杠
     *
     * @param ju           局
     * @param gouXingPanHu 胡牌构型
     * @param player       玩家
     */
    private void shoupaiMingGang(Ju ju, GouXingPanHu gouXingPanHu, MajiangPlayer player) {
        List<MajiangPai> majiangPais = ju.getHupaiPaixingSolutionFilter().kehuFilter(player, gouXingPanHu);
        List<PengchuPaiZu> pengchupaiZuList = player.getPengchupaiZuList();
        for (PengchuPaiZu pengchuPaiZu : pengchupaiZuList) {
            MajiangPai majiangPai = pengchuPaiZu.getKezi().getPaiType();
            if (player.getFangruShoupaiList().contains(majiangPai)) {
                player.getShoupaiCalculator().removePai(majiangPai);
                List<MajiangPai> afterList = ju.getHupaiPaixingSolutionFilter().kehuFilter(player, gouXingPanHu);
                boolean allowGang = true;
                for (MajiangPai tingpai : majiangPais) {
                    if (!majiangPais.contains(tingpai)) {
                        allowGang = false;
                        break;
                    }
                }
                if (majiangPais.size() != afterList.size()) {
                    allowGang = false;
                }
                if (allowGang) {
                    player.normalTryKezigangshoupaiAndGenerateCandidateAction();
                }
                player.getShoupaiCalculator().addPai(majiangPai);
            }
        }
    }

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 自动出牌
     *
     * @param xiajia                下家玩家
     * @param playerGameHostingList 托管玩家集合
     */
    public void autoDapai(MajiangPlayer xiajia, Map<String, String> playerGameHostingList) {
        String gameId = playerGameHostingList.get(xiajia.getId());
        if (xiajia.isTingpai()) {
            boolean gang = false;
            Map<Integer, MajiangPlayerAction> actionCandidates = xiajia.getActionCandidates();
            for (MajiangPlayerAction action : actionCandidates.values()) {
                if (action instanceof MajiangGuoAction || action instanceof MajiangGangAction || action instanceof MajiangPengAction) {
                    gang = true;
                }
            }
            automatic.automaticAction(xiajia.getId(), 1, gameId);
            if (gang) { //听牌离线玩家可能会有杠动作 执行后需要再次摸牌
                executorService.submit(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    automatic.automaticAction(xiajia.getId(), 1, gameId);
                });
            }
        } else {
            xiajia.clearActionCandidates();
            xiajia.generateDaQuepaiActions();
            if (xiajia.getActionCandidates().isEmpty()) {
                xiajia.generateDaActions();
            }
            automatic.automaticAction(xiajia.getId(), 1, gameId);
        }
    }

    /**
     * 补花
     *
     * @param xiajia 下家玩家
     * @param gameId 游戏ID
     */
    public void autoBuhua(MajiangPlayer xiajia, String gameId) {
        automatic.automaticAction(xiajia.getId(), 1, gameId);
    }

    /**
     * 摸牌后补花
     *
     * @param avaliablePaiList 牌库
     * @param player           玩家
     * @param huapai           花牌
     */
    private void moPaiBuHua(List<MajiangPai> avaliablePaiList, MajiangPlayer player, MajiangPai huapai) {
        MajiangPai pai = avaliablePaiList.remove(0);
        if (pai.ordinal() >= huapai.ordinal()) {
            player.addPublicPai(pai);
            moPaiBuHua(avaliablePaiList, player, huapai);
        } else {
            player.addShoupai(pai);
        }
    }

}