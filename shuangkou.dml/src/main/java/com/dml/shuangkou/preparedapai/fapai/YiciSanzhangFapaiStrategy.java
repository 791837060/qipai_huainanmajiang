package com.dml.shuangkou.preparedapai.fapai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;

/**
 * 每人每次发三张牌
 * 
 * @author lsc
 *
 */
public class YiciSanzhangFapaiStrategy implements FapaiStrategy {

	@Override
	public void fapai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<String> playerIds = currentPan.findAllPlayerId();
		List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
		List<PukePai> remainPaiList = new ArrayList<>();
		Map<String, ShuangkouPlayer> shuangkouPlayerIdMajiangPlayerMap = currentPan.getShuangkouPlayerIdPlayerMap();
		if (playerIds.size() > 2) {// 4人
			for (int i = 0; i < 9; i++) {
				for (ShuangkouPlayer player : shuangkouPlayerIdMajiangPlayerMap.values()) {
					for (int j = 0; j < 3; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
				}
			}
		} else {// 2人
			for (int i = 0; i < 9; i++) {
				for (String playerId : playerIds) {
					ShuangkouPlayer player = shuangkouPlayerIdMajiangPlayerMap.get(playerId);
					for (int j = 0; j < 3; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
					for (int j = 0; j < 3; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						remainPaiList.add(pukePai);
					}
				}
			}
		}
		avaliablePaiList.addAll(remainPaiList);
	}

}
