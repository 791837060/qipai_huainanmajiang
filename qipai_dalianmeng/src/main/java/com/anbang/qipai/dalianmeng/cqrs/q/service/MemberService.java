package com.anbang.qipai.dalianmeng.cqrs.q.service;

import com.anbang.qipai.dalianmeng.cqrs.q.dao.MemberDboDao;
import com.anbang.qipai.dalianmeng.cqrs.q.dbo.MemberDbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberDboDao memberDboDao;

    public MemberDbo findMemberDboByMemberId(String memberId) {
        return memberDboDao.findById(memberId);
    }

    public void saveMemberDbo(MemberDbo member) {
        memberDboDao.save(member);
    }

    public void updateMember(String memberId, String nickname, String headimgurl) {
        memberDboDao.updateMember(memberId, nickname, headimgurl);
    }

    public MemberDbo findMemberDboByNickname(String nickname) {
        return memberDboDao.findByNickname(nickname);
    }

    public void updateMemberDalianmengApply(String memberId,boolean dalianmeng,boolean qinyouquan){
        memberDboDao.updateMemberDalianmengApply(memberId,dalianmeng,qinyouquan);
    }
}
