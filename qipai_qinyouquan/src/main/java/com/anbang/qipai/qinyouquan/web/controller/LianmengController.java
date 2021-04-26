package com.anbang.qipai.qinyouquan.web.controller;

import com.anbang.qipai.qinyouquan.cqrs.c.domain.CreateLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.JoinLianmengResult;
import com.anbang.qipai.qinyouquan.cqrs.c.domain.MemberNotFoundException;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengCmdService;
import com.anbang.qipai.qinyouquan.cqrs.c.service.LianmengDiamondCmdService;
import com.anbang.qipai.qinyouquan.cqrs.q.dbo.*;
import com.anbang.qipai.qinyouquan.cqrs.q.service.*;
import com.anbang.qipai.qinyouquan.plan.bean.game.GameMemberTable;
import com.anbang.qipai.qinyouquan.plan.bean.result.GameHistoricalJuResult;
import com.anbang.qipai.qinyouquan.plan.service.LianmengApplyService;
import com.anbang.qipai.qinyouquan.plan.service.MemberAuthService;
import com.anbang.qipai.qinyouquan.plan.service.MemberDayResultDataService;
import com.anbang.qipai.qinyouquan.plan.service.PlayService;
import com.anbang.qipai.qinyouquan.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.qinyouquan.remote.vo.CommonRemoteVO;
import com.anbang.qipai.qinyouquan.web.vo.*;
import com.dml.accounting.AccountingRecord;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/lianmeng")
public class LianmengController {

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengCmdService lianmengCmdService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private QipaiMembersRemoteService qipaiMembersRomoteService;

    @Autowired
    private LianmengDiamondCmdService lianmengDiamondCmdService;

    @Autowired
    private LianmengDiamondService lianmengDiamondService;

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private PlayService playService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private MemberDiamondService memberDiamondService;

    @Autowired
    private LianmengApplyService lianmengApplyService;

    /**
     * 管理后台-大联盟详情查询
     */
    @RequestMapping("/querylianmeng")
    public CommonVO queryLianmeng(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId, String memberId) {
        CommonVO vo = new CommonVO();
        ListPage listPage = lianmengService.queryLianmeng(page, size, lianmengId, memberId);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 管理后台-大联盟详情查询
     */
    @RequestMapping("/querylianmengBymengzhu")
    public CommonVO querylianmengBymengzhu(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String mengzhuId) {
        CommonVO vo = new CommonVO();
        ListPage listPage = lianmengService.querylianmengBymengzhu(page, size, mengzhuId);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 管理后台-大联盟详情-合伙人
     */
    @RequestMapping("/querylianmeng_hehuoren")
    public CommonVO queryLianmengDetailHehuoren(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId) {
        CommonVO vo = new CommonVO();
        LianmengDetail lianmengDetail = lianmengService.queryLianmengDetail(lianmengId);
        ListPage listPage = lianmengService.queryLianmengHehuoren(page, size, lianmengId);
        Map data = new HashMap();
        data.put("lianmengDetail", lianmengDetail);
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 管理后台-大联盟详情-玩家
     */
    @RequestMapping("/querylianmeng_member")
    public CommonVO queryLianmengDetailMember(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId) {
        CommonVO vo = new CommonVO();
        LianmengDetail lianmengDetail = lianmengService.queryLianmengDetail(lianmengId);
        ListPage listPage = lianmengService.queryLianmengMember(page, size, lianmengId);
        Map data = new HashMap();
        data.put("lianmengDetail", lianmengDetail);
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 管理后台-联盟管理员
     */
    @RequestMapping("/lianmeng_mengzhu")
    public CommonVO lianmeng_mengzhu(String memberId) {
        CommonVO vo = new CommonVO();
        List<AgentVO> agentVOList = lianmengService.queryMengzhu(memberId);
        int count = agentVOList.size();
        Map data = new HashMap();
        data.put("agentVOList", agentVOList);
        data.put("totalCount", count);
        vo.setData(data);
        return vo;
    }

    /**
     * 管理后台-大联盟消耗
     */
    @RequestMapping("/lianmeng_cost")
    public CommonVO lianmeng_cost(String goldSort, String gameSort, String agentId, String lianmengId, Long startTime, Long endTime) {
        CommonVO vo = new CommonVO();
        if (startTime == null) {
            startTime = 0L;
        }
        if (endTime == null) {
            endTime = 0L;
        }

        List<LianmengCost> lianmengCost = lianmengService.queryLianmengCost(agentId, lianmengId, goldSort, gameSort, startTime, endTime);
        Map data = new HashMap();
        data.put("lianmengCost", lianmengCost);
        vo.setData(data);
        return vo;
    }


    /**
     * 邀请加入联盟
     *
     * @param token      令牌
     * @param lianmengId 联盟ID
     */
    @RequestMapping("/invite_member")
    public CommonVO invite(String token, String lianmengId, String inviteMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberDbo memberDbo = memberService.findMemberDboByMemberId(inviteMemberId);
        if (memberDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("member not found");
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("lianmeng not found");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)) {
            if (StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
                vo.setSuccess(false);
                vo.setMsg("member is chengyuan");
                return vo;
            } else {
                memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }
        }
        MemberLianmengDbo inviteMemberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(inviteMemberId, lianmengId);
        if (inviteMemberLianmengDbo != null) {
            vo.setSuccess(false);
            vo.setMsg("already in lianmeng");
            return vo;
        }
        try {
            JoinLianmengResult joinLianmengResult = lianmengCmdService.joinLianmeng(inviteMemberId, lianmengId);
            lianmengService.joinLianmeng(inviteMemberId, lianmengId, memberLianmengDbo.getMemberId(), joinLianmengResult);
        } catch (Exception e) {
            lianmengService.reJoinLianmeng(inviteMemberId, lianmengId, memberLianmengDbo.getMemberId());
            MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(inviteMemberId, lianmengId);
//            e.printStackTrace();
//            return vo;
        }
        memberDayResultDataService.memberInit(inviteMemberId, lianmengId);
        return vo;
    }

    /**
     * 申请加入联盟
     *
     * @param token      令牌
     * @param lianmengId 联盟id
     */
    @RequestMapping("/apply_member")
    public CommonVO apply(String token, String lianmengId, String inviteMemberId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("lianmeng not found");
            return vo;
        }

        MemberLianmengDbo memberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(inviteMemberId, lianmengId);
        if (memberLianmengDbo == null) {
            vo.setMsg("not found ren");
            vo.setSuccess(false);
            return vo;
        }
        if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("member is chengyuan");
            return vo;
        }
        MemberLianmengDbo inviteMemberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberId, lianmengId);
        if (inviteMemberLianmengDbo != null) {
            vo.setSuccess(false);
            vo.setMsg("already in lianmeng");
            return vo;
        }
        List<MemberApplyingRecord> memberApplyingRecord = lianmengService.findByMemberIdAndLianmengAndIdentity(memberId, lianmengId);
        if (!memberApplyingRecord.isEmpty()) {
            for (MemberApplyingRecord applyingRecord : memberApplyingRecord) {
                if (applyingRecord != null && applyingRecord.getState().equals(ApplyState.APPLYING)) {
                    vo.setSuccess(false);
                    vo.setMsg("apply already");
                    return vo;
                }
            }
        }
        lianmengService.apply(memberId, lianmengId, Identity.CHENGYUAN, inviteMemberId);
        vo.setMsg("apply success");
        return vo;
    }


    /**
     * 查询具有邀请权限的人
     */
    @RequestMapping("/referer")
    public CommonVO queryReferer(String token, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        List<MemberLianmengDbo> memberByLianmengIdAndIdentity = lianmengService.findMemberByLianmengIdAndIdentity(lianmengId, Identity.MENGZHU);

        memberByLianmengIdAndIdentity.addAll(lianmengService.findMemberByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN));
        Map data = new HashMap<>();
        data.put("Identity", memberByLianmengIdAndIdentity);
        vo.setData(data);
        vo.setMsg("success");
        return vo;
    }

    /**
     * 申请列表
     *
     * @param token 令牌
     */
    @RequestMapping("/query_apply")
    public CommonVO queryApply(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
                               String token, String lianmengId, long queryTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        ListPage listPage = lianmengService.queryApplyingRecord(page, size, lianmengId, queryTime);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 申请记录
     *
     * @param token
     * @return
     */
    @RequestMapping("/query_applyrecord")
    public CommonVO queryApplyRecord(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30")
            int size, String token, String lianmengId, long queryTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        ListPage listPage = lianmengService.queryApplyRecord(page, size, lianmengId, queryTime);
        Map data = new HashMap();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 通过联盟申请
     *
     * @param token   令牌
     * @param applyId 申请ID
     */
    @RequestMapping("/pass")
    public CommonVO pass(String token, String applyId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberApplyingRecord memberApplyingRecord = lianmengService.findMemberApplyingRecordByApplyId(applyId);
        if (!ApplyState.APPLYING.equals(memberApplyingRecord.getState())) {
            vo.setSuccess(false);
            vo.setMsg("not applying");
            return vo;
        }
        MemberLianmengDbo inviteMemberLianmengDbo = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(memberApplyingRecord.getMemberId(), memberApplyingRecord.getLianmengId());
        if (inviteMemberLianmengDbo != null) {
            lianmengService.updateStateAndAuditorById(applyId, memberApplyingRecord.getMemberId());
            vo.setSuccess(false);
            vo.setMsg("already in lianmeng");
            return vo;
        }
        if (Identity.CHENGYUAN.equals(memberApplyingRecord.getIdentity())) {
            try {
                JoinLianmengResult joinLianmengResult = lianmengCmdService.joinLianmeng(memberApplyingRecord.getMemberId(), memberApplyingRecord.getLianmengId());
                lianmengService.passApply(applyId, memberId, joinLianmengResult);
            } catch (Exception e) {
                lianmengService.repassApply(applyId, memberId);
                MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(memberApplyingRecord.getMemberId(), memberApplyingRecord.getLianmengId());
                e.printStackTrace();
                return vo;
            }
            memberDayResultDataService.memberInit(memberApplyingRecord.getMemberId(), memberApplyingRecord.getLianmengId());
        }
        return vo;
    }

    /**
     * 拒绝联盟申请
     *
     * @param token   令牌
     * @param applyId 申请ID
     */
    @RequestMapping("/refuse")
    public CommonVO refuse(String token, String applyId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        lianmengService.refuseApply(applyId, memberId);
        return vo;
    }

    /**
     * 查询加入的联盟
     */
    @RequestMapping("/query")
    public CommonVO queryLianmeng(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        List<LianmengVO> lianmengVOList = lianmengService.findLianmengByMemberId(memberId);
        Map data = new HashMap();
        data.put("lianmengVOList", lianmengVOList);
        vo.setData(data);
        return vo;
    }

    /**
     * 查询管理员的联盟
     */
    @RequestMapping("/query_guanli")
    public CommonVO queryLianmeng_guanli(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        List<LianmengVO> lianmengVOList = lianmengService.findGuanliyuanLianmeng(memberId);
        Map data = new HashMap();
        data.put("lianmengVOList", lianmengVOList);
        vo.setData(data);
        return vo;
    }

    /**
     * 查询联盟的合伙人的玩家id
     */
    @RequestMapping("/query_hehuoren_member")
    public CommonVO query_hehuoren_member(String lianmengId) {
        CommonVO vo = new CommonVO();
        List<HehuorenVO> hehuorenList = new ArrayList<>();
        List<MemberLianmengDbo> memberLianmengDboList = lianmengService.findMemberByLianmengIdAndIdentity(lianmengId, Identity.HEHUOREN);
        memberLianmengDboList.addAll(lianmengService.findMemberByLianmengIdAndIdentity(lianmengId, Identity.MENGZHU));
        for (MemberLianmengDbo memberLianmengDbo : memberLianmengDboList) {
            MemberDbo memberDbo = memberService.findMemberDboByMemberId(memberLianmengDbo.getMemberId());
            if (memberDbo != null) {
                HehuorenVO hehuoren = new HehuorenVO();
                hehuoren.setNickname(memberDbo.getNickname());
                hehuoren.setMemberId(memberDbo.getId());
                hehuorenList.add(hehuoren);
            }
        }
        Map data = new HashMap();
        data.put("hehuorenList", hehuorenList);
        vo.setData(data);
        return vo;
    }

    @RequestMapping("/showOnlineMember")
    public CommonVO banDeskmateRecord(String lianmengId) {
        CommonVO vo = new CommonVO();
        List<MemberLianmengDbo> onlineMemberList = lianmengMemberService.findOnlineMemberByLianmengId(lianmengId);
        for (int i = 0; i < onlineMemberList.size(); i++) {
            if (playService.findByMemberId(onlineMemberList.get(i).getMemberId()) != null) {
                onlineMemberList.remove(i);
                i--;
            }
        }
        Map data = new HashMap<>();
        data.put("onlineMemberList", onlineMemberList);
        vo.setData(data);
        return vo;
    }

    /**
     * 用户开通联盟
     */
    @RequestMapping("/applyCreateLianmeng")
    public CommonVO agentApplyLianmeng(String token, String lianmengName) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberDbo memberDbo = memberService.findMemberDboByMemberId(memberId);
        if (!memberDbo.isQinyouquan()) {
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }

        CreateLianmengResult createLianmengResult;
        try {
            //创建新联盟账户
            createLianmengResult = lianmengCmdService.createNewLianmengId(memberId, System.currentTimeMillis());
            if (!lianmengDiamondCmdService.createAccountForAgentIsExist(memberId)) {
                String accountId = lianmengDiamondCmdService.createAccountForMengzhu(memberId, System.currentTimeMillis());
                lianmengDiamondService.newLianmengYushiAccount(accountId, memberId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.createLianmeng(createLianmengResult, lianmengName, memberId);
        memberDayResultDataService.memberInit(memberId, allianceDbo.getId());
        Map data = new HashMap();
        data.put("lianmengId", allianceDbo.getId());
        vo.setData(data);
        return vo;

    }

    /**
     * 解散联盟
     */
    @RequestMapping("/lianmeng_remove")
    public CommonVO lianmeng_remove(String token, String lianmengId) {
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
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }
        lianmengService.removeAlliance(lianmengId);
        try {
            lianmengCmdService.removeLianmeng(lianmengId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    @RequestMapping("/update_lianmeng_setting")
    public CommonVO update_lianmeng_setting(String token, String lianmengId, boolean renshuHide, boolean kongzhuoqianzhi, boolean nicknameHide, boolean idHide, boolean banAlliance,
                                            boolean zhuomanHide, int buzhunbeituichushichang, boolean zidongzhunbei, boolean lianmengIdHide) {
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
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }

        }
        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }

        lianmengService.updateSetting(lianmengId, renshuHide, kongzhuoqianzhi, nicknameHide, idHide, banAlliance, zhuomanHide, buzhunbeituichushichang, zidongzhunbei, lianmengIdHide);
        return vo;
    }

    /**
     * 增加盟主联盟钻石
     *
     * @param
     * @param memberId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/giveDiamondToMember")
    public CommonVO giveYushiToMember(String memberId, Integer amount) {
        CommonVO vo = new CommonVO();
        if (lianmengService.findByMengzhuId(memberId).isEmpty()) {
            vo.setSuccess(false);
            vo.setMsg("member is not mengzhu");
            return vo;
        }
        try {
            AccountingRecord rcd = lianmengDiamondCmdService.giveDiamondToMengzhu(memberId, amount, "mengzhu handle", System.currentTimeMillis());
            LianmengDiamondAccountingRecord record = lianmengDiamondService.withdraw(memberId, rcd, null, null);
            return vo;
        } catch (MemberNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }

    /**
     * 盟主兑换联盟钻石
     *
     * @param memberId 玩家ID
     * @param amount   数量
     */
    @RequestMapping(value = "/mengzhuExchangeDiamond")
    public CommonVO mengzhuExchangeDiamond(String memberId, Integer amount) {
        CommonVO vo = new CommonVO();
        if (lianmengService.findByMengzhuId(memberId).isEmpty()) {
            vo.setSuccess(false);
            vo.setMsg("member is not mengzhu");
            return vo;
        }
        CommonRemoteVO commonRemoteVO = qipaiMembersRomoteService.gold_withdraw(memberId, amount , "mengzhu exchange");
        if (!commonRemoteVO.isSuccess()) {
            vo.setSuccess(false);
            vo.setMsg(commonRemoteVO.getMsg());
            return vo;
        }
        try {
            AccountingRecord rcd = lianmengDiamondCmdService.giveDiamondToMengzhu(memberId, amount, "mengzhu exchange", System.currentTimeMillis());
            LianmengDiamondAccountingRecord record = lianmengDiamondService.withdraw(memberId, rcd, null, null);
            return vo;
        } catch (MemberNotFoundException e) {
            qipaiMembersRomoteService.gold_withdraw(memberId, -amount , "mengzhu exchange");
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
    }

    /**
     * 查询联盟是否存在
     *
     * @param token
     * @param lianmengId 联盟id
     * @return
     */
    @RequestMapping(value = "/queryLianmendById")
    public CommonVO queryLianmendById(String token, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        AllianceDbo allianceDbo = lianmengService.findAllianceDboById(lianmengId);
        if (allianceDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("lianmeng not found");
            return vo;
        }
        vo.setData(allianceDbo);
        return vo;
    }


    /**
     * 后台-圈主管理-查询合伙人
     */
    @RequestMapping(value = "/querylianmeng_hehuorens",method = RequestMethod.POST)
    public CommonVO queryLianmengDetailHehuorens(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId) {
        CommonVO vo = new CommonVO();
        LianmengDetail lianmengDetail = lianmengService.queryLianmengDetail(lianmengId);
        ListPage listPage = lianmengService.queryLianmengHehuorens(page, size, lianmengId);
        Map data = new HashMap();
        data.put("lianmengDetail", lianmengDetail);
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 后台-查询成员
     */
    @RequestMapping(value = "/querylianmeng_members",method = RequestMethod.POST)
    public CommonVO queryLianmengMember(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId){
        CommonVO vo = new CommonVO();
        LianmengDetail lianmengDetail = lianmengService.queryLianmengDetail(lianmengId);
        ListPage listPage = lianmengService.queryLianmengMembers(page,size,lianmengId);
        Map data = new HashMap();
        data.put("lianmengDetail", lianmengDetail);
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 后台——战绩查询
     */
    @RequestMapping("/web_queryLianmengJuResult")
    public CommonVO queryLianmengJuResult(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, String lianmengId,String playerId,long startTime,long endTime){
        CommonVO vo = new CommonVO();
        ListPage listPage = lianmengService.queryLianmengJuResult(page, size, lianmengId,playerId,startTime,endTime);
        Map data = new HashMap();
        data.put("listPage",listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 对局详情
     */
    @RequestMapping("/playerParticulars")
    public CommonVO playerParticulars(int page,int size,String memberId,long startTime,long endTime){
        CommonVO vo = new CommonVO();
        ListPage listPage = playService.queryGameTableByMemberId(page,size,memberId,startTime,endTime);
        Map data = new HashMap();
        data.put("listPage",listPage);
        vo.setData(data);
        return vo;
    }

    /**
     * 后台——踢出大联盟
     */
    @RequestMapping("/web_remove_member")
    public CommonVO remove_members(String handleMemberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("handleMember not found");
            return vo;
        }
        GameMemberTable memberTable = playService.findByMemberId(handleMemberId);
        if (memberTable!=null){
            vo.setSuccess(false);
            vo.setMsg("member is playing");
            return vo;
        }
        MemberDiamondAccountDbo memberDiamondAccountDbo = memberDiamondService.findDiamondAccountDbo(updateMemberLianmengDbo.getMemberId(), updateMemberLianmengDbo.getLianmengId());
        if (memberDiamondAccountDbo.getBalance()!=0) {
            vo.setSuccess(false);
            vo.setMsg("updateMember power balance should Cleared");
            return vo;
        }
        lianmengMemberService.updateSuperiorMemberIdAndIdentity(handleMemberId, lianmengId, updateMemberLianmengDbo.getSuperiorMemberId());
        lianmengMemberService.removeMemberLianmengDbo(handleMemberId, lianmengId);
        memberDayResultDataService.deleteByLianmengIdAndMemberId(handleMemberId,lianmengId);
        return vo;
    }




}
