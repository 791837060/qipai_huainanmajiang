package com.dml.paodekuai.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.puke.pai.PukePai;
import com.dml.puke.pai.QiShouLiangPaiMark;
import com.dml.puke.pai.ZuDuiLiangPaiMark;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.paodekuai.player.action.da.solution.DaPaiDianShuSolution;

public class PaodekuaiPlayerValueObject {
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
	private boolean shouABiYa;

	public PaodekuaiPlayerValueObject() {

	}

	public PaodekuaiPlayerValueObject(PaodekuaiPlayer paodekuaiPlayer) {
		id = paodekuaiPlayer.getId();
		position = paodekuaiPlayer.getPosition();
		liangPaiList = new ArrayList<>();
		allShoupai = new HashMap<>();
		allShoupai.putAll(paodekuaiPlayer.getAllShoupai());
		for (PukePai pukePai : allShoupai.values()) {
			if (pukePai.getMark() != null && pukePai.getMark() instanceof ZuDuiLiangPaiMark
					|| pukePai.getMark() instanceof QiShouLiangPaiMark) {
				liangPaiList.add(pukePai);
			}
		}
		totalShoupai = allShoupai.size();
		shoupaiDianShuAmountArray = paodekuaiPlayer.getShoupaiDianShuAmountArray().clone();
		shoupaiIdListForSortList = new ArrayList<>(paodekuaiPlayer.getShoupaiIdListForSortList());
		lishiDachuPaiZuList = new ArrayList<>(paodekuaiPlayer.getLishiDachuPaiZuList());
		publicDachuPaiZu = paodekuaiPlayer.getPublicDachuPaiZu();
		yaPaiSolutionCandidates = new ArrayList<>(paodekuaiPlayer.getYaPaiSolutionCandidates().values());
		yaPaiSolutionsForTips = new ArrayList<>(paodekuaiPlayer.getYaPaiSolutionsForTips());
		guo = paodekuaiPlayer.isGuo();
		shouABiYa=paodekuaiPlayer.isShouABiYa();
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

	public boolean isShouABiYa() {
		return shouABiYa;
	}

	public void setShouABiYa(boolean shouABiYa) {
		this.shouABiYa = shouABiYa;
	}
}
