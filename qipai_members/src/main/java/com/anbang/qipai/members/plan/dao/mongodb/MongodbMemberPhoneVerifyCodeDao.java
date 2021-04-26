package com.anbang.qipai.members.plan.dao.mongodb;

import com.anbang.qipai.members.plan.bean.MemberPhoneVerifyCode;
import com.anbang.qipai.members.plan.dao.MemberPhoneVerifyCodeDao;
import com.anbang.qipai.members.plan.dao.mongodb.repository.MemberVerifyPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbMemberPhoneVerifyCodeDao implements MemberPhoneVerifyCodeDao {

	@Autowired
	private MemberVerifyPhoneRepository memberVerifyPhoneRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberPhoneVerifyCode memberPhoneVerifyCode) {
		memberVerifyPhoneRepository.save(memberPhoneVerifyCode);
	}


	@Override
	public MemberPhoneVerifyCode findMemberByPhone(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
		return mongoTemplate.findOne(query, MemberPhoneVerifyCode.class);

	}

	@Override
	public MemberPhoneVerifyCode findByMemberId(String memberId) {
		Query query = new Query(Criteria.where("id").is(memberId));
		return mongoTemplate.findOne(query, MemberPhoneVerifyCode.class);
	}
	@Override
	public List<MemberPhoneVerifyCode> findAll() {
		return mongoTemplate.findAll(MemberPhoneVerifyCode.class);
	}

    @Override
    public void removeByTime(long currentTime) {
        Query query =new Query();
        query.addCriteria(Criteria.where("createTime").lt(currentTime));
        mongoTemplate.remove(query, MemberPhoneVerifyCode.class);
    }

    @Override
    public void removeByPhone(String phone) {
        Query query =new Query();
        query.addCriteria(Criteria.where("phone").is(phone));
        mongoTemplate.remove(query, MemberPhoneVerifyCode.class);
    }
}
