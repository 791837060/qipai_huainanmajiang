package com.anbang.qipai.tuidaohu.cqrs.c.domain;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.listener.TuiDaoHuPengGangActionStatisticsListener;
import com.dml.majiang.ju.Ju;
import com.dml.majiang.pan.Pan;
import com.dml.majiang.pan.frame.PanValueObject;
import com.dml.majiang.pan.result.CurrentPanResultBuilder;
import com.dml.majiang.pan.result.PanResult;
import com.dml.majiang.player.MajiangPlayer;

import java.math.BigDecimal;
import java.util.*;

public class TuiDaoHuPanResultBuilder implements CurrentPanResultBuilder {
    private OptionalPlay optionalPlay;           //清一色
    private Double difen;                   //底分

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
        TuiDaoHuPanResult latestFinishedPanResult = (TuiDaoHuPanResult) ju.findLatestFinishedPanResult();//获取上局结果
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        if (latestFinishedPanResult != null) {
            for (TuiDaoHuPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());//当前盘总分
            }
        }

        TuiDaoHuPengGangActionStatisticsListener fangGangCounter = ju.getActionStatisticsListenerManager().findListener(TuiDaoHuPengGangActionStatisticsListener.class); //碰杠统计检测器
        Map<String, Integer> playerFangGangMap = fangGangCounter.getPlayerIdFangGangShuMap();

        List<MajiangPlayer> huPlayers = currentPan.findAllHuPlayers();      //胡牌玩家集合
        TuiDaoHuPanResult tuidaohuPanResult = new TuiDaoHuPanResult();      //局结果
        tuidaohuPanResult.setPan(new PanValueObject(currentPan));
        List<String> playerIdList = currentPan.sortedPlayerIdList();        //玩家ID集合
        List<TuiDaoHuPanPlayerResult> playerResultList = new ArrayList<>(); //玩家结果
        TuidaohuNiao niao = new TuidaohuNiao(ju.getCurrentPan().getAvaliablePaiList(), optionalPlay.getNiaoshu());
        niao.calculate();
        String dianPaoPlayerId = "";                                        //放炮玩家id

        if (huPlayers.size() > 1) { //一炮多响
            Set<String> huPlayerSet = new HashSet<>();
            int delta = 0; //放炮玩家输给胡家们的胡分
            for (MajiangPlayer huPlayer : huPlayers) { //计算胡家胡分
                huPlayerSet.add(huPlayer.getId());//胡家集合
                TuiDaoHuPanPlayerResult huPlayerResult = new TuiDaoHuPanPlayerResult();
                TuiDaoHuHu hu = (TuiDaoHuHu) huPlayer.getHu();
                TuiDaoHuHushu huPlayerHufen = hu.getHufen();//获取胡分
                dianPaoPlayerId = hu.getDianpaoPlayerId();//获取点炮玩家ID
                huPlayerResult.setPlayerId(huPlayer.getId());
                huPlayerResult.setHufen(huPlayerHufen);
                delta += huPlayerHufen.getValue();
                //计算杠分
                Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                TuiDaoHuGang gang = new TuiDaoHuGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                TuidaohuNiao niaofen = new TuidaohuNiao();
                niaofen.jiesuan(-niao.getValue());
                huPlayerResult.setNiao(niaofen);
                playerResultList.add(huPlayerResult);
            }
            for (String playerId : playerIdList) { //计算非胡玩家胡分
                if (!huPlayerSet.contains(playerId)) {
                    if (dianPaoPlayerId.equals(playerId)) { //计算点炮玩家分数
                        MajiangPlayer dianpaoPlayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(dianpaoPlayer, optionalPlay));//设置胡分
                        TuiDaoHuHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta);
                        // 计算杠分
                        Integer fangGangCount = playerFangGangMap.get(playerId);
                        if (fangGangCount == null) {
                            fangGangCount = 0;
                        }
                        TuiDaoHuGang gang = new TuiDaoHuGang(dianpaoPlayer);
                        gang.calculate(playerIdList.size(), fangGangCount);
                        buHuPlayerResult.setGang(gang);
                        playerResultList.add(buHuPlayerResult);
                    } else { //计算其他玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        // 计算杠分
                        Integer fangGangCount = playerFangGangMap.get(playerId);
                        if (fangGangCount == null) {
                            fangGangCount = 0;
                        }
                        TuiDaoHuGang gang = new TuiDaoHuGang(buHuplayer);
                        gang.calculate(playerIdList.size(), fangGangCount);
                        buHuPlayerResult.setGang(gang);
                        niao.jiesuan(niao.getValue() * huPlayers.size());
                        buHuPlayerResult.setNiao(niao);
                        playerResultList.add(buHuPlayerResult);
                    }
                }
            }
            for (int i = 0; i < playerResultList.size(); i++) { // 两两结算暗杠（明杠只扣被杠人3分）
                TuiDaoHuPanPlayerResult playerResult1 = playerResultList.get(i);
                TuiDaoHuGang gang1 = playerResult1.getGang();
                for (int j = (i + 1); j < playerResultList.size(); j++) {
                    TuiDaoHuPanPlayerResult playerResult2 = playerResultList.get(j);
                    TuiDaoHuGang gang2 = playerResult2.getGang();
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
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue() + playerResult.getNiao().getTotalScore();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            tuidaohuPanResult.setPan(new PanValueObject(currentPan));
            tuidaohuPanResult.setPanFinishTime(panFinishTime);
            tuidaohuPanResult.setPanPlayerResultList(playerResultList);
            tuidaohuPanResult.setHu(true);
            tuidaohuPanResult.setZimo(false);
            tuidaohuPanResult.setDianpaoPlayerId(dianPaoPlayerId);
            return tuidaohuPanResult;
        } else if (huPlayers.size() == 1) { //一人胡
            MajiangPlayer huPlayer = huPlayers.get(0);//胡牌玩家
            TuiDaoHuHu hu = (TuiDaoHuHu) huPlayer.getHu();//胡牌
            TuiDaoHuHushu huPlayerHufen = hu.getHufen();//牌型胡分
            if (hu.isDianpao()) { //点炮胡
                // 结算胡数
                TuiDaoHuPanPlayerResult huPlayerResult = new TuiDaoHuPanPlayerResult();
                huPlayerResult.setPlayerId(huPlayer.getId());
                huPlayerResult.setHufen(huPlayerHufen);
                // 放炮玩家输给胡家的胡数
                int delta = huPlayerHufen.getValue();
                // 计算杠分
                Integer fangGangCount = playerFangGangMap.get(huPlayer.getId());
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                TuiDaoHuGang gang = new TuiDaoHuGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                niao.jiesuan(niao.getValue());
                huPlayerResult.setNiao(niao);
                playerResultList.add(huPlayerResult);
                playerIdList.forEach((playerId)->{
                    if (playerId.equals(huPlayer.getId())) {
                        // 胡家已经计算过了
                    } else if (playerId.equals(hu.getDianpaoPlayerId())) { //计算点炮玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        TuiDaoHuHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta);
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(playerId);
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        TuiDaoHuGang gang1 = new TuiDaoHuGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        TuidaohuNiao niaofen = new TuidaohuNiao();
                        niaofen.jiesuan(-niao.getValue());
                        buHuPlayerResult.setNiao(niaofen);
                        playerResultList.add(buHuPlayerResult);
                    } else { //计算非胡玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        //计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(buHuplayer.getId());
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        TuiDaoHuGang gang1 = new TuiDaoHuGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        buHuPlayerResult.setNiao(new TuidaohuNiao());
                        playerResultList.add(buHuPlayerResult);
                    }
                });
            }
            if (hu.isQianggang()) { //抢杠胡
                // 结算胡数
                TuiDaoHuPanPlayerResult huPlayerResult = new TuiDaoHuPanPlayerResult();
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
                TuiDaoHuGang gang = new TuiDaoHuGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                niao.jiesuan(niao.getValue()*(playerIdList.size() - 1));
                huPlayerResult.setNiao(niao);
                playerResultList.add(huPlayerResult);
                playerIdList.forEach((playerId)->{
                    if (playerId.equals(huPlayer.getId())) {
                        // 胡家已经计算过了
                    } else if (playerId.equals(hu.getDianpaoPlayerId())) { //计算点炮玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        TuiDaoHuHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta*(playerIdList.size() - 1));
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(playerId);
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        TuiDaoHuGang gang1 = new TuiDaoHuGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        TuidaohuNiao niaofen = new TuidaohuNiao();
                        niaofen.jiesuan(-niao.getValue() * (playerIdList.size() - 1));
                        buHuPlayerResult.setNiao(niaofen);
                        playerResultList.add(buHuPlayerResult);
                    } else { //计算非胡玩家分数
                        MajiangPlayer buHuplayer = currentPan.findPlayerById(playerId);
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        buHuPlayerResult.setPlayerId(playerId);
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuplayer, optionalPlay));
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(buHuplayer.getId());
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        TuiDaoHuGang gang1 = new TuiDaoHuGang(buHuplayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        buHuPlayerResult.setNiao(new TuidaohuNiao());
                        playerResultList.add(buHuPlayerResult);
                    }
                });
            }
            if (hu.isZimo()) { //自摸胡
                TuiDaoHuPanPlayerResult huPlayerResult = new TuiDaoHuPanPlayerResult(); //结算胡数
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
                TuiDaoHuGang gang = new TuiDaoHuGang(huPlayer);
                gang.calculate(playerIdList.size(), fangGangCount);
                huPlayerResult.setGang(gang);
                niao.jiesuan(niao.getValue() * (playerIdList.size() - 1));
                huPlayerResult.setNiao(niao);
                playerResultList.add(huPlayerResult);

                for (String playerId : playerIdList) {
                    if (playerId.equals(huPlayer.getId())) {
                        // 胡家已经计算过了
                    } else {
                        TuiDaoHuPanPlayerResult buHuPlayerResult = new TuiDaoHuPanPlayerResult();
                        MajiangPlayer buHuPlayer = currentPan.findPlayerById(playerId);
                        buHuPlayerResult.setPlayerId(playerId);
                        // 计算非胡玩家分数
                        buHuPlayerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(buHuPlayer, optionalPlay));
                        TuiDaoHuHushu hufen = buHuPlayerResult.getHufen();
                        hufen.jiesuan(-delta);
                        // 计算杠分
                        Integer fangGangCount1 = playerFangGangMap.get(buHuPlayer.getId());
                        if (fangGangCount1 == null) {
                            fangGangCount1 = 0;
                        }
                        TuiDaoHuGang gang1 = new TuiDaoHuGang(buHuPlayer);
                        gang1.calculate(playerIdList.size(), fangGangCount1);
                        buHuPlayerResult.setGang(gang1);
                        TuidaohuNiao niaofen = new TuidaohuNiao();
                        niaofen.jiesuan(-niao.getValue());
                        buHuPlayerResult.setNiao(niaofen);
                        playerResultList.add(buHuPlayerResult);
                    }
                }
            }
            for (int i = 0; i < playerResultList.size(); i++) { //两两结算暗杠（明杠只扣被杠人3分）
                TuiDaoHuPanPlayerResult playerResult1 = playerResultList.get(i);
                TuiDaoHuGang gang1 = playerResult1.getGang();
                for (int j = (i + 1); j < playerResultList.size(); j++) {
                    TuiDaoHuPanPlayerResult playerResult2 = playerResultList.get(j);
                    TuiDaoHuGang gang2 = playerResult2.getGang();
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
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue() + playerResult.getNiao().getTotalScore();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            tuidaohuPanResult.setPan(new PanValueObject(currentPan));
            tuidaohuPanResult.setPanFinishTime(panFinishTime);
            tuidaohuPanResult.setPanPlayerResultList(playerResultList);
            tuidaohuPanResult.setHu(true);
            tuidaohuPanResult.setZimo(hu.isZimo());
            tuidaohuPanResult.setDianpaoPlayerId(hu.getDianpaoPlayerId());
            return tuidaohuPanResult;
        } else { //流局
            // 结算胡数
            playerIdList.forEach((playerId) -> {
                MajiangPlayer player = currentPan.findPlayerById(playerId);
                TuiDaoHuPanPlayerResult playerResult = new TuiDaoHuPanPlayerResult();
                playerResult.setPlayerId(playerId);
                // 计算非胡玩家分数
                playerResult.setHufen(TuiDaoHuJiesuanCalculator.calculateBestHuFenForBuhuPlayer(player, optionalPlay));
                // 计算杠分
                Integer fangGangCount = playerFangGangMap.get(playerId);
                if (fangGangCount == null) {
                    fangGangCount = 0;
                }
                TuiDaoHuGang gang = new TuiDaoHuGang();
//                gang.calculate(playerIdList.size(), fangGangCount);
                playerResult.setGang(gang);
                playerResult.setNiao(new TuidaohuNiao());
                playerResultList.add(playerResult);
            });
            for (int i = 0; i < playerResultList.size(); i++) { //两两结算暗杠（明杠只扣被杠人3分）
                TuiDaoHuPanPlayerResult playerResult1 = playerResultList.get(i);
                TuiDaoHuGang gang1 = playerResult1.getGang();
//                for (int j = (i + 1); j < playerResultList.size(); j++) {
//                    TuiDaoHuPanPlayerResult playerResult2 = playerResultList.get(j);
//                    TuiDaoHuGang gang2 = playerResult2.getGang();
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
                int score = playerResult.getHufen().getValue() + playerResult.getGang().getValue() + playerResult.getNiao().getTotalScore();
                playerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(score))).doubleValue());
                // 计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    playerResult.setTotalScore(playerResult.getScore());
                }
            });
            tuidaohuPanResult.setPan(new PanValueObject(currentPan));
            tuidaohuPanResult.setPanFinishTime(panFinishTime);
            tuidaohuPanResult.setPanPlayerResultList(playerResultList);
            tuidaohuPanResult.setHu(false);
            return tuidaohuPanResult;
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
