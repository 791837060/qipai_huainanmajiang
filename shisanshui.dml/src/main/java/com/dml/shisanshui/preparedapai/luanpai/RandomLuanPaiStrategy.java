package com.dml.shisanshui.preparedapai.luanpai;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pan.Pan;

/**
 * 随机的乱牌策略
 * 
 * @author lsc
 *
 */
public class RandomLuanPaiStrategy implements LuanpaiStrategy {

	private long seed;

	public RandomLuanPaiStrategy() {

	}

	public RandomLuanPaiStrategy(long seed) {
		this.seed = seed;
	}

	@Override
	public void luanpai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> allPaiList = currentPan.getAvaliablePaiList();
		Random rnm = new Random(seed + currentPan.getNo());
		Collections.shuffle(allPaiList, rnm);
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

}
