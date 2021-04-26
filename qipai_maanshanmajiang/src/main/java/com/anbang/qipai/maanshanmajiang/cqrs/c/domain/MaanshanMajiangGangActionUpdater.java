package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.listener.MaanshanMajiangPengGangActionStatisticsListener;
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

import java.util.Map;

/**
 * 别人可以抢杠胡。原先碰牌后自己摸到碰出刻子牌的第四张牌而形成的明杠,才可以抢
 *
 * @author lsc
 */
public class MaanshanMajiangGangActionUpdater implements MajiangPlayerGangActionUpdater {

    @Override
    public void updateActions(MajiangGangAction gangAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer player = currentPan.findPlayerById(gangAction.getActionPlayerId()); //获取玩家
        MaanshanMajiangPengGangActionStatisticsListener pengGangRecordListener = ju.getActionStatisticsListenerManager().findListener(MaanshanMajiangPengGangActionStatisticsListener.class);//碰杠统计监测器

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
            MaanshanMajiangPanResultBuilder maanshanMajiangPanResultBuilder = (MaanshanMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
            OptionalPlay optionalPlay = maanshanMajiangPanResultBuilder.getOptionalPlay();
//            // 首先看一下,我过的是什么? 是我摸牌之后的胡,杠? 还是别人打出牌之后我可以吃碰杠胡
//            PanActionFrame latestPanActionFrame = currentPan.findNotGuoLatestActionFrame();
//            MajiangPlayerAction action = latestPanActionFrame.getAction();
            //看看是不是有其他玩家可以抢杠胡
            boolean qiangganghu = false;
            if (gangAction.getGangType().equals(GangType.kezigangmo) || gangAction.getGangType().equals(GangType.kezigangshoupai)) {
                GouXingPanHu gouXingPanHu = ju.getGouXingPanHu();
                MajiangPlayer currentPlayer = player;
                while (true) {
                    MajiangPlayer xiajia = currentPan.findXiajia(currentPlayer);
                    if (xiajia.getId().equals(player.getId())) {
                        break;
                    }
                    MaanshanMajiangHu bestHu = MaanshanMajiangJiesuanCalculator.calculateBestQianggangHu(gangAction.getPai(), gouXingPanHu, xiajia, optionalPlay, currentPan);
                    if (optionalPlay.isZimohupai() && bestHu != null) {
                        MaanshanMajiangHushu hufen = bestHu.getHufen();
                        if (!hufen.isKuyazhi() && !hufen.isQingyise() && !hufen.isFengyise()) {
                            bestHu = null;
                        }
                    }
                    if (bestHu != null) {
                        bestHu.setQianggang(true); //抢杠
                        bestHu.setDianpaoPlayerId(player.getId());
                        xiajia.addActionCandidate(new MajiangHuAction(xiajia.getId(), bestHu));
                        xiajia.checkAndGenerateGuoCandidateAction();
                        qiangganghu = true;
                    } else {
                    }
                    currentPlayer = xiajia;
                }

            }

//            gangSocreCalculate(gangAction, ju, optionalPlay, difen);

            //没有抢杠胡，杠完之后要摸牌
            if (!qiangganghu) {
                player.addActionCandidate(new MajiangMoAction(player.getId(), new GanghouBupai(gangAction.getPai(), gangAction.getGangType())));//补牌
            }

        }
    }

    /**
     * 杠分及时计算
     */
    private void gangSocreCalculate(MajiangGangAction gangAction, Ju ju, OptionalPlay optionalPlay, Double difen) {
        GangType gangType = gangAction.getGangType();
        Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap = ju.getCurrentPan().getMajiangPlayerIdMajiangPlayerMap();
        switch (gangType) {
            case gangdachu://明杠
                mingGangScoreCalculate(gangAction.getActionPlayerId(), gangAction.getDachupaiPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case shoupaigangmo://暗杠
                anGangScoreCalculate(gangAction.getActionPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case kezigangmo://补杠
                buGangScoreCalculate(gangAction.getActionPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case gangsigeshoupai://暗杠
                anGangScoreCalculate(gangAction.getActionPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case sanbanziminggang://三搬子明杠
                mingGangScoreCalculate(gangAction.getActionPlayerId(), gangAction.getDachupaiPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case sanbanziangangmo://三搬子暗杠(摸牌)
                anGangScoreCalculate(gangAction.getActionPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
            case sanbanziangangshoupai://三搬子暗杠（手牌）
                anGangScoreCalculate(gangAction.getActionPlayerId(), majiangPlayerIdMajiangPlayerMap, false, difen);
                break;
        }
    }

    /**
     * 明杠分计算
     */
    private void mingGangScoreCalculate(String gangPlayerId, String beigangPlayerId, Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap, boolean jinyuanzi, Double difen) {
        MajiangPlayer gangPlayer = majiangPlayerIdMajiangPlayerMap.get(gangPlayerId);
        MajiangPlayer beigangPlayer = majiangPlayerIdMajiangPlayerMap.get(beigangPlayerId);
        double score = 2 * difen;
        if (jinyuanzi) {
            if (beigangPlayer.getPlayerTotalScore() <= score) {
                score = beigangPlayer.getPlayerTotalScore();
            }
        }
        beigangPlayer.setGangScore(-((int) score));
        beigangPlayer.calculatePlayerTotalScore(-score);
        gangPlayer.setGangScore((int) score);
        gangPlayer.calculatePlayerTotalScore(score);
    }

    /**
     * 暗杠分计算
     */
    private void anGangScoreCalculate(String gangPlayerId, Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap, boolean jinyuanzi, Double difen) {
        double totalGangScore = 0;
        double score = 2 * difen;
        for (MajiangPlayer player : majiangPlayerIdMajiangPlayerMap.values()) {
            if (jinyuanzi) {
                if (!player.getId().equals(gangPlayerId)) {
                    if (player.getPlayerTotalScore() <= score) {
                        score = player.getPlayerTotalScore();
                    }
                    player.setGangScore(-((int) score));
                    player.calculatePlayerTotalScore(-score);
                    totalGangScore += score;
                }
            } else {
                if (!player.getId().equals(gangPlayerId)) {
                    player.setGangScore(-((int) score));
                    player.calculatePlayerTotalScore(-score);
                    totalGangScore += score;
                }
            }
        }
        MajiangPlayer gangPlayer = majiangPlayerIdMajiangPlayerMap.get(gangPlayerId);
        gangPlayer.setGangScore((int) totalGangScore);
        gangPlayer.calculatePlayerTotalScore(totalGangScore);
    }

    /**
     * 补杠分计算
     */
    private void buGangScoreCalculate(String gangPlayerId, Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap, boolean jinyuanzi, Double difen) {
        double totalGangScore = 0;
        double score = difen;
        for (MajiangPlayer player : majiangPlayerIdMajiangPlayerMap.values()) {
            if (jinyuanzi) {
                if (!player.getId().equals(gangPlayerId)) {
                    if (player.getPlayerTotalScore() <= score) {
                        score = player.getPlayerTotalScore();
                    }
                    player.setGangScore(-((int) score));
                    player.calculatePlayerTotalScore(-score);
                    totalGangScore += score;
                }
            } else {
                if (!player.getId().equals(gangPlayerId)) {
                    player.setGangScore(-((int) score));
                    player.calculatePlayerTotalScore(-score);
                    totalGangScore += score;
                }
            }
        }
        MajiangPlayer gangPlayer = majiangPlayerIdMajiangPlayerMap.get(gangPlayerId);
        gangPlayer.setGangScore((int) totalGangScore);
        gangPlayer.calculatePlayerTotalScore(totalGangScore);
    }

}