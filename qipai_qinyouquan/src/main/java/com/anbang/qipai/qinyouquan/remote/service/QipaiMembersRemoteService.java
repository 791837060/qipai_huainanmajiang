package com.anbang.qipai.qinyouquan.remote.service;

import com.anbang.qipai.qinyouquan.remote.vo.CommonRemoteVO;
import com.anbang.qipai.qinyouquan.remote.vo.MemberRemoteVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员中心远程服务
 */
@FeignClient("qipai-members/members")
//@FeignClient("qipai-members")
public interface QipaiMembersRemoteService {

    @RequestMapping(value = "/auth/trytoken")
    public CommonRemoteVO auth_trytoken(@RequestParam("token") String token);

    @RequestMapping(value = "/member/info")
    public MemberRemoteVO member_info(@RequestParam("memberId") String memberId);

    @RequestMapping(value = "/gold/withdraw")
    public CommonRemoteVO gold_withdraw(@RequestParam("memberId") String memberId, @RequestParam("amount") int amount, @RequestParam("textSummary") String textSummary);

}
