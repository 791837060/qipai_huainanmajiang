package com.dml.paodekuai.preparedapai.avaliablepai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.paodekuai.ju.Ju;

/**
 * 一桌牌去掉大小王三个2一个A，共48张牌（保留黑桃2，删除黑桃A）
 */
public class CutTypeAvaliablePaiFiller implements AvaliablePaiFiller {

	private static Set<PukePaiMian> notPlaySet;

	static {
		notPlaySet = new HashSet<>();
		notPlaySet.add(PukePaiMian.dawang);
		notPlaySet.add(PukePaiMian.xiaowang);
		notPlaySet.add(PukePaiMian.hongxiner);
		notPlaySet.add(PukePaiMian.meihuaer);
		notPlaySet.add(PukePaiMian.fangkuaier);
		notPlaySet.add(PukePaiMian.heitaoA);
	}

	public CutTypeAvaliablePaiFiller() {
	}

	@Override
	public void fillAvaliablePai(Ju ju) throws Exception {
		List<PukePaiMian> playPaiTypeList = new ArrayList<>();
		// 移除不可用牌
		for (PukePaiMian pukePaiMian : PukePaiMian.values()) {
			if (!notPlaySet.contains(pukePaiMian)) {
				playPaiTypeList.add(pukePaiMian);
			}
		}

		List<PukePai> allPaiList = new ArrayList<>();
		// 生成牌
		int id = 0;
		for (PukePaiMian paiType : playPaiTypeList) {
			PukePai pai = new PukePai();
			pai.setId(id);
			pai.setPaiMian(paiType);
			allPaiList.add(pai);
			id++;
		}

		ju.getCurrentPan().setAvaliablePaiList(allPaiList);
	}

}
