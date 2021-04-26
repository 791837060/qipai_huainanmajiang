package com.anbang.qipai.admin.plan.dao.hongbaodao;

import com.anbang.qipai.admin.plan.bean.hongbao.MemberInvitationRecord;
import com.anbang.qipai.admin.web.query.InvitationQuery;

import java.util.List;


public interface MemberInvitationRecordDao {

	void save(MemberInvitationRecord record);

	void insert(MemberInvitationRecord record);

	void updateState(String id, String state);

	MemberInvitationRecord findByMemberIdAndInvitationMemberId(String memberId, String invitationMemberId);

	MemberInvitationRecord findByInvitationMemberId(String invitationMemberId);

	MemberInvitationRecord findById(String id);

	List<MemberInvitationRecord> findByMemberId(int page, int size, String memberId, String state);

	long countByMemberId(String memberId, String state);

	long countByQuery(InvitationQuery invitationQuery);

	List<MemberInvitationRecord> listByQuery(InvitationQuery invitationQuery);

	void updateCause(String id, String cause);
}
