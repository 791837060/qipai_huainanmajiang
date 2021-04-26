package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.player.DoudizhuPlayer;
import com.dml.doudizhu.preparedapai.fapai.FaPaiStrategy;
import com.dml.puke.pai.PukePai;

/**
 * 每个玩家十七张牌
 * 
 * @author lsc
 *
 */
public class OnePlayerShiqizhangpaiFaPaiStrategy implements FaPaiStrategy {

	private int renshu;
	private boolean qxp;// 去小牌
	private boolean szfbxp;// 三张发不洗牌

	public OnePlayerShiqizhangpaiFaPaiStrategy(int renshu, boolean qxp, boolean szfbxp) {
		this.renshu = renshu;
		this.qxp = qxp;
		this.szfbxp = szfbxp;
	}

	@Override
	public void fapai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		List<PukePai> avaliablePaiList = currentPan.getAvaliablePaiList();
		Map<String, DoudizhuPlayer> doudizhuPlayerIdPlayerMap = currentPan.getDoudizhuPlayerIdPlayerMap();
		// 2人
		if (renshu == 2) {
			List<PukePai> filterAvaliablePaiList = new ArrayList<>();
			if (qxp) {// 去小牌
				for (PukePai pukePai : avaliablePaiList) {
					if (pukePai.getPaiMian().dianShu().ordinal() > 3) {// 去除3、4、5、6共16张
						filterAvaliablePaiList.add(pukePai);
					}
				}
				avaliablePaiList = filterAvaliablePaiList;
			} else {
				for (PukePai pukePai : avaliablePaiList) {
					if (pukePai.getPaiMian().dianShu().ordinal() > 1) {// 去除3与4共8张
						filterAvaliablePaiList.add(pukePai);
					}
				}
				avaliablePaiList = filterAvaliablePaiList;
			}
			for (int i = 0; i < 17; i++) {
				for (DoudizhuPlayer player : doudizhuPlayerIdPlayerMap.values()) {
					PukePai pukePai = avaliablePaiList.remove(0);
					player.addShouPai(pukePai);
				}
			}
		} else {
			if (szfbxp) {
				for (int i = 0; i < 5; i++) {
					for (DoudizhuPlayer player : doudizhuPlayerIdPlayerMap.values()) {
						for (int j = 0; j < 3; j++) {
							PukePai pukePai = avaliablePaiList.remove(0);
							player.addShouPai(pukePai);
						}
					}
				}
				for (DoudizhuPlayer player : doudizhuPlayerIdPlayerMap.values()) {
					for (int j = 0; j < 2; j++) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
				}
			} else {
				for (int i = 0; i < 17; i++) {
					for (DoudizhuPlayer player : doudizhuPlayerIdPlayerMap.values()) {
						PukePai pukePai = avaliablePaiList.remove(0);
						player.addShouPai(pukePai);
					}
				}
			}
		}
		currentPan.setAvaliablePaiList(avaliablePaiList);
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public boolean isQxp() {
		return qxp;
	}

	public void setQxp(boolean qxp) {
		this.qxp = qxp;
	}

	public boolean isSzfbxp() {
		return szfbxp;
	}

	public void setSzfbxp(boolean szfbxp) {
		this.szfbxp = szfbxp;
	}

}
