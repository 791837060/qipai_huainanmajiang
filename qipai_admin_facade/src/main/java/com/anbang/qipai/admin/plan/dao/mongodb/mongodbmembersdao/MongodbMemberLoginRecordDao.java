package com.anbang.qipai.admin.plan.dao.mongodb.mongodbmembersdao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginRecord;
import com.anbang.qipai.admin.plan.dao.membersdao.MemberLoginRecordDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

@Component
public class MongodbMemberLoginRecordDao implements MemberLoginRecordDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(MemberLoginRecord record) {
		mongoTemplate.insert(record);
	}

	@Override
	public void updateOnlineTimeById(String id, long onlineTime) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("onlineTime", onlineTime);
		mongoTemplate.updateFirst(query, update, MemberLoginRecord.class);
	}

	@Override
	public MemberLoginRecord findRecentRecordByMemberId(String memberId) {
		Query query = new Query(Criteria.where("memberId").is(memberId));
		// 需要建立索引
		Sort sort = new Sort(new Order(Direction.DESC, "loginTime"));
		query.with(sort);
		return mongoTemplate.findOne(query, MemberLoginRecord.class);
	}

	@Override
	public int countLoginMemberByTime(long startTime, long endTime) {
		List<DBObject> pipeline = new ArrayList<>();
		DBObject match = new BasicDBObject();
		DBObject criteria = new BasicDBObject();
		match.put("loginTime", criteria);
		criteria.put("$gt", startTime);
		criteria.put("$lt", endTime);
		DBObject queryMatch = new BasicDBObject("$match", match);
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", "$memberId");
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);

		DBObject queryCount = new BasicDBObject("$count", "num");
		pipeline.add(queryCount);

		Cursor cursor = mongoTemplate.getCollection("memberLoginRecord").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int countRemainMemberByDeviationTime(long deviation) {
		List<DBObject> pipeline = new ArrayList<>();
		long loginTime = System.currentTimeMillis() - deviation;
		BasicDBObject project = new BasicDBObject();
		project.put("memberId", "$memberId");
		project.put("loginTime", "$loginTime");
		List<Object> subtract = new ArrayList<>();
		subtract.add("$loginTime");
		subtract.add(loginTime);
		BasicDBObject val = new BasicDBObject("$subtract", subtract);
		project.put("val", val);
		DBObject queryProject = new BasicDBObject("$project", project);
		pipeline.add(queryProject);

		DBObject queryMatch = new BasicDBObject("$match", new BasicDBObject("val", new BasicDBObject("$gt", 0)));
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", "$memberId");
		group.put("no", new BasicDBObject("$sum", 1));
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);

		DBObject queryCount = new BasicDBObject("$count", "num");
		pipeline.add(queryCount);

		Cursor cursor = mongoTemplate.getCollection("memberLoginRecord").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<MemberLoginRecord> findMemberLoginRecords(String loginIp, String memberId) {
		Query query = new Query();
		if (StringUtils.isNotBlank(loginIp)) {
			query.addCriteria(Criteria.where("loginIp").is(loginIp));
		}
		if (StringUtils.isNotBlank(memberId)) {
			query.addCriteria(Criteria.where("memberId").is(memberId));
		}
		return mongoTemplate.find(query, MemberLoginRecord.class);
	}

	@Override
	public List<MemberLoginRecord> findMemberLoginRecordByMemberIds(String[] memberIds) {
		Query query = new Query(Criteria.where("memberId").in(memberIds));
		return mongoTemplate.find(query,MemberLoginRecord.class);
	}

}
