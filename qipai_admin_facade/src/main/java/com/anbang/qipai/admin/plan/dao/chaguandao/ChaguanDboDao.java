package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanDbo;

public interface ChaguanDboDao {

	void save(ChaguanDbo dbo);

	long count(String agentId);

	void removeById(String chaguanId);

	List<ChaguanDbo> find(int page, int size,String agentId);

	ChaguanDbo findByChaguanId(String chaguanId);
}
