package com.anbang.qipai.biji.cqrs.c.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJuResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanResult;
import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.ju.JuResult;
import com.dml.shisanshui.ju.JuResultBuilder;
import com.dml.shisanshui.pan.PanResult;

public class BijiJuResultBuilder implements JuResultBuilder {

	@Override
	public JuResult buildJuResult(Ju ju) {
		BijiJuResult juResult = new BijiJuResult();
		juResult.setFinishedPanCount(ju.countFinishedPan());
		if (ju.countFinishedPan() > 0) {
			Map<String, BijiJuPlayerResult> juPlayerResultMap = new HashMap<>();
			for (PanResult panResult : ju.getFinishedPanResultList()) {
				BijiPanResult doudizhuPanResult = (BijiPanResult) panResult;
				for (BijiPanPlayerResult panPlayerResult : doudizhuPanResult.getPanPlayerResultList()) {
					BijiJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
					if (juPlayerResult == null) {
						juPlayerResult = new BijiJuPlayerResult();
						juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
						juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
					}
					if (panPlayerResult.getJiesuanScore().hasTeshupaixing()) {
						juPlayerResult.increaseTspx();
					}
					if (panPlayerResult.getJiesuanScore().isTongguan()) {
						juPlayerResult.increaseQld();
					}
//					juPlayerResult.increaseTotalScore(panPlayerResult.getJiesuanScore().getValue());
					juPlayerResult.setTotalScore(panPlayerResult.getTotalScore());
				}
			}

			BijiJuPlayerResult dayingjia = null;
			BijiJuPlayerResult datuhao = null;
			for (BijiJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
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
			juResult.setDatuhaoId(datuhao.getPlayerId());
			juResult.setDayingjiaId(dayingjia.getPlayerId());
			juResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
		}
		return juResult;
	}

}
