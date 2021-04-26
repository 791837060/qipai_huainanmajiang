package com.anbang.qipai.paodekuai.cqrs.q.dao;

import com.anbang.qipai.paodekuai.cqrs.q.dbo.PanActionFrameDbo;

import java.util.List;

public interface PanActionFrameDboDao {

    void save(PanActionFrameDbo dbo);

    List<PanActionFrameDbo> findByGameIdAndPanNo(String gameId, int panNo);

    PanActionFrameDbo findByGameIdAndPanNo(String gameId, int panNo, int actionNo);

    void removeByTime(long endTime);
}
