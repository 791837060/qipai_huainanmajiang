package com.anbang.qipai.admin.plan.dao.shopdao;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianShopProductDbo;

import java.util.List;


public interface HongbaodianShopProductDboDao {

	void save(HongbaodianShopProductDbo dbo);

	void update(HongbaodianShopProductDbo dbo);

	void removeByIds(String[] ids);

	void removeAll();

	void saveAll(List<HongbaodianShopProductDbo> products);

	HongbaodianShopProductDbo findById(String id);

	long countByType(String type);

	List<HongbaodianShopProductDbo> list(int page, int size);

	void incRemainById(String id, int amount);

	void updateRemainById(String id, int remain);

	long count();

	List<HongbaodianShopProductDbo> listAll();
}
