package com.anbang.qipai.qinyouquan.plan.dao.mongodb;


import com.anbang.qipai.qinyouquan.plan.bean.LianmengApply;
import com.anbang.qipai.qinyouquan.plan.dao.LianmengApplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbLianmengApplyDao implements LianmengApplyDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addApply(LianmengApply lianmengApply) {
		mongoTemplate.insert(lianmengApply);
	}

	@Override
	public LianmengApply findByApplyId(String applyId) {
		Query query = new Query(Criteria.where("id").is(applyId));
		return mongoTemplate.findOne(query, LianmengApply.class);
	}

	@Override
	public LianmengApply findByAgentIdAndStatus(String memberId, String status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		query.addCriteria(Criteria.where("status").is(status));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));
		return mongoTemplate.findOne(query, LianmengApply.class);
	}

	@Override
	public void updateApplyStatus(String applyId, String status) {
		Query query = new Query(Criteria.where("id").is(applyId));
		Update update = new Update();
		update.set("status", status);
		mongoTemplate.updateFirst(query, update, LianmengApply.class);
	}

	@Override
	public List<LianmengApply> listApplyByStatus(String status) {
		Query query = new Query(Criteria.where("status").is(status));
		mongoTemplate.find(query, LianmengApply.class);
		return mongoTemplate.find(query, LianmengApply.class);
	}

}
