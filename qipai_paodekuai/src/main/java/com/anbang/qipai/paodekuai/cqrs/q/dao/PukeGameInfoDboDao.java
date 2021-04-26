package com.anbang.qipai.paodekuai.cqrs.q.dao;

import com.anbang.qipai.paodekuai.cqrs.q.dbo.PukeGameInfoDbo;

import java.util.List;

public interface PukeGameInfoDboDao {
    void save(PukeGameInfoDbo dbo);

    List<PukeGameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo);

    void removeByTime(long endTime);
}
