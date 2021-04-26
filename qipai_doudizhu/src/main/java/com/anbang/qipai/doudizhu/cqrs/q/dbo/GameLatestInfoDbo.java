package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.puke.pai.PukePai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLatestInfoDbo {
    private String id;
    private int panNo;
    private DoudizhuBeishu beishu;
    private int qiangdizhuCount;
    private List<PukePai> dipaiList;
    private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;
    private long createTime;

    public GameLatestInfoDbo() {

    }

    public GameLatestInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerQiangdizhuState> playerQiangdizhuMap,
                             Map<String,Integer> playerJiaofenMap,
                             GameInfo gameInfo) {
        id = pukeGame.getId();
        panNo = pukeGame.getPanNo();
        beishu = gameInfo.getBeishu();
        qiangdizhuCount = gameInfo.getQiangdizhuCount();
        dipaiList = gameInfo.getDipaiList();
        playerQiangdizhuInfos = new ArrayList<>();
        for (String playerId : playerQiangdizhuMap.keySet()) {
            PlayerQiangdizhuInfoDbo player = new PlayerQiangdizhuInfoDbo();
            player.setPlayerId(playerId);
            player.setState(playerQiangdizhuMap.get(playerId));
            playerQiangdizhuInfos.add(player);
        }
        if (!playerJiaofenMap.isEmpty()) {
            for (PlayerQiangdizhuInfoDbo playerQiangdizhuInfo : playerQiangdizhuInfos) {
                for (String pllayerId : playerJiaofenMap.keySet()) {
                    if (playerQiangdizhuInfo.getPlayerId().equals(pllayerId)){
                        playerQiangdizhuInfo.setScore(playerJiaofenMap.get(pllayerId));
                    }
                }
            }
        }
        createTime = System.currentTimeMillis();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPanNo() {
        return panNo;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public int getQiangdizhuCount() {
        return qiangdizhuCount;
    }

    public void setQiangdizhuCount(int qiangdizhuCount) {
        this.qiangdizhuCount = qiangdizhuCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DoudizhuBeishu getBeishu() {
        return beishu;
    }

    public void setBeishu(DoudizhuBeishu beishu) {
        this.beishu = beishu;
    }

    public List<PukePai> getDipaiList() {
        return dipaiList;
    }

    public void setDipaiList(List<PukePai> dipaiList) {
        this.dipaiList = dipaiList;
    }

    public List<PlayerQiangdizhuInfoDbo> getPlayerQiangdizhuInfos() {
        return playerQiangdizhuInfos;
    }

    public void setPlayerQiangdizhuInfos(List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos) {
        this.playerQiangdizhuInfos = playerQiangdizhuInfos;
    }

}
