package com.anbang.qipai.dalianmeng.msg.receiver;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengYushiCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberPowerCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberScoreCmdService;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengYushiService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.PowerService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.ScoreService;
import com.anbang.qipai.dalianmeng.msg.msjobs.CommonMO;
import com.anbang.qipai.dalianmeng.msg.sink.BijiResultSink;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengWanfa;
import com.anbang.qipai.dalianmeng.plan.bean.PayForContribution;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GamePayType;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameTable;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameHistoricalPanResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.GameJuPlayerResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.GamePanPlayerResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.puke.BijiJuPlayerResult;
import com.anbang.qipai.dalianmeng.plan.bean.result.puke.BijiPanPlayerResult;
import com.anbang.qipai.dalianmeng.plan.service.*;
import com.dml.accounting.AccountingRecord;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.math.BigDecimal;
import java.util.*;

@EnableBinding(BijiResultSink.class)
public class BijiResultMsgReceiver {

    @Autowired
    private PlayService playService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private GameHistoricalJuResultService gameHistoricalResultService;

    @Autowired
    private GameHistoricalPanResultService gameHistoricalPanResultService;

    @Autowired
    private LianmengYushiService lianmengYushiService;

    @Autowired
    private LianmengYushiCmdService lianmengYushiCmdService;

    @Autowired
    private MemberPowerCmdService memberPowerCmdService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    @Autowired
    private PowerService powerService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private MemberScoreCmdService memberScoreCmdService;

    private Gson gson = new Gson();

    @StreamListener(BijiResultSink.BIJIRESULT)
    public void recordMajiangHistoricalResult(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        Map map = gson.fromJson(json, Map.class);
        if ("biji ju result".equals(msg)) {
            Object gid = map.get("gameId");
            Object dyjId = map.get("dayingjiaId");
            Object dthId = map.get("datuhaoId");
            if (gid != null && dyjId != null && dthId != null) {
                String gameId = (String) gid;
                GameTable table = playService.findTableByGameAndServerGameGameId(Game.biji, gameId);
                if (table != null) {
                    if (!table.isJuFinish()) {
                        playService.updateGameMemberTable(gameId);
                        GameHistoricalJuResult pukeHistoricalResult = new GameHistoricalJuResult();
                        pukeHistoricalResult.setGameId(gameId);
                        pukeHistoricalResult.setRoomNo(table.getNo());
                        pukeHistoricalResult.setGame(Game.biji);
                        pukeHistoricalResult.setDayingjiaId((String) dyjId);
                        pukeHistoricalResult.setDatuhaoId((String) dthId);
                        pukeHistoricalResult.setLianmengId(table.getLianmengId());
                        long finishTime = ((Double) map.get("finishTime")).longValue();
                        Object playerList = map.get("playerResultList");
                        if (playerList != null) {
                            List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
                            LianmengWanfa lianmengWanfa = table.getWanfa();
                            Set<String> dayingjiaCountSuperiorMemberIdSet = new HashSet<>();
                            List<String> playerIdList = new ArrayList<>();
                            for (Object juPlayerResult : (List) playerList) {
                                playerIdList.add((String) ((Map) juPlayerResult).get("playerId"));
                            }
                            for (Object juPlayerResult : (List) playerList) {
                                String playerId = (String) ((Map) juPlayerResult).get("playerId");
                                double totalScore = (double) ((Map) juPlayerResult).get("totalScore");
                                MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(playerId, table.getLianmengId());
                                if (GamePayType.DAYINGJIA.equals(lianmengWanfa.getPayType())) {
                                    if (playerId.equals(dyjId)) {
                                        totalScore = jiesuan(table, playerId, totalScore, finishTime, lianmengWanfa,playerIdList);
                                    } else {
                                        powerJiesuan(table, memberLianmengDbo.getSuperiorMemberId(), playerId, totalScore, finishTime);
                                    }
                                } else {
                                    double aaScore = lianmengWanfa.getAaScore();
                                    double mengzhuScore = lianmengWanfa.getMengzhuAADifen();
                                    totalScore = aaJiesuan(table, playerId, totalScore, finishTime, playerId.equals(dyjId), aaScore, mengzhuScore);
                                }
                                BijiJuPlayerResult pr = new BijiJuPlayerResult((Map) juPlayerResult);
                                juPlayerResultList.add(pr);
                                switch (table.getPlayersCount()) {
                                    case 2:
                                        memberDayResultDataService.increaseErrenJuCount(playerId,table.getLianmengId());
                                        memberDayResultDataService.increaseMemberErrenJuCount(playerId,table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet,playerId,table.getLianmengId());
                                            memberDayResultDataService.increaseMemberErrenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;
                                    case 3:
                                        memberDayResultDataService.increaseSanrenJuCount(playerId,table.getLianmengId());
                                        memberDayResultDataService.increaseMemberSanrenJuCount(playerId,table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet,playerId,table.getLianmengId());
                                            memberDayResultDataService.increaseMemberSanrenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;
                                    case 4:
                                        memberDayResultDataService.increaseSirenJuCount(playerId,table.getLianmengId());
                                        memberDayResultDataService.increaseMemberSirenJuCount(playerId,table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet,playerId,table.getLianmengId());
                                            memberDayResultDataService.increaseMemberSirenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;
                                    default:
                                        memberDayResultDataService.increaseDuorenJuCount(playerId,table.getLianmengId());
                                        memberDayResultDataService.increaseMemberDuorenJuCount(playerId,table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet,playerId,table.getLianmengId());
                                            memberDayResultDataService.increaseMemberDuorenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                }
                                memberDayResultDataService.increaseJuCount(playerId,table.getLianmengId());
                                memberDayResultDataService.updatePowerCost(playerId, table.getLianmengId(), totalScore);
                                memberDayResultDataService.updatePower(playerId, table.getLianmengId(), totalScore);
                                memberDayResultDataService.updateMemberPowerForMember(playerId, table.getLianmengId(), totalScore);
                            }
                            for (String playerId : dayingjiaCountSuperiorMemberIdSet) {
                                switch (table.getPlayersCount()) {
                                    case 2:
                                        memberDayResultDataService.increaseErrenDayingjiaCount(playerId, table.getLianmengId());
                                        break;
                                    case 3:
                                        memberDayResultDataService.increaseSanrenDayingjiaCount(playerId, table.getLianmengId());
                                        break;
                                    case 4:
                                        memberDayResultDataService.increaseSirenDayingjiaCount(playerId, table.getLianmengId());
                                        break;
                                    default:
                                        memberDayResultDataService.increaseDuorenDayingjiaCount(playerId, table.getLianmengId());

                                }
                                memberDayResultDataService.increaseDayingjiaCount(playerId, table.getLianmengId());
                            }
                            pukeHistoricalResult.setPlayerResultList(juPlayerResultList);
                            pukeHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
                            pukeHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
                            pukeHistoricalResult.setFinishTime(finishTime);
                            juPlayerResultList.forEach(gameJuPlayerResult -> memberLatAndLonService.deleteMemberLatAndLon(gameJuPlayerResult.playerId()));
                            gameHistoricalResultService.addGameHistoricalResult(pukeHistoricalResult);
                        }
                    }
                }
            }
        }
        if ("biji pan result".equals(msg)) {
            Object gid = map.get("gameId");
            if (gid != null) {
                String gameId = (String) gid;
                GameTable table = playService.findTableByGameAndServerGameGameId(Game.biji, gameId);
                if (table != null) {
                    GameHistoricalPanResult pukeHistoricalResult = new GameHistoricalPanResult();
                    pukeHistoricalResult.setGameId(gameId);
                    pukeHistoricalResult.setGame(Game.biji);
                    Object playerList = map.get("playerResultList");
                    if (playerList != null) {
                        List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
                        ((List) playerList).forEach((panPlayerResult) -> panPlayerResultList.add(new BijiPanPlayerResult((Map) panPlayerResult)));
                        pukeHistoricalResult.setPlayerResultList(panPlayerResultList);
                        pukeHistoricalResult.setNo(((Double) map.get("no")).intValue());
                        pukeHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());
                        gameHistoricalPanResultService.addGameHistoricalResult(pukeHistoricalResult);
                    }
                }
            }
        }
    }


    /**
     * 大赢家结算
     *
     * @param table         桌子实体类
     * @param memberId      玩家ID
     * @param power         增减能量
     * @param finishTime    结束时间
     * @param lianmengWanfa 玩法
     * @return
     */
    private double jiesuan(GameTable table, String memberId, double power, long finishTime, LianmengWanfa lianmengWanfa,List<String>playerIdList) {
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, table.getLianmengId());
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(memberLianmengDbo.getLianmengId());
        List<PayForContribution> payForContributions = lianmengWanfa.getContribution();
        PayForContribution latestPayForContribution = null;
        for (PayForContribution payForContribution : payForContributions) { //大赢家支付能量区间
            if (power < payForContribution.getMaxScore() && power >= payForContribution.getMinScore()) {
                latestPayForContribution = payForContribution;
            }
        }
        if (latestPayForContribution == null) {
            try {
                AccountingRecord poweraccountingRecord = memberPowerCmdService.givePowerToMember(memberId, table.getLianmengId(), power, "game ju finish", finishTime);
                PowerAccountingRecord powerAccountingRecord = powerService.withdraw(memberId, table.getLianmengId(), null, poweraccountingRecord);
                AccountingRecord accountingRecord = lianmengYushiCmdService.withdraw(allianceDbo.getMengzhu(), table.getYushi(), "game ju finish", finishTime);
                LianmengYushiAccountingRecord record = lianmengYushiService.withdraw(allianceDbo.getMengzhu(), accountingRecord, allianceDbo.getId(), table);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int renshu = table.getPlayersCount();
            double contribution = new BigDecimal(Double.toString(latestPayForContribution.getContribution())).divide(new BigDecimal(Double.toString(renshu))).doubleValue();//大赢家需支付的能量
            for (String playerId : playerIdList) {
                List<String> memberIds = new ArrayList<>();
                List<Integer> contributions = new ArrayList<>();
                List<Double> scores = new ArrayList<>();
                double tempScore = 0;
                MemberLianmengDbo memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(playerId, table.getLianmengId());
                if (!memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) { //如果玩家等级是成员以上，设置贡献分比例
                    memberIds.add(memberLianmengDbo1.getMemberId());
                    contributions.add(100 - memberLianmengDbo1.getContributionProportion());
                }
                while (true) {
                    if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {   //循环查询玩家的上级 设置贡献分比例
                        memberIds.add(memberLianmengDbo1.getSuperiorMemberId());
                        contributions.add(memberLianmengDbo1.getContributionProportion());
                    } else {
                        break;
                    }
                    memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo1.getSuperiorMemberId(), memberLianmengDbo1.getLianmengId());
                }
                for (int i = memberIds.size() - 1; i >= 0; i--) {
                    if (i == memberIds.size() - 1) {
                        scores.add(new BigDecimal(Double.toString(contribution)).multiply(new BigDecimal(contributions.get(i))).divide(new BigDecimal(Double.toString(100))).doubleValue());
                        tempScore = new BigDecimal(Double.toString(contribution)).subtract(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
                    } else {
                        scores.add(new BigDecimal(Double.toString(tempScore)).multiply(new BigDecimal(contributions.get(i))).divide(new BigDecimal(Double.toString(100))).doubleValue());
                        tempScore = new BigDecimal(Double.toString(tempScore)).subtract(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
                    }
                    if (i == 0) {
                        tempScore = new BigDecimal(Double.toString(tempScore)).add(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
                        scores.remove(scores.size() - 1);
                        scores.add(tempScore);
                    }
                }
                for (int i = memberIds.size() - 1; i >= 0; i--) {   //根据每个上级的贡献分比例，给上级贡献分
                    if (i == 0) {
                        if (memberIds.get(i).equals(playerId)) {
                            giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), playerId, finishTime, null);
                        } else {
                            giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), playerId, finishTime, playerId);
                        }
                    } else {
                        giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), playerId, finishTime, memberIds.get(i - 1));
                    }

                }

            }
            try {
                if (power - (latestPayForContribution.getContribution() + latestPayForContribution.getMengzhuContirbution()) >= 0) {  //给用户增能量
                    AccountingRecord poweraccountingRecord = memberPowerCmdService.givePowerToMember(memberId, table.getLianmengId(), power - (latestPayForContribution.getContribution() + latestPayForContribution.getMengzhuContirbution()), "game ju finish", finishTime);
                    PowerAccountingRecord powerAccountingRecord = powerService.withdraw(memberId, table.getLianmengId(), memberLianmengDbo.getSuperiorMemberId(), poweraccountingRecord);
                } else {    //给用户减能量
                    AccountingRecord poweraccountingRecord = memberPowerCmdService.withdraw(memberId, table.getLianmengId(), (latestPayForContribution.getContribution() + latestPayForContribution.getMengzhuContirbution()) - power, "game ju finish", finishTime);
                    PowerAccountingRecord powerAccountingRecord = powerService.withdraw(memberId, table.getLianmengId(), memberLianmengDbo.getSuperiorMemberId(), poweraccountingRecord);
                }
                //扣盟主的钻石
                AccountingRecord accountingRecord = lianmengYushiCmdService.withdraw(allianceDbo.getMengzhu(), table.getYushi(), "game ju finish", finishTime);
                LianmengYushiAccountingRecord record = lianmengYushiService.withdraw(allianceDbo.getMengzhu(), accountingRecord, allianceDbo.getId(), table);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (latestPayForContribution.getMengzhuContirbution() != 0) {
                MemberLianmengDbo memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, table.getLianmengId());
                while (!memberLianmengDbo1.getSuperiorMemberId().equals(allianceDbo.getMengzhu())) {
                    memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo1.getSuperiorMemberId(), table.getLianmengId());
                }
                memberDayResultDataService.updateScore(memberLianmengDbo1.getMemberId(), table.getLianmengId(), latestPayForContribution.getMengzhuContirbution());
                try {
                    AccountingRecord memberScoreRecord = memberScoreCmdService.giveScoreToMember(allianceDbo.getMengzhu(), table.getLianmengId(), latestPayForContribution.getMengzhuContirbution(), "game ju finish", finishTime);
                    ScoreAccountingRecord scoreAccountingRecord = scoreService.withdraw(allianceDbo.getMengzhu(), table.getLianmengId(), memberId, memberScoreRecord);
                } catch (MemberNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return power - (latestPayForContribution.getContribution() + latestPayForContribution.getMengzhuContirbution());
    }

    /**
     * AA结算
     *
     * @param table      桌子实体类
     * @param memberId   玩家ID
     * @param power      玩家分数
     * @param finishTime 结束时间
     * @param dayingjia  玩家是否为大赢家
     * @param aaScore    AA分数
     * @return
     */
    private double aaJiesuan(GameTable table, String memberId, double power, long finishTime, boolean dayingjia, double aaScore, double mengzhuScore) {
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, table.getLianmengId());
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(memberLianmengDbo.getLianmengId());
        List<String> memberIds = new ArrayList<>();
        List<Integer> contributions = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        double tempScore = 0;
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) { //如果玩家等级是成员以上，设置贡献分比例
            memberIds.add(memberLianmengDbo1.getMemberId());
            contributions.add(100 - memberLianmengDbo1.getContributionProportion());
        }
        while (true) {  //循环查询玩家的上级 设置贡献分比例
            if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                memberIds.add(memberLianmengDbo1.getSuperiorMemberId());
                contributions.add(memberLianmengDbo1.getContributionProportion());
            } else {
                break;
            }
            memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo1.getSuperiorMemberId(), memberLianmengDbo1.getLianmengId());
        }
        for (int i = memberIds.size() - 1; i >= 0; i--) {
            if (i == memberIds.size() - 1) {
                scores.add(new BigDecimal(Double.toString(aaScore)).multiply(new BigDecimal(contributions.get(i))).divide(new BigDecimal(Double.toString(100))).doubleValue());
                tempScore = new BigDecimal(Double.toString(aaScore)).subtract(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
            } else {
                scores.add(new BigDecimal(Double.toString(tempScore)).multiply(new BigDecimal(contributions.get(i))).divide(new BigDecimal(Double.toString(100))).doubleValue());
                tempScore = new BigDecimal(Double.toString(tempScore)).subtract(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
            }
            if (i == 0) {
                tempScore = new BigDecimal(Double.toString(tempScore)).add(BigDecimal.valueOf(scores.get(scores.size() - 1))).doubleValue();
                scores.remove(scores.size() - 1);
                scores.add(tempScore);
            }
        }
        for (int i = memberIds.size() - 1; i >= 0; i--) {   //根据每个上级的贡献分比例，给上级贡献分
            if (i == 0) {
                if (memberIds.get(i).equals(memberId)) {
                    giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), memberId, finishTime, null);
                } else {
                    giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), memberId, finishTime, memberId);
                }
            } else {
                giveScoreToMember(memberIds.get(i), table.getLianmengId(), scores.get(memberIds.size() - i - 1), memberId, finishTime, memberIds.get(i - 1));
            }
        }
        try {   //给用户增减能量
            AccountingRecord poweraccountingRecord = memberPowerCmdService.givePowerToMember(memberId, table.getLianmengId(), power - (aaScore + mengzhuScore), "game ju finish", finishTime);
            PowerAccountingRecord powerAccountingRecord = powerService.withdraw(memberId, table.getLianmengId(), memberLianmengDbo.getSuperiorMemberId(), poweraccountingRecord);
            if (dayingjia) {    //如果是大赢家，扣盟主的钻石
                AccountingRecord accountingRecord = lianmengYushiCmdService.withdraw(allianceDbo.getMengzhu(), table.getYushi(), "game ju finish", finishTime);
                LianmengYushiAccountingRecord record = lianmengYushiService.withdraw(allianceDbo.getMengzhu(), accountingRecord, allianceDbo.getId(), table);
            }
            MemberLianmengDbo memberLianmengDbo2 = memberLianmengDbo;
            while (!memberLianmengDbo2.getSuperiorMemberId().equals(allianceDbo.getMengzhu())) {
                memberLianmengDbo2 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo2.getSuperiorMemberId(), table.getLianmengId());
            }
            memberDayResultDataService.updateScore(memberLianmengDbo2.getMemberId(), table.getLianmengId(), mengzhuScore);
            try {
                AccountingRecord memberScoreRecord = memberScoreCmdService.giveScoreToMember(allianceDbo.getMengzhu(), table.getLianmengId(), mengzhuScore, "game ju finish", finishTime);
                ScoreAccountingRecord scoreAccountingRecord = scoreService.withdraw(allianceDbo.getMengzhu(), table.getLianmengId(), memberId, memberScoreRecord);
            } catch (MemberNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return power - aaScore - mengzhuScore;
    }


    /**
     * 给用户增贡献分
     *
     * @param memberId    玩家ID
     * @param lianmengId  联盟ID
     * @param score       增减贡献分
     * @param dayingjiaId 大赢家ID
     * @param finishTime  结束时间
     */
    public void giveScoreToMember(String memberId, String lianmengId, Double score, String dayingjiaId, long finishTime, String sourceId) {
        try {
            AccountingRecord memberScoreRecord = memberScoreCmdService.giveScoreToMember(memberId, lianmengId, score, "game ju finish", finishTime);
            ScoreAccountingRecord scoreAccountingRecord = scoreService.withdraw(memberId, lianmengId, dayingjiaId, memberScoreRecord);
            if (sourceId != null) {
                memberDayResultDataService.updateScore(sourceId, lianmengId, score);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 给用户增能量
     *
     * @param table      桌子实体类
     * @param referer    来源ID
     * @param memberId   玩家ID
     * @param power      增减能量
     * @param finishTime 结束时间
     */
    private void powerJiesuan(GameTable table, String referer, String memberId, double power, long finishTime) {
        try {
            AccountingRecord poweraccountingRecord = memberPowerCmdService.givePowerToMember(memberId, table.getLianmengId(), power, "game ju finish", finishTime);
            PowerAccountingRecord powerAccountingRecord = powerService.withdraw(memberId, table.getLianmengId(), referer, poweraccountingRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recordSuperiorMemberId(Set<String> memberIdSet, String memberId, String lianmengId) {
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, lianmengId);
        while (true) {
            memberIdSet.add(memberLianmengDbo.getMemberId());
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                break;
            }
            memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), lianmengId);
        }
    }


}
