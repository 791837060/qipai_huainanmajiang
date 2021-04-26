package com.anbang.qipai.tuidaohu.cqrs.q.dbo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.TuiDaoHuPanPlayerResult;
import com.anbang.qipai.tuidaohu.cqrs.c.domain.TuiDaoHuPanResult;
import com.dml.majiang.pan.frame.PanActionFrame;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({@CompoundIndex(name = "gameId_1_panNo_1", def = "{'gameId': 1, 'panNo': 1}")})
public class PanResultDbo {
    private String id;
    private String gameId;
    private int panNo;
    private String zhuangPlayerId;
    private boolean hu;
    private boolean zimo;
    private String dianpaoPlayerId;
    private List<TuiDaoHuPanPlayerResultDbo> playerResultList;
    private long finishTime;
    private PanActionFrame panActionFrame;

    public PanResultDbo() {
    }

    public PanResultDbo(String gameId, TuiDaoHuPanResult tuidaohuPanResult) {
        this.gameId = gameId;
        panNo = tuidaohuPanResult.getPan().getNo();
        zhuangPlayerId = tuidaohuPanResult.findZhuangPlayerId();
        hu = tuidaohuPanResult.isHu();
        zimo = tuidaohuPanResult.isZimo();
        dianpaoPlayerId = tuidaohuPanResult.getDianpaoPlayerId();
        playerResultList = new ArrayList<>();
        for (TuiDaoHuPanPlayerResult playerResult : tuidaohuPanResult.getPanPlayerResultList()) {
            TuiDaoHuPanPlayerResultDbo dbo = new TuiDaoHuPanPlayerResultDbo();
            dbo.setPlayerId(playerResult.getPlayerId());
            dbo.setPlayerResult(playerResult);
            dbo.setPlayer(tuidaohuPanResult.findPlayer(playerResult.getPlayerId()));
            playerResultList.add(dbo);
        }
        finishTime = tuidaohuPanResult.getPanFinishTime();
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

    public String getZhuangPlayerId() {
        return zhuangPlayerId;
    }

    public void setZhuangPlayerId(String zhuangPlayerId) {
        this.zhuangPlayerId = zhuangPlayerId;
    }

    public boolean isHu() {
        return hu;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }

    public boolean isZimo() {
        return zimo;
    }

    public void setZimo(boolean zimo) {
        this.zimo = zimo;
    }

    public String getDianpaoPlayerId() {
        return dianpaoPlayerId;
    }

    public void setDianpaoPlayerId(String dianpaoPlayerId) {
        this.dianpaoPlayerId = dianpaoPlayerId;
    }

    public List<TuiDaoHuPanPlayerResultDbo> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<TuiDaoHuPanPlayerResultDbo> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public PanActionFrame getPanActionFrame() {
        return panActionFrame;
    }

    public void setPanActionFrame(PanActionFrame panActionFrame) {
        this.panActionFrame = panActionFrame;
    }

}
