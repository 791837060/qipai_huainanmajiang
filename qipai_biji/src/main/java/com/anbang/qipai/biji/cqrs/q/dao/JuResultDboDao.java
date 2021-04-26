package com.anbang.qipai.biji.cqrs.q.dao;

import com.anbang.qipai.biji.cqrs.q.dbo.JuResultDbo;

public interface JuResultDboDao {

	void save(JuResultDbo juResultDbo);

	JuResultDbo findByGameId(String gameId);

	void removeByTime(long endTime);

}
