package com.anbang.qipai.biji.cqrs.c.domain;

import java.util.*;

import com.anbang.qipai.biji.cqrs.c.domain.result.BijiJiesuanScore;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanPlayerResult;
import com.anbang.qipai.biji.cqrs.c.domain.result.BijiPanResult;
import com.dml.shisanshui.ju.Ju;
import com.dml.shisanshui.pai.paixing.comparator.DaoComparator;
import com.dml.shisanshui.pan.CurrentPanResultBuilder;
import com.dml.shisanshui.pan.Pan;
import com.dml.shisanshui.pan.PanResult;
import com.dml.shisanshui.pan.PanValueObject;
import com.dml.shisanshui.player.ShisanshuiPlayer;

public class BijiCurrentPanResultBuilder implements CurrentPanResultBuilder {
    private DaoComparator daoComparator;    //道比较器
    private OptionalPlay optionalPlay;      //玩法
    private int renshu;                     //人数
    private Double difen;                   //底分

    @Override
    public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
        BijiPanResult latestFinishedPanResult = (BijiPanResult) ju.findLatestFinishedPanResult();
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        Pan currentPan = ju.getCurrentPan();
        List<BijiPanPlayerResult> panPlayerResultList = new ArrayList<>();
        Map<String, ShisanshuiPlayer> playerIdPlayerMap = currentPan.getPlayerIdPlayerMap();

        if (latestFinishedPanResult != null) {
            for (BijiPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
            }
        } else {
            for (String playerId : currentPan.getPlayerIdPlayerMap().keySet()) {
                if (optionalPlay.isJinyuanzi()) {
                    playerTotalScoreMap.put(playerId, (double) optionalPlay.getYuanzifen());//如果是进园子 默认初始分是园子分
                } else {
                    playerTotalScoreMap.put(playerId, 0d);//如果不是进园子 默认初始分是0
                }
            }
        }

        List<String> playerIdList = currentPan.sortedPlayerIds();
        for (String playerId : playerIdList) {
            BijiPanPlayerResult playerResult = new BijiPanPlayerResult();
            playerResult.setPlayerId(playerId);
            ShisanshuiPlayer player = currentPan.findShisanshuiPlayerById(playerId);
            BijiJiesuanScore jiesuanScore = new BijiJiesuanScore();
            jiesuanScore.setChupaiSolution(player.getChupaiSolution());
            jiesuanScore.setPlayerShoupai(new ArrayList<>(player.getAllShoupai().values()));
            playerResult.setJiesuanScore(jiesuanScore);
            panPlayerResultList.add(playerResult);
        }

        //两两计算每道获胜次数
        for (int i = 0; i < panPlayerResultList.size(); i++) {
            BijiPanPlayerResult playerResult1 = panPlayerResultList.get(i);
            String playerId1 = playerResult1.getPlayerId();
            BijiJiesuanScore score1 = playerResult1.getJiesuanScore();
            ShisanshuiPlayer shisanshuiPlayer = playerIdPlayerMap.get(playerId1);
            for (int j = i + 1; j < panPlayerResultList.size(); j++) {
                BijiPanPlayerResult playerResult2 = panPlayerResultList.get(j);
                String playerId2 = playerResult2.getPlayerId();
                BijiJiesuanScore score2 = playerResult2.getJiesuanScore();
                ShisanshuiPlayer shisanshuiPlayer2 = playerIdPlayerMap.get(playerId2);
                score1.calculatePlayerJiesuanScore(playerId2, score2.getChupaiSolution(), daoComparator, shisanshuiPlayer.isQipai(), shisanshuiPlayer2.isQipai());
                score2.calculatePlayerJiesuanScore(playerId1, score1.getChupaiSolution(), daoComparator, shisanshuiPlayer2.isQipai(), shisanshuiPlayer.isQipai());
            }
        }

        int qipaiCount = 0;//弃牌人数
        for (ShisanshuiPlayer player : playerIdPlayerMap.values()) {
            if (player.isQipai()) qipaiCount++;
        }

        //计算每道基础分
        for (BijiPanPlayerResult playerResult : panPlayerResultList) {
            playerResult.getJiesuanScore().calculateScore(renshu, qipaiCount);
        }

        //计算总分和喜牌分
        for (BijiPanPlayerResult playerResult : panPlayerResultList) {
            playerResult.getJiesuanScore().calculateDaoScore(difen);
            int playXipaiScore = playerResult.getJiesuanScore().calculateXipai(renshu, optionalPlay, qipaiCount, playerIdPlayerMap.get(playerResult.getPlayerId()).isQipai(), difen);
            for (BijiPanPlayerResult otherPlayerResult : panPlayerResultList) {
                if (!otherPlayerResult.getPlayerId().equals(playerResult.getPlayerId()) && !playerIdPlayerMap.get(otherPlayerResult.getPlayerId()).isQipai()) {
                    otherPlayerResult.getJiesuanScore().jiesuan(-playXipaiScore);
                }
            }
        }

        //累加总分
        panPlayerResultList.forEach((playerResult) -> {
            double score = playerResult.getJiesuanScore().getValue();
            // 计算累计总分
            if (latestFinishedPanResult != null) {
                playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + score);
            } else {
                if (optionalPlay.isJinyuanzi()) {
                    playerResult.setTotalScore(optionalPlay.getYuanzifen() + score);
                } else {
                    playerResult.setTotalScore(score);
                }
            }
        });

        if (optionalPlay.isJinyuanzi()) {    //进园子总分不会低于0分 如果有多个赢家 分数依次给赢分多的玩家
            Map<Double, BijiPanPlayerResult> map = new TreeMap<>(Double::compareTo);
            double chaScore = 0;
            Map<String, Double> sameScoreMap = new HashMap<>();
            Random random = new Random();
            for (BijiPanPlayerResult playerResult : panPlayerResultList) {
                if (playerResult.getJiesuanScore().getValue() <= 0) {
                    if (playerResult.getTotalScore() <= 0) {
                        chaScore += playerResult.getTotalScore();
                        playerResult.setTotalScore(0d);
                    }
                } else {
                    if (map.containsKey(playerResult.getJiesuanScore().getValue())) {
                        sameScoreMap.put(playerResult.getPlayerId(), playerResult.getJiesuanScore().getValue());
                        int randomInt = random.nextInt(5);
                        playerResult.getJiesuanScore().setValue(playerResult.getJiesuanScore().getValue() + randomInt);
                    }
                    map.put(playerResult.getJiesuanScore().getValue(), playerResult);
                }
            }

            for (Map.Entry<Double, BijiPanPlayerResult> entry : map.entrySet()) {
                if (chaScore < 0) {
                    double totalScore = entry.getValue().getTotalScore();
                    Double keyScore = entry.getKey();
                    if (sameScoreMap.containsKey(entry.getValue().getPlayerId())) {
                        keyScore = sameScoreMap.get(entry.getValue().getPlayerId());
                    }

                    if (Math.abs(chaScore) > keyScore) {
                        entry.getValue().setTotalScore(totalScore - keyScore);
                    } else {
                        entry.getValue().setTotalScore(totalScore + chaScore);
                    }
                    chaScore += keyScore;
                } else {
                    break;
                }
            }

        }

        for (BijiPanPlayerResult playerResult : panPlayerResultList) {
            ShisanshuiPlayer player = playerIdPlayerMap.get(playerResult.getPlayerId());
            player.setPlayerTotalScore(playerResult.getTotalScore());
        }

        BijiPanResult panResult = new BijiPanResult();
        panResult.setPan(new PanValueObject(currentPan));
        panResult.setPanFinishTime(panFinishTime);
        panResult.setPanPlayerResultList(panPlayerResultList);
        return panResult;

    }

    public DaoComparator getDaoComparator() {
        return daoComparator;
    }

    public void setDaoComparator(DaoComparator daoComparator) {
        this.daoComparator = daoComparator;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
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
