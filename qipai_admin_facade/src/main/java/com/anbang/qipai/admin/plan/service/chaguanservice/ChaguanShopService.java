package com.anbang.qipai.admin.plan.service.chaguanservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopOrder;
import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopProduct;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanShopOrderDao;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanShopProductDao;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanShopOrderVO;
import com.highto.framework.web.page.ListPage;

@Service
public class ChaguanShopService {

	@Autowired
	private ChaguanShopOrderDao chaguanShopOrderDao;

	@Autowired
	private ChaguanShopProductDao chaguanShopProductDao;

	public ListPage findOrderByConditions(int page, int size, ChaguanShopOrderVO order) {
		long amount = chaguanShopOrderDao.countByConditions(order);
		List<ChaguanShopOrder> orderList = chaguanShopOrderDao.findByConditions(page, size, order);
		ListPage listPage = new ListPage(orderList, page, size, (int) amount);
		return listPage;
	}

	public ListPage findChaguanShopProductByConditions(int page, int size) {
		long amount = chaguanShopProductDao.count();
		List<ChaguanShopProduct> productList = chaguanShopProductDao.findByConditions(page, size);
		ListPage listPage = new ListPage(productList, page, size, (int) amount);
		return listPage;
	}

	public void saveChaguanShopOrder(ChaguanShopOrder order) {
		chaguanShopOrderDao.save(order);
	}

	public void finishChaguanShopOrder(ChaguanShopOrder order) {
		chaguanShopOrderDao.save(order);
	}

	public void saveChaguanShopProduct(ChaguanShopProduct product) {
		chaguanShopProductDao.save(product);
	}

	public void updateChaguanShopProduct(ChaguanShopProduct product) {
		chaguanShopProductDao.save(product);
	}

	public void removeChaguanShopProduct(String[] productIds) {
		chaguanShopProductDao.removeByIds(productIds);
	}
}
