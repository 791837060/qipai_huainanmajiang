package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistory;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskDocumentHistoryDao;

@Component
public class MongodbTaskDocumentHistoryDao implements TaskDocumentHistoryDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<TaskDocumentHistory> findTaskDocumentHistory(int page, int size, Sort sort, TaskDocumentHistory task) {
		Query query = new Query();
		if (task.getName() != null && !"".equals(task.getName())) {
			query.addCriteria(Criteria.where("name").regex(task.getName()));
		}
		if (task.getTaskDocId() != null && !"".equals(task.getTaskDocId())) {
			query.addCriteria(Criteria.where("taskDocId").is(task.getTaskDocId()));
		}
		if (task.getType() != null && !"".equals(task.getType())) {
			query.addCriteria(Criteria.where("type").is(task.getType()));
		}
		if (task.getVip() != null) {
			query.addCriteria(Criteria.where("vip").is(task.getVip()));
		}
		query.addCriteria(Criteria.where("state").is("START"));
		query.skip((page - 1) * size);
		query.limit(size);
		query.with(sort);
		return mongoTemplate.find(query, TaskDocumentHistory.class);
	}

	@Override
	public long getAmount(TaskDocumentHistory task) {
		Query query = new Query();
		if (task.getName() != null && !"".equals(task.getName())) {
			query.addCriteria(Criteria.where("name").regex(task.getName()));
		}
		if (task.getTaskDocId() != null && !"".equals(task.getTaskDocId())) {
			query.addCriteria(Criteria.where("taskDocId").is(task.getTaskDocId()));
		}
		if (task.getType() != null && !"".equals(task.getType())) {
			query.addCriteria(Criteria.where("type").is(task.getType()));
		}
		if (task.getVip() != null) {
			query.addCriteria(Criteria.where("vip").is(task.getVip()));
		}
		query.addCriteria(Criteria.where("state").is("START"));
		return mongoTemplate.count(query, TaskDocumentHistory.class);
	}

	@Override
	public void addTaskDocumentHistory(TaskDocumentHistory task) {
		mongoTemplate.insert(task);
	}

	@Override
	public void updateTaskState(String[] taskIds, TaskDocumentHistoryState state) {
		Object[] ids = taskIds;
		Query query = new Query(Criteria.where("id").in(ids));
		Update update = new Update();
		update.set("state", state);
		mongoTemplate.updateMulti(query, update, TaskDocumentHistory.class);
	}

}
