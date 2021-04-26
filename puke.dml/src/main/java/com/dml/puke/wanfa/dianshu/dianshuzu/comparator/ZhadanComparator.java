package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;

/**
 * 炸弹比大小
 * 
 * @author Neo
 *
 */
public interface ZhadanComparator {
	/**
	 * @param zhadan1
	 * @param zhadan2
	 * @return -1 ,0 , 1, 当1小于, 等于, 大于2的时候
	 */
	public int compare(ZhadanDianShuZu zhadan1, ZhadanDianShuZu zhadan2);
}
