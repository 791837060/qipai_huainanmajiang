package com.anbang.qipai.maanshanmajiang.cqrs.c.domain;

import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanValueObject;
import com.dml.majiang.pan.result.CurrentPanResultBuilder;
import com.dml.majiang.pan.result.PanResult;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.position.MajiangPosition;
import com.dml.majiang.position.MajiangPositionUtil;

import java.math.BigDecimal;
import java.util.*;

public class MaanshanMajiangPanResultBuilder implements CurrentPanResultBuilder {
    private OptionalPlay optionalPlay;  //玩法
    private Double difen;               //底分

    /**
     * 构建局得分结果
     *
     * @param ju            当前局
     * @param panFinishTime 结束时间
     */
    @Override
    public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
        Pan currentPan = ju.getCurrentPan();
        MaanshanMajiangPanResult latestFinishedPanResult = (MaanshanMajiangPanResult) ju.findLatestFinishedPanResult();
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        boolean newDao = false;
        if (latestFinishedPanResult != null) {
            for (MaanshanMajiangPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());//当前盘总分
                if (panPlayerResult.getTotalScore() <= 0) {
                    newDao = true;
                    break;
                }
            }
        } else {
            for (String playerId : currentPan.getMajiangPlayerIdMajiangPlayerMap().keySet()) {
                playerTotalScoreMap.put(playerId, 50d);//暂时1倒50分
            }
        }

        if (newDao) {
            for (String playerId : currentPan.getMajiangPlayerIdMajiangPlayerMap().keySet()) {
                playerTotalScoreMap.put(playerId, 50d);//暂时1倒50分
            }
        }

        List<MajiangPlayer> huPlayers = currentPan.findAllHuPlayers();                          //胡牌玩家集合
        MaanshanMajiangPanResult tianchangxiaohuaPanResult = new MaanshanMajiangPanResult();    //局结果
        tianchangxiaohuaPanResult.setPan(new PanValueObject(currentPan));
        List<String> playerIdList = currentPan.sortedPlayerIdList();                            //玩家ID集合
        List<MaanshanMajiangPanPlayerResult> playerResultList = new ArrayList<>();              //玩家结果

        if (huPlayers.size() > 1) {
            String dianpaoPlayerId = huPlayers.get(0).getHu().getDianpaoPlayerId();//点炮玩家
            //一炮多响
            Map<String, MajiangPlayer> majiangPlayerIdMajiangPlayerMap = currentPan.getMajiangPlayerIdMajiangPlayerMap();
            Map<MajiangPosition, String> menFengMajiangPlayerIdMap = currentPan.getMenFengMajiangPlayerIdMap();
            MajiangPosition dianpaoPlayerMenFeng = majiangPlayerIdMajiangPlayerMap.get(dianpaoPlayerId).getMenFeng();
            MajiangPosition majiangPosition = MajiangPositionUtil.nextPositionAntiClockwise(dianpaoPlayerMenFeng);
            String huPlayerId = null;
            for (int i = 0; i < 4; i++) {
                huPlayerId = menFengMajiangPlayerIdMap.get(majiangPosition);
                boolean playerHu = false;
                for (MajiangPlayer huPlayer : huPlayers) {
                    if (huPlayer.getId().equals(huPlayerId)) {
                        playerHu = true;
                        break;
                    }
                }
                if (huPlayerId != null && playerHu) {
                    break;
                } else {
                    majiangPosition = MajiangPositionUtil.nextPositionAntiClockwise(majiangPosition);
                }
            }

            for (MajiangPlayer player : majiangPlayerIdMajiangPlayerMap.values()) {
                if (!player.getId().equals(huPlayerId)) {
                    player.setHu(null);
                }
            }

            MajiangPlayer huPlayer = majiangPlayerIdMajiangPlayerMap.get(huPlayerId);   //胡牌玩家
            MaanshanMajiangHu hu = (MaanshanMajiangHu) huPlayer.getHu();                //胡牌
            MaanshanMajiangHushu huPlayerHufen = hu.getHufen();                         //牌型胡分
            MaanshanMajiangPanPlayerResult huPlayerResult = new MaanshanMajiangPanPlayerResult();
            huPlayerResult.setPlayerId(huPlayerId);
            double delta = huPlayerHufen.getValue();//胡牌玩家的胡牌分数
            double tiwaixunhuan = huPlayerHufen.getTiwaixunhuan();
            huPlayerResult.setTiwaixunhuanScore(tiwaixunhuan * (playerIdList.size() - 1));
            double winScore = 0;
            huPlayerResult.setHufen(huPlayerHufen);
            playerResultList.add(huPlayerResult);
            for (String playerId : playerIdList) {
                if (!playerId.equals(huPlayer.getId())) {
                    //计算非胡玩家分数
                    MaanshanMajiangPanPlayerResult buHuPlayerResult = new MaanshanMajiangPanPlayerResult();
                    buHuPlayerResult.setPlayerId(playerId);
                    buHuPlayerResult.setHufen(new MaanshanMajiangHushu());
                    MaanshanMajiangHushu hufen = buHuPlayerResult.getHufen();
                    Double buHuplayerTotalScore = playerTotalScoreMap.get(playerId);
                    if (buHuplayerTotalScore >= delta) {
                        hufen.setValue(-delta);
                        buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                        winScore += delta;
                    } else {
                        hufen.setValue(-buHuplayerTotalScore);
                        buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                        winScore += buHuplayerTotalScore;
                    }
                    playerResultList.add(buHuPlayerResult);
                }
            }
            huPlayerHufen.setValue(winScore);
            playerResultList.forEach((playerResult) -> {
                // 计算当盘总分
                double score = playerResult.getHufen().getValue();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                Double totalScore = playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore();
                playerResult.setTotalScore(totalScore);
            });

            tianchangxiaohuaPanResult.setPan(new PanValueObject(currentPan));
            tianchangxiaohuaPanResult.setPanFinishTime(panFinishTime);
            tianchangxiaohuaPanResult.setPanPlayerResultList(playerResultList);
            tianchangxiaohuaPanResult.setHu(true);
            tianchangxiaohuaPanResult.setZimo(false);
            tianchangxiaohuaPanResult.setDianpaoPlayerId(dianpaoPlayerId);
            return tianchangxiaohuaPanResult;
        } else if (huPlayers.size() == 1) { //一人胡
            MajiangPlayer huPlayer = huPlayers.get(0);                      //胡牌玩家
            MaanshanMajiangHu hu = (MaanshanMajiangHu) huPlayer.getHu();    //胡牌
            MaanshanMajiangHushu huPlayerHufen = hu.getHufen();             //牌型胡分
            if (hu.isDianpao()) {
                //点炮胡
                MaanshanMajiangPanPlayerResult huPlayerResult = new MaanshanMajiangPanPlayerResult();
                huPlayerResult.setPlayerId(huPlayer.getId());
                double delta = huPlayerHufen.getValue();//胡牌玩家的胡牌分数
                double tiwaixunhuan = huPlayerHufen.getTiwaixunhuan();
                huPlayerResult.setTiwaixunhuanScore(tiwaixunhuan * (playerIdList.size() - 1));
                double winScore = 0;
                huPlayerResult.setHufen(huPlayerHufen);
                playerResultList.add(huPlayerResult);
                for (String playerId : playerIdList) {
                    if (!playerId.equals(huPlayer.getId())) {
                        //计算非胡玩家分数
                        MaanshanMajiangPanPlayerResult buHuPlayerResult = new MaanshanMajiangPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(new MaanshanMajiangHushu());
                        MaanshanMajiangHushu hufen = buHuPlayerResult.getHufen();
                        Double buHuplayerTotalScore = playerTotalScoreMap.get(playerId);
                        if (buHuplayerTotalScore >= delta) {
                            hufen.setValue(-delta);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += delta;
                        } else {
                            hufen.setValue(-buHuplayerTotalScore);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += buHuplayerTotalScore;
                        }
                        playerResultList.add(buHuPlayerResult);
                    }
                }
                huPlayerHufen.setValue(winScore);
            } else if (hu.isQianggang()) {
                //抢杠胡
                MaanshanMajiangPanPlayerResult huPlayerResult = new MaanshanMajiangPanPlayerResult();
                huPlayerResult.setPlayerId(huPlayer.getId());
                double delta = huPlayerHufen.getValue();//胡牌玩家的胡牌分数
                double tiwaixunhuan = huPlayerHufen.getTiwaixunhuan();
                huPlayerResult.setTiwaixunhuanScore(tiwaixunhuan * (playerIdList.size() - 1));
                double winScore = 0;
                huPlayerResult.setHufen(huPlayerHufen);
                playerResultList.add(huPlayerResult);
                for (String playerId : playerIdList) {
                    if (!playerId.equals(huPlayer.getId())) {
                        //计算非胡玩家分数
                        MaanshanMajiangPanPlayerResult buHuPlayerResult = new MaanshanMajiangPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(new MaanshanMajiangHushu());
                        MaanshanMajiangHushu hufen = buHuPlayerResult.getHufen();
                        Double buHuplayerTotalScore = playerTotalScoreMap.get(playerId);
                        if (buHuplayerTotalScore >= delta) {
                            hufen.setValue(-delta);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += delta;
                        } else {
                            hufen.setValue(-buHuplayerTotalScore);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += buHuplayerTotalScore;
                        }
                        playerResultList.add(buHuPlayerResult);
                    }
                }
                huPlayerHufen.setValue(winScore);
            } else if (hu.isZimo()) {
                //自摸胡
                MaanshanMajiangPanPlayerResult huPlayerResult = new MaanshanMajiangPanPlayerResult();
                huPlayerResult.setPlayerId(huPlayer.getId());
                double delta = huPlayerHufen.getValue();//胡牌玩家的胡牌分数
                double tiwaixunhuan = huPlayerHufen.getTiwaixunhuan();
                huPlayerResult.setTiwaixunhuanScore(tiwaixunhuan * (playerIdList.size() - 1));
                double winScore = 0;
                huPlayerResult.setHufen(huPlayerHufen);
                playerResultList.add(huPlayerResult);
                for (String playerId : playerIdList) {
                    if (!playerId.equals(huPlayer.getId())) {
                        //计算非胡玩家分数
                        MaanshanMajiangPanPlayerResult buHuPlayerResult = new MaanshanMajiangPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(new MaanshanMajiangHushu());
                        MaanshanMajiangHushu hufen = buHuPlayerResult.getHufen();
                        Double buHuplayerTotalScore = playerTotalScoreMap.get(playerId);
                        if (buHuplayerTotalScore >= delta) {
                            hufen.setValue(-delta);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += delta;
                        } else {
                            hufen.setValue(-buHuplayerTotalScore);
                            hufen.setTiwaixunhuan(-tiwaixunhuan);
                            buHuPlayerResult.setTiwaixunhuanScore(-tiwaixunhuan);
                            winScore += buHuplayerTotalScore;
                        }
                        playerResultList.add(buHuPlayerResult);
                    }
                }
                huPlayerHufen.setValue(winScore);
            }
            playerResultList.forEach((playerResult) -> {
                // 计算当盘总分
                double score = playerResult.getHufen().getValue();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                Double totalScore = playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore();
                playerResult.setTotalScore(totalScore);
            });

            tianchangxiaohuaPanResult.setPan(new PanValueObject(currentPan));
            tianchangxiaohuaPanResult.setPanFinishTime(panFinishTime);
            tianchangxiaohuaPanResult.setPanPlayerResultList(playerResultList);
            tianchangxiaohuaPanResult.setHu(true);
            tianchangxiaohuaPanResult.setZimo(hu.isZimo());
            tianchangxiaohuaPanResult.setDianpaoPlayerId(hu.getDianpaoPlayerId());
            return tianchangxiaohuaPanResult;
        } else {
            //流局
            playerIdList.forEach((playerId) -> {
                MaanshanMajiangPanPlayerResult playerResult = new MaanshanMajiangPanPlayerResult();
                playerResult.setPlayerId(playerId);
                playerResult.setHufen(new MaanshanMajiangHushu());
                playerResultList.add(playerResult);
            });
            playerResultList.forEach((playerResult) -> {
                //计算当盘分
                double score = playerResult.getHufen().getValue();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                //计算累计总分
                Double totalScore = playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore();
                playerResult.setTotalScore(totalScore);
                playerResult.setTiwaixunhuanScore(0d);
            });
            tianchangxiaohuaPanResult.setPan(new PanValueObject(currentPan));
            tianchangxiaohuaPanResult.setPanFinishTime(panFinishTime);
            tianchangxiaohuaPanResult.setPanPlayerResultList(playerResultList);
            tianchangxiaohuaPanResult.setHu(false);
            return tianchangxiaohuaPanResult;
        }

    }

    public MaanshanMajiangPanResultBuilder() {
    }

    public MaanshanMajiangPanResultBuilder(OptionalPlay optionalPlay, Double difen) {
        this.optionalPlay = optionalPlay;
        this.difen = difen;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

}
