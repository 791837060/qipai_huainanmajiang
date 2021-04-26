package com.anbang.qipai.biji.cqrs.q.dao;

import java.util.List;

import com.anbang.qipai.biji.cqrs.q.dbo.PanActionFrameDbo;

public interface PanActionFrameDboDao {

	void save(PanActionFrameDbo frame);

	List<PanActionFrameDbo> findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);
}
