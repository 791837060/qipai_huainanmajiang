package com.anbang.qipai.admin.plan.dao.tasksdao;

import com.anbang.qipai.admin.plan.bean.tasks.HongbaodianProduct;

import java.util.List;

public interface ExchangeManageDao {

    void addExchangeManage(HongbaodianProduct hongbaodianProduct);

    void updateExchangeManage(HongbaodianProduct hongbaodianProduct);

    void deleteExchangeManage(String[] ids);

    HongbaodianProduct getExchangeManage(String id);

    long countExchangeMange();

    List<HongbaodianProduct> getExchangeManages(int page, int size);
}
