package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopProduct;

public interface ChaguanShopProductDao {

	void save(ChaguanShopProduct product);

	void removeByIds(String[] productIds);

	long count();

	List<ChaguanShopProduct> findByConditions(int page, int size);
}
