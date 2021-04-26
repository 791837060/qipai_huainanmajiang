package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.LianXuDianShuZu;

/**
 * 连续点数组比大小
 * 
 * @author Neo
 *
 */
public interface LianXuDianShuZuComparator {

	/**
	 * @param dianShuZu1
	 * @param dianShuZu2
	 * @return -1 ,0 , 1, 当1小于, 等于, 大于2的时候
	 * @throws CanNotCompareException
	 *             无法比较，比如334455比33445566
	 */
	public int compare(LianXuDianShuZu dianShuZu1, LianXuDianShuZu dianShuZu2) throws CanNotCompareException;

}
