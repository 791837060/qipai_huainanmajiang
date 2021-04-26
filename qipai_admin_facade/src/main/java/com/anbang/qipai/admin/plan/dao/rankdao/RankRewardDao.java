package com.anbang.qipai.admin.plan.dao.rankdao;

import com.anbang.qipai.admin.plan.bean.rank.RankRewardDO;
import com.anbang.qipai.admin.plan.bean.rank.RankType;

import java.util.List;

public interface RankRewardDao {
    void save(RankRewardDO rankRewardDO);

    void removeById(String id);

    void removeByType(RankType rankType);

    void updateRemain(String id, int remain);

    RankRewardDO get(String id);

    List<RankRewardDO> list(RankType rankType);
}
