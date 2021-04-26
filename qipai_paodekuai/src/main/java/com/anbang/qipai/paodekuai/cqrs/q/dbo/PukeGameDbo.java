package com.anbang.qipai.paodekuai.cqrs.q.dbo;

import com.anbang.qipai.paodekuai.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.paodekuai.plan.bean.PlayerInfo;
import com.dml.mpgame.game.GamePlayerValueObject;
import com.dml.mpgame.game.GameState;
import com.dml.paodekuai.wanfa.OptionalPlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PukeGameDbo {
    private String id;
    private int panshu;
    private int renshu;
    private OptionalPlay optionalPlay;
    private GameState state;// 原来是 waitingStart, playing, waitingNextPan, finished
    private int panNo;
    private List<PukeGamePlayerDbo> players;
    private long createTime;
    private double difen;
    private int powerLimit;

    public PukeGameDbo() {
    }

    public PukeGameDbo(PukeGameValueObject pukeGame, Map<String, PlayerInfo> playerInfoMap) {
        id = pukeGame.getId();
        difen = pukeGame.getDifen();
        panshu = pukeGame.getPanshu();
        renshu = pukeGame.getRenshu();
        optionalPlay = pukeGame.getOptionalPlay();
        state = pukeGame.getState();
        panNo = pukeGame.getPanNo();
        powerLimit = pukeGame.getPowerLimit();
        players = new ArrayList<>();
        Map<String, Double> playerTotalScoreMap = pukeGame.getPlayeTotalScoreMap();
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
            } else {
                if (pukeGame.getOptionalPlay().isJinyuanzi()) {
                    playerDbo.setTotalScore(pukeGame.getOptionalPlay().getYuanzifen());
                } else {
                    playerDbo.setTotalScore(0d);
                }
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

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
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
}
