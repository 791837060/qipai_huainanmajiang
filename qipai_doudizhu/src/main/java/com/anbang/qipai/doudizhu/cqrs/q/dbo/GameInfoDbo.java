package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.DoudizhuBeishu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.GameInfo;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.puke.pai.PukePai;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document
@CompoundIndexes({@CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}")})
public class GameInfoDbo {
    private String id;
    private String gameId;
    private int panNo;
    private int actionNo;
    private long actionTime;
    private DoudizhuBeishu beishu;
    private int qiangdizhuCount;
    private List<PukePai> dipaiList;
    private List<PlayerQiangdizhuInfoDbo> playerQiangdizhuInfos;
    private long createTime;

    public GameInfoDbo() {

    }

    public GameInfoDbo(PukeGameValueObject pukeGame, Map<String, PlayerQiangdizhuState> playerQiangdizhuMap,
                       Map<String,Integer> playerJiaofenMap,
                       GameInfo gameInfo, int actionNo) {
        gameId = pukeGame.getId();
        panNo = pukeGame.getPanNo();
        this.actionNo = actionNo;
        actionTime = gameInfo.getActionTime();
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

    public int getActionNo() {
        return actionNo;
    }

    public void setActionNo(int actionNo) {
        this.actionNo = actionNo;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
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
