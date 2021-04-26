package com.dml.doudizhu.preparedapai.dipai;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.puke.pai.DianShu;
import com.dml.puke.pai.HuaSe;
import com.dml.puke.pai.PukePai;

/**
 * 留三张作为底牌
 * 
 * @author lsc
 *
 */
public class SanzhangDipaiDeterminer implements DipaiDeterminer {

	private List<PukePai> dipaiList = new ArrayList<>();

	public boolean dipaiHasDuiA() {
		int count = 0;
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().dianShu().equals(DianShu.A)) {
				count++;
			}
		}
		return count > 1;
	}

	public boolean dipaiHasDuier() {
		int count = 0;
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().dianShu().equals(DianShu.er)) {
				count++;
			}
		}
		return count > 1;
	}

	public boolean dipaiHasXiaowang() {
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().dianShu().equals(DianShu.xiaowang)) {
				return true;
			}
		}
		return false;
	}

	public boolean dipaiHasDawang() {
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().dianShu().equals(DianShu.dawang)) {
				return true;
			}
		}
		return false;
	}

	public boolean dipaiIsTonghua() {
		// 大、小王无花色
		HuaSe huaSe = dipaiList.get(0).getPaiMian().huaSe();
		if (huaSe == null) {
			return false;
		}
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().huaSe() == null || !pukePai.getPaiMian().huaSe().equals(huaSe)) {
				return false;
			}
		}
		return true;
	}

	public boolean dipaiIsShunzi() {
		int one = dipaiList.get(0).getPaiMian().dianShu().ordinal();
		int two = dipaiList.get(1).getPaiMian().dianShu().ordinal();
		int three = dipaiList.get(2).getPaiMian().dianShu().ordinal();
		return Math.abs((one - two) * (two - three) * 2 + 1) == 3;
	}

	public boolean dipaiIsTongdianshu() {
		DianShu dianshu = dipaiList.get(0).getPaiMian().dianShu();
		for (PukePai pukePai : dipaiList) {
			if (!pukePai.getPaiMian().dianShu().equals(dianshu)) {
				return false;
			}
		}
		return true;
	}

	public boolean dipaiXiaoyushi() {
		for (PukePai pukePai : dipaiList) {
			if (pukePai.getPaiMian().dianShu().compareTo(DianShu.shi) >= 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void fadipai(Ju ju) throws Exception {
		dipaiList.clear();
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
		DoudizhuPlayer player = currentPan.findDizhu();
		for (int i = 0; i < 3; i++) {
			PukePai pukePai = avaliablePaiList.remove(0);
			dipaiList.add(pukePai);
			player.addShouPai(pukePai);
		}
	}

	public List<PukePai> getDipaiList() {
		return dipaiList;
	}

	public void setDipaiList(List<PukePai> dipaiList) {
		this.dipaiList = dipaiList;
	}

}
