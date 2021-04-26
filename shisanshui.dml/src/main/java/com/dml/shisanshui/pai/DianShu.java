package com.dml.shisanshui.pai;

/**
 * A＞K＞Q＞J＞...＞3＞2
 * 
 * @author lsc
 *
 */
public enum DianShu {
	minA,er, san, si, wu, liu, qi, ba, jiu, shi, J, Q, K, A, xiaowang, dawang;

	public static DianShu getDianShuByOrdinal(int ordinal) {
		DianShu[] dianshuZu = DianShu.values();
		return dianshuZu[ordinal];
	}
}
