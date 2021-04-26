package com.dml.shuangkou.player.action.da;

import java.util.List;

import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 可打牌的提示
 * 
 * @author Neo
 *
 */
public interface KedaPaiSolutionsForTipsGenerator {
	public List<DaPaiDianShuSolution> generateKedaPaiSolutionsForTips(int[] dianShuAmountArray);
}
