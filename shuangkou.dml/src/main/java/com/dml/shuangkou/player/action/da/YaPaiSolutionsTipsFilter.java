package com.dml.shuangkou.player.action.da;

import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 可压牌的提示过滤器
 * 
 * @author Neo
 *
 */
public interface YaPaiSolutionsTipsFilter {
	public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> YaPaiSolutions, Map<Integer, PukePai> allShoupai, boolean yapai);
}
