package com.anbang.qipai.guandan.cqrs.q.dao;

import com.anbang.qipai.guandan.cqrs.q.dbo.GameLatestPanActionFrameDbo;
import com.dml.shuangkou.pan.PanActionFrame;

public interface GameLatestPanActionFrameDboDao {

	GameLatestPanActionFrameDbo findById(String id);

	void save(String id, PanActionFrame panActionFrame);

	void removeByTime(long endTime);
}
