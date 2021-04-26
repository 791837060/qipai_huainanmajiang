package com.anbang.qipai.admin.plan.service.tasksservice;

import com.anbang.qipai.admin.constant.Constants;
import com.anbang.qipai.admin.plan.bean.tasks.HongbaodianProduct;
import com.anbang.qipai.admin.plan.bean.tasks.RewardType;
import com.anbang.qipai.admin.plan.dao.tasksdao.ExchangeManageDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yins
 * @Description: TODO
 */
@Service
public class ExchangeManageService {

    @Autowired
    private ExchangeManageDao exchangeManageDao;

    public void addExchangeManage(HongbaodianProduct hongbaodianProduct) {
        exchangeManageDao.addExchangeManage(hongbaodianProduct);
    }

    public void deleteExchangeManage(String[] ids) {
        exchangeManageDao.deleteExchangeManage(ids);
    }

    public void updateExchangeManage(HongbaodianProduct hongbaodianProduct) {
        exchangeManageDao.updateExchangeManage(hongbaodianProduct);
    }

    public HongbaodianProduct getExchangeManage(String id) {
        return exchangeManageDao.getExchangeManage(id);
    }

    public ListPage getExchangeManages(int page, int size) {
        long count = exchangeManageDao.countExchangeMange();
        List<HongbaodianProduct> hongbaodianProducts = exchangeManageDao.getExchangeManages(page, size);
        List<Map> data = new ArrayList<>();
        for (HongbaodianProduct list : hongbaodianProducts) {
            Map map = new HashMap();
            map.put("id",list.getId());
            map.put("name",list.getName());
            map.put("price",list.getPrice());
            map.put("rewardType", RewardType.toMap().get(list.getRewardType().name()));
            map.put("rewardNum",list.getRewardNum());
            data.add(map);
        }
        ListPage result = new ListPage(data, page, size, (int) count);
        return result;
    }
}
