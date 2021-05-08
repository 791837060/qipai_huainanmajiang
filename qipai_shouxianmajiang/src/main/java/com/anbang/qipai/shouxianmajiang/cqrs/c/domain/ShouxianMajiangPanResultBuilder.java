package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.listener.ShouxianMajiangPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanValueObject;
import com.dml.majiang.pan.result.CurrentPanResultBuilder;
import com.dml.majiang.pan.result.PanResult;
import com.dml.majiang.player.MajiangPlayer;
import com.dml.majiang.player.menfeng.PlayersMenFengDeterminer;

import java.math.BigDecimal;
import java.util.*;

public class ShouxianMajiangPanResultBuilder implements CurrentPanResultBuilder {
    private OptionalPlay optionalPlay;           //清一色
    private Double difen;                   //底分
    private Map<String,Integer> playerpiaofenMap=new HashMap<>();


    /**
     * 构建局得分结果
     *
     * @param ju            当前局
     * @param panFinishTime 结束时间
     * @return
     */
    @Override
    public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
        Pan currentPan = ju.getCurrentPan();
        ShouxianMajiangPanResult latestFinishedPanResult = (ShouxianMajiangPanResult) ju.findLatestFinishedPanResult();//获取上局结果
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        if (latestFinishedPanResult != null) {
            for (ShouxianMajiangPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());//当前盘总分
            }
        }

        ShouxianMajiangPengGangActionStatisticsListener fangGangCounter = ju.getActionStatisticsListenerManager().findListener(ShouxianMajiangPengGangActionStatisticsListener.class); //碰杠统计检测器
        Map<String, Integer> playerFangGangMap = fangGangCounter.getPlayerIdFangGangShuMap();
        ShouxianMajiangPlayersMenFengDeterminer playersMenFengDeterminerForFirstPan = (ShouxianMajiangPlayersMenFengDeterminer)ju.getPlayersMenFengDeterminerForFirstPan();

        List<MajiangPlayer> huPlayers = currentPan.findAllHuPlayers();      //胡牌玩家集合
        ShouxianMajiangPanResult shouxianmajiangPanResult = new ShouxianMajiangPanResult();      //局结果
        shouxianmajiangPanResult.setPan(new PanValueObject(currentPan));
        List<String> playerIdList = currentPan.sortedPlayerIdList();        //玩家ID集合
        List<ShouxianMajiangPanPlayerResult> playerResultList = new ArrayList<>(); //玩家结果

        String dianPaoPlayerId = "";                                        //放炮玩家id

        if (huPlayers.size() > 1) { //一炮多响
            MajiangPlayer huPlayer = huPlayers.get(0);
            ShouxianMajiangHu hu = (ShouxianMajiangHu) huPlayer.getHu();
            dianPaoPlayerId = hu.getDianpaoPlayerId();
            MajiangPlayer dianpaoPlayer = currentPan.findPlayerById(dianPaoPlayerId);
            dianpaoPlayer.setHu(null);// 财神吊时有其他人胡，其他人优先胡
            MajiangPlayer xiajiaPlayer = currentPan.findXiajia(dianpaoPlayer);
            // 按点炮者开始遍历出最先胡并把其他胡变为null
            boolean anyPlayerHu = false;
            while (true) {
                if (!xiajiaPlayer.getId().equals(dianPaoPlayerId)) {
                    ShouxianMajiangHu hu1 = (ShouxianMajiangHu) xiajiaPlayer.getHu();
                    if (!anyPlayerHu && hu1 != null) {
                        huPlayer = xiajiaPlayer;
                        hu = hu1;
                        anyPlayerHu = true;
                    } else {
                        xiajiaPlayer.setHu(null);
                    }
                } else {
                    break;
                }
                xiajiaPlayer = currentPan.findXiajia(xiajiaPlayer);
            }
            //抢杠胡
            // 结算胡数
            ShouxianMajiangHushu huPlayerHufen = hu.getHufen();
            ShouxianMajiangPanPlayerResult huPlayerResult = new ShouxianMajiangPanPlayerResult();
            huPlayerResult.setPlayerId(huPlayer.getId());
            huPlayerResult.setHufen(huPlayerHufen);
            // 放炮玩家输给胡家的胡数
            int delta = huPlayerHufen.getValue();
            huPlayerHufen.setValue(huPlayerHufen.getValue() * (playerIdList.size() - 1)); //每人共赢分
            // 计算杠分
            Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
            if (fangGangCount == null) {
                fangGangCount = 0;
            }
            ShouxianMajiangGang gang = new ShouxianMajiangGang(huPlayer);
            gang.calculate(playerIdList.size(), fangGangCount);
            huPlayerResult.setGang(gang);
            huPlayerResult.setXiapaoCount(playerpiaofenMap.get(huPlayer.getId()));
            huPlayerResult.setPaofen(playerpiaofenMap.get(huPlayer.getId())+playerpiaofenMap.get(dianPaoPlayerId));
            playerResultList.add(huPlayerResult);
            MajiangPlayer finalHuPlayer = huPlayer;
            ShouxianMajiangHu finalHu = hu;
            playerIdList.forEach((playerId)->{
                if (playerId.equals(finalHuPlayer.getId())) {
                    // 胡家已经计算过了
                } else if (playerId.equals(finalHu.getDianpaoPlayerId())) { //计算点炮玩家分数
                    MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                    ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
                    buHuPlayerResult.setPlayerId(playerId);
                    buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                    ShouxianMajiangHushu hufen = buHuPlayerResult.getHufen();
                    hufen.jiesuan(-delta);
                    // 计算杠分
                    Integer fangGangCount1 = playerFangGangMap.get(playerId);
                    if (fangGangCount1 == null) {
                        fangGangCount1 = 0;
                    }
                    ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
                    gang1.calculate(playerIdList.size(), fangGangCount1);
                    buHuPlayerResult.setGang(gang1);
                    playerResultList.add(buHuPlayerResult);
                } else { //计算非胡玩家分数
                    MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                    ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
                    buHuPlayerResult.setPlayerId(playerId);
                    buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                    // 计算杠分
                    Integer fangGangCount1 = playerFangGangMap.get(buHuplayer.getId());
                    if (fangGangCount1 == null) {
                        fangGangCount1 = 0;
                    }
                    ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
                    gang1.calculate(playerIdList.size(), fangGangCount1);
                    buHuPlayerResult.setGang(gang1);
                    playerResultList.add(buHuPlayerResult);
                }
            });
            for (int i = 0; i < playerResultList.size(); i++) { // 两两结算暗杠（明杠只扣被杠人3分）
                ShouxianMajiangPanPlayerResult playerResult1 = playerResultList.get(i);
                ShouxianMajiangGang gang1 = playerResult1.getGang();
                for (int j = (i + 1); j < playerResultList.size(); j++) {
                    ShouxianMajiangPanPlayerResult playerResult2 = playerResultList.get(j);
                    ShouxianMajiangGang gang2 = playerResult2.getGang();
                    // 结算杠分
                    int zimogang1 = gang1.getZimoMingGangShu();
                    int zimogang2 = gang2.getZimoMingGangShu();
                    int angang1 = gang1.getAnGangShu();
                    int angang2 = gang2.getAnGangShu();
                    gang1.jiesuan(-zimogang2 - angang2 * 2);
                    gang2.jiesuan(-zimogang1 - angang1 * 2);
                }
            }

            playerResultList.forEach((playerResult) -> {
                // 计算当盘总分
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue()+playerResult.getPaofen();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            shouxianmajiangPanResult.setPan(new PanValueObject(currentPan));
            shouxianmajiangPanResult.setPanFinishTime(panFinishTime);
            shouxianmajiangPanResult.setPanPlayerResultList(playerResultList);
            shouxianmajiangPanResult.setHu(true);
            shouxianmajiangPanResult.setZimo(false);
            shouxianmajiangPanResult.setDianpaoPlayerId(dianPaoPlayerId);
            return shouxianmajiangPanResult;
        } else if (huPlayers.size() == 1) { //一人胡
            MajiangPlayer huPlayer = huPlayers.get(0);//胡牌玩家
            ShouxianMajiangHu hu = (ShouxianMajiangHu) huPlayer.getHu();//胡牌
            ShouxianMajiangHushu huPlayerHufen = hu.getHufen();//牌型胡分
            if (hu.isDianpao()) {
                //点炮胡
                // 结算胡数
                ShouxianMajiangPanPlayerResult huPlayerResult = new ShouxianMajiangPanPlayerResult();
                huPlayerResult.setPlayerId(huPlayer.getId());
                huPlayerResult.setHufen(huPlayerHufen);
                // 放炮玩家输给胡家的胡数
                int delta = huPlayerHufen.getValue();
                // 计算杠分
                Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                ShouxianMajiangGang gang = new ShouxianMajiangGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                playerResultList.add(huPlayerResult);
                playerIdList.forEach((playerId)->{
                    if (playerId.equals(huPlayer.getId())) {
                        // 胡家已经计算过了
                    } else if (playerId.equals(hu.getDianpaoPlayerId())) { //计算点炮玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        ShouxianMajiangHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta);
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(playerId);
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        playerResultList.add(buHuPlayerResult);
                    } else { //计算非胡玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        //计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(buHuplayer.getId());
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        playerResultList.add(buHuPlayerResult);
                    }
                });
            }
//            if (hu.isQianggang()) {
//                //抢杠胡
//                // 结算胡数
//                ShouxianMajiangPanPlayerResult huPlayerResult = new ShouxianMajiangPanPlayerResult();
//                huPlayerResult.setPlayerId(huPlayer.getId());
//                huPlayerResult.setHufen(huPlayerHufen);
//                // 放炮玩家输给胡家的胡数
//                int delta = huPlayerHufen.getValue();
//                huPlayerHufen.setValue(huPlayerHufen.getValue() * (playerIdList.size() - 1)); //每人共赢分
//                // 计算杠分
//                Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
//                if (fangGangCount == null) {
//                    fangGangCount = 0;
//                }
//                ShouxianMajiangGang gang = new ShouxianMajiangGang(huPlayer);
//                gang.calculate(playerIdList.size(), fangGangCount);
//                huPlayerResult.setGang(gang);
//                playerResultList.add(huPlayerResult);
//                playerIdList.forEach((playerId)->{
//                    if (playerId.equals(huPlayer.getId())) {
//                        // 胡家已经计算过了
//                    } else if (playerId.equals(hu.getDianpaoPlayerId())) { //计算点炮玩家分数
//                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
//                        ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
//                        buHuPlayerResult.setPlayerId(playerId);
//                        buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
//                        ShouxianMajiangHushu hufen = buHuPlayerResult.getHufen();
//                        hufen.jiesuan(-delta*(playerIdList.size() - 1));
//                        // 计算杠分
//                        Integer fangGangCount1 = playerFangGangMap.get(playerId);
//                        if (fangGangCount1 == null) {
//                            fangGangCount1 = 0;
//                        }
//                        ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
//                        gang1.calculate(playerIdList.size(), fangGangCount1);
//                        buHuPlayerResult.setGang(gang1);
//                        playerResultList.add(buHuPlayerResult);
//                    } else { //计算非胡玩家分数
//                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
//                        ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
//                        buHuPlayerResult.setPlayerId(playerId);
//                        buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
//                        // 计算杠分
//                        Integer fangGangCount1 = playerFangGangMap.get(buHuplayer.getId());
//                        if (fangGangCount1 == null) {
//                            fangGangCount1 = 0;
//                        }
//                        ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuplayer);
//                        gang1.calculate(playerIdList.size(), fangGangCount1);
//                        buHuPlayerResult.setGang(gang1);
//                        playerResultList.add(buHuPlayerResult);
//                    }
//                });
//            }
            if (hu.isZimo()) { //自摸胡
                ShouxianMajiangPanPlayerResult huPlayerResult = new ShouxianMajiangPanPlayerResult(); //结算胡数
                huPlayerResult.setPlayerId(huPlayer.getId());

                //其他人输给胡家的胡数
                int delta = huPlayerHufen.getValue();
                huPlayerHufen.setValue(huPlayerHufen.getValue() * (playerIdList.size() - 1)); //每人共赢分
                huPlayerResult.setHufen(huPlayerHufen);

                // 计算杠分
                Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                ShouxianMajiangGang gang = new ShouxianMajiangGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                playerResultList.add(huPlayerResult);

                for (String playerId : playerIdList) {
                    if (playerId.equals(huPlayer.getId())) {
                        // 胡家已经计算过了
                    } else {
                        ShouxianMajiangPanPlayerResult buHuPlayerResult = new ShouxianMajiangPanPlayerResult();
                        MajiangPlayer buHuPlayer = currentPan.findPlayerById(playerId);
                        buHuPlayerResult.setPlayerId(playerId);
                        // 计算非胡玩家分数
                        buHuPlayerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuPlayer, optionalPlay));
                        ShouxianMajiangHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta);
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(buHuPlayer.getId());
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        ShouxianMajiangGang gang1 = new ShouxianMajiangGang(buHuPlayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        playerResultList.add(buHuPlayerResult);
                    }
                }
            }
            for (int i = 0; i < playerResultList.size(); i++) { //两两结算暗杠（明杠只扣被杠人3分）
                ShouxianMajiangPanPlayerResult playerResult1 = playerResultList.get(i);
                ShouxianMajiangGang gang1 = playerResult1.getGang();
                for (int j = (i + 1); j < playerResultList.size(); j++) {
                    ShouxianMajiangPanPlayerResult playerResult2 = playerResultList.get(j);
                    ShouxianMajiangGang gang2 = playerResult2.getGang();
                    // 结算杠分
                    int zimogang1 = gang1.getZimoMingGangShu();
                    int zimogang2 = gang2.getZimoMingGangShu();
                    int angang1 = gang1.getAnGangShu();
                    int angang2 = gang2.getAnGangShu();
                    gang1.jiesuan(-zimogang2 - angang2 * 2);
                    gang2.jiesuan(-zimogang1 - angang1 * 2);
                }
            }

            playerResultList.forEach((playerResult) -> {
                // 计算当盘总分
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue() ;
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            shouxianmajiangPanResult.setPan(new PanValueObject(currentPan));
            shouxianmajiangPanResult.setPanFinishTime(panFinishTime);
            shouxianmajiangPanResult.setPanPlayerResultList(playerResultList);
            shouxianmajiangPanResult.setHu(true);
            shouxianmajiangPanResult.setZimo(hu.isZimo());
            shouxianmajiangPanResult.setDianpaoPlayerId(hu.getDianpaoPlayerId());
            return shouxianmajiangPanResult;
        } else { //流局
            // 结算胡数
            playerIdList.forEach((playerId) -> {
                MajiangPlayer player = currentPan.findPlayerById(playerId);
                ShouxianMajiangPanPlayerResult playerResult = new ShouxianMajiangPanPlayerResult();
                playerResult.setPlayerId(playerId);
                // 计算非胡玩家分数
                playerResult.setHufen(ShouxianMajiangJiesuanCalculator.calculateBestHuFenForBuhuPlayer(player, optionalPlay));
                // 计算杠分
                Integer fangGangCount = playerFangGangMap.get(playerId);
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                ShouxianMajiangGang gang = new ShouxianMajiangGang();
//                gang.calculate(playerIdList.size(), fangGangCount);
                playerResult.setGang(gang);
                playerResultList.add(playerResult);
            });
            for (int i = 0; i < playerResultList.size(); i++) { //两两结算暗杠（明杠只扣被杠人3分）
                ShouxianMajiangPanPlayerResult playerResult1 = playerResultList.get(i);
                ShouxianMajiangGang gang1 = playerResult1.getGang();
//                for (int j = (i + 1); j < playerResultList.size(); j++) {
//                    ShouxianMajiangPanPlayerResult playerResult2 = playerResultList.get(j);
//                    ShouxianMajiangGang gang2 = playerResult2.getGang();
//                    // 结算杠分
//                    int zimogang1 = gang1.getZimoMingGangShu();
//                    int zimogang2 = gang2.getZimoMingGangShu();
//                    int angang1 = gang1.getAnGangShu();
//                    int angang2 = gang2.getAnGangShu();
//                    gang1.jiesuan(-zimogang2 - angang2 * 2);
//                    gang2.jiesuan(-zimogang1 - angang1 * 2);
//                }
            }
            playerResultList.forEach((playerResult) -> {
                // 计算当盘总分
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue() ;
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            shouxianmajiangPanResult.setPan(new PanValueObject(currentPan));
            shouxianmajiangPanResult.setPanFinishTime(panFinishTime);
            shouxianmajiangPanResult.setPanPlayerResultList(playerResultList);
            shouxianmajiangPanResult.setHu(false);
            return shouxianmajiangPanResult;
        }
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
