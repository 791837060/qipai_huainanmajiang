package com.anbang.qipai.shouxianmajiang.cqrs.q.dao;

import java.util.List;

import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanActionFrameDbo;
import com.anbang.qipai.shouxianmajiang.cqrs.q.dbo.PanActionFrameDbo;

public interface PanActionFrameDboDao {

	void save(PanActionFrameDbo dbo);

	List<PanActionFrameDbo> findByGameIdAndPanNo(String gameId, int panNo);

	void removeByTime(long endTime);

    PanActionFrameDbo findLatestPanActionFrame(String gameId);
}
