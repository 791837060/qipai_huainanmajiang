package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.members.MemberOperationRecord;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberOperationRecordDao;

@Component
public class MongodbMemberOperationRecordDao implements MemberOperationRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberOperationRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public List<MemberOperationRecord> findByConditions(int page, int size, MemberOperationRecord record) {
		Query query = new Query();
		if (record.getMemberId() != null && !"".equals(record.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(record.getMemberId()));
		}
		if (record.getDesc() != null && !"".equals(record.getDesc())) {
			query.addCriteria(Criteria.where("desc").regex(record.getDesc()));
		}
		query.with(new Sort(new Order(Direction.DESC, "operationTime")));
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, MemberOperationRecord.class);
	}

	@Override
	public long countAmountByConditions(MemberOperationRecord record) {
		Query query = new Query();
		if (record.getMemberId() != null && !"".equals(record.getMemberId())) {
			query.addCriteria(Criteria.where("memberId").is(record.getMemberId()));
		}
		if (record.getDesc() != null && !"".equals(record.getDesc())) {
			query.addCriteria(Criteria.where("desc").regex(record.getDesc()));
		}
		return mongoTemplate.count(query, MemberOperationRecord.class);
	}

}
