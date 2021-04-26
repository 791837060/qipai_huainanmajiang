package com.anbang.qipai.biji.cqrs.c.domain.result;

import com.dml.shisanshui.pai.paixing.Paixing;

/**
 * 对某个玩家的结算结果
 *
 * @author lsc
 */
public class PlayerJiesuanScore {
    private String playerId;            //玩家ID
    private int toudao;                 //头道基础分
    private Paixing toudaoPaixing;      //头道牌型
    private int zhongdao;               //中道基础分
    private Paixing zhongdaoPaixing;    //中道牌型
    private int weidao;                 //尾道基础分
    private Paixing weidaoPaixing;      //尾道牌型
    private int score;                  //基础结算分
    private int value;                  //总结算分

    /**
     * 若尾道牌型和点数都相同,则比较中道大小,中道大的玩家两道牌都算大;
     * 如果尾道和中道牌都相同大小,则比较头道大小,如果头道大,则中道尾道都算大.
     * 如果是中道牌型点数都相同,则比较尾道大小,尾道大的玩家,中道尾道都算大.
     */
    public void calculateScore() {
        boolean ying = weidao > 0 || weidao + zhongdao + toudao > 0;
        if (ying) {
            if (weidao == 0) {
                weidao = 1;
            }
            if (zhongdao == 0) {
                zhongdao = 1;
            }
            if (toudao == 0) {
                toudao = 1;
            }
        } else {
            if (weidao == 0) {
                weidao = -1;
            }
            if (zhongdao == 0) {
                zhongdao = -1;
            }
            if (toudao == 0) {
                toudao = -1;
            }
        }
        score = toudao + zhongdao + weidao;
    }


    /**
     * 是否打枪
     */
    public boolean isDaqiang() {
        return toudao > 0 && zhongdao > 0 && weidao > 0;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getToudao() {
        return toudao;
    }

    public void setToudao(int value) {
        this.toudao += value;
    }

    public int getZhongdao() {
        return zhongdao;
    }

    public void setZhongdao(int value) {
        this.zhongdao += value;
    }

    public int getWeidao() {
        return weidao;
    }

    public void setWeidao(int value) {
        this.weidao += value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Paixing getToudaoPaixing() {
        return toudaoPaixing;
    }

    public void setToudaoPaixing(Paixing toudaoPaixing) {
        this.toudaoPaixing = toudaoPaixing;
    }

    public Paixing getZhongdaoPaixing() {
        return zhongdaoPaixing;
    }

    public void setZhongdaoPaixing(Paixing zhongdaoPaixing) {
        this.zhongdaoPaixing = zhongdaoPaixing;
    }

    public Paixing getWeidaoPaixing() {
        return weidaoPaixing;
    }

    public void setWeidaoPaixing(Paixing weidaoPaixing) {
        this.weidaoPaixing = weidaoPaixing;
    }

}
