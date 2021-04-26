package com.anbang.qipai.admin.remote.service;

import com.anbang.qipai.admin.plan.bean.rank.RankRewardDO;
import com.anbang.qipai.admin.plan.bean.rank.RankType;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 排行榜rpc
 */
@FeignClient("qipai-rank")
public interface QipaiRankRemoteService {

    @RequestMapping("/rankReward/release")
    CommonRemoteVO release(@RequestParam(value = "rankType")RankType rankType, @RequestBody List<RankRewardDO> rankRewardDOList);
}
