package com.dml.shisanshui.pai.paixing.comparator;

import com.dml.shisanshui.pai.paixing.Dao;

/**
 * 根据编码大小比较每道的大小
 * 
 * @author lsc
 *
 */
public class TypeCodeDaoComparator implements DaoComparator {

	@Override
	public int compare(Dao dao1, Dao dao2) {
		long code1 = dao1.getTypeCode();
		long code2 = dao2.getTypeCode();
		if (code1 > code2) {
			return 1;
		} else if (code1 < code2) {
			return -1;
		}
		return 0;
	}

}
