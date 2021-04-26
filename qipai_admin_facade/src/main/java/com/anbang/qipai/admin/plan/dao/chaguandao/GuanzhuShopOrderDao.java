package com.anbang.qipai.admin.plan.dao.chaguandao;

import com.anbang.qipai.admin.plan.bean.chaguan.GuanzhuShopOrder;
import com.anbang.qipai.admin.web.query.GuanzhuOrderQuery;

import java.util.List;

/**
 * @author ethan
 */
public interface GuanzhuShopOrderDao {
    void save(GuanzhuShopOrder guanzhuShopOrder);

    void updateAgentTotalSale(String id, double agentTotalSale);

    int countByQuery(GuanzhuOrderQuery guanzhuOrderQuery);

    List<GuanzhuShopOrder> listByQuery(GuanzhuOrderQuery guanzhuOrderQuery, int page, int size);

    double agentTotalSale(String agentId);
}
