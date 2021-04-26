package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.pai.dianshuzu.FeijidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.FeijidaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaiyiDianShuZu;
import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.DianShu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;

public class DoudizhuSolutionGenerator {
	/**
	 * 单张打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateDanzhangDaPaiDianShuSolution(
			List<DanzhangDianShuZu> danzhangList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 单张
		for (DanzhangDianShuZu danzhangDianShuZu : danzhangList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(danzhangDianShuZu);
			DianShu[] dachuDianShuArray = { danzhangDianShuZu.getDianShu() };
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 对子打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateDuiziDaPaiDianShuSolution(List<DuiziDianShuZu> duiziList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 对子
		for (DuiziDianShuZu duiziDianShuZu : duiziList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(duiziDianShuZu);
			DianShu[] dachuDianShuArray = { duiziDianShuZu.getDianShu(), duiziDianShuZu.getDianShu() };
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 三张打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSanzhangDaPaiDianShuSolution(
			List<SanzhangDianShuZu> sanzhangList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 三张
		for (SanzhangDianShuZu sanzhangDianShuZu : sanzhangList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sanzhangDianShuZu);
			DianShu[] dachuDianShuArray = { sanzhangDianShuZu.getDianShu(), sanzhangDianShuZu.getDianShu(),
					sanzhangDianShuZu.getDianShu() };
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 顺子打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateShunziDaPaiDianShuSolution(List<ShunziDianShuZu> shunziList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 顺子
		for (ShunziDianShuZu shunziDianShuZu : shunziList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(shunziDianShuZu);
			DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
			DianShu[] dachuDianShuArray = new DianShu[lianXuDianShuArray.length];
			for (int i = 0; i < lianXuDianShuArray.length; i++) {
				dachuDianShuArray[i] = lianXuDianShuArray[i];
			}
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 连对打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateLianduiDaPaiDianShuSolution(List<LianduiDianShuZu> lianduiList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 连对
		for (LianduiDianShuZu lianduiDianShuZu : lianduiList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(lianduiDianShuZu);
			DianShu[] lianXuDianShuArray = lianduiDianShuZu.getLianXuDianShuArray();
			DianShu[] dachuDianShuArray = new DianShu[2 * lianXuDianShuArray.length];
			for (int i = 0; i < lianXuDianShuArray.length; i++) {
				dachuDianShuArray[i * 2] = lianXuDianShuArray[i];
				dachuDianShuArray[i * 2 + 1] = lianXuDianShuArray[i];
			}
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 连三张打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateLiansanzhangDaPaiDianShuSolution(
			List<LiansanzhangDianShuZu> lianSanZhangList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 连三张
		for (LiansanzhangDianShuZu liansanzhangDianShuZu : lianSanZhangList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(liansanzhangDianShuZu);
			DianShu[] lianXuDianShuArray = liansanzhangDianShuZu.getLianXuDianShuArray();
			DianShu[] dachuDianShuArray = new DianShu[3 * lianXuDianShuArray.length];
			for (int i = 0; i < lianXuDianShuArray.length; i++) {
				dachuDianShuArray[i * 3] = lianXuDianShuArray[i];
				dachuDianShuArray[i * 3 + 1] = lianXuDianShuArray[i];
				dachuDianShuArray[i * 3 + 2] = lianXuDianShuArray[i];
			}
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 单个炸弹打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateDangezhadanDaPaiDianShuSolution(
			List<DanGeZhadanDianShuZu> zhadanList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 单个炸弹
		for (DanGeZhadanDianShuZu danGeZhadanDianShuZu : zhadanList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(danGeZhadanDianShuZu);
			DianShu[] dachuDianShuArray = new DianShu[danGeZhadanDianShuZu.getSize()];
			for (int i = 0; i < danGeZhadanDianShuZu.getSize(); i++) {
				dachuDianShuArray[i] = danGeZhadanDianShuZu.getDianShu();
			}
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成火箭
	 */
	public static List<HuojianDianShuZu> generateAllHuojianDianShuZu(int[] dianShuAmountArray) {
		List<HuojianDianShuZu> huojianList = new ArrayList<>();
		if (dianShuAmountArray[13] == 1 && dianShuAmountArray[14] == 1) {
			huojianList.add(new HuojianDianShuZu());
		}
		return huojianList;
	}

	/**
	 * 火箭打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateHuojianDaPaiDianShuSolution(List<HuojianDianShuZu> huojianList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 火箭
		for (HuojianDianShuZu huojianDianShuZu : huojianList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(huojianDianShuZu);
			DianShu[] dachuDianShuArray = { DianShu.xiaowang, DianShu.dawang };
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成带单张的飞机
	 */
	public static List<FeijidaiyiDianShuZu> generateAllFeijidaiyiDianShuZu(int[] dianShuAmountArray, int length) {
		List<FeijidaiyiDianShuZu> feijidaiyiList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int danzhangLianXuCount = 0;
			int j = i;
			while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 3) {// 2和大、小王无法参与
				danzhangLianXuCount++;
				j++;
			}
			if (danzhangLianXuCount >= length) {
				int[] amountArray = dianShuAmountArray.clone();
				DianShu[] lianXuDianShuArray = new DianShu[length];
				for (int k = 0; k < length; k++) {
					lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i + k);
					amountArray[i + k] -= 3;
				}
				List<DianShu> chibangList = new ArrayList<>();
				generateFeijidaiyiDianShuZuChibang(feijidaiyiList, lianXuDianShuArray, chibangList, amountArray, 0, 0,
						length);
			}
		}
		return feijidaiyiList;
	}

	private static void generateFeijidaiyiDianShuZuChibang(List<FeijidaiyiDianShuZu> feijidaiyiList,
			DianShu[] lianXuDianShuArray, List<DianShu> chibangList, int[] dianShuAmountArray, int start, int count,
			int length) {
		if (chibangList.size() < length) {
			for (int i = start; i < dianShuAmountArray.length; i++) {
				if (dianShuAmountArray[i] > 0) {
					List<DianShu> newChibangList = new ArrayList<>(chibangList);
					newChibangList.add(DianShu.getDianShuByOrdinal(i));
					int[] amountArray = dianShuAmountArray.clone();
					amountArray[i] -= 1;
					generateFeijidaiyiDianShuZuChibang(feijidaiyiList, lianXuDianShuArray, newChibangList, amountArray,
							i, count + 1, length);
				}
			}
		} else {
			DianShu[] chibangArray = new DianShu[chibangList.size()];
			feijidaiyiList.add(new FeijidaiyiDianShuZu(lianXuDianShuArray, chibangList.toArray(chibangArray)));
		}
	}

	/**
	 * 带单牌的飞机打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateFeijidaiyiDaPaiDianShuSolution(
			List<FeijidaiyiDianShuZu> feijidaiyiList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 单牌的飞机
		for (FeijidaiyiDianShuZu feijidaiyiDianShuZu : feijidaiyiList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(feijidaiyiDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			for (DianShu dianshu : feijidaiyiDianShuZu.getLianxuDianshuArray()) {
				dachuDianShuList.add(dianshu);
				dachuDianShuList.add(dianshu);
				dachuDianShuList.add(dianshu);
			}
			for (DianShu dianshu : feijidaiyiDianShuZu.getChibangArray()) {
				dachuDianShuList.add(dianshu);
			}
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成带对子的飞机
	 */
	public static List<FeijidaierDianShuZu> generateAllFeijidaierDianShuZu(int[] dianShuAmountArray, int length) {
		List<FeijidaierDianShuZu> feijidaierList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int danzhangLianXuCount = 0;
			int j = i;
			while (danzhangLianXuCount < length && j < 12 && dianShuAmountArray[j] >= 3) {// 2和大、小王无法参与
				danzhangLianXuCount++;
				j++;
			}
			if (danzhangLianXuCount >= length) {
				int[] amountArray = dianShuAmountArray.clone();
				DianShu[] lianXuDianShuArray = new DianShu[length];
				for (int k = 0; k < length; k++) {
					lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(i + k);
					amountArray[i + k] -= 3;
				}
				List<DianShu> chibangList = new ArrayList<>();
				generateFeijidaierDianShuZuChibang(feijidaierList, lianXuDianShuArray, chibangList, amountArray, 0, 0,
						length);
			}
		}
		return feijidaierList;
	}

	private static void generateFeijidaierDianShuZuChibang(List<FeijidaierDianShuZu> feijidaierList,
			DianShu[] lianXuDianShuArray, List<DianShu> chibangList, int[] dianShuAmountArray, int start, int count,
			int length) {
		if (chibangList.size() < length) {
			for (int i = start; i < dianShuAmountArray.length; i++) {
				if (dianShuAmountArray[i] > 1) {
					List<DianShu> newChibangList = new ArrayList<>(chibangList);
					newChibangList.add(DianShu.getDianShuByOrdinal(i));
					int[] amountArray = dianShuAmountArray.clone();
					amountArray[i] -= 2;
					generateFeijidaierDianShuZuChibang(feijidaierList, lianXuDianShuArray, newChibangList, amountArray,
							i, count + 1, length);
				}
			}
		} else {
			DianShu[] chibangArray = new DianShu[chibangList.size()];
			feijidaierList.add(new FeijidaierDianShuZu(lianXuDianShuArray, chibangList.toArray(chibangArray)));
		}
	}

	/**
	 * 带对子的飞机打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateFeijidaierDaPaiDianShuSolution(
			List<FeijidaierDianShuZu> feijidaierList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 带对子的飞机
		for (FeijidaierDianShuZu feijidaierDianShuZu : feijidaierList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(feijidaierDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			for (DianShu dianshu : feijidaierDianShuZu.getLianxuDianshuArray()) {
				dachuDianShuList.add(dianshu);
				dachuDianShuList.add(dianshu);
				dachuDianShuList.add(dianshu);
			}
			for (DianShu dianshu : feijidaierDianShuZu.getChibangArray()) {
				dachuDianShuList.add(dianshu);
				dachuDianShuList.add(dianshu);
			}
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成三带一点数组
	 */
	public static List<SandaiyiDianShuZu> generateAllSandaiyiDianShuZu(int[] dianShuAmountArray) {
		List<SandaiyiDianShuZu> sandaiyiList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int dianshuCount = dianShuAmountArray[i];
			if (dianshuCount >= 3) {
				int[] amountArray = dianShuAmountArray.clone();
				amountArray[i] -= 3;
				for (int j = 0; j < amountArray.length; j++) {
					if (amountArray[j] > 0) {
						DianShu dianshu = DianShu.getDianShuByOrdinal(i);
						DianShu chibang = DianShu.getDianShuByOrdinal(j);
						SandaiyiDianShuZu sandaiyiDianShuZu = new SandaiyiDianShuZu(dianshu, chibang);
						sandaiyiList.add(sandaiyiDianShuZu);
					}
				}
			}
		}
		return sandaiyiList;
	}

	/**
	 * 三带一打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSandaiyiDaPaiDianShuSolution(
			List<SandaiyiDianShuZu> sandaiyiList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 三带一
		for (SandaiyiDianShuZu sandaiyiDianShuZu : sandaiyiList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sandaiyiDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			dachuDianShuList.add(sandaiyiDianShuZu.getDianshu());
			dachuDianShuList.add(sandaiyiDianShuZu.getDianshu());
			dachuDianShuList.add(sandaiyiDianShuZu.getDianshu());

			dachuDianShuList.add(sandaiyiDianShuZu.getChibang());
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成三带二点数组
	 */
	public static List<SandaierDianShuZu> generateAllSandaierDianShuZu(int[] dianShuAmountArray) {
		List<SandaierDianShuZu> sandaierList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int dianshuCount = dianShuAmountArray[i];
			if (dianshuCount >= 3) {
				int[] amountArray = dianShuAmountArray.clone();
				amountArray[i] -= 3;
				for (int j = 0; j < amountArray.length; j++) {
					if (amountArray[j] > 1) {
						DianShu dianshu = DianShu.getDianShuByOrdinal(i);
						DianShu chibang = DianShu.getDianShuByOrdinal(j);
						SandaierDianShuZu sandaierDianShuZu = new SandaierDianShuZu(dianshu, chibang);
						sandaierList.add(sandaierDianShuZu);
					}
				}
			}
		}
		return sandaierList;
	}

	/**
	 * 三带二打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSandaierDaPaiDianShuSolution(
			List<SandaierDianShuZu> sandaierList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 三带二
		for (SandaierDianShuZu sandaierDianShuZu : sandaierList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sandaierDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());

			dachuDianShuList.add(sandaierDianShuZu.getChibang());
			dachuDianShuList.add(sandaierDianShuZu.getChibang());
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成三带对王点数组
	 */
	public static List<SandaierDianShuZu> generateAllSandaiduiwangDianShuZu(int[] dianShuAmountArray) {
		List<SandaierDianShuZu> sandaierList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int dianshuCount = dianShuAmountArray[i];
			if (dianshuCount >= 3) {
				int[] amountArray = dianShuAmountArray.clone();
				amountArray[i] -= 3;
				if (amountArray[13] == 1 && amountArray[14] == 1) {
					DianShu dianshu = DianShu.getDianShuByOrdinal(i);
					DianShu chibang = DianShu.getDianShuByOrdinal(14);
					SandaierDianShuZu sandaierDianShuZu = new SandaierDianShuZu(dianshu, chibang);
					sandaierList.add(sandaierDianShuZu);
				}
			}
		}
		return sandaierList;
	}

	/**
	 * 三带对王打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSandaiduiwangDaPaiDianShuSolution(
			List<SandaierDianShuZu> sandaierList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 三带二
		for (SandaierDianShuZu sandaierDianShuZu : sandaierList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sandaierDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());
			dachuDianShuList.add(sandaierDianShuZu.getDianshu());

			dachuDianShuList.add(DianShu.xiaowang);
			dachuDianShuList.add(DianShu.dawang);
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成四带二单张点数组
	 */
	public static List<SidaiyiDianShuZu> generateAllSidaiyiDianShuZu(int[] dianShuAmountArray) {
		List<SidaiyiDianShuZu> sidaierList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int dianshuCount = dianShuAmountArray[i];
			if (dianshuCount >= 4) {
				int[] amountArray = dianShuAmountArray.clone();
				amountArray[i] -= 4;
				for (int j = 0; j < amountArray.length; j++) {
					if (amountArray[j] > 0) {
						int[] amountArray1 = amountArray.clone();
						amountArray1[j] -= 1;
						for (int k = 0; k < amountArray1.length; k++) {
							if (amountArray1[k] > 0) {
								DianShu dianshu = DianShu.getDianShuByOrdinal(i);
								DianShu chibang = DianShu.getDianShuByOrdinal(j);
								DianShu chibanger = DianShu.getDianShuByOrdinal(k);
								SidaiyiDianShuZu sidaiyiDianShuZu = new SidaiyiDianShuZu(dianshu, chibang, chibanger);
								sidaierList.add(sidaiyiDianShuZu);
							}
						}
					}
				}
			}
		}
		return sidaierList;
	}

	/**
	 * 四带二单张打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSidaiyiDaPaiDianShuSolution(List<SidaiyiDianShuZu> sidaiyiList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 四带二单张
		for (SidaiyiDianShuZu sidaiyiDianShuZu : sidaiyiList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sidaiyiDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			dachuDianShuList.add(sidaiyiDianShuZu.getDianshu());
			dachuDianShuList.add(sidaiyiDianShuZu.getDianshu());
			dachuDianShuList.add(sidaiyiDianShuZu.getDianshu());
			dachuDianShuList.add(sidaiyiDianShuZu.getDianshu());

			dachuDianShuList.add(sidaiyiDianShuZu.getChibang());
			dachuDianShuList.add(sidaiyiDianShuZu.getChibanger());
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}

	/**
	 * 生成四带二对子点数组
	 */
	public static List<SidaierDianShuZu> generateAllSidaierDianShuZu(int[] dianShuAmountArray) {
		List<SidaierDianShuZu> sidaierList = new ArrayList<>();
		for (int i = 0; i < dianShuAmountArray.length; i++) {
			int dianshuCount = dianShuAmountArray[i];
			if (dianshuCount >= 4) {
				int[] amountArray = dianShuAmountArray.clone();
				amountArray[i] -= 4;
				for (int j = 0; j < amountArray.length; j++) {
					if (amountArray[j] > 1) {
						int[] amountArray1 = amountArray.clone();
						amountArray1[j] -= 2;
						for (int k = 0; k < amountArray1.length; k++) {
							if (amountArray1[k] > 1) {
								DianShu dianshu = DianShu.getDianShuByOrdinal(i);
								DianShu chibang = DianShu.getDianShuByOrdinal(j);
								DianShu chibanger = DianShu.getDianShuByOrdinal(k);
								SidaierDianShuZu sidaierDianShuZu = new SidaierDianShuZu(dianshu, chibang, chibanger);
								sidaierList.add(sidaierDianShuZu);
							}
						}
					}
				}
			}
		}
		return sidaierList;
	}

	/**
	 * 四带二对子打牌方案
	 */
	public static List<DaPaiDianShuSolution> calculateSidaierDaPaiDianShuSolution(List<SidaierDianShuZu> sidaierList) {
		List<DaPaiDianShuSolution> solutionList = new ArrayList<>();
		// 四带二对子
		for (SidaierDianShuZu sidaierDianShuZu : sidaierList) {
			DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
			solution.setDianShuZu(sidaierDianShuZu);
			List<DianShu> dachuDianShuList = new ArrayList<>();
			dachuDianShuList.add(sidaierDianShuZu.getDianshu());
			dachuDianShuList.add(sidaierDianShuZu.getDianshu());
			dachuDianShuList.add(sidaierDianShuZu.getDianshu());
			dachuDianShuList.add(sidaierDianShuZu.getDianshu());

			dachuDianShuList.add(sidaierDianShuZu.getChibang());
			dachuDianShuList.add(sidaierDianShuZu.getChibang());
			dachuDianShuList.add(sidaierDianShuZu.getChibanger());
			dachuDianShuList.add(sidaierDianShuZu.getChibanger());
			DianShu[] dachuDianShuArray = new DianShu[dachuDianShuList.size()];
			dachuDianShuList.toArray(dachuDianShuArray);
			solution.setDachuDianShuArray(dachuDianShuArray);
			solution.calculateDianshuZuheIdx();
			solutionList.add(solution);
		}
		return solutionList;
	}
}
