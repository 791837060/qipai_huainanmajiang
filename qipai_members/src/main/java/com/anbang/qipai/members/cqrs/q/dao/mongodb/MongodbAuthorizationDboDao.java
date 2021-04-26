package com.anbang.qipai.members.cqrs.q.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.members.cqrs.q.dao.AuthorizationDboDao;
import com.anbang.qipai.members.cqrs.q.dao.mongodb.repository.AuthorizationDboRepository;
import com.anbang.qipai.members.cqrs.q.dbo.AuthorizationDbo;

@Component
public class MongodbAuthorizationDboDao implements AuthorizationDboDao {

	@Autowired
	private AuthorizationDboRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;



	@Override
	public AuthorizationDbo find(boolean thirdAuth, String publisher, String uuid) {
		return repository.findOneByThirdAuthAndPublisherAndUuid(thirdAuth, publisher, uuid);
	}

	@Override
	public void save(AuthorizationDbo authDbo) {
		repository.save(authDbo);
	}

	@Override
	public AuthorizationDbo findThirdAuthorizationDboByPhone(boolean thirdAuth, String publisher, String phone) {
		return repository.findOneByThirdAuthAndPublisherAndUuid(thirdAuth, publisher, phone);
	}

	@Override
	public AuthorizationDbo findbyuuid(String unionid) {
		Query query=new Query(Criteria.where("uuid").is(unionid));

		return mongoTemplate.findOne(query,AuthorizationDbo.class);
	}

	@Override
	public void updateUuidByMemberId(String unionid, String publisher, String memberId) {
		Query query=new Query(Criteria.where("memberId").is(memberId));
		Update update=new Update();
		update.set("unionid",unionid);
		update.set("publisher",publisher);
		mongoTemplate.updateFirst(query,update,AuthorizationDbo.class);
	}


}
