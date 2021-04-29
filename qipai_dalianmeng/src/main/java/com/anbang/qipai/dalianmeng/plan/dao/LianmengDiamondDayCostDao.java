package com.anbang.qipai.dalianmeng.plan.dao;

import com.anbang.qipai.dalianmeng.plan.bean.GameCountAndCost;
import com.anbang.qipai.dalianmeng.plan.bean.LianmengDiamondDayCost;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;

import java.util.List;
import java.util.Map;

public interface LianmengDiamondDayCostDao {
    void save(LianmengDiamondDayCost diamondDayCost);

    LianmengDiamondDayCost findByLianmengId(String lianmengId, long startTime, long endTime);

    void updateCostByLianmengId(String lianmengId, long startTime, long endTime, int cost);

    void updateGameCostByLianmengId(String lianmengId, long startTime, long endTime, Map<Game, GameCountAndCost> gameCost);


    void deleteByTime(long startTime);

    List<LianmengDiamondDayCost> findAllByLianmengId(String lianmengId, long startTime, long endTime);

}
