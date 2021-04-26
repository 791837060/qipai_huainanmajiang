package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.player.action.da.ZaDanYaPaiSolutionCalculator;
import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZuGenerator;
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;

/**
 * 斗地主炸弹压牌
 * 
 * @author lsc
 *
 */
public class DoudizhuZaDanYaPaiSolutionCalculator implements ZaDanYaPaiSolutionCalculator {

	private ZhadanComparator zhadanComparator;

	@Override
	public Map<String, DaPaiDianShuSolution> calculate(DianShuZu beiYaDianShuZu, int[] dianShuAmountArray) {
		Map<String, DaPaiDianShuSolution> kedaPaiSolutions = new HashMap<>();
		// 单个炸弹点数组
		List<DanGeZhadanDianShuZu> zhadanList = DianShuZuGenerator.generateAllZhadanDianShuZu(dianShuAmountArray);
		// 王炸
		List<HuojianDianShuZu> huojianList = DoudizhuSolutionGenerator.generateAllHuojianDianShuZu(dianShuAmountArray);
		List<DanGeZhadanDianShuZu> zhadanFilterList = new ArrayList<>();
		List<HuojianDianShuZu> huojianFilterList = new ArrayList<>();
		if (beiYaDianShuZu instanceof ZhadanDianShuZu) {
			ZhadanDianShuZu beiYaZhadanDianShuZu = (ZhadanDianShuZu) beiYaDianShuZu;
			for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanList) {
				if (zhadanComparator.compare(danGeZhadanDianShuZu, beiYaZhadanDianShuZu) > 0) {
					zhadanFilterList.add(danGeZhadanDianShuZu);
				}
			}
			for (HuojianDianShuZu huojianDianShuZu : huojianList) {
				if (zhadanComparator.compare(huojianDianShuZu, beiYaZhadanDianShuZu) > 0) {
					huojianFilterList.add(huojianDianShuZu);
				}
			}
		} else {
			zhadanFilterList = zhadanList;
			huojianFilterList = huojianList;
		}
		List<DaPaiDianShuSolution> zhadanSolution = DoudizhuSolutionGenerator
				.calculateDangezhadanDaPaiDianShuSolution(zhadanFilterList);
		zhadanSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		List<DaPaiDianShuSolution> huojianSolution = DoudizhuSolutionGenerator
				.calculateHuojianDaPaiDianShuSolution(huojianFilterList);
		huojianSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		return kedaPaiSolutions;
	}

	public ZhadanComparator getZhadanComparator() {
		return zhadanComparator;
	}

	public void setZhadanComparator(ZhadanComparator zhadanComparator) {
		this.zhadanComparator = zhadanComparator;
	}

}
