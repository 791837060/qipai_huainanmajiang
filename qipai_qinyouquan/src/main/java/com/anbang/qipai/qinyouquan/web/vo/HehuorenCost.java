package com.anbang.qipai.qinyouquan.web.vo;

public class HehuorenCost {
    private String memberId;//玩家id
    private String nickname;//昵称
    private int memberCount;//下级玩家人数
    private int lianmengyushiCost;//联盟玉石消耗

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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getLianmengyushiCost() {
        return lianmengyushiCost;
    }

    public void setLianmengyushiCost(int lianmengyushiCost) {
        this.lianmengyushiCost = lianmengyushiCost;
    }
}
