package com.anbang.qipai.maanshanmajiang.msg.msjobj;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MaanshanMajiangPanPlayerResultDbo;

public class PanPlayerResultMO {
    private String playerId;// 玩家id
    private String nickname;// 玩家昵称
    private Double score;// 一盘总分
    private boolean hu;

    public PanPlayerResultMO(MajiangGamePlayerDbo gamePlayerDbo, MaanshanMajiangPanPlayerResultDbo panPlayerResult) {
        playerId = gamePlayerDbo.getPlayerId();
        nickname = gamePlayerDbo.getNickname();
        score = panPlayerResult.getPlayerResult().getScore();
        hu = panPlayerResult.getPlayer().getHu() != null;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public boolean isHu() {
        return hu;
    }

    public void setHu(boolean hu) {
        this.hu = hu;
    }

}
