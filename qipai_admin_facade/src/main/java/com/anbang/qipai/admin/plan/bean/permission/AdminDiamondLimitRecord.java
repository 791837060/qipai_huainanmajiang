package com.anbang.qipai.admin.plan.bean.permission;

public class AdminDiamondLimitRecord {
    private String id;
    private Admin admin;            //管理员
    private int diamondAmount;      //钻石流水(修改的数量)
    private int balance;            //充值对象充值以后砖石余额
    private String nickName;        //被充值对象用户名
    private long accountingTime;    //创建时间
    private String adminId;

    private String memberId;        //盟主ID
    private String userId;          //用户Id

    private int jadeAmount;         //玉石流水(修改的数量)
    private int jadeBalace;         //充值对象充值以后玉石余额

    public AdminDiamondLimitRecord(String id, Admin admin, int diamondAmount, int balance, String nickName, long accountingTime, String adminId, String memberId,int jadeAmount) {
        this.id = id;
        this.admin = admin;
        this.diamondAmount = diamondAmount;
        this.balance = balance;
        this.nickName = nickName;
        this.accountingTime = accountingTime;
        this.adminId = adminId;
        this.memberId = memberId;
        this.jadeAmount = jadeAmount;
    }

    public AdminDiamondLimitRecord() {
    }

    public AdminDiamondLimitRecord(Integer diamondLimit, int balance, String nickName, long accountingTime, String adminId) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public int getDiamondAmount() {
        return diamondAmount;
    }

    public void setDiamondAmount(int diamondAmount) {
        this.diamondAmount = diamondAmount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getAccountingTime() {
        return accountingTime;
    }

    public void setAccountingTime(long accountingTime) {
        this.accountingTime = accountingTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getJadeAmount() {
        return jadeAmount;
    }

    public void setJadeAmount(int jadeAmount) {
        this.jadeAmount = jadeAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getJadeBalace() {
        return jadeBalace;
    }

    public void setJadeBalace(int jadeBalace) {
        this.jadeBalace = jadeBalace;
    }
}
