package com.anbang.qipai.biji.cqrs.q.dao;

import com.anbang.qipai.biji.cqrs.q.dbo.PanResultDbo;

public interface PanResultDboDao {

	void save(PanResultDbo panResultDbo);

	PanResultDbo findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
