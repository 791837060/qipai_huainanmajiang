package com.anbang.qipai.doudizhu.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.Qiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.VoteNotPassWhenQiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.VotingWhenQiangdizhu;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.dml.mpgame.game.Canceled;
import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.fpmpv.VoteNotPassWhenWaitingNextPan;
import com.dml.mpgame.game.extend.fpmpv.VotingWhenWaitingNextPan;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByVote;
import com.dml.mpgame.game.extend.vote.VoteNotPassWhenPlaying;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;

import javax.crypto.spec.OAEPParameterSpec;

public class GameVO {
	private String id;// 就是gameid
	private int panshu;
	private int renshu;
	private double difen;
	private OptionalPlay optionalPlay;
	private int panNo;
	private List<PukeGamePlayerVO> playerList;
	private String state;

	public GameVO() {

	}

	public GameVO(PukeGameDbo pukeGameDbo) {
		id = pukeGameDbo.getId();
		difen=pukeGameDbo.getDifen();
		panshu = pukeGameDbo.getPanshu();
		renshu = pukeGameDbo.getRenshu();
		optionalPlay = pukeGameDbo.getOptionalPlay();
		difen = pukeGameDbo.getDifen();
		playerList = new ArrayList<>();
		pukeGameDbo.getPlayers().forEach((dbo) -> playerList.add(new PukeGamePlayerVO(dbo)));
		panNo = pukeGameDbo.getPanNo();
		String sn = pukeGameDbo.getState().name();
		if (sn.equals(Canceled.name)) {
			state = "canceled";
		} else if (sn.equals(Finished.name)) {
			state = "finished";
		} else if (sn.equals(FinishedByVote.name)) {
			state = "finishedbyvote";
		} else if (sn.equals(Playing.name)) {
			state = "playing";
		} else if (sn.equals(VotingWhenPlaying.name)) {
			state = "playing";
		} else if (sn.equals(VoteNotPassWhenPlaying.name)) {
			state = "playing";
		} else if (sn.equals(Qiangdizhu.name)) {
			state = "qiangdizhu";
		} else if (sn.equals(VotingWhenQiangdizhu.name)) {
			state = "qiangdizhu";
		} else if (sn.equals(VoteNotPassWhenQiangdizhu.name)) {
			state = "qiangdizhu";
		} else if (sn.equals(VotingWhenWaitingNextPan.name)) {
			state = "waitingNextPan";
		} else if (sn.equals(VoteNotPassWhenWaitingNextPan.name)) {
			state = "waitingNextPan";
		} else if (sn.equals(WaitingNextPan.name)) {
			state = "waitingNextPan";
		} else if (sn.equals(WaitingStart.name)) {
			state = "waitingStart";
		} else {
		}
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

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}


    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public List<PukeGamePlayerVO> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<PukeGamePlayerVO> playerList) {
		this.playerList = playerList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
