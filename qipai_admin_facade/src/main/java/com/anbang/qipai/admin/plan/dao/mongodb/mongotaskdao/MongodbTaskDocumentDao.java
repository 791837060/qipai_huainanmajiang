package com.anbang.qipai.admin.plan.dao.mongodb.mongotaskdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocument;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskDocumentDao;

@Component
public class MongodbTaskDocumentDao implements TaskDocumentDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<TaskDocument> findTaskDocuments(int page, int size, TaskDocument taskDoc) {
		Query query = new Query();
		if (taskDoc.getName() != null && !"".equals(taskDoc.getName())) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getType() != null && !"".equals(taskDoc.getType())) {
			query.addCriteria(Criteria.where("type").is(taskDoc.getType()));
		}
		query.skip((page - 1) * size);
		query.limit(size);
		return mongoTemplate.find(query, TaskDocument.class);
	}

	@Override
	public long getAmount(TaskDocument taskDoc) {
		Query query = new Query();
		if (taskDoc.getName() != null && !"".equals(taskDoc.getName())) {
			query.addCriteria(Criteria.where("name").regex(taskDoc.getName()));
		}
		if (taskDoc.getType() != null && !"".equals(taskDoc.getType())) {
			query.addCriteria(Criteria.where("type").is(taskDoc.getType()));
		}
		return mongoTemplate.count(query, TaskDocument.class);
	}

	@Override
	public void addTaskDocument(TaskDocument taskDoc) {
		mongoTemplate.insert(taskDoc);
	}

	@Override
	public void deleteTaskDocuments(String[] taskDocIds) {
		Object[] ids = taskDocIds;
		Query query = new Query(Criteria.where("id").in(ids));
		mongoTemplate.remove(query, TaskDocument.class);
	}

	@Override
	public void updateTaskDocument(TaskDocument taskDoc) {
		Query query = new Query(Criteria.where("id").is(taskDoc.getId()));
		Update update = new Update();
		if (taskDoc.getDesc() != null) {
			update.set("desc", taskDoc.getDesc());
		}
		if (taskDoc.getName() != null) {
			update.set("name", taskDoc.getName());
		}
		if (taskDoc.getTaskName() != null) {
			update.set("taskType", taskDoc.getTaskName());
		}
		if (taskDoc.getType() != null) {
			update.set("type", taskDoc.getType());
		}
		mongoTemplate.updateFirst(query, update, TaskDocument.class);
	}

	@Override
	public TaskDocument findTaskDocumentById(String taskDocId) {
		Query query = new Query(Criteria.where("id").is(taskDocId));
		return mongoTemplate.findOne(query, TaskDocument.class);
	}

}
