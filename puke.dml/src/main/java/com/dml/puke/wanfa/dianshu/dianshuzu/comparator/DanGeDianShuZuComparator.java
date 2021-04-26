package com.dml.puke.wanfa.dianshu.dianshuzu.comparator;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeDianShuZu;

/**
 * 单个点数组比大小
 * 
 * @author Neo
 *
 */
public interface DanGeDianShuZuComparator {

	/**
	 * @param dianShuZu1
	 * @param dianShuZu2
	 * @return -1 ,0 , 1, 当1小于, 等于, 大于2的时候
	 * @throws CanNotCompareException
	 *             无法比较，比如对子和三张比大小
	 */
	public int compare(DanGeDianShuZu dianShuZu1, DanGeDianShuZu dianShuZu2) throws CanNotCompareException;

}
