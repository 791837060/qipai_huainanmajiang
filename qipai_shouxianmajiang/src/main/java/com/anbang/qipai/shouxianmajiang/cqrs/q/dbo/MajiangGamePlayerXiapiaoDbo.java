package com.anbang.qipai.shouxianmajiang.cqrs.q.dbo;


import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao.MajiangPlayerXiapiaoState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MajiangGamePlayerXiapiaoDbo {
    private String id;
    private String gameId;
    private int panNo;
    private Map<String, MajiangPlayerXiapiaoState> playerXiapiaoStateMap;
    private Map<String ,Integer> playerpiaofenMap;
    private long createTime;

    public MajiangGamePlayerXiapiaoDbo() {

    }

    public MajiangGamePlayerXiapiaoDbo(MajiangGameValueObject majiangGame) {
        this.gameId = majiangGame.getId();
        this.panNo = majiangGame.getPanNo();
        this.playerXiapiaoStateMap = majiangGame.getPlayerXiapiaoStateMap();
        this.playerpiaofenMap=majiangGame.getPlayerpiaofenMap();
        createTime = System.currentTimeMillis();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<String> allPlayerIds() {
        return new ArrayList<>(playerXiapiaoStateMap.keySet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPanNo() {
        return panNo;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public Map<String, MajiangPlayerXiapiaoState> getPlayerXiapiaoStateMap() {
        return playerXiapiaoStateMap;
    }

    public void setPlayerXiapiaoStateMap(Map<String, MajiangPlayerXiapiaoState> playerXiapiaoStateMap) {
        this.playerXiapiaoStateMap = playerXiapiaoStateMap;
    }

    public Map<String, Integer> getPlayerpiaofenMap() {
        return playerpiaofenMap;
    }

    public void setPlayerpiaofenMap(Map<String, Integer> playerpiaofenMap) {
        this.playerpiaofenMap = playerpiaofenMap;
    }
}
