package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

/**
 * 炸弹大小，先比较张数再比较点数
 * 
 * @author Neo
 *
 */
public class ZhangshuFirstZhadanComparator implements ZhadanComparator {

	@Override
	public int compare(ZhadanDianShuZu zhadan1, ZhadanDianShuZu zhadan2) {
		DanGeZhadanDianShuZu danGeZhadan1 = (DanGeZhadanDianShuZu) zhadan1;
		DanGeZhadanDianShuZu danGeZhadan2 = (DanGeZhadanDianShuZu) zhadan2;
		if (danGeZhadan1.getSize() < danGeZhadan2.getSize()) {
			return -1;
		} else if (danGeZhadan1.getSize() > danGeZhadan2.getSize()) {
			return 1;
		} else {
			return danGeZhadan1.getDianShu().compareTo(danGeZhadan2.getDianShu());
		}
	}

}
