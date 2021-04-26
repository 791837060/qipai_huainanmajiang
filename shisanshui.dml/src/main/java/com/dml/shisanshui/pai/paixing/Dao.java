package com.dml.shisanshui.pai.paixing;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.dml.shisanshui.pai.PukePai;

/**
 * 一道牌
 * 
 * @author lsc
 *
 */
public class Dao {

	/**
	 * 按牌id从小到大排序拼接，<10的左边补零
	 */
	private String index;

	/**
	 * 对牌型、点数、花色进行编码,方便比较
	 */
	private long typeCode;

	private Paixing paixing;

	private List<PukePai> pukePaiList = new ArrayList<>();

	public void calculateIndex() {
		StringBuilder sb = new StringBuilder();
		SortedMap<Integer, PukePai> map = new TreeMap<>();
		for (PukePai pukePai : pukePaiList) {
			map.put(pukePai.getId(), pukePai);
		}
		for (Integer id : map.keySet()) {
			if (id < 10) {
				sb.append('0');
			}
			sb.append(id);
		}
		index = sb.toString();
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public long getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(long typeCode) {
		this.typeCode = typeCode;
	}

	public Paixing getPaixing() {
		return paixing;
	}

	public void setPaixing(Paixing paixing) {
		this.paixing = paixing;
	}

	public List<PukePai> getPukePaiList() {
		return pukePaiList;
	}

	public void setPukePaiList(List<PukePai> pukePaiList) {
		this.pukePaiList = pukePaiList;
	}
}
