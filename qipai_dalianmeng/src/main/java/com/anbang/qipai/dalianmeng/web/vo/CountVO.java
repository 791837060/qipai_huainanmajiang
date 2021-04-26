package com.anbang.qipai.dalianmeng.web.vo;

public class CountVO {
    private int yushiCost_day;//今日玉石消耗
    private int yushiCost_ysday;//昨日玉石消耗
    private int yushiCost_month;//本月玉石消耗
    private int totalCost;//总消耗
    private int balance;//剩余玉石

    public int getYushiCost_day() {
        return yushiCost_day;
    }

    public void setYushiCost_day(int yushiCost_day) {
        this.yushiCost_day = yushiCost_day;
    }

    public int getYushiCost_ysday() {
        return yushiCost_ysday;
    }

    public void setYushiCost_ysday(int yushiCost_ysday) {
        this.yushiCost_ysday = yushiCost_ysday;
    }

    public int getYushiCost_month() {
        return yushiCost_month;
    }

    public void setYushiCost_month(int yushiCost_month) {
        this.yushiCost_month = yushiCost_month;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
