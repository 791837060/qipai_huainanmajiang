package com.anbang.qipai.dalianmeng.plan.bean;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;

import java.util.HashMap;
import java.util.Map;

public class LianmengDiamondDayCost {
    private String id;
    private String lianmengId;
    private int cost;
    private long createTime;
    private Map<Game,GameCountAndCost> gameCost;

    public LianmengDiamondDayCost() {
        Map<Game,GameCountAndCost> gameIntegerMap=new HashMap<>();
        for (Game game: Game.values()) {
            GameCountAndCost gameCountAndCost = new GameCountAndCost();
            gameIntegerMap.put(game,gameCountAndCost);
        }
        this.gameCost=gameIntegerMap;
    }

    public LianmengDiamondDayCost(String id, String lianmengId, int cost, long createTime, Map<Game, GameCountAndCost> gameCost) {
        this.id = id;
        this.lianmengId = lianmengId;
        this.cost = cost;
        this.createTime = createTime;
        this.gameCost = gameCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Map<Game, GameCountAndCost> getGameCost() {
        return gameCost;
    }

    public void setGameCost(Map<Game, GameCountAndCost> gameCost) {
        this.gameCost = gameCost;
    }
}
