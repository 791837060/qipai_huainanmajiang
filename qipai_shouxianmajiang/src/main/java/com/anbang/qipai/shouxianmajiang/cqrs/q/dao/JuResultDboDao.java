package com.anbang.qipai.shouxianmajiang.cqrs.q.dao;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.JuResultDbo;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.JuResultDbo;

public interface JuResultDboDao {

	void save(JuResultDbo juResultDbo);

	JuResultDbo findByGameId(String gameId);

	void removeByTime(long endTime);

}
