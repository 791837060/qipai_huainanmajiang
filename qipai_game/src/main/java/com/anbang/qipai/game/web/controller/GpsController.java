package com.anbang.qipai.game.web.controller;


import com.anbang.qipai.game.plan.bean.members.MemberLatAndLon;
import com.anbang.qipai.game.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.game.plan.service.MemberAuthService;
import com.anbang.qipai.game.plan.service.MemberLatAndLonService;
import com.anbang.qipai.game.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.game.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定位
 *
 */
@RestController
@RequestMapping("/gps")
public class GpsController {
    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private MemberLoginLimitRecordService memberLoginLimitRecordService;

    @Autowired
    private MemberLatAndLonService memberLatAndLonService;


    @RequestMapping("/queryLatAndLon")
    public CommonVO querylatAndlon(String token, @RequestBody List<String> memberIds, String roomNo){
        CommonVO vo=new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLoginLimitRecord record = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (record != null) {
            vo.setSuccess(false);
            vo.setMsg("login limited");
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
