package com.dml.doudizhu.player.action.da;

import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;

/**
 * 通过非炸弹去压牌的方案
 * 
 * @author Neo
 *
 */
public interface ZaDanYaPaiSolutionCalculator {

	public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray);

}
