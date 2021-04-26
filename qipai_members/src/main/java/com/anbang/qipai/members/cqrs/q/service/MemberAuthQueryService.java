package com.anbang.qipai.members.cqrs.q.service;

import com.anbang.qipai.members.cqrs.q.dao.AuthorizationDboDao;
import com.anbang.qipai.members.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.members.cqrs.q.dbo.AuthorizationDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthQueryService {

	@Autowired
	private AuthorizationDboDao authorizationDboDao;

	@Autowired
	private MemberDboDao memberDboDao;


	public AuthorizationDbo findThirdAuthorizationDbo(String publisher, String uuid) {
		return authorizationDboDao.find(true, publisher, uuid);
	}

	public void updateMember(String memberId, String nickname, String headimgurl, Integer sex, String reqIP,String phone) {
		String gender = "unknow";
		if (sex.intValue() == 1) {
			gender = "male";
		}
		if (sex.intValue() == 2) {
			gender = "female";
		}
		memberDboDao.update(memberId, nickname, headimgurl, gender,phone);
		MemberDbo memberDbo = memberDboDao.findMemberById(memberId);
		if (StringUtil.isBlank(memberDbo.getReqIP())) {
			memberDboDao.updateMemberReqIP(memberId, reqIP);
		}
	}

	public AuthorizationDbo createMemberAndAddThirdAuth(String memberId, String publisher, String uuid, String reqIP, String phone) {
		MemberDbo memberDbo = new MemberDbo();
		memberDbo.setId(memberId);
		memberDbo.setCreateTime(System.currentTimeMillis());
		memberDbo.setReqIP(reqIP);
		memberDbo.setPhone(phone);
		memberDbo.setVerifyWeChat(false);
		memberDboDao.save(memberDbo);

		AuthorizationDbo authDbo = new AuthorizationDbo();
		authDbo.setMemberId(memberId);
		authDbo.setPublisher(publisher);
		authDbo.setThirdAuth(true);
		authDbo.setUuid(uuid);
		authDbo.setPhone(phone);
		authorizationDboDao.save(authDbo);
		return authDbo;
	}

	public AuthorizationDbo addThirdAuth(String publisher, String uuid, String memberId) {
		AuthorizationDbo authDbo = new AuthorizationDbo();
		authDbo.setMemberId(memberId);
		authDbo.setPublisher(publisher);
		authDbo.setThirdAuth(true);
		authDbo.setUuid(uuid);
		authorizationDboDao.save(authDbo);
		return authDbo;
	}

	public MemberDbo findMemberById(String memberId) {
		return memberDboDao.findMemberById(memberId);
	}

	public MemberDbo findMemberByPhone(String phone) {
		return memberDboDao.findMemberByPhone(phone);
	}




	/**
	 * 注册手机
	 */
	public MemberDbo registerPhone(String memberId, String phone ) {
		memberDboDao.updateMemberPhone(memberId, phone);
		return memberDboDao.findMemberById(memberId);
	}

    public void bindWeChat(String memberId ) {
        memberDboDao.updateMemberWeChat(memberId,true);
    }


	public MemberDbo updateMemberRealUser(String memberId, String realName, String IDcard, boolean verify) {
		memberDboDao.updateMemberRealUser(memberId, realName, IDcard, verify);
		return memberDboDao.findMemberById(memberId);
	}




	public AuthorizationDbo findThirdAuthorizationDboByMemberId(String id) {
		return memberDboDao.findThirdAuthorizationDboByMemberId(id);
	}

	public AuthorizationDbo findThirdAuthorizationDboByPhone(String publisher, String phone) {
		return authorizationDboDao.findThirdAuthorizationDboByPhone(true, publisher, phone);
	}

	public void updateMemberByMemberId(MemberDbo memberDbo, String memberId) {
		memberDboDao.updateMemberByMemberId(memberDbo,memberId);
	}

	public AuthorizationDbo find(String unionid) {
		return authorizationDboDao.findbyuuid(unionid);
	}

	public MemberDbo updateMemberDalianmengApply(String memberId,boolean dalianmeng,boolean qinyouquan){
	    memberDboDao.updateMemberDalianmengApply(memberId,dalianmeng,qinyouquan);
        return memberDboDao.findMemberById(memberId);
    }
}
