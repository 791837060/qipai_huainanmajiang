package com.dml.shuangkou.preparedapai.fapai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.player.ShuangkouPlayer;

/**
 * 6*1+7*3发牌策略
 * 
 * @author lsc
 *
 */
public class YiliuSanqiFapaiStrategy implements FapaiStrategy {

	@Override
	public void fapai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<String> playerIds = currentPan.findAllPlayerId();
		List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
		List<PukePai> remainPaiList = new ArrayList<>();
		Map<String, ShuangkouPlayer> shuangkouPlayerIdMajiangPlayerMap = currentPan.getShuangkouPlayerIdPlayerMap();
		fapai(playerIds, avaliablePaiList, remainPaiList, shuangkouPlayerIdMajiangPlayerMap, 1, 6);
		fapai(playerIds, avaliablePaiList, remainPaiList, shuangkouPlayerIdMajiangPlayerMap, 3, 7);
		avaliablePaiList.addAll(remainPaiList);
	}

	public void fapai(List<String> playerIds, List<PukePai> avaliablePaiList, List<PukePai> remainPaiList,
			Map<String, ShuangkouPlayer> shuangkouPlayerIdMajiangPlayerMap, int m, int n) {

		if (playerIds.size() > 2) {// 4人
			for (int i = 0; i < m; i++) {
				for (ShuangkouPlayer player : shuangkouPlayerIdMajiangPlayerMap.values()) {
					for (int j = 0; j < n; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
				}
			}
		} else {// 2人
			for (int i = 0; i < m; i++) {
				for (String playerId : playerIds) {
					ShuangkouPlayer player = shuangkouPlayerIdMajiangPlayerMap.get(playerId);
					for (int j = 0; j < n; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
					for (int j = 0; j < n; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						remainPaiList.add(pukePai);
					}
				}
			}
		}
	}
}
