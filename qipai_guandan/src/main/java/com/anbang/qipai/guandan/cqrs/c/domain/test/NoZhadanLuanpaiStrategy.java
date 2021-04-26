package com.anbang.qipai.guandan.cqrs.c.domain.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.PukePaiMian;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.preparedapai.luanpai.LuanpaiStrategy;

/**
 * 没有炸弹的乱牌方法
 * 
 * @author lsc
 *
 */
public class NoZhadanLuanpaiStrategy implements LuanpaiStrategy {

	@Override
	public void luanpai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> allPaiList = currentPan.getAvaliablePaiList();
		// 最终的牌堆
		List<PukePai> finalPaiList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 13; j++) {
				finalPaiList.add(allPaiList.get(j * 8 + i));
			}
		}
		finalPaiList.add(allPaiList.get(104));
		finalPaiList.add(allPaiList.get(105));
		finalPaiList.add(allPaiList.get(106));
		finalPaiList.add(allPaiList.get(107));
		currentPan.setAvaliablePaiList(finalPaiList);
	}

	public static void main(String[] args) {
		Set<PukePaiMian> notPlaySet = new HashSet<>();
		PukePaiMian[] allPukePaiMianArray = PukePaiMian.values();
		List<PukePaiMian> playPaiTypeList = new ArrayList<>();
		// 移除不可用牌
		for (int i = 0; i < allPukePaiMianArray.length; i++) {
			PukePaiMian paimain = allPukePaiMianArray[i];
			if (!notPlaySet.contains(paimain)) {
				playPaiTypeList.add(paimain);
			}
		}

		List<PukePai> allPaiList = new ArrayList<>();
		// 生成两副牌
		int id = 0;
		for (PukePaiMian paiType : playPaiTypeList) {
			for (int i = 0; i < 2; i++) {
				PukePai pai = new PukePai();
				pai.setId(id);
				pai.setPaiMian(paiType);
				allPaiList.add(pai);
				id++;
			}
		}
		// 最终的牌堆
		List<PukePai> finalPaiList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 13; j++) {
				finalPaiList.add(allPaiList.get(j * 8 + i));
			}
		}
		finalPaiList.add(allPaiList.get(104));
		finalPaiList.add(allPaiList.get(105));
		finalPaiList.add(allPaiList.get(106));
		finalPaiList.add(allPaiList.get(107));
		for (int i = 0; i < 2; i++) {
			for (int k = 0; k < 2; k++) {
				for (int j = 0; j < 13; j++) {
					PukePai pukePai = finalPaiList.remove(0);
					System.out.print(pukePai.getPaiMian() + ",");
				}
				System.out.println("-----");
			}
		}
		System.out.println("-----");
		for (int k = 0; k < 2; k++) {
			for (int j = 0; j < 2; j++) {
				PukePai pukePai = finalPaiList.remove(0);
				System.out.println(pukePai.getPaiMian() + ",");
			}
		}
	}
}
