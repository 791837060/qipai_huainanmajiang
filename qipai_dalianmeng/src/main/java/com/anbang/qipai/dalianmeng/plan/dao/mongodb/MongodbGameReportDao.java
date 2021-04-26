package com.anbang.qipai.dalianmeng.plan.dao.mongodb;

import com.anbang.qipai.dalianmeng.plan.bean.GameDataReport;
import com.anbang.qipai.dalianmeng.plan.bean.game.Game;
import com.anbang.qipai.dalianmeng.plan.dao.GameReportDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongodbGameReportDao implements GameReportDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<GameDataReport> findReportByTime(long startTime, long endTime, Game game) {
		Query query = new Query(Criteria.where("game").is(game));
		query.addCriteria(Criteria.where("date").gte(startTime).lte(endTime));
		// 需要按照date建立索引
		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "date")));
		return mongoTemplate.find(query, GameDataReport.class);
	}

	@Override
	public void addReport(GameDataReport report) {
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
		Cursor cursor = mongoTemplate.getCollection("gameDataReport").aggregate(pipeline,
				AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build());
		try {
			return (int) cursor.next().get("num");
		} catch (Exception e) {
			return 0;
		}
	}

}
