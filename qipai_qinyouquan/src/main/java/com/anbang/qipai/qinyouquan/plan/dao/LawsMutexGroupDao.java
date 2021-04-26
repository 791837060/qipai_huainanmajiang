package com.anbang.qipai.qinyouquan.plan.dao;


import com.anbang.qipai.qinyouquan.plan.bean.game.LawsMutexGroup;

import java.util.List;

public interface LawsMutexGroupDao {

	void save(LawsMutexGroup lawsMutexGroup);

	void remove(String id);

	List<LawsMutexGroup> findLawsMutexGroupByConditions(int page, int size, LawsMutexGroup lawsMutexGroup);

	long getAmountByConditions(LawsMutexGroup lawsMutexGroup);
}
