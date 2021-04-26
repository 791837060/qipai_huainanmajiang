package com.anbang.qipai.qinyouquan.plan.dao;



import com.anbang.qipai.qinyouquan.plan.bean.MemberPhoneVerifyCode;

import java.util.List;

public interface MemberPhoneVerifyCodeDao {
	void save(MemberPhoneVerifyCode memberPhoneVerifyCode);

	MemberPhoneVerifyCode findMemberByPhone(String phone);


    MemberPhoneVerifyCode findByMemberId(String memberId);

	List<MemberPhoneVerifyCode> findAll();

    void removeByTime(long currentTime);

    void removeByPhone(String phone);

}
