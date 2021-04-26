package com.anbang.qipai.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbang.qipai.admin.plan.bean.membershiprights.CommonUser;
import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
import com.anbang.qipai.admin.plan.bean.membershiprights.VipUser;
import com.anbang.qipai.admin.plan.service.MemberShipRightsService;
import com.anbang.qipai.admin.remote.service.QipaiGameRemoteService;
import com.anbang.qipai.admin.remote.service.QipaiMembersRemoteService;
import com.anbang.qipai.admin.remote.vo.CommonRemoteVO;

/**
 * 会员权益Controller
 * 
 * @author 程佳 2018.6.12
 **/
@CrossOrigin
@RestController
@RequestMapping("/rights")
public class MemberShipRightsCtrl {

	@Autowired
	private QipaiMembersRemoteService qipaMemberClient;

	@Autowired
	private MemberShipRightsService memberShipRightsService;

	@Autowired
	private QipaiGameRemoteService qipaiGameRomoteService;

	/**
	 * @param 普通会员所有权益
	 * @return 操作结果
	 **/
	@RequestMapping(value = "/commonuser", method = RequestMethod.POST)
	@ResponseBody
	public String commonuser(MemberShipRights commonuser) {
		CommonRemoteVO co = qipaMemberClient.commonuser(commonuser.getSignGoldNumber(),
				commonuser.getGoldForNewNember(), commonuser.getInviteIntegralNumber(),
				commonuser.getGoldForAgentInvite(), commonuser.getPlanGrowIntegralSpeed());
		CommonRemoteVO cos = qipaiGameRomoteService.commonuser(commonuser.getPlanMemberRoomsCount(),
				commonuser.getPlanMemberRoomsAliveHours(), commonuser.getPlanMemberMaxCreateRoomDaily(),
				commonuser.getPlanMemberCreateRoomDailyGoldPrice(), commonuser.getPlanMemberJoinRoomGoldPrice());
		if (co.isSuccess() && cos.isSuccess()) {
			return "success";
		} else {
			return "file";
		}
	}

	/**
	 * @param vip会员所有权益
	 * @return 操作结果
	 **/
	@RequestMapping(value = "/vipuser", method = RequestMethod.POST)
	@ResponseBody
	public String vipuser(MemberShipRights vipUser) {
		CommonRemoteVO co = qipaMemberClient.vipuser(vipUser.getSignGoldNumber(), vipUser.getInviteIntegralNumber(),
				vipUser.getVipGrowIntegralSpeed(), vipUser.getVipGrowGradeSpeed());
		CommonRemoteVO cos = qipaiGameRomoteService.vipuser(vipUser.getVipMemberRoomsCount(),
				vipUser.getVipMemberRoomsAliveHours());
		if (co.isSuccess() && cos.isSuccess()) {
			return "success";
		} else {
			return "file";
		}
	}

	/**
	 * 查询普通用户所有权益
	 * 
	 * @return 操作结果
	 **/
	@RequestMapping("/commonrights")
	@ResponseBody
	public CommonUser commonrights() {
		CommonUser comm = new CommonUser();
		MemberShipRights memberShipRights = memberShipRightsService.findallCommonUser();
		if (memberShipRights != null) {
			comm.setId("1");
			comm.setSignGoldNumber(memberShipRights.getSignGoldNumber());
			comm.setInviteIntegralNumber(memberShipRights.getInviteIntegralNumber());
			comm.setPlanGrowIntegralSpeed(memberShipRights.getPlanGrowIntegralSpeed());
			comm.setPlanMemberaddRoomDailyGoldPrice(memberShipRights.getPlanMemberJoinRoomGoldPrice());
			comm.setPlanMemberCreateRoomDailyGoldPrice(memberShipRights.getPlanMemberCreateRoomDailyGoldPrice());
			comm.setPlanMemberMaxCreateRoomDaily(memberShipRights.getPlanMemberMaxCreateRoomDaily());
			comm.setPlanMemberRoomsAliveHours(memberShipRights.getPlanMemberRoomsAliveHours());
			comm.setPlanMemberRoomsCount(memberShipRights.getPlanMemberRoomsCount());
			comm.setGoldForNewNember(memberShipRights.getGoldForNewNember());
			comm.setGoldForAgentInvite(memberShipRights.getGoldForAgentInvite());
		}
		return comm;
	}

	/**
	 * 查询vip用户所有权益
	 * 
	 * @return 操作结果
	 **/
	@RequestMapping("/viprights")
	@ResponseBody
	public VipUser viprights() {
		VipUser vip = new VipUser();
		MemberShipRights memberShipRights = memberShipRightsService.findallCommonUser();
		if (memberShipRights != null) {
			vip.setId("1");
			vip.setSignGoldNumber(memberShipRights.getSignGoldNumber());
			vip.setInviteIntegralNumber(memberShipRights.getInviteIntegralNumber());
			vip.setVipGrowIntegralSpeed(memberShipRights.getVipGrowIntegralSpeed());
			vip.setVipMemberRoomsAliveHours(memberShipRights.getVipMemberRoomsAliveHours());
			vip.setVipMemberRoomsCount(memberShipRights.getVipMemberRoomsCount());
			vip.setVipGrowGradeSpeed(memberShipRights.getVipGrowGradeSpeed());
		}
		return vip;

	}

}
