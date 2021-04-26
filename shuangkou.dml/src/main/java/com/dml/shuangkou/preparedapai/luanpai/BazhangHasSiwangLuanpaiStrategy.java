package com.dml.shuangkou.preparedapai.luanpai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.wanfa.BianXingWanFa;

/**
 * 有四个王的8张插牌策略
 * 
 * @author lsc
 *
 */
public class BazhangHasSiwangLuanpaiStrategy implements LuanpaiStrategy {
	private BianXingWanFa bx;
	private long seed;

	public BazhangHasSiwangLuanpaiStrategy() {

	}

	public BazhangHasSiwangLuanpaiStrategy(BianXingWanFa bx, long seed) {
		this.bx = bx;
		this.seed = seed;
	}

	@Override
	public void luanpai(Ju ju) throws Exception {
		Random r = new Random(seed);
		// 随机出13份牌的叠加顺序
		Integer[] indexs = new Integer[13];
		Integer[] ordinals = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		List<Integer> indexList = new ArrayList<>(Arrays.asList(ordinals));
		for (int i = 0; i < 13; i++) {
			int index = r.nextInt(indexList.size());
			indexs[i] = indexList.remove(index);
		}
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> allPaiList = currentPan.getAvaliablePaiList();
		// 最终的牌堆
		List<PukePai> finalPaiList = new ArrayList<>();
		// 临时存放的牌堆
		List<PukePai> tmpPaiList = new LinkedList<>();
		Map<Integer, List<PukePai>> temMap = new HashMap<>();
		// 将每种牌8张作为1份
		List<PukePai> paiList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 8; j++) {
				paiList.add(allPaiList.get(i * 8 + j));
			}
			// 保存分组和叠加顺序
			int index = indexs[i];
			temMap.put(index, new ArrayList<>(paiList));
			paiList.clear();
		}
		// 将所有牌放入牌堆
		for (int i = 0; i < 13; i++) {
			tmpPaiList.addAll(temMap.get(i));
		}
		// 插入大、小王
		if (BianXingWanFa.qianbian.equals(bx)) {// 千变
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(107));
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(106));
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(105));
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(104));
		} else {// 百变，半千变：两张小王永远叠在一起与其他两张大王随机插入已经叠好的牌堆
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(107));
			tmpPaiList.add(r.nextInt(tmpPaiList.size()), allPaiList.get(106));
			int index = r.nextInt(tmpPaiList.size());
			tmpPaiList.add(index, allPaiList.get(105));
			tmpPaiList.add(index + 1, allPaiList.get(104));
		}
		// 切牌 TODO通过链表的指针直接排序
		List<PukePai> paiList1 = new ArrayList<>();
		List<PukePai> paiList2 = new ArrayList<>();
		List<PukePai> paiList3 = new ArrayList<>();
		Map<Integer, List<PukePai>> ordinalListMap = new HashMap<>();
		ordinalListMap.put(0, paiList1);
		ordinalListMap.put(1, paiList2);
		ordinalListMap.put(2, paiList3);
		int firstIndex;
		int secondIndex;
		do {
			firstIndex = r.nextInt(tmpPaiList.size());// 第一刀位置
			secondIndex = r.nextInt(tmpPaiList.size());// 第二刀位置
		} while (firstIndex == secondIndex || firstIndex == 0 || firstIndex == 107 || secondIndex == 0
				|| secondIndex == 107);// 两刀位置不相同
		if (firstIndex > secondIndex) {// 如果第二刀位置在第一刀前面，则交换位置
			firstIndex = firstIndex ^ secondIndex;
			secondIndex = firstIndex ^ secondIndex;
			firstIndex = firstIndex ^ secondIndex;
		}
		for (int j = 0; j < firstIndex; j++) {
			paiList1.add(tmpPaiList.get(j));
		}
		for (int j = firstIndex; j < secondIndex; j++) {
			paiList2.add(tmpPaiList.get(j));
		}
		for (int j = secondIndex; j < tmpPaiList.size(); j++) {
			paiList3.add(tmpPaiList.get(j));
		}
		List<Integer> ordinalList = new ArrayList<>(Arrays.asList(new Integer[] { 0, 1, 2 }));
		for (int i = 0; i < 3; i++) {
			int ordinal = r.nextInt(ordinalList.size());
			int x = ordinalList.remove(ordinal);
			finalPaiList.addAll(ordinalListMap.get(x));
		}
		currentPan.setAvaliablePaiList(finalPaiList);
		seed++;
	}

	public BianXingWanFa getBx() {
		return bx;
	}

	public void setBx(BianXingWanFa bx) {
		this.bx = bx;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

}
