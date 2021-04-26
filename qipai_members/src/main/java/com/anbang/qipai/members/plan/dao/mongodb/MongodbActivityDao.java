package com.anbang.qipai.members.plan.dao.mongodb;



import com.anbang.qipai.members.plan.bean.Activity;
import com.anbang.qipai.members.plan.bean.ActivityState;
import com.anbang.qipai.members.plan.dao.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongodbActivityDao implements ActivityDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void addActivity(Activity activity) {
		mongoTemplate.insert(activity);
	}

	@Override
	public void updateActivityStateById(String activityId, ActivityState state) {
		Query query = new Query(Criteria.where("id").is(activityId));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateFirst(query, update, Activity.class);
	}

	@Override
	public List<Activity> findActivity() {
		Query query = new Query(Criteria.where("state").is(ActivityState.START));
		query.with(new Sort(new Order(Direction.DESC, "createTime")));
		return mongoTemplate.find(query, Activity.class);
	}

	@Override
	public Activity findActivityById(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId));
		return mongoTemplate.findOne(query, Activity.class);
	}

	@Override
	public void deleteActivityById(String activityId) {
		Query query = new Query(Criteria.where("id").is(activityId));
		mongoTemplate.remove(query, Activity.class);
	}

}
