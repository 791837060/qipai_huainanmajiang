package com.anbang.qipai.guandan.cqrs.c.domain;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.WangZhadanDianShuZu;

public class GuandanZhadanComparator implements ZhadanComparator {

	@Override
	public int compare(ZhadanDianShuZu zhadan1, ZhadanDianShuZu zhadan2) {
		// 线数比较
		if (calculateXianShu(zhadan1) < calculateXianShu(zhadan2)) {
			return -1;
		} else if (calculateXianShu(zhadan1) > calculateXianShu(zhadan2)) {
			return 1;
		} else {
			// 不是连续炸弹的比较
			if (zhadan1 instanceof DanGeZhadanDianShuZu && zhadan2 instanceof DanGeZhadanDianShuZu) {
				DanGeZhadanDianShuZu danGeZhadan1 = (DanGeZhadanDianShuZu) zhadan1;
				DanGeZhadanDianShuZu danGeZhadan2 = (DanGeZhadanDianShuZu) zhadan2;
				return danGeZhadan1.getDianShu().compareTo(danGeZhadan2.getDianShu());
			} else if (zhadan1 instanceof DanGeZhadanDianShuZu) {
				return 1;
			} else if (zhadan2 instanceof DanGeZhadanDianShuZu) {
				return -1;
			} else if (zhadan1 instanceof WangZhadanDianShuZu) {
				return -1;
			} else if (zhadan2 instanceof WangZhadanDianShuZu) {
				return 1;
			} else {
				// 连续炸弹的比较
				LianXuZhadanDianShuZu lianXuZhadanDianShuZu1 = (LianXuZhadanDianShuZu) zhadan1;
				LianXuZhadanDianShuZu lianXuZhadanDianShuZu2 = (LianXuZhadanDianShuZu) zhadan2;
				if (lianXuZhadanDianShuZu1.getMinXianShu() < lianXuZhadanDianShuZu2.getMinXianShu()) {
					return -1;
				} else if (lianXuZhadanDianShuZu1.getMinXianShu() > lianXuZhadanDianShuZu2.getMinXianShu()) {
					return 1;
				} else {
					return lianXuZhadanDianShuZu1.getLastOrdinal() - lianXuZhadanDianShuZu2.getLastOrdinal();
				}
			}
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

}
