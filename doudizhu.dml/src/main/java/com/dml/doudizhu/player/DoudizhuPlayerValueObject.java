package com.dml.doudizhu.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.player.action.da.solution.DaPaiDianShuSolution;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;

public class DoudizhuPlayerValueObject {
	private String id;
	private Position position;
	private Map<Integer, PukePai> allShoupai;
	private int totalShoupai;
	private int[] shoupaiDianShuAmountArray;
	private List<List<Integer>> shoupaiIdListForSortList;
	private List<DianShuZuPaiZu> lishiDachuPaiZuList;
	private DianShuZuPaiZu publicDachuPaiZu;
	private List<DaPaiDianShuSolution> yaPaiSolutionCandidates;
	private List<DaPaiDianShuSolution> yaPaiSolutionsForTips;
	private boolean guo;

	public DoudizhuPlayerValueObject() {

	}

	public DoudizhuPlayerValueObject(DoudizhuPlayer doudizhuPlayer) {
		id = doudizhuPlayer.getId();
		position = doudizhuPlayer.getPosition();
		allShoupai = new HashMap<>();
		allShoupai.putAll(doudizhuPlayer.getAllShoupai());
		totalShoupai = allShoupai.size();
		shoupaiDianShuAmountArray = doudizhuPlayer.getShoupaiDianShuAmountArray().clone();
		shoupaiIdListForSortList = new ArrayList<>(doudizhuPlayer.getShoupaiIdListForSortList());
		lishiDachuPaiZuList = new ArrayList<>(doudizhuPlayer.getLishiDachuPaiZuList());
		publicDachuPaiZu = doudizhuPlayer.getPublicDachuPaiZu();
		yaPaiSolutionCandidates = new ArrayList<>(doudizhuPlayer.getYaPaiSolutionCandidates().values());
		yaPaiSolutionsForTips = new ArrayList<>(doudizhuPlayer.getYaPaiSolutionsForTips());
		guo = doudizhuPlayer.isGuo();
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

}
