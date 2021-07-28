package com.anbang.qipai.zongyangmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.result.JuResult;
import com.dml.majiang.ju.result.JuResultBuilder;
import com.dml.majiang.pan.result.PanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ZongyangMajiangJuResultBuilder implements JuResultBuilder {

    @Override
    public JuResult buildJuResult(Ju ju) {
        ZongyangMajiangJuResult tuiDaoHuJuResult = new ZongyangMajiangJuResult();
        tuiDaoHuJuResult.setFinishedPanCount(ju.countFinishedPan());
        if (ju.countFinishedPan() > 0) {
            Map<String, ZongyangMajiangJuPlayerResult> juPlayerResultMap = new HashMap<>();
            for (PanResult panResult : ju.getFinishedPanResultList()) {
                ZongyangMajiangPanResult tuiDaoHuPanResult = (ZongyangMajiangPanResult) panResult;
                for (ZongyangMajiangPanPlayerResult panPlayerResult : tuiDaoHuPanResult.getPanPlayerResultList()) {
                    ZongyangMajiangJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
                    if (juPlayerResult == null) {
                        juPlayerResult = new ZongyangMajiangJuPlayerResult();
                        juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
                        juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
                    }
                    if (tuiDaoHuPanResult.ifPlayerHu(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseHuCount();
                    }
                    juPlayerResult.increaseCaishenCount(tuiDaoHuPanResult.playerGuipaiCount(panPlayerResult.getPlayerId()));
                    if (tuiDaoHuPanResult.ifPlayerHu(panPlayerResult.getPlayerId()) && tuiDaoHuPanResult.isZimo()) {
                        juPlayerResult.increaseZiMoCount();
                    }
                    String dianPaoPlayerId = tuiDaoHuPanResult.getDianpaoPlayerId();
                    if (dianPaoPlayerId != null && dianPaoPlayerId.equals(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseFangPaoCount();
                    }
                    juPlayerResult.setTotalScore(panPlayerResult.getTotalScore());
                }
            }

            ZongyangMajiangJuPlayerResult dayingjia = null;
            ZongyangMajiangJuPlayerResult datuhao = null;
            for (ZongyangMajiangJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
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
            tuiDaoHuJuResult.setDatuhaoId(datuhao.getPlayerId());
            tuiDaoHuJuResult.setDayingjiaId(dayingjia.getPlayerId());
            tuiDaoHuJuResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
        }
        return tuiDaoHuJuResult;
    }

}
