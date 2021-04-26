package com.anbang.qipai.doudizhu.web.vo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;

public class DoudizhuBeishuVO {
	private int qiangdizhuCount;// 抢地主次数
	private boolean dipaiHasDuiA;// 底牌中有对A
	private int dpDA = 2;
	private boolean dipaiHasDuier;// 底牌中有对2
	private int dpD2 = 2;
	private boolean dipaiTonghua;// 三张底牌是同花
	private int dpTH = 3;
	private boolean dipaiShunzi;// 三张底牌是顺子
	private int dpSZ = 3;
	private boolean dipaiTongdianshu;// 三张底牌点数相同
	private int dpTD = 3;
	private boolean dipaiXiaoyuShi;// 三张底牌都<10点
	private int dpLS = 3;
	private boolean dipaihasDanwang;// 三张底牌含单王
	private int dpDW = 2;
	private boolean dipaihasShuangwang;// 三张底牌含双王
	private int dpSW = 4;
	private int dachuZhadanCount;// 玩家打出炸弹 火箭
	private boolean chuntian;// 春天(地主出完所有牌农民一张未出)
	private boolean fanchuntian;// 反春天(任一农民出完牌,地主只出过一手牌)
    private int maxJiaofen;
    private int value;

	public DoudizhuBeishuVO() {

	}

	public DoudizhuBeishuVO(DoudizhuBeishu beishu) {
        qiangdizhuCount = beishu.getQiangdizhuCount();
        dipaiHasDuiA = false;
        dipaiHasDuier = false;
        dipaiTonghua = false;
        dipaiShunzi = false;
        dipaiTongdianshu = false;
        dipaiXiaoyuShi = false;
        dipaihasDanwang = false;
        dipaihasShuangwang = false;
        dachuZhadanCount = beishu.getDachuZhadanCount();
        chuntian = beishu.isChuntian();
        fanchuntian = beishu.isFanchuntian();
        maxJiaofen = beishu.getJiaofen();
        value = beishu.getValue();

    }

	public int getQiangdizhuCount() {
		return qiangdizhuCount;
	}

	public void setQiangdizhuCount(int qiangdizhuCount) {
		this.qiangdizhuCount = qiangdizhuCount;
	}

	public boolean isDipaiHasDuiA() {
		return dipaiHasDuiA;
	}

	public void setDipaiHasDuiA(boolean dipaiHasDuiA) {
		this.dipaiHasDuiA = dipaiHasDuiA;
	}

	public boolean isDipaiHasDuier() {
		return dipaiHasDuier;
	}

	public void setDipaiHasDuier(boolean dipaiHasDuier) {
		this.dipaiHasDuier = dipaiHasDuier;
	}

	public boolean isDipaiTonghua() {
		return dipaiTonghua;
	}

	public void setDipaiTonghua(boolean dipaiTonghua) {
		this.dipaiTonghua = dipaiTonghua;
	}

	public boolean isDipaiShunzi() {
		return dipaiShunzi;
	}

	public void setDipaiShunzi(boolean dipaiShunzi) {
		this.dipaiShunzi = dipaiShunzi;
	}

	public boolean isDipaiTongdianshu() {
		return dipaiTongdianshu;
	}

	public void setDipaiTongdianshu(boolean dipaiTongdianshu) {
		this.dipaiTongdianshu = dipaiTongdianshu;
	}

	public boolean isDipaiXiaoyuShi() {
		return dipaiXiaoyuShi;
	}

	public void setDipaiXiaoyuShi(boolean dipaiXiaoyuShi) {
		this.dipaiXiaoyuShi = dipaiXiaoyuShi;
	}

	public int getDachuZhadanCount() {
		return dachuZhadanCount;
	}

	public void setDachuZhadanCount(int dachuZhadanCount) {
		this.dachuZhadanCount = dachuZhadanCount;
	}

	public boolean isChuntian() {
		return chuntian;
	}

	public void setChuntian(boolean chuntian) {
		this.chuntian = chuntian;
	}

	public boolean isFanchuntian() {
		return fanchuntian;
	}

	public void setFanchuntian(boolean fanchuntian) {
		this.fanchuntian = fanchuntian;
	}

	public int getDpDA() {
		return dpDA;
	}

	public void setDpDA(int dpDA) {
		this.dpDA = dpDA;
	}

	public int getDpD2() {
		return dpD2;
	}

	public void setDpD2(int dpD2) {
		this.dpD2 = dpD2;
	}

	public int getDpTH() {
		return dpTH;
	}

	public void setDpTH(int dpTH) {
		this.dpTH = dpTH;
	}

	public int getDpSZ() {
		return dpSZ;
	}

	public void setDpSZ(int dpSZ) {
		this.dpSZ = dpSZ;
	}

	public int getDpTD() {
		return dpTD;
	}

	public void setDpTD(int dpTD) {
		this.dpTD = dpTD;
	}

	public int getDpLS() {
		return dpLS;
	}

	public void setDpLS(int dpLS) {
		this.dpLS = dpLS;
	}

	public boolean isDipaihasDanwang() {
		return dipaihasDanwang;
	}

	public void setDipaihasDanwang(boolean dipaihasDanwang) {
		this.dipaihasDanwang = dipaihasDanwang;
	}

	public int getDpDW() {
		return dpDW;
	}

	public void setDpDW(int dpDW) {
		this.dpDW = dpDW;
	}

	public boolean isDipaihasShuangwang() {
		return dipaihasShuangwang;
	}

	public void setDipaihasShuangwang(boolean dipaihasShuangwang) {
		this.dipaihasShuangwang = dipaihasShuangwang;
	}

	public int getDpSW() {
		return dpSW;
	}

	public void setDpSW(int dpSW) {
		this.dpSW = dpSW;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

    public int getMaxJiaofen() {
        return maxJiaofen;
    }

    public void setMaxJiaofen(int maxJiaofen) {
        this.maxJiaofen = maxJiaofen;
    }
}
