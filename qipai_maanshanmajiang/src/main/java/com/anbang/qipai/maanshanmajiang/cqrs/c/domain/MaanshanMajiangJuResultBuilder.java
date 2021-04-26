package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.ju.result.JuResult;
import com.dml.majiang.ju.result.JuResultBuilder;
import com.dml.majiang.pai.MajiangPai;
import com.dml.majiang.pan.result.PanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaanshanMajiangJuResultBuilder implements JuResultBuilder {

    @Override
    public JuResult buildJuResult(Ju ju) {
        MaanshanMajiangJuResult maanshanMajiangJuResult = new MaanshanMajiangJuResult();
        maanshanMajiangJuResult.setFinishedPanCount(ju.countFinishedPan());
        if (ju.countFinishedPan() > 0) {
            Map<String, MaanshanMajiangJuPlayerResult> juPlayerResultMap = new HashMap<>();
            for (PanResult panResult : ju.getFinishedPanResultList()) {
                MaanshanMajiangPanResult maanshanMajiangPanResult = (MaanshanMajiangPanResult) panResult;
                for (MaanshanMajiangPanPlayerResult panPlayerResult : maanshanMajiangPanResult.getPanPlayerResultList()) {
                    MaanshanMajiangJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
                    if (juPlayerResult == null) {
                        juPlayerResult = new MaanshanMajiangJuPlayerResult();
                        juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
                        juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
                    }
                    if (maanshanMajiangPanResult.ifPlayerHu(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseHuCount();
                    }
                    juPlayerResult.increaseCaishenCount(maanshanMajiangPanResult.playerGuipaiCount(panPlayerResult.getPlayerId()));
                    if (maanshanMajiangPanResult.ifPlayerHu(panPlayerResult.getPlayerId()) && maanshanMajiangPanResult.isZimo()) {
                        juPlayerResult.increaseZiMoCount();
                    }
                    String dianPaoPlayerId = maanshanMajiangPanResult.getDianpaoPlayerId();
                    if (dianPaoPlayerId != null && dianPaoPlayerId.equals(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseFangPaoCount();
                    }
                    juPlayerResult.setTotalScore(panPlayerResult.getTotalScore());
                }
            }

            MaanshanMajiangJuPlayerResult dayingjia = null;
            MaanshanMajiangJuPlayerResult datuhao = null;
            for (MaanshanMajiangJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
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
            maanshanMajiangJuResult.setDatuhaoId(datuhao.getPlayerId());
            maanshanMajiangJuResult.setDayingjiaId(dayingjia.getPlayerId());
            maanshanMajiangJuResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
        }
        return maanshanMajiangJuResult;
    }

    public JuResult buildJuResult(Ju ju, MajiangGame majiangGame) {
        MaanshanMajiangJuResult maanshanMajiangJuResult = new MaanshanMajiangJuResult();
        maanshanMajiangJuResult.setFinishedPanCount(ju.countFinishedPan());
        if (ju.countFinishedPan() > 0) {
            Map<String, MaanshanMajiangJuPlayerResult> juPlayerResultMap = new HashMap<>();
            for (PanResult panResult : ju.getFinishedPanResultList()) {
                MaanshanMajiangPanResult maanshanMajiangPanResult = (MaanshanMajiangPanResult) panResult;
                for (MaanshanMajiangPanPlayerResult panPlayerResult : maanshanMajiangPanResult.getPanPlayerResultList()) {
                    MaanshanMajiangJuPlayerResult juPlayerResult = juPlayerResultMap.get(panPlayerResult.getPlayerId());
                    if (juPlayerResult == null) {
                        juPlayerResult = new MaanshanMajiangJuPlayerResult();
                        juPlayerResult.setPlayerId(panPlayerResult.getPlayerId());
                        juPlayerResultMap.put(panPlayerResult.getPlayerId(), juPlayerResult);
                    }
                    if (maanshanMajiangPanResult.ifPlayerHu(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseHuCount();
                    }
                    juPlayerResult.increaseCaishenCount(maanshanMajiangPanResult.playerGuipaiCount(panPlayerResult.getPlayerId()));
                    if (maanshanMajiangPanResult.ifPlayerHu(panPlayerResult.getPlayerId()) && maanshanMajiangPanResult.isZimo()) {
                        juPlayerResult.increaseZiMoCount();
                    }
                    String dianPaoPlayerId = maanshanMajiangPanResult.getDianpaoPlayerId();
                    if (dianPaoPlayerId != null && dianPaoPlayerId.equals(panPlayerResult.getPlayerId())) {
                        juPlayerResult.increaseFangPaoCount();
                    }
                    juPlayerResult.setTotalScore(panPlayerResult.getTotalScore());
                }
            }

            double suanfa = majiangGame.getOptionalPlay().getSuanfa();
            int daozi = majiangGame.getOptionalPlay().getDaozi();
            int daoziScore = daozi * 50;
            Map<String, List<Double>> playerTotalDaoScoreMap = majiangGame.getPlayerTotalDaoScoreMap();
            Map<String, Double> playerTiwaixunhuanScoreMap = majiangGame.getPlayerTiwaixunhuanScoreMap();

            for (String playerId : juPlayerResultMap.keySet()) {
                double playerTotalScore = 0;
                List<Double> daoScore = playerTotalDaoScoreMap.get(playerId);
                for (Double score : daoScore) {
                    playerTotalScore += score;
                }
                playerTotalScore -= daoziScore;
                Double tiwaixunhuanScore = playerTiwaixunhuanScoreMap.get(playerId);
                playerTotalScore += tiwaixunhuanScore;
                playerTotalScore *= (suanfa / 50);
                MaanshanMajiangJuPlayerResult maanshanMajiangJuPlayerResult = juPlayerResultMap.get(playerId);
                maanshanMajiangJuPlayerResult.setTotalScore(playerTotalScore);
            }

            MaanshanMajiangJuPlayerResult dayingjia = null;
            MaanshanMajiangJuPlayerResult datuhao = null;
            for (MaanshanMajiangJuPlayerResult juPlayerResult : juPlayerResultMap.values()) {
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
            maanshanMajiangJuResult.setDatuhaoId(datuhao.getPlayerId());
            maanshanMajiangJuResult.setDayingjiaId(dayingjia.getPlayerId());
            maanshanMajiangJuResult.setPlayerResultList(new ArrayList<>(juPlayerResultMap.values()));
        }
        return maanshanMajiangJuResult;
    }

}
