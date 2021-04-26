package com.anbang.qipai.doudizhu.cqrs.q.service;

import com.anbang.qipai.doudizhu.cqrs.c.domain.PukeGameValueObject;
import com.anbang.qipai.doudizhu.cqrs.c.domain.result.DoudizhuJuResult;
import com.anbang.qipai.doudizhu.cqrs.q.dao.*;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameFinishVoteDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.anbang.qipai.doudizhu.plan.bean.PlayerInfo;
import com.anbang.qipai.doudizhu.plan.dao.PlayerInfoDao;
import com.dml.mpgame.game.extend.vote.GameFinishVoteValueObject;
import com.dml.mpgame.game.watch.WatchRecord;
import com.dml.mpgame.game.watch.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PukeGameQueryService {

    @Autowired
    private PukeGameDboDao pukeGameDboDao;

    @Autowired
    private PlayerInfoDao playerInfoDao;

    @Autowired
    private GameFinishVoteDboDao gameFinishVoteDboDao;

    @Autowired
    private JuResultDboDao juResultDboDao;

    @Autowired
    private WatchRecordDao watchRecordDao;

    @Autowired
    private GameInfoDboDao gameInfoDboDao;

    @Autowired
    private GameLatestInfoDboDao gameLatestInfoDboDao;

    @Autowired
    private GameLatestPanActionFrameDboDao gameLatestPanActionFrameDboDao;

    @Autowired
    private PanActionFrameDboDao panActionFrameDboDao;

    @Autowired
    private PanResultDboDao panResultDboDao;

    public void removeGameData(long endTime) {
        new Thread() {
            @Override
            public void run() {
                pukeGameDboDao.removeByTime(endTime);
                gameFinishVoteDboDao.removeByTime(endTime);
                juResultDboDao.removeByTime(endTime);
                watchRecordDao.removeByTime(endTime);
                gameInfoDboDao.removeByTime(endTime);
                gameLatestInfoDboDao.removeByTime(endTime);
                gameLatestPanActionFrameDboDao.removeByTime(endTime);
                panActionFrameDboDao.removeByTime(endTime);
                panResultDboDao.removeByTime(endTime);
            }
        }.start();
    }

    public PukeGameDbo findPukeGameDboById(String gameId) {
        return pukeGameDboDao.findById(gameId);
    }

    public void newPukeGame(PukeGameValueObject pukeGame) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);
    }

    public void joinGame(PukeGameValueObject pukeGame) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);
    }

    public void leaveGame(PukeGameValueObject pukeGame) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGame.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGame, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        GameFinishVoteValueObject gameFinishVoteValueObject = pukeGame.getVote();
        if (gameFinishVoteValueObject != null) {
            gameFinishVoteDboDao.removeGameFinishVoteDboByGameId(pukeGame.getId());
            GameFinishVoteDbo gameFinishVoteDbo = new GameFinishVoteDbo();
            gameFinishVoteDbo.setVote(gameFinishVoteValueObject);
            gameFinishVoteDbo.setGameId(pukeGame.getId());
            gameFinishVoteDbo.setCreateTime(System.currentTimeMillis());
            gameFinishVoteDboDao.save(gameFinishVoteDbo);
        }
    }

    public void backToGame(String playerId, PukeGameValueObject pukeGameValueObject) {
        pukeGameDboDao.updatePlayerOnlineState(pukeGameValueObject.getId(), playerId,
                pukeGameValueObject.findPlayerOnlineState(playerId));
        GameFinishVoteValueObject gameFinishVoteValueObject = pukeGameValueObject.getVote();
        if (gameFinishVoteValueObject != null) {
            gameFinishVoteDboDao.update(pukeGameValueObject.getId(), gameFinishVoteValueObject);
        }
    }

    public void finishGameImmediately(PukeGameValueObject pukeGameValueObject) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGameValueObject.allPlayerIds()
                .forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGameValueObject, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        if (pukeGameValueObject.getJuResult() != null) {
            DoudizhuJuResult doudizhuJuResult = (DoudizhuJuResult) pukeGameValueObject.getJuResult();
            JuResultDbo juResultDbo = new JuResultDbo(pukeGameValueObject.getId(), null, doudizhuJuResult);
            juResultDboDao.save(juResultDbo);
        }
    }

    public void finish(PukeGameValueObject pukeGameValueObject) {
        GameFinishVoteValueObject gameFinishVoteValueObject = pukeGameValueObject.getVote();
        if (gameFinishVoteValueObject != null) {
            gameFinishVoteDboDao.removeGameFinishVoteDboByGameId(pukeGameValueObject.getId());
            GameFinishVoteDbo gameFinishVoteDbo = new GameFinishVoteDbo();
            gameFinishVoteDbo.setVote(gameFinishVoteValueObject);
            gameFinishVoteDbo.setGameId(pukeGameValueObject.getId());
            gameFinishVoteDbo.setCreateTime(System.currentTimeMillis());
            gameFinishVoteDboDao.save(gameFinishVoteDbo);
        }

        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGameValueObject.allPlayerIds()
                .forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGameValueObject, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        if (pukeGameValueObject.getJuResult() != null) {
            DoudizhuJuResult doudizhuJuResult = (DoudizhuJuResult) pukeGameValueObject.getJuResult();
            JuResultDbo juResultDbo = new JuResultDbo(pukeGameValueObject.getId(), null, doudizhuJuResult);
            juResultDboDao.save(juResultDbo);
        }
    }

    public void quit(PukeGameValueObject pukeGameValueObject) {
        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGameValueObject.allPlayerIds().forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGameValueObject, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);
    }

    public void voteToFinish(PukeGameValueObject pukeGameValueObject) {
        GameFinishVoteValueObject gameFinishVoteValueObject = pukeGameValueObject.getVote();
        if (gameFinishVoteValueObject != null) {
            gameFinishVoteDboDao.update(pukeGameValueObject.getId(), gameFinishVoteValueObject);
        }

        Map<String, PlayerInfo> playerInfoMap = new HashMap<>();
        pukeGameValueObject.allPlayerIds()
                .forEach((playerId) -> playerInfoMap.put(playerId, playerInfoDao.findById(playerId)));
        PukeGameDbo pukeGameDbo = new PukeGameDbo(pukeGameValueObject, playerInfoMap);
        pukeGameDboDao.save(pukeGameDbo);

        if (pukeGameValueObject.getJuResult() != null) {
            DoudizhuJuResult doudizhuJuResult = (DoudizhuJuResult) pukeGameValueObject.getJuResult();
            JuResultDbo juResultDbo = new JuResultDbo(pukeGameValueObject.getId(), null, doudizhuJuResult);
            juResultDboDao.save(juResultDbo);
        }
    }

    public GameFinishVoteDbo findGameFinishVoteDbo(String gameId) {
        return gameFinishVoteDboDao.findByGameId(gameId);
    }

    public WatchRecord saveWatchRecord(String gameId, Watcher watcher) {
        WatchRecord watchRecord = watchRecordDao.findByGameId(gameId);
        if (watchRecord == null) {
            WatchRecord record = new WatchRecord();
            List<Watcher> watchers = new ArrayList<>();
            watchers.add(watcher);

            record.setGameId(gameId);
            record.setWatchers(watchers);
            record.setCreateTime(System.currentTimeMillis());
            watchRecordDao.save(record);
            return record;
        }

        for (Watcher list : watchRecord.getWatchers()) {
            if (list.getId().equals(watcher.getId())) {
                list.setState(watcher.getState());
                watchRecord.setCreateTime(System.currentTimeMillis());
                watchRecordDao.save(watchRecord);
                return watchRecord;
            }
        }

        watchRecord.getWatchers().add(watcher);
        watchRecord.setCreateTime(System.currentTimeMillis());
        watchRecordDao.save(watchRecord);
        return watchRecord;
    }

    /**
     * 查询观战中的玩家
     */
    public boolean findByPlayerId(String gameId, String playerId) {
        if (watchRecordDao.findByPlayerId(gameId, playerId, "join") != null) {
            return true;
        }
        return false;
    }

}
