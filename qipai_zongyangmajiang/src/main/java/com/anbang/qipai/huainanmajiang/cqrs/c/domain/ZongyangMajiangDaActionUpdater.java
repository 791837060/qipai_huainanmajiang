package com.anbang.qipai.huainanmajiang.cqrs.c.domain;

import com.anbang.qipai.huainanmajiang.utils.SpringUtil;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.da.MajiangDaAction;
import com.dml.majiang.player.action.da.MajiangPlayerDaActionUpdater;
import com.dml.majiang.player.action.guo.MajiangGuoAction;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.listener.comprehensive.GuoHuBuHuStatisticsListener;
import com.dml.majiang.player.action.listener.comprehensive.GuoPengBuPengStatisticsListener;
import com.dml.majiang.player.action.listener.comprehensive.TianHuAndDihuOpportunityDetector;
import com.dml.majiang.player.action.mo.LundaoMopai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一炮多响的打
 *
 * @author lsc
 */
public class ZongyangMajiangDaActionUpdater implements MajiangPlayerDaActionUpdater {

    private boolean dianpao;

    @Override
    public void updateActions(MajiangDaAction daAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer daPlayer = currentPan.findPlayerById(daAction.getActionPlayerId());//出牌麻将玩家

        TianHuAndDihuOpportunityDetector dianpaoDihuOpportunityDetector = ju.getActionStatisticsListenerManager().findListener(TianHuAndDihuOpportunityDetector.class);//天胡地胡监测器
        boolean couldDihu = dianpaoDihuOpportunityDetector.ifDihuOpportunity(); //是否可以地胡

        daPlayer.clearActionCandidates();                               //清理打牌玩家动作缓存
        MajiangPlayer xiajiaPlayer = currentPan.findXiajia(daPlayer);   //打牌玩家的下家
        xiajiaPlayer.clearActionCandidates();                           //清理打牌玩家下家动作缓存

        //下家吃牌动作
        //xiajiaPlayer.tryChiAndGenerateCandidateActions(daAction.getActionPlayerId(), daAction.getPai());

        GuoHuBuHuStatisticsListener guoHuBuHuStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoHuBuHuStatisticsListener.class);              //过胡不胡
        GuoPengBuPengStatisticsListener guoPengBuPengStatisticsListener = ju.getActionStatisticsListenerManager().findListener(GuoPengBuPengStatisticsListener.class);  //过碰不碰

        Set<String> canNotHuPlayers = guoHuBuHuStatisticsListener.getCanNotHuPlayers();                                         //过胡不胡玩家集合
        Map<String, List<MajiangPai>> canNotPengPlayersPaiMap = guoPengBuPengStatisticsListener.getCanNotPengPlayersPaiMap();   //过碰不碰玩家列表

        while (true) {
            if (!xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) { //出牌玩家不是下家玩家
                // 其他的可以碰杠胡
                List<MajiangPai> fangruShoupaiList = xiajiaPlayer.getFangruShoupaiList(); //下家玩家手牌
                if (fangruShoupaiList.size() != 2) {
                    boolean canPeng = true; //可以碰
                    if (canNotPengPlayersPaiMap.containsKey(xiajiaPlayer.getId())) {
                        List<MajiangPai> canNotPengPaiList = canNotPengPlayersPaiMap.get(xiajiaPlayer.getId());
                        if (canNotPengPaiList != null && !canNotPengPaiList.isEmpty()) {
                            for (MajiangPai pai : canNotPengPaiList) {
                                if (pai.equals(daAction.getPai())) {
                                    canPeng = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (canPeng) {
                        xiajiaPlayer.tryPengAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai()); //碰牌
                    }
                }
                xiajiaPlayer.tryGangdachuAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai()); //杠牌
                if (!canNotHuPlayers.contains(xiajiaPlayer.getId()) && dianpao) { //点炮胡
                    ZongyangMajiangPanResultBuilder tuidaoHuPanResultBuilder = (ZongyangMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
                    OptionalPlay optionalPlay = tuidaoHuPanResultBuilder.getOptionalPlay();
                    GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();
                    // 先把这张牌放入计算器
                    xiajiaPlayer.getShoupaiCalculator().addPai(daAction.getPai());
                    ZongyangMajiangHuHu bestHu = ZongyangMajiangJiesuanCalculator.calculateBestDianpaoHu(couldDihu && !currentPan.getZhuangPlayerId().equals(xiajiaPlayer.getId()),
                            gouXingPanHu, xiajiaPlayer, daAction.getPai(), optionalPlay);
                    // 再把这张牌拿出计算器
                    xiajiaPlayer.getShoupaiCalculator().removePai(daAction.getPai());
                    if (bestHu != null) {
                        bestHu.setDianpao(true);
                        bestHu.setDianpaoPlayerId(daPlayer.getId());
                        xiajiaPlayer.addActionCandidate(new MajiangHuAction(xiajiaPlayer.getId(), bestHu));
                    } else {
//                        //非胡牌型特殊胡---十三幺(听胡)
//                        boolean shisanyaoPaixing = TuiDaoHuJiesuanCalculator.isShisanyaoPaixing(xiajiaPlayer,daAction.getPai());
//                        if (shisanyaoPaixing) {
//                            TuiDaoHuHushu hufen = new TuiDaoHuHushu();
//                            hufen.setHu(true);
//                            hufen.setShisanyao(true);
//                            hufen.calculate();
//                            if (!optionalPlay.isKeyidahu()){
//                                hufen.setValue(3);
//                            }
//                            TuiDaoHuHu tuiDaoHuHu = new TuiDaoHuHu(hufen);
//                            tuiDaoHuHu.setDianpao(true);
//                            tuiDaoHuHu.setDianpaoPlayerId(daPlayer.getId());
//                            xiajiaPlayer.addActionCandidate(new MajiangHuAction(xiajiaPlayer.getId(), tuiDaoHuHu));
//                        }
                    }
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

        Map<String, String> depositPlayerList = ju.getDepositPlayerList();
        Set<String> tuoguanPlayerId = depositPlayerList.keySet();
        for (String playerId : tuoguanPlayerId) {
            if (playerId.equals(daAction.getActionPlayerId())) continue;
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
                        autoGuopai(player, depositPlayerList);
                    });
                }
            }
        }

        MajiangPlayer xiajia = currentPan.findXiajia(daPlayer);
        String gameId = depositPlayerList.get(xiajia.getId());
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

    public void autoMopai(MajiangPlayer xiajia, Map<String, String> depositPlayerList) {
        xiajia.clearActionCandidates();
        xiajia.addActionCandidate(new MajiangMoAction(xiajia.getId(), new LundaoMopai()));
        String gameId = depositPlayerList.get(xiajia.getId());
        automatic.automaticAction(xiajia.getId(), 1, gameId);
    }

    public void autoGuopai(MajiangPlayer xiajia, Map<String, String> depositPlayerList) {
        String gameId = depositPlayerList.get(xiajia.getId());
        Map<Integer, MajiangPlayerAction> actionCandidates = xiajia.getActionCandidates();
        automatic.automaticAction(xiajia.getId(), actionCandidates.size(), gameId);
    }

    public boolean isDianpao() {
        return dianpao;
    }

    public void setDianpao(boolean dianpao) {
        this.dianpao = dianpao;
    }

}
