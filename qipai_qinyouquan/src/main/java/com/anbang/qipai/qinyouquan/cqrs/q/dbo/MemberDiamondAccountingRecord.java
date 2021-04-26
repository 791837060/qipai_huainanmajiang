package com.anbang.qipai.qinyouquan.cqrs.q.dbo;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.dml.accounting.AccountingSummary;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/11 11:18
 */
public class MemberDiamondAccountingRecord {
    private String id;
    private String accountId;//账户id
    private String memberId;//玩家
    private String lianmengId;//联盟id
    private String referer;//上级id
    private Game game;                  //游戏
    private String wanfaName;           //玩法名称
    private String gameRoomNo;
    private int no;//流水号
    private double accountAmount;//交易额
    private double balance;//余额
    private AccountingSummary summary;//摘要
    private long accountingTime;//交易时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountingSummary getSummary() {
        return summary;
    }

    public void setSummary(AccountingSummary summary) {
        this.summary = summary;
    }

    public long getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(long accountingTime) {
        this.accountingTime = accountingTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getWanfaName() {
        return wanfaName;
    }

    public void setWanfaName(String wanfaName) {
        this.wanfaName = wanfaName;
    }

    public String getGameRoomNo() {
        return gameRoomNo;
    }

    public void setGameRoomNo(String gameRoomNo) {
        this.gameRoomNo = gameRoomNo;
    }
}
