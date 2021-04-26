package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.pai.dianshuzu.FeijidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.FeijidaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.HuojianDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SandaiyiDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaierDianShuZu;
import com.dml.doudizhu.pai.dianshuzu.SidaiyiDianShuZu;
import com.dml.doudizhu.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;

public class DoudizhuYaPaiSolutionsTipsFilter implements YaPaiSolutionsTipsFilter {

	@Override
	public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> YaPaiSolutions,
			Map<Integer, PukePai> allShoupai) {
		int[] dianshuCountArray = new int[15];
		for (PukePai pukePai : allShoupai.values()) {
			DianShu dianShu = pukePai.getPaiMian().dianShu();
			dianshuCountArray[dianShu.ordinal()]++;
		}
		List<DaPaiDianShuSolution> filtedSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> danzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> duiziSolutionList = new LinkedList<>();
		// 单张至两张的单张
		LinkedList<DaPaiDianShuSolution> danzhangYiShangSolutionList = new LinkedList<>();
		// 两张以上的单张
		LinkedList<DaPaiDianShuSolution> danzhangDuiziYiShangSolutionList = new LinkedList<>();
		// 有三张的对子
		LinkedList<DaPaiDianShuSolution> duiziYiShangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sanzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sandaiyiSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sandaierSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sidaiyiSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> sidaierSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> shunziSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> lianduiSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> liansanzhangSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> feijidaiyiSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> feijidaierSolutionList = new LinkedList<>();
		LinkedList<DaPaiDianShuSolution> zhadanSolutionList = new LinkedList<>();
		for (DaPaiDianShuSolution solution : YaPaiSolutions) {
			DianShuZu dianshuZu = solution.getDianShuZu();
			// 飞机带对子
			if (dianshuZu instanceof FeijidaierDianShuZu) {
				FeijidaierDianShuZu feijidaierDianShuZu = (FeijidaierDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = feijidaierDianShuZu.getLianxuDianshuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] != 3) {
						allSuccess = false;
						break;
					}
				}
				for (DianShu dianshu : feijidaierDianShuZu.getChibangArray()) {
					if (dianshuCountArray[dianshu.ordinal()] == 4) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (feijidaierSolutionList.isEmpty()) {
						feijidaierSolutionList.add(solution);
					} else {
						int length = feijidaierSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((FeijidaierDianShuZu) feijidaierSolutionList.get(i).getDianShuZu())
									.getLianxuDianshuArray()[0]
											.compareTo(feijidaierDianShuZu.getLianxuDianshuArray()[0]);
							if (compare > 0) {
								feijidaierSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((FeijidaierDianShuZu) feijidaierSolutionList.get(i).getDianShuZu())
										.getChibangArray()[0].compareTo(feijidaierDianShuZu.getChibangArray()[0]) > 0) {
									feijidaierSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								feijidaierSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 飞机带单张
			if (dianshuZu instanceof FeijidaiyiDianShuZu) {
				FeijidaiyiDianShuZu feijidaiyiDianShuZu = (FeijidaiyiDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = feijidaiyiDianShuZu.getLianxuDianshuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] != 3) {
						allSuccess = false;
						break;
					}
				}
				for (DianShu dianshu : feijidaiyiDianShuZu.getChibangArray()) {
					if (dianshuCountArray[dianshu.ordinal()] == 4) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (feijidaiyiSolutionList.isEmpty()) {
						feijidaiyiSolutionList.add(solution);
					} else {
						int length = feijidaiyiSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((FeijidaiyiDianShuZu) feijidaiyiSolutionList.get(i).getDianShuZu())
									.getLianxuDianshuArray()[0]
											.compareTo(feijidaiyiDianShuZu.getLianxuDianshuArray()[0]);
							if (compare > 0) {
								feijidaiyiSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((FeijidaiyiDianShuZu) feijidaiyiSolutionList.get(i).getDianShuZu())
										.getChibangArray()[0].compareTo(feijidaiyiDianShuZu.getChibangArray()[0]) > 0) {
									feijidaiyiSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								feijidaiyiSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 四带二对子
			if (dianshuZu instanceof SidaierDianShuZu) {
				SidaierDianShuZu sidaierDianShuZu = (SidaierDianShuZu) dianshuZu;
				DianShu chibang = sidaierDianShuZu.getChibang();
				DianShu chibanger = sidaierDianShuZu.getChibanger();
				if (dianshuCountArray[chibang.ordinal()] < 4 && dianshuCountArray[chibanger.ordinal()] < 4) {
					if (sidaierSolutionList.isEmpty()) {
						sidaierSolutionList.add(solution);
					} else {
						int length = sidaierSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((SidaierDianShuZu) sidaierSolutionList.get(i).getDianShuZu()).getDianshu()
									.compareTo(sidaierDianShuZu.getDianshu());
							if (compare > 0) {
								sidaierSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((SidaierDianShuZu) sidaierSolutionList.get(i).getDianShuZu()).getChibang()
										.compareTo(sidaierDianShuZu.getChibang()) > 0) {
									sidaierSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								sidaierSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 四带二单牌
			if (dianshuZu instanceof SidaiyiDianShuZu) {
				SidaiyiDianShuZu sidaiyiDianShuZu = (SidaiyiDianShuZu) dianshuZu;
				DianShu chibang = sidaiyiDianShuZu.getChibang();
				DianShu chibanger = sidaiyiDianShuZu.getChibanger();
				if (dianshuCountArray[chibang.ordinal()] < 4 && dianshuCountArray[chibanger.ordinal()] < 4) {
					if (sidaiyiSolutionList.isEmpty()) {
						sidaiyiSolutionList.add(solution);
					} else {
						int length = sidaiyiSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((SidaiyiDianShuZu) sidaiyiSolutionList.get(i).getDianShuZu()).getDianshu()
									.compareTo(sidaiyiDianShuZu.getDianshu());
							if (compare > 0) {
								sidaiyiSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((SidaiyiDianShuZu) sidaiyiSolutionList.get(i).getDianShuZu()).getChibang()
										.compareTo(sidaiyiDianShuZu.getChibang()) > 0) {
									sidaiyiSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								sidaiyiSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 三带二
			if (dianshuZu instanceof SandaierDianShuZu) {
				SandaierDianShuZu sandaierDianShuZu = (SandaierDianShuZu) dianshuZu;
				DianShu dianshu = sandaierDianShuZu.getDianshu();
				if (dianshuCountArray[dianshu.ordinal()] == 3) {
					if (sandaierSolutionList.isEmpty()) {
						sandaierSolutionList.add(solution);
					} else {
						int length = sandaierSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((SandaierDianShuZu) sandaierSolutionList.get(i).getDianShuZu()).getDianshu()
									.compareTo(sandaierDianShuZu.getDianshu());
							if (compare > 0) {
								sandaierSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((SandaierDianShuZu) sandaierSolutionList.get(i).getDianShuZu()).getChibang()
										.compareTo(sandaierDianShuZu.getChibang()) > 0) {
									sandaierSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								sandaierSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 三带一
			if (dianshuZu instanceof SandaiyiDianShuZu) {
				SandaiyiDianShuZu sandaiyiDianShuZu = (SandaiyiDianShuZu) dianshuZu;
				DianShu dianshu = sandaiyiDianShuZu.getDianshu();
				if (dianshuCountArray[dianshu.ordinal()] == 3) {
					if (sandaiyiSolutionList.isEmpty()) {
						sandaiyiSolutionList.add(solution);
					} else {
						int length = sandaiyiSolutionList.size();
						int i = 0;
						while (i < length) {
							int compare = ((SandaiyiDianShuZu) sandaiyiSolutionList.get(i).getDianShuZu()).getDianshu()
									.compareTo(sandaiyiDianShuZu.getDianshu());
							if (compare > 0) {
								sandaiyiSolutionList.add(i, solution);
								break;
							}
							if (compare == 0) {
								if (((SandaiyiDianShuZu) sandaiyiSolutionList.get(i).getDianShuZu()).getChibang()
										.compareTo(sandaiyiDianShuZu.getChibang()) > 0) {
									sandaiyiSolutionList.add(i, solution);
									break;
								}
							}
							if (i == length - 1) {
								sandaiyiSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 三张
			if (dianshuZu instanceof SanzhangDianShuZu) {
				SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianshuZu;
				DianShu dianshu = sanzhangDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 3) {
					if (sanzhangSolutionList.isEmpty()) {
						sanzhangSolutionList.add(solution);
					} else {
						int length = sanzhangSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((SanzhangDianShuZu) sanzhangSolutionList.get(i).getDianShuZu()).getDianShu()
									.compareTo(sanzhangDianShuZu.getDianShu()) > 0) {
								sanzhangSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								sanzhangSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 对子
			if (dianshuZu instanceof DuiziDianShuZu) {
				DuiziDianShuZu duiziDianShuZu = (DuiziDianShuZu) dianshuZu;
				DianShu dianshu = duiziDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] < 4) {
					if (dianshuCountArray[dianshu.ordinal()] > 2) {
						if (duiziYiShangSolutionList.isEmpty()) {
							duiziYiShangSolutionList.add(solution);
						} else {
							int length1 = duiziYiShangSolutionList.size();
							int j = 0;
							while (j < length1) {
								if (((DuiziDianShuZu) duiziYiShangSolutionList.get(j).getDianShuZu()).getDianShu()
										.compareTo(duiziDianShuZu.getDianShu()) > 0) {
									duiziYiShangSolutionList.add(j, solution);
									break;
								}
								if (j == length1 - 1) {
									duiziYiShangSolutionList.add(solution);
								}
								j++;
							}
						}
					} else {
						if (duiziSolutionList.isEmpty()) {
							duiziSolutionList.add(solution);
						} else {
							int length = duiziSolutionList.size();
							int i = 0;
							while (i < length) {
								if (((DuiziDianShuZu) duiziSolutionList.get(i).getDianShuZu()).getDianShu()
										.compareTo(duiziDianShuZu.getDianShu()) > 0) {
									duiziSolutionList.add(i, solution);
									break;
								}
								if (i == length - 1) {
									duiziSolutionList.add(solution);
								}
								i++;
							}
						}
					}
				}
			}
			// 单张
			if (dianshuZu instanceof DanzhangDianShuZu) {
				DanzhangDianShuZu danzhangDianShuZu = (DanzhangDianShuZu) dianshuZu;
				DianShu dianshu = danzhangDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] < 4) {
					if (dianshuCountArray[dianshu.ordinal()] > 2) {
						if (danzhangDuiziYiShangSolutionList.isEmpty()) {
							danzhangDuiziYiShangSolutionList.add(solution);
						} else {
							int length1 = danzhangDuiziYiShangSolutionList.size();
							int j = 0;
							while (j < length1) {
								if (((DanzhangDianShuZu) danzhangDuiziYiShangSolutionList.get(j).getDianShuZu())
										.getDianShu().compareTo(danzhangDianShuZu.getDianShu()) > 0) {

									danzhangDuiziYiShangSolutionList.add(j, solution);
									break;
								}
								if (j == length1 - 1) {
									danzhangDuiziYiShangSolutionList.add(solution);
								}
								j++;
							}
						}
					} else if (dianshuCountArray[dianshu.ordinal()] > 1) {
						if (danzhangYiShangSolutionList.isEmpty()) {
							danzhangYiShangSolutionList.add(solution);
						} else {
							int length1 = danzhangYiShangSolutionList.size();
							int j = 0;
							while (j < length1) {
								if (((DanzhangDianShuZu) danzhangYiShangSolutionList.get(j).getDianShuZu()).getDianShu()
										.compareTo(danzhangDianShuZu.getDianShu()) > 0) {
									danzhangYiShangSolutionList.add(j, solution);
									break;
								}
								if (j == length1 - 1) {
									danzhangYiShangSolutionList.add(solution);
								}
								j++;
							}
						}
					} else {
						if ((dianshu.equals(DianShu.xiaowang) || dianshu.equals(DianShu.dawang))
								&& (dianshuCountArray[13] == 1 && dianshuCountArray[14] == 1)) {// 有火箭
						} else {
							if (danzhangSolutionList.isEmpty()) {
								danzhangSolutionList.add(solution);
							} else {
								int length = danzhangSolutionList.size();
								int i = 0;
								while (i < length) {
									if (((DanzhangDianShuZu) danzhangSolutionList.get(i).getDianShuZu()).getDianShu()
											.compareTo(danzhangDianShuZu.getDianShu()) > 0) {
										danzhangSolutionList.add(i, solution);
										break;
									}
									if (i == length - 1) {
										danzhangSolutionList.add(solution);
									}
									i++;
								}
							}
						}
					}
				}
			}
			// 连三张
			if (dianshuZu instanceof LiansanzhangDianShuZu) {
				LiansanzhangDianShuZu liansanzhangDianShuZu = (LiansanzhangDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = liansanzhangDianShuZu.getLianXuDianShuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] == 4) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (liansanzhangSolutionList.isEmpty()) {
						liansanzhangSolutionList.add(solution);
					} else {
						int length = liansanzhangSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((LiansanzhangDianShuZu) liansanzhangSolutionList.get(i).getDianShuZu())
									.getLianXuDianShuArray()[0]
											.compareTo(liansanzhangDianShuZu.getLianXuDianShuArray()[0]) > 0) {
								liansanzhangSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								liansanzhangSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 连对
			if (dianshuZu instanceof LianduiDianShuZu) {
				LianduiDianShuZu lianduiDianShuZu = (LianduiDianShuZu) dianshuZu;
				DianShu[] lianXuDianShuArray = lianduiDianShuZu.getLianXuDianShuArray();
				boolean allSuccess = true;
				for (DianShu dianshu : lianXuDianShuArray) {
					if (dianshuCountArray[dianshu.ordinal()] == 4) {
						allSuccess = false;
						break;
					}
				}
				if (allSuccess) {
					if (lianduiSolutionList.isEmpty()) {
						lianduiSolutionList.add(solution);
					} else {
						int length = lianduiSolutionList.size();
						int i = 0;
						while (i < length) {
							if (((LianduiDianShuZu) lianduiSolutionList.get(i).getDianShuZu())
									.getLianXuDianShuArray()[0]
											.compareTo(lianduiDianShuZu.getLianXuDianShuArray()[0]) > 0) {
								lianduiSolutionList.add(i, solution);
								break;
							}
							if (i == length - 1) {
								lianduiSolutionList.add(solution);
							}
							i++;
						}
					}
				}
			}
			// 顺子
			if (dianshuZu instanceof ShunziDianShuZu) {
				ShunziDianShuZu shunziDianShuZu = (ShunziDianShuZu) dianshuZu;
				if (shunziSolutionList.isEmpty()) {
					shunziSolutionList.add(solution);
				} else {
					int length = shunziSolutionList.size();
					int i = 0;
					while (i < length) {
						if (((ShunziDianShuZu) shunziSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0]
								.compareTo(shunziDianShuZu.getLianXuDianShuArray()[0]) > 0) {
							shunziSolutionList.add(i, solution);
							break;
						}
						if (i == length - 1) {
							shunziSolutionList.add(solution);
						}
						i++;
					}
				}
			}
			// 单个炸弹
			if (dianshuZu instanceof DanGeZhadanDianShuZu) {
				DanGeZhadanDianShuZu danGeZhadanDianShuZu = (DanGeZhadanDianShuZu) dianshuZu;
				DianShu dianshu = danGeZhadanDianShuZu.getDianShu();
				if (dianshuCountArray[dianshu.ordinal()] == 4) {
					if (zhadanSolutionList.isEmpty()) {
						zhadanSolutionList.add(solution);
					} else {
						int length = zhadanSolutionList.size();
						int i = 0;
						while (i < length) {
							if (zhadanSolutionList.get(i).getDianShuZu() instanceof DanGeZhadanDianShuZu) {
								if (((DanGeZhadanDianShuZu) zhadanSolutionList.get(i).getDianShuZu()).getDianShu()
										.compareTo(danGeZhadanDianShuZu.getDianShu()) > 0) {
									zhadanSolutionList.add(i, solution);
									break;
								}
								if (i == length - 1) {
									zhadanSolutionList.add(solution);
								}
							}
							i++;
						}
					}
				}
			}
			// 火箭
			if (dianshuZu instanceof HuojianDianShuZu) {
				zhadanSolutionList.add(solution);
			}
		}
		filtedSolutionList.addAll(danzhangSolutionList);
		filtedSolutionList.addAll(danzhangYiShangSolutionList);
		filtedSolutionList.addAll(danzhangDuiziYiShangSolutionList);
		filtedSolutionList.addAll(duiziSolutionList);
		filtedSolutionList.addAll(duiziYiShangSolutionList);
		filtedSolutionList.addAll(sanzhangSolutionList);
		filtedSolutionList.addAll(sandaiyiSolutionList);
		filtedSolutionList.addAll(sandaierSolutionList);
		filtedSolutionList.addAll(sidaiyiSolutionList);
		filtedSolutionList.addAll(sidaierSolutionList);
		filtedSolutionList.addAll(shunziSolutionList);
		filtedSolutionList.addAll(lianduiSolutionList);
		filtedSolutionList.addAll(liansanzhangSolutionList);
		filtedSolutionList.addAll(feijidaiyiSolutionList);
		filtedSolutionList.addAll(feijidaierSolutionList);
		filtedSolutionList.addAll(zhadanSolutionList);
		return filtedSolutionList;
	}

}
