package com.anbang.qipai.dalianmeng.web.vo;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.Identity;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;

public class LianmengMemberVO {
    private String memberId;//玩家id
    private String headimgurl;//玩家头像
    private String nickname;//玩家昵称
    private String superiorMemberId;//上级Id
    private String zhushouId;//助手Id
    private Identity identity;//身份
    private String onlineState;//在线状态
    private int contributionProportion = 0;//贡献分比例
    private int juCount=0;//局数
    private int dayingjiaCount=0;//大赢家数
    private int erJuCount=0;//局数
    private int erDayingjiaCount=0;//大赢家数
    private int sanJuCount=0;//局数
    private int sanDayingjiaCount=0;//大赢家数
    private int siJuCount=0;//局数
    private int siDayingjiaCount=0;//大赢家数
    private int duoJuCount=0;//局数
    private int duoDayingjiaCount=0;//大赢家数
    private double power;//能量
    private double score;//贡献分
    private double powerCost;
    private boolean ban;//封禁

    public LianmengMemberVO(MemberLianmengDbo memberLianmengDbo , MemberDayResultData memberDayResultData, String onlineState, boolean first) {
        memberId = memberLianmengDbo.getMemberId();
        headimgurl = memberLianmengDbo.getHeadimgurl();
        nickname = memberLianmengDbo.getNickname();
        superiorMemberId=memberLianmengDbo.getSuperiorMemberId();
        zhushouId = memberLianmengDbo.getZhushouId();
        identity = memberLianmengDbo.getIdentity();
        contributionProportion = memberLianmengDbo.getContributionProportion();
        this.onlineState=onlineState;
        if (first){
            juCount = memberDayResultData.getMemberErrenJuCount()+memberDayResultData.getMemberSanrenJuCount()+memberDayResultData.getMemberSirenJuCount()+memberDayResultData.getMemberDuorenJuCount();
            dayingjiaCount = memberDayResultData.getMemberErrenDayingjiaCount()+memberDayResultData.getMemberSanrenDayingjiaCount()+memberDayResultData.getMemberSirenDayingjiaCount()+memberDayResultData.getMemberDuorenDayingjiaCount();
            erJuCount = memberDayResultData.getMemberErrenJuCount();
            erDayingjiaCount = memberDayResultData.getMemberErrenDayingjiaCount();
            sanJuCount = memberDayResultData.getMemberSanrenJuCount();
            sanDayingjiaCount = memberDayResultData.getMemberSanrenDayingjiaCount();
            siJuCount = memberDayResultData.getMemberSirenJuCount();
            siDayingjiaCount = memberDayResultData.getMemberSirenDayingjiaCount();
            duoJuCount=memberDayResultData.getMemberDuorenJuCount();
            duoDayingjiaCount=memberDayResultData.getMemberDuorenDayingjiaCount();
            power= memberDayResultData.getMemberPower();
            score = memberDayResultData.getScore();
            powerCost = memberDayResultData.getMemberPowerCost();
        }else {
            juCount = memberDayResultData.getErrenJuCount()+memberDayResultData.getSanrenJuCount()+memberDayResultData.getSirenJuCount()+memberDayResultData.getDuorenJuCount();
            dayingjiaCount = memberDayResultData.getErrenDayingjiaCount()+memberDayResultData.getSanrenDayingjiaCount()+memberDayResultData.getSirenDayingjiaCount()+memberDayResultData.getDuorenDayingjiaCount();
            erJuCount = memberDayResultData.getErrenJuCount();
            erDayingjiaCount = memberDayResultData.getErrenDayingjiaCount();
            sanJuCount = memberDayResultData.getSanrenJuCount();
            sanDayingjiaCount = memberDayResultData.getSanrenDayingjiaCount();
            siJuCount = memberDayResultData.getSirenJuCount();
            siDayingjiaCount = memberDayResultData.getSirenDayingjiaCount();
            duoJuCount=memberDayResultData.getDuorenJuCount();
            duoDayingjiaCount=memberDayResultData.getDuorenDayingjiaCount();
            power= memberDayResultData.getPower();
            score = memberDayResultData.getScore();
            powerCost = memberDayResultData.getPowerCost();
        }
        ban = memberLianmengDbo.isBan();
    }

    public LianmengMemberVO() {
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSuperiorMemberId() {
        return superiorMemberId;
    }

    public void setSuperiorMemberId(String superiorMemberId) {
        this.superiorMemberId = superiorMemberId;
    }

    public String getZhushouId() {
        return zhushouId;
    }

    public void setZhushouId(String zhushouId) {
        this.zhushouId = zhushouId;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public int getContributionProportion() {
        return contributionProportion;
    }

    public void setContributionProportion(int contributionProportion) {
        this.contributionProportion = contributionProportion;
    }

    public int getJuCount() {
        return juCount;
    }

    public void setJuCount(int juCount) {
        this.juCount = juCount;
    }

    public int getDayingjiaCount() {
        return dayingjiaCount;
    }

    public void setDayingjiaCount(int dayingjiaCount) {
        this.dayingjiaCount = dayingjiaCount;
    }

    public int getErJuCount() {
        return erJuCount;
    }

    public void setErJuCount(int erJuCount) {
        this.erJuCount = erJuCount;
    }

    public int getErDayingjiaCount() {
        return erDayingjiaCount;
    }

    public void setErDayingjiaCount(int erDayingjiaCount) {
        this.erDayingjiaCount = erDayingjiaCount;
    }

    public int getSanJuCount() {
        return sanJuCount;
    }

    public void setSanJuCount(int sanJuCount) {
        this.sanJuCount = sanJuCount;
    }

    public int getSanDayingjiaCount() {
        return sanDayingjiaCount;
    }

    public void setSanDayingjiaCount(int sanDayingjiaCount) {
        this.sanDayingjiaCount = sanDayingjiaCount;
    }

    public int getSiJuCount() {
        return siJuCount;
    }

    public void setSiJuCount(int siJuCount) {
        this.siJuCount = siJuCount;
    }

    public int getSiDayingjiaCount() {
        return siDayingjiaCount;
    }

    public void setSiDayingjiaCount(int siDayingjiaCount) {
        this.siDayingjiaCount = siDayingjiaCount;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
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

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public int getDuoJuCount() {
        return duoJuCount;
    }

    public void setDuoJuCount(int duoJuCount) {
        this.duoJuCount = duoJuCount;
    }

    public int getDuoDayingjiaCount() {
        return duoDayingjiaCount;
    }

    public void setDuoDayingjiaCount(int duoDayingjiaCount) {
        this.duoDayingjiaCount = duoDayingjiaCount;
    }
}
