package com.anbang.qipai.admin.plan.service.rankservice;

import com.anbang.qipai.admin.plan.bean.rank.RankRewardDO;
import com.anbang.qipai.admin.plan.bean.rank.RankType;
import com.anbang.qipai.admin.plan.dao.rankdao.RankRewardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankRewardService {
    @Autowired
    private RankRewardDao rankRewardDao;

    public void saveRankReward(RankRewardDO rankRewardDO){
        rankRewardDao.save(rankRewardDO);
    }

    public void removeById(String id) {
        rankRewardDao.removeById(id);
    }

    public void removeByType(RankType rankType) {
        rankRewardDao.removeByType(rankType);
    }

    public RankRewardDO getRankReward(String id) {
        return rankRewardDao.get(id);
    }

    public List<RankRewardDO> listRandRewrad(RankType rankType){
        return rankRewardDao.list(rankType);
    }

}
