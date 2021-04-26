package com.anbang.qipai.members.plan.service;

import com.anbang.qipai.members.plan.bean.MemberPhoneVerifyCode;
import com.anbang.qipai.members.plan.dao.MemberPhoneVerifyCodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberPhoneVerifyCodeService {

	@Autowired
	private MemberPhoneVerifyCodeDao memberPhoneVerifyCodeDao;

	public void save(MemberPhoneVerifyCode memberPhoneVerifyCode) {
		memberPhoneVerifyCodeDao.save(memberPhoneVerifyCode);
	}


	public MemberPhoneVerifyCode findByPhone(String phone){
		return memberPhoneVerifyCodeDao.findMemberByPhone(phone);
	}

	public MemberPhoneVerifyCode findByMemberId(String memberId) {
		return memberPhoneVerifyCodeDao.findByMemberId(memberId);
	}

	public List<MemberPhoneVerifyCode> findAll() {

		return memberPhoneVerifyCodeDao.findAll();
	}

    public void removeByPhone(String phone){
        memberPhoneVerifyCodeDao.removeByPhone(phone);
    }


    public void removeByTime(long currentTime){
        memberPhoneVerifyCodeDao.removeByTime(currentTime);
    }
}
