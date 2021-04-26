package com.anbang.qipai.admin.plan.dao.mongodb.mongoshopdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.shop.HongbaodianProductRecord;
import com.anbang.qipai.admin.plan.dao.shopdao.HongbaodianProductRecordDao;
import com.anbang.qipai.admin.web.query.HongbaoRecordQuery;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Component
public class MongodbHongbaodianProductRecordDao implements HongbaodianProductRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(HongbaodianProductRecord record) {
		mongoTemplate.save(record);
	}

	@Override
	public HongbaodianProductRecord findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, HongbaodianProductRecord.class);
	}

	@Override
	public void updateStatusById(String id, String status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("status", status);
		mongoTemplate.updateFirst(query, update, HongbaodianProductRecord.class);
	}

	@Override
	public long countByMemberIdAndStatus(String memberId, String status) {
		Query query = new Query();
		if (StringUtil.isNotBlank(memberId)) {
			query.addCriteria(Criteria.where("memberId").is(memberId));
		}
		if (StringUtil.isNotBlank(status)) {
			query.addCriteria(Criteria.where("status").is(status));
		}
		return mongoTemplate.count(query, HongbaodianProductRecord.class);
	}

	@Override
	public List<HongbaodianProductRecord> findByMemberIdAndStatus(int page, int size, String memberId, String status) {
		Query query = new Query();
		if (StringUtil.isNotBlank(memberId)) {
			query.addCriteria(Criteria.where("memberId").is(memberId));
		}
		if (StringUtil.isNotBlank(status)) {
			query.addCriteria(Criteria.where("status").is(status));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, HongbaodianProductRecord.class);
	}

	@Override
	public void updateDeliverTimeById(String id, long deliverTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deliverTime", deliverTime);
		mongoTemplate.updateFirst(query, update, HongbaodianProductRecordDao.class);
	}

	@Override
	public List<HongbaodianProductRecord> findByQuery(int page, int size, HongbaoRecordQuery recordQuery) {
		Query query = new Query();
		if (StringUtils.isNotBlank(recordQuery.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(recordQuery.getMemberId()));
		}
		if (StringUtils.isNotBlank(recordQuery.getNickname())) {
			query.addCriteria(Criteria.where("nickname").is(recordQuery.getNickname()));
		}
		if (StringUtils.isNotBlank(recordQuery.getPhone())) {
			query.addCriteria(Criteria.where("phone").is(recordQuery.getPhone()));
		}
		if (recordQuery.getStartTime() != null || recordQuery.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (recordQuery.getStartTime() != null) {
				criteria = criteria.gte(recordQuery.getStartTime());
			}
			if (recordQuery.getEndTime() != null) {
				criteria = criteria.lte(recordQuery.getEndTime());
			}
			query.addCriteria(criteria);
		}
		query.with(recordQuery.getSort());
		query.limit(size);
		query.skip((page - 1) * size);
		return mongoTemplate.find(query, HongbaodianProductRecord.class);
	}

	@Override
	public long countByQuery(HongbaoRecordQuery recordQuery) {
		Query query = new Query();
		if (StringUtils.isNotBlank(recordQuery.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(recordQuery.getMemberId()));
		}
		if (StringUtils.isNotBlank(recordQuery.getNickname())) {
			query.addCriteria(Criteria.where("nickname").is(recordQuery.getNickname()));
		}
		if (StringUtils.isNotBlank(recordQuery.getPhone())) {
			query.addCriteria(Criteria.where("phone").is(recordQuery.getPhone()));
		}
		if (recordQuery.getStartTime() != null || recordQuery.getEndTime() != null) {
			Criteria criteria = Criteria.where("createTime");
			if (recordQuery.getStartTime() != null) {
				criteria = criteria.gte(recordQuery.getStartTime());
			}
			if (recordQuery.getEndTime() != null) {
				criteria = criteria.lte(recordQuery.getEndTime());
			}
			query.addCriteria(criteria);
		}
		return mongoTemplate.count(query, HongbaodianProductRecord.class);
	}

}
