package com.dml.doudizhu.player.action.da;

import java.util.List;
import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.PukePai;

/**
 * 可压牌的提示过滤器
 * 
 * @author Neo
 *
 */
public interface YaPaiSolutionsTipsFilter {

	public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> YaPaiSolutions,
			Map<Integer, PukePai> allShoupai);
}
