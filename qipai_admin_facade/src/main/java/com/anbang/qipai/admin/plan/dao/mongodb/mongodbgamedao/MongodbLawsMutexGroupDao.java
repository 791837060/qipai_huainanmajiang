package com.anbang.qipai.admin.plan.dao.mongodb.mongodbgamedao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.games.GameLaw;
import com.anbang.qipai.admin.plan.bean.games.LawsMutexGroup;
import com.anbang.qipai.admin.plan.dao.gamedao.LawsMutexGroupDao;

@Component
public class MongodbLawsMutexGroupDao implements LawsMutexGroupDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(LawsMutexGroup lawsMutexGroup) {
		mongoTemplate.insert(lawsMutexGroup);
	}

	@Override
	public void remove(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, LawsMutexGroup.class);
	}

	@Override
	public List<LawsMutexGroup> findLawsMutexGroupByConditions(int page, int size, LawsMutexGroup lawsMutexGroup) {
		Query query = new Query();
		if (lawsMutexGroup.getName() != null && !"".equals(lawsMutexGroup.getName())) {
			query.addCriteria(Criteria.where("name").regex(lawsMutexGroup.getName()));
		}
		if (lawsMutexGroup.getGame() != null) {
			query.addCriteria(Criteria.where("game").is(lawsMutexGroup.getGame()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, LawsMutexGroup.class);
	}

	@Override
	public long getAmountByConditions(LawsMutexGroup lawsMutexGroup) {
		Query query = new Query();
		if (lawsMutexGroup.getName() != null && !"".equals(lawsMutexGroup.getName())) {
			query.addCriteria(Criteria.where("name").regex(lawsMutexGroup.getName()));
		}
		if (lawsMutexGroup.getGame() != null) {
			query.addCriteria(Criteria.where("game").is(lawsMutexGroup.getGame()));
		}
		return mongoTemplate.count(query, LawsMutexGroup.class);
	}

}
