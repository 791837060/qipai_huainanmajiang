package com.anbang.qipai.dalianmeng.cqrs.q.dao;

import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberDbo;

public interface MemberDboDao {

    void save(MemberDbo dbo);

    MemberDbo findById(String memberId);

    void updateMember(String memberId, String nickname, String headimgurl);

    MemberDbo findByNickname(String nickname);

    void updateMemberDalianmengApply(String memberId,boolean dalianmeng,boolean qinyouquan);
}
