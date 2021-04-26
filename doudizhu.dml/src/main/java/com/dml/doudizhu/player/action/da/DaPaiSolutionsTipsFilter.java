package com.dml.doudizhu.player.action.da;

import java.util.List;
import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.PukePai;

/**
 * 可打牌的提示过滤器
 * 
 * @author lsc
 *
 */
public interface DaPaiSolutionsTipsFilter {

	public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> DaPaiSolutions,
			Map<Integer, PukePai> allShoupai);
}
