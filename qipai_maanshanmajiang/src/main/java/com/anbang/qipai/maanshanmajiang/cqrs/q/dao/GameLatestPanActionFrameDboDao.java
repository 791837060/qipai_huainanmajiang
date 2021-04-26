package com.anbang.qipai.maanshanmajiang.cqrs.q.dao;

import com.anbang.qipai.maanshanmajiang.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.majiang.pan.frame.PanActionFrame;

public interface GameLatestPanActionFrameDboDao {

	GameLatestPanActionFrameDbo findById(String id);

	void save(String id, PanActionFrame panActionFrame);

	void removeByTime(long endTime);
}
