package com.anbang.qipai.doudizhu.cqrs.q.dao;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.GameInfoDbo;

import java.util.List;

public interface GameInfoDboDao {

	void save(GameInfoDbo info);

	List<GameInfoDbo> findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
