package com.anbang.qipai.doudizhu.cqrs.c.domain;

import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;

/**
 * 最常见的斗地主炸弹比大小
 * 
 * @author lsc
 *
 */
public class DoudizhuZhadanComparator implements ZhadanComparator {

	@Override
	public int compare(ZhadanDianShuZu zhadan1, ZhadanDianShuZu zhadan2) {
		if (zhadan1 instanceof HuojianDianShuZu) {
			return 1;
		} else if (zhadan2 instanceof HuojianDianShuZu) {
			return -1;
		} else {
			DanGeZhadanDianShuZu danGeZhadan1 = (DanGeZhadanDianShuZu) zhadan1;
			DanGeZhadanDianShuZu danGeZhadan2 = (DanGeZhadanDianShuZu) zhadan2;
			return danGeZhadan1.getDianShu().compareTo(danGeZhadan2.getDianShu());
		}
	}

}
