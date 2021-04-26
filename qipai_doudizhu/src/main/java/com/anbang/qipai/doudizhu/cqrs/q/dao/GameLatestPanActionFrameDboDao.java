package com.anbang.qipai.doudizhu.cqrs.q.dao;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.doudizhu.pan.PanActionFrame;

public interface GameLatestPanActionFrameDboDao {

    GameLatestPanActionFrameDbo findById(String id);

    void save(String id, PanActionFrame panActionFrame);

    void removeByTime(long endTime);
}
