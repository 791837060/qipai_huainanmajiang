package com.anbang.qipai.guandan.cqrs.c.domain;

import java.math.BigDecimal;
import java.util.*;

import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanPlayerResult;
import com.anbang.qipai.guandan.cqrs.c.domain.result.GuandanPanResult;
import com.dml.shuangkou.ju.Ju;
import com.dml.shuangkou.pan.CurrentPanResultBuilder;
import com.dml.shuangkou.pan.Pan;
import com.dml.shuangkou.pan.PanResult;
import com.dml.shuangkou.pan.PanValueObject;
import com.dml.shuangkou.player.ShuangkouPlayer;

public class GuandanCurrentPanResultBuilder implements CurrentPanResultBuilder {
    private OptionalPlay optionalPlay;  //玩法
    private int renshu;
    //    private BianXingWanFa bx;
    //    private boolean bxfd;//八线封顶
    //    private boolean jxfd;//九线封顶
    //    private boolean sxfd;//十线封顶
    //    private boolean gxjb;//贡献分减半
    private Map<String, GuandanGongxianFen> playerTotalGongxianfenMap = new HashMap<>();
    private List<String> chaodiPlayerIdList = new ArrayList<>();
    private double difen;

    @Override
    public PanResult buildCurrentPanResult(Ju ju, long panFinishTime) {
        Pan currentPan = ju.getCurrentPan();
        GuandanPanResult latestFinishedPanResult = (GuandanPanResult) ju.findLatestFinishedPanResult();
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        if (latestFinishedPanResult != null) {
            for (GuandanPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
            }
        } else {
            for (String playerId : currentPan.getShuangkouPlayerIdPlayerMap().keySet()) {
                if (optionalPlay.isJinyuanzi()) {
                    playerTotalScoreMap.put(playerId, (double) optionalPlay.getYuanzifen());//如果是进园子 默认初始分是园子分
                } else {
                    playerTotalScoreMap.put(playerId, 0d);//如果不是进园子 默认初始分是0
                }
            }
        }
        List<String> playerIds = currentPan.findAllPlayerId();

//        XianshuCountDaActionStatisticsListener guandanListener = ju.getActionStatisticsListenerManager().findDaListener(XianshuCountDaActionStatisticsListener.class);
//        Map<String, int[]> playerXianshuMap = guandanListener.getPlayerXianshuMap();

        List<String> noPaiPlayerIdList = currentPan.getNoPaiPlayerIdList();
        if (renshu > 2) {    //4人游戏
            List<GuandanPanPlayerResult> panPlayerResultList = new ArrayList<>();
            String yingPlayerId = noPaiPlayerIdList.get(0);                             //获胜玩家ID
            ShuangkouPlayer duijiaPlayer = currentPan.findDuijiaPlayer(yingPlayerId);   //获胜玩家对家
            String playerId1 = noPaiPlayerIdList.get(1);
            String playerId2 = "";
            if (noPaiPlayerIdList.size() > 2) {
                playerId2 = noPaiPlayerIdList.get(2);
            } else {
                for (String pid : playerIds) {
                    if (!pid.equals(yingPlayerId) && !pid.equals(playerId1)) {
                        playerId2 = pid;
                    }
                }
            }

            String playerId3 = "";
            for (String pid : playerIds) {
                if (!pid.equals(yingPlayerId) && !pid.equals(playerId1) && !pid.equals(playerId2)) {
                    playerId3 = pid;
                }
            }
//			GuandanXianshuBeishu beishu = new GuandanXianshuBeishu();
            //赢家
            GuandanPanPlayerResult yingPlayerResult = new GuandanPanPlayerResult();
            yingPlayerResult.setPlayerId(yingPlayerId);

//            int[] xianshuCount = playerXianshuMap.get(yingPlayerId);
//            if (xianshuCount == null) {
//                xianshuCount = new int[9];
//            }
//            GuandanGongxianFen gongxianfen = calculateTotalGongxianfenForPlayer(yingPlayerId, xianshuCount, ju);
//            gongxianfen.calculate(renshu);
//            yingPlayerResult.setGongxianfen(gongxianfen);
//			int[] xianshuCountArray = new int[9];
//			xianshuCountArray[0] = gongxianfen.getSixian();
//			xianshuCountArray[1] = gongxianfen.getWuxian();
//			xianshuCountArray[2] = gongxianfen.getLiuxian();
//			xianshuCountArray[3] = gongxianfen.getQixian();
//			xianshuCountArray[4] = gongxianfen.getBaxian();
//			xianshuCountArray[5] = gongxianfen.getJiuxian();
//			xianshuCountArray[6] = gongxianfen.getShixian();
//			xianshuCountArray[7] = gongxianfen.getShiyixian();
//			xianshuCountArray[8] = gongxianfen.getShierxian();
//			GuandanXianshuBeishu yingBeishu = new GuandanXianshuBeishu(xianshuCountArray);
//			yingBeishu.calculate(bxfd, jxfd, sxfd);
//			beishu = yingBeishu;
//			GuandanChaixianbufen bufen = new GuandanChaixianbufen();
//			bufen.setTotalScore(playerTotalGongxianfenMap.get(yingPlayerId).getValue());
//			bufen.setScore(gongxianfen.getValue());
//			bufen.calculate();
//			yingPlayerResult.setBufen(bufen);

            GuandanMingcifen mingcifen = new GuandanMingcifen();
            mingcifen.setYing(true);
            mingcifen.setMingci(1);
            if (playerId1.equals(duijiaPlayer.getId())) {
                mingcifen.setShuangkou(true);
            } else if (playerId2.equals(duijiaPlayer.getId())) {
                mingcifen.setDankou(true);
            } else {
                mingcifen.setPingkou(true);
            }
            mingcifen.calculate();
            yingPlayerResult.setMingcifen(mingcifen);
            panPlayerResultList.add(yingPlayerResult);

            //玩家1
            GuandanPanPlayerResult playerResult1 = new GuandanPanPlayerResult();
            playerResult1.setPlayerId(playerId1);

//            int[] xianshuCount1 = playerXianshuMap.get(playerId1);
//            if (xianshuCount1 == null) {
//                xianshuCount1 = new int[9];
//            }
//			GuandanGongxianFen gongxianfen1 = calculateTotalGongxianfenForPlayer(playerId1, xianshuCount1, ju);
//			gongxianfen1.calculate(renshu);
//			playerResult1.setGongxianfen(gongxianfen1);
//			int[] xianshuCountArray1 = new int[9];
//			xianshuCountArray1[0] = gongxianfen1.getSixian();
//			xianshuCountArray1[1] = gongxianfen1.getWuxian();
//			xianshuCountArray1[2] = gongxianfen1.getLiuxian();
//			xianshuCountArray1[3] = gongxianfen1.getQixian();
//			xianshuCountArray1[4] = gongxianfen1.getBaxian();
//			xianshuCountArray1[5] = gongxianfen1.getJiuxian();
//			xianshuCountArray1[6] = gongxianfen1.getShixian();
//			xianshuCountArray1[7] = gongxianfen1.getShiyixian();
//			xianshuCountArray1[8] = gongxianfen1.getShierxian();
//			GuandanXianshuBeishu beishu1 = new GuandanXianshuBeishu(xianshuCountArray1);
//			beishu1.calculate(bxfd, jxfd, sxfd);
//			GuandanChaixianbufen bufen1 = new GuandanChaixianbufen();
//			bufen1.setTotalScore(playerTotalGongxianfenMap.get(playerId1).getValue());
//			bufen1.setScore(gongxianfen1.getValue());
//			bufen1.calculate();
//			playerResult1.setBufen(bufen1);

            GuandanMingcifen mingcifen1 = new GuandanMingcifen();
            mingcifen1.setMingci(2);
            if (playerId1.equals(duijiaPlayer.getId())) {
                mingcifen1.setYing(true);
                mingcifen1.setShuangkou(true);

//				if (beishu1.getValue() > beishu.getValue()) {
//					beishu = beishu1;
//				}

            } else if (playerId2.equals(duijiaPlayer.getId())) {
                mingcifen1.setYing(false);
                mingcifen1.setDankou(true);
            } else {
                mingcifen1.setYing(false);
                mingcifen1.setPingkou(true);
            }
            mingcifen1.calculate();
            playerResult1.setMingcifen(mingcifen1);
            panPlayerResultList.add(playerResult1);

            // 玩家2
            GuandanPanPlayerResult playerResult2 = new GuandanPanPlayerResult();
            playerResult2.setPlayerId(playerId2);

//            int[] xianshuCount2 = playerXianshuMap.get(playerId2);
//            if (xianshuCount2 == null) {
//                xianshuCount2 = new int[9];
//            }
//			GuandanGongxianFen gongxianfen2 = calculateTotalGongxianfenForPlayer(playerId2, xianshuCount2, ju);
//			gongxianfen2.calculate(renshu);
//			playerResult2.setGongxianfen(gongxianfen2);
//			int[] xianshuCountArray2 = new int[9];
//			xianshuCountArray2[0] = gongxianfen2.getSixian();
//			xianshuCountArray2[1] = gongxianfen2.getWuxian();
//			xianshuCountArray2[2] = gongxianfen2.getLiuxian();
//			xianshuCountArray2[3] = gongxianfen2.getQixian();
//			xianshuCountArray2[4] = gongxianfen2.getBaxian();
//			xianshuCountArray2[5] = gongxianfen2.getJiuxian();
//			xianshuCountArray2[6] = gongxianfen2.getShixian();
//			xianshuCountArray2[7] = gongxianfen2.getShiyixian();
//			xianshuCountArray2[8] = gongxianfen2.getShierxian();
//			GuandanXianshuBeishu beishu2 = new GuandanXianshuBeishu(xianshuCountArray2);
//			beishu2.calculate(bxfd, jxfd, sxfd);
//			GuandanChaixianbufen bufen2 = new GuandanChaixianbufen();
//			bufen2.setTotalScore(playerTotalGongxianfenMap.get(playerId2).getValue());
//			bufen2.setScore(gongxianfen2.getValue());
//			bufen2.calculate();
//			playerResult2.setBufen(bufen2);

            GuandanMingcifen mingcifen2 = new GuandanMingcifen();
            mingcifen2.setMingci(3);
            if (playerId1.equals(duijiaPlayer.getId())) {
                mingcifen2.setYing(false);
                mingcifen2.setShuangkou(true);
            } else if (playerId2.equals(duijiaPlayer.getId())) {
                mingcifen2.setYing(true);
                mingcifen2.setDankou(true);

//				if (beishu2.getValue() > beishu.getValue()) {
//					beishu = beishu2;
//				}

            } else {
                mingcifen2.setYing(false);
                mingcifen2.setPingkou(true);
            }
            mingcifen2.calculate();
            playerResult2.setMingcifen(mingcifen2);
            panPlayerResultList.add(playerResult2);

            // 玩家3
            GuandanPanPlayerResult playerResult3 = new GuandanPanPlayerResult();
            playerResult3.setPlayerId(playerId3);
//            int[] xianshuCount3 = playerXianshuMap.get(playerId3);
//            if (xianshuCount3 == null) {
//                xianshuCount3 = new int[9];
//            }
//			GuandanGongxianFen gongxianfen3 = calculateTotalGongxianfenForPlayer(playerId3, xianshuCount3, ju);
//			gongxianfen3.calculate(renshu);
//			playerResult3.setGongxianfen(gongxianfen3);
//			int[] xianshuCountArray3 = new int[9];
//			xianshuCountArray3[0] = gongxianfen3.getSixian();
//			xianshuCountArray3[1] = gongxianfen3.getWuxian();
//			xianshuCountArray3[2] = gongxianfen3.getLiuxian();
//			xianshuCountArray3[3] = gongxianfen3.getQixian();
//			xianshuCountArray3[4] = gongxianfen3.getBaxian();
//			xianshuCountArray3[5] = gongxianfen3.getJiuxian();
//			xianshuCountArray3[6] = gongxianfen3.getShixian();
//			xianshuCountArray3[7] = gongxianfen3.getShiyixian();
//			xianshuCountArray3[8] = gongxianfen3.getShierxian();
//			GuandanXianshuBeishu beishu3 = new GuandanXianshuBeishu(xianshuCountArray3);
//			beishu3.calculate(bxfd, jxfd, sxfd);
//			GuandanChaixianbufen bufen3 = new GuandanChaixianbufen();
//			bufen3.setTotalScore(playerTotalGongxianfenMap.get(playerId3).getValue());
//			bufen3.setScore(gongxianfen3.getValue());
//			bufen3.calculate();
//			playerResult3.setBufen(bufen3);
            GuandanMingcifen mingcifen3 = new GuandanMingcifen();
            mingcifen3.setMingci(4);
            if (playerId1.equals(duijiaPlayer.getId())) {
                mingcifen3.setYing(false);
                mingcifen3.setShuangkou(true);
            } else if (playerId2.equals(duijiaPlayer.getId())) {
                mingcifen3.setYing(false);
                mingcifen3.setDankou(true);
            } else {
                mingcifen3.setYing(true);
                mingcifen3.setPingkou(true);
//				if (beishu3.getValue() > beishu.getValue()) {
//					beishu = beishu3;
//				}
            }
            mingcifen3.calculate();
            playerResult3.setMingcifen(mingcifen3);
            panPlayerResultList.add(playerResult3);

//			// 结算线数倍数
//			yingPlayerResult.setXianshubeishu(beishu.getValue());
//			playerResult1.setXianshubeishu(beishu.getValue());
//			playerResult2.setXianshubeishu(beishu.getValue());
//			playerResult3.setXianshubeishu(beishu.getValue());
//
//			// 两两结算贡献分
//			for (int i = 0; i < panPlayerResultList.size(); i++) {
//				GuandanPanPlayerResult playerResulti = panPlayerResultList.get(i);
//				GuandanGongxianFen gongxiani = playerResulti.getGongxianfen();
//				for (int j = (i + 1); j < panPlayerResultList.size(); j++) {
//					GuandanPanPlayerResult playerResultj = panPlayerResultList.get(j);
//					GuandanGongxianFen gongxianj = playerResultj.getGongxianfen();
//					// 结算贡献分
//					int feni = gongxiani.getValue();
//					int fenj = gongxianj.getValue();
//					gongxiani.jiesuan(-fenj);
//					gongxianj.jiesuan(-feni);
//				}
//			}
//
//			// 计算补分
//			Set<String> yingjiaPlayerId = new HashSet<>();
//			for (int i = 0; i < panPlayerResultList.size(); i++) {
//				GuandanPanPlayerResult playerResulti = panPlayerResultList.get(i);
//				if (yingPlayerId.equals(playerResulti.getPlayerId())) {
//					GuandanChaixianbufen chaixianBufen1 = playerResulti.getBufen();
//					int bufeni = chaixianBufen1.getValue();
//					for (int j = 0; j < panPlayerResultList.size(); j++) {
//						GuandanPanPlayerResult playerResultj = panPlayerResultList.get(j);
//						if (duijiaPlayer.getId().equals(playerResultj.getPlayerId())) {
//							GuandanChaixianbufen chaixianBufen2 = playerResultj.getBufen();
//							int bufenj = chaixianBufen2.getValue();
//							// 结算补分
//							chaixianBufen1.jiesuan(-bufenj);
//							chaixianBufen2.jiesuan(-bufeni);
//							yingjiaPlayerId.add(yingPlayerId);
//							yingjiaPlayerId.add(duijiaPlayer.getId());
//							break;
//						}
//					}
//				}
//			}
//			for (int i = 0; i < panPlayerResultList.size(); i++) {
//				GuandanPanPlayerResult playerResulti = panPlayerResultList.get(i);
//				if (!yingjiaPlayerId.contains(playerResulti.getPlayerId())) {
//					GuandanChaixianbufen chaixianBufen1 = playerResulti.getBufen();
//					int bufeni = chaixianBufen1.getValue();
//					ShuangkouPlayer duijiaPlayer1 = currentPan.findDuijiaPlayer(playerResulti.getPlayerId());
//					for (int j = 0; j < panPlayerResultList.size(); j++) {
//						GuandanPanPlayerResult playerResultj = panPlayerResultList.get(j);
//						if (duijiaPlayer1.getId().equals(playerResultj.getPlayerId())) {
//							GuandanChaixianbufen chaixianBufen2 = playerResultj.getBufen();
//							int bufenj = chaixianBufen2.getValue();
//							// 结算补分
//							chaixianBufen1.jiesuan(-bufenj);
//							chaixianBufen2.jiesuan(-bufeni);
//							break;
//						}
//					}
//					break;
//				}
//			}

            panPlayerResultList.forEach((playerResult) -> {
                //计算当盘总分
                playerResult.setScore(playerResult.getMingcifen().getValue());
                //计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    if (optionalPlay.isJinyuanzi()) {
                        playerResult.setTotalScore(playerResult.getScore() + optionalPlay.getYuanzifen());
                    } else {
                        playerResult.setTotalScore(playerResult.getScore());
                    }
                }
                if (optionalPlay.isJinyuanzi()) {
                    if (playerResult.getTotalScore() <= 0) {
                        ju.getGaungtouPlayers().add(playerResult.getPlayerId());
                    } else {
                        ju.getGaungtouPlayers().remove(playerResult.getPlayerId());
                    }
                }
            });

            //进园子玩法玩家分数不会低于0分
            if (optionalPlay.isJinyuanzi()) {
                double balanceScor = 0;
                for (GuandanPanPlayerResult guandanPanPlayerResult : panPlayerResultList) {
                    if (guandanPanPlayerResult.getTotalScore() < 0) {
                        double score = guandanPanPlayerResult.getTotalScore();
                        balanceScor += score;
                        guandanPanPlayerResult.setTotalScore(0);
                        guandanPanPlayerResult.setScore(guandanPanPlayerResult.getScore() - score);
                    }
                }
                if (balanceScor != 0) {
                    balanceScor /= 2;
                    Optional<GuandanPanPlayerResult> yingjiaPlayerResultScore = panPlayerResultList.stream().filter(playerResult -> yingPlayerId.equals(playerResult.getPlayerId())).findFirst();
                    if (yingjiaPlayerResultScore.isPresent()) {
                        GuandanPanPlayerResult guandanPanPlayerResult = yingjiaPlayerResultScore.get();
                        guandanPanPlayerResult.setTotalScore(guandanPanPlayerResult.getTotalScore() + balanceScor);
                        guandanPanPlayerResult.setScore(guandanPanPlayerResult.getScore() + balanceScor);
                    }
                    Optional<GuandanPanPlayerResult> yingDuijiaPlayerResultScore = panPlayerResultList.stream().filter(playerResult -> duijiaPlayer.getId().equals(playerResult.getPlayerId())).findFirst();
                    if (yingDuijiaPlayerResultScore.isPresent()) {
                        GuandanPanPlayerResult guandanPanPlayerResult = yingDuijiaPlayerResultScore.get();
                        guandanPanPlayerResult.setTotalScore(guandanPanPlayerResult.getTotalScore() + balanceScor);
                        guandanPanPlayerResult.setScore(guandanPanPlayerResult.getScore() + balanceScor);
                    }
                }
            }

            GuandanPanResult guandanPanResult = new GuandanPanResult();
            guandanPanResult.setPan(new PanValueObject(currentPan));
            guandanPanResult.setPanFinishTime(panFinishTime);
            guandanPanResult.setPanPlayerResultList(panPlayerResultList);
            return guandanPanResult;
        } else {
            List<GuandanPanPlayerResult> panPlayerResultList = new ArrayList<>();

            String yingPlayerId = noPaiPlayerIdList.get(0);                            //出完牌玩家ID
            ShuangkouPlayer shuPlayer = currentPan.findDuijiaPlayer(yingPlayerId);     //输家
            String shuPlayerId = shuPlayer.getId();                                    //输家ID
            GuandanPanPlayerResult yingPlayerResult = new GuandanPanPlayerResult();    //赢家
            yingPlayerResult.setPlayerId(yingPlayerId);

            GuandanMingcifen mingcifen = new GuandanMingcifen();
            mingcifen.setMingci(1);
            mingcifen.setYing(true);
            if (shuPlayer.getAllShoupai().size() > 10) {
                mingcifen.setShuangkou(true);
            } else if (shuPlayer.getAllShoupai().size() > 4) {
                mingcifen.setDankou(true);
            } else {
                mingcifen.setPingkou(true);
            }
            mingcifen.calculate();
            yingPlayerResult.setMingcifen(mingcifen);

//			int[] xianshuCount = playerXianshuMap.get(yingPlayerId);
//			if (xianshuCount == null) {
//				xianshuCount = new int[9];
//			}
//			GuandanGongxianFen gongxianfen = calculateTotalGongxianfenForPlayer(yingPlayerId, xianshuCount, ju);
//			gongxianfen.calculate(renshu);
//			yingPlayerResult.setGongxianfen(gongxianfen);
//			int[] xianshuCountArray = new int[9];
//			xianshuCountArray[0] = gongxianfen.getSixian();
//			xianshuCountArray[1] = gongxianfen.getWuxian();
//			xianshuCountArray[2] = gongxianfen.getLiuxian();
//			xianshuCountArray[3] = gongxianfen.getQixian();
//			xianshuCountArray[4] = gongxianfen.getBaxian();
//			xianshuCountArray[5] = gongxianfen.getJiuxian();
//			xianshuCountArray[6] = gongxianfen.getShixian();
//			xianshuCountArray[7] = gongxianfen.getShiyixian();
//			xianshuCountArray[8] = gongxianfen.getShierxian();
//			GuandanXianshuBeishu beishu = new GuandanXianshuBeishu(xianshuCountArray);
//			beishu.calculate(bxfd, jxfd, sxfd);
//			yingPlayerResult.setXianshubeishu(beishu.getValue());
//			GuandanChaixianbufen bufen = new GuandanChaixianbufen();
//			yingPlayerResult.setBufen(bufen);

            panPlayerResultList.add(yingPlayerResult);

            //输家
            GuandanPanPlayerResult shuPlayerResult = new GuandanPanPlayerResult();
            shuPlayerResult.setPlayerId(shuPlayerId);
            GuandanMingcifen mingcifen1 = new GuandanMingcifen();
            mingcifen1.setMingci(2);
            mingcifen1.setYing(false);
            if (shuPlayer.getAllShoupai().size() > 10) {
                mingcifen1.setShuangkou(true);
            } else if (shuPlayer.getAllShoupai().size() > 4) {
                mingcifen1.setDankou(true);
            } else {
                mingcifen1.setPingkou(true);
            }
            mingcifen1.calculate();
            shuPlayerResult.setMingcifen(mingcifen1);

//			int[] xianshuCount1 = playerXianshuMap.get(shuPlayerId);
//			if (xianshuCount1 == null) {
//				xianshuCount1 = new int[9];
//			}
//			shuPlayerResult.setXianshubeishu(beishu.getValue());
//			GuandanGongxianFen gongxianfen1 = calculateTotalGongxianfenForPlayer(shuPlayerId, xianshuCount1, ju);
//			gongxianfen1.calculate(renshu);
//			shuPlayerResult.setGongxianfen(gongxianfen1);
//			GuandanChaixianbufen bufen1 = new GuandanChaixianbufen();
//			shuPlayerResult.setBufen(bufen1);
//
//			// 两两结算贡献分
//			for (int i = 0; i < panPlayerResultList.size(); i++) {
//				GuandanPanPlayerResult playerResult1 = panPlayerResultList.get(i);
//				GuandanGongxianFen gongxian1 = playerResult1.getGongxianfen();
//				for (int j = (i + 1); j < panPlayerResultList.size(); j++) {
//					GuandanPanPlayerResult playerResult2 = panPlayerResultList.get(j);
//					GuandanGongxianFen gongxian2 = playerResult2.getGongxianfen();
//					// 结算贡献分
//					int fen1 = gongxian1.getValue();
//					int fen2 = gongxian2.getValue();
//					gongxian1.jiesuan(-fen2);
//					gongxian2.jiesuan(-fen1);
//				}
//			}

            panPlayerResultList.add(shuPlayerResult);

            panPlayerResultList.forEach((playerResult) -> {
                //计算当盘总分
                playerResult.setScore(playerResult.getMingcifen().getValue());
                //计算累计总分
                if (latestFinishedPanResult != null) {
                    playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
                } else {
                    if (optionalPlay.isJinyuanzi()) {
                        playerResult.setTotalScore(playerResult.getScore() + optionalPlay.getYuanzifen());
                    } else {
                        playerResult.setTotalScore(playerResult.getScore());
                    }
                }
                if (optionalPlay.isJinyuanzi()) {
                    if (playerResult.getTotalScore() <= 0) {
                        ju.getGaungtouPlayers().add(playerResult.getPlayerId());
                    } else {
                        ju.getGaungtouPlayers().remove(playerResult.getPlayerId());
                    }
                }
            });

            //进园子玩法玩家分数不会低于0分
            if (optionalPlay.isJinyuanzi()) {
                double balanceScor = 0;
                for (GuandanPanPlayerResult guandanPanPlayerResult : panPlayerResultList) {
                    if (guandanPanPlayerResult.getTotalScore() < 0) {
                        balanceScor += guandanPanPlayerResult.getTotalScore();
                        guandanPanPlayerResult.setTotalScore(0);
                        guandanPanPlayerResult.setScore(guandanPanPlayerResult.getScore() - balanceScor);
                    }
                }
                Optional<GuandanPanPlayerResult> yingPlayerResultScore = panPlayerResultList.stream().filter(playerResult -> yingPlayerId.equals(playerResult.getPlayerId())).findFirst();
                if (yingPlayerResultScore.isPresent()) {
                    GuandanPanPlayerResult guandanPanPlayerResult = yingPlayerResultScore.get();
                    guandanPanPlayerResult.setTotalScore(guandanPanPlayerResult.getTotalScore() + balanceScor);
                    guandanPanPlayerResult.setScore(guandanPanPlayerResult.getScore() + balanceScor);
                }
            }

            for (GuandanPanPlayerResult guandanPanPlayerResult : panPlayerResultList) {
                guandanPanPlayerResult.setTotalScore(guandanPanPlayerResult.getTotalScore() - guandanPanPlayerResult.getScore());
                guandanPanPlayerResult.setScore(new BigDecimal(Double.toString(difen)).multiply(new BigDecimal(Double.toString(guandanPanPlayerResult.getScore()))).doubleValue());
                guandanPanPlayerResult.setTotalScore(guandanPanPlayerResult.getTotalScore() + guandanPanPlayerResult.getScore());
            }
            GuandanPanResult guandanPanResult = new GuandanPanResult();
            guandanPanResult.setPan(new PanValueObject(currentPan));
            guandanPanResult.setPanFinishTime(panFinishTime);
            guandanPanResult.setPanPlayerResultList(panPlayerResultList);
            return guandanPanResult;
        }

    }

    public PanResult buildCurrentPanResultByChaodi(Ju ju, long panFinishTime) {
        Pan currentPan = ju.getCurrentPan();
        GuandanPanResult latestFinishedPanResult = (GuandanPanResult) ju.findLatestFinishedPanResult();
        Map<String, Double> playerTotalScoreMap = new HashMap<>();
        if (latestFinishedPanResult != null) {
            for (GuandanPanPlayerResult panPlayerResult : latestFinishedPanResult.getPanPlayerResultList()) {
                playerTotalScoreMap.put(panPlayerResult.getPlayerId(), panPlayerResult.getTotalScore());
            }
        }
        List<GuandanPanPlayerResult> panPlayerResultList = new ArrayList<>();
        List<String> playerIdList = ju.getCurrentPan().findAllPlayerId();
        playerIdList.forEach((playerId) -> {
            GuandanPanPlayerResult playerResult = new GuandanPanPlayerResult();
            playerResult.setPlayerId(playerId);
            GuandanMingcifen mingcifen = new GuandanMingcifen();
            playerResult.setMingcifen(mingcifen);
//            playerResult.setXianshubeishu(1);
            GuandanGongxianFen gongxianfen = new GuandanGongxianFen();
            if (chaodiPlayerIdList.contains(playerId)) {
                gongxianfen.setValue(playerTotalGongxianfenMap.get(playerId).getValue() + 4);
            } else {
                gongxianfen.setValue(playerTotalGongxianfenMap.get(playerId).getValue());
            }
            gongxianfen.setTotalscore(gongxianfen.getValue() * (renshu - 1));
//            playerResult.setGongxianfen(gongxianfen);
            GuandanChaixianbufen bufen = new GuandanChaixianbufen();
//            playerResult.setBufen(bufen);
            playerResult.setChaodi(true);
            panPlayerResultList.add(playerResult);
        });

//        // 两两结算贡献分
//        for (int i = 0; i < panPlayerResultList.size(); i++) {
//            GuandanPanPlayerResult playerResult1 = panPlayerResultList.get(i);
//            GuandanGongxianFen gongxian1 = playerResult1.getGongxianfen();
//            for (int j = (i + 1); j < panPlayerResultList.size(); j++) {
//                GuandanPanPlayerResult playerResult2 = panPlayerResultList.get(j);
//                GuandanGongxianFen gongxian2 = playerResult2.getGongxianfen();
//                // 结算贡献分
//                int fen1 = gongxian1.getValue();
//                int fen2 = gongxian2.getValue();
//                gongxian1.jiesuan(-fen2);
//                gongxian2.jiesuan(-fen1);
//            }
//        }

        panPlayerResultList.forEach((playerResult) -> {
            // 计算当盘总分
            playerResult.setScore(playerResult.getScore());
            // 计算累计总分
            if (latestFinishedPanResult != null) {
                playerResult.setTotalScore(playerTotalScoreMap.get(playerResult.getPlayerId()) + playerResult.getScore());
            } else {
                playerResult.setTotalScore(playerResult.getScore());
            }
        });
        GuandanPanResult guandanPanResult = new GuandanPanResult();
        guandanPanResult.setChaodi(true);
        guandanPanResult.setPan(new PanValueObject(currentPan));
        guandanPanResult.setPanFinishTime(panFinishTime);
        guandanPanResult.setPanPlayerResultList(panPlayerResultList);
        return guandanPanResult;
    }

//    private GuandanGongxianFen calculateTotalGongxianfenForPlayer(String playerId, int[] xianshuCount, Ju ju) {
//        return GuandanShoupaiGongxianfenCalculator.calculateTotalGongxianfenWithShouPaiForPlayer(playerId, ju, bx, xianshuCount, gxjb);
//    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }


    public Map<String, GuandanGongxianFen> getPlayerTotalGongxianfenMap() {
        return playerTotalGongxianfenMap;
    }

    public void setPlayerTotalGongxianfenMap(Map<String, GuandanGongxianFen> playerTotalGongxianfenMap) {
        this.playerTotalGongxianfenMap = playerTotalGongxianfenMap;
    }

    public List<String> getChaodiPlayerIdList() {
        return chaodiPlayerIdList;
    }

    public void setChaodiPlayerIdList(List<String> chaodiPlayerIdList) {
        this.chaodiPlayerIdList = chaodiPlayerIdList;
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
