package com.anbang.qipai.guandan.cqrs.c.domain;

public class GuandanMingcifen {
    private int mingci;
    private boolean ying;
    private boolean shuangkou;
    private boolean dankou;
    private boolean pingkou;
    private int value;

    public void calculate() {
        int score = 0;
        int beishu = ying ? 1 : -1;
        if (shuangkou) {
            score = 30;
        } else if (dankou) {
            score = 20;
        } else if (pingkou) {
            score = 10;
        }
        value = beishu * score;
    }

    public int getMingci() {
        return mingci;
    }

    public void setMingci(int mingci) {
        this.mingci = mingci;
    }

    public boolean isYing() {
        return ying;
    }

    public void setYing(boolean ying) {
        this.ying = ying;
    }

    public boolean isShuangkou() {
        return shuangkou;
    }

    public void setShuangkou(boolean shuangkou) {
        this.shuangkou = shuangkou;
    }

    public boolean isDankou() {
        return dankou;
    }

    public void setDankou(boolean dankou) {
        this.dankou = dankou;
    }

    public boolean isPingkou() {
        return pingkou;
    }

    public void setPingkou(boolean pingkou) {
        this.pingkou = pingkou;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
