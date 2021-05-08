package com.anbang.qipai.shouxianmajiang.web.vo;

import com.anbang.qipai.shouxianmajiang.cqrs.c.domain.piao.*;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGamePlayerDbo;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.MajiangGamePlayerXiapiaoDbo;
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

public class MajiangGamePlayerVO {
	private String playerId;
	private String nickname;
	private String gender;// 会员性别:男:male,女:female
	private String headimgurl;
	private String state;
    private MajiangPlayerXiapiaoState playerXiapiaoState ;
    private Integer playerpiaofen;
	private String onlineState;
	private Double totalScore;
    private boolean deposit;//托管

    public MajiangGamePlayerVO(MajiangGamePlayerDbo dbo, MajiangGamePlayerXiapiaoDbo majiangGamePlayerXiapiaoDbo) {
        playerId = dbo.getPlayerId();
        nickname = dbo.getNickname();
        gender = dbo.getGender();
        headimgurl = dbo.getHeadimgurl();
        onlineState = dbo.getOnlineState().name();
        totalScore = dbo.getTotalScore();
        playerXiapiaoState=majiangGamePlayerXiapiaoDbo.getPlayerXiapiaoStateMap().get(dbo.getPlayerId());
        playerpiaofen=majiangGamePlayerXiapiaoDbo.getPlayerpiaofenMap().get(dbo.getPlayerId());
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
        } else if (sn.equals(PlayerXiapiao.name)) {
            state = "xiapiao";
        } else if (sn.equals(PlayerAfterXiapiao.name)) {
            state = "xiapiao";
        } else if (sn.equals(PlayerVotedWhenXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenAfterXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenAfterXiapiao.name)) {
            state = sn;
        } else {
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
        } else if (sn.equals(PlayerXiapiao.name)) {
            state = "xiapiao";
        } else if (sn.equals(PlayerAfterXiapiao.name)) {
            state = "xiapiao";
        } else if (sn.equals(PlayerVotedWhenXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotedWhenAfterXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenXiapiao.name)) {
            state = sn;
        } else if (sn.equals(PlayerVotingWhenAfterXiapiao.name)) {
            state = sn;
        } else {
        }

    }
    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

    public MajiangPlayerXiapiaoState getPlayerXiapiaoState() {
        return playerXiapiaoState;
    }

    public void setPlayerXiapiaoState(MajiangPlayerXiapiaoState playerXiapiaoState) {
        this.playerXiapiaoState = playerXiapiaoState;
    }

    public Integer getPlayerpiaofen() {
        return playerpiaofen;
    }

    public void setPlayerpiaofen(Integer playerpiaofen) {
        this.playerpiaofen = playerpiaofen;
    }
}
