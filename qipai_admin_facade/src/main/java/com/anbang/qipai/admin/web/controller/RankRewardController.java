package com.anbang.qipai.admin.web.controller;

import com.anbang.qipai.admin.plan.bean.rank.RankRewardDO;
import com.anbang.qipai.admin.plan.bean.rank.RankType;
import com.anbang.qipai.admin.plan.service.rankservice.RankRewardService;
import com.anbang.qipai.admin.remote.service.QipaiRankRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.CommonVOUtil;
import com.anbang.qipai.admin.web.vo.CommonVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rankReward")
public class RankRewardController {
    @Autowired
    private RankRewardService rankRewardService;

    @Autowired
    private QipaiRankRemoteService qipaiRankRemoteService;

    @RequestMapping("/saveRankReward")
    public CommonVO saveRankReward(RankRewardDO rankRewardDO){
        if (StringUtils.isAnyBlank(rankRewardDO.getIconUrl(),rankRewardDO.getRemark())) {
            return CommonVOUtil.lackParameter();
        }
        if (StringUtils.isBlank(rankRewardDO.getId())) {
            rankRewardDO.setId(null);
        }

        rankRewardService.saveRankReward(rankRewardDO);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/deleteRankReward")
    public CommonVO deleteRankReward(String id){
        rankRewardService.removeById(id);
        return CommonVOUtil.success("success");
    }

    @RequestMapping("/getRankReward")
    public CommonVO getRankReward(String id) {
        RankRewardDO rankRewardDO = rankRewardService.getRankReward(id);
        return CommonVOUtil.success(rankRewardDO, "success");
    }

    @RequestMapping("/listByType")
    public CommonVO listByType(RankType rankType){
        List<RankRewardDO> rankRewards = rankRewardService.listRandRewrad(rankType);
        return CommonVOUtil.success(rankRewards, "success");
    }

    @RequestMapping("/release")
    public CommonVO release(RankType rankType){
        List<RankRewardDO> rankRewards = rankRewardService.listRandRewrad(rankType);
        CommonRemoteVO remoteVO = qipaiRankRemoteService.release(rankType, rankRewards);
        if (!remoteVO.isSuccess()) {
            return CommonVOUtil.error("set error");
        }
        return CommonVOUtil.success("success");

    }
}
