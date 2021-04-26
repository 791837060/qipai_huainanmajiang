package com.dml.shisanshui.preparedapai.lipai;

import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;

/**
 * 理牌策略
 * 
 * @author Neo
 *
 */
public interface ShoupaiSortStrategy {

	public List<List<Integer>> sortShoupai(Map<Integer, PukePai> allShoupai);
}
