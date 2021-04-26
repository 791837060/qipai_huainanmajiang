package com.anbang.qipai.guandan.cqrs.q.dao;

import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;

import java.util.List;

public interface PukeGameInfoDboDao {
    void save(PukeGameInfoDbo dbo);

    List<PukeGameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo);

    void removeByTime(long endTime);
}
