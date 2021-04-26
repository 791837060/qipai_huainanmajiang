package com.dml.shisanshui.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.position.Position;
import com.dml.shisanshui.pai.paixing.Dao;
import com.dml.shisanshui.pai.paixing.Paixing;
import com.dml.shisanshui.pai.paixing.PaixingSolution;

public class ShisanshuiPlayerValueObject {
    private String id;
    private Position position;
    /**
     * 玩家手牌
     */
    private Map<Integer, PukePai> allShoupai = new HashMap<>();
    private List<List<Integer>> shoupaiIdListForSortList;
    private int totalShoupai;
    /**
     * 玩家对子出牌方案
     */
    private List<List<PukePai>> duiziCandidates = new ArrayList<>();
    /**
     * 玩家顺子出牌方案
     */
    private List<List<PukePai>> shunziCandidates = new ArrayList<>();
    /**
     * 玩家同花出牌方案
     */
    private List<List<PukePai>> tonghuaCandidates = new ArrayList<>();
    /**
     * 玩家同花顺出牌方案
     */
    private List<List<PukePai>> tonghuashunCandidates = new ArrayList<>();
    /**
     * 玩家三条出牌方案
     */
    private List<List<PukePai>> santiaoCandidates = new ArrayList<>();
    /**
     * 玩家出牌提示
     */
    private List<PaixingSolution> chupaiSolutionForTips = new ArrayList<>();

    /**
     * 最终出牌
     */
    private PaixingSolution chupaiSolution;

    /**
     * 弃牌
     */
    private boolean qipai;

    public ShisanshuiPlayerValueObject() {

    }

    public ShisanshuiPlayerValueObject(ShisanshuiPlayer player) {
        id = player.getId();
        position = player.getPosition();
        allShoupai.putAll(player.getAllShoupai());
        shoupaiIdListForSortList = new ArrayList<>(player.getShoupaiIdListForSortList());
        totalShoupai = allShoupai.size();
        qipai = player.isQipai();
        int[] dianshuArray = new int[16];
        for (PukePai pukePai : allShoupai.values()) {
            dianshuArray[pukePai.getPaiMian().dianShu().ordinal()]++;
        }
        for (int i = 0; i < dianshuArray.length; i++) {
            if (dianshuArray[i] == 2) {
                List<PukePai> duizi = new ArrayList<>();
                for (PukePai pukePai : allShoupai.values()) {
                    if (pukePai.getPaiMian().dianShu().ordinal() == i) {
                        duizi.add(pukePai);
                    }
                }
                duiziCandidates.add(duizi);
            }
            if (dianshuArray[i] == 3) {
                List<PukePai> santiao = new ArrayList<>();
                for (PukePai pukePai : allShoupai.values()) {
                    if (pukePai.getPaiMian().dianShu().ordinal() == i) {
                        santiao.add(pukePai);
                    }
                }
                santiaoCandidates.add(santiao);
            }
        }
        for (Dao dao : player.getChupaiSolutionCandidates().values()) {
            Paixing paixing = dao.getPaixing();
            if (Paixing.shunzi.equals(paixing)) {
                if (shunziCandidates.size() > 10) {
                    continue;
                }
                shunziCandidates.add(dao.getPukePaiList());
            } else if (Paixing.tonghua.equals(paixing)) {
                if (tonghuaCandidates.size() > 10) {
                    continue;
                }
                tonghuaCandidates.add(dao.getPukePaiList());
            } else if (Paixing.tonghuashun.equals(paixing)) {
                if (tonghuashunCandidates.size() > 10) {
                    continue;
                }
                tonghuashunCandidates.add(dao.getPukePaiList());
            }
        }
        chupaiSolutionForTips.addAll(player.getChupaiSolutionForTips());
        chupaiSolution = player.getChupaiSolution();
        qipai = player.isQipai();
    }

    public List<List<Integer>> getShoupaiIdListForSortList() {
        return shoupaiIdListForSortList;
    }

    public void setShoupaiIdListForSortList(List<List<Integer>> shoupaiIdListForSortList) {
        this.shoupaiIdListForSortList = shoupaiIdListForSortList;
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

    public int getTotalShoupai() {
        return totalShoupai;
    }

    public void setTotalShoupai(int totalShoupai) {
        this.totalShoupai = totalShoupai;
    }

    public List<List<PukePai>> getDuiziCandidates() {
        return duiziCandidates;
    }

    public void setDuiziCandidates(List<List<PukePai>> duiziCandidates) {
        this.duiziCandidates = duiziCandidates;
    }

    public List<List<PukePai>> getShunziCandidates() {
        return shunziCandidates;
    }

    public void setShunziCandidates(List<List<PukePai>> shunziCandidates) {
        this.shunziCandidates = shunziCandidates;
    }

    public List<List<PukePai>> getTonghuaCandidates() {
        return tonghuaCandidates;
    }

    public void setTonghuaCandidates(List<List<PukePai>> tonghuaCandidates) {
        this.tonghuaCandidates = tonghuaCandidates;
    }

    public List<List<PukePai>> getTonghuashunCandidates() {
        return tonghuashunCandidates;
    }

    public void setTonghuashunCandidates(List<List<PukePai>> tonghuashunCandidates) {
        this.tonghuashunCandidates = tonghuashunCandidates;
    }

    public List<List<PukePai>> getSantiaoCandidates() {
        return santiaoCandidates;
    }

    public void setSantiaoCandidates(List<List<PukePai>> santiaoCandidates) {
        this.santiaoCandidates = santiaoCandidates;
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

    public boolean isQipai() {
        return qipai;
    }

    public void setQipai(boolean qipai) {
        this.qipai = qipai;
    }
}
