package com.anbang.qipai.guandan.cqrs.c.domain.listener;

import java.util.HashMap;
import java.util.Map;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;
import com.dml.shuangkou.player.action.da.DaAction;
import com.dml.shuangkou.player.action.da.DaActionStatisticsListener;

/**
 * 记录打出的每个玩家的各个线数的次数
 * 
 * @author lsc
 *
 */
public class XianshuCountDaActionStatisticsListener implements DaActionStatisticsListener {

	private Map<String, int[]> playerXianshuMap = new HashMap<>();

	@Override
	public void updateForNextPan() {
		playerXianshuMap = new HashMap<>();
	}

	@Override
	public void update(DaAction daAction, Ju ju) {
		String daActionPlayerId = daAction.getActionPlayerId();
		int[] xianshuCount = playerXianshuMap.get(daActionPlayerId);
		if (xianshuCount == null) {
			xianshuCount = new int[9];
			playerXianshuMap.put(daActionPlayerId, xianshuCount);
		}
		DianShuZu dianShuZu = daAction.getDachuPaiZu().getDianShuZu();
		if (dianShuZu instanceof ZhadanDianShuZu) {
			int xianshu = calculateXianShu((ZhadanDianShuZu) dianShuZu);
			xianshuCount[xianshu - 4]++;
		}
	}

	private int calculateXianShu(ZhadanDianShuZu zhadan) {
		if (zhadan instanceof DanGeZhadanDianShuZu) {
			DanGeZhadanDianShuZu danGeZhadan = (DanGeZhadanDianShuZu) zhadan;
			return danGeZhadan.getSize();
		} else if (zhadan instanceof LianXuZhadanDianShuZu) {
			LianXuZhadanDianShuZu lianXuZhadan = (LianXuZhadanDianShuZu) zhadan;
			return lianXuZhadan.getXianShu();
		} else {
			WangZhadanDianShuZu wangZhadan = (WangZhadanDianShuZu) zhadan;
			int xiaowangCount = wangZhadan.getXiaowangCount();
			int dawangCount = wangZhadan.getDawangCount();
			if (xiaowangCount + dawangCount == 4) {
				return 7;
			} else {
				return 6;
			}
		}
	}

	public Map<String, int[]> getPlayerXianshuMap() {
		return playerXianshuMap;
	}

	public void setPlayerXianshuMap(Map<String, int[]> playerXianshuMap) {
		this.playerXianshuMap = playerXianshuMap;
	}

}
