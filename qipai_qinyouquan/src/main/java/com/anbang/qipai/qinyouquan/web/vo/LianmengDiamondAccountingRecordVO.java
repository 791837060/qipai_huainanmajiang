package com.anbang.qipai.qinyouquan.web.vo;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.LianmengDiamondAccountingRecord;
import com.anbang.qipai.qinyouquan.plan.bean.game.Game;
import com.dml.accounting.AccountingSummary;

/**
 * 联盟玉石充值记录
 */
public class LianmengDiamondAccountingRecordVO {
    private String id;
    private String accountId;           //账户id
    private String memberId;             //推广员id
    private String nickname;
    private Game game;                  //游戏
    private String wanfaName;           //玩法名称
    private String referer;//上级id
    private String gameRoomNo;
    private int no;                     //流水号
    private int accountAmount;          //交易额
    private String lianmengId;          //联盟id
    private int balance;                //余额
    private AccountingSummary summary;  //摘要
    private long accountingTime;        //交易时间

    public LianmengDiamondAccountingRecordVO(LianmengDiamondAccountingRecord lianmengDiamondAccountingRecord) {
        this.id = lianmengDiamondAccountingRecord.getId();
        this.accountId = lianmengDiamondAccountingRecord.getAccountId();
        this.memberId = lianmengDiamondAccountingRecord.getMengzhuId();
        if (lianmengDiamondAccountingRecord.getGame()!=null) {
            this.game = lianmengDiamondAccountingRecord.getGame();
            this.wanfaName = lianmengDiamondAccountingRecord.getWanfaName();
            this.gameRoomNo = lianmengDiamondAccountingRecord.getGameRoomNo();
        }
        this.referer = lianmengDiamondAccountingRecord.getReferer();
        this.no = lianmengDiamondAccountingRecord.getNo();
        this.accountAmount = lianmengDiamondAccountingRecord.getAccountAmount();
        this.lianmengId = lianmengDiamondAccountingRecord.getLianmengId();
        this.balance = lianmengDiamondAccountingRecord.getBalance();
        this.summary = lianmengDiamondAccountingRecord.getSummary();
        this.accountingTime = lianmengDiamondAccountingRecord.getAccountingTime();
    }

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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(int accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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

    public String getGameRoomNo() {
        return gameRoomNo;
    }

    public void setGameRoomNo(String gameRoomNo) {
        this.gameRoomNo = gameRoomNo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
