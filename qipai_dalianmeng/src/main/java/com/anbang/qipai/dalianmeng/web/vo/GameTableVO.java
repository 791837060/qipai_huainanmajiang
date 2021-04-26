package com.anbang.qipai.dalianmeng.web.vo;

import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameLaw;

import java.util.List;

public class GameTableVO {
    private String no;// 房间6位编号,可循环使用
    private String lianmengId;// 联盟id
    private Game game;
    private List<GameLaw> laws;
    private int currentPanNum;//当前盘数
    private int panCountPerJu;//每局盘数
    private int playersCount;//总人数
    private String state;// 状态
    private List<PlayerVO> playerList;
    private String tableName;
    private long createTime;
    private String wanfaId;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLianmengId() {
        return lianmengId;
    }

    public void setLianmengId(String lianmengId) {
        this.lianmengId = lianmengId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<GameLaw> getLaws() {
        return laws;
    }

    public void setLaws(List<GameLaw> laws) {
        this.laws = laws;
    }

    public int getCurrentPanNum() {
        return currentPanNum;
    }

    public void setCurrentPanNum(int currentPanNum) {
        this.currentPanNum = currentPanNum;
    }

    public int getPanCountPerJu() {
        return panCountPerJu;
    }

    public void setPanCountPerJu(int panCountPerJu) {
        this.panCountPerJu = panCountPerJu;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PlayerVO> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerVO> playerList) {
        this.playerList = playerList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getWanfaId() {
        return wanfaId;
    }

    public void setWanfaId(String wanfaId) {
        this.wanfaId = wanfaId;
    }
}
