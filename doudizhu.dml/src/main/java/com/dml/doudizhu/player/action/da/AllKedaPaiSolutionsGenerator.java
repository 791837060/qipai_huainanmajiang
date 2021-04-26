package com.dml.doudizhu.player.action.da;

import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.PukePai;

/**
 * 生成所有可打的牌的方案
 * 
 * @author Neo
 *
 */
public interface AllKedaPaiSolutionsGenerator {

	public Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai);

}
