package com.anbang.qipai.members.plan.dao.mongodb.repository;

import com.anbang.qipai.members.plan.bean.MemberPhoneVerifyCode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberVerifyPhoneRepository extends MongoRepository<MemberPhoneVerifyCode, String> {

}
