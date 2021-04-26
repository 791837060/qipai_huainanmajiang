package com.anbang.qipai.dalianmeng.web.controller;

import com.anbang.qipai.dalianmeng.cqrs.c.service.LianmengCmdService;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.*;
import com.anbang.qipai.dalianmeng.cqrs.q.service.*;
import com.anbang.qipai.dalianmeng.plan.bean.MemberDayResultData;
import com.anbang.qipai.dalianmeng.plan.bean.game.GameMemberTable;
import com.anbang.qipai.dalianmeng.plan.service.MemberAuthService;
import com.anbang.qipai.dalianmeng.plan.service.MemberDayResultDataService;
import com.anbang.qipai.dalianmeng.plan.service.PlayService;
import com.anbang.qipai.dalianmeng.web.vo.CommonVO;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/member")
public class LianmengMemberController {

    @Autowired
    private LianmengMemberService lianmengMemberService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private LianmengCmdService lianmengCmdService;

    @Autowired
    private LianmengService lianmengService;

    @Autowired
    private MemberDayResultDataService memberDayResultDataService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PlayService playService;

    @Autowired
    private PowerService powerService;

    /**
     * 查看成员列表填充信息
     */
    @RequestMapping("/query_list")
    public CommonVO query_list(String token,@RequestParam(required = false) String searchMemberId, String lianmengId, long queryTime) {
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
        boolean zhushou = false;
        boolean isXiaji = true;
        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            memberId = memberLianmengDbo.getZhushouId();
            zhushou = true;
        }
        if (!StringUtils.isEmpty(searchMemberId)) {
            MemberLianmengDbo byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (byMemberIdAndLianmengId != null && !byMemberIdAndLianmengId.getSuperiorMemberId().equals(memberId)) {
                while (true) {
                    byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    if (byMemberIdAndLianmengId.getMemberId().equals(memberId)) {
                        memberId = searchMemberId;
                        break;
                    }
                    if (byMemberIdAndLianmengId.getIdentity().equals(Identity.MENGZHU)) {
                        isXiaji = false;
                        break;
                    } else {
                        byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    }

                }
            } else {
                memberId = searchMemberId;
            }
        }
        if (!isXiaji) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
        Map data = lianmengMemberService.queryMemberByMemberIdAndLianmengIdAndSuperior( memberId, lianmengId, queryTime);
        if (zhushou) {
            data.put("superiorMemberId", memberLianmengDbo.getZhushouId());
        }
        vo.setData(data);
        return vo;
    }
    /**
     * 查看成员列表默认列表
     */
    @RequestMapping("/query_list_normal")
    public CommonVO query_list_normal(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String token,
                                      @RequestParam(required = false) String searchMemberId, String lianmengId, long queryTime) {
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
        boolean isXiaji = true;
        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            memberId = memberLianmengDbo.getZhushouId();
        }
        if (!StringUtils.isEmpty(searchMemberId)) {
            MemberLianmengDbo byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (byMemberIdAndLianmengId != null && !byMemberIdAndLianmengId.getSuperiorMemberId().equals(memberId)) {
                while (true) {
                    byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    if (byMemberIdAndLianmengId.getMemberId().equals(memberId)) {
                        memberId = searchMemberId;
                        break;
                    }
                    if (byMemberIdAndLianmengId.getIdentity().equals(Identity.MENGZHU)) {
                        isXiaji = false;
                        break;
                    } else {
                        byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    }

                }
            } else {
                memberId = searchMemberId;
            }
        }
        if (!isXiaji) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
        ListPage listPage = lianmengMemberService.queryMemberByMemberIdAndLianmengIdAndSuperior(page, size, memberId, lianmengId, queryTime);
        vo.setData(listPage);
        return vo;
    }
    /**
     * 查看成员列表排序列表
     */
    @RequestMapping("/query_list_sort")
    public CommonVO query_list_sort(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String token,
                                    @RequestParam(required = false) String searchMemberId, String lianmengId, long queryTime,
                                    String powerSort, String scoreSort, String juCountSort, String dayingjiaCountSort, String powerCostSort,
                                    String onlineSort) {
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
        boolean isXiaji = true;
        if (!StringUtils.isEmpty(memberLianmengDbo.getZhushouId())) {
            memberId = memberLianmengDbo.getZhushouId();
        }
        if (!StringUtils.isEmpty(searchMemberId)) {
            MemberLianmengDbo byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(searchMemberId, lianmengId);
            if (byMemberIdAndLianmengId != null && !byMemberIdAndLianmengId.getSuperiorMemberId().equals(memberId)) {
                while (true) {
                    byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    if (byMemberIdAndLianmengId.getMemberId().equals(memberId)) {
                        memberId = searchMemberId;
                        break;
                    }
                    if (byMemberIdAndLianmengId.getIdentity().equals(Identity.MENGZHU)) {
                        isXiaji = false;
                        break;
                    } else {
                        byMemberIdAndLianmengId = lianmengMemberService.findByMemberIdAndLianmengId(byMemberIdAndLianmengId.getSuperiorMemberId(), lianmengId);
                    }

                }
            } else {
                memberId = searchMemberId;
            }
        }
        if (!isXiaji) {
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
        ListPage listPage = lianmengMemberService.queryMemberByMemberIdAndLianmengIdAndSuperior(page, size, memberId, lianmengId, queryTime, powerSort,
                scoreSort,juCountSort, dayingjiaCountSort, powerCostSort,onlineSort);
        vo.setData(listPage);
        return vo;
    }
    /**
     * id/昵称搜索
     */
    @RequestMapping("/findByNicknameOrId")
    public CommonVO findByNicknameOrId(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String token,
                                @RequestParam(required = false) String search, String lianmengId,long queryTime) {
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
        ListPage listPage = lianmengMemberService.findByNicknameOrMemberId(page, size, search, lianmengId, queryTime);
        vo.setData(listPage);
        return vo;
    }

    /**
     * 查询成员所在的联盟
     * @param memberId
     * @return
     */
    @RequestMapping("/queryLianmeng")
    public CommonVO queryLianmeng(String memberId){
        CommonVO vo=new CommonVO();
        List<MemberLianmengDbo> list=lianmengMemberService.findByMemberId(memberId);
        if (list==null&&list.isEmpty()){
            vo.setMsg("no lianmeng");
            return vo;
        }
        List<AllianceDbo> list1 = new ArrayList<>();
        for (MemberLianmengDbo memberLianmengDbo : list) {
            list1.add(lianmengService.findAllianceDboById(memberLianmengDbo.getLianmengId()));
        }
        vo.setData(list1);
        return vo;
    }

//    /**
//     * 管理层查看下级玩家
//     */
//    @RequestMapping("/querypartner_list")
//    public CommonVO queryPartner(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size,
//                                  String memberId,@RequestParam(required = false)String searchMemberId, String lianmengId,String powerSort,String scoreSort) {
//        CommonVO vo = new CommonVO();
//        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
//        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
//            vo.setSuccess(false);
//            vo.setMsg("ban");
//            return vo;
//        }
//        if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
//            vo.setSuccess(false);
//            vo.setMsg("member has not competence");
//            return vo;
//        }
//        ListPage listPage = lianmengMemberService.queryMemberByMemberIdAndLianmengIdAndSuperior(page, size, searchMemberId, lianmengId, memberId,powerSort,scoreSort);
//        Map data = new HashMap<>();
//        data.put("listPage", listPage);
//        vo.setData(data);
//        return vo;
//    }

//    /**
//     * 成员列表-提出大联盟
//     */
//    @RequestMapping("/remove_member")
//    public CommonVO removeMember(String memberId, String lianmengId) {
//        CommonVO vo = new CommonVO();
//        lianmengMemberService.removeMemberLianmengDbo(memberId, lianmengId);
//        return vo;
//    }

    /**
     * 成员列表-冻结账号
     */
    @RequestMapping("/ban_member")
    public CommonVO ban_member(String banMemberId, String lianmengId,String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
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
            vo.setMsg("用户没有权限");
            return vo;
        }
        lianmengMemberService.banMemberLianmengDbo(banMemberId, lianmengId);
        return vo;
    }

    /**
     * 成员列表-解冻账号
     */
    @RequestMapping("/release_member")
    public CommonVO release_member(String token,String releaseMemberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
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
            vo.setMsg("用户没有权限");
            return vo;
        }
        lianmengMemberService.releaseMemberLianmengDbo(releaseMemberId, lianmengId);
        return vo;
    }

    /**
     * 成员列表-调整上级合伙人
     */
    @RequestMapping("/update_member_superior")
    public CommonVO update_member_superior(String updateMemberId, String lianmengId, String superiorMemberId,String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
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
            vo.setMsg("用户没有权限");
            return vo;
        }
        MemberLianmengDbo superiorMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(superiorMemberId, lianmengId);
        if (superiorMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("上级用户没有找到");
            return vo;
        }
        if (superiorMemberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)){
            vo.setSuccess(false);
            vo.setMsg("上级用户没有权限");
            return vo;
        }
        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(updateMemberId, lianmengId);
        if (updateMemberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("被修改用户没有找到");
            return vo;
        }
        if(updateMemberLianmengDbo.getMemberId().equals(superiorMemberLianmengDbo.getMemberId())){
            vo.setSuccess(false);
            vo.setMsg("memberAreSameMember");
            return vo;
        }
        MemberLianmengDbo updateMemberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(superiorMemberLianmengDbo.getSuperiorMemberId(),superiorMemberLianmengDbo.getLianmengId());
        boolean canUpdate=true;
        while (true){
            if (!updateMemberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)){
                if (updateMemberLianmengDbo1.getMemberId().equals(updateMemberLianmengDbo.getMemberId())){
                    canUpdate=false;
                }
            } else {
                break;
            }
            updateMemberLianmengDbo1 = lianmengService.findMemberLianmengDboByMemberIdAndLianmengId(updateMemberLianmengDbo1.getSuperiorMemberId(), updateMemberLianmengDbo1.getLianmengId());
        }
        if (!canUpdate) {
            vo.setSuccess(false);
            vo.setMsg("该用户不能成为被调整用户的上级");
            return vo;
        }
        if (!StringUtils.isEmpty(updateMemberLianmengDbo.getZhushouId())) {
            lianmengMemberService.updateZhushouId(updateMemberId, lianmengId, "");
        }
        PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(updateMemberLianmengDbo.getMemberId(), updateMemberLianmengDbo.getLianmengId());
        if (powerAccountDbo.getBalance()!=0d) {
            vo.setSuccess(false);
            vo.setMsg("updateMember power balance should Cleared");
            return vo;
        }
        MemberDayResultData updateMemberDayResult = memberDayResultDataService.findByMemberIdAndLianmengId(updateMemberId, lianmengId);
        memberDayResultDataService.updatePower(superiorMemberId, lianmengId, updateMemberDayResult.getPower());
        memberDayResultDataService.updatePower(updateMemberLianmengDbo.getSuperiorMemberId(), lianmengId, -updateMemberDayResult.getPower());
        lianmengMemberService.updateMemberLianmengDboSuperiorMember(updateMemberId, lianmengId, superiorMemberId);
        return vo;
    }



    /**
     * 成员列表-调整成员身份
     */
    @RequestMapping("/update_member_identity")
    public CommonVO update_member_identity(String updateMemberId, String lianmengId,String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("无效token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null || memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("用户没找到或被冻结");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }

        }
        if (memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("用户没有权限");
            return vo;
        }

        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(updateMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("上级用户未找到");
            return vo;
        }
        if (!StringUtils.isEmpty(updateMemberLianmengDbo.getZhushouId())) {
            vo.setSuccess(false);
            vo.setMsg("updateMember is zhushou");
            return vo;
        }

        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setSuccess(false);
                vo.setMsg("被调整用户不是该用户的下级");
                return vo;
            }

        }
        if (updateMemberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)) {
            if (scoreService.findScoreAccountDbo(updateMemberId,lianmengId)==null) {
                try {
                    String scoreAccountId = lianmengCmdService.undateIdentity(updateMemberId, lianmengId);
                    lianmengService.updateMemberLianmengDboIdentity(updateMemberId, lianmengId, Identity.HEHUOREN, scoreAccountId);
                } catch (Exception e) {

                    e.printStackTrace();
                    return vo;
                }
            }else {
                lianmengService.updateRejoinMemberLianmengDboIdentity(updateMemberId, lianmengId, Identity.HEHUOREN);
            }
        }else {
            lianmengMemberService.updateMemberLianmengDboIdentity(updateMemberId, lianmengId,Identity.HEHUOREN);
        }
        return vo;
    }

    /**
     * 成员列表-调整下级贡献分成
     */
    @RequestMapping("/update_member_contributionProportion")
    public CommonVO update_member_contributionProportion(String updateMemberId, String lianmengId,String token,int contributionProportion) {
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
            vo.setMsg("用户没找到或被冻结");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }

        }
        if (memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("用户没有权限");
            return vo;
        }
        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(updateMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("上级用户未找到");
            return vo;
        }
        if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())){
            vo.setSuccess(false);
            vo.setMsg("被调整用户不是该用户的下级");
            return vo;
        }
        if (contributionProportion > 100 || contributionProportion < 0) {
            vo.setSuccess(false);
            vo.setMsg("贡献值区间错误");
            return vo;
        }
        lianmengMemberService.updateMemberLianmengDboContributionProportion(updateMemberId, lianmengId, contributionProportion);
        return vo;
    }

    /**
     * 成为/取消助手
     *
     * @param token
     * @param updateMemberId
     * @param lianmengId
     * @param zhushou
     * @return
     */
    @RequestMapping("/update_member_zhushou")
    public CommonVO update_member_zhushou(String token, String updateMemberId, String lianmengId, boolean zhushou) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        if (memberLianmengDbo.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }
        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(updateMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("update not found");
            return vo;
        }
        if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberId)) {
            vo.setSuccess(false);
            vo.setMsg("member is not update superiorMember");
            return vo;
        }
        if (zhushou) {
            if (!StringUtils.isEmpty(updateMemberLianmengDbo.getZhushouId())) {
                vo.setSuccess(false);
                vo.setMsg("updateMember is zhushou");
            } else {
                if (updateMemberLianmengDbo.getIdentity().equals(Identity.HEHUOREN)){
                    vo.setSuccess(false);
                    vo.setMsg("updateMember is hehuoren");
                    return vo;
                }
                lianmengMemberService.updateZhushouId(updateMemberId, lianmengId, memberId);
                vo.setSuccess(true);
                vo.setMsg("success");
            }
        } else {
            if (StringUtils.isEmpty(updateMemberLianmengDbo.getZhushouId())) {
                vo.setSuccess(false);
                vo.setMsg("updateMember is not zhushou");
            }
            lianmengMemberService.updateZhushouId(updateMemberId, lianmengId, "");
            vo.setSuccess(true);
            vo.setMsg("success");
        }
        return vo;


    }

    /**
     * 修改公告
     */
    @RequestMapping("/update_desc")
    public CommonVO update_desc(String lianmengId, String token, String desc) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        if (!memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)){
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }
        lianmengService.updateDesc(lianmengId, desc);
        return vo;
    }


    /**
     * 查询玩家信息
     */
    @RequestMapping("/info_member")
    public CommonVO info_member(String memberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("not lianmeng member");
            return vo;
        }
        MemberDbo memberDbo = memberService.findMemberDboByMemberId(memberLianmengDbo.getMemberId());
        Map data = new HashMap<>();
        vo.setData(data);
        data.put("member", memberDbo);
        return vo;
    }


    /**
     * 玩家是否在联盟验证
     * @param memberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/memberInLianmengVerify")
    public CommonVO memberInlianmengVerify(String memberId,String lianmengId){
        CommonVO vo = new CommonVO();
        MemberDbo memberDbo = memberService.findMemberDboByMemberId(memberId);
        if(memberDbo == null){
            vo.setSuccess(false);
            vo.setMsg("MemberNotFoundException");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberId,lianmengId);
        if(memberLianmengDbo1 == null){
            vo.setSuccess(false);
            vo.setMsg("MemberNotInLianmengException");
            return vo;
        }
        vo.setSuccess(true);
        vo.setData(memberDbo.getNickname());
        return vo;
    }



    /**
     * 提出大联盟
     */
    @RequestMapping("/remove_member")
    public CommonVO remove_member(String token,String handleMemberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
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
        if (memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("member has not competence");
            return vo;
        }
        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("handleMember not found");
            return vo;
        }
        if (memberLianmengDbo1.getIdentity().equals(Identity.HEHUOREN)) {
            if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setSuccess(false);
                vo.setMsg("member has not competence");
                return vo;
            }
        }
        GameMemberTable memberTable = playService.findByMemberId(handleMemberId);
        if (memberTable!=null){
            vo.setSuccess(false);
            vo.setMsg("member is playing");
            return vo;
        }
        MemberDayResultData updateMemberDayResultData = memberDayResultDataService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        memberDayResultDataService.updatePower(memberLianmengDbo1.getMemberId(), lianmengId, -updateMemberDayResultData.getPower());

        lianmengMemberService.updateSuperiorMemberIdAndIdentity(handleMemberId, lianmengId, updateMemberLianmengDbo.getSuperiorMemberId());
        lianmengMemberService.removeMemberLianmengDbo(handleMemberId, lianmengId);
        return vo;
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void dayInit(){
        long currentTime = System.currentTimeMillis();
        memberDayResultDataService.dayInit();
        memberDayResultDataService.deleteByTime(currentTime);
    }
    @RequestMapping("/lianmengDayResultInit")
    public CommonVO lianmengDayResultInit(){
        CommonVO vo = new CommonVO();
        long currentTime = System.currentTimeMillis();
        long l = memberDayResultDataService.dayInitForAdmin(currentTime + 1);
        memberDayResultDataService.deleteByTime(currentTime);
        vo.setData(l);
        return vo;
    }

    @RequestMapping("/memberPowerInit")
    public CommonVO memberPowerInit(){
        CommonVO vo = new CommonVO();
        memberDayResultDataService.memberPowerInit();
        return vo;
    }

    /**
     * 新增合伙人禁止同桌
     * @param token
     * @param lianmengId
     * @param banMemberId
     * @return
     */
    @RequestMapping("/addHehuorenBanDeskMate")
    public CommonVO hehuorenBanDeskMate(String token,String lianmengId,String banMemberId,boolean suoyouxiaji){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }

        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(banMemberId, lianmengId);
        if (updateMemberLianmengDbo == null || updateMemberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("banMember is ban");
            return vo;
        }
        if (!memberLianmengDbo.getIdentity().equals(Identity.MENGZHU)) {
            if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberId)){
                vo.setSuccess(false);
                vo.setMsg("member has not competence");
                return vo;
            }
        }
        if (!updateMemberLianmengDbo.getIdentity().equals(Identity.HEHUOREN)) {
            vo.setSuccess(false);
            vo.setMsg("banMember has not competence");
            return vo;

        }
        lianmengMemberService.addHehuorenBanDeskMate(banMemberId,suoyouxiaji,memberId,lianmengId);
        return vo;
    }
    /**
     * 新增禁止同桌
     * @param lianmengId
     * @param memberAId
     * @param memberBId
     * @param token
     * @return
     */
    @RequestMapping("/addBanDeskMate")
    public CommonVO banDeskMate(String lianmengId ,String memberAId,String memberBId,String token){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("ban");
            return vo;
        }
        if(memberAId.equals(memberBId)){
            vo.setSuccess(false);
            vo.setMsg("MembersAreSameException");
            return vo;
        }
        MemberLianmengDbo memberLianmengDboA = lianmengMemberService.findByMemberIdAndLianmengId(memberAId, lianmengId);
        if (memberLianmengDboA == null ) {
            vo.setSuccess(false);
            vo.setMsg("memberA not in lianmeng");
            return vo;
        }
        if (memberLianmengDboA.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("memberA is ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDboB = lianmengMemberService.findByMemberIdAndLianmengId(memberBId, lianmengId);
        if (memberLianmengDboB == null ) {
            vo.setSuccess(false);
            vo.setMsg("memberB not in lianmeng");
            return vo;
        }
        if (memberLianmengDboB.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("memberB is ban");
            return vo;
        }
        if(memberLianmengDbo.getIdentity()!=Identity.MENGZHU){
            if (!memberLianmengDboA.getSuperiorMemberId().equals(memberId)){
                vo.setSuccess(false);
                vo.setMsg("member has not competence");
                return vo;
            }
            if (!memberLianmengDboB.getSuperiorMemberId().equals(memberId)){
                vo.setSuccess(false);
                vo.setMsg("member has not competence");
                return vo;
            }
        }
        lianmengMemberService.addBanDeskMate(memberAId,memberBId,memberId,lianmengId);
        return vo;
    }

    /**
     * 移除禁止同桌
     * @param lianmengId
     * @param banId
     * @param token
     * @return
     */
    @RequestMapping("/removeBanDeskMate")
    public CommonVO removeBanDeskMate(String lianmengId ,String banId,String token){
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("member is ban");
            return vo;
        }
        lianmengMemberService.removeBanDeskMate(banId,lianmengId);
        return vo;
    }

    /**
     * 同桌管理
     * @param page
     * @param size
     * @param lianmengId
     * @param token
     * @return
     */
    @RequestMapping("/banDeskMateRecord")
    public CommonVO banDeskMateRecord(@RequestParam(defaultValue = "1") int page ,@RequestParam(defaultValue = "30")int size,
                                      String lianmengId, String token,long queryTime) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("member is ban");
            return vo;
        }
        ListPage listPage = lianmengMemberService.queryBanDeskMate(page, size, memberId, lianmengId,queryTime);
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }
    /**
     * 同桌管理查询可操作下级
     * @param lianmengId
     * @param token
     * @return
     */
    @RequestMapping("/queryXiji")
    public CommonVO queryXiji(String lianmengId, String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("member is ban");
            return vo;
        }

        List list = lianmengMemberService.queryXiaji(memberId, lianmengId);
        Map data = new HashMap<>();
        data.put("list", list);
        vo.setData(data);
        return vo;
    }
    /**
     * 解除合伙人
     *
     * @param token
     * @param handleMemberId
     * @param lianmengId
     * @return
     */
    @RequestMapping("/dismiss_member")
    public CommonVO dismiss_member(String token, String handleMemberId, String lianmengId) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(memberId, lianmengId);
        if (memberLianmengDbo == null ) {
            vo.setSuccess(false);
            vo.setMsg("member not in lianmeng");
            return vo;
        }
        if (memberLianmengDbo.isBan()) {
            vo.setSuccess(false);
            vo.setMsg("member is ban");
            return vo;
        }
        MemberLianmengDbo memberLianmengDbo1 = memberLianmengDbo;
        if (!StringUtils.isEmpty(memberLianmengDbo1.getZhushouId())) {
            if (lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId) != null) {
                memberLianmengDbo1 = lianmengMemberService.findByMemberIdAndLianmengId(memberLianmengDbo.getZhushouId(), lianmengId);
            }

        }
        if (memberLianmengDbo1.getIdentity().equals(Identity.CHENGYUAN)) {
            vo.setSuccess(false);
            vo.setMsg("用户没有权限");
            return vo;
        }

        MemberLianmengDbo updateMemberLianmengDbo = lianmengMemberService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        if (updateMemberLianmengDbo == null) {
            vo.setSuccess(false);
            vo.setMsg("上级用户未找到");
            return vo;
        }
        if (!StringUtils.isEmpty(updateMemberLianmengDbo.getZhushouId())) {
            vo.setSuccess(false);
            vo.setMsg("updateMember is zhushou");
            return vo;
        }

        if (!memberLianmengDbo1.getIdentity().equals(Identity.MENGZHU)) {
            if (!updateMemberLianmengDbo.getSuperiorMemberId().equals(memberLianmengDbo1.getMemberId())) {
                vo.setSuccess(false);
                vo.setMsg("被调整用户不是该用户的下级");
                return vo;
            }

        }
        lianmengMemberService.updateSuperiorMemberIdAndIdentity(handleMemberId, lianmengId, updateMemberLianmengDbo.getSuperiorMemberId());
        MemberDayResultData updateMemberDayResultData = memberDayResultDataService.findByMemberIdAndLianmengId(handleMemberId, lianmengId);
        PowerAccountDbo powerAccountDbo = powerService.findPowerAccountDbo(handleMemberId, lianmengId);

        memberDayResultDataService.updateMemberTotalPower(handleMemberId, lianmengId, -updateMemberDayResultData.getPower()+powerAccountDbo.getBalance());
        lianmengMemberService.updateMemberLianmengDboIdentity(handleMemberId, lianmengId, Identity.CHENGYUAN);

        return vo;
    }

    @RequestMapping("/showOnlineMember")
    public CommonVO banDeskmateRecord(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "30") int size, String lianmengId) {
        CommonVO vo = new CommonVO();
        List<MemberLianmengDbo> onlineMemberList = lianmengMemberService.findOnlineMemberByLianmengId(lianmengId);
        for (int i = 0; i < onlineMemberList.size(); i++) {
            if (playService.findByMemberId(onlineMemberList.get(i).getMemberId()) != null) {
                onlineMemberList.remove(i);
                i--;
            }
        }
        List<MemberLianmengDbo> memberLianmengDboList = new ArrayList<>();
        for (int i = 0; i < onlineMemberList.size(); i++) {
            if (i < page * size && i >= (size * (page - 1))) {
                memberLianmengDboList.add(onlineMemberList.get(i));
            }
        }
        ListPage listPage = new ListPage(memberLianmengDboList, page, size, onlineMemberList.size());
        Map data = new HashMap<>();
        data.put("listPage", listPage);
        vo.setData(data);
        return vo;
    }

}




