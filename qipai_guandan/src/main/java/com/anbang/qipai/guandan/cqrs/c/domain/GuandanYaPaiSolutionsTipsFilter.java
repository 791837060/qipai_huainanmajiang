package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.dml.puke.wanfa.dianshu.dianshuzu.ZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.comparator.ZhadanComparator;
import com.dml.shuangkou.pai.dianshuzu.LianXuZhadanDianShuZu;
import com.dml.shuangkou.pai.dianshuzu.SandaierDianShuZu;
import com.dml.shuangkou.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.wanfa.BianXingWanFa;

/**
 * 提示顺序： 没有王牌代替的打法、不拆炸弹、同种类型、
 *
 * @author lsc
 */
public class GuandanYaPaiSolutionsTipsFilter implements YaPaiSolutionsTipsFilter {

    private ZhadanComparator zhadanComparator;

    private BianXingWanFa bx;

    @Override
    public List<DaPaiDianShuSolution> filter(List<DaPaiDianShuSolution> YaPaiSolutions, Map<Integer, PukePai> allShoupai, boolean yapai) {
        int[] dianshuCountArray = new int[16];
        for (PukePai pukePai : allShoupai.values()) {
            DianShu dianShu = pukePai.getPaiMian().dianShu();
            dianshuCountArray[dianShu.ordinal()]++;
        }

        List<DaPaiDianShuSolution> filtedSolutionList = new LinkedList<>();
        List<DaPaiDianShuSolution> noWangSolutionList = new ArrayList<>();
        List<DaPaiDianShuSolution> hasWangSolutionList = new ArrayList<>();

        f1:
        for (DaPaiDianShuSolution solution : YaPaiSolutions) {
            for (DianShu dianshu : solution.getDachuDianShuArray()) {
//                if (DianShu.xiaowang.equals(dianshu) || DianShu.dawang.equals(dianshu)) {
//                    hasWangSolutionList.add(solution);
//                    continue f1;
//                }
                if (DianShu.hongxiner2.equals(dianshu)) {
                    hasWangSolutionList.add(solution);
                    continue f1;
                }
            }
            noWangSolutionList.add(solution);
        }

        LinkedList<DaPaiDianShuSolution> danGeZhadanSolutionList = new LinkedList<>();
        for (DaPaiDianShuSolution solution : noWangSolutionList) {    //没有王的炸弹
            DianShuZu dianshuZu = solution.getDianShuZu();
            if (dianshuZu instanceof DanGeZhadanDianShuZu) {
                DanGeZhadanDianShuZu danGeZhadanDianShuZu1 = (DanGeZhadanDianShuZu) dianshuZu;
                DianShu dianshu = danGeZhadanDianShuZu1.getDianShu();
                if (danGeZhadanDianShuZu1.getSize() == dianshuCountArray[dianshu.ordinal()]) {
                    if (danGeZhadanSolutionList.isEmpty()) {
                        danGeZhadanSolutionList.add(solution);
                    } else {
                        int length = danGeZhadanSolutionList.size();
                        for (int i = 0; i < length; i++) {
                            DanGeZhadanDianShuZu danGeZhadanDianShuZu2 = (DanGeZhadanDianShuZu) danGeZhadanSolutionList.get(i).getDianShuZu();
                            int compare = danGeZhadanDianShuZu2.getDianShu().compareTo(dianshu);
                            if (compare > 0) {
                                danGeZhadanSolutionList.add(i, solution);
                                break;
                            }
                            if (i == length - 1) {
                                danGeZhadanSolutionList.add(solution);
                            }
                        }
                    }
                }
            }
        }

        LinkedList<DaPaiDianShuSolution> zhadanSolutionList = new LinkedList<>();
        for (DaPaiDianShuSolution zhadanSolution : danGeZhadanSolutionList) {    //炸弹排序
            if (zhadanSolutionList.isEmpty()) {
                zhadanSolutionList.add(zhadanSolution);
            } else {
                DanGeZhadanDianShuZu danGeZhadanDianShuZu1 = (DanGeZhadanDianShuZu) zhadanSolution.getDianShuZu();
                int length = zhadanSolutionList.size();
                for (int i = 0; i < length; i++) {
                    DanGeZhadanDianShuZu danGeZhadanDianShuZu2 = (DanGeZhadanDianShuZu) zhadanSolutionList.get(i).getDianShuZu();
                    if (danGeZhadanDianShuZu1.getSize() < danGeZhadanDianShuZu2.getSize()) {
                        zhadanSolutionList.add(i, zhadanSolution);
                        break;
                    }
                    if (i == length - 1) {
                        zhadanSolutionList.add(zhadanSolution);
                    }
                }
            }
        }

        LinkedList<DaPaiDianShuSolution> noWangDanzhangSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangDuiziSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangSanzhangSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangShunziSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangSandaierSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangLianduiSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> noWangLiansanzhangSolutionList = new LinkedList<>();

        // 单张至两张的单张
        LinkedList<DaPaiDianShuSolution> noWangDanzhangYiShangSolutionList = new LinkedList<>();
        // 两张以上的单张
        LinkedList<DaPaiDianShuSolution> noWangDanzhangDuiziYiShangSolutionList = new LinkedList<>();
        // 有三张的对子
        LinkedList<DaPaiDianShuSolution> noWangDuiziYiShangSolutionList = new LinkedList<>();

        LinkedList<DaPaiDianShuSolution> hasWangDanzhangSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangDuiziSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangSanzhangSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangShunziSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangSandaierSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangLianduiSolutionList = new LinkedList<>();
        LinkedList<DaPaiDianShuSolution> hasWangLiansanzhangSolutionList = new LinkedList<>();

        if (!yapai) {
            for (DaPaiDianShuSolution solution : noWangSolutionList) {
                DianShuZu dianshuZu = solution.getDianShuZu();
                // 三张
                if (dianshuZu instanceof SanzhangDianShuZu) {
                    SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianshuZu;
                    DianShu dianshu = sanzhangDianShuZu.getDianShu();
                    if (dianshuCountArray[dianshu.ordinal()] == 3) {
                        if (noWangSanzhangSolutionList.isEmpty()) {
                            noWangSanzhangSolutionList.add(solution);
                        } else {
                            int length = noWangSanzhangSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((SanzhangDianShuZu) noWangSanzhangSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(sanzhangDianShuZu.getDianShu()) > 0) {
                                    noWangSanzhangSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangSanzhangSolutionList.add(solution);
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
                    if (dianshuCountArray[dianshu.ordinal()] == 2) {
                        if (noWangDuiziSolutionList.isEmpty()) {
                            noWangDuiziSolutionList.add(solution);
                        } else {
                            int length = noWangDuiziSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((DuiziDianShuZu) noWangDuiziSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(duiziDianShuZu.getDianShu()) > 0) {
                                    noWangDuiziSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangDuiziSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
                // 单张
                if (dianshuZu instanceof DanzhangDianShuZu) {
                    DanzhangDianShuZu danzhangDianShuZu = (DanzhangDianShuZu) dianshuZu;
                    DianShu dianshu = danzhangDianShuZu.getDianShu();
                    if (dianshuCountArray[dianshu.ordinal()] == 1) {
                        if (noWangDanzhangSolutionList.isEmpty()) {
                            noWangDanzhangSolutionList.add(solution);
                        } else {
                            int length = noWangDanzhangSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((DanzhangDianShuZu) noWangDanzhangSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(danzhangDianShuZu.getDianShu()) > 0) {
                                    noWangDanzhangSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangDanzhangSolutionList.add(solution);
                                }
                                i++;
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
                        if (dianshuCountArray[dianshu.ordinal()] < 3) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangLiansanzhangSolutionList.isEmpty()) {
                            noWangLiansanzhangSolutionList.add(solution);
                        } else {
                            int length = noWangLiansanzhangSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((LiansanzhangDianShuZu) noWangLiansanzhangSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LiansanzhangDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangLiansanzhangSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangLiansanzhangSolutionList.add(solution);
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
                        if (dianshuCountArray[dianshu.ordinal()] < 2) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangLianduiSolutionList.isEmpty()) {
                            noWangLianduiSolutionList.add(solution);
                        } else {
                            int length = noWangLianduiSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((LianduiDianShuZu) noWangLianduiSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LianduiDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangLianduiSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangLianduiSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
                // 顺子
                if (dianshuZu instanceof ShunziDianShuZu) {
                    ShunziDianShuZu shunziDianShuZu = (ShunziDianShuZu) dianshuZu;
                    DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
                    boolean allSuccess = true;
                    for (DianShu dianshu : lianXuDianShuArray) {
                        if (dianshuCountArray[dianshu.ordinal()] < 1) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangShunziSolutionList.isEmpty()) {
                            noWangShunziSolutionList.add(solution);
                        } else {
                            int length = noWangShunziSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((ShunziDianShuZu) noWangShunziSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((ShunziDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangShunziSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangShunziSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
                //三带二
                if (dianshuZu instanceof SandaierDianShuZu) {
                    SandaierDianShuZu shunziDianShuZu = (SandaierDianShuZu) dianshuZu;
                    DianShu[] lianXuDianShuArray = shunziDianShuZu.getSanzhangDianShuArray();
                    DianShu[] daipaiDianShuArray = shunziDianShuZu.getDaipaiDianShuArray();
                    boolean allSuccess = true;
                    for (DianShu dianshu : lianXuDianShuArray) {
                        if (dianshuCountArray[dianshu.ordinal()] < 3) {
                            allSuccess = false;
                            break;
                        }
                    }
                    for (DianShu dianShu : daipaiDianShuArray) {
                        if (dianshuCountArray[dianShu.ordinal()] < 2) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangSandaierSolutionList.isEmpty()) {
                            noWangSandaierSolutionList.add(solution);
                        } else {
                            int length = noWangSandaierSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((SandaierDianShuZu) noWangSandaierSolutionList.get(i).getDianShuZu()).getSanzhangDianShuArray()[0].compareTo(((SandaierDianShuZu) dianshuZu).getSanzhangDianShuArray()[0]) > 0) {
                                    noWangSandaierSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangSandaierSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
            }
        } else {
            for (DaPaiDianShuSolution solution : noWangSolutionList) {
                DianShuZu dianshuZu = solution.getDianShuZu();
                // 三张
                if (dianshuZu instanceof SanzhangDianShuZu) {
                    SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianshuZu;
                    DianShu dianshu = sanzhangDianShuZu.getDianShu();
                    if (dianshuCountArray[dianshu.ordinal()] < 4) {
                        if (noWangSanzhangSolutionList.isEmpty()) {
                            noWangSanzhangSolutionList.add(solution);
                        } else {
                            int length = noWangSanzhangSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((SanzhangDianShuZu) noWangSanzhangSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(sanzhangDianShuZu.getDianShu()) > 0) {
                                    noWangSanzhangSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangSanzhangSolutionList.add(solution);
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
                            if (noWangDuiziYiShangSolutionList.isEmpty()) {
                                noWangDuiziYiShangSolutionList.add(solution);
                            } else {
                                int length1 = noWangDuiziYiShangSolutionList.size();
                                int j = 0;
                                while (j < length1) {
                                    if (((DuiziDianShuZu) noWangDuiziYiShangSolutionList.get(j).getDianShuZu()).getDianShu().compareTo(duiziDianShuZu.getDianShu()) > 0) {
                                        noWangDuiziYiShangSolutionList.add(j, solution);
                                        break;
                                    }
                                    if (j == length1 - 1) {
                                        noWangDuiziYiShangSolutionList.add(solution);
                                    }
                                    j++;
                                }
                            }
                        } else {
                            if (noWangDuiziSolutionList.isEmpty()) {
                                noWangDuiziSolutionList.add(solution);
                            } else {
                                int length = noWangDuiziSolutionList.size();
                                int i = 0;
                                while (i < length) {
                                    if (((DuiziDianShuZu) noWangDuiziSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(duiziDianShuZu.getDianShu()) > 0) {
                                        noWangDuiziSolutionList.add(i, solution);
                                        break;
                                    }
                                    if (i == length - 1) {
                                        noWangDuiziSolutionList.add(solution);
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
                            if (noWangDanzhangDuiziYiShangSolutionList.isEmpty()) {
                                noWangDanzhangDuiziYiShangSolutionList.add(solution);
                            } else {
                                int length1 = noWangDanzhangDuiziYiShangSolutionList.size();
                                int j = 0;
                                while (j < length1) {
                                    if (((DanzhangDianShuZu) noWangDanzhangDuiziYiShangSolutionList.get(j).getDianShuZu()).getDianShu().compareTo(danzhangDianShuZu.getDianShu()) > 0) {
                                        noWangDanzhangDuiziYiShangSolutionList.add(j, solution);
                                        break;
                                    }
                                    if (j == length1 - 1) {
                                        noWangDanzhangDuiziYiShangSolutionList.add(solution);
                                    }
                                    j++;
                                }
                            }
                        } else if (dianshuCountArray[dianshu.ordinal()] > 1) {
                            if (noWangDanzhangYiShangSolutionList.isEmpty()) {
                                noWangDanzhangYiShangSolutionList.add(solution);
                            } else {
                                int length1 = noWangDanzhangYiShangSolutionList.size();
                                int j = 0;
                                while (j < length1) {
                                    if (((DanzhangDianShuZu) noWangDanzhangYiShangSolutionList.get(j).getDianShuZu()).getDianShu().compareTo(danzhangDianShuZu.getDianShu()) > 0) {
                                        noWangDanzhangYiShangSolutionList.add(j, solution);
                                        break;
                                    }
                                    if (j == length1 - 1) {
                                        noWangDanzhangYiShangSolutionList.add(solution);
                                    }
                                    j++;
                                }
                            }
                        } else {
                            if (noWangDanzhangSolutionList.isEmpty()) {
                                noWangDanzhangSolutionList.add(solution);
                            } else {
                                int length = noWangDanzhangSolutionList.size();
                                int i = 0;
                                while (i < length) {
                                    if (((DanzhangDianShuZu) noWangDanzhangSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(danzhangDianShuZu.getDianShu()) > 0) {
                                        noWangDanzhangSolutionList.add(i, solution);
                                        break;
                                    }
                                    if (i == length - 1) {
                                        noWangDanzhangSolutionList.add(solution);
                                    }
                                    i++;
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
                        if (dianshuCountArray[dianshu.ordinal()] < 3) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangLiansanzhangSolutionList.isEmpty()) {
                            noWangLiansanzhangSolutionList.add(solution);
                        } else {
                            int length = noWangLiansanzhangSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((LiansanzhangDianShuZu) noWangLiansanzhangSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LiansanzhangDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangLiansanzhangSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangLiansanzhangSolutionList.add(solution);
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
                        if (dianshuCountArray[dianshu.ordinal()] < 2) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangLianduiSolutionList.isEmpty()) {
                            noWangLianduiSolutionList.add(solution);
                        } else {
                            int length = noWangLianduiSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((LianduiDianShuZu) noWangLianduiSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LianduiDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangLianduiSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangLianduiSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
                // 顺子
                if (dianshuZu instanceof ShunziDianShuZu) {
                    ShunziDianShuZu shunziDianShuZu = (ShunziDianShuZu) dianshuZu;
                    DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
                    boolean allSuccess = true;
                    for (DianShu dianshu : lianXuDianShuArray) {
                        if (dianshuCountArray[dianshu.ordinal()] < 1) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangShunziSolutionList.isEmpty()) {
                            noWangShunziSolutionList.add(solution);
                        } else {
                            int length = noWangShunziSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((ShunziDianShuZu) noWangShunziSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((ShunziDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                    noWangShunziSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangShunziSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }
                //三带二
                if (dianshuZu instanceof SandaierDianShuZu) {
                    SandaierDianShuZu sandaierDianShuZu = (SandaierDianShuZu) dianshuZu;
                    DianShu[] dianShuArray = sandaierDianShuZu.getSanzhangDianShuArray();
                    DianShu[] daipaiDianShuArray = sandaierDianShuZu.getDaipaiDianShuArray();
                    boolean allSuccess = true;
                    for (DianShu dianshu : dianShuArray) {
                        if (dianshuCountArray[dianshu.ordinal()] < 3) {
                            allSuccess = false;
                            break;
                        }
                    }
                    for (DianShu dianShu : daipaiDianShuArray) {
                        if (dianshuCountArray[dianShu.ordinal()] < 2) {
                            allSuccess = false;
                            break;
                        }
                    }
                    if (allSuccess) {
                        if (noWangSandaierSolutionList.isEmpty()) {
                            noWangSandaierSolutionList.add(solution);
                        } else {
                            int length = noWangSandaierSolutionList.size();
                            int i = 0;
                            while (i < length) {
                                if (((SandaierDianShuZu) noWangSandaierSolutionList.get(i).getDianShuZu()).getSanzhangDianShuArray()[0].compareTo(((SandaierDianShuZu) dianshuZu).getSanzhangDianShuArray()[0]) > 0) {
                                    noWangSandaierSolutionList.add(i, solution);
                                    break;
                                }
                                if (i == length - 1) {
                                    noWangSandaierSolutionList.add(solution);
                                }
                                i++;
                            }
                        }
                    }
                }

            }
        }
        f2:
        for (DaPaiDianShuSolution solution : hasWangSolutionList) {
            DianShuZu dianshuZu = solution.getDianShuZu();
            // 三张
            if (dianshuZu instanceof SanzhangDianShuZu) {
                SanzhangDianShuZu sanzhangDianShuZu = (SanzhangDianShuZu) dianshuZu;
                DianShu dianshu = sanzhangDianShuZu.getDianShu();
                if (dianshuCountArray[dianshu.ordinal()] >= 3) {
                    continue f2;
                }
                if (hasWangSanzhangSolutionList.isEmpty()) {
                    hasWangSanzhangSolutionList.add(solution);
                } else {
                    int length = hasWangSanzhangSolutionList.size();
                    int i = 0;
                    while (i < length) {
                        if (((SanzhangDianShuZu) hasWangSanzhangSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(sanzhangDianShuZu.getDianShu()) > 0) {
                            hasWangSanzhangSolutionList.add(i, solution);
                            break;
                        }
                        if (i == length - 1) {
                            hasWangSanzhangSolutionList.add(solution);
                        }
                        i++;
                    }
                }
            }
            // 对子
            if (dianshuZu instanceof DuiziDianShuZu) {
                DuiziDianShuZu duiziDianShuZu = (DuiziDianShuZu) dianshuZu;
                DianShu dianshu = duiziDianShuZu.getDianShu();
                if (dianshu.ordinal() < 13 && dianshuCountArray[dianshu.ordinal()] >= 2) {
                    continue f2;
                }
                if (hasWangDuiziSolutionList.isEmpty()) {
                    hasWangDuiziSolutionList.add(solution);
                } else {
                    int length = hasWangDuiziSolutionList.size();
                    int i = 0;
                    while (i < length) {
                        if (((DuiziDianShuZu) hasWangDuiziSolutionList.get(i).getDianShuZu()).getDianShu().compareTo(duiziDianShuZu.getDianShu()) > 0) {
                            hasWangDuiziSolutionList.add(i, solution);
                            break;
                        }
                        if (i == length - 1) {
                            hasWangDuiziSolutionList.add(solution);
                        }
                        i++;
                    }
                }
            }
            // 单张
            if (dianshuZu instanceof DanzhangDianShuZu) {
                DanzhangDianShuZu danzhangDianShuZu = (DanzhangDianShuZu) dianshuZu;
                DianShu dianshu = danzhangDianShuZu.getDianShu();
                if (dianshu.equals(DianShu.hongxiner2)) {
                    hasWangDanzhangSolutionList.add(solution);
                }
            }
            // 连三张
            if (dianshuZu instanceof LiansanzhangDianShuZu) {
//                LiansanzhangDianShuZu liansanzhangDianShuZu = (LiansanzhangDianShuZu) dianshuZu;
//                DianShu[] lianXuDianShuArray = liansanzhangDianShuZu.getLianXuDianShuArray();
                boolean allSuccess = true;
//                for (DianShu dianshu : lianXuDianShuArray) {
//                    if (dianshuCountArray[dianshu.ordinal()] >= 3) {
//                        allSuccess = false;
//                        break;
//                    }
//                }
                if (allSuccess) {
                    if (hasWangLiansanzhangSolutionList.isEmpty()) {
                        hasWangLiansanzhangSolutionList.add(solution);
                    } else {
                        int length = hasWangLiansanzhangSolutionList.size();
                        int i = 0;
                        while (i < length) {
                            if (((LiansanzhangDianShuZu) hasWangLiansanzhangSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LiansanzhangDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                hasWangLiansanzhangSolutionList.add(i, solution);
                                break;
                            }
                            if (i == length - 1) {
                                hasWangLiansanzhangSolutionList.add(solution);
                            }
                            i++;
                        }
                    }
                }
            }
            // 连对
            if (dianshuZu instanceof LianduiDianShuZu) {
//                LianduiDianShuZu lianduiDianShuZu = (LianduiDianShuZu) dianshuZu;
//                DianShu[] lianXuDianShuArray = lianduiDianShuZu.getLianXuDianShuArray();
                boolean allSuccess = true;
//                for (DianShu dianshu : lianXuDianShuArray) {
//                    if (dianshuCountArray[dianshu.ordinal()] < 2) {
//                        allSuccess = false;
//                        break;
//                    }
//                }
                if (allSuccess) {
                    if (hasWangLianduiSolutionList.isEmpty()) {
                        hasWangLianduiSolutionList.add(solution);
                    } else {
                        int length = hasWangLianduiSolutionList.size();
                        int i = 0;
                        while (i < length) {
                            if (((LianduiDianShuZu) hasWangLianduiSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((LianduiDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                hasWangLianduiSolutionList.add(i, solution);
                                break;
                            }
                            if (i == length - 1) {
                                hasWangLianduiSolutionList.add(solution);
                            }
                            i++;
                        }
                    }
                }
            }
            // 顺子
            if (dianshuZu instanceof ShunziDianShuZu) {
//                ShunziDianShuZu shunziDianShuZu = (ShunziDianShuZu) dianshuZu;
//                DianShu[] lianXuDianShuArray = shunziDianShuZu.getLianXuDianShuArray();
                boolean allSuccess = true;
//                for (DianShu dianshu : lianXuDianShuArray) {
//                    if (dianshuCountArray[dianshu.ordinal()] >= 1) {
//                        allSuccess = false;
//                        break;
//                    }
//                }
                if (allSuccess) {
                    if (hasWangShunziSolutionList.isEmpty()) {
                        hasWangShunziSolutionList.add(solution);
                    } else {
                        int length = hasWangShunziSolutionList.size();
                        int i = 0;
                        while (i < length) {
                            if (((ShunziDianShuZu) hasWangShunziSolutionList.get(i).getDianShuZu()).getLianXuDianShuArray()[0].compareTo(((ShunziDianShuZu) dianshuZu).getLianXuDianShuArray()[0]) > 0) {
                                hasWangShunziSolutionList.add(i, solution);
                                break;
                            }
                            if (i == length - 1) {
                                hasWangShunziSolutionList.add(solution);
                            }
                            i++;
                        }
                    }
                }
            }
            //三带二
            if (dianshuZu instanceof SandaierDianShuZu) {
                SandaierDianShuZu shunziDianShuZu = (SandaierDianShuZu) dianshuZu;
                DianShu[] daipaiDianShuArray = shunziDianShuZu.getDaipaiDianShuArray();
                boolean allSuccess = true;
                for (DianShu dianshu : daipaiDianShuArray) {
                    if (dianshuCountArray[dianshu.ordinal()] < 2) {
                        allSuccess = false;
                        break;
                    }
                }
                if (allSuccess) {
                    if (hasWangSandaierSolutionList.isEmpty()) {
                        hasWangSandaierSolutionList.add(solution);
                    } else {
                        int length = hasWangSandaierSolutionList.size();
                        int i = 0;
                        while (i < length) {
                            if (((SandaierDianShuZu) hasWangSandaierSolutionList.get(i).getDianShuZu()).getSanzhangDianShuArray()[0].compareTo(((SandaierDianShuZu) dianshuZu).getSanzhangDianShuArray()[0]) > 0) {
                                hasWangSandaierSolutionList.add(i, solution);
                                break;
                            }
                            if (i == length - 1) {
                                hasWangSandaierSolutionList.add(solution);
                            }
                            i++;
                        }
                    }
                }
            }
        }
        DaPaiDianShuSolution maxZhadanSolution = null;
        for (DaPaiDianShuSolution solution : YaPaiSolutions) {
            DianShuZu dianshuZu = solution.getDianShuZu();
            if (dianshuZu instanceof ZhadanDianShuZu) {
                ZhadanDianShuZu zhadanDianShuZu1 = (ZhadanDianShuZu) dianshuZu;
                if (maxZhadanSolution != null) {
                    int comparator = zhadanComparator.compare(zhadanDianShuZu1, (ZhadanDianShuZu) maxZhadanSolution.getDianShuZu());
                    if (comparator > 0) {
                        maxZhadanSolution = solution;
                    } else if (comparator == 0 && maxZhadanSolution.getDianShuZu() instanceof LianXuZhadanDianShuZu && zhadanDianShuZu1 instanceof LianXuZhadanDianShuZu) {
                        LianXuZhadanDianShuZu lianXuZhadanDianShuZu1 = (LianXuZhadanDianShuZu) maxZhadanSolution.getDianShuZu();
                        LianXuZhadanDianShuZu lianXuZhadanDianShuZu2 = (LianXuZhadanDianShuZu) zhadanDianShuZu1;
                        if (lianXuZhadanDianShuZu2.getZhangshu() > lianXuZhadanDianShuZu1.getZhangshu()) {
                            maxZhadanSolution = solution;
                        }
                    }
                } else if (maxZhadanSolution == null) {
                    maxZhadanSolution = solution;
                }
            }
        }
        if (maxZhadanSolution != null) {
            if (!zhadanSolutionList.isEmpty()) {
                if (zhadanSolutionList.size() == 1) {// 只有一个炸弹，把王也带上
                    if (maxZhadanSolution.getDachuDianShuArray().length == allShoupai.size()) {
                        zhadanSolutionList.clear();
                    }
                    zhadanSolutionList.add(maxZhadanSolution);
                } else {
                    DaPaiDianShuSolution solution = zhadanSolutionList.getLast();
                    if (!solution.getDianshuZuheIdx().equals(maxZhadanSolution.getDianshuZuheIdx())) {
                        zhadanSolutionList.add(maxZhadanSolution);
                    }
                }
            } else {
                zhadanSolutionList.add(maxZhadanSolution);
            }
        }
        noWangDanzhangSolutionList.addAll(noWangDanzhangYiShangSolutionList);
        noWangDanzhangSolutionList.addAll(noWangDanzhangDuiziYiShangSolutionList);
        noWangDuiziSolutionList.addAll(noWangDuiziYiShangSolutionList);

        filtedSolutionList.addAll(noWangDanzhangSolutionList);
        filtedSolutionList.addAll(noWangDuiziSolutionList);
        filtedSolutionList.addAll(noWangSanzhangSolutionList);
        filtedSolutionList.addAll(noWangShunziSolutionList);
        filtedSolutionList.addAll(noWangSandaierSolutionList);
        filtedSolutionList.addAll(noWangLianduiSolutionList);
        filtedSolutionList.addAll(noWangLiansanzhangSolutionList);

        if (bx.equals(BianXingWanFa.baibian)) {
            filtedSolutionList.addAll(hasWangDanzhangSolutionList);
        }
        if (noWangDuiziSolutionList.isEmpty()) {
            filtedSolutionList.addAll(hasWangDuiziSolutionList);
            filtedSolutionList.addAll(hasWangSanzhangSolutionList);
            filtedSolutionList.addAll(hasWangShunziSolutionList);
            filtedSolutionList.addAll(hasWangSandaierSolutionList);
            filtedSolutionList.addAll(hasWangLianduiSolutionList);
            filtedSolutionList.addAll(hasWangLiansanzhangSolutionList);
        }
        filtedSolutionList.addAll(zhadanSolutionList);
        return filtedSolutionList;
    }

    public ZhadanComparator getZhadanComparator() {
        return zhadanComparator;
    }

    public void setZhadanComparator(ZhadanComparator zhadanComparator) {
        this.zhadanComparator = zhadanComparator;
    }

    public BianXingWanFa getBx() {
        return bx;
    }

    public void setBx(BianXingWanFa bx) {
        this.bx = bx;
    }

}
