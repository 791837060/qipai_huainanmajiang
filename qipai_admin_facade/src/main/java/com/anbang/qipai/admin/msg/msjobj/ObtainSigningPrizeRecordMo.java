package com.anbang.qipai.admin.msg.msjobj;

import com.anbang.qipai.admin.plan.bean.PrizeEnum;
import com.dml.accounting.AccountingRecord;

public class ObtainSigningPrizeRecordMo {

    private String id;
    private String memberId;
    private PrizeEnum prize;
    private long obtainTime;
    /**
     * 会员等级
     */
    private int vipLevel;
    /**
     * 如果是会员获取的礼券数
     */
    private int score;
    /**
     * 如果是金币奖励
     */
    private AccountingRecord goldAccountingRecord;

    /**
     * 如果是会员
     */
    private AccountingRecord scoreAccountingRecord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public PrizeEnum getPrize() {
        return prize;
    }

    public void setPrize(PrizeEnum prize) {
        this.prize = prize;
    }

    public long getObtainTime() {
        return obtainTime;
    }

    public void setObtainTime(long obtainTime) {
        this.obtainTime = obtainTime;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AccountingRecord getGoldAccountingRecord() {
        return goldAccountingRecord;
    }

    public void setGoldAccountingRecord(AccountingRecord goldAccountingRecord) {
        this.goldAccountingRecord = goldAccountingRecord;
    }

    public AccountingRecord getScoreAccountingRecord() {
        return scoreAccountingRecord;
    }

    public void setScoreAccountingRecord(AccountingRecord scoreAccountingRecord) {
        this.scoreAccountingRecord = scoreAccountingRecord;
    }
}
