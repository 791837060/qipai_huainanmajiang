package com.dml.majiang.player.menfeng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.position.MajiangPosition;

public class RandomMustHasDongPlayersMenFengDeterminer implements PlayersMenFengDeterminer {

	private long seed;

	public RandomMustHasDongPlayersMenFengDeterminer() {
	}

	public RandomMustHasDongPlayersMenFengDeterminer(long seed) {
		this.seed = seed;
	}

	@Override
	public void determinePlayersMenFeng(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<String> sortedPlayerIdList = currentPan.sortedPlayerIdList();
		List<MajiangPosition> pList = new ArrayList<>();
		pList.add(MajiangPosition.nan);
		pList.add(MajiangPosition.xi);
		pList.add(MajiangPosition.bei);

		Random r = new Random(seed);
		String dongPlayerId = sortedPlayerIdList.get(r.nextInt(sortedPlayerIdList.size()));
		currentPan.updatePlayerMenFeng(dongPlayerId, MajiangPosition.dong);
		sortedPlayerIdList.remove(dongPlayerId);

		if (sortedPlayerIdList.size() == 1) {
			currentPan.updatePlayerMenFeng(sortedPlayerIdList.get(0), MajiangPosition.xi);
		} else {
			while (!sortedPlayerIdList.isEmpty()) {
				String playerId = sortedPlayerIdList.remove(0);
				MajiangPosition position = pList.remove(r.nextInt(pList.size()));
				currentPan.updatePlayerMenFeng(playerId, position);
			}
		}

	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

}
