package com.anbang.qipai.shouxianmajiang.cqrs.q.dbo;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.ShouxianMajiangJuResult;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({@CompoundIndex(name = "gameId_1", def = "{'gameId': 1}")})
public class JuResultDbo {

    private String id;
    private String gameId;
    private PanResultDbo lastPanResult;
    private ShouxianMajiangJuResult juResult;
    private long finishTime;

    public JuResultDbo() {
    }

    public JuResultDbo(String gameId, PanResultDbo lastPanResult, ShouxianMajiangJuResult juResult) {
        this.gameId = gameId;
        this.lastPanResult = lastPanResult;
        this.juResult = juResult;
        finishTime = System.currentTimeMillis();
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

    public PanResultDbo getLastPanResult() {
        return lastPanResult;
    }

    public void setLastPanResult(PanResultDbo lastPanResult) {
        this.lastPanResult = lastPanResult;
    }

    public ShouxianMajiangJuResult getJuResult() {
        return juResult;
    }

    public void setJuResult(ShouxianMajiangJuResult juResult) {
        this.juResult = juResult;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

}
