package com.dml.shisanshui.preparedapai.zuowei;

import java.util.HashMap;
import java.util.Map;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.position.Position;

/**
 * 按设置的顺序决定座位
 * 
 * @author lsc
 *
 */
public class JoinGameZuoweiDeterminer implements ZuoweiDeterminer {

	private Map<String, Position> playerIdPositionMap = new HashMap<>();

	@Override
	public void determineZuowei(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		for (String playerId : playerIdPositionMap.keySet()) {
			currentPan.updatePlayerPositionByPlayerId(playerId, playerIdPositionMap.get(playerId));
		}
	}

	public Map<String, Position> getPlayerIdPositionMap() {
		return playerIdPositionMap;
	}

	public void setPlayerIdPositionMap(Map<String, Position> playerIdPositionMap) {
		this.playerIdPositionMap = playerIdPositionMap;
	}

}
