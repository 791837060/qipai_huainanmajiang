package com.dml.doudizhu.player.action.da;

import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 通过正常的点数组(非炸弹)去压牌的方案
 * 
 * @author Neo
 *
 */
public interface DianShuZuYaPaiSolutionCalculator {

	public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray);

}
