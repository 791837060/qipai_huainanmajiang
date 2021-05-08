package com.anbang.qipai.shouxianmajiang.cqrs.c.domain;

import com.dml.majiang.ju.result.JuResult;

import java.util.List;

public class ShouxianMajiangJuResult implements JuResult {

    private int finishedPanCount;

    private List<ShouxianMajiangJuPlayerResult> playerResultList;

    private String dayingjiaId;

    private String datuhaoId;

    public int getFinishedPanCount() {
        return finishedPanCount;
    }

    public void setFinishedPanCount(int finishedPanCount) {
        this.finishedPanCount = finishedPanCount;
    }

    public List<ShouxianMajiangJuPlayerResult> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<ShouxianMajiangJuPlayerResult> playerResultList) {
        this.playerResultList = playerResultList;
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

}
