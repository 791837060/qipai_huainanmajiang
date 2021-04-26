package com.anbang.qipai.doudizhu.cqrs.c.domain.jiaofen;

import com.anbang.qipai.doudizhu.cqrs.c.domain.OptionalPlay;
import com.anbang.qipai.doudizhu.cqrs.c.domain.qiangdizhu.CannotQiangdizhuException;
import com.anbang.qipai.doudizhu.cqrs.c.domain.state.PlayerQiangdizhuState;
import com.dml.doudizhu.ju.Ju;
import com.dml.doudizhu.pan.Pan;
import com.dml.doudizhu.preparedapai.dizhu.DizhuDeterminer;
import com.dml.puke.wanfa.position.Position;
import com.dml.puke.wanfa.position.PositionUtil;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;

import java.util.HashMap;
import java.util.Map;

public class DoudizhuJiaofenDizhuDeterminer implements DizhuDeterminer {
    private int renshu;
    private Map<String, PlayerQiangdizhuState> playerQiangdizhuMap = new HashMap<>();
    private Map<String, Integer> playerJiaofenMap = new HashMap<>();
    private OptionalPlay optionalPlay;
    private int score;

    public void init(Ju ju) {
        score = 0;
        Pan currentPan = ju.getCurrentPan();
        for (String pid : currentPan.sortedPlayerIdList()) {
            if (currentPan.findPlayerById(pid).getPosition().equals(Position.dong)) {
                playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.startJiaodizhu);
            } else {
                playerQiangdizhuMap.put(pid, PlayerQiangdizhuState.waitForJiaodizhu);
            }
            playerJiaofenMap.put(pid, 0);
        }
    }

    @Override
    public String determineToDizhu(Ju ju, String playerId, Integer score) throws Exception {
        Pan currentPan = ju.getCurrentPan();
        String dizhuId = null;
        if (score >= this.score) {
            this.score = score;
        } else if (score != 0) {
            throw new CannotQiangdizhuException();
        }
        if (score == 3) {
            dizhuId = playerId;
        }
        playerJiaofenMap.put(playerId, score);
        if (score==0){
            playerQiangdizhuMap.put(playerId, PlayerQiangdizhuState.over);
        }else {
            playerQiangdizhuMap.put(playerId, PlayerQiangdizhuState.afterjiaodizhu);
        }
        String nextPlayerId = null;
        Position nextPosition = currentPan.findPlayerPosition(playerId);
        while (nextPlayerId == null) {
            nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
            nextPlayerId = currentPan.playerIdForPosition(nextPosition);
        }
        boolean finishJiaofen = false;
        for (Integer value : playerJiaofenMap.values()) {
            if (value == 3) {
                finishJiaofen = true;
                break;
            }
        }
        if (finishJiaofen) {
            for (String pid : playerJiaofenMap.keySet()) {
                if (this.score == playerJiaofenMap.get(pid)) {
                    dizhuId = pid;
                }
            }
        }else {
            int overCount=0;
            for (PlayerQiangdizhuState value : playerQiangdizhuMap.values()) {
                if (value.equals(PlayerQiangdizhuState.over)) {
                    overCount ++;
                }
            }
            if (overCount==renshu){
                dizhuId=currentPan.playerIdForPosition(Position.dong);
            }else if (overCount==renshu-1){
                for (String s : playerQiangdizhuMap.keySet()) {
                    if (!playerQiangdizhuMap.get(s).equals(PlayerQiangdizhuState.over)) {
                        if (playerJiaofenMap.get(s)!=0){
                            dizhuId=s;
                        }
                    }
                }
            }
        }
        if (dizhuId!=null){
            for (String pid : currentPan.sortedPlayerIdList()) {
                playerQiangdizhuMap.put(pid,PlayerQiangdizhuState.over);
            }
        }else {
            if (playerQiangdizhuMap.get(nextPlayerId).equals(PlayerQiangdizhuState.over)) {
                nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
                nextPlayerId = currentPan.playerIdForPosition(nextPosition);
                while (nextPlayerId == null) {
                    nextPosition = PositionUtil.nextPositionClockwise(nextPosition);
                    nextPlayerId = currentPan.playerIdForPosition(nextPosition);
                }
            }
            playerQiangdizhuMap.put(nextPlayerId, PlayerQiangdizhuState.startJiaodizhu);
        }
        return dizhuId;
    }


    public Map<String, PlayerQiangdizhuState> getPlayerQiangdizhuMap() {
        return playerQiangdizhuMap;
    }

    public void setPlayerQiangdizhuMap(Map<String, PlayerQiangdizhuState> playerQiangdizhuMap) {
        this.playerQiangdizhuMap = playerQiangdizhuMap;
    }

    public Map<String, Integer> getPlayerJiaofenMap() {
        return playerJiaofenMap;
    }

    public void setPlayerJiaofenMap(Map<String, Integer> playerJiaofenMap) {
        this.playerJiaofenMap = playerJiaofenMap;
    }

    public OptionalPlay getOptionalPlay() {
        return optionalPlay;
    }

    public void setOptionalPlay(OptionalPlay optionalPlay) {
        this.optionalPlay = optionalPlay;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRenshu() {
        return renshu;
    }

    public void setRenshu(int renshu) {
        this.renshu = renshu;
    }
}
