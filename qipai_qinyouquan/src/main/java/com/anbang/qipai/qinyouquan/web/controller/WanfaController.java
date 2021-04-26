package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.Identity;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.plan.bean.LianmengWanfa;
import com.anbang.qipai.qinyouquan.plan.service.LianmengWanfaService;
import com.anbang.qipai.qinyouquan.plan.service.MemberAuthService;
import com.anbang.qipai.qinyouquan.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/wanfa")
public class WanfaController {

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private LianmengWanfaService lianmengWanfaService;

    @RequestMapping("/findAllWanfa")
    public CommonVO findAllWanfa(String token, String lianmengId){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        vo.setData(lianmengWanfaService.findLianmengWanfaBylianmengId(lianmengId));
        return vo;
    }

    @RequestMapping(value = "/addWanfa", method = RequestMethod.POST)
    @ResponseBody
    public CommonVO addwanfa(String token,  @RequestBody LianmengWanfa wanfa ){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, wanfa.getLianmengId());
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  wanfa.getLianmengId()) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  wanfa.getLianmengId());
            }

        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
            vo.setMsg("member not mengzhu");
            vo.setSuccess(false);
            return vo;
        }
        lianmengWanfaService.addNewWanfa(wanfa);
        vo.setMsg("success");
       return vo;
    }

    @RequestMapping("/deleteWanfa")
    public CommonVO deleteWanfa(String token ,String lianmengId,String wanfaId){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  lianmengId);
            }

        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
            vo.setMsg("member not mengzhu");
            vo.setSuccess(false);
            return vo;
        }
        lianmengWanfaService.deleteLianmengWanfa(wanfaId);
        vo.setMsg("delete success");
        return vo;
    }

    @RequestMapping(value = "/updateWanfa", method = RequestMethod.POST)
    @ResponseBody
    public CommonVO updateWanfa(String token,@RequestBody LianmengWanfa wanfa){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, wanfa.getLianmengId());
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  wanfa.getLianmengId()) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(),  wanfa.getLianmengId());
            }

        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
            vo.setMsg("member not mengzhu");
            vo.setSuccess(false);
            return vo;
        }
        lianmengWanfaService.updateLianmengWanfa(wanfa);
        vo.setMsg("update success");
        return vo;
    }

}
