package com.anbang.qipai.guandan.cqrs.q.dao;

import java.util.Map;

import com.anbang.qipai.guandan.cqrs.c.domain.state.PukeGamePlayerChaodiState;
import com.anbang.qipai.guandan.cqrs.q.dbo.PukeGamePlayerChaodiDbo;

public interface PukeGamePlayerChaodiDboDao {
	void addPukeGamePlayerChaodiDbo(PukeGamePlayerChaodiDbo dbo);

	void updatePukeGamePlayerChaodiDbo(String gameId, int panNo,
			Map<String, PukeGamePlayerChaodiState> playerChaodiStateMap);

	PukeGamePlayerChaodiDbo findLastByGameId(String gameId);

	PukeGamePlayerChaodiDbo findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
