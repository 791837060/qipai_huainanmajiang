package com.anbang.qipai.admin.plan.bean;

import com.dml.accounting.AccountingRecord;

public class ObatinSigningPrizeRecord {
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

    private Long goldenAccountingNo;

    private Long scoreAccountingNo;

    public String getMemberId() {
        return memberId;
    }

    public PrizeEnum getPrize() {
        return prize;
    }

    public long getObtainTime() {
        return obtainTime;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public int getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setPrize(PrizeEnum prize) {
        this.prize = prize;
    }

    public void setObtainTime(long obtainTime) {
        this.obtainTime = obtainTime;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getGoldenAccountingNo() {
        return goldenAccountingNo;
    }

    public void setGoldenAccountingNo(Long goldenAccountingNo) {
        this.goldenAccountingNo = goldenAccountingNo;
    }

    public Long getScoreAccountingNo() {
        return scoreAccountingNo;
    }

    public void setScoreAccountingNo(Long scoreAccountingNo) {
        this.scoreAccountingNo = scoreAccountingNo;
    }
}
