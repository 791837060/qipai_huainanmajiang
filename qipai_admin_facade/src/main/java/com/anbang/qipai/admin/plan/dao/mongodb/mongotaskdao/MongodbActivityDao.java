package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.tasks.Activity;
import com.anbang.qipai.admin.plan.dao.tasksdao.ActivityDao;

@Component
public class MongodbActivityDao implements ActivityDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addActivity(Activity activity) {
		mongoTemplate.insert(activity);
	}

	@Override
	public void updateActivityStateById(String activityId, String state) {
		Query query = new Query(Criteria.where("id").is(activityId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, Activity.class);
	}

	@Override
	public List<Activity> findActivityByConditions(int page, int size, Activity activity) {
		Query query = new Query();
		if (activity.getTheme() != null && !"".equals(activity.getTheme())) {
			query.addCriteria(Criteria.where("theme").regex(activity.getTheme()));
		}
		if (activity.getState() != null && !"".equals(activity.getState())) {
			query.addCriteria(Criteria.where("state").is(activity.getState()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public long getAmountByConditions(Activity activity) {
		Query query = new Query();
		if (activity.getTheme() != null && !"".equals(activity.getTheme())) {
			query.addCriteria(Criteria.where("theme").regex(activity.getTheme()));
		}
		if (activity.getState() != null && !"".equals(activity.getState())) {
			query.addCriteria(Criteria.where("state").is(activity.getState()));
		}
		return mongoTemplate.count(query, Activity.class);
	}

	@Override
	public void deleteActivity(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId));
		mongoTemplate.remove(query,Activity.class);
	}

}
