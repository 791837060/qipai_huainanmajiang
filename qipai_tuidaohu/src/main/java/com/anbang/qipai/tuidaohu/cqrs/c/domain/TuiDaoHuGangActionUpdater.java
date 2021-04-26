package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.listener.TuiDaoHuPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanActionFrame;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.MajiangPlayerActionType;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.gang.MajiangPlayerGangActionUpdater;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.mo.GanghouBupai;
import com.dml.majiang.player.action.mo.MajiangMoAction;
import com.dml.majiang.player.action.peng.MajiangPengAction;
import com.dml.majiang.player.shoupai.gouxing.GouXingPanHu;

/**
 * 别人可以抢杠胡。原先碰牌后自己摸到碰出刻子牌的第四张牌而形成的明杠,才可以抢
 *
 * @author lsc
 */
public class TuiDaoHuGangActionUpdater implements MajiangPlayerGangActionUpdater {
    private boolean qiangGang;

    @Override
    public void updateActions(MajiangGangAction gangAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer player = currentPan.findPlayerById(gangAction.getActionPlayerId()); //获取玩家
        TuiDaoHuPengGangActionStatisticsListener pengGangRecordListener = ju.getActionStatisticsListenerManager().findListener(TuiDaoHuPengGangActionStatisticsListener.class);//碰杠统计监测器

        if (gangAction.isDisabledByHigherPriorityAction()) { //动作是否被阻塞
            player.clearActionCandidates(); //玩家已经做了决定，要删除动作
            if (currentPan.allPlayerHasNoActionCandidates() && !currentPan.anyPlayerHu()) { //所有玩家行牌结束，并且没人胡
                MajiangPlayerAction finallyDoneAction = pengGangRecordListener.findPlayerFinallyDoneAction(); //找出最终应该执行的动作
                MajiangPlayer actionPlayer = currentPan.findPlayerById(finallyDoneAction.getActionPlayerId());
                if (finallyDoneAction instanceof MajiangGangAction) { //如果是杠，也只能是杠
                    MajiangGangAction action = (MajiangGangAction) finallyDoneAction;
                    actionPlayer.addActionCandidate(new MajiangGangAction(action.getActionPlayerId(), action.getDachupaiPlayerId(), action.getPai(), action.getGangType()));
                }
                pengGangRecordListener.updateForNextLun();// 清空动作缓存
            }
        } else {
            currentPan.clearAllPlayersActionCandidates();   //清除所有玩家动作
            pengGangRecordListener.updateForNextLun();      //清空杠动作缓存
            TuiDaoHuPanResultBuilder tuidaohuPanResultBuilder = (TuiDaoHuPanResultBuilder) ju.getCurrentPanResultBuilder();
            OptionalPlay optionalPlay = tuidaohuPanResultBuilder.getOptionalPlay();
            // 首先看一下,我过的是什么? 是我摸牌之后的胡,杠? 还是别人打出牌之后我可以吃碰杠胡
            PanActionFrame latestPanActionFrame = currentPan.findNotGuoLatestActionFrame();
            MajiangPlayerAction action = latestPanActionFrame.getAction();
            //看看是不是有其他玩家可以抢杠胡
            boolean qiangganghu = false;
            if (qiangGang) {
                if (gangAction.getGangType().equals(GangType.kezigangmo) || gangAction.getGangType().equals(GangType.kezigangshoupai)) {
                    if (action.getType().equals(MajiangPlayerActionType.peng)) {
                        MajiangPengAction pengAction = (MajiangPengAction) action;
                        if (!pengAction.getPai().equals(gangAction.getPai())) {
                            GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();
                            MajiangPlayer currentPlayer = player;
                            while (true) {
                                MajiangPlayer xiajia = currentPan.findXiajia(currentPlayer);
                                if (xiajia.getId().equals(player.getId())) {
                                    break;
                                }
                                TuiDaoHuHu bestHu = TuiDaoHuJiesuanCalculator.calculateBestQianggangHu(gangAction.getPai(), gouXingPanHu, xiajia, optionalPlay);
                                if (bestHu != null) {
                                    bestHu.setQianggang(true); //抢杠
                                    bestHu.setDianpaoPlayerId(player.getId());
                                    xiajia.addActionCandidate(new MajiangHuAction(xiajia.getId(), bestHu));
                                    xiajia.checkAndGenerateGuoCandidateAction();
                                    qiangganghu = true;
                                } else {
    //                                //非胡牌型特殊胡---十三幺(抢杠胡)
    //                                boolean shisanyaoPaixing = TuiDaoHuJiesuanCalculator.isShisanyaoPaixing(xiajia,gangAction.getPai());
    //                                if (shisanyaoPaixing) {
    //                                    TuiDaoHuHushu hufen = new TuiDaoHuHushu();
    //                                    hufen.setHu(true);
    //                                    hufen.setShisanyao(true);
    //                                    hufen.calculate();
    //                                    if (!optionalPlay.isKeyidahu()){
    //                                        hufen.setValue(3);
    //                                    }
    //                                    TuiDaoHuHu tuiDaoHuHu = new TuiDaoHuHu(hufen);
    //                                    tuiDaoHuHu.setQianggang(true);
    //                                    tuiDaoHuHu.setDianpaoPlayerId(player.getId());
    //                                    xiajia.addActionCandidate(new MajiangHuAction(xiajia.getId(), tuiDaoHuHu));
    //                                }
                                }
                                currentPlayer = xiajia;
                            }
                        }
                    } else {
                        GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();
                        MajiangPlayer currentPlayer = player;
                        while (true) {
                            MajiangPlayer xiajia = currentPan.findXiajia(currentPlayer);
                            if (xiajia.getId().equals(player.getId())) {
                                break;
                            }
                            TuiDaoHuHu bestHu = TuiDaoHuJiesuanCalculator.calculateBestQianggangHu(gangAction.getPai(), gouXingPanHu, xiajia, optionalPlay);
                            if (bestHu != null) {
                                bestHu.setQianggang(true); //抢杠
                                bestHu.setDianpaoPlayerId(player.getId());
                                xiajia.addActionCandidate(new MajiangHuAction(xiajia.getId(), bestHu));
                                xiajia.checkAndGenerateGuoCandidateAction();
                                qiangganghu = true;
                            } else {
    //                            //非胡牌型特殊胡---十三幺(抢杠胡)
    //                            boolean shisanyaoPaixing = TuiDaoHuJiesuanCalculator.isShisanyaoPaixing(xiajia,gangAction.getPai());
    //                            if (shisanyaoPaixing) {
    //                                TuiDaoHuHushu hufen = new TuiDaoHuHushu();
    //                                hufen.setHu(true);
    //                                hufen.setShisanyao(true);
    //                                hufen.calculate();
    //                                if (!optionalPlay.isKeyidahu()){
    //                                    hufen.setValue(2);
    //                                }
    //                                TuiDaoHuHu tuiDaoHuHu = new TuiDaoHuHu(hufen);
    //                                tuiDaoHuHu.setQianggang(true);
    //                                tuiDaoHuHu.setDianpaoPlayerId(player.getId());
    //                                xiajia.addActionCandidate(new MajiangHuAction(xiajia.getId(), tuiDaoHuHu));
    //                            }
                            }
                            currentPlayer = xiajia;
                        }
                    }
                }
            }
            //没有抢杠胡，杠完之后要摸牌
            if (!qiangganghu) {
                player.addActionCandidate(new MajiangMoAction(player.getId(), new GanghouBupai(gangAction.getPai(), gangAction.getGangType())));//补拍
            }
        }
    }

    public boolean isQiangGang() {
        return qiangGang;
    }

    public void setQiangGang(boolean qiangGang) {
        this.qiangGang = qiangGang;
    }
}