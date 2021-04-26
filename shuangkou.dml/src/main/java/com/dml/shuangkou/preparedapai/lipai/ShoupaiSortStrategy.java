package com.dml.shuangkou.preparedapai.lipai;

import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;

/**
 * 理牌策略
 * 
 * @author Neo
 *
 */
public interface ShoupaiSortStrategy {
	public List<List<Integer>> sortShoupai(Map<Integer, PukePai> allShoupai);
}
