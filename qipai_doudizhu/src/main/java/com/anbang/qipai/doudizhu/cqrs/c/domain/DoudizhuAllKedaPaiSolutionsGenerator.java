package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.pai.dianshuzu.FeijidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.FeijidaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaiyiDianShuZu;
import com.dml.doudizhu.player.action.da.AllKedaPaiSolutionsGenerator;
import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZuGenerator;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;

/**
 * 最普通的斗地主打法
 * 
 * @author lsc
 *
 */
public class DoudizhuAllKedaPaiSolutionsGenerator implements AllKedaPaiSolutionsGenerator {

	/*
	 * 有顺序的打牌方案，如4444，可以算3个4带1个4，也可以算炸弹，优先算炸弹
	 */
	@Override
	public Map<String, DaPaiDianShuSolution> generateAllKedaPaiSolutions(Map<Integer, PukePai> allShoupai) {
		Map<String, DaPaiDianShuSolution> kedaPaiSolutions = new HashMap<>();
		int[] dianShuAmountArray = new int[15];
		for (PukePai pukePai : allShoupai.values()) {
			dianShuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
		}
		// 单张点数组
		List<DanzhangDianShuZu> danzhangList = DianShuZuGenerator.generateAllDanzhangDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> danzhangSolution = DoudizhuSolutionGenerator
				.calculateDanzhangDaPaiDianShuSolution(danzhangList);
		danzhangSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 对子点数组
		List<DuiziDianShuZu> duiziList = DianShuZuGenerator.generateAllDuiziDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> duiziSolution = DoudizhuSolutionGenerator
				.calculateDuiziDaPaiDianShuSolution(duiziList);
		duiziSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 三张点数组
		List<SanzhangDianShuZu> sanzhangList = DianShuZuGenerator.generateAllSanzhangDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sanzhangSolution = DoudizhuSolutionGenerator
				.calculateSanzhangDaPaiDianShuSolution(sanzhangList);
		sanzhangSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 顺子点数组
		List<ShunziDianShuZu> shunziList = new ArrayList<>();
		for (int length = 5; length < 13; length++) {// 顺子:五张或更多的连续单牌,不包括2与双王,如单5+单6+单7+单8+单9.
			shunziList.addAll(DianShuZuGenerator.generateAllShunziDianShuZu(dianShuAmountArray, length));
		}
		List<DaPaiDianShuSolution> shunziSolution = DoudizhuSolutionGenerator
				.calculateShunziDaPaiDianShuSolution(shunziList);
		shunziSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 连对点数组
		List<LianduiDianShuZu> lianduiList = new ArrayList<>();
		for (int length = 3; length < 12; length++) {// 连对:三对或更多的连续对子,不包括2与双王,如对5+对6+对7.
			lianduiList.addAll(DianShuZuGenerator.generateAllLianduiDianShuZu(dianShuAmountArray, length));
		}
		List<DaPaiDianShuSolution> lianduiSolution = DoudizhuSolutionGenerator
				.calculateLianduiDaPaiDianShuSolution(lianduiList);
		lianduiSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 连三张点数组
		List<LiansanzhangDianShuZu> lianSanZhangList = new ArrayList<>();
		for (int length = 2; length < 12; length++) {// 连三张:二或更多的连续三张,不包括2与双王
			lianSanZhangList.addAll(DianShuZuGenerator.generateAllLiansanzhangDianShuZu(dianShuAmountArray, length));
		}
		List<DaPaiDianShuSolution> liansanzhangSolution = DoudizhuSolutionGenerator
				.calculateLiansanzhangDaPaiDianShuSolution(lianSanZhangList);
		liansanzhangSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 三带一
		List<SandaiyiDianShuZu> sandaiyiList = DoudizhuSolutionGenerator
				.generateAllSandaiyiDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sandaiyiSolution = DoudizhuSolutionGenerator
				.calculateSandaiyiDaPaiDianShuSolution(sandaiyiList);
		sandaiyiSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 三带二
		List<SandaierDianShuZu> sandaierList = DoudizhuSolutionGenerator
				.generateAllSandaierDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sandaierSolution = DoudizhuSolutionGenerator
				.calculateSandaierDaPaiDianShuSolution(sandaierList);
		sandaierSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 特殊:三带对王
		List<SandaierDianShuZu> sandaiduiwangList = DoudizhuSolutionGenerator
				.generateAllSandaiduiwangDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sandaiduiwangSolution = DoudizhuSolutionGenerator
				.calculateSandaiduiwangDaPaiDianShuSolution(sandaiduiwangList);
		sandaiduiwangSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 带单张飞机
		List<FeijidaiyiDianShuZu> feijidaiyiList = new ArrayList<>();
		for (int length = 2; length < 12; length++) {// 连三张:二或更多的连续三张,不包括2与双王
			feijidaiyiList.addAll(DoudizhuSolutionGenerator.generateAllFeijidaiyiDianShuZu(dianShuAmountArray, length));
		}
		List<DaPaiDianShuSolution> feijidaiyiSolution = DoudizhuSolutionGenerator
				.calculateFeijidaiyiDaPaiDianShuSolution(feijidaiyiList);
		feijidaiyiSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 带对子飞机
		List<FeijidaierDianShuZu> feijidaierList = new ArrayList<>();
		for (int length = 2; length < 12; length++) {// 连三张:二或更多的连续三张,不包括2与双王
			feijidaierList.addAll(DoudizhuSolutionGenerator.generateAllFeijidaierDianShuZu(dianShuAmountArray, length));
		}
		List<DaPaiDianShuSolution> feijidaierSolution = DoudizhuSolutionGenerator
				.calculateFeijidaierDaPaiDianShuSolution(feijidaierList);
		feijidaierSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 四带二单张
		List<SidaiyiDianShuZu> sidaiyiList = DoudizhuSolutionGenerator.generateAllSidaiyiDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sidaiyiSolution = DoudizhuSolutionGenerator
				.calculateSidaiyiDaPaiDianShuSolution(sidaiyiList);
		sidaiyiSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 四带二对子
		List<SidaierDianShuZu> sidaierList = DoudizhuSolutionGenerator.generateAllSidaierDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> sidaierSolution = DoudizhuSolutionGenerator
				.calculateSidaierDaPaiDianShuSolution(sidaierList);
		sidaierSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 单个炸弹点数组
		List<DanGeZhadanDianShuZu> zhadanList = DianShuZuGenerator.generateAllZhadanDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> zhadanSolution = DoudizhuSolutionGenerator
				.calculateDangezhadanDaPaiDianShuSolution(zhadanList);
		zhadanSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		// 王炸
		List<HuojianDianShuZu> huojianList = DoudizhuSolutionGenerator.generateAllHuojianDianShuZu(dianShuAmountArray);
		List<DaPaiDianShuSolution> huojianSolution = DoudizhuSolutionGenerator
				.calculateHuojianDaPaiDianShuSolution(huojianList);
		huojianSolution.forEach((solution) -> {
			kedaPaiSolutions.put(solution.getDianshuZuheIdx(), solution);
		});
		return kedaPaiSolutions;
	}

}
