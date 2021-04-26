package com.dml.shuangkou.gameprocess;

import java.util.List;

import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;

/**
 * 一队的两个人都打完牌或者只剩一人手上有牌，一盘结束
 *
 * @author lsc
 */
public class OnePlayerHasPaiPanFinishiDeterminer implements CurrentPanFinishiDeterminer {

    @Override
    public boolean determineToFinishCurrentPan(Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        List<String> playerIds = currentPan.getNoPaiPlayerIdList();//打完手牌玩家集合
        for (String playerId : playerIds) {
            ShuangkouPlayer player = currentPan.findDuijiaPlayer(playerId);//获取玩家对家玩家
            String duijiaPlayerId = player.getId();
            if (playerIds.contains(duijiaPlayerId)) {
                return true;//打完手牌玩家的对家也打完手牌
            }
        }
        return currentPan.findAllPlayerId().size() - playerIds.size() <= 1;//打完牌玩家数量等于所有玩家数量-1
    }

}
