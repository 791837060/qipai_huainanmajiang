package com.dml.shuangkou.preparedapai.xianda;

import com.dml.puke.wanfa.position.Position;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;

public class HongxinbaXiandaPlayerDeterminer implements XiandaPlayerDeterminer {

    @Override
    public String determineXiandaPlayer(Ju ju) {
        Pan currentPan = ju.getCurrentPan();
        String daplayerId = null;
//		for (ShuangkouPlayer player : currentPan.getShuangkouPlayerIdPlayerMap().values()) {
//			for (PukePai pukePai : player.getAllShoupai().values()) {
//				if (pukePai.getMark() != null && pukePai.getMark().name().equals("qishouLiangPai")) {
//					daplayerId = player.getId();
//					return daplayerId;
//				}
//			}
//		}
        daplayerId = currentPan.playerIdForPosition(Position.dong);
        return daplayerId;
    }

}
