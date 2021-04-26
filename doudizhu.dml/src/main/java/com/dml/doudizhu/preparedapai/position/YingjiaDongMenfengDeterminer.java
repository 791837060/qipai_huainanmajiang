package com.dml.doudizhu.preparedapai.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanResult;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;

/**
 * 赢家做东风
 * 
 * @author lsc
 *
 */
public class YingjiaDongMenfengDeterminer implements MenfengDeterminer {

	@Override
	public void determineMenfengForPlayer(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<Position> positions = new ArrayList<>();
		positions.add(Position.dong);
		positions.add(Position.nan);
		positions.add(Position.xi);
		positions.add(Position.bei);
		// 上一盘座风
		PanResult panResult = ju.findLatestFinishedPanResult();
		PanValueObject pan = panResult.getPan();
		Map<Position, String> positionPlayerIdMap = pan.getPositionPlayerIdMap();
		String yingjiaPlayerId = pan.getYingjiaPlayerId();
		Position position = pan.playerPosition(yingjiaPlayerId);
		if (currentPan.getDoudizhuPlayerIdPlayerMap().size() == 2) {
			currentPan.updatePlayerPosition(yingjiaPlayerId, positions.remove(0));
			positions.remove(0);
			String nextPlayerId = findNextPlayer(position, positionPlayerIdMap);
			currentPan.updatePlayerPosition(nextPlayerId, positions.remove(0));
			positions.remove(0);
		} else if (currentPan.getDoudizhuPlayerIdPlayerMap().size() == 3) {
			currentPan.updatePlayerPosition(yingjiaPlayerId, positions.remove(0));
			String nextPlayerId = findNextPlayer(position, positionPlayerIdMap);
			currentPan.updatePlayerPosition(nextPlayerId, positions.remove(0));
			position = pan.playerPosition(nextPlayerId);
			nextPlayerId = findNextPlayer(position, positionPlayerIdMap);
			currentPan.updatePlayerPosition(nextPlayerId, positions.remove(0));
		}
	}

	public String findNextPlayer(Position nextPosition, Map<Position, String> positionPlayerIdMap) {
		nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
		String playerId = positionPlayerIdMap.get(nextPosition);
		while (playerId == null) {
			nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
			playerId = positionPlayerIdMap.get(nextPosition);
		}
		return playerId;
	}

}
