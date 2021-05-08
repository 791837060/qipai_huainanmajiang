package com.anbang.qipai.shouxianmajiang.cqrs.q.dao;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanResultDbo;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanResultDbo;

public interface PanResultDboDao {

	void save(PanResultDbo panResultDbo);

	PanResultDbo findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
