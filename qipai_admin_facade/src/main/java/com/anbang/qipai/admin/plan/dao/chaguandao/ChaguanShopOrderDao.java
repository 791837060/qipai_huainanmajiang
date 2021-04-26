package com.anbang.qipai.admin.plan.dao.chaguandao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanShopOrder;
import com.anbang.qipai.admin.web.vo.chaguanvo.ChaguanShopOrderVO;

public interface ChaguanShopOrderDao {

	void save(ChaguanShopOrder order);

	long countByConditions(ChaguanShopOrderVO order);

	List<ChaguanShopOrder> findByConditions(int page, int size, ChaguanShopOrderVO order);
}
