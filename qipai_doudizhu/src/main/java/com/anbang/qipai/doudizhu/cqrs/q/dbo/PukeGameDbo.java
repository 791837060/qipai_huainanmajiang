package com.anbang.qipai.doudizhu.cqrs.q.dbo;

import com.anbang.qipai.doudizhu.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;
import com.dml.mpgame.game.GamePlayerValueObject;
import com.dml.mpgame.game.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PukeGameDbo {
    private String id;
    private int panshu;
    private int renshu;
    private double difen;
    private GameState state;// 原来是 waitingStart, playing, waitingNextPan, finished
    private OptionalPlay optionalPlay;
    private int panNo;
    private List<PukeGamePlayerDbo> players;
    private long createTime;
    private int powerLimit;

    public PukeGameDbo() {

    }

    public PukeGameDbo(PukeGameValueObject pukeGame, Map<String, PlayerInfo> playerInfoMap) {
        id = pukeGame.getId();
        panshu = pukeGame.getPanshu();
        renshu = pukeGame.getRenshu();
        difen = pukeGame.getDifen();
        state = pukeGame.getState();
        panNo = pukeGame.getPanNo();
        optionalPlay = pukeGame.getOptionalPlay();
        powerLimit = pukeGame.getPowerLimit();
        players = new ArrayList<>();
        Map<String, Double> playerTotalScoreMap = pukeGame.getPlayerTotalScoreMap();
        for (GamePlayerValueObject playerValueObject : pukeGame.getPlayers()) {
            String playerId = playerValueObject.getId();
            PlayerInfo playerInfo = playerInfoMap.get(playerId);
            PukeGamePlayerDbo playerDbo = new PukeGamePlayerDbo();
            playerDbo.setHeadimgurl(playerInfo.getHeadimgurl());
            playerDbo.setNickname(playerInfo.getNickname());
            playerDbo.setGender(playerInfo.getGender());
            playerDbo.setOnlineState(playerValueObject.getOnlineState());
            playerDbo.setPlayerId(playerId);
            playerDbo.setState(playerValueObject.getState());
            if (playerTotalScoreMap.get(playerId) != null) {
                playerDbo.setTotalScore(playerTotalScoreMap.get(playerId));
            }
            players.add(playerDbo);
        }
        createTime = System.currentTimeMillis();
    }

    public PukeGamePlayerDbo findPlayer(String playerId) {
        for (PukeGamePlayerDbo player : players) {
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

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getPanNo() {
        return panNo;
    }

    public void setPanNo(int panNo) {
        this.panNo = panNo;
    }

    public List<PukeGamePlayerDbo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PukeGamePlayerDbo> players) {
        this.players = players;
    }

    public double getDifen() {
        return difen;
    }

    public void setDifen(double difen) {
        this.difen = difen;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }
}
