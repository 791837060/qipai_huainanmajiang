package com.anbang.qipai.qinyouquan.msg.receiver;

import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.c.service.MemberDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDiamondAccountingRecord;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengDiamondService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.MemberDiamondService;
import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.sink.YizhengMajiangResultSink;
import com.anbang.qipai.qinyouquan.plan.bean.LianmengWanfa;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.anbang.qipai.qinyouquan.plan.bean.game.GamePayType;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameTable;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalPanResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameJuPlayerResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.GamePanPlayerResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.majiang.YizhengMajiangJuPlayerResult;
import com.anbang.qipai.qinyouquan.plan.bean.result.majiang.YizhengMajiangPanPlayerResult;
import com.anbang.qipai.qinyouquan.plan.service.*;
import com.anbang.qipai.qinyouquan.plan.service.*;
import com.dml.accounting.AccountingRecord;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.*;

@EnableBinding(YizhengMajiangResultSink.class)
public class YizhengResultMsgReceiver {

    @Autowired
    private PlayService playService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private GameHistoricalJuResultService gameHistoricalResultService;

    @Autowired
    private GameHistoricalPanResultService gameHistoricalPanResultService;

    @Autowired
    private LianmengDiamondService lianmengDiamondService;

    @Autowired
    private LianmengDiamondCmdService lianmengDiamondCmdService;

    @Autowired
    private MemberDiamondCmdService memberDiamondCmdService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    @Autowired
    private MemberDiamondService memberDiamondService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private LianmengMemberService lianmengMemberService;


    private Gson gson = new Gson();

    @StreamListener(YizhengMajiangResultSink.YIZHENGMAJIANGRESULT)
    public void recordMajiangHistoricalResult(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        Map map = gson.fromJson(json, Map.class);
        if ("yizhengMajiang ju result".equals(msg)) {
            Object gid = map.get("gameId");
            Object dyjId = map.get("dayingjiaId");
            Object dthId = map.get("datuhaoId");
            if (gid != null && dyjId != null && dthId != null) {
                String gameId = (String) gid;
                GameTable table = playService.findTableByGameAndServerGameGameId(Game.yizhengMajiang, gameId);
                if (table != null) {
                    if (!table.isJuFinish()) {
                        playService.updateGameMemberTable(gameId);
                        GameHistoricalJuResult pukeHistoricalResult = new GameHistoricalJuResult();
                        pukeHistoricalResult.setGameId(gameId);
                        pukeHistoricalResult.setRoomNo(table.getNo());
                        pukeHistoricalResult.setGame(Game.yizhengMajiang);
                        pukeHistoricalResult.setDayingjiaId((String) dyjId);
                        pukeHistoricalResult.setSuperiorMemberId(lianmengService.findMemberLianmengDboByMemberIdAndLianmengId((String) dyjId, table.getLianmengId()).getSuperiorMemberId());
                        pukeHistoricalResult.setDatuhaoId((String) dthId);
                        pukeHistoricalResult.setLianmengId(table.getLianmengId());
                        long finishTime = ((Double) map.get("finishTime")).longValue();
                        Object playerList = map.get("playerResultList");
                        if (playerList != null) {
                            List<GameJuPlayerResult> juPlayerResultList = new ArrayList<>();
                            LianmengWanfa lianmengWanfa = table.getWanfa();
                            Set<String> dayingjiaCountSuperiorMemberIdSet = new HashSet<>();
                            int cost = table.getCost();
                            for (Object juPlayerResult : (List) playerList) {
                                String playerId = (String) ((Map) juPlayerResult).get("playerId");
                                double totalScore = (double) ((Map) juPlayerResult).get("totalScore");
                                MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(playerId, table.getLianmengId());
                                lianmengMemberService.updateMemberScore(playerId,memberLianmengDbo.getLianmengId(),memberLianmengDbo.getScore()+totalScore);
                                YizhengMajiangJuPlayerResult pr = new YizhengMajiangJuPlayerResult((Map) juPlayerResult);
                                if (GamePayType.DAYINGJIA.equals(lianmengWanfa.getPayType())) {
                                    if (playerId.equals(dyjId)) {
                                        if (!memberLianmengDbo.isFree()) {
                                            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                                                mengzhuDiamondWithdraw(table.getLianmengId(),playerId,cost,finishTime,table);
                                            } else {
                                                diamondWithdraw(table.getLianmengId(), playerId, cost, finishTime, memberLianmengDbo.getSuperiorMemberId(), table);

                                            }
                                            pr.setDiamondCost(cost);
                                        } else {
                                            MemberLianmengDbo memberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo.getSuperiorMemberId(), memberLianmengDbo.getLianmengId());
                                            if (memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                                                mengzhuDiamondWithdraw(table.getLianmengId(), memberLianmengDbo1.getMemberId(), cost, finishTime, table);

                                            } else {
                                                diamondWithdraw(table.getLianmengId(), memberLianmengDbo1.getMemberId(), cost, finishTime, memberLianmengDbo.getSuperiorMemberId(), table);
                                            }
                                        }
                                        memberDayResultDataService.increaseDiamondCost(playerId, table.getLianmengId(), cost);
                                        memberDayResultDataService.increaseMemberDiamondCost(playerId, table.getLianmengId(), cost);
                                    }
                                } else {
                                    if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                                        mengzhuDiamondWithdraw(table.getLianmengId(), playerId, cost/table.getPlayersCount(), finishTime, table);
                                    } else {
                                        diamondWithdraw(table.getLianmengId(), playerId, cost/table.getPlayersCount(), finishTime, memberLianmengDbo.getSuperiorMemberId(), table);
                                    }
                                    memberDayResultDataService.increaseDiamondCost(playerId, table.getLianmengId(), cost/table.getPlayersCount());
                                    memberDayResultDataService.increaseMemberDiamondCost(playerId, table.getLianmengId(), cost/table.getPlayersCount());
                                    pr.setDiamondCost(cost/table.getPlayersCount());
                                    if (playerId.equals(dyjId)) {
                                        memberDayResultDataService.increaseFinishCount(playerId, table.getLianmengId(), 1);
                                    }
                                }
                                pr.setSuperiorMemberId(memberLianmengDbo.getSuperiorMemberId());
                                juPlayerResultList.add(pr);
                                switch (table.getPlayersCount()) {
                                    case 2:
                                        memberDayResultDataService.increaseErrenJuCount(playerId, table.getLianmengId());
                                        memberDayResultDataService.increaseMemberErrenJuCount(playerId, table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet, playerId, table.getLianmengId());
                                            memberDayResultDataService.increaseMemberErrenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;
                                    case 3:
                                        memberDayResultDataService.increaseSanrenJuCount(playerId, table.getLianmengId());
                                        memberDayResultDataService.increaseMemberSanrenJuCount(playerId, table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet, playerId, table.getLianmengId());
                                            memberDayResultDataService.increaseMemberSanrenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;
                                    case 4:
                                        memberDayResultDataService.increaseSirenJuCount(playerId, table.getLianmengId());
                                        memberDayResultDataService.increaseMemberSirenJuCount(playerId, table.getLianmengId());
                                        if (playerId.equals(dyjId)) {
                                            recordSuperiorMemberId(dayingjiaCountSuperiorMemberIdSet, playerId, table.getLianmengId());
                                            memberDayResultDataService.increaseMemberSirenDayingjiaCount(playerId, table.getLianmengId());
                                        }
                                        break;

                                }
                                memberDayResultDataService.increaseMemberTotalScore(playerId, table.getLianmengId(), totalScore);
                                memberDayResultDataService.increaseTotalScore(playerId, table.getLianmengId(),totalScore);
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

                                }
                                memberDayResultDataService.increaseDayingjiaCount(playerId, table.getLianmengId());

                            }
                            pukeHistoricalResult.setPlayerResultList(juPlayerResultList);
                            pukeHistoricalResult.setPanshu(((Double) map.get("panshu")).intValue());
                            pukeHistoricalResult.setLastPanNo(((Double) map.get("lastPanNo")).intValue());
                            pukeHistoricalResult.setFinishTime(finishTime);
                            juPlayerResultList.forEach(gameJuPlayerResult -> memberLatAndLonService.deleteMemberLatAndLon(gameJuPlayerResult.playerId()));
                            if (GamePayType.AA.equals(lianmengWanfa.getPayType())) {
                                pukeHistoricalResult.setFinish(true);
                            }
                            pukeHistoricalResult.setPayType(lianmengWanfa.getPayType());
                            pukeHistoricalResult.setDiamondCost(table.getCost());
                            gameHistoricalResultService.addGameHistoricalResult(pukeHistoricalResult);
                        }
                    }
                }
            }
        }
        if ("yizhengMajiang pan result".equals(msg)) {
            Object gid = map.get("gameId");
            if (gid != null) {
                String gameId = (String) gid;
                GameTable table = playService.findTableByGameAndServerGameGameId(Game.yizhengMajiang, gameId);
                if (table != null) {
                    GameHistoricalPanResult pukeHistoricalResult = new GameHistoricalPanResult();
                    pukeHistoricalResult.setGameId(gameId);
                    pukeHistoricalResult.setGame(Game.yizhengMajiang);
                    Object playerList = map.get("playerResultList");
                    if (playerList != null) {
                        List<GamePanPlayerResult> panPlayerResultList = new ArrayList<>();
                        ((List) playerList).forEach((panPlayerResult) -> panPlayerResultList
                                .add(new YizhengMajiangPanPlayerResult((Map) panPlayerResult)));
                        pukeHistoricalResult.setPlayerResultList(panPlayerResultList);

                        pukeHistoricalResult.setNo(((Double) map.get("no")).intValue());
                        pukeHistoricalResult.setFinishTime(((Double) map.get("finishTime")).longValue());

                        gameHistoricalPanResultService.addGameHistoricalResult(pukeHistoricalResult);
                    }
                }
            }
        }
    }


    private void diamondWithdraw(String lianmengId, String memberId, int amount, long finishTime, String superiorMemberId, GameTable gameTable) {
        try {
            AccountingRecord accountingRecord = memberDiamondCmdService.withdraw(memberId, lianmengId, amount, "game ju finish", finishTime);
            MemberDiamondAccountingRecord memberDiamondAccountingRecord = memberDiamondService.withdraw(memberId, lianmengId, superiorMemberId, accountingRecord, gameTable);
//            memberDayResultDataService.increaseDiamondCost(memberId, lianmengId, amount);
//            memberDayResultDataService.increaseMemberDiamondCost(memberId, lianmengId, amount);
            memberDayResultDataService.increaseDiamond(memberId, lianmengId, -amount);
            memberDayResultDataService.increaseMemberDiamond(memberId, lianmengId, -amount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mengzhuDiamondWithdraw(String lianmengId, String memberId, int amount, long finishTime, GameTable gameTable) {
        try {
            AccountingRecord accountingRecord = lianmengDiamondCmdService.withdraw(memberId, amount, "game ju finish", finishTime);
            lianmengDiamondService.withdraw(memberId, accountingRecord, lianmengId, gameTable);
//            memberDayResultDataService.increaseDiamondCost(memberId,lianmengId,amount);
//            memberDayResultDataService.increaseMemberDiamondCost(memberId,lianmengId,amount);
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
