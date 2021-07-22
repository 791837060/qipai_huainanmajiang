package com.anbang.qipai.huainanmajiang.cqrs.q.dao;

import com.anbang.qipai.huainanmajiang.cqrs.q.dbo.JuResultDbo;

public interface JuResultDboDao {

	void save(JuResultDbo juResultDbo);

	JuResultDbo findByGameId(String gameId);

	void removeByTime(long endTime);

}
