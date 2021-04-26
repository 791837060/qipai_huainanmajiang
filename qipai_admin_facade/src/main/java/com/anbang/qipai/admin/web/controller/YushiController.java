package com.anbang.qipai.admin.web.controller;


import com.anbang.qipai.admin.msg.service.MemberYushiMsgService;
import com.anbang.qipai.admin.plan.bean.members.MemberYushi;
import com.anbang.qipai.admin.plan.service.membersservice.MemberGoldService;
import com.anbang.qipai.admin.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/12/9 10:31
 */
@CrossOrigin
@RestController
@RequestMapping("/yushi")
public class YushiController {

    @Autowired
    private MemberGoldService memberGoldService;

    @Autowired
    private MemberYushiMsgService memberYushiMsgService;

    /**
     * 查询游戏商城玉石列表
     *
     * @return
     */
    @RequestMapping(value = "/showyushi")
    public CommonVO showYushi() {
        CommonVO vo = new CommonVO();
        List<MemberYushi> yushiList = memberGoldService.findAllYushi();
        vo.setSuccess(true);
        vo.setMsg("yushiList");
        vo.setData(yushiList);
        return vo;
    }

    /**
     * 添加游戏商城玉石
     *
     * @param yushi
     * @return
     */
    @RequestMapping(value = "/addyushi")
    public CommonVO addYushi(MemberYushi yushi) {
        CommonVO vo = new CommonVO();
        if (yushi.getName() == null) {
            vo.setSuccess(false);
            vo.setMsg("at least one param is null");
            return vo;
        }
        if (yushi.getFirstDiscount() < 0.1 || yushi.getFirstDiscount() > 1) {
            vo.setSuccess(false);
            vo.setMsg("invalid first discount");
            return vo;
        }
        memberGoldService.addYushi(yushi);
        memberYushiMsgService.addYushi(yushi);
        vo.setSuccess(true);
        return vo;
    }

    /**
     * 删除游戏商城玉石
     *
     * @param yushiId
     * @return
     */
    @RequestMapping(value = "/deleteyushi")
    public CommonVO deleteYushiByIds(@RequestParam(value = "id") String[] yushiId) {
        CommonVO vo = new CommonVO();
        memberGoldService.deleteYushiByIds(yushiId);
        memberYushiMsgService.deleteYushi(yushiId);
        vo.setSuccess(true);
        return vo;
    }

    /**
     * 修改游戏商城玉石
     *
     * @param yushi
     * @return
     */
    @RequestMapping(value = "/updateyushi")
    public CommonVO updateYushi(MemberYushi yushi) {
        CommonVO vo = new CommonVO();
        if (yushi.getFirstDiscount() < 0.1 || yushi.getFirstDiscount() > 1) {
            vo.setSuccess(false);
            vo.setMsg("invalid first discount");
            return vo;
        }
        memberGoldService.updateYushi(yushi);
        memberYushiMsgService.updateYushi(yushi);
        vo.setSuccess(true);
        return vo;
    }

}
