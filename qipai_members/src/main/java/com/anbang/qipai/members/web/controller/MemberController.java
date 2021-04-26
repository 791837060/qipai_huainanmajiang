package com.anbang.qipai.members.web.controller;

import com.anbang.qipai.members.config.PhoneVerifyConfig;
import com.anbang.qipai.members.config.RealNameVerifyConfig;
import com.anbang.qipai.members.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberGoldAccountDbo;
import com.anbang.qipai.members.cqrs.q.service.MemberAuthQueryService;
import com.anbang.qipai.members.cqrs.q.service.MemberGoldQueryService;
import com.anbang.qipai.members.msg.service.MembersMsgService;
import com.anbang.qipai.members.plan.bean.LianmengApply;
import com.anbang.qipai.members.plan.bean.LianmengApplyStatus;
import com.anbang.qipai.members.plan.bean.MemberLoginLimitRecord;
import com.anbang.qipai.members.plan.bean.MemberPhoneVerifyCode;
import com.anbang.qipai.members.plan.service.LianmengApplyService;
import com.anbang.qipai.members.plan.service.MemberLoginLimitRecordService;
import com.anbang.qipai.members.plan.service.MemberPhoneVerifyCodeService;
import com.anbang.qipai.members.util.HttpUtil;
import com.anbang.qipai.members.util.VerifyPhoneCodeUtil;
import com.anbang.qipai.members.web.vo.CommonVO;
import com.anbang.qipai.members.web.vo.DetailsVo;
import com.anbang.qipai.members.web.vo.MemberVO;
import com.google.gson.Gson;
import com.highto.framework.web.page.ListPage;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 会员controller
 *
 * @author 林少聪 2018.7.9
 */
@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private MemberAuthQueryService memberAuthQueryService;

    @Autowired
    private MemberGoldQueryService memberGoldQueryService;

    @Autowired
    private MembersMsgService membersMsgService;

    @Autowired
    private MemberLoginLimitRecordService memberLoginLimitRecordService;

    @Autowired
    private MemberPhoneVerifyCodeService memberPhoneVerifyCodeService;

    @Autowired
    private LianmengApplyService lianmengApplyService;


    private Gson gson = new Gson();

    @RequestMapping(value = "/info")
    public MemberVO info(String memberId) {
        MemberVO vo = new MemberVO();
        MemberDbo memberDbo = memberAuthQueryService.findMemberById(memberId);
        if (memberDbo == null) {
            vo.setSuccess(false);
            return vo;
        }
        vo.setSuccess(true);
        vo.setHeadimgurl(memberDbo.getHeadimgurl());
        vo.setMemberId(memberId);
        vo.setNickname(memberDbo.getNickname());
        vo.setVerifyUser(memberDbo.isVerifyUser());
        vo.setPhone(memberDbo.getPhone());
        vo.setVerifyPhone(memberDbo.getPhone()!=null);
        vo.setVerifyWeChat(memberDbo.isVerifyWeChat());

        MemberGoldAccountDbo memberGoldAccountDbo = memberGoldQueryService.findMemberGoldAccount(memberId);
        if (memberGoldAccountDbo != null) {
            int gold = memberGoldAccountDbo.getBalance();
            if (gold > 999999) {
                vo.setGold(gold / 10000 + "w");
            } else {
                vo.setGold(String.valueOf(gold));
            }
        }
        return vo;
    }

    @RequestMapping(value = "/getMemberDbo")
    public CommonVO getMemberDbo(String memberId) {
        CommonVO vo = new CommonVO();
        MemberDbo memberDbo = memberAuthQueryService.findMemberById(memberId);
        if (memberDbo == null) {
            vo.setSuccess(false);
            return vo;
        }

        vo.setSuccess(true);
        vo.setData(memberDbo);
        return vo;
    }

    @RequestMapping("/querymember")
    public DetailsVo queryMember(String token) {
        DetailsVo vo = new DetailsVo();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberLoginLimitRecord record = memberLoginLimitRecordService.findByMemberId(memberId, true);
        if (record != null) {// 被封号
            vo.setSuccess(false);
            vo.setMsg("login limited");
            return vo;
        }
        MemberGoldAccountDbo memberGoldAccountDbo = memberGoldQueryService.findMemberGoldByMemberId(memberId);
        if (memberGoldAccountDbo != null) {
            int gold = memberGoldAccountDbo.getBalance();
            if (gold > 999999) {
                vo.setGold(gold / 10000 + "w");
            } else {
                vo.setGold(String.valueOf(gold));
            }
        }
        MemberDbo member = memberAuthQueryService.findMemberById(memberId);
        vo.setPhone(member.getPhone());

        vo.setSuccess(true);
        vo.setMsg("information");
        return vo;
    }

    /**
     * 实名认证
     */
    @RequestMapping("/verify")
    public CommonVO verifyMember(@RequestParam(required = true) String realName,
                                 @RequestParam(required = true) String IDcard, String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("无效的凭证");
            return vo;
        }
        if (memberAuthQueryService.findMemberById(memberId).isVerifyUser()) {
            vo.setSuccess(false);
            vo.setMsg("已经认证通过");
            return vo;
        }
        if (IDcard.length() != 18 || !Pattern.matches("[0-9]{14}\\S{4}", IDcard)) {
            vo.setSuccess(false);
            vo.setMsg("无效的身份证号");
            return vo;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String birthString = IDcard.substring(6, 14);
        String year = birthString.substring(0, 4);
        int targetAge = Integer.parseInt(year) + 18;
        String targetBirth = targetAge + IDcard.substring(10, 14);
        try {
            Date targetDate = format.parse(targetBirth);
            if (System.currentTimeMillis() - targetDate.getTime() < 0) {
                vo.setSuccess(false);
                vo.setMsg("未到法定年龄");
                return vo;
            }
            String host = "https://idcert.market.alicloudapi.com";
            String path = "/idcard";
            String method = "GET";
            String appcode = PhoneVerifyConfig.APPCODE;
            Map<String, String> headers = new HashMap<String, String>();
            // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("idCard", IDcard);
            querys.put("name", realName);

            HttpResponse response = HttpUtil.doGet(host, path, method, headers, querys);
            Map map = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class);
            if (map == null) {
                vo.setSuccess(false);
                vo.setMsg("系统异常");
                return vo;
            }
            String status = (String) map.get("status");
            // 认证成功
            if ("01".equals(status)) {
                String name = (String) map.get("name");
                String idCard = (String) map.get("idCard");
                MemberDbo member = memberAuthQueryService.updateMemberRealUser(memberId, name, idCard, true);
                membersMsgService.updateMemberRealUser(member);

                vo.setMsg("认证通过");
                return vo;
            }
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
        vo.setSuccess(false);
        vo.setMsg("认证未通过");
        return vo;
    }


    @RequestMapping("/querygoldaccount")
    public CommonVO queryGoldAccount(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "size", defaultValue = "10") Integer size, String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        ListPage listPage = memberGoldQueryService.findMemberGoldRecords(page, size, memberId);
        vo.setSuccess(true);
        vo.setMsg("goldaccout");
        vo.setData(listPage);
        return vo;
    }

    /**
     * 查询验证码
     * @return
     */
    @RequestMapping("/queryparam")
    public CommonVO queryparem(String phone){
        CommonVO vo=new CommonVO();
        MemberPhoneVerifyCode byPhone = memberPhoneVerifyCodeService.findByPhone(phone);
        vo.setData(byPhone);
        return vo;
    }

    @RequestMapping("/loginByPhone")
    public CommonVO loginByPhone(String phone) {
        CommonVO vo = new CommonVO();
        if (!Pattern.matches("[0-9]{11}", phone)) {
            vo.setSuccess(false);
            vo.setMsg("invalid phone");
            return vo;
        }
        MemberPhoneVerifyCode memberVerify = memberPhoneVerifyCodeService.findByPhone(phone);
        if (memberVerify != null && System.currentTimeMillis() - memberVerify.getCreateTime() < 30000) {
            vo.setSuccess(false);
            vo.setMsg("too frequently");
            return vo;
        }
        try {
            String param = VerifyPhoneCodeUtil.generateVerifyCode();
            String host = "http://dingxin.market.alicloudapi.com";
            String path = "/dx/sendSms";
            String method = "POST";
            String appcode = PhoneVerifyConfig.APPCODE;
            Map<String, String> headers = new HashMap<String, String>();
            // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("mobile", phone);
            querys.put("param", "code:" + param);
            querys.put("tpl_id", "TP1711063");
            Map<String, String> bodys = new HashMap<String, String>();

            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            Map map = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class);
            String return_code = (String) map.get("return_code");

            MemberPhoneVerifyCode memberPhoneVerifyCode = new MemberPhoneVerifyCode();
            memberPhoneVerifyCode.setCreateTime(System.currentTimeMillis());
            memberPhoneVerifyCode.setPhone(phone);
            memberPhoneVerifyCode.setCode(param);
            memberPhoneVerifyCodeService.save(memberPhoneVerifyCode);
            if (return_code.equals("00000")) {
                vo.setSuccess(true);
            } else {
                vo.setSuccess(false);
                vo.setMsg(return_code);
            }
            return vo;
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
    }

    /**
     * 用户申请开通联盟
     */
    @RequestMapping("/apply")
    public CommonVO memberApplyLianmeng(String token, String name , String phone , String param) {
        CommonVO vo = new CommonVO();
        MemberPhoneVerifyCode memberPhoneVerifyCode = memberPhoneVerifyCodeService.findByPhone(phone);
        if (memberPhoneVerifyCode != null) {
            if (!memberPhoneVerifyCode.getCode().equals(param)) {
                vo.setSuccess(false);
                vo.setMsg("invalid param");
                return vo;
            }
        } else {
            vo.setSuccess(false);
            vo.setMsg("invalid phone");
            return vo;
        }
        memberPhoneVerifyCodeService.removeByPhone(phone);
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberDbo memberDbo = memberAuthQueryService.findMemberById(memberId);
        if (memberDbo.isDalianmeng()||memberDbo.isQinyouquan()) {
            vo.setSuccess(false);
            vo.setMsg("member has competence");
            return vo;
        }
        if (lianmengApplyService.findLianmengApplyByMemberIdAndStatus(memberId, LianmengApplyStatus.SUCCESS) != null ||
                lianmengApplyService.findLianmengApplyByMemberIdAndStatus(memberId, LianmengApplyStatus.APPLYING) != null) {
            vo.setSuccess(false);
            vo.setMsg("apply already");
            return vo;
        }
        LianmengApply apply = new LianmengApply();
        apply.setName(name);
        apply.setPhone(phone);
        apply.setMemberId(memberDbo.getId());
        apply.setNickname(memberDbo.getNickname());
        apply.setHeadimgurl(memberDbo.getHeadimgurl());
        apply.setCreateTime(System.currentTimeMillis());
        apply.setStatus(LianmengApplyStatus.APPLYING);
        lianmengApplyService.addApply(apply);
        return vo;
    }

    /**
     * 通过联盟申请
     */
    @RequestMapping("/apply_pass")
    public CommonVO applylianmeng_pass(String applyId,boolean dalianmeng,boolean qinyouquan) {
        CommonVO vo = new CommonVO();
        LianmengApply apply = lianmengApplyService.findLianmengApplyByApplyId(applyId);
        if (apply == null) {
            vo.setSuccess(false);
            vo.setMsg("apply not existed");
            return vo;
        }
        LianmengApply lianmengApply = lianmengApplyService.updateApplyStatus(applyId, LianmengApplyStatus.SUCCESS);
        MemberDbo memberDbo = memberAuthQueryService.updateMemberDalianmengApply(lianmengApply.getMemberId(), dalianmeng, qinyouquan);
        membersMsgService.updateMemberDalianmengApply(memberDbo);
        return vo;
    }

    /**
     * 拒绝联盟申请
     */
    @RequestMapping("/apply_refuse")
    public CommonVO applylianmeng_refuse(String applyId) {
        CommonVO vo = new CommonVO();
        lianmengApplyService.updateApplyStatus(applyId, LianmengApplyStatus.FAIL);
        return vo;
    }

    /**
     * 修改联盟权限
     */
    @RequestMapping("/update_lianmeng_competence")
    public CommonVO update_lianmeng_competence(String memberId,boolean dalianmeng ,boolean qinyouquan) {
        CommonVO vo = new CommonVO();
        MemberDbo memberDbo = memberAuthQueryService.updateMemberDalianmengApply(memberId, dalianmeng, qinyouquan);
        membersMsgService.updateMemberDalianmengApply(memberDbo);
        return vo;
    }

    @RequestMapping("/find_applying")
    public CommonVO find_applying(){
        CommonVO vo = new CommonVO();
        List<LianmengApply> lianmengApplies = lianmengApplyService.listApplyByStatus(LianmengApplyStatus.APPLYING);
        if (lianmengApplies == null) {
            vo.setSuccess(false);
            vo.setMsg("not apply");
            return vo;
        }
        vo.setData(lianmengApplies);
        return vo;
    }
    /**
     * 用户是否开通联盟
     */
    @RequestMapping("/apply_info")
    public CommonVO agentApplyInfo(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        LianmengApply lianmengApply = lianmengApplyService.findLianmengApplyByMemberId(memberId);
        if (lianmengApply == null) {
            vo.setSuccess(false);
            vo.setMsg("not apply");
            return vo;
        }
        if (!lianmengApply.getStatus().equals(LianmengApplyStatus.SUCCESS)) {
            vo.setData(lianmengApply);
            vo.setSuccess(false);
            vo.setMsg("not success");
            return vo;
        }
        MemberDbo memberById = memberAuthQueryService.findMemberById(memberId);
        vo.setData(memberById);
        return vo;
    }

    /**
     * 用户是否开通联盟
     */
    @RequestMapping("/delete_apply")
    public CommonVO delete_apply(String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        lianmengApplyService.deleteByMemberIdAndStatus(memberId);
        return vo;
    }
}
