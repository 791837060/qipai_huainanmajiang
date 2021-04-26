package com.anbang.qipai.doudizhu.cqrs.q.dao;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.PukeGameDbo;
import com.dml.mpgame.game.player.GamePlayerOnlineState;

public interface PukeGameDboDao {

    PukeGameDbo findById(String id);

    void save(PukeGameDbo pukeGameDbo);

    void updatePlayerOnlineState(String id, String playerId, GamePlayerOnlineState onlineState);

    void removeByTime(long endTime);
}
