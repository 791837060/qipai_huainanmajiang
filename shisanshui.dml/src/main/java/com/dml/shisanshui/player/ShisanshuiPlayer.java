package com.dml.shisanshui.player;

import java.util.*;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.preparedapai.lipai.ShoupaiSortStrategy;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.player.action.ChupaiException;
import com.dml.shisanshui.player.action.ChupaiPaixingSolutionFilter;
import com.dml.shisanshui.player.action.ToudaoDayuZhongdaoException;
import com.dml.shisanshui.player.action.ZhongdaoDayuWeidaoException;
import com.dml.shisanshui.position.Position;

public class ShisanshuiPlayer {
    /**
     * 玩家id
     */
    private String id;
    /**
     * 玩家座位
     */
    private Position position;
    /**
     * 玩家手牌
     */
    private Map<Integer, PukePai> allShoupai = new HashMap<>();
    /**
     * 可以有多种理牌方案
     */
    private List<List<Integer>> shoupaiIdListForSortList = new ArrayList<>();
    /**
     * 玩家出牌方案
     */
    private Map<String, Dao> chupaiSolutionCandidates = new HashMap<>();
    /**
     * 玩家出牌提示
     */
    private List<PaixingSolution> chupaiSolutionForTips = new ArrayList<>();

    /**
     * 最终出牌
     */
    private PaixingSolution chupaiSolution;
    /**
     * 是否弃牌
     */
    private boolean qipai;
    /**
     * 托管状态是否已出牌
     */
    private boolean tuoguanChupai = false;
    /**
     * 玩家总分
     */
    private double playerTotalScore = 0;

    /**
     * 添加手牌
     *
     * @param pukePai 扑克牌
     */
    public void addShoupai(PukePai pukePai) {
        allShoupai.put(pukePai.getId(), pukePai);
    }

    public boolean chuwanpai() {
        return chupaiSolution != null;
    }

    public Dao findDaoByIndex(String index) {
        return chupaiSolutionCandidates.get(index);
    }

    /**
     * 出牌
     *
     * @param toudaoIndex   头道索引
     * @param zhongdaoIndex 中道索引
     * @param weidaoIndex   尾道索引
     * @param daoComparator 道比较器
     */
    public PaixingSolution chupai(String toudaoIndex, String zhongdaoIndex, String weidaoIndex, DaoComparator daoComparator) throws Exception {
        List<Dao> daoList = new ArrayList();
        daoList.add(chupaiSolutionCandidates.get(toudaoIndex));
        daoList.add(chupaiSolutionCandidates.get(zhongdaoIndex));
        daoList.add(chupaiSolutionCandidates.get(weidaoIndex));
        daoList.sort(daoComparator::compare);
        Dao toudao = daoList.get(0);
        Dao zhongdao = daoList.get(1);
        Dao weidao = daoList.get(2);

        PaixingSolution solution = new PaixingSolution();
        solution.setToudao(toudao);
        solution.setZhongdao(zhongdao);
        solution.setWeidao(weidao);
        if (toudao == null || zhongdao == null || weidao == null) {
            throw new ChupaiException();
        }
        if (daoComparator.compare(toudao, zhongdao) > 0) {
            throw new ToudaoDayuZhongdaoException();
        }
        if (daoComparator.compare(zhongdao, weidao) > 0) {
            throw new ZhongdaoDayuWeidaoException();
        }
        Map<Integer, PukePai> allPai = new HashMap<>();
        for (PukePai pukePai : toudao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        for (PukePai pukePai : zhongdao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        for (PukePai pukePai : weidao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        if (allPai.size() != allShoupai.size()) {
            throw new ChupaiException();
        }
        chupaiSolution = solution;
        chupaiSolutionCandidates.clear();
        chupaiSolutionForTips.clear();
        return chupaiSolution;
    }

    /**
     * 自动出牌
     *
     * @param daoComparator 道比较器
     */
    public PaixingSolution autoChupai(DaoComparator daoComparator) throws Exception {
        PaixingSolution paixingSolution = chupaiSolutionForTips.get(0);
        Dao toudao = paixingSolution.getToudao();
        Dao zhongdao = paixingSolution.getZhongdao();
        Dao weidao = paixingSolution.getWeidao();
        PaixingSolution solution = new PaixingSolution();
        solution.setToudao(toudao);
        solution.setZhongdao(zhongdao);
        solution.setWeidao(weidao);
        if (toudao == null || zhongdao == null || weidao == null) {
            throw new ChupaiException();
        }
        if (daoComparator.compare(toudao, zhongdao) > 0) {
            throw new ToudaoDayuZhongdaoException();
        }
        if (daoComparator.compare(zhongdao, weidao) > 0) {
            throw new ZhongdaoDayuWeidaoException();
        }
        Map<Integer, PukePai> allPai = new HashMap<>();
        for (PukePai pukePai : toudao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        for (PukePai pukePai : zhongdao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        for (PukePai pukePai : weidao.getPukePaiList()) {
            if (!allShoupai.containsKey(pukePai.getId())) {
                throw new ChupaiException();
            }
            allPai.put(pukePai.getId(), pukePai);
        }
        if (allPai.size() != allShoupai.size()) {
            throw new ChupaiException();
        }
        chupaiSolution = solution;
        chupaiSolutionCandidates.clear();
        chupaiSolutionForTips.clear();
        return chupaiSolution;
    }

    /**
     * 可出牌
     *
     * @param daoList 可出牌集合
     */
    public void putChupaiSolutionCandidates(List<Dao> daoList) {
        daoList.forEach((dao) -> chupaiSolutionCandidates.put(dao.getIndex(), dao));
    }

    /**
     * 出牌提示
     */
    public void generateChupaiSolutionForTips(ChupaiPaixingSolutionFilter chupaiPaixingSolutionFilter) {
        chupaiSolutionForTips = chupaiPaixingSolutionFilter.filter(allShoupai, new ArrayList<>(chupaiSolutionCandidates.values()));
    }

    /**
     * 理牌
     *
     * @param shoupaiSortStrategy 理牌策略
     */
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

    public Map<String, Dao> getChupaiSolutionCandidates() {
        return chupaiSolutionCandidates;
    }

    public void setChupaiSolutionCandidates(Map<String, Dao> chupaiSolutionCandidates) {
        this.chupaiSolutionCandidates = chupaiSolutionCandidates;
    }

    public List<PaixingSolution> getChupaiSolutionForTips() {
        return chupaiSolutionForTips;
    }

    public void setChupaiSolutionForTips(List<PaixingSolution> chupaiSolutionForTips) {
        this.chupaiSolutionForTips = chupaiSolutionForTips;
    }

    public PaixingSolution getChupaiSolution() {
        return chupaiSolution;
    }

    public void setChupaiSolution(PaixingSolution chupaiSolution) {
        this.chupaiSolution = chupaiSolution;
    }

    public List<List<Integer>> getShoupaiIdListForSortList() {
        return shoupaiIdListForSortList;
    }

    public void setShoupaiIdListForSortList(List<List<Integer>> shoupaiIdListForSortList) {
        this.shoupaiIdListForSortList = shoupaiIdListForSortList;
    }

    public boolean isQipai() {
        return qipai;
    }

    public void setQipai(boolean qipai) {
        this.qipai = qipai;
    }

    public boolean isTuoguanChupai() {
        return tuoguanChupai;
    }

    public void setTuoguanChupai(boolean tuoguanChupai) {
        this.tuoguanChupai = tuoguanChupai;
    }

    public double getPlayerTotalScore() {
        return playerTotalScore;
    }

    public void setPlayerTotalScore(double playerTotalScore) {
        this.playerTotalScore = playerTotalScore;
    }
}
