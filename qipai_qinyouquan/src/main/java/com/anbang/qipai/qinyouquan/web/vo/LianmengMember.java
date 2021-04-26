package com.anbang.qipai.qinyouquan.web.vo;

public class LianmengMember {
    private String memberId;//玩家id
    private String nickname;//玩家昵称
    private String shangjiId;//上级id
    private String shangji;//上级昵称
    private boolean ban;//封禁

    private int dayingjiaCount;             //大赢家数
    private int juCount;                    //对局总数
    private int balance;                    //砖石余额
    private int lianmengmembercost;         //联盟成员砖石消耗

    private int threeDayJuCount;            //三日对局总数
    private int sevenDayJuCount;            //七日对局总数

    private int dayJuCount;                 //当日对局总数

    public int getDayJuCount() {
        return dayJuCount;
    }

    public void setDayJuCount(int dayJuCount) {
        this.dayJuCount = dayJuCount;
    }

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

    public int getDayingjiaCount() {
        return dayingjiaCount;
    }

    public void setDayingjiaCount(int dayingjiaCount) {
        this.dayingjiaCount = dayingjiaCount;
    }

    public int getJuCount() {
        return juCount;
    }

    public void setJuCount(int juCount) {
        this.juCount = juCount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLianmengmembercost() {
        return lianmengmembercost;
    }

    public void setLianmengmembercost(int lianmengmembercost) {
        this.lianmengmembercost = lianmengmembercost;
    }

    public int getThreeDayJuCount() {
        return threeDayJuCount;
    }

    public void setThreeDayJuCount(int threeDayJuCount) {
        this.threeDayJuCount = threeDayJuCount;
    }

    public int getSevenDayJuCount() {
        return sevenDayJuCount;
    }

    public void setSevenDayJuCount(int sevenDayJuCount) {
        this.sevenDayJuCount = sevenDayJuCount;
    }
}
