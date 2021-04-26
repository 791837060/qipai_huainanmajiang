package com.anbang.qipai.admin.plan.dao.gamedao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.games.LawsMutexGroup;

public interface LawsMutexGroupDao {

	void save(LawsMutexGroup lawsMutexGroup);

	void remove(String id);

	List<LawsMutexGroup> findLawsMutexGroupByConditions(int page, int size, LawsMutexGroup lawsMutexGroup);

	long getAmountByConditions(LawsMutexGroup lawsMutexGroup);
}
