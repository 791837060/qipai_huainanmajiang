package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.utils.SpringUtil;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.XushupaiCategory;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.da.MajiangDaAction;
import com.dml.majiang.player.action.da.MajiangPlayerDaActionUpdater;
import com.dml.majiang.player.action.guo.MajiangGuoAction;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.listener.comprehensive.GuoPengBuPengStatisticsListener;
import com.dml.majiang.player.action.mo.LundaoMopai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.mo.TuoguanMopai;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一炮多响的打
 *
 * @author lsc
 */
public class MaanshanMajiangDaActionUpdater implements MajiangPlayerDaActionUpdater {

    @Override
    public void updateActions(MajiangDaAction daAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer daPlayer = currentPan.findPlayerById(daAction.getActionPlayerId());

        daPlayer.clearActionCandidates();                               //清理打牌玩家动作缓存
        MajiangPlayer xiajiaPlayer = currentPan.findXiajia(daPlayer);   //打牌玩家的下家
        xiajiaPlayer.clearActionCandidates();                           //清理打牌玩家下家动作缓存

        GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();

        //下家吃牌动作
        //xiajiaPlayer.tryChiAndGenerateCandidateActions(daAction.getActionPlayerId(), daAction.getPai());

//        GuoHuBuHuStatisticsListener guoHuBuHuStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoHuBuHuStatisticsListener.class);                //过胡不胡
//        Set<String> canNotHuPlayers = guoHuBuHuStatisticsListener.getCanNotHuPlayers();                                                                                   //过胡不胡玩家集合
//        GuoPengBuPengStatisticsListener guoPengBuPengStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoPengBuPengStatisticsListener.class);    //过碰不碰
//        Map<String, List<MajiangPai>> canNotPengPlayersPaiMap = guoPengBuPengStatisticsListener.getCanNotPengPlayersPaiMap();                                             //过碰不碰玩家列表

        MaanshanMajiangPanResultBuilder maanshanMajiangPanResultBuilder = (MaanshanMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
        OptionalPlay optionalPlay = maanshanMajiangPanResultBuilder.getOptionalPlay();

        while (true) {
            if (!xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) { //从打牌玩家开始向依次下家判断是否可以碰杠牌玩家打的牌
                // 其他的可以碰杠胡
//                List<MajiangPai> fangruShoupaiList = xiajiaPlayer.getFangruShoupaiList(); //下家玩家手牌
                if (!xiajiaPlayer.getQuemen().equals(XushupaiCategory.getCategoryforXushupai(daAction.getPai()))) {
                    xiajiaPlayer.tingNotTryPengAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());//碰牌
                    xiajiaPlayer.normalTryGangdachuAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());//杠牌
                }
                //先把这张牌放入计算器
                xiajiaPlayer.getShoupaiCalculator().addPai(daAction.getPai());
                MaanshanMajiangHu bestHu = MaanshanMajiangJiesuanCalculator.calculateBestDianpaoHu(false, gouXingPanHu, xiajiaPlayer, daAction.getPai(), optionalPlay, currentPan);
                if (optionalPlay.isZimohupai() && bestHu != null) {
                    MaanshanMajiangHushu hufen = bestHu.getHufen();
                    if (!hufen.isKuyazhi() && !hufen.isQingyise() && !hufen.isFengyise()) {
                        bestHu = null;
                    }
                }
                //再把这张牌拿出计算器
                xiajiaPlayer.getShoupaiCalculator().removePai(daAction.getPai());
                if (bestHu != null) {
                    bestHu.setDianpao(true);
                    bestHu.setDianpaoPlayerId(daPlayer.getId());
                    xiajiaPlayer.addActionCandidate(new MajiangHuAction(xiajiaPlayer.getId(), bestHu));
                }
                xiajiaPlayer.checkAndGenerateGuoCandidateAction();
            } else {
                break;
            }
            xiajiaPlayer = currentPan.findXiajia(xiajiaPlayer);
            xiajiaPlayer.clearActionCandidates();
        }
        currentPan.disablePlayerActionsByHuPengGangChiPriority(); //吃碰杠胡优先级判断

        // 如果所有玩家啥也做不了,那就下家摸牌
        if (currentPan.allPlayerHasNoActionCandidates()) {
            xiajiaPlayer = currentPan.findXiajia(daPlayer);
            xiajiaPlayer.addActionCandidate(new MajiangMoAction(xiajiaPlayer.getId(), new LundaoMopai()));//摸牌
        }

        //托管
        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        Set<String> tuoguanPlayerId = depositPlayerList.keySet();
        for (String playerId : tuoguanPlayerId) {
            if (playerId.equals(daAction.getActionPlayerId())) {
                continue;
            }
            MajiangPlayer player = currentPan.findPlayerById(playerId);
            Collection<MajiangPlayerAction> values = player.getActionCandidates().values();
            for (MajiangPlayerAction action : values) {
                if (action instanceof MajiangGuoAction) {
                    executorService.submit(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        autoGuopai(player, depositPlayerList);//替托管玩家出牌
                    });
                }
            }
        }
        MajiangPlayer xiajia = currentPan.findXiajia(daPlayer);
        boolean hasPlayerAction = false;
        if (depositPlayerList.containsKey(xiajia.getId()) && !automatic.isPlayerOnLine(xiajia.getId())) {
            for (MajiangPlayer player : currentPan.getMajiangPlayerIdMajiangPlayerMap().values()) {
                if (player.getActionCandidates().size() > 1) {
                    hasPlayerAction = true;
                }
            }
            if (!hasPlayerAction) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    autoMopai(xiajia, depositPlayerList);
                });
            }
        }

    }

    private final Automatic automatic = SpringUtil.getBean(Automatic.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 自动摸牌
     *
     * @param xiajia            下家玩家
     * @param depositPlayerList 托管玩家列表
     */
    public void autoMopai(MajiangPlayer xiajia, Map<String, String> depositPlayerList) {
        xiajia.clearActionCandidates();
        xiajia.addActionCandidate(new MajiangMoAction(xiajia.getId(), new TuoguanMopai()));
        String gameId = depositPlayerList.get(xiajia.getId());
        automatic.automaticAction(xiajia.getId(), 1, gameId);
    }

    /**
     * 自动过牌
     *
     * @param xiajia            下家玩家
     * @param depositPlayerList 托管玩家列表
     */
    public void autoGuopai(MajiangPlayer xiajia, Map<String, String> depositPlayerList) {
        xiajia.clearActionCandidates();
        xiajia.addActionCandidate(new MajiangGuoAction(xiajia.getId()));
        String gameId = depositPlayerList.get(xiajia.getId());
        automatic.automaticAction(xiajia.getId(), 1, gameId);
    }

}
