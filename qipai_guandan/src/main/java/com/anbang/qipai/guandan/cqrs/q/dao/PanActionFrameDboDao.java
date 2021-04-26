package com.anbang.qipai.guandan.cqrs.q.dao;

import java.util.List;

import com.anbang.qipai.guandan.cqrs.q.dbo.PanActionFrameDbo;

public interface PanActionFrameDboDao {

	void save(PanActionFrameDbo dbo);

	List<PanActionFrameDbo> findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
