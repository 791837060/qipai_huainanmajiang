package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanMemberDbo;

public interface ChaguanMemberDboDao {

	void save(ChaguanMemberDbo dbo);

	long countByChaguanId(String chaguanId);

	void removeByChaguanId(String chaguanId);

	List<ChaguanMemberDbo> findByChaguanId(int page, int size, String chaguanId);
}
