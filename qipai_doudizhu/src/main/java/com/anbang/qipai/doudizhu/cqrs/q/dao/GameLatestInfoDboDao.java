package com.anbang.qipai.doudizhu.cqrs.q.dao;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestInfoDbo;

public interface GameLatestInfoDboDao {

    void save(GameLatestInfoDbo info);

    GameLatestInfoDbo findById(String gameId);

    void removeByTime(long endTime);
}
