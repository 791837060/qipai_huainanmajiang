package com.anbang.qipai.dalianmeng.plan.dao;

import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;

import java.util.List;

public interface LianmengDiamondDayCostDao {
    void save(LianmengDiamondDayCost diamondDayCost);

    LianmengDiamondDayCost findByLianmengId(String lianmengId, long startTime, long endTime);

    void updateCostByLianmengId(String lianmengId, long startTime, long endTime, int cost);

    void deleteByTime(long startTime);

    List<LianmengDiamondDayCost> findAllByLianmengId(String lianmengId, long startTime, long endTime);

}
