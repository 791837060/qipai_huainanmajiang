package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.listener.MaanshanMajiangPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pai.fenzu.Kezi;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.action.hu.MajiangHuAction;
import com.dml.majiang.player.action.hu.MajiangPlayerHuActionUpdater;
import com.dml.majiang.player.chupaizu.GangchuPaiZu;
import com.dml.majiang.player.chupaizu.PengchuPaiZu;

import java.util.List;
import java.util.Map;

/**
 * 玩家胡了之后清除自身所有动作
 *
 * @author lsc
 */
public class MaanshanMajiangClearAllActionHuActionUpdater implements MajiangPlayerHuActionUpdater {

    @Override
    public void updateActions(MajiangHuAction huAction, Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        MajiangPlayer huPlayer = currentPan.findPlayerById(huAction.getActionPlayerId());
        huPlayer.clearActionCandidates();

        // 抢杠胡，删除被抢的杠
        if (huAction.getHu().isQianggang()) {
            MajiangPlayer dianpaoPlayer = currentPan.findPlayerById(huAction.getHu().getDianpaoPlayerId());
            List<GangchuPaiZu> gangchupaiZuList = dianpaoPlayer.getGangchupaiZuList();
            if (gangchupaiZuList.size() > 0) {
                GangchuPaiZu gangChuPaiZu = gangchupaiZuList.remove(gangchupaiZuList.size() - 1);
                PengchuPaiZu pengChuPaiZu = new PengchuPaiZu(new Kezi(gangChuPaiZu.getGangzi().getPaiType()), gangChuPaiZu.getDachuPlayerId(), dianpaoPlayer.getId());
                dianpaoPlayer.getPengchupaiZuList().add(pengChuPaiZu);
                //删除碰撞监测器中的杠
                MaanshanMajiangPengGangActionStatisticsListener pengGangRecordListener = ju.getActionStatisticsListenerManager().findListener(MaanshanMajiangPengGangActionStatisticsListener.class);//碰杠统计监测器
                Map<String, Integer> playerIdFangGangShuMap = pengGangRecordListener.getPlayerIdFangGangShuMap();
                String dachuPlayerId = gangChuPaiZu.getDachuPlayerId();
                Integer gangCount = playerIdFangGangShuMap.get(dachuPlayerId);
                if (gangCount != null) {
                    playerIdFangGangShuMap.put(dachuPlayerId, Math.max(gangCount - 1, 0));
                }
            }
        }

    }

}
