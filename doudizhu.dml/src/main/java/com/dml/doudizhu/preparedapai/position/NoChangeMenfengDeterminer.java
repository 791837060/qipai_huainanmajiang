package com.dml.doudizhu.preparedapai.position;

import java.util.Map;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanResult;
import com.dml.puke.wanfa.position.Position;

/**
 * 座风不变
 * 
 * @author lsc
 *
 */
public class NoChangeMenfengDeterminer implements MenfengDeterminer {

	@Override
	public void determineMenfengForPlayer(Ju ju) throws Exception {
		PanResult panResult = ju.findLatestFinishedPanResult();
		Pan currentPan = ju.getCurrentPan();
		Map<Position, String> positionPlayerIdMap = panResult.getPan().getPositionPlayerIdMap();
		for (Position position : positionPlayerIdMap.keySet()) {
			currentPan.updatePlayerPosition(positionPlayerIdMap.get(position), position);
		}
	}

}
