package com.anbang.qipai.paodekuai.cqrs.c.domain;

import com.anbang.qipai.paodekuai.cqrs.c.domain.listener.BoomCountDaActionStatisticsListener;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.PaodekuaiPanPlayerResult;
import com.anbang.qipai.paodekuai.cqrs.c.domain.result.PaodekuaiPanResult;
import com.dml.paodekuai.ju.Ju;
import com.dml.paodekuai.pan.CurrentPanResultBuilder;
import com.dml.paodekuai.pan.Pan;
import com.dml.paodekuai.pan.PanResult;
import com.dml.paodekuai.pan.PanValueObject;
import com.dml.paodekuai.player.PaodekuaiPlayer;
import com.dml.paodekuai.wanfa.OptionalPlay;
import com.dml.puke.wanfa.position.Position;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PaodekuaiCurrentPanResultBuilder implements CurrentPanResultBuilder {
    private int renshu;
    private OptionalPlay optionalPlay;
    private double difen;

    @Override
    public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
        Pan currentPan = ju.getCurrentPan();

        // 玩家之前盘的得分
        PaodekuaiPanResult latestFinishedPanResult = (PaodekuaiPanResult) ju.findLatestFinishedPanResult();
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        if (latestFinishedPanResult != null) {
            for (PaodekuaiPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
            }
        } else {
            for (String playerId : currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().keySet()) {
                if (optionalPlay.isJinyuanzi()) {
                    playerTotalScoreMap.put(playerId, (double) optionalPlay.getYuanzifen());//如果是进园子 默认初始分是园子分
                } else {
                    playerTotalScoreMap.put(playerId, 0d);//如果不是进园子 默认初始分是0
                }
            }
        }

        // 玩家炸弹数
        BoomCountDaActionStatisticsListener paodekuaiListener = ju.getActionStatisticsListenerManager().findDaListener(BoomCountDaActionStatisticsListener.class);
        Map<String, Integer> boomMap = paodekuaiListener.getPlayerzhadanshuMap();
        Map<String, Integer> playerBeiyaZhadanShuMap = paodekuaiListener.getPlayerBeiyaZhadanShuMap();

        // List<String> noPaiPlayerIdList = currentPan.getNoPaiPlayerIdList();
        List<String> playerIds = currentPan.findAllPlayerId();
        String zhuaniaoPlayer = currentPan.getZhuaniaoPlayerId();

        int playPaiCount;//初始手牌数量
        if (optionalPlay.isShiwuzhang()) {
            playPaiCount = 15;
        } else if (optionalPlay.isShiliuzhang()) {
            playPaiCount = 16;
        } else {
            playPaiCount = 17;
        }

        List<PaodekuaiPanPlayerResult> panPlayerResultList = new ArrayList<>();

        // 赢家
        String winner = currentPan.getNoPaiPlayerIdList().get(0);
        boolean winnerZhuaniao = winner.equals(zhuaniaoPlayer);
        int winnerScore = 0;
        int winnerBoomScore = 0;
//		if (boomMap.get(winner) != null) {
//			winnerBoomScore = boomMap.get(winner) * PaodekuaiConstant.BOOM_SCORE;
//		}
        int guanmenCount = 0;

        if (optionalPlay.isYingsuanzha()) {
            for (String playerId : boomMap.keySet()) {
                if (!playerId.equals(winner)) {
                    boomMap.put(playerId, 0);
                } else {
                    Integer beiyaZhadanCount = playerBeiyaZhadanShuMap.get(playerId);
                    Integer zhadanCount = boomMap.get(playerId);
                    if (beiyaZhadanCount != null && zhadanCount != null) {
                        int boomCount = zhadanCount - beiyaZhadanCount;
                        if (boomCount < 0) {
                            boomCount = 0;
                        }
                        boomMap.put(playerId, boomCount);
                    }

                }
            }
        }

        // 输家
        List<String> loserList = playerIds.stream().filter(p -> !p.equals(winner)).collect(Collectors.toList());
        for (String loser : loserList) {
            PaodekuaiPanPlayerResult loserPanResulet = new PaodekuaiPanPlayerResult();
            loserPanResulet.setPlayerId(loser);
            int paiScore = currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().get(loser).getAllShoupai().size();
            loserPanResulet.setYupaiCount(paiScore);
            if (paiScore == 1) {
//			    if (!optionalPlay.isBaodankoufen()){
//                    paiScore = 0;
//                }
                loserPanResulet.setBaodan(true);
            }

            if (optionalPlay.isDaxiaoguan()) {  //大小关门
                if (paiScore == playPaiCount) {
                    paiScore = paiScore * 2;
                    loserPanResulet.setGuanmen(true);
                    guanmenCount++;
                } else if (paiScore == (playPaiCount - 1)) {
                    loserPanResulet.setXiaoguan(true);
                    if (optionalPlay.isXiaoguanjiufen()) {
                        paiScore += 9;
                    } else {
                        paiScore += 5;
                    }
                }
            }

            String dongPlayer = currentPan.getPositionPlayerIdMap().get(Position.dong);
            PaodekuaiPlayer player = currentPan.getPaodekuaiPlayerIdMajiangPlayerMap().get(dongPlayer);
            if (dongPlayer.equals(loser) && player.getDaCount() == 1 && optionalPlay.isFanguan()) { //反关门(只出一手牌)
                paiScore *= 2;
                loserPanResulet.setFanguan(true);
            }

            if (boomMap.get(winner) >= 1 && optionalPlay.isZhadanfanbei()) {    //赢家有炸弹并且有炸弹翻倍玩法 分数翻倍
                paiScore *= 2;
            }

            if (boomMap.get(loser) != null) { // 输家可能一张牌都没出
                int loserBoomCount = boomMap.get(loser);
                loserPanResulet.setZhadanCount(loserBoomCount);
            }
            int loserScore = paiScore + winnerBoomScore;
            loserPanResulet.setScore(-loserScore);
            loserPanResulet.setTotalScore(-loserScore + playerTotalScoreMap.get(loser));
            panPlayerResultList.add(loserPanResulet);
            winnerScore = winnerScore + loserScore;
        }

        PaodekuaiPanPlayerResult winnerPanResulet = new PaodekuaiPanPlayerResult();
        winnerPanResulet.setPlayerId(winner);
        winnerPanResulet.setZhadanCount(boomMap.get(winner));
        winnerPanResulet.setZhuaniao(winnerZhuaniao);
        winnerPanResulet.setScore(winnerScore);
        winnerPanResulet.setTotalScore(winnerScore + playerTotalScoreMap.get(winner));
        winnerPanResulet.setWin(true);
        winnerPanResulet.setGuanmenCount(guanmenCount);
        panPlayerResultList.add(winnerPanResulet);

        for (PaodekuaiPanPlayerResult panPlayerResult1 : panPlayerResultList) {
            for (PaodekuaiPanPlayerResult panPlayerResult2 : panPlayerResultList) {
                if (!panPlayerResult1.getPlayerId().equals(panPlayerResult2.getPlayerId())) {
                    int zhadanCount1 = panPlayerResult1.getZhadanCount();
                    int zhadanCount2 = panPlayerResult2.getZhadanCount();
                    if (zhadanCount1 != zhadanCount2) {
                        panPlayerResult1.setTotalScore(panPlayerResult1.getTotalScore() - panPlayerResult1.getScore());
                        panPlayerResult2.setTotalScore(panPlayerResult2.getTotalScore() - panPlayerResult2.getScore());
                        if (optionalPlay.isZhadanshifen()) {
                            panPlayerResult1.setScore(panPlayerResult1.getScore() + ((zhadanCount1 - zhadanCount2) * 5));
                            panPlayerResult2.setScore(panPlayerResult2.getScore() + ((zhadanCount2 - zhadanCount1) * 5));
                        }
                        if (optionalPlay.isZhadanwufen()) {
                            panPlayerResult1.setScore(panPlayerResult1.getScore() + ((zhadanCount1 - zhadanCount2) * 2.5));
                            panPlayerResult2.setScore(panPlayerResult2.getScore() + ((zhadanCount2 - zhadanCount1) * 2.5));
                        }
                        panPlayerResult1.setTotalScore(panPlayerResult1.getTotalScore() + panPlayerResult1.getScore());
                        panPlayerResult2.setTotalScore(panPlayerResult2.getTotalScore() + panPlayerResult2.getScore());
                    }
                }
            }
        }

        for (PaodekuaiPanPlayerResult panPlayerResult : panPlayerResultList) {
            panPlayerResult.setTotalScore(panPlayerResult.getTotalScore() - panPlayerResult.getScore());
            panPlayerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(panPlayerResult.getScore()))).doubleValue());
            panPlayerResult.setTotalScore(panPlayerResult.getTotalScore() + panPlayerResult.getScore());
        }

        if (optionalPlay.isJinyuanzi()) {    //进园子总分不会低于0分
            double chaScore = 0;
            String playerId1 = "";//多个玩家赢分时非赢家的玩家
            for (PaodekuaiPanPlayerResult playerResult : panPlayerResultList) {
                if (!winner.equals(playerResult.getPlayerId())) {
                    if (playerResult.getTotalScore() <= 0) {
                        chaScore += playerResult.getTotalScore();
                        playerResult.setTotalScore(0d);
                    } else {
                        playerId1 = playerResult.getPlayerId();
                    }
                }
                if (playerResult.getTotalScore() <= 0) {    //如果总分小于等于0分 放入光头集合
                    ju.getGaungtouPlayers().add(playerResult.getPlayerId());
                } else {
                    ju.getGaungtouPlayers().remove(playerResult.getPlayerId());
                }
            }

            for (PaodekuaiPanPlayerResult playerResult : panPlayerResultList) { //赢家扣除输家多输于0分的分数
                if (winner.equals(playerResult.getPlayerId())) {
                    double totalScore = playerResult.getTotalScore();
                    playerResult.setTotalScore(totalScore + chaScore);
                }
            }

            for (PaodekuaiPanPlayerResult playerResult : panPlayerResultList) {
                if (winner.equals(playerResult.getPlayerId())) {
                    double totalScore = playerResult.getTotalScore();
                    if (totalScore < 0) {   //如果赢家分数小于0 就扣除多个玩家赢分时非赢家的玩家多赢得分
                        for (PaodekuaiPanPlayerResult playerResult1 : panPlayerResultList) {
                            if (playerResult1.getPlayerId().equals(playerId1)) {
                                double totalScore1 = playerResult1.getTotalScore();
                                playerResult1.setTotalScore(totalScore1 - playerResult.getTotalScore());
                            }
                        }
                        playerResult.setTotalScore(0);
                    }
                }
            }

        }

        Map<String, PaodekuaiPlayer> playerIdPlayerMap = currentPan.getPaodekuaiPlayerIdMajiangPlayerMap();
        for (PaodekuaiPanPlayerResult playerResult : panPlayerResultList) {
            PaodekuaiPlayer player = playerIdPlayerMap.get(playerResult.getPlayerId());
            player.setPlayerTotalScore(playerResult.getTotalScore());
        }

        PaodekuaiPanResult paodekuaiPanResult = new PaodekuaiPanResult();
        paodekuaiPanResult.setPan(new PanValueObject(currentPan));
        paodekuaiPanResult.setPanFinishTime(panFinishTime);
        paodekuaiPanResult.setPanPlayerResultList(panPlayerResultList);
        return paodekuaiPanResult;

    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

}
