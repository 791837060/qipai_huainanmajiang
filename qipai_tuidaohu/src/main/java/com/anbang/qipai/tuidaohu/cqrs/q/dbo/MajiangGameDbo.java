package com.anbang.qipai.tuidaohu.cqrs.q.dbo;

import com.anbang.qipai.tuidaohu.cqrs.c.domain.MajiangGameValueObject;
import com.anbang.qipai.tuidaohu.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.tuidaohu.plan.bean.PlayerInfo;
import com.dml.mpgame.game.GamePlayerValueObject;
import com.dml.mpgame.game.GameState;

import java.util.*;

public class MajiangGameDbo {
    private String id;// 就是gameid
    private int panshu;
    private int renshu;
    private OptionalPlay optionalPlay;   //麻将可选玩法
    private Double difen;
    private int panNo;
    private GameState state;// 原来是 waitingStart, playing, waitingNextPan, finished
    private List<MajiangGamePlayerDbo> players;
    private Set<String> xipaiPlayerIds;
    private long createTime;
    private int powerLimit;

    public MajiangGameDbo() {
    }

    public MajiangGameDbo(MajiangGameValueObject majiangGame, Map<String, PlayerInfo> playerInfoMap) {
        id = majiangGame.getId();
        panshu = majiangGame.getPanshu();
        renshu = majiangGame.getRenshu();
        optionalPlay = majiangGame.getOptionalPlay();
        difen = majiangGame.getDifen();
        panNo = majiangGame.getPanNo();
        powerLimit = majiangGame.getPowerLimit();
        state = majiangGame.getState();
        xipaiPlayerIds = new HashSet<>(majiangGame.getXipaiPlayerIds());
        players = new ArrayList<>();
        Map<String, Double> playeTotalScoreMap = majiangGame.getPlayeTotalScoreMap();
        for (GamePlayerValueObject playerValueObject : majiangGame.getPlayers()) {
            String playerId = playerValueObject.getId();
            PlayerInfo playerInfo = playerInfoMap.get(playerId);
            MajiangGamePlayerDbo playerDbo = new MajiangGamePlayerDbo();
            playerDbo.setHeadimgurl(playerInfo.getHeadimgurl());
            playerDbo.setNickname(playerInfo.getNickname());
            playerDbo.setGender(playerInfo.getGender());
            playerDbo.setOnlineState(playerValueObject.getOnlineState());
            playerDbo.setPlayerId(playerId);
            playerDbo.setState(playerValueObject.getState());
            if (playeTotalScoreMap.get(playerId) != null) {
                playerDbo.setTotalScore(playeTotalScoreMap.get(playerId));
            }else{
                playerDbo.setTotalScore(0d);
            }
            players.add(playerDbo);
        }
        createTime = System.currentTimeMillis();
    }

    public MajiangGamePlayerDbo findPlayer(String playerId) {
        for (MajiangGamePlayerDbo player : players) {
            if (player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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


    public int getPanNo() {
        return panNo;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public List<MajiangGamePlayerDbo> getPlayers() {
        return players;
    }

    public void setPlayers(List<MajiangGamePlayerDbo> players) {
        this.players = players;
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

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }
}
