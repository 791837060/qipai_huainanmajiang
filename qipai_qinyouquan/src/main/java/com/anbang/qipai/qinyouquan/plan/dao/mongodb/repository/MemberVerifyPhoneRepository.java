package com.anbang.qipai.qinyouquan.plan.dao.mongodb.repository;


import com.anbang.qipai.qinyouquan.plan.bean.MemberPhoneVerifyCode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberVerifyPhoneRepository extends MongoRepository<MemberPhoneVerifyCode, String> {

}
