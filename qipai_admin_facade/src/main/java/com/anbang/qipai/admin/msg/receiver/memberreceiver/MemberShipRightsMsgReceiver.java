package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.anbang.qipai.admin.msg.channel.sink.MemberShipRightsSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
import com.anbang.qipai.admin.plan.service.MemberShipRightsService;
import com.google.gson.Gson;

/**
 * 接收会员大厅权益返回消息类
 * 
 * @author 程佳 2018.6.1 这里的类的注解是使一个或多个接口作为参数
 **/
@EnableBinding(MemberShipRightsSink.class)
public class MemberShipRightsMsgReceiver {

	@Autowired
	private MemberShipRightsService createMemberService;

	private Gson gson = new Gson();

	/**
	 * 设置会员大厅权益接收信息的方法
	 * 
	 * @param payload
	 *            接收的json数据 这里的方法的注解做消息监听
	 **/
	@StreamListener(MemberShipRightsSink.membershiprights)
	public void members(CommonMO mo) {
		String json = gson.toJson(mo.getData());
		MemberShipRights commonuser = gson.fromJson(json, MemberShipRights.class);
		MemberShipRights commonusers = createMemberService.findallCommonUser();
		commonusers.setSignGoldNumber(commonuser.getSignGoldNumber());
		commonusers.setGoldForNewNember(commonuser.getGoldForNewNember());
		commonusers.setInviteIntegralNumber(commonuser.getInviteIntegralNumber());
		commonusers.setVipGrowIntegralSpeed(commonuser.getVipGrowIntegralSpeed());
		commonusers.setPlanGrowIntegralSpeed(commonuser.getPlanGrowIntegralSpeed());
		commonusers.setVipGrowGradeSpeed(commonuser.getVipGrowGradeSpeed());
		commonusers.setGoldForAgentInvite(commonuser.getGoldForAgentInvite());
		createMemberService.saveMemberShipRights(commonusers);
	}

}
