package com.anbang.qipai.tuidaohu.web.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.tuidaohu.cqrs.q.dbo.MajiangGameDbo;
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

public class GameVO {
    private String id;// 就是gameid
    private int panshu;
    private int renshu;
    private Double difen;
    private int panNo;
    private OptionalPlay optionalPlay;   //麻将可选玩法
    private List<MajiangGamePlayerVO> playerList;
    private Set<String> xipaiPlayerIds;
    private String state;// 原来是 waitingStart, playing, waitingNextPan, finished
    private List<String> tuoguanPlayerIds=new ArrayList<>();

    public GameVO(MajiangGameDbo majiangGameDbo) {
        id = majiangGameDbo.getId();
        panshu = majiangGameDbo.getPanshu();
        renshu = majiangGameDbo.getRenshu();
        difen=majiangGameDbo.getDifen();
        panNo=majiangGameDbo.getPanNo();
        xipaiPlayerIds = majiangGameDbo.getXipaiPlayerIds();
        optionalPlay=majiangGameDbo.getOptionalPlay();
        playerList = new ArrayList<>();
        majiangGameDbo.getPlayers().forEach((dbo) -> playerList.add(new MajiangGamePlayerVO(dbo)));
        String sn = majiangGameDbo.getState().name();
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

    public Set<String> getXipaiPlayerIds() {
        return xipaiPlayerIds;
    }

    public void setXipaiPlayerIds(Set<String> xipaiPlayerIds) {
        this.xipaiPlayerIds = xipaiPlayerIds;
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


    public List<MajiangGamePlayerVO> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<MajiangGamePlayerVO> playerList) {
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

    public Double getDifen() {
        return difen;
    }

    public void setDifen(Double difen) {
        this.difen = difen;
    }

    public int getPanNo() {
        return panNo;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public List<String> getTuoguanPlayerIds() {
        return tuoguanPlayerIds;
    }

    public void setTuoguanPlayerIds(List<String> tuoguanPlayerIds) {
        this.tuoguanPlayerIds = tuoguanPlayerIds;
    }
}
