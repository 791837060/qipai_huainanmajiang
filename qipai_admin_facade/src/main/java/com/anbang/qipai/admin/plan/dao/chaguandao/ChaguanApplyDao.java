package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanApply;

public interface ChaguanApplyDao {

	void save(ChaguanApply apply);

	long countByConditions(ChaguanApply apply);

	List<ChaguanApply> findByConditions(int page, int size, ChaguanApply apply);
}
