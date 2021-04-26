package com.anbang.qipai.dalianmeng.web.controller;

import com.anbang.qipai.dalianmeng.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberPowerCmdService;
import com.anbang.qipai.dalianmeng.cqrs.c.service.MemberScoreCmdService;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.PowerService;
import com.anbang.qipai.dalianmeng.cqrs.q.service.ScoreService;
import com.anbang.qipai.dalianmeng.plan.service.MemberAuthService;
import com.anbang.qipai.dalianmeng.plan.service.MemberDayResultDataService;
import com.anbang.qipai.dalianmeng.web.vo.CommonVO;
import com.dml.accounting.AccountingRecord;
import com.dml.accounting.InsufficientBalanceException;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private PowerService powerService;

    @Autowired
    private MemberPowerCmdService memberPowerCmdService;

    @Autowired
    private MemberScoreCmdService memberScoreCmdService;
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private MemberAuthService memberAuthService;


    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    /**
     * 提取贡献值
     * @param token
     * @param lianmengId
     *
     * @return
     */
    @RequestMapping(value = "/contributionWithdraw")
    public CommonVO contributionWithdraw(String token, String lianmengId) {
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
        if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
            vo.setSuccess(false);
            vo.setMsg("member is chengyuan");
            return vo;
        }
        ScoreAccountDbo scoreAccountDbo=scoreService.findScoreAccountDbo(memberId,lianmengId);
        if (scoreAccountDbo==null){
            vo.setSuccess(false);
            vo.setMsg("find error");
            return vo;
        }
        Double amount1=scoreAccountDbo.getBalance();
        if (amount1!=0){
            try {
                AccountingRecord rcd = memberScoreCmdService.withdraw(memberId, lianmengId, amount1, "contributionWithdraw",
                        System.currentTimeMillis());
                ScoreAccountingRecord dbo = scoreService.withdraw(memberId, lianmengId, memberId, rcd);
                AccountingRecord rcd2 = memberPowerCmdService.givePowerToMember(memberId, lianmengId, amount1, "contributionWithdraw",
                        System.currentTimeMillis());
                PowerAccountingRecord dbo2 = powerService.withdraw(memberId, lianmengId, memberId, rcd2);
                memberDayResultDataService.updatePower(memberId, lianmengId, amount1);
                memberDayResultDataService.updateMemberPowerForMember(memberId, lianmengId, amount1);
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
        return vo;
    }

    /**
     * 查询流水
     * @param page
     * @param size
     * @param token
     * @param lianmengId
     * @return
     */

    @RequestMapping("/findScore")
    public CommonVO findByMemberIdAndLianmengId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
                                                String token, String lianmengId, long queryTime){
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
        ListPage listPage= scoreService.findByMemberIdAndLianmengId(page, size, memberId, lianmengId,queryTime);
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(memberId,lianmengId);
        data.put("nowPower",powerAccountDbo.getBalance());
        ScoreAccountDbo scoreAccountDbo = scoreService.findScoreAccountDbo(memberId,lianmengId);
        data.put("nowScore",scoreAccountDbo.getBalance());
        vo.setData(data);
        return vo;
    }

}
