package com.anbang.qipai.biji.web.vo;

import java.util.List;

import com.dml.shisanshui.pai.PukePai;
import com.dml.shisanshui.pai.paixing.PaixingSolution;
import com.dml.shisanshui.player.ShisanshuiPlayerValueObject;
import com.dml.shisanshui.position.Position;

public class BijiPlayerValueObjectVO {
    private String id;
    private Position position;
    private BijiPlayerShoupaiVO allShoupai;
    private List<List<Integer>> shoupaiIdListForSortList;
    /**
     * 玩家对子出牌方案
     */
    private List<List<PukePai>> duiziCandidates;
    /**
     * 玩家三条出牌方案
     */
    private List<List<PukePai>> santiaoCandidates;
    /**
     * 玩家顺子出牌方案
     */
    private List<List<PukePai>> shunziCandidates;
    /**
     * 玩家同花出牌方案
     */
    private List<List<PukePai>> tonghuaCandidates;
    /**
     * 玩家同花顺出牌方案
     */
    private List<List<PukePai>> tonghuashunCandidates;
    /**
     * 玩家出牌提示
     */
    private List<PaixingSolution> chupaiSolutionForTips;

    /**
     * 是否出牌
     */
    private boolean chupai;
    /**
     * 弃牌
     */
    private boolean qipai;

    private PaixingSolution chupaiSolution;

    public BijiPlayerValueObjectVO() {

    }

    public BijiPlayerValueObjectVO(ShisanshuiPlayerValueObject shisanshuiPlayerValueObject) {
        id = shisanshuiPlayerValueObject.getId();
        position = shisanshuiPlayerValueObject.getPosition();
        shoupaiIdListForSortList = shisanshuiPlayerValueObject.getShoupaiIdListForSortList();
        if (shoupaiIdListForSortList == null || shoupaiIdListForSortList.isEmpty()) {
            allShoupai = new BijiPlayerShoupaiVO(shisanshuiPlayerValueObject.getAllShoupai(), shisanshuiPlayerValueObject.getTotalShoupai(), null);
        } else {
            allShoupai = new BijiPlayerShoupaiVO(shisanshuiPlayerValueObject.getAllShoupai(), shisanshuiPlayerValueObject.getTotalShoupai(), shoupaiIdListForSortList.get(0));
        }
        duiziCandidates = shisanshuiPlayerValueObject.getDuiziCandidates();
        shunziCandidates = shisanshuiPlayerValueObject.getShunziCandidates();
        tonghuaCandidates = shisanshuiPlayerValueObject.getTonghuaCandidates();
        tonghuashunCandidates = shisanshuiPlayerValueObject.getTonghuashunCandidates();
        santiaoCandidates = shisanshuiPlayerValueObject.getSantiaoCandidates();
        chupaiSolutionForTips = shisanshuiPlayerValueObject.getChupaiSolutionForTips();
        chupai = shisanshuiPlayerValueObject.getChupaiSolution() != null;
        chupaiSolution = shisanshuiPlayerValueObject.getChupaiSolution();
        qipai=shisanshuiPlayerValueObject.isQipai();
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

    public BijiPlayerShoupaiVO getAllShoupai() {
        return allShoupai;
    }

    public void setAllShoupai(BijiPlayerShoupaiVO allShoupai) {
        this.allShoupai = allShoupai;
    }

    public List<List<Integer>> getShoupaiIdListForSortList() {
        return shoupaiIdListForSortList;
    }

    public void setShoupaiIdListForSortList(List<List<Integer>> shoupaiIdListForSortList) {
        this.shoupaiIdListForSortList = shoupaiIdListForSortList;
    }

    public List<List<PukePai>> getDuiziCandidates() {
        return duiziCandidates;
    }

    public void setDuiziCandidates(List<List<PukePai>> duiziCandidates) {
        this.duiziCandidates = duiziCandidates;
    }

    public List<List<PukePai>> getSantiaoCandidates() {
        return santiaoCandidates;
    }

    public void setSantiaoCandidates(List<List<PukePai>> santiaoCandidates) {
        this.santiaoCandidates = santiaoCandidates;
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

    public List<PaixingSolution> getChupaiSolutionForTips() {
        return chupaiSolutionForTips;
    }

    public void setChupaiSolutionForTips(List<PaixingSolution> chupaiSolutionForTips) {
        this.chupaiSolutionForTips = chupaiSolutionForTips;
    }

    public boolean isChupai() {
        return chupai;
    }

    public void setChupai(boolean chupai) {
        this.chupai = chupai;
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
