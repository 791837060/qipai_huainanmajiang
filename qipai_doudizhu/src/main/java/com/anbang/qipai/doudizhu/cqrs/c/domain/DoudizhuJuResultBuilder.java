package com.anbang.qipai.doudizhu.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuJuPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuJuResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanPlayerResult;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuPanResult;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.ju.JuResult;
import com.dml.doudizhu.ju.JuResultBuilder;
import com.dml.doudizhu.pan.PanResult;

public class DoudizhuJuResultBuilder implements JuResultBuilder {

	@Override
	public JuResult buildJuResult(Ju ju) {
		DoudizhuJuResult doudizhuJuResult = new DoudizhuJuResult();
		doudizhuJuResult.setFinishedPanCount(ju.countFinishedPan());
		if (ju.countFinishedPan() > 0) {
			Map<String, DoudizhuJuPlayerResult> juPlayerResultMap = new HashMap<>();
			for (PanResult panResult : ju.getFinishedPanResultList()) {
				DoudizhuPanResult doudizhuPanResult = (DoudizhuPanResult) panResult;
				for (DoudizhuPanPlayerResult panPlayerResult : doudizhuPanResult.getPanPlayerResultList()) {
					DoudizhuJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
					if (juPlayerResult == null) {
						juPlayerResult = new DoudizhuJuPlayerResult();
						juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
						juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
					}
					if (panPlayerResult.isYing()) {
						juPlayerResult.increaseYingCount();
					}
					if (panPlayerResult.getBeishu().isChuntian()) {
						juPlayerResult.increaseChuntianCount();
						;
					}
					if (panPlayerResult.getBeishu().isFanchuntian()) {
						juPlayerResult.increaseFanchuntianCount();
					}
					juPlayerResult.tryAndUpdateMaxBeishu(panPlayerResult.getBeishu().getValue());
					juPlayerResult.increaseTotalScore(panPlayerResult.getScore());
				}
			}

			DoudizhuJuPlayerResult dayingjia = null;
			DoudizhuJuPlayerResult datuhao = null;
			for (DoudizhuJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
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
			doudizhuJuResult.setDatuhaoId(datuhao.getPlayerId());
			doudizhuJuResult.setDayingjiaId(dayingjia.getPlayerId());
			doudizhuJuResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
		}
		return doudizhuJuResult;
	}

}
