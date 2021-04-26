package com.dml.doudizhu.preparedapai.position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.puke.wanfa.position.Position;

/**
 * 随机座位
 * 
 * @author lsc
 *
 */
public class MustHasDongMenfengDeterminer implements MenfengDeterminer {

	public MustHasDongMenfengDeterminer() {

	}

	@Override
	public void determineMenfengForPlayer(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<Position> positions = new ArrayList<>();
		positions.add(Position.dong);
		positions.add(Position.nan);
		positions.add(Position.xi);
		positions.add(Position.bei);
		List<String> playerIdList = new ArrayList<>(currentPan.getDoudizhuPlayerIdPlayerMap().keySet());
		Collections.shuffle(playerIdList);
		if (currentPan.getDoudizhuPlayerIdPlayerMap().size() == 2) {
			for (String playerId : playerIdList) {
				currentPan.updatePlayerPosition(playerId, positions.remove(0));
				positions.remove(0);
			}
		} else if (currentPan.getDoudizhuPlayerIdPlayerMap().size() == 3) {
			for (String playerId : playerIdList) {
				currentPan.updatePlayerPosition(playerId, positions.remove(0));
			}
		}
	}

}
