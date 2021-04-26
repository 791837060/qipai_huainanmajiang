package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

/**
 * 联盟成员
 */
public class MemberLianmengDbo {
    private String id;
    private String memberId;//玩家id
    private String nickname;// 会员昵称
    private String headimgurl;// 头像url
    private Identity identity;//身份
    private String lianmengId;//联盟
    private String superiorMemberId;
    private String onlineState;//在线状态
    private boolean ban;//封禁
    private long createTime;//加入时间
    private String zhushouId;
    private boolean free=false;
    private int maxScore=0;
    private int minScore = 0;
    private double score = 0;



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

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

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
