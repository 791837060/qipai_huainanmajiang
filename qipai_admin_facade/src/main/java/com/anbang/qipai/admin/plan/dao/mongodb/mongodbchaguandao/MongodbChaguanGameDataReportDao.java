package com.anbang.qipai.admin.plan.dao.mongodb.mongodbchaguandao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.chaguan.ChaguanGameDataReport;
import com.anbang.qipai.admin.plan.bean.games.Game;
import com.anbang.qipai.admin.plan.dao.chaguandao.ChaguanGameDataReportDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;

@Component
public class MongodbChaguanGameDataReportDao implements ChaguanGameDataReportDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<ChaguanGameDataReport> findReportByTime(long startTime, long endTime, Game game) {
		Query query = new Query(Criteria.where("game").is(game));
		query.addCriteria(Criteria.where("date").gte(startTime).lte(endTime));
		// 需要按照date建立索引
		query.with(new Sort(new Order(Direction.DESC, "date")));
		return mongoTemplate.find(query, ChaguanGameDataReport.class);
	}

	@Override
	public void addReport(ChaguanGameDataReport report) {
		mongoTemplate.insert(report);
	}

	@Override
	public int countGameNumByTime(long startTime, long endTime) {
		List<DBObject> pipeline = new ArrayList<>();
		BasicDBObject match = new BasicDBObject();
		match.put("date", new BasicDBObject("$gt", startTime).put("$lt", endTime));
		DBObject queryMatch = new BasicDBObject("$match", match);
		pipeline.add(queryMatch);

		BasicDBObject group = new BasicDBObject();
		group.put("_id", null);
		group.put("num", new BasicDBObject("$sum", "$gameNum"));
		DBObject queryGroup = new BasicDBObject("$group", group);
		pipeline.add(queryGroup);
		Cursor cursor = mongoTemplate.getCollection("chaguanGameDataReport").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

}
