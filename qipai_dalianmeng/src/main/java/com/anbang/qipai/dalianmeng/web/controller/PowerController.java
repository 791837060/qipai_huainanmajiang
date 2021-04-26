package com.anbang.qipai.dalianmeng.web.controller;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengYushiCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberPowerCmdService;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengYushiService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.MemberService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.PowerService;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameMemberTable;
import com.anbang.qipai.dalianmeng.plan.service.MemberAuthService;
import com.anbang.qipai.dalianmeng.plan.service.MemberDayResultDataService;
import com.anbang.qipai.dalianmeng.plan.service.PlayService;
import com.anbang.qipai.dalianmeng.util.TimeUtil;
import com.anbang.qipai.dalianmeng.web.vo.CommonVO;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/11/11 16:15
 */
@CrossOrigin
@RestController
@RequestMapping("/power")
public class PowerController {
    @Autowired
    private PowerService powerService;
    @Autowired
    private LianmengMemberService lianmengMemberService;
    @Autowired
    private MemberPowerCmdService memberPowerCmdService;
    @Autowired
    private MemberService memberService;
    //    @Autowired
//    private MemberBoxCmdService memberBoxCmdService;
    @Autowired
    private MemberAuthService memberAuthService;
    @Autowired
    private LianmengYushiCmdService lianmengYushiCmdService;
    @Autowired
    private LianmengYushiService lianmengYushiService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private PlayService playService;

    /**
     * 能量详情
     *
     * @param page
     * @param size
     * @param memberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/queryMemberPowerDetail")
    public CommonVO queryMemberPowerDetail(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                                           String memberId, String lianmengId, long queryTime,boolean searchGameCost) {
        CommonVO vo = new CommonVO();
        ListPage listPage = powerService.queryRecordByMemberIdAndLianmengId(page, size, memberId, lianmengId,queryTime,searchGameCost);
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 能量详情
     *
     * @param page
     * @param size
     * @param memberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/queryXiajiMemberPowerDetail")
    public CommonVO queryXiajiMemberPowerDetail(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                                                String memberId, String lianmengId, long queryTime,boolean searchGameCost) {
        CommonVO vo = new CommonVO();
        ListPage listPage = powerService.queryXiajiRecordByMemberIdAndLianmengId(page, size, memberId, lianmengId, queryTime,searchGameCost);
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 增加能量
     *
     * @param lianmengId
     * @param token
     * @param handleMemberId
     * @param handlePower
     * @return
     */
    @RequestMapping("/addPower")
    public CommonVO addPower(String lianmengId, String token, String handleMemberId, double handlePower) {
        CommonVO vo = new CommonVO();
        if (handlePower <= 0) {
            vo.setSuccess(false);
            vo.setMsg("HandlePowerException");
            return vo;
        }
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
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if(!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
            if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setMsg("member can not give to handleMember");
                vo.setSuccess(false);
                return vo;
            }
        }
        try {

            AccountingRecord rcd = memberPowerCmdService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handlePower, "give xiaji",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo = powerService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleMemberId, rcd);
            memberDayResultDataService.updatePower(memberLianmengDbo1.getMemberId(), lianmengId, -handlePower);
            memberDayResultDataService.updateMemberPowerForMember(memberLianmengDbo1.getMemberId(), lianmengId, -handlePower);

            AccountingRecord rcd2 = memberPowerCmdService.givePowerToMember(handleMemberId, lianmengId, handlePower, "xiaji give",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo2 = powerService.withdraw(handleMemberId, lianmengId, memberId, rcd2);
            memberDayResultDataService.updatePower(handleMemberId, lianmengId, handlePower);
            memberDayResultDataService.updateMemberPowerForMember(handleMemberId, lianmengId, handlePower);
            return vo;
        } catch (InsufficientBalanceException e) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        } catch (MemberNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }

    /**
     * 减少能量
     *
     * @param lianmengId
     * @param token
     * @param handleMemberId
     * @param handlePower
     * @return
     */
    @RequestMapping("/reducePower")
    public CommonVO reducePower(String lianmengId, String token, String handleMemberId, double handlePower) {
        CommonVO vo = new CommonVO();
        if (handlePower <= 0) {
            vo.setSuccess(false);
            vo.setMsg("HandlePowerException");
            return vo;
        }
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
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if(!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
            if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setMsg("member can not give to handleMember");
                vo.setSuccess(false);
                return vo;
            }
        }

        GameMemberTable memberTable = playService.findByMemberId(handleMemberId);
        if (memberTable!=null){
            vo.setSuccess(false);
            vo.setMsg("member is playing");
            return vo;
        }
        try {

            AccountingRecord rcd2 = memberPowerCmdService.withdraw(handleMemberId, lianmengId, handlePower, "xiaji reduce",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo2 = powerService.withdraw(handleMemberId, lianmengId, memberId, rcd2);
            memberDayResultDataService.updatePower(handleMemberId, lianmengId, -handlePower);
            memberDayResultDataService.updateMemberPowerForMember(handleMemberId, lianmengId, -handlePower);

            AccountingRecord rcd = memberPowerCmdService.givePowerToMember(memberLianmengDbo1.getMemberId(), lianmengId, handlePower, "reduce xiaji",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo = powerService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleMemberId, rcd);
            memberDayResultDataService.updatePower(memberLianmengDbo1.getMemberId(), lianmengId, handlePower);
            memberDayResultDataService.updateMemberPowerForMember(memberLianmengDbo1.getMemberId(), lianmengId, handlePower);
            return vo;
        } catch (InsufficientBalanceException e) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        } catch (MemberNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }

    /**
     * 减少能量
     *
     * @param lianmengId
     * @param token
     * @param handleMemberId
     * @return
     */
    @RequestMapping("/clearPower")
    public CommonVO clearPower(String lianmengId, String token, String handleMemberId) {
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
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }

        if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
            vo.setMsg("member can not give to handleMember");
            vo.setSuccess(false);
            return vo;
        }
        GameMemberTable memberTable = playService.findByMemberId(handleMemberId);
        if (memberTable!=null){
            vo.setSuccess(false);
            vo.setMsg("member is playing");
            return vo;
        }
        try {
            PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(handleMemberId, lianmengId);

            AccountingRecord rcd2 = memberPowerCmdService.withdraw(handleMemberId, lianmengId, powerAccountDbo.getBalance(), "xiaji reduce",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo2 = powerService.withdraw(handleMemberId, lianmengId, memberId, rcd2);
            memberDayResultDataService.updatePower(handleMemberId, lianmengId, -powerAccountDbo.getBalance());
            memberDayResultDataService.updateMemberPowerForMember(handleMemberId, lianmengId, -powerAccountDbo.getBalance());

            AccountingRecord rcd = memberPowerCmdService.givePowerToMember(memberLianmengDbo1.getMemberId(), lianmengId, powerAccountDbo.getBalance(), "reduce xiaji",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo = powerService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleMemberId, rcd);
            memberDayResultDataService.updatePower(memberLianmengDbo1.getMemberId(), lianmengId, powerAccountDbo.getBalance());
            memberDayResultDataService.updateMemberPowerForMember(memberLianmengDbo1.getMemberId(), lianmengId, powerAccountDbo.getBalance());
            return vo;
        } catch (InsufficientBalanceException e) {
            vo.setSuccess(false);
            vo.setMsg("InsufficientBalanceException");
            return vo;
        } catch (MemberNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }





    /**
     * 盟主给自己增加能量
     *
     * @param token
     * @param lianmengId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/givePowerToMember")
    public CommonVO givePowerToMember(String token, String lianmengId, double amount) {
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

        if (!memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            vo.setMsg("member not mengzhu");
            vo.setSuccess(false);
            return vo;
        }
        try {

            AccountingRecord rcd = memberPowerCmdService.givePowerToMember(memberId, lianmengId, amount, "mengzhu handle",
                    System.currentTimeMillis());
            PowerAccountingRecord dbo = powerService.withdraw(memberId, lianmengId, null, rcd);
            memberDayResultDataService.updatePower(memberId, lianmengId, amount);
            memberDayResultDataService.updateMemberPowerForMember(memberId, lianmengId, amount);
            Map data = new HashMap<>();
            PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(memberId, lianmengId);
            data.put("powerbalance", powerAccountDbo.getBalance());
            return vo;
        } catch (MemberNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }

    /**
     * 增加盟主联盟玉石
     *
     * @param
     * @param memberId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/giveYushiToMember")
    public CommonVO giveYushiToMember(String memberId, Integer amount) {
        CommonVO vo = new CommonVO();
        List<MemberLianmengDbo> memberList = lianmengMemberService.findByMemberId(memberId);
        for (MemberLianmengDbo memberLianmengDbo : memberList) {
            if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
                try {
                    AccountingRecord rcd = lianmengYushiCmdService.giveYushiToAgent(memberId, amount, "mengzhu handle", System.currentTimeMillis());
                    LianmengYushiAccountingRecord record = lianmengYushiService.withdraw(memberId, rcd,null,null);


                    return vo;
                } catch (MemberNotFoundException e) {
                    vo.setSuccess(false);
                    vo.setMsg("MemberNotFoundException");
                    return vo;
                }
            }
        }
        vo.setSuccess(false);
        vo.setMsg("member is not mengzhu");
        return vo;
    }

    /**
     * 当前能量余额
     *
     * @param memberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/nowPowerForRemote")
    public CommonVO nowPowerForRemote(String memberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(memberId, lianmengId);
        if (powerAccountDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("MemberPowerAccountNotFoundException");
            return vo;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("powerbalance", powerAccountDbo.getBalance());
        vo.setData(data);
        return vo;
    }

    /**
     * 当前能量余额
     *
     * @param memberIds
     * @param lianmengId
     * @return
     */
    @RequestMapping("/nowPowerFor_u3d")
    public CommonVO nowPowerForU3d(String[] memberIds, String lianmengId) {
        CommonVO vo = new CommonVO();
        List<PowerAccountDbo> powerAccountDboList = new ArrayList<>();
        for (String memberId : memberIds) {
            PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(memberId, lianmengId);
            if (powerAccountDbo == null) {
                vo.setSuccess(false);
                vo.setMsg("MemberPowerAccountNotFoundException");
                return vo;
            }
            powerAccountDboList.add(powerAccountDbo);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("powerAccountDboList", powerAccountDboList);
        vo.setData(data);
        return vo;
    }

    /**
     * 盟主钻石流水记录
     */
    @RequestMapping("/diamondRecord")
    public CommonVO diamondRecord(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                                  String token ,String lianmengId ,long queryTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        long startTime= TimeUtil.getDayStartTime(queryTime);
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            ListPage listPage = lianmengYushiService.queryRecordByMemberIdAndLianmengId(page, size, memberId, lianmengId, startTime, queryTime);

            Map data = new HashMap<>();
            data.put("listPage", listPage);
            vo.setData(data);
        } else {
            vo.setSuccess(false);
            vo.setMsg("member is not mengzhu");
        }
        return vo;
    }

}
