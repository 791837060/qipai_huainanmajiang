package com.dml.paodekuai.pai.dianshuzu;

import java.util.ArrayList;
import java.util.List;

import com.dml.puke.wanfa.dianshu.dianshuzu.DanGeZhadanDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.DuiziDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LianduiDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.LiansanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.SanzhangDianShuZu;
import com.dml.puke.wanfa.dianshu.dianshuzu.ShunziDianShuZu;

/**
 * 所有的点数组不包括单张点数组和aaa炸
 */
public class PaiXing {
	private List<ShunziDianShuZu> shunziDianShuZuList = new ArrayList<>();
	private List<DuiziDianShuZu> duiziDianShuZuList = new ArrayList<>();
	private List<LianduiDianShuZu> lianduiDianShuZuList = new ArrayList<>();
	private	List<SanzhangDianShuZu> sanzhangDianShuList = new ArrayList<>();
 	private List<SidaierDianShuZu> sidaierDianShuZulist = new ArrayList<>();
 	private List<SidaisanDianShuZu> sidaisanDianShuZuList = new ArrayList<>();
	private List<SandaierDianShuZu> sandaierDianShuZuArrayList = new ArrayList<>();
	private List<SandaiyiDianShuZu> sandaiyiDianShuZuArrayList = new ArrayList<>();
	private List<FeijiDianShuZu> feijiDianShuZuArrayList = new ArrayList<>();
	private List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList = new ArrayList<>();
	private List<DaiPaiZhaDanDianShuZu> daipaiZhaDanDianShuZuList = new ArrayList<>();

	public PaiXing() {

	}

	public boolean hasZhadan() {
		if (danGeZhadanDianShuZuList.isEmpty()) {
			return true;
		}
		return false;
	}

	public List<DuiziDianShuZu> getDuiziDianShuZuList() {
		return duiziDianShuZuList;
	}

	public void setDuiziDianShuZuList(List<DuiziDianShuZu> duiziDianShuZuList) {
		this.duiziDianShuZuList = duiziDianShuZuList;
	}

	public List<LianduiDianShuZu> getLianduiDianShuZuList() {
		return lianduiDianShuZuList;
	}

	public void setLianduiDianShuZuList(List<LianduiDianShuZu> lianduiDianShuZuList) {
		this.lianduiDianShuZuList = lianduiDianShuZuList;
	}

	public List<ShunziDianShuZu> getShunziDianShuZuList() {
		return shunziDianShuZuList;
	}

	public void setShunziDianShuZuList(List<ShunziDianShuZu> shunziDianShuZuList) {
		this.shunziDianShuZuList = shunziDianShuZuList;
	}

	public List<SandaierDianShuZu> getSandaierDianShuZuArrayList() {
		return sandaierDianShuZuArrayList;
	}

	public void setSandaierDianShuZuArrayList(List<SandaierDianShuZu> sandaierDianShuZuArrayList) {
		this.sandaierDianShuZuArrayList = sandaierDianShuZuArrayList;
	}

	public List<FeijiDianShuZu> getFeijiDianShuZuArrayList() {
		return feijiDianShuZuArrayList;
	}

	public void setFeijiDianShuZuArrayList(List<FeijiDianShuZu> feijiDianShuZuArrayList) {
		this.feijiDianShuZuArrayList = feijiDianShuZuArrayList;
	}

	public List<DanGeZhadanDianShuZu> getDanGeZhadanDianShuZuList() {
		return danGeZhadanDianShuZuList;
	}

	public void setDanGeZhadanDianShuZuList(List<DanGeZhadanDianShuZu> danGeZhadanDianShuZuList) {
		this.danGeZhadanDianShuZuList = danGeZhadanDianShuZuList;
	}

	public List<SanzhangDianShuZu> getSanzhangDianShuList() {
		return sanzhangDianShuList;
	}

	public void setSanzhangDianShuList(List<SanzhangDianShuZu> sanzhangDianShuList) {
		this.sanzhangDianShuList = sanzhangDianShuList;
	}

	public List<SidaierDianShuZu> getSidaierDianShuZulist() {
		return sidaierDianShuZulist;
	}

	public void setSidaierDianShuZulist(List<SidaierDianShuZu> sidaierDianShuZulist) {
		this.sidaierDianShuZulist = sidaierDianShuZulist;
	}

	public List<SidaisanDianShuZu> getSidaisanDianShuZuList() {
		return sidaisanDianShuZuList;
	}

	public void setSidaisanDianShuZuList(List<SidaisanDianShuZu> sidaisanDianShuZuList) {
		this.sidaisanDianShuZuList = sidaisanDianShuZuList;
	}

	public List<DaiPaiZhaDanDianShuZu> getDaipaiZhaDanDianShuZuList() {
		return daipaiZhaDanDianShuZuList;
	}

	public void setDaipaiZhaDanDianShuZuList(List<DaiPaiZhaDanDianShuZu> daipaiZhaDanDianShuZuList) {
		this.daipaiZhaDanDianShuZuList = daipaiZhaDanDianShuZuList;
	}

	public List<SandaiyiDianShuZu> getSandaiyiDianShuZuArrayList() {
		return sandaiyiDianShuZuArrayList;
	}

	public void setSandaiyiDianShuZuArrayList(List<SandaiyiDianShuZu> sandaiyiDianShuZuArrayList) {
		this.sandaiyiDianShuZuArrayList = sandaiyiDianShuZuArrayList;
	}
}
