package com.anbang.qipai.admin.plan.service.chaguanservice;

import com.anbang.qipai.admin.plan.bean.chaguan.GuanzhuShopOrder;
import com.anbang.qipai.admin.plan.dao.chaguandao.GuanzhuShopOrderDao;
import com.anbang.qipai.admin.web.query.GuanzhuOrderQuery;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ethan
 */
@Service
public class GuanzhuShopOrderService {
    @Autowired
    private GuanzhuShopOrderDao guanzhuShopOrderDao;

    /**
     * 保存馆主商城订单并统计累计销售额
     */
    public void save(GuanzhuShopOrder guanzhuShopOrder){
        guanzhuShopOrderDao.save(guanzhuShopOrder);
        double agentTotalSale = guanzhuShopOrderDao.agentTotalSale(guanzhuShopOrder.getReceiverId());
        guanzhuShopOrderDao.updateAgentTotalSale(guanzhuShopOrder.getId(), agentTotalSale);
    }

    public ListPage listByQuery(GuanzhuOrderQuery guanzhuOrderQuery, int page, int size){
        int count = guanzhuShopOrderDao.countByQuery(guanzhuOrderQuery);
        List<GuanzhuShopOrder> guanzhuShopOrders = guanzhuShopOrderDao.listByQuery(guanzhuOrderQuery, page, size);
        return new ListPage(guanzhuShopOrders, page, size, count);
    }
}
