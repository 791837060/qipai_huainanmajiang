package com.anbang.qipai.admin.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.membershiprights.MemberShipRights;
import com.anbang.qipai.admin.plan.dao.membershiprightsdao.MemberShipRightsDao;

@Service
public class MemberShipRightsService {

	@Autowired
	private MemberShipRightsDao memberDao;

	public MemberShipRights findallCommonUser() {
		return memberDao.findallCommonUser();
	}

	public MemberShipRights saveMemberShipRights(MemberShipRights commonuser) {
		return memberDao.saveMemberShipRights(commonuser);
	}

	public void updateGameMemberShipRights(MemberShipRights commonuser) {
		memberDao.updateGameMemberShipRights(commonuser);
	}

	public void updateMembersMemberShipRights(MemberShipRights commonuser) {
		memberDao.updateMembersMemberShipRights(commonuser);
	}
}
