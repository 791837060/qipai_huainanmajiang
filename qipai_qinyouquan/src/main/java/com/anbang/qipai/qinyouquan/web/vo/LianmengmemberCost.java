package com.anbang.qipai.qinyouquan.web.vo;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;

public class LianmengmemberCost {

    private int diamondCosts;       //单个游戏消耗砖石
    private int amount;             //单个游戏总局数
    private Game game;              //游戏名称


    public int getDiamondCosts() {
        return diamondCosts;
    }

    public void setDiamondCosts(int diamondCosts) {
        this.diamondCosts = diamondCosts;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "LianmengmemberCost{" +
                "diamondCosts=" + diamondCosts +
                ", amount=" + amount +
                ", game=" + game +
                '}';
    }
}
