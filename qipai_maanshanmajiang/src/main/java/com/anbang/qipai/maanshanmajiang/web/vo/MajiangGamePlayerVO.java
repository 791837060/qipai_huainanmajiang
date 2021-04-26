package com.anbang.qipai.maanshanmajiang.web.vo;

import com.anbang.qipai.maanshanmajiang.cqrs.c.domain.dingque.*;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGameDbo;
import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerPanFinishedAndVoted;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerPanFinishedAndVoting;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerReadyToStartNextPanAndVoted;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerReadyToStartNextPanAndVoting;
import com.dml.mpgame.game.extend.multipan.player.PlayerPanFinished;
import com.dml.mpgame.game.extend.multipan.player.PlayerReadyToStartNextPan;
import com.dml.mpgame.game.extend.vote.player.PlayerPlayingAndVoted;
import com.dml.mpgame.game.extend.vote.player.PlayerPlayingAndVoting;
import com.dml.mpgame.game.player.PlayerFinished;
import com.dml.mpgame.game.player.PlayerJoined;
import com.dml.mpgame.game.player.PlayerPlaying;
import com.dml.mpgame.game.player.PlayerReadyToStart;

import java.util.ArrayList;
import java.util.List;

public class MajiangGamePlayerVO {
    private String playerId;
    private String nickname;
    private String gender;// 会员性别:男:male,女:female
    private String headimgurl;
    private String state;
    private String onlineState;
    private Double totalScore;
    private boolean deposit;//托管状态
    /**
     * 玩家每倒分数
     */
    private List<Double> playerDaoScore;
    /**
     * 玩家体外循环分数
     */
    private Double playerTiwaixunhuanScore;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public MajiangGamePlayerVO(MajiangGamePlayerDbo dbo, MajiangGameDbo majiangGameDbo) {
        playerId = dbo.getPlayerId();
        nickname = dbo.getNickname();
        gender = dbo.getGender();
        headimgurl = dbo.getHeadimgurl();
        onlineState = dbo.getOnlineState().name();
        totalScore = dbo.getTotalScore();
        String sn = dbo.getState().name();
        if (sn.equals(PlayerFinished.name)) {
            state = "finished";
        } else if (sn.equals(PlayerJoined.name)) {
            state = "joined";
        } else if (sn.equals(PlayerPanFinished.name)) {
            state = "panFinished";
        } else if (sn.equals(PlayerPlaying.name)) {
            state = "playing";
        } else if (sn.equals(PlayerReadyToStart.name)) {
            state = "readyToStart";
        } else if (sn.equals(PlayerReadyToStartNextPan.name)) {
            state = "readyToStart";
        } else if (sn.equals(PlayerPlayingAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerPlayingAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerPanFinishedAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerPanFinishedAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerReadyToStartNextPanAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerReadyToStartNextPanAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerDingque.name)) {
            state = "dingque";
        } else if (sn.equals(PlayerAfterDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenAfterDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenAfterDingque.name)) {
            state = sn;
        } else {
        }
        playerDaoScore = majiangGameDbo.getPlayeTotalDaoScoreMap().get(dbo.getPlayerId());
        if (playerDaoScore == null) {
            List<Double> list = new ArrayList<>();
            switch (majiangGameDbo.getOptionalPlay().getDaozi()) {
                case 1:
                    list.add(50d);
                    break;
                case 2:
                    list.add(50d);
                    list.add(50d);
                    break;
                case 3:
                    list.add(50d);
                    list.add(50d);
                    list.add(50d);
                    break;
            }
            playerDaoScore = list;
        }
        if (majiangGameDbo.getPlayerTiwaixunhuanScoreMap() == null) {
            playerTiwaixunhuanScore = 0d;
        } else {
            playerTiwaixunhuanScore = majiangGameDbo.getPlayerTiwaixunhuanScoreMap().get(dbo.getPlayerId());
        }
    }

    public MajiangGamePlayerVO(MajiangGamePlayerDbo dbo) {
        playerId = dbo.getPlayerId();
        nickname = dbo.getNickname();
        gender = dbo.getGender();
        headimgurl = dbo.getHeadimgurl();
        onlineState = dbo.getOnlineState().name();
        totalScore = dbo.getTotalScore();
        String sn = dbo.getState().name();
        if (sn.equals(PlayerFinished.name)) {
            state = "finished";
        } else if (sn.equals(PlayerJoined.name)) {
            state = "joined";
        } else if (sn.equals(PlayerPanFinished.name)) {
            state = "panFinished";
        } else if (sn.equals(PlayerPlaying.name)) {
            state = "playing";
        } else if (sn.equals(PlayerReadyToStart.name)) {
            state = "readyToStart";
        } else if (sn.equals(PlayerReadyToStartNextPan.name)) {
            state = "readyToStart";
        } else if (sn.equals(PlayerPlayingAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerPlayingAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerPanFinishedAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerPanFinishedAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerReadyToStartNextPanAndVoted.name)) {
            state = sn;
        } else if (sn.equals(PlayerReadyToStartNextPanAndVoting.name)) {
            state = sn;
        } else if (sn.equals(PlayerDingque.name)) {
            state = "dingque";
        } else if (sn.equals(PlayerAfterDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenAfterDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenDingque.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenAfterDingque.name)) {
            state = sn;
        } else {
        }
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

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isDeposit() {
        return deposit;
    }

    public void setDeposit(boolean deposit) {
        this.deposit = deposit;
    }

    public List<Double> getPlayerDaoScore() {
        return playerDaoScore;
    }

    public void setPlayerDaoScore(List<Double> playerDaoScore) {
        this.playerDaoScore = playerDaoScore;
    }

    public Double getPlayerTiwaixunhuanScore() {
        return playerTiwaixunhuanScore;
    }

    public void setPlayerTiwaixunhuanScore(Double playerTiwaixunhuanScore) {
        this.playerTiwaixunhuanScore = playerTiwaixunhuanScore;
    }
}
