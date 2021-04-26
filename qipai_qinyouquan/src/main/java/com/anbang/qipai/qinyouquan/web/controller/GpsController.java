package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberLianmengDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.plan.bean.MemberLatAndLon;
import com.anbang.qipai.qinyouquan.plan.service.MemberAuthService;
import com.anbang.qipai.qinyouquan.plan.service.MemberLatAndLonService;
import com.anbang.qipai.qinyouquan.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定位
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/gps")
public class GpsController {
    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;

    @RequestMapping("/queryLatAndLon")
    public CommonVO querylatAndlon(String token,String lianmengId,@RequestBody List<String> memberIds,String roomNo){
        CommonVO vo=new CommonVO();
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

        List<MemberLatAndLon> list = memberLatAndLonService.find(memberIds,roomNo);
        if (list!=null){
            vo.setData(list);
            vo.setMsg("查询成功");
            return vo;
        }else {
            vo.setSuccess(false);
            vo.setMsg("查询失败");
            return vo;
        }
    }

}
