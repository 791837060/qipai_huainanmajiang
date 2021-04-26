package com.dml.shuangkou.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.QiShouLiangPaiMark;
import com.dml.puke.pai.ZuDuiLiangPaiMark;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.shuangkou.player.action.da.solution.DaPaiDianShuSolution;

public class ShuangkouPlayerValueObject {
    private String id;
    private Position position;
    private Map<Integer, PukePai> allShoupai;
    private List<PukePai> liangPaiList;
    private int totalShoupai;
    private int[] shoupaiDianShuAmountArray;
    private List<List<Integer>> shoupaiIdListForSortList;
    private List<DianShuZuPaiZu> lishiDachuPaiZuList;
    private DianShuZuPaiZu publicDachuPaiZu;
    // private List<DaPaiDianShuSolution> daPaiSolutionsForTips;
    private List<DaPaiDianShuSolution> yaPaiSolutionCandidates;
    private List<DaPaiDianShuSolution> yaPaiSolutionsForTips;
    private boolean guo;

    /**
     * 同花顺出牌方案
     */
    Map<String,List<DaPaiDianShuSolution>> tonghuashunSolutionList;

    public ShuangkouPlayerValueObject() {

    }

    public ShuangkouPlayerValueObject(ShuangkouPlayer shuangkouPlayer) {
        id = shuangkouPlayer.getId();
        position = shuangkouPlayer.getPosition();
        liangPaiList = new ArrayList<>();
        allShoupai = new HashMap<>();
        allShoupai.putAll(shuangkouPlayer.getAllShoupai());
        for (PukePai pukePai : allShoupai.values()) {
            if (pukePai.getMark() != null && pukePai.getMark() instanceof ZuDuiLiangPaiMark
                    || pukePai.getMark() instanceof QiShouLiangPaiMark) {
                liangPaiList.add(pukePai);
            }
        }
        totalShoupai = allShoupai.size();
        shoupaiDianShuAmountArray = shuangkouPlayer.getShoupaiDianShuAmountArray().clone();
        shoupaiIdListForSortList = new ArrayList<>(shuangkouPlayer.getShoupaiIdListForSortList());
        lishiDachuPaiZuList = new ArrayList<>(shuangkouPlayer.getLishiDachuPaiZuList());
        publicDachuPaiZu = shuangkouPlayer.getPublicDachuPaiZu();
        yaPaiSolutionCandidates = new ArrayList<>(shuangkouPlayer.getYaPaiSolutionCandidates().values());
        yaPaiSolutionsForTips = new ArrayList<>(shuangkouPlayer.getYaPaiSolutionsForTips());
        guo = shuangkouPlayer.isGuo();
        tonghuashunSolutionList = new HashMap<>(shuangkouPlayer.getTonghuashunSolutionList());
    }

    public int getTotalShoupai() {
        return totalShoupai;
    }

    public void setTotalShoupai(int totalShoupai) {
        this.totalShoupai = totalShoupai;
    }

    public List<PukePai> getLiangPaiList() {
        return liangPaiList;
    }

    public void setLiangPaiList(List<PukePai> liangPaiList) {
        this.liangPaiList = liangPaiList;
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

    public List<DaPaiDianShuSolution> getYaPaiSolutionCandidates() {
        return yaPaiSolutionCandidates;
    }

    public void setYaPaiSolutionCandidates(List<DaPaiDianShuSolution> yaPaiSolutionCandidates) {
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
}
