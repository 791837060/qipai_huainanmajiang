package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.c.service.MemberDiamondCmdService;

import com.anbang.qipai.qinyouquan.cqrs.q.dbo.*;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengDiamondService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengMemberService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.LianmengService;
import com.anbang.qipai.qinyouquan.cqrs.q.service.MemberDiamondService;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameMemberTable;
import com.anbang.qipai.qinyouquan.plan.service.GameHistoricalJuResultService;
import com.anbang.qipai.qinyouquan.plan.service.MemberAuthService;
import com.anbang.qipai.qinyouquan.plan.service.MemberDayResultDataService;
import com.anbang.qipai.qinyouquan.plan.service.PlayService;
import com.anbang.qipai.qinyouquan.web.vo.CommonVO;
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
@RequestMapping("/diamond")
public class MemberDiamondController {
    @Autowired
    private MemberDiamondService memberDiamondService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private MemberDiamondCmdService memberDiamondCmdService;

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengDiamondCmdService lianmengDiamondCmdService;

    @Autowired
    private LianmengDiamondService lianmengDiamondService;

    @Autowired
    private GameHistoricalJuResultService gameHistoricalJuResultService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private PlayService playService;

    @Autowired
    private LianmengService lianmengService;

    /**
     * 增加钻石
     *
     * @param lianmengId
     * @param token
     * @param handleMemberId
     * @param handleDiamond
     * @return
     */
    @RequestMapping("/addDiamond")
    public CommonVO addDiamond(String lianmengId, String token, String handleMemberId, int handleDiamond) {
        CommonVO vo = new CommonVO();
        if (handleDiamond <= 0) {
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

        if (!handleMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
            vo.setMsg("member can not give to handleMember");
            vo.setSuccess(false);
            return vo;
        }
        try {
            if (memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                AccountingRecord rcd = lianmengDiamondCmdService.withdraw(memberLianmengDbo1.getMemberId(), handleDiamond, "give xiaji", System.currentTimeMillis());
                LianmengDiamondAccountingRecord dbo = lianmengDiamondService.withdraw(memberLianmengDbo1.getMemberId(), rcd, lianmengId, null);
            } else {
                AccountingRecord rcd = memberDiamondCmdService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleDiamond, "give xiaji", System.currentTimeMillis());
                MemberDiamondAccountingRecord dbo = memberDiamondService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleMemberId, rcd, null);
                memberDayResultDataService.increaseDiamond(memberLianmengDbo1.getMemberId(), lianmengId, -handleDiamond);
                memberDayResultDataService.increaseMemberDiamond(memberLianmengDbo1.getMemberId(), lianmengId, -handleDiamond);
            }

            AccountingRecord rcd2 = memberDiamondCmdService.giveDiamondToMember(handleMemberId, lianmengId, handleDiamond, "xiaji give", System.currentTimeMillis());
            MemberDiamondAccountingRecord dbo2 = memberDiamondService.withdraw(handleMemberId, lianmengId, memberId, rcd2, null);
            memberDayResultDataService.increaseDiamond(handleMemberId, lianmengId, handleDiamond);
            memberDayResultDataService.increaseMemberDiamond(handleMemberId, lianmengId, handleDiamond);
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
     * 减少钻石
     *
     * @param lianmengId
     * @param token
     * @param handleMemberId
     * @param handleDiamond
     * @return
     */
    @RequestMapping("/reduceDiamond")
    public CommonVO reduceDiamond(String lianmengId, String token, String handleMemberId, int handleDiamond) {
        CommonVO vo = new CommonVO();
        if (handleDiamond <= 0) {
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
            AccountingRecord rcd2 = memberDiamondCmdService.withdraw(handleMemberId, lianmengId, handleDiamond, "xiaji reduce", System.currentTimeMillis());
            MemberDiamondAccountingRecord dbo2 = memberDiamondService.withdraw(handleMemberId, lianmengId, memberId, rcd2, null);
            memberDayResultDataService.increaseDiamond(handleMemberId, lianmengId, -handleDiamond);
            memberDayResultDataService.increaseMemberDiamond(handleMemberId, lianmengId, -handleDiamond);
            if (memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
                AccountingRecord rcd = lianmengDiamondCmdService.giveDiamondToMengzhu(memberLianmengDbo1.getMemberId(), handleDiamond, "reduce xiaji", System.currentTimeMillis());
                LianmengDiamondAccountingRecord dbo = lianmengDiamondService.withdraw(memberLianmengDbo1.getMemberId(), rcd, handleMemberId, null);
            } else {
                AccountingRecord rcd = memberDiamondCmdService.giveDiamondToMember(memberLianmengDbo1.getMemberId(), lianmengId, handleDiamond, "reduce xiaji", System.currentTimeMillis());
                MemberDiamondAccountingRecord dbo = memberDiamondService.withdraw(memberLianmengDbo1.getMemberId(), lianmengId, handleMemberId, rcd, null);
                memberDayResultDataService.increaseDiamond(memberLianmengDbo1.getMemberId(), lianmengId, handleDiamond);
                memberDayResultDataService.increaseMemberDiamond(memberLianmengDbo1.getMemberId(), lianmengId, handleDiamond);
            }


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
     * 当前钻石余额
     *
     * @param memberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/nowPowerForRemote")
    public CommonVO nowPowerForRemote(String memberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(memberId, lianmengId);
        if (memberDiamondAccountDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("MemberPowerAccountNotFoundException");
            return vo;
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("powerbalance", memberDiamondAccountDbo.getBalance());
        vo.setData(data);
        return vo;
    }

    /**
     * 当前钻石余额
     *
     * @param memberIds
     * @param lianmengId
     * @return
     */
    @RequestMapping("/nowPowerFor_u3d")
    public CommonVO nowPowerForU3d(String[] memberIds, String lianmengId) {
        CommonVO vo = new CommonVO();
        List<MemberDiamondAccountDbo> memberDiamondAccountDboList = new ArrayList<>();
        for (String memberId : memberIds) {
            MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(memberId, lianmengId);
            if (memberDiamondAccountDbo == null) {
                vo.setSuccess(false);
                vo.setMsg("MemberPowerAccountNotFoundException");
                return vo;
            }
            memberDiamondAccountDboList.add(memberDiamondAccountDbo);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("powerAccountDboList", memberDiamondAccountDboList);
        vo.setData(data);
        return vo;
    }

    /**
     * 盟主钻石流水记录
     */
    @RequestMapping("/lianmengDiamondRecord")
    public CommonVO lianmengDiamondRecord(String lianmengId, String token, long startTime, long endTime) {
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

        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            vo.setSuccess(false);
            vo.setMsg("member is not mengzhu");
            return vo;
        }
        Map data = new HashMap();
        Map map = gameHistoricalJuResultService.queryLianmengDiamondCost( endTime, lianmengId);
        data.put("map", map);
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }

    /**
     * 成员钻石流水记录_u3d
     */
    @RequestMapping("/memberDiamondRecord")
    public CommonVO memberDiamondRecord(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                                        String lianmengId, String token, String searchMemberId , long dayTime, boolean gameCost) {
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
        MemberLianmengDbo handleMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
        if (handleMemberLianmengDbo == null || handleMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        Map data = new HashMap();
        ListPage listPage;
        if (handleMemberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            listPage = lianmengDiamondService.queryRecordByMemberIdAndLianmengId(page,size,memberId,lianmengId,dayTime, gameCost);

        }else {
            listPage = memberDiamondService.queryRecordByMemberIdsAndLianmengId(page,size,searchMemberId,lianmengId,dayTime, gameCost);

        }
        data.put("listPage", listPage);
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }

    /**
     * 盟主钻石流水记录_web
     */
    @RequestMapping("/mengzhuDiamondRecord")
    public CommonVO mengzhuDiamondRecord(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                                         String lianmengId, long dayTime, boolean gameCost) {
        CommonVO vo = new CommonVO();
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        Map data = new HashMap();
        if (allianceDbo==null){
            vo.setMsg("lianmeng not found");
            vo.setSuccess(false);
            return vo;
        }
        ListPage listPage = lianmengDiamondService.queryRecordByMemberIdAndLianmengId(page,size,allianceDbo.getMengzhu(),lianmengId,dayTime, gameCost);
        data.put("listPage", listPage);
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }

    /**
     * 联盟游戏钻石记录_web
     */
    @RequestMapping("/lianmengGameDiamondRecord_web")
    public CommonVO lianmengDiamondRecord_web(String lianmengId,  long dayTime ) {
        CommonVO vo = new CommonVO();
        Map data = new HashMap();
        Map map = gameHistoricalJuResultService.queryLianmengDiamondCost(dayTime, lianmengId);
        data.put("map", map);
        vo.setSuccess(true);
        vo.setData(data);
        return vo;
    }


}
