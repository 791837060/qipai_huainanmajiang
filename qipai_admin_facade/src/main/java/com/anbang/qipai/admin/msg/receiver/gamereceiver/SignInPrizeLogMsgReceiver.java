package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.alibaba.fastjson.JSON;
import com.anbang.qipai.admin.constant.LotteryType;
import com.anbang.qipai.admin.msg.channel.sink.SignInPrizeLogSink;
import com.anbang.qipai.admin.msg.msjobj.*;
import com.anbang.qipai.admin.plan.bean.ObatinSigningPrizeRecord;
import com.anbang.qipai.admin.plan.bean.PrizeEnum;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;
import com.anbang.qipai.admin.plan.bean.members.MemberGoldRecordDbo;
import com.anbang.qipai.admin.plan.bean.members.MemberScoreRecordDbo;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeExchangeLog;
import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;
import com.anbang.qipai.admin.plan.service.membersservice.*;
import com.anbang.qipai.admin.plan.service.signinservice.ObtainSignPrizeRecordService;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeExchangeLogService;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeLogService;
import com.anbang.qipai.admin.plan.service.signinservice.SignInPrizeService;
import com.anbang.qipai.admin.remote.LotteryMoTypeEnum;
import com.dml.accounting.AccountingRecord;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Calendar;
import java.util.Date;

import static com.anbang.qipai.admin.msg.channel.sink.SignInPrizeLogSink.*;

/**
 * 接受中奖记录消息
 *
 * @author created by hanzhuofan  2018.09.18
 */
@EnableBinding(SignInPrizeLogSink.class)
public class SignInPrizeLogMsgReceiver {

    @Autowired
    private SignInPrizeService signInPrizeService;

    @Autowired
    private SignInPrizeLogService signInPrizeLogService;

    @Autowired
    private SignInPrizeExchangeLogService signInPrizeExchangeLogService;

    @Autowired
    private ObtainSignPrizeRecordService obtainSignPrizeRecordService;

    @Autowired
    private MemberGoldService memberGoldService;

    @Autowired
    private MemberScoreService memberScoreService;

    @Autowired
    private MemberDboService memberService;

    @Autowired
    private MemberExchangeEntityService exchangeEntityService;

    @Autowired
    private MemberClubCardService memberClubCardService;

    private Gson gson = new Gson();

    @StreamListener(SignInPrizeLogSink.channel)
    public void addSignInPrizeLog(CommonMO mo) {
        final String msg = mo.getMsg();
        final String dataJson = gson.toJson(mo.getData());
        try {
            if (RAFFLE_RECORD.equals(msg)) {
                this.handleRaffle(dataJson);
            } else if (OBTAIN_SIGN_PRIZE.equals(msg)) {
                this.handleObtainSigningPrize(dataJson);
            } else if (PRIZE_EXCHANGE.equals(msg)) {
                this.handleExchange(dataJson);
            } else if (RAFFLE_HISTORY_ADDRESS.equals(msg)) {
                this.handleEntityExchange(dataJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleObtainSigningPrize(String dataJson) {
        ObtainSigningPrizeRecordMo obtainSigningPrizeRecordMo = JSON.parseObject(dataJson, ObtainSigningPrizeRecordMo.class);
        ObatinSigningPrizeRecord obatinSigningPrizeRecord = new ObatinSigningPrizeRecord();
        obatinSigningPrizeRecord.setId(obtainSigningPrizeRecordMo.getId());
        obatinSigningPrizeRecord.setMemberId(obtainSigningPrizeRecordMo.getMemberId());
        obatinSigningPrizeRecord.setObtainTime(obtainSigningPrizeRecordMo.getObtainTime());
        obatinSigningPrizeRecord.setPrize(obtainSigningPrizeRecordMo.getPrize());
        obatinSigningPrizeRecord.setScore(obtainSigningPrizeRecordMo.getScore());
        obatinSigningPrizeRecord.setVipLevel(obtainSigningPrizeRecordMo.getVipLevel());
        obatinSigningPrizeRecord.setGoldenAccountingNo(obtainSigningPrizeRecordMo.getGoldAccountingRecord().getAccountingNo());
        obatinSigningPrizeRecord.setScoreAccountingNo(obtainSigningPrizeRecordMo.getScoreAccountingRecord().getAccountingNo());
        obtainSignPrizeRecordService.save(obatinSigningPrizeRecord);
        PrizeEnum prize = obatinSigningPrizeRecord.getPrize();
        if (PrizeEnum.isGoldType(prize)) {
            MemberGoldRecordDbo goldRecordDbo = new MemberGoldRecordDbo();
            AccountingRecord accountingRecord = obtainSigningPrizeRecordMo.getGoldAccountingRecord();
            goldRecordDbo.setAccountId(accountingRecord.getAccountId());
            goldRecordDbo.setAccountingNo(accountingRecord.getAccountingNo());
            goldRecordDbo.setAccountingAmount((int) accountingRecord.getAccountingAmount());
            goldRecordDbo.setAccountingTime(accountingRecord.getAccountingTime());
            goldRecordDbo.setBalanceAfter((int) accountingRecord.getBalanceAfter());
            goldRecordDbo.setSummary(accountingRecord.getSummary());
            goldRecordDbo.setMemberId(obtainSigningPrizeRecordMo.getMemberId());
            memberGoldService.addGoldRecord(goldRecordDbo);
        } else if (PrizeEnum.isMemberCardType(prize)) {

        }
        if (obtainSigningPrizeRecordMo.getScoreAccountingRecord() != null) {
            AccountingRecord scoreRecord = obtainSigningPrizeRecordMo.getScoreAccountingRecord();
            MemberScoreRecordDbo memberScoreRecordDbo = new MemberScoreRecordDbo();
            memberScoreRecordDbo.setAccountId(scoreRecord.getAccountId());
            memberScoreRecordDbo.setAccountingAmount((int) scoreRecord.getAccountingAmount());
            memberScoreRecordDbo.setBalanceAfter((int) scoreRecord.getBalanceAfter());
            memberScoreRecordDbo.setAccountingNo(scoreRecord.getAccountingNo());
            memberScoreRecordDbo.setAccountingTime(scoreRecord.getAccountingTime());
            memberScoreRecordDbo.setMemberId(obatinSigningPrizeRecord.getMemberId());
            memberScoreRecordDbo.setSummary(scoreRecord.getSummary());
            memberScoreService.addScoreRecord(memberScoreRecordDbo);
        }
    }

    public void handleRaffle(String dataJson) {
        MemberRaffleHistoryMo memberRaffleHistoryMo = JSON.parseObject(dataJson, MemberRaffleHistoryMo.class);
        SignInPrizeLog signInPrizeLog = new SignInPrizeLog();
        signInPrizeLog.setId(memberRaffleHistoryMo.getId());
        signInPrizeLog.setCreateTime(memberRaffleHistoryMo.getTime());
        final LotteryMo lotteryMo = memberRaffleHistoryMo.getLottery();

//        final String id = lotteryMo.getId();
//        signInPrizeService.decreaseStoreById(id);

        signInPrizeLog.setName(lotteryMo.getName());
        signInPrizeLog.setSignInPrizeId(memberRaffleHistoryMo.getLottery().getId());
        signInPrizeLog.setSingleNum(lotteryMo.getSingleNum());
        final String type = LotteryMoTypeEnum.of(lotteryMo.getType());
        signInPrizeLog.setType(type);
        signInPrizeLog.setMemberId(memberRaffleHistoryMo.getMemberId());

        signInPrizeLog.setNickname(memberService.findMemberById(memberRaffleHistoryMo.getMemberId()).getNickname());

        try {

            signInPrizeLogService.addSignInPrizeLog(signInPrizeLog);
            signInPrizeService.decreaseStoreById(memberRaffleHistoryMo.getLottery().getId());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //如果是会员卡的话
        if (LotteryType.isClubCard(lotteryMo.getType().name())) {
            MemberDbo memberDbo = memberService.findMemberById(memberRaffleHistoryMo.getMemberId());
            if (memberDbo == null) {
                return;
            }
            long time = 0;
            if (memberDbo.getVipEndTime() < System.currentTimeMillis()) {
                time = System.currentTimeMillis();
            } else {
                time = memberDbo.getVipEndTime();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(time));

            if (lotteryMo.getType().name().equals("MEMBER_CARD_DAY")) {
                c.add(Calendar.DAY_OF_WEEK, +lotteryMo.getSingleNum());
                memberClubCardService.giveClubCardToMember(memberDbo.getId(), 1, lotteryMo.getSingleNum());
            }
            if (lotteryMo.getType().name().equals("MEMBER_CARD_WEAK")) {
                c.add(Calendar.WEEK_OF_MONTH, +lotteryMo.getSingleNum());
                memberClubCardService.giveClubCardToMember(memberDbo.getId(), 7, lotteryMo.getSingleNum());
            }
            if (lotteryMo.getType().name().equals("MEMBER_CARD_MONTH")) {
                c.add(Calendar.MONTH, +lotteryMo.getSingleNum());
                memberClubCardService.giveClubCardToMember(memberDbo.getId(), 30, lotteryMo.getSingleNum());
            }
            if (lotteryMo.getType().name().equals("MEMBER_CARD_SEASON")) {
                c.add(Calendar.MONTH, +(3 * lotteryMo.getSingleNum()));
                memberClubCardService.giveClubCardToMember(memberDbo.getId(), 90, lotteryMo.getSingleNum());
            }
            memberService.updateVip(memberDbo.getId(), true, c.getTime().getTime());

        }
    }

    public void handleExchange(String dataJson) {

        ScoreExchangeMo scoreExchangeMo = JSON.parseObject(dataJson, ScoreExchangeMo.class);
        SignInPrizeExchangeLog signInPrizeExchangeLog = new SignInPrizeExchangeLog();
        signInPrizeExchangeLog.setId(scoreExchangeMo.getId());
        signInPrizeExchangeLog.setPhone(scoreExchangeMo.getPhone());
        signInPrizeExchangeLog.setIssue("0");
        signInPrizeExchangeLog.setRewardTime(scoreExchangeMo.getTime());
        if (scoreExchangeMo.getExchangeTypeEnum() == ExchangeTypeEnum.HONG_BAO) {
            String prizeName = scoreExchangeMo.getScore() + "红包点兑换" + scoreExchangeMo.getCurrency() + "红包";
            signInPrizeExchangeLog.setPrizeName(prizeName);

        } else if (scoreExchangeMo.getExchangeTypeEnum() == ExchangeTypeEnum.PHONE_FEE) {
            String prizeName = scoreExchangeMo.getScore() + "话费点兑换" + scoreExchangeMo.getCurrency() + "话费";
            signInPrizeExchangeLog.setPrizeName(prizeName);
        }
        signInPrizeExchangeLog.setScore(scoreExchangeMo.getScore());
        signInPrizeExchangeLog.setCurrency(scoreExchangeMo.getCurrency());
        signInPrizeExchangeLogService.addSignInPrizeExchangeLog(signInPrizeExchangeLog);
    }

    public void handleEntityExchange(String dataJson) {
        EntityExchangeMO entityExchangeMO = JSON.parseObject(dataJson, EntityExchangeMO.class);

        if (!StringUtils.isEmpty(entityExchangeMO.getLotteryType())) {
            if (entityExchangeMO.getLotteryType().equals("PHONE_FEE") || entityExchangeMO.getLotteryType().equals("HONG_BAO")) {
                MemberExchangeEntityDbo entityDbo = new MemberExchangeEntityDbo();
                BeanUtils.copyProperties(entityExchangeMO, entityDbo);
                entityDbo.setHasExchange(false);
                exchangeEntityService.addOne(entityDbo);
                return;
            }
        }

        String id = entityExchangeMO.getMemberId() + "_" + entityExchangeMO.getRaffleRecordId();
        MemberExchangeEntityDbo entityDbo = new MemberExchangeEntityDbo();
        BeanUtils.copyProperties(entityExchangeMO, entityDbo);
        entityDbo.setId(id);
        entityDbo.setTelephone(entityExchangeMO.getTelephone());
        entityDbo.setExchangeTime(entityExchangeMO.getExchangeTime());
        entityDbo.setAddress(entityExchangeMO.getAddress());
        entityDbo.setRealName(entityExchangeMO.getRealName());
        entityDbo.setHasExchange(false);
        exchangeEntityService.saveOne(entityDbo);
    }

}
