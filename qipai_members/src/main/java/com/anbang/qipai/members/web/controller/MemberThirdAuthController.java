package com.anbang.qipai.members.web.controller;

import com.anbang.qipai.members.config.PhoneVerifyConfig;
import com.anbang.qipai.members.cqrs.c.domain.CreateMemberResult;
import com.anbang.qipai.members.cqrs.c.service.MemberAuthCmdService;
import com.anbang.qipai.members.cqrs.c.service.MemberAuthService;
import com.anbang.qipai.members.cqrs.q.dbo.AuthorizationDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberGoldRecordDbo;
import com.anbang.qipai.members.cqrs.q.service.MemberAuthQueryService;
import com.anbang.qipai.members.cqrs.q.service.MemberGoldQueryService;
import com.anbang.qipai.members.msg.service.AuthorizationMsgService;
import com.anbang.qipai.members.msg.service.GoldsMsgService;
import com.anbang.qipai.members.msg.service.MembersMsgService;
import com.anbang.qipai.members.plan.bean.MemberPhoneVerifyCode;
import com.anbang.qipai.members.plan.service.MemberPhoneVerifyCodeService;
import com.anbang.qipai.members.util.HttpUtil;
import com.anbang.qipai.members.util.IPUtil;
import com.anbang.qipai.members.util.VerifyPhoneCodeUtil;
import com.anbang.qipai.members.web.vo.CommonVO;
import com.dml.users.AuthorizationAlreadyExistsException;
import com.dml.users.UserNotFoundException;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/thirdauth")
public class MemberThirdAuthController {


    @Autowired
    protected HttpClient sslHttpClient;

    @Autowired
    private MemberAuthCmdService memberAuthCmdService;

    @Autowired
    private MemberAuthQueryService memberAuthQueryService;

    @Autowired
    private MemberAuthService memberAuthService;

    @Autowired
    private MemberPhoneVerifyCodeService memberPhoneVerifyCodeService;

    @Autowired
    private MemberGoldQueryService memberGoldQueryService;

    @Autowired
    private MembersMsgService membersMsgService;

    @Autowired
    private GoldsMsgService goldsMsgService;

    @Autowired
    private AuthorizationMsgService authorizationMsgService;

    private Gson gson = new Gson();

    /**
     * 客户端已经获取好了openid/unionid和微信用户信息
     *
     * @param unionid
     * @param openid
     * @param nickname
     * @param headimgurl
     * @param sex        值为1时是男性，值为2时是女性，值为0时是未知
     * @return
     */

    // if 1fail
    @RequestMapping(value = "/wechatidlogin")
    @ResponseBody
    public CommonVO wechatidlogin(HttpServletRequest request, String unionid, String openid, String nickname,
                                  String headimgurl, Integer sex) {
        CommonVO vo = new CommonVO();
        try {
            AuthorizationDbo unionidAuthDbo = memberAuthQueryService.findThirdAuthorizationDbo("union.weixin", unionid);
            if (unionidAuthDbo != null) {// 已unionid注册
                AuthorizationDbo openidAuthDbo = memberAuthQueryService
                        .findThirdAuthorizationDbo("open.weixin.app.qipai", openid);
                if (openidAuthDbo == null) {// openid未注册
                    // 添加openid授权
                    try {
                        memberAuthCmdService.addThirdAuth("open.weixin.app.qipai", openid,
                                unionidAuthDbo.getMemberId());
                        AuthorizationDbo authDbo = memberAuthQueryService.addThirdAuth("open.weixin.app.qipai", openid,
                                unionidAuthDbo.getMemberId());
                        authorizationMsgService.newAuthorization(authDbo);
                    } catch (AuthorizationAlreadyExistsException e) {
                        AuthorizationDbo authDbo = memberAuthQueryService.addThirdAuth("open.weixin.app.qipai", openid,
                                unionidAuthDbo.getMemberId());
                        authorizationMsgService.newAuthorization(authDbo);
                    }
                }
                // 更新用户信息
                memberAuthQueryService.updateMember(unionidAuthDbo.getMemberId(), nickname, headimgurl, sex,
                        IPUtil.getRealIp(request),null);
                memberAuthQueryService.bindWeChat(unionidAuthDbo.getMemberId());
				// 发送消息
				MemberDbo memberDbo = memberAuthQueryService.findMemberById(unionidAuthDbo.getMemberId());
				membersMsgService.updateMemberBaseInfo(memberDbo);

				// openid登录
				String token = memberAuthService.thirdAuth("open.weixin.app.qipai", openid);
				vo.setSuccess(true);
				Map data = new HashMap();
				data.put("token", token);
				vo.setData(data);
				return vo;
			} else {
                int goldForNewMember = 10;

                // 创建会员和unionid授权
                CreateMemberResult createMemberResult = memberAuthCmdService.createMemberAndAddThirdAuth("union.weixin",
                        unionid, goldForNewMember, System.currentTimeMillis());

                AuthorizationDbo unionAuthDbo = memberAuthQueryService.createMemberAndAddThirdAuth(
                        createMemberResult.getMemberId(), "union.weixin", unionid,
                        IPUtil.getRealIp(request), null);
                authorizationMsgService.newAuthorization(unionAuthDbo);
                // 添加openid授权
                memberAuthCmdService.addThirdAuth("open.weixin.app.qipai", openid, unionAuthDbo.getMemberId());
                AuthorizationDbo openAuthDbo = memberAuthQueryService.addThirdAuth("open.weixin.app.qipai", openid,
                        unionAuthDbo.getMemberId());
                authorizationMsgService.newAuthorization(openAuthDbo);

                // 填充用户信息
                memberAuthQueryService.updateMember(createMemberResult.getMemberId(), nickname, headimgurl, sex,
                        IPUtil.getRealIp(request),  null);
                memberAuthQueryService.bindWeChat(createMemberResult.getMemberId());
                // 发送消息
                MemberDbo memberDbo = memberAuthQueryService.findMemberById(createMemberResult.getMemberId());
                membersMsgService.createMember(memberDbo);
                // 创建金币帐户，赠送金币记账
                MemberGoldRecordDbo goldDbo = memberGoldQueryService.createMember(createMemberResult);
				// 发送金币记账消息
				goldsMsgService.withdraw(goldDbo);
                // unionid登录
                String token = memberAuthService.thirdAuth("union.weixin", unionid);
                vo.setSuccess(true);
                Map data = new HashMap();
                data.put("token", token);
                vo.setData(data);
                return vo;
            }
        } catch (Exception e) {

            vo.setSuccess(false);
            vo.setMsg(e.getClass().toString());
            return vo;
        }

    }


    @RequestMapping("/registerphone")
    public CommonVO registerPhone(String phone, String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberDbo member = memberAuthQueryService.findMemberByPhone(phone);
        if (member != null) {
            vo.setSuccess(false);
            vo.setMsg("already used phone");
            return vo;
        }
        AuthorizationDbo unionidAuthDbo = memberAuthQueryService.findThirdAuthorizationDbo("phone", phone);
        if (unionidAuthDbo != null) {
            vo.setSuccess(false);
            vo.setMsg("already used phone");
            return vo;
        }
        member = memberAuthQueryService.findMemberById(memberId);
        if (!StringUtils.isEmpty(member.getPhone())) {
            vo.setSuccess(false);
            vo.setMsg("already register phone");
            return vo;
        }
        if (!Pattern.matches("[0-9]{11}", phone)) {
            vo.setSuccess(false);
            vo.setMsg("invalid phone");
            return vo;
        }
        MemberPhoneVerifyCode memberVerify = memberPhoneVerifyCodeService.findByMemberId(memberId);
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
            memberPhoneVerifyCode.setId(memberId);
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

    @RequestMapping("/verifyphone")
    public CommonVO verifyPhone(String phone, String param, String token) {
        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        MemberDbo member = memberAuthQueryService.findMemberById(memberId);
        if (!StringUtils.isEmpty(member.getPhone())) {
            vo.setSuccess(false);
            vo.setMsg("already register phone");
            return vo;
        }
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
        try {
            memberAuthCmdService.addThirdAuth("phone", phone, memberId);
            memberAuthQueryService.addThirdAuth("phone", phone, memberId);
        } catch (AuthorizationAlreadyExistsException | UserNotFoundException e) {
            vo.setSuccess(false);
            vo.setMsg("invalid phone");
            return vo;
        }
        member = memberAuthQueryService.registerPhone(memberId, phone);
        membersMsgService.updateMemberPhone(member);
        memberPhoneVerifyCodeService.removeByPhone(phone);
        Map data = new HashMap<>();
        data.put("phone", phone);
        vo.setData(data);
        vo.setSuccess(true);
        vo.setMsg("register success");
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
//            String host = "http://dingxin.market.alicloudapi.com";
//            String path = "/dx/sendSms";
//            String method = "POST";
//            String appcode = PhoneVerifyConfig.APPCODE;
//            Map<String, String> headers = new HashMap<String, String>();
//            // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//            headers.put("Authorization", "APPCODE " + appcode);
//            Map<String, String> querys = new HashMap<String, String>();
//            querys.put("mobile", phone);
//            querys.put("param", "code:" + param);
//            querys.put("tpl_id", "TP1711063");
//            Map<String, String> bodys = new HashMap<String, String>();
//            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
//            Map map = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class);
//            String return_code = (String) map.get("return_code");
            MemberPhoneVerifyCode memberPhoneVerifyCode = new MemberPhoneVerifyCode();
            memberPhoneVerifyCode.setCreateTime(System.currentTimeMillis());
            memberPhoneVerifyCode.setPhone(phone);
            memberPhoneVerifyCode.setCode(param);
            memberPhoneVerifyCodeService.save(memberPhoneVerifyCode);
//            if (return_code.equals("00000")) {
//                vo.setSuccess(true);
//            } else {
//                vo.setSuccess(false);
//                vo.setMsg(return_code);
//            }
            return vo;
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().getName());
            return vo;
        }
    }

    @RequestMapping(value = "/phoneLogin")
    @ResponseBody
    public CommonVO phoneLogin(HttpServletRequest request, String phone, String param) {
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

        try {
            AuthorizationDbo phoneAuthDbo = memberAuthQueryService.findThirdAuthorizationDbo("phone", phone);
            if (phoneAuthDbo != null) {// 已unionid注册
                memberPhoneVerifyCodeService.removeByPhone(phone);
                String token = memberAuthService.thirdAuth("phone", phone);
                vo.setSuccess(true);
                Map data = new HashMap();
                data.put("token", token);
                vo.setData(data);
            } else {
                int goldForNewMember = 10;
                // 创建会员和unionid授权
                CreateMemberResult createMemberResult = memberAuthCmdService.createMemberAndAddThirdAuth("phone",
                        phone, goldForNewMember, System.currentTimeMillis());
                phoneAuthDbo = memberAuthQueryService.createMemberAndAddThirdAuth(createMemberResult.getMemberId(), "phone", phone,
                        IPUtil.getRealIp(request), null);
                // 填充用户信息
                String headimgurl = "http://qiniu.3cscy.com/0547d326456025bdfc703238778e15e.png";
                String nickname = "游客" + createMemberResult.getMemberId();
                memberAuthQueryService.updateMember(createMemberResult.getMemberId(), nickname, headimgurl, 1,
                        IPUtil.getRealIp(request), phone);
                // 发送消息
                MemberDbo memberDbo = memberAuthQueryService.findMemberById(createMemberResult.getMemberId());
                membersMsgService.createMember(memberDbo);
                // 创建金币帐户，赠送金币记账
                MemberGoldRecordDbo goldDbo = memberGoldQueryService.createMember(createMemberResult);

                // 发送金币记账消息
                goldsMsgService.withdraw(goldDbo);
                memberPhoneVerifyCodeService.removeByPhone(phone);

                // unionid登录
                String token = memberAuthService.thirdAuth("phone", phone);
                vo.setSuccess(true);
                Map data = new HashMap();
                data.put("token", token);
                vo.setData(data);
            }
            return vo;
        } catch (Exception e) {
            vo.setSuccess(false);
            vo.setMsg(e.getClass().toString());
            return vo;
        }

    }


    @RequestMapping("/phoneBindWeChat")
    public CommonVO phoneBindWeChat(HttpServletRequest request, String unionid, String openid, String nickname,
                                    String headimgurl, Integer sex, String token) {

        CommonVO vo = new CommonVO();
        String memberId = memberAuthService.getMemberIdBySessionId(token);
        if (memberId == null) {
            vo.setSuccess(false);
            vo.setMsg("invalid token");
            return vo;
        }
        if (memberAuthQueryService.findMemberById(memberId).isVerifyWeChat()) {
            vo.setSuccess(false);
            vo.setMsg("already register WeChat");
            return vo;
        }
        try {
            AuthorizationDbo openIdAuthDbo = memberAuthQueryService.find(openid);
            AuthorizationDbo unionIdAuthDbo = memberAuthQueryService.find(unionid);
            if (openIdAuthDbo != null || unionIdAuthDbo != null) {
                vo.setSuccess(false);
                vo.setMsg("WeChat already register");
                return vo;
            }
            try {
                memberAuthCmdService.addThirdAuth("open.weixin.app.qipai", openid, memberId);
                memberAuthQueryService.addThirdAuth("open.weixin.app.qipai", openid, memberId);
                memberAuthCmdService.addThirdAuth("union.weixin", unionid, memberId);
                memberAuthQueryService.addThirdAuth("union.weixin", unionid, memberId);
            } catch (AuthorizationAlreadyExistsException e) {
                vo.setSuccess(false);
                vo.setMsg("invalid WeChat");
                return vo;
            }
            memberAuthQueryService.updateMember(memberId, nickname, headimgurl, sex, IPUtil.getRealIp(request),null);
            memberAuthQueryService.bindWeChat(memberId);
            MemberDbo memberDbo = memberAuthQueryService.findMemberById(memberId);
            membersMsgService.updateMemberBaseInfo(memberDbo);
            vo.setSuccess(true);
            vo.setMsg("phoneBindWeChat success");
            return vo;

        } catch (Exception e) {
            e.printStackTrace();
            vo.setSuccess(false);
            vo.setMsg("phoneBindWeChat failed");
            return vo;
        }

    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void removeByTime(){
        //nowTime系统当前时间  System.currentTimeMillis()获取系统当前时间long类型
        //currentTime系统当前时间的五分钟之前的时间
        long nowTime = System.currentTimeMillis();
        long currentTime=nowTime-300000;
        memberPhoneVerifyCodeService.removeByTime(currentTime);
    }

}
