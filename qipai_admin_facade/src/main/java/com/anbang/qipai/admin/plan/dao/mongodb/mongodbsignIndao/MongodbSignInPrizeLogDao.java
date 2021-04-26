package com.anbang.qipai.admin.plan.dao.mongodb.mongodbsignIndao;

import java.util.List;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;
import com.anbang.qipai.admin.plan.dao.signindao.SignInPrizeLogDao;

@Component
public class MongodbSignInPrizeLogDao implements SignInPrizeLogDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addSignInPrizeLog(SignInPrizeLog signInPrizeLog) {
		mongoTemplate.insert(signInPrizeLog);
	}

	@Override
	public List<SignInPrizeLog> querySignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime,
			int page, int size) {
		Query query = new Query();
		if (!StringUtil.isBlank(signInPrizeLog.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(signInPrizeLog.getMemberId()));
		}
		if (!StringUtil.isBlank(signInPrizeLog.getNickname())) {
			query.addCriteria(Criteria.where("nickname").is(signInPrizeLog.getNickname()));
		}
		if (startTime != null || endTime != null) {
			Criteria criteria = Criteria.where("createTime");
			if (startTime != null) {
				criteria = criteria.gte(startTime);
			}
			if (endTime != null) {
				criteria = criteria.lte(endTime);
			}
			query.addCriteria(criteria);
		}
		if (!StringUtil.isBlank(signInPrizeLog.getType())) {
			query.addCriteria(Criteria.where("type").is(signInPrizeLog.getType()));
		}

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
		query.with(sort);

		query.skip((page - 1) * size);
		query.limit(size);

		return mongoTemplate.find(query, SignInPrizeLog.class);
	}

	@Override
	public int countSignInPrizeLog(SignInPrizeLog signInPrizeLog, Long startTime, Long endTime) {
		Query query = new Query();
		if (!StringUtil.isBlank(signInPrizeLog.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(signInPrizeLog.getMemberId()));
		}
		if (!StringUtil.isBlank(signInPrizeLog.getNickname())) {
			query.addCriteria(Criteria.where("nickname").is(signInPrizeLog.getNickname()));
		}
		if (startTime != null || endTime != null) {
			Criteria criteria = Criteria.where("createTime");
			if (startTime != null) {
				criteria = criteria.gte(startTime);
			}
			if (endTime != null) {
				criteria = criteria.lte(endTime);
			}
			query.addCriteria(criteria);
		}
		if (!StringUtil.isBlank(signInPrizeLog.getType())) {
			query.addCriteria(Criteria.where("type").is(signInPrizeLog.getType()));
		}
		return (int) mongoTemplate.count(query, SignInPrizeLog.class);
	}

}
