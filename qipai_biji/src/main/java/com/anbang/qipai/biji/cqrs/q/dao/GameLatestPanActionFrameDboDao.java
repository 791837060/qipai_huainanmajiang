package com.anbang.qipai.biji.cqrs.q.dao;

import com.anbang.qipai.biji.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.shisanshui.pan.PanActionFrame;

public interface GameLatestPanActionFrameDboDao {

    GameLatestPanActionFrameDbo findById(String id);

    void save(String id, PanActionFrame panActionFrame);

    void removeByTime(long endTime);
}
