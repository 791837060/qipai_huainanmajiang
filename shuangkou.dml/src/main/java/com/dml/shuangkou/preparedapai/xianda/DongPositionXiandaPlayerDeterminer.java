package com.dml.shuangkou.preparedapai.xianda;

import java.util.Map;

import com.dml.puke.wanfa.position.Position;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;

/**
 * 座位“东”的人先打牌
 * 
 * @author lsc
 *
 */
public class DongPositionXiandaPlayerDeterminer implements XiandaPlayerDeterminer {

	@Override
	public String determineXiandaPlayer(Ju ju) {
		Pan currentPan = ju.getCurrentPan();
		Map<Position, String> positionPlayerIdMap = currentPan.getPositionPlayerIdMap();
		String daplayerId = positionPlayerIdMap.get(Position.dong);
		return daplayerId;
	}

}
