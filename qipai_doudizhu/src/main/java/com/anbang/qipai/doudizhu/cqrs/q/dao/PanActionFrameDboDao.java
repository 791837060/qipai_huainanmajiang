package com.anbang.qipai.doudizhu.cqrs.q.dao;

import java.util.List;

import com.anbang.qipai.doudizhu.cqrs.q.dbo.PanActionFrameDbo;

public interface PanActionFrameDboDao {

	void save(PanActionFrameDbo dbo);

	List<PanActionFrameDbo> findByGameIdAndPanNo(String gameId, int panNo);

	PanActionFrameDbo findByGameIdAndPanNo(String gameId, int panNo, int actionNo);

	void removeByTime(long endTime);
}
