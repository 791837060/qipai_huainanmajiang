package com.anbang.qipai.admin.plan.dao.mongodb.mongodbhongbaodao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.hongbao.BlackList;
import com.anbang.qipai.admin.plan.dao.hongbaodao.BlackListDao;

@Component
public class MongodbBlackListDao implements BlackListDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(BlackList blackList) {
		mongoTemplate.insert(blackList);
	}

	@Override
	public void remove(String[] ids) {
		Object[] blackListIds = ids;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").in(blackListIds));
		mongoTemplate.remove(query, BlackList.class);
	}

	@Override
	public long count() {
		Query query = new Query();
		return mongoTemplate.count(query, BlackList.class);
	}

	@Override
	public List<BlackList> find(int page, int size) {
		Query query = new Query();
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, BlackList.class);
	}

}
