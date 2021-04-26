package com.anbang.qipai.qinyouquan.plan.bean.result;

import com.anbang.qipai.qinyouquan.plan.bean.game.Game;

import java.util.List;

public class GameHistoricalJuResult {
    private String id;
    private String gameId;
    private Game game;
    private String roomNo;
    private String dayingjiaId;
    private String datuhaoId;
    private String lianmengId;
    private List<GameJuPlayerResult> playerResultList;
    private int lastPanNo;
    private int panshu;
    private long finishTime;
    private boolean finish=false;
    private String payType;                         //支付类型
    private String superiorMemberId=null;
    private int diamondCost;

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
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

    public List<GameJuPlayerResult> getPlayerResultList() {
        return playerResultList;
    }

    public void setPlayerResultList(List<GameJuPlayerResult> playerResultList) {
        this.playerResultList = playerResultList;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public int getLastPanNo() {
        return lastPanNo;
    }

    public void setLastPanNo(int lastPanNo) {
        this.lastPanNo = lastPanNo;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPanshu() {
        return panshu;
    }

    public void setPanshu(int panshu) {
        this.panshu = panshu;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSuperiorMemberId() {
        return superiorMemberId;
    }

    public void setSuperiorMemberId(String superiorMemberId) {
        this.superiorMemberId = superiorMemberId;
    }

    public int getDiamondCost() {
        return diamondCost;
    }

    public void setDiamondCost(int diamondCost) {
        this.diamondCost = diamondCost;
    }
}
