package com.dml.shuangkou.player.action.da;

import java.util.List;

import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;

/**
 * 生成所有可压的打牌点数方案。
 * 
 * @author Neo
 *
 */
public interface KeyaDaPaiDianShuSolutionsGenerator {
	public List<DaPaiDianShuSolution> generateKeyaDaPaiDianShuSolutions(DianShuZuPaiZu dachuPaiZu,
			List<PukePai> shoupaiList);
}
