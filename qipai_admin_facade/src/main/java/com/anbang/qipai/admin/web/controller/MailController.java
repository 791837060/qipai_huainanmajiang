package com.anbang.qipai.admin.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.cqrs.c.service.AdminAuthService;
import com.anbang.qipai.admin.plan.bean.mail.SystemMail;
import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;
import com.anbang.qipai.admin.plan.bean.members.MemberDbo;
import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.service.gameservice.MailService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberClubCardService;
import com.anbang.qipai.admin.plan.service.membersservice.MemberDboService;
import com.anbang.qipai.admin.plan.service.permissionservice.AdminService;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;
import com.anbang.qipai.admin.util.QiniuUtil;
import com.anbang.qipai.admin.util.TimeUtil;
import com.anbang.qipai.admin.web.vo.CommonVO;
import com.google.gson.Gson;
import com.highto.framework.web.page.ListPage;

/**
 * 系统邮件controller
 * 
 * @author 程佳 2018.6.6
 **/
@CrossOrigin
@RestController
@RequestMapping("/mailctrl")
public class MailController {

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	@Autowired
	private MemberClubCardService clubCardService;

	@Autowired
	private MemberDboService memberService;

	@Autowired
	private MailService mailService;

	@Autowired
	private AdminAuthService adminAuthService;

	@Autowired
	private AdminService adminService;

	private Gson gson = new Gson();

	/**
	 * 发布系统邮件
	 * 
	 * @param mail
	 *            邮件信息
	 **/
	@RequestMapping(value = "/addmail", method = RequestMethod.POST)
	@ResponseBody
	public String addmail(SystemMail mail, String token) {
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			return "fail";
		}
		Admin admin = adminService.findAdminById(adminId);
		mail.setAdminname(admin.getNickname());
		String str = gson.toJson(mail);
		qipaiGameRomoteService.addmail(str);
		return "success";
	}

	/**
	 * 查询历史维护，恢复记录
	 * 
	 * @param page
	 *            当前页，size 每页显示条数,admin 根据管理员名称查询,status,邮件状态
	 **/
	@RequestMapping(value = "/querymail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querymail(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, String adminname, Integer status) {
		Map<String, Object> map = mailService.findall(page, size, adminname, status);
		return map;
	}

	/**
	 * 邮件图片上传，获取七牛云token
	 **/
	@RequestMapping(value = "/uptoken", method = RequestMethod.POST)
	@ResponseBody
	public CommonVO uptoken() {
		CommonVO vo = new CommonVO();
		String uptoken = QiniuUtil.getUpToken();
		vo.setSuccess(true);
		vo.setMsg("Obtaintoken");
		vo.setData(uptoken);
		return vo;
	}

	/**
	 * 查询所有会员卡
	 **/
	@RequestMapping(value = "/find_vipcard", method = RequestMethod.POST)
	@ResponseBody
	public CommonRemoteVO find_vipcard() {
		CommonRemoteVO co = new CommonRemoteVO();
		List<MemberClubCard> list = clubCardService.showClubCard();
		co.setSuccess(true);
		co.setData(list);
		return co;
	}

	/**
	 * 管理员根据id发布邮件
	 **/
	@RequestMapping(value = "/addmailbyid", method = RequestMethod.POST)
	@ResponseBody
	public CommonRemoteVO addMailById(SystemMail mail, @RequestParam(value = "ids", required = false) String[] ids,
			Integer validDay, @RequestParam(value = "vipCardId", required = false) String vipCardId, String token,
			Integer sendType) {
		CommonRemoteVO vo = new CommonRemoteVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("登录异常，重新登录");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		mail.setAdminname(admin.getNickname());
		List<String> idss = new ArrayList<String>();
		for (String id : ids) {
			idss.add(id);
		}
		for (String id : idss) {
			MemberDbo memberDbo = memberService.findMemberById(id);
			if (memberDbo == null) {
				vo.setMsg("memberId:" + id);
				return vo;
			}
		}
		if (validDay != null) {
			long validTime = TimeUtil.getDate(System.currentTimeMillis(), validDay);
			mail.setValidTime(validTime);
		}
		if (vipCardId != null) {
			MemberClubCard card = clubCardService.findClubCardById(vipCardId);
			mail.setCardName(card.getName());
			mail.setVipCardId(vipCardId);
		}
		String str = gson.toJson(mail);
		vo = qipaiGameRomoteService.addMailById(str, idss);
		return vo;
	}

	/**
	 * 管理员根据用户类型发布邮件
	 */
	@RequestMapping(value = "/addmailbytype", method = RequestMethod.POST)
	public CommonRemoteVO addMailByType(SystemMail mail, Integer sendType, Integer validDay,
			@RequestParam(value = "vipCardId", required = false) String vipCardId, String token) {
		CommonRemoteVO vo = new CommonRemoteVO();
		String adminId = adminAuthService.getAdminIdBySessionId(token);
		if (adminId == null) {
			vo.setSuccess(false);
			vo.setMsg("登录异常，重新登录");
			return vo;
		}
		Admin admin = adminService.findAdminById(adminId);
		mail.setAdminname(admin.getNickname());
		List<String> idss = new ArrayList<String>();
		switch (sendType) {
		case 1:// 会员邮件
			idss = memberService.findVipMemberId();
			break;
		case 2:// 非会员邮件
			idss = memberService.findMemberId();
			break;
		case 3:// 系统邮件
			idss = memberService.findAllMemberId();
			break;
		default:
			vo.setSuccess(false);
			vo.setMsg("sendType异常");
			return vo;
		}
		for (String id : idss) {
			MemberDbo memberDbo = memberService.findMemberById(id);
			if (memberDbo == null) {
				vo.setMsg("memberId:" + id);
				return vo;
			}
		}
		if (validDay != null) {
			long validTime = TimeUtil.getDate(System.currentTimeMillis(), validDay);
			mail.setValidTime(validTime);
		}
		if (vipCardId != null) {
			mail.setVipCardId(vipCardId);
		}
		String str = gson.toJson(mail);
		vo = qipaiGameRomoteService.addMailById(str, idss);
		return vo;
	}

	/**
	 * 查询邮件列表
	 * 
	 * @param page
	 *            当前页，size 每页显示条数,memberId 会员编号,mailType 邮件类型
	 */
	@RequestMapping(value = "/find_mail_record", method = RequestMethod.POST)
	@ResponseBody
	public CommonRemoteVO find_mail_record(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, String memberId, String mailType,
			Long startTime, Long endTime, String adminName) {
		CommonRemoteVO co = new CommonRemoteVO();
		ListPage listPage = mailService.find_mail_record(page, size, memberId, mailType, adminName, startTime, endTime);
		co.setData(listPage);
		co.setSuccess(true);
		return co;
	}

	/**
	 * 批量删除邮件
	 */
	@RequestMapping(value = "/deleteMailStateAll", method = RequestMethod.POST)
	@ResponseBody
	public CommonRemoteVO deleteMailStateAll(@RequestParam(value = "ids") String[] ids) {
		CommonRemoteVO co = new CommonRemoteVO();
		mailService.deleteAllMailState(ids);
		co.setSuccess(true);
		return co;
	}

}
