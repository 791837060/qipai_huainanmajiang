package com.anbang.qipai.guandan.cqrs.q.dao;

import com.anbang.qipai.guandan.cqrs.q.dbo.GameLatestPukeGameInfoDbo;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGameInfoDbo;

public interface GameLatestPukeGameInfoDboDao {
	GameLatestPukeGameInfoDbo findById(String id);

	void save(String id, PukeGameInfoDbo gameInfoDbo);

	void removeByTime(long endTime);
}
