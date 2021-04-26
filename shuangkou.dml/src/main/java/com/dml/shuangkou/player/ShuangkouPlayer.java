package com.dml.shuangkou.player;

import java.util.*;

import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.dianshuzu.TonghuashunDianShuZu;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.shuangkou.pai.waihao.WaihaoGenerator;
import com.dml.shuangkou.player.action.da.DaPaiException;
import com.dml.shuangkou.player.action.da.KedaPaiSolutionsForTipsGenerator;
import com.dml.shuangkou.player.action.da.YaPaiSolutionsTipsFilter;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.shuangkou.preparedapai.lipai.ShoupaiSortStrategy;

public class ShuangkouPlayer {

    private String id;
    private Position position;
    private Map<Integer, PukePai> allShoupai = new HashMap<>();
    private int[] shoupaiDianShuAmountArray = new int[16];// 用于方便计算

    /**
     * 可以有多种理牌方案
     */
    private List<List<Integer>> shoupaiIdListForSortList = new ArrayList<>();

    /**
     * 历史打出牌组，不包含还在公示的打出牌组
     */
    private List<DianShuZuPaiZu> lishiDachuPaiZuList = new ArrayList<>();

    /**
     * 还在公示的打出牌组
     */
    private DianShuZuPaiZu publicDachuPaiZu;

    /**
     * 用于提示的打牌的方案。这些提示是提示可以打的牌，不一定能压上一手牌，也不一定提示所有方案。
     */
    private List<DaPaiDianShuSolution> daPaiSolutionsForTips = new ArrayList<>();

    /**
     * 可以压上一手牌的所有候选方案,key是基于“算术基本定理”的dianshuZuheIdx
     */
    private Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates = new HashMap<>();

    /**
     * 用于给用户提示的可以压上一手牌的方案，是yaPaiSolutionCandidates的子集。顺序有意义，就是提示顺序。
     */
    private List<DaPaiDianShuSolution> yaPaiSolutionsForTips = new ArrayList<>();

    /**
     * 同花顺出牌方案
     */
    Map<String, List<DaPaiDianShuSolution>> tonghuashunSolutionList = new HashMap<>();
    /**
     * 玩家总分
     */
    private double playerTotalScore = 0;

    private boolean guo;// 是否选择"过"

    public void addShouPai(PukePai pukePai) {
        allShoupai.put(pukePai.getId(), pukePai);
        int ordinal = pukePai.getPaiMian().dianShu().ordinal();
        shoupaiDianShuAmountArray[ordinal]++;
    }

    public void putYaPaiSolutionCandidates(Map<String, DaPaiDianShuSolution> solutionMap) {
        yaPaiSolutionCandidates.putAll(solutionMap);
    }

    public List<DaPaiDianShuSolution> takeYaPaiSolutionCandidates() {
        return new ArrayList<>(yaPaiSolutionCandidates.values());
    }

    public void da(List<Integer> paiIds, String dianshuZuheIdx, WaihaoGenerator waihaoGenerator) throws Exception {
        DaPaiDianShuSolution solution = null;
        for (List<DaPaiDianShuSolution> solutions : tonghuashunSolutionList.values()) {
            for (DaPaiDianShuSolution tonghuaseSolution : solutions) {
                if (tonghuaseSolution.getDianshuZuheIdx().equals(dianshuZuheIdx)) {
                    solution = tonghuaseSolution;
                }
            }
        }
        if (solution == null) {
            solution = yaPaiSolutionCandidates.get(dianshuZuheIdx);
        }

        if (solution == null) {
            throw new DaPaiException();
        }
        List<DianShu> dapaiDianshuList = new ArrayList<>();
        List<PukePai> paiList = new ArrayList<>();
        for (int i = 0; i < paiIds.size(); i++) {
            Integer paiId = paiIds.get(i);
            PukePai pai = allShoupai.get(paiId);
            if (pai == null) {
                throw new DaPaiException();
            }
            paiList.add(pai);
            dapaiDianshuList.add(pai.getPaiMian().dianShu());
        }
        DianShu[] solutionDianShuArray = solution.getDachuDianShuArray();
        for (int i = 0; i < solutionDianShuArray.length; i++) {
            DianShu solutionDianShu = solutionDianShuArray[i];
            if (!dapaiDianshuList.remove(solutionDianShu)) {
                throw new DaPaiException();
            }
        }
        if (!dapaiDianshuList.isEmpty()) {
            throw new DaPaiException();
        }

        // if (publicDachuPaiZu != null) {
        // lishiDachuPaiZuList.add(publicDachuPaiZu);
        // }
        DianShuZuPaiZu newPublicDachuPaiZu = new DianShuZuPaiZu();
        newPublicDachuPaiZu.setDianShuZu(solution.getDianShuZu());
        // 对打出的牌进行排序
        DianShu[] dachuDianShuArray = solution.getDachuDianShuArray();
        PukePai[] paiArray = new PukePai[paiList.size()];
        for (int i = 0; i < dachuDianShuArray.length; i++) {
            DianShu dianshu = dachuDianShuArray[i];
            for (int j = 0; j < paiList.size(); j++) {
                if (paiList.get(j).getPaiMian().dianShu().equals(dianshu)) {
                    paiArray[i] = paiList.remove(j);
                    break;
                }
            }
        }
        newPublicDachuPaiZu.setPaiArray(paiArray);
        waihaoGenerator.generateWaihao(newPublicDachuPaiZu);
        publicDachuPaiZu = newPublicDachuPaiZu;
        for (int i = 0; i < paiIds.size(); i++) {
            Integer paiId = paiIds.get(i);
            PukePai pukePai = allShoupai.get(paiId);
            int ordinal = pukePai.getPaiMian().dianShu().ordinal();
            shoupaiDianShuAmountArray[ordinal]--;
            allShoupai.remove(paiId);
        }
        guo = false;
        yaPaiSolutionCandidates.clear();
        yaPaiSolutionsForTips.clear();
//        tonghuashunSolutionList.clear();
        // daPaiSolutionsForTips.clear();
    }

    public void guo() {
        // putPublicDachuPaiZuToLishi();
        guo = true;
        yaPaiSolutionCandidates.clear();
        yaPaiSolutionsForTips.clear();
//        tonghuashunSolutionList.clear();
        // daPaiSolutionsForTips.clear();
    }

    public void putPublicDachuPaiZuToLishi() {
        if (publicDachuPaiZu != null) {
            lishiDachuPaiZuList.add(publicDachuPaiZu);
            publicDachuPaiZu = null;
        }
    }

    /**
     * 计算同花顺出牌方案
     */
    public Map<String, List<DaPaiDianShuSolution>> calculateTonghuashunSolution() {
        int hongxinEr = 0;
        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getPaiMian().dianShu().equals(DianShu.hongxiner2) && pukePai.getPaiMian().huaSe().equals(HuaSe.hongxin)) {
                hongxinEr++;
            }
        }
        List<PukePai> heitaoPaiList = new ArrayList<>();
        List<PukePai> hongxinPaiList = new ArrayList<>();
        List<PukePai> meihuaPaiList = new ArrayList<>();
        List<PukePai> fangkuaiPaiList = new ArrayList<>();
        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getPaiMian().huaSe() != null) {
                switch (pukePai.getPaiMian().huaSe()) {
                    case heitao:
                        heitaoPaiList.add(pukePai);
                        break;
                    case hongxin:
                        hongxinPaiList.add(pukePai);
                        break;
                    case meihua:
                        meihuaPaiList.add(pukePai);
                        break;
                    case fangkuai:
                        fangkuaiPaiList.add(pukePai);
                        break;
                }
            }
        }
        int[] heitaoDianshuAmountArray = new int[15];
        int[] hongxinDianshuAmountArray = new int[15];
        int[] meihuaDianshuAmountArray = new int[15];
        int[] fangkuaiDianshuAmountArray = new int[15];
        for (PukePai pukePai : heitaoPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                heitaoDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : hongxinPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                hongxinDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : meihuaPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                meihuaDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        for (PukePai pukePai : fangkuaiPaiList) {
            if (pukePai.getPaiMian().dianShu().ordinal() != 15) {
                fangkuaiDianshuAmountArray[pukePai.getPaiMian().dianShu().ordinal()]++;
            }
        }
        List<TonghuashunDianShuZu> heitaoTonghuashun = generateAllHuaseShunziDianShuZu(heitaoDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> hongxinTonghuashun = generateAllHuaseShunziDianShuZu(hongxinDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> meihuaTonghuashun = generateAllHuaseShunziDianShuZu(meihuaDianshuAmountArray, 5, hongxinEr);
        List<TonghuashunDianShuZu> fangkuaiTonghuashun = generateAllHuaseShunziDianShuZu(fangkuaiDianshuAmountArray, 5, hongxinEr);

        Map<String, List<DaPaiDianShuSolution>> solutionList = new HashMap<>();
        solutionList.put("heitao", calculateHuaseSolution(heitaoTonghuashun));
        solutionList.put("hongxin", calculateHuaseSolution(hongxinTonghuashun));
        solutionList.put("meihua", calculateHuaseSolution(meihuaTonghuashun));
        solutionList.put("fangkuai", calculateHuaseSolution(fangkuaiTonghuashun));
        return solutionList;
    }

    /**
     * 计算单个花色的同花顺
     *
     * @param dianShuAmountArray 点数数量数组
     * @param length             顺子长度
     * @param hongxinErs         红心二数量
     */
    public static List<TonghuashunDianShuZu> generateAllHuaseShunziDianShuZu(int[] dianShuAmountArray, int length, int hongxinErs) {
        List<TonghuashunDianShuZu> shunziList = new ArrayList<>();
        int[] aerDianshuAmountArray = getAerDianshuAmountArray(dianShuAmountArray);
        for (int i = 0; i < aerDianshuAmountArray.length; i++) {
            int danzhangLianXuCount = 0;
            int j = i;
            int guipaiCount = hongxinErs;
            List<Integer> dangpaiList = new ArrayList<>();
            while (danzhangLianXuCount < length && j <= 13) {   //无2和大、小王
                if (aerDianshuAmountArray[j] >= 1) {
                    danzhangLianXuCount++;
                    j++;
                } else if (guipaiCount > 0) {
                    if (j == 1) {   //重新计算A2点数数组的2
                        dangpaiList.add(12);//真正的2
                    } else {
                        dangpaiList.add(j - 2);
                    }
                    danzhangLianXuCount++;
                    j++;
                    guipaiCount--;
                } else {
                    break;
                }
            }
            if (danzhangLianXuCount >= length && i <= 9) {
                if (i != 1) {
                    DianShu[] lianXuDianShuArray = new DianShu[length];
                    for (int k = 0; k < length; k++) {
                        //重新定义的数组0处是A，而扑克点数0处是3 所以所有数组索引-2
                        int index = i;
                        if (index == 0) {
                            index = 11;
                        } else {
                            index -= 2;
                        }

                        int ordinal;
                        if (index + k >= 13) {
                            ordinal = index + k - 13;
                        } else {
                            ordinal = index + k;
                        }

                        if (index + k >= 13) {
                            index -= 13;
                        }
                        if (dangpaiList.contains(index + k)) {
                            ordinal = 15;
                        }

                        lianXuDianShuArray[k] = DianShu.getDianShuByOrdinal(ordinal);
                    }
                    TonghuashunDianShuZu tonghuashunDianShuZu = new TonghuashunDianShuZu(lianXuDianShuArray);
                    shunziList.add(tonghuashunDianShuZu);
                }
            }
        }

        List<TonghuashunDianShuZu> shunziList2 = new ArrayList<>();
        for (TonghuashunDianShuZu tonghuashunDianShuZu : shunziList) {
            int alreadyUsedHongxinEr = 0;
            DianShu[] lianXuDianShuArray = tonghuashunDianShuZu.getLianXuDianShuArray();
            for (DianShu dianShu : lianXuDianShuArray) {
                if (dianShu.equals(DianShu.hongxiner2)) {
                    alreadyUsedHongxinEr++;
                }
            }
            if (alreadyUsedHongxinEr == 0 && hongxinErs > 0) {
                for (int i = 0; i < lianXuDianShuArray.length; i++) {
                    DianShu[] cloneLianXuDianShuArray = lianXuDianShuArray.clone();
                    cloneLianXuDianShuArray[i] = DianShu.hongxiner2;
                    TonghuashunDianShuZu tonghuashunDianShuZu1 = new TonghuashunDianShuZu(cloneLianXuDianShuArray);
                    shunziList2.add(tonghuashunDianShuZu1);
                }
            }
        }
        shunziList.addAll(shunziList2);

        return shunziList;
    }

    /**
     * 重新计算从A开始的点数数量数组
     *
     * @param dianShuAmountArray 点数数量数组
     */
    private static int[] getAerDianshuAmountArray(int[] dianShuAmountArray) {
        int[] dianShuAmountArray2 = new int[15];
        for (int i = 0; i < dianShuAmountArray.length; i++) {
            if (i + 2 < dianShuAmountArray2.length) {
                dianShuAmountArray2[i + 2] = dianShuAmountArray[i];
            }
        }
        dianShuAmountArray2[0] = dianShuAmountArray[11];
        dianShuAmountArray2[1] = dianShuAmountArray[12];
        return dianShuAmountArray2;
    }

    /**
     * 生成单个花色同花顺出牌方案
     *
     * @param heitaoTonghuashun 同花顺数组
     */
    private static List<DaPaiDianShuSolution> calculateHuaseSolution(List<TonghuashunDianShuZu> heitaoTonghuashun) {
        List<DaPaiDianShuSolution> shuSolutions = new ArrayList<>();
        for (TonghuashunDianShuZu tonghuashunDianShuZu : heitaoTonghuashun) {
            DaPaiDianShuSolution solution = new DaPaiDianShuSolution();
            solution.setDianShuZu(tonghuashunDianShuZu);
            DianShu[] lianXuDianShuArray = tonghuashunDianShuZu.getLianXuDianShuArray();
            DianShu[] dachuDianShuArray = new DianShu[lianXuDianShuArray.length];
            for (int i = 0; i < lianXuDianShuArray.length; i++) {
                dachuDianShuArray[i] = lianXuDianShuArray[i];
            }
            solution.setDachuDianShuArray(dachuDianShuArray);
            solution.calculateDianshuZuheIdx();
            shuSolutions.add(solution);
        }
        return shuSolutions;
    }

    public void addDaPaiDianShuSolutions(Map<String, DaPaiDianShuSolution> solutionMap) {
        yaPaiSolutionCandidates.putAll(solutionMap);
    }

    public void generateYaPaiSolutionsForTips(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
        yaPaiSolutionsForTips = yaPaiSolutionsTipsFilter.filter(new ArrayList<>(yaPaiSolutionCandidates.values()), allShoupai, true);
    }

    public void generateNotYaPaiSolutionsForTips(YaPaiSolutionsTipsFilter yaPaiSolutionsTipsFilter) {
        yaPaiSolutionsForTips = yaPaiSolutionsTipsFilter.filter(new ArrayList<>(yaPaiSolutionCandidates.values()), allShoupai, false);
    }

    public void generateDaPaiSolutionsForTips(KedaPaiSolutionsForTipsGenerator kedaPaiSolutionsForTipsGenerator) {
        daPaiSolutionsForTips = kedaPaiSolutionsForTipsGenerator.generateKedaPaiSolutionsForTips(shoupaiDianShuAmountArray);
    }

    public void lipai(ShoupaiSortStrategy shoupaiSortStrategy) {
        shoupaiIdListForSortList = shoupaiSortStrategy.sortShoupai(allShoupai);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Map<Integer, PukePai> getAllShoupai() {
        return allShoupai;
    }

    public void setAllShoupai(Map<Integer, PukePai> allShoupai) {
        this.allShoupai = allShoupai;
    }

    public int[] getShoupaiDianShuAmountArray() {
        return shoupaiDianShuAmountArray;
    }

    public void setShoupaiDianShuAmountArray(int[] shoupaiDianShuAmountArray) {
        this.shoupaiDianShuAmountArray = shoupaiDianShuAmountArray;
    }

    public List<List<Integer>> getShoupaiIdListForSortList() {
        return shoupaiIdListForSortList;
    }

    public void setShoupaiIdListForSortList(List<List<Integer>> shoupaiIdListForSortList) {
        this.shoupaiIdListForSortList = shoupaiIdListForSortList;
    }

    public List<DianShuZuPaiZu> getLishiDachuPaiZuList() {
        return lishiDachuPaiZuList;
    }

    public void setLishiDachuPaiZuList(List<DianShuZuPaiZu> lishiDachuPaiZuList) {
        this.lishiDachuPaiZuList = lishiDachuPaiZuList;
    }

    public DianShuZuPaiZu getPublicDachuPaiZu() {
        return publicDachuPaiZu;
    }

    public void setPublicDachuPaiZu(DianShuZuPaiZu publicDachuPaiZu) {
        this.publicDachuPaiZu = publicDachuPaiZu;
    }

    public List<DaPaiDianShuSolution> getDaPaiSolutionsForTips() {
        return daPaiSolutionsForTips;
    }

    public void setDaPaiSolutionsForTips(List<DaPaiDianShuSolution> daPaiSolutionsForTips) {
        this.daPaiSolutionsForTips = daPaiSolutionsForTips;
    }

    public Map<String, DaPaiDianShuSolution> getYaPaiSolutionCandidates() {
        return yaPaiSolutionCandidates;
    }

    public void setYaPaiSolutionCandidates(Map<String, DaPaiDianShuSolution> yaPaiSolutionCandidates) {
        this.yaPaiSolutionCandidates = yaPaiSolutionCandidates;
    }

    public List<DaPaiDianShuSolution> getYaPaiSolutionsForTips() {
        return yaPaiSolutionsForTips;
    }

    public void setYaPaiSolutionsForTips(List<DaPaiDianShuSolution> yaPaiSolutionsForTips) {
        this.yaPaiSolutionsForTips = yaPaiSolutionsForTips;
    }

    public boolean isGuo() {
        return guo;
    }

    public void setGuo(boolean guo) {
        this.guo = guo;
    }

    public Map<String, List<DaPaiDianShuSolution>> getTonghuashunSolutionList() {
        return tonghuashunSolutionList;
    }

    public void setTonghuashunSolutionList(Map<String, List<DaPaiDianShuSolution>> tonghuashunSolutionList) {
        this.tonghuashunSolutionList = tonghuashunSolutionList;
    }

    public double getPlayerTotalScore() {
        return playerTotalScore;
    }

    public void setPlayerTotalScore(double playerTotalScore) {
        this.playerTotalScore = playerTotalScore;
    }
}
