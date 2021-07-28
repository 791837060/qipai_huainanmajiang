package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.anbang.qipai.zongyangmajiang.cqrs.c.domain.listener.ZongyangMajiangPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.gang.MajiangPlayerGangActionUpdater;
import com.dml.majiang.player.action.mo.GanghouBupai;
import com.dml.majiang.player.action.mo.MajiangMoAction;

/**
 * 别人可以抢杠胡。原先碰牌后自己摸到碰出刻子牌的第四张牌而形成的明杠,才可以抢
 *
 * @author lsc
 */
public class ZongyangMajiangGangActionUpdater implements MajiangPlayerGangActionUpdater {

    @Override
    public void updateActions(MajiangGangAction gangAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer player = currentPan.findPlayerById(gangAction.getActionPlayerId()); //获取玩家
        ZongyangMajiangPengGangActionStatisticsListener pengGangRecordListener = ju.getActionStatisticsListenerManager().findListener(ZongyangMajiangPengGangActionStatisticsListener.class);//碰杠统计监测器

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
                player.addActionCandidate(new MajiangMoAction(player.getId(), new GanghouBupai(gangAction.getPai(), gangAction.getGangType())));//补拍
        }
    }
}