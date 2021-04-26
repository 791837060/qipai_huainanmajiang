package com.dml.paodekuai.pai.jiesuanpai;

import com.dml.puke.pai.DianShu;

/**
 * 小王当其他牌
 * 
 * @author lsc
 *
 */
public class XiaowangDangPai extends ShoupaiJiesuanPai {
	private String dangType = "xiaowangDang";

	private DianShu[] yuanPai;

	private DianShu dangPai;

	public XiaowangDangPai() {

	}

	public XiaowangDangPai(int num, DianShu dangPai) {
		yuanPai = new DianShu[num];
		for (int i = 0; i < num; i++) {
			yuanPai[i] = DianShu.xiaowang;
		}
		this.dangPai = dangPai;
	}

	@Override
	public String dangType() {
		return dangType;
	}

	@Override
	public DianShu[] getAllYuanPai() {
		return yuanPai;
	}

	public String getDangType() {
		return dangType;
	}

	public void setDangType(String dangType) {
		this.dangType = dangType;
	}

	public DianShu[] getYuanPai() {
		return yuanPai;
	}

	public void setYuanPai(DianShu[] yuanPai) {
		this.yuanPai = yuanPai;
	}

	public DianShu getDangPai() {
		return dangPai;
	}

	public void setDangPai(DianShu dangPai) {
		this.dangPai = dangPai;
	}

	@Override
	public DianShu getDangPaiType() {
		return dangPai;
	}

}
