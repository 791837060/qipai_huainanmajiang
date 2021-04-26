package com.dml.doudizhu.preparedapai.luanpai;

import java.util.ArrayList;
import java.util.List;

import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.pan.PanValueObject;
import com.dml.doudizhu.player.DoudizhuPlayerValueObject;
import com.dml.puke.pai.PukePai;
import com.dml.puke.wanfa.dianshu.paizu.DianShuZuPaiZu;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;

/**
 * 按照上一盘的出牌的顺序将牌叠在一起
 * 
 * @author lsc
 *
 */
public class LastPanChuPaiOrdinalLuanpaiStrategy implements LuanPaiStrategy {

	@Override
	public void luanpai(Ju ju) throws Exception {
		Pan currentPan = ju.getCurrentPan();
		PanValueObject lastPan = ju.findLatestFinishedPanResult().getPan();
		List<DianShuZuPaiZu> dachuPaiZuList = lastPan.getDachuPaiZuList();
		String yingjiaPlayerId = lastPan.getYingjiaPlayerId();
		List<PukePai> finalPaiList = new ArrayList<>();
		for (DianShuZuPaiZu paizu : dachuPaiZuList) {
			for (PukePai pukePai : paizu.getPaiArray()) {
				finalPaiList.add(pukePai);
			}
		}
		for (PukePai pukePai : lastPan.getPaiListValueObject().getPaiList()) {
			finalPaiList.add(pukePai);
		}
		DoudizhuPlayerValueObject player = lastPan.findPlayer(yingjiaPlayerId);
		for (PukePai pukePai : player.getAllShoupai().values()) {
			finalPaiList.add(pukePai);
		}
		String nextPlayerId = findNextPlayer(lastPan, player);
		while (!nextPlayerId.equals(yingjiaPlayerId)) {
			player = lastPan.findPlayer(nextPlayerId);
			for (PukePai pukePai : player.getAllShoupai().values()) {
				finalPaiList.add(pukePai);
			}
			nextPlayerId = findNextPlayer(lastPan, player);
		}
		currentPan.setAvaliablePaiList(finalPaiList);
	}

	public String findNextPlayer(PanValueObject lastPan, DoudizhuPlayerValueObject player) {
		Position nextPosition = PositionUtil.nextPositionClockwise(player.getPosition());
		String nextPlayerId = lastPan.getPositionPlayerIdMap().get(nextPosition);
		while (nextPlayerId == null) {
			nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
			nextPlayerId = lastPan.getPositionPlayerIdMap().get(nextPosition);
		}
		return nextPlayerId;
	}

}
