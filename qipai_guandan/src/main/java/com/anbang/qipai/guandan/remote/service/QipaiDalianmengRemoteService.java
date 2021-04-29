package com.anbang.qipai.guandan.remote.service;

import com.anbang.qipai.guandan.remote.vo.CommonRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员中心远程服务
 */
//@FeignClient("qipai-dalianmeng")
@FeignClient("qipai-dalianmeng/dalianmeng")
public interface QipaiDalianmengRemoteService {

    @RequestMapping(value = "/power/nowPowerForRemote")
    public CommonRemoteVO nowPowerForRemote(@RequestParam("memberId") String memberId, @RequestParam("lianmengId") String lianmengId);

}
