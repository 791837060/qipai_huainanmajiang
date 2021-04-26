package com.anbang.qipai.guandan.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanJuPlayerResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanJuResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanPlayerResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanResult;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.ju.JuResult;
import com.dml.shuangkou.ju.JuResultBuilder;
import com.dml.shuangkou.pan.PanResult;

public class GuandankouJuResultBuilder implements JuResultBuilder {

	@Override
	public JuResult buildJuResult(Ju ju) {
		GuandanJuResult guandanJuResult = new GuandanJuResult();
		guandanJuResult.setFinishedPanCount(ju.countFinishedPan());
		if (ju.countFinishedPan() > 0) {
			Map<String, GuandanJuPlayerResult> juPlayerResultMap = new HashMap<>();
			for (PanResult panResult : ju.getFinishedPanResultList()) {
				GuandanPanResult guandanPanResult = (GuandanPanResult) panResult;
				for (GuandanPanPlayerResult panPlayerResult : guandanPanResult.getPanPlayerResultList()) {
					GuandanJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
					if (juPlayerResult == null) {
						juPlayerResult = new GuandanJuPlayerResult();
						juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
						juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
					}
					GuandanMingcifen mingcifen = panPlayerResult.getMingcifen();
					if (mingcifen.isYing() && mingcifen.isShuangkou()) {
						juPlayerResult.increaseShuangkouCount();
					}
					if (mingcifen.isYing() && mingcifen.isDankou()) {
						juPlayerResult.increaseDankouCount();
					}
					if (mingcifen.isYing() && mingcifen.isPingkou()) {
						juPlayerResult.increasePingkouCount();
					}
//					juPlayerResult.tryAndUpdateMaxXianshu(panPlayerResult.getXianshubeishu());
					juPlayerResult.increaseTotalScore(panPlayerResult.getScore());
				}
			}

			GuandanJuPlayerResult dayingjia = null;
			GuandanJuPlayerResult datuhao = null;
			for (GuandanJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
				if (dayingjia == null) {
					dayingjia = juPlayerResult;
				} else {
					if (juPlayerResult.getTotalScore() > dayingjia.getTotalScore()) {
						dayingjia = juPlayerResult;
					}
				}

				if (datuhao == null) {
					datuhao = juPlayerResult;
				} else {
					if (juPlayerResult.getTotalScore() < datuhao.getTotalScore()) {
						datuhao = juPlayerResult;
					}
				}
			}
			guandanJuResult.setDatuhaoId(datuhao.getPlayerId());
			guandanJuResult.setDayingjiaId(dayingjia.getPlayerId());
			guandanJuResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
		}
		return guandanJuResult;
	}

}
