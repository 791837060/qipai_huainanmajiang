package com.anbang.qipai.members.cqrs.q.dao;

import com.anbang.qipai.members.cqrs.q.dbo.AuthorizationDbo;
import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;

public interface MemberDboDao {

	void save(MemberDbo memberDbo);

	void update(String memberId, String nickname, String headimgurl, String gender, String phone);

	MemberDbo findMemberById(String memberId);

	MemberDbo findMemberByPhone(String phone);

	void updateMemberPhone(String memberId, String phone);

    void updateMemberWeChat(String memberId, boolean verifyWeChat);

	void updateMemberReqIP(String memberId, String reqIP);

	void updateMemberRealUser(String memberId, String realName, String IDcard, boolean verify);

	void updateMemberGold(String memberId, int gold);

	AuthorizationDbo findThirdAuthorizationDboByMemberId(String id);

	void updateMemberByMemberId(MemberDbo memberDbo, String memberId);

	void updateMemberDalianmengApply(String memberId,boolean dalianmeng,boolean qinyouquan);

}
