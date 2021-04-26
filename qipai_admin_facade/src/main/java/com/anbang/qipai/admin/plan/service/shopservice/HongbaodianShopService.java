package com.anbang.qipai.admin.plan.service.shopservice;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianProductRecord;
import com.anbang.qipai.admin.plan.bean.shop.HongbaodianShopProductDbo;
import com.anbang.qipai.admin.plan.dao.shopdao.HongbaodianProductRecordDao;
import com.anbang.qipai.admin.plan.dao.shopdao.HongbaodianShopProductDboDao;
import com.anbang.qipai.admin.web.query.HongbaoRecordQuery;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HongbaodianShopService {
	@Autowired
	private HongbaodianProductRecordDao hongbaodianProductRecordDao;

	@Autowired
	private HongbaodianShopProductDboDao hongbaodianShopProductDboDao;

	public void addHongbaodianProduct(HongbaodianShopProductDbo productDbo){
		hongbaodianShopProductDboDao.save(productDbo);
	}

	public void delete(String[] ids) {
		hongbaodianShopProductDboDao.removeByIds(ids);
	}

	public HongbaodianShopProductDbo getHongbaodianShopProduct(String id) {
		return hongbaodianShopProductDboDao.findById(id);
	}

	public List<HongbaodianShopProductDbo> listAllHongbaodianShopProduct(){
		return hongbaodianShopProductDboDao.listAll();
	}

	public ListPage listHongbaodianShopProduct(int page, int size){
		int count = (int) hongbaodianShopProductDboDao.count();
		List<HongbaodianShopProductDbo> records = hongbaodianShopProductDboDao.list(page, size);
		return new ListPage(records, page, size, count);
	}

	public long countByMemberIdAndStatus(String id, String status){
		return hongbaodianProductRecordDao.countByMemberIdAndStatus(id, status);
	}

	public ListPage finScordRecordByQuery(int page, int size, HongbaoRecordQuery recordQuery) {
		int count = (int) hongbaodianProductRecordDao.countByQuery(recordQuery);
		List<HongbaodianProductRecord> records = hongbaodianProductRecordDao.findByQuery(page, size, recordQuery);
		return new ListPage(records, page, size, count);
	}


}
