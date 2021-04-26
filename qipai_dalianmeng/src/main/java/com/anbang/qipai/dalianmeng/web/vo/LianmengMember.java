package com.anbang.qipai.dalianmeng.web.vo;

public class LianmengMember {
    private String memberId;//玩家id
    private String nickname;//玩家昵称
    private String shangjiId;//上级id
    private String shangji;//上级昵称
    private boolean ban;//封禁

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getShangjiId() {
        return shangjiId;
    }

    public void setShangjiId(String shangjiId) {
        this.shangjiId = shangjiId;
    }

    public String getShangji() {
        return shangji;
    }

    public void setShangji(String shangji) {
        this.shangji = shangji;
    }
}
