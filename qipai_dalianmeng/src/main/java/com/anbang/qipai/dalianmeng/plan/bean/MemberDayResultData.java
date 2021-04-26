package com.anbang.qipai.dalianmeng.plan.bean;

public class MemberDayResultData {
    private String id;
    private String memberId;
    private String lianmengId;
    private int errenJuCount =0;
    private int sanrenJuCount =0;
    private int sirenJuCount =0;
    private int duorenJuCount=0;
    private int juCount =0;
    private int memberErrenJuCount =0;
    private int memberSanrenJuCount =0;
    private int memberSirenJuCount =0;
    private int memberDuorenJuCount=0;
    private int errenDayingjiaCount=0;
    private int sanrenDayingjiaCount=0;
    private int sirenDayingjiaCount=0;
    private int duorenDayingjiaCount=0;
    private int dayingjiaCount=0;
    private int memberErrenDayingjiaCount=0;
    private int memberSanrenDayingjiaCount=0;
    private int memberSirenDayingjiaCount=0;
    private int memberDuorenDayingjiaCount=0;
    private double power=0d;//个人与所有下级能量总和
    private double memberPower=0d;
    private double score=0d;//贡献分数
    private double powerCost=0d;//个人能量消耗
    private double memberPowerCost=0d;//
    private double totalScore=0d;
    private long createTime;

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

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public int getErrenJuCount() {
        return errenJuCount;
    }

    public void setErrenJuCount(int errenJuCount) {
        this.errenJuCount = errenJuCount;
    }

    public int getSanrenJuCount() {
        return sanrenJuCount;
    }

    public void setSanrenJuCount(int sanrenJuCount) {
        this.sanrenJuCount = sanrenJuCount;
    }

    public int getSirenJuCount() {
        return sirenJuCount;
    }

    public void setSirenJuCount(int sirenJuCount) {
        this.sirenJuCount = sirenJuCount;
    }

    public int getJuCount() {
        return juCount;
    }

    public void setJuCount(int juCount) {
        this.juCount = juCount;
    }

    public int getMemberErrenJuCount() {
        return memberErrenJuCount;
    }

    public void setMemberErrenJuCount(int memberErrenJuCount) {
        this.memberErrenJuCount = memberErrenJuCount;
    }

    public int getMemberSanrenJuCount() {
        return memberSanrenJuCount;
    }

    public void setMemberSanrenJuCount(int memberSanrenJuCount) {
        this.memberSanrenJuCount = memberSanrenJuCount;
    }

    public int getMemberSirenJuCount() {
        return memberSirenJuCount;
    }

    public void setMemberSirenJuCount(int memberSirenJuCount) {
        this.memberSirenJuCount = memberSirenJuCount;
    }

    public int getErrenDayingjiaCount() {
        return errenDayingjiaCount;
    }

    public void setErrenDayingjiaCount(int errenDayingjiaCount) {
        this.errenDayingjiaCount = errenDayingjiaCount;
    }

    public int getSanrenDayingjiaCount() {
        return sanrenDayingjiaCount;
    }

    public void setSanrenDayingjiaCount(int sanrenDayingjiaCount) {
        this.sanrenDayingjiaCount = sanrenDayingjiaCount;
    }

    public int getSirenDayingjiaCount() {
        return sirenDayingjiaCount;
    }

    public void setSirenDayingjiaCount(int sirenDayingjiaCount) {
        this.sirenDayingjiaCount = sirenDayingjiaCount;
    }

    public int getDayingjiaCount() {
        return dayingjiaCount;
    }

    public void setDayingjiaCount(int dayingjiaCount) {
        this.dayingjiaCount = dayingjiaCount;
    }

    public int getMemberErrenDayingjiaCount() {
        return memberErrenDayingjiaCount;
    }

    public void setMemberErrenDayingjiaCount(int memberErrenDayingjiaCount) {
        this.memberErrenDayingjiaCount = memberErrenDayingjiaCount;
    }

    public int getMemberSanrenDayingjiaCount() {
        return memberSanrenDayingjiaCount;
    }

    public void setMemberSanrenDayingjiaCount(int memberSanrenDayingjiaCount) {
        this.memberSanrenDayingjiaCount = memberSanrenDayingjiaCount;
    }

    public int getMemberSirenDayingjiaCount() {
        return memberSirenDayingjiaCount;
    }

    public void setMemberSirenDayingjiaCount(int memberSirenDayingjiaCount) {
        this.memberSirenDayingjiaCount = memberSirenDayingjiaCount;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getMemberPower() {
        return memberPower;
    }

    public void setMemberPower(double memberPower) {
        this.memberPower = memberPower;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPowerCost() {
        return powerCost;
    }

    public void setPowerCost(double powerCost) {
        this.powerCost = powerCost;
    }

    public double getMemberPowerCost() {
        return memberPowerCost;
    }

    public void setMemberPowerCost(double memberPowerCost) {
        this.memberPowerCost = memberPowerCost;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public int getDuorenJuCount() {
        return duorenJuCount;
    }

    public void setDuorenJuCount(int duorenJuCount) {
        this.duorenJuCount = duorenJuCount;
    }

    public int getMemberDuorenJuCount() {
        return memberDuorenJuCount;
    }

    public void setMemberDuorenJuCount(int memberDuorenJuCount) {
        this.memberDuorenJuCount = memberDuorenJuCount;
    }

    public int getDuorenDayingjiaCount() {
        return duorenDayingjiaCount;
    }

    public void setDuorenDayingjiaCount(int duorenDayingjiaCount) {
        this.duorenDayingjiaCount = duorenDayingjiaCount;
    }

    public int getMemberDuorenDayingjiaCount() {
        return memberDuorenDayingjiaCount;
    }

    public void setMemberDuorenDayingjiaCount(int memberDuorenDayingjiaCount) {
        this.memberDuorenDayingjiaCount = memberDuorenDayingjiaCount;
    }
}
