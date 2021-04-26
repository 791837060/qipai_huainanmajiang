package com.anbang.qipai.admin.plan.dao.hongbaodao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.hongbao.BlackList;

public interface BlackListDao {

	void save(BlackList blackList);

	void remove(String[] ids);

	long count();

	List<BlackList> find(int page, int size);
}
