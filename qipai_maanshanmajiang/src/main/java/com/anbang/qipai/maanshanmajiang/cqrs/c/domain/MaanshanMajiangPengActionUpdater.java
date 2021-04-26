package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.listener.MaanshanMajiangPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pai.fenzu.GangType;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.MajiangPlayerAction;
import com.dml.majiang.player.action.gang.MajiangGangAction;
import com.dml.majiang.player.action.peng.MajiangPengAction;
import com.dml.majiang.player.action.peng.MajiangPlayerPengActionUpdater;
import com.dml.majiang.player.action.ting.MajiangTingAction;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;

import java.util.List;

/**
 * 碰的那个人要打牌
 *
 * @author lsc
 */
public class MaanshanMajiangPengActionUpdater implements MajiangPlayerPengActionUpdater {

    @Override
    public void updateActions(MajiangPengAction pengAction, Ju ju) throws Exception {
//        MaanshanMajiangPanResultBuilder currentPanResultBuilder = (MaanshanMajiangPanResultBuilder) ju.getCurrentPanResultBuilder();
//        OptionalPlay optionalPlay = currentPanResultBuilder.getOptionalPlay();

        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer player = currentPan.findPlayerById(pengAction.getActionPlayerId());
        MaanshanMajiangPengGangActionStatisticsListener pengGangRecordListener = ju.getActionStatisticsListenerManager().findListener(MaanshanMajiangPengGangActionStatisticsListener.class);//碰杠监测器
        if (pengAction.isDisabledByHigherPriorityAction()) { //如果动作被阻塞
            player.clearActionCandidates(); //玩家已经做了决定，要删除动作
            if (currentPan.allPlayerHasNoActionCandidates() && !currentPan.anyPlayerHu()) { //所有玩家行牌结束，并且没人胡
                MajiangPlayerAction finallyDoneAction = pengGangRecordListener.findPlayerFinallyDoneAction(); //找出最终应该执行的动作
                MajiangPlayer actionPlayer = currentPan.findPlayerById(finallyDoneAction.getActionPlayerId());
                if (finallyDoneAction instanceof MajiangPengAction) {// 如果是碰，也只能是碰
                    MajiangPengAction action = (MajiangPengAction) finallyDoneAction;
                    actionPlayer.addActionCandidate(new MajiangPengAction(action.getActionPlayerId(), action.getDachupaiPlayerId(), action.getPai()));
                }
                pengGangRecordListener.updateForNextLun();// 清空动作缓存
            }
        } else {
            currentPan.clearAllPlayersActionCandidates(); //清空所有玩家动作缓存
            pengGangRecordListener.updateForNextLun();    //清空当前碰动作
            // 刻子杠手牌
            List<PengchuPaiZu> pengchupaiZuList = player.getPengchupaiZuList();
            PengchuPaiZu pengchuPaiZu = pengchupaiZuList.get(pengchupaiZuList.size() - 1);
            for (MajiangPai fangruShoupai : player.getFangruShoupaiList()) {
                if (pengchuPaiZu.getKezi().getPaiType().equals(fangruShoupai)) {
                    player.addActionCandidate(new MajiangGangAction(pengAction.getActionPlayerId(), pengAction.getDachupaiPlayerId(), fangruShoupai, GangType.kezigangshoupai));
                    break;
                }
            }

            //需要有“过”
            player.checkAndGenerateGuoCandidateAction();
            if (player.getActionCandidates().isEmpty()) {
                player.generateDaQuepaiActions();
                if (player.getActionCandidates().isEmpty()){
                    player.generateDaGuipaiActions();
                }
            }

        }

    }

}
