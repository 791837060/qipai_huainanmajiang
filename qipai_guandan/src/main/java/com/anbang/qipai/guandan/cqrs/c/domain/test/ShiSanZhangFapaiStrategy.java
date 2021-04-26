package com.anbang.qipai.guandan.cqrs.c.domain.test;

import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;
import com.dml.shuangkou.preparedapai.fapai.FapaiStrategy;

public class ShiSanZhangFapaiStrategy implements FapaiStrategy {

	@Override
	public void fapai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
		Map<String, ShuangkouPlayer> shuangkouPlayerIdMajiangPlayerMap = currentPan.getShuangkouPlayerIdPlayerMap();
		for (int i = 0; i < 2; i++) {
			for (ShuangkouPlayer player : shuangkouPlayerIdMajiangPlayerMap.values()) {
				for (int j = 0; j < 13; j++) {
					PukePai pukePai = avaliablePaiList.remove(0);
					player.addShouPai(pukePai);
				}
			}
		}
		for (ShuangkouPlayer player : shuangkouPlayerIdMajiangPlayerMap.values()) {
			for (int j = 0; j < 1; j++) {
				PukePai pukePai = avaliablePaiList.remove(0);
				player.addShouPai(pukePai);
			}
		}
	}

}
