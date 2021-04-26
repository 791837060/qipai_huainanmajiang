package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginLimitRecord;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberLoginLimitRecordDao;

@Component
public class MongodbMemberLoginLimitRecordDao implements MemberLoginLimitRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberLoginLimitRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient) {
		Object[] tempIds = ids;
		Query query = new Query(Criteria.where("id").in(tempIds));
		Update update = new Update();
		update.set("efficient", efficient);
		mongoTemplate.updateMulti(query, update, MemberLoginLimitRecord.class);
	}

	@Override
	public List<MemberLoginLimitRecord> findMemberLoginLimitRecordByMemberId(int page, int size, String memberId) {
		Query query = new Query();
		if (memberId != null && !"".equals(memberId)) {
			query.addCriteria(Criteria.where("memberId").is(memberId));
		}
		query.addCriteria(Criteria.where("efficient").is(true));
		query.limit(size);
		query.skip((page - 1) * size);
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		return mongoTemplate.find(query, MemberLoginLimitRecord.class);
	}

	@Override
	public long getAmountByMemberId(String memberId) {
		Query query = new Query();
		if (memberId != null && !"".equals(memberId)) {
			query.addCriteria(Criteria.where("memberId").is(memberId));
		}
		query.addCriteria(Criteria.where("efficient").is(true));
		return mongoTemplate.count(query, MemberLoginLimitRecord.class);
	}

}
