package com.anbang.qipai.maanshanmajiang.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.MaanshanMajiangJuResult;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;

public class JuResultVO {

    private String gameId;
    private String dayingjiaId;
    private String datuhaoId;
    private int panshu;
    private int finishedPanCount;
    private List<JuPlayerResultVO> playerResultList;

    private PanResultVO lastPanResult;
    private long finishTime;

    public JuResultVO(JuResultDbo juResultDbo, MajiangGameDbo majiangGameDbo) {
        gameId = juResultDbo.getGameId();
        MaanshanMajiangJuResult maanshanMajiangJuResult = juResultDbo.getJuResult();
        dayingjiaId = maanshanMajiangJuResult.getDayingjiaId();
        datuhaoId = maanshanMajiangJuResult.getDatuhaoId();
        if (juResultDbo.getLastPanResult() != null) {
            lastPanResult = new PanResultVO(juResultDbo.getLastPanResult(), majiangGameDbo);
        }
        finishTime = juResultDbo.getFinishTime();
        this.panshu = majiangGameDbo.getPanshu();
        finishedPanCount = maanshanMajiangJuResult.getFinishedPanCount();
        playerResultList = new ArrayList<>();
        if (maanshanMajiangJuResult.getPlayerResultList() != null) {
            maanshanMajiangJuResult.getPlayerResultList().forEach((juPlayerResult) -> playerResultList.add(new JuPlayerResultVO(juPlayerResult, majiangGameDbo.findPlayer(juPlayerResult.getPlayerId()), majiangGameDbo)));
        } else {
            majiangGameDbo.getPlayers().forEach((majiangGamePlayerDbo) -> playerResultList.add(new JuPlayerResultVO(majiangGamePlayerDbo)));
        }
    }

    public String getDayingjiaId() {
        return dayingjiaId;
    }

    public void setDayingjiaId(String dayingjiaId) {
        this.dayingjiaId = dayingjiaId;
    }

    public String getDatuhaoId() {
        return datuhaoId;
    }

    public void setDatuhaoId(String datuhaoId) {
        this.datuhaoId = datuhaoId;
    }

    public List<JuPlayerResultVO> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<JuPlayerResultVO> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public PanResultVO getLastPanResult() {
        return lastPanResult;
    }

    public void setLastPanResult(PanResultVO lastPanResult) {
        this.lastPanResult = lastPanResult;
    }

    public int getPanshu() {
        return panshu;
    }

    public void setPanshu(int panshu) {
        this.panshu = panshu;
    }

    public int getFinishedPanCount() {
        return finishedPanCount;
    }

    public void setFinishedPanCount(int finishedPanCount) {
        this.finishedPanCount = finishedPanCount;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

}
