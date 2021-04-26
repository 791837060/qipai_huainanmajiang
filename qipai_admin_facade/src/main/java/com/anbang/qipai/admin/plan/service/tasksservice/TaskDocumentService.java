package com.anbang.qipai.admin.plan.service.tasksservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocument;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskDocumentDao;
import com.highto.framework.web.page.ListPage;

@Service
public class TaskDocumentService {

	@Autowired
	private TaskDocumentDao taskDocumentDao;

	public void addTaskDocument(TaskDocument taskDoc) {
		String id = UUID.randomUUID().toString().substring(0, 8);
		taskDoc.setId(id);
		taskDocumentDao.addTaskDocument(taskDoc);
	}

	public void deleteTaskDocuments(String[] taskDocIds) {
		taskDocumentDao.deleteTaskDocuments(taskDocIds);
	}

	public void updateTaskDocument(TaskDocument taskDoc) {
		taskDocumentDao.updateTaskDocument(taskDoc);
	}

	public ListPage findTaskDocuments(int page, int size, TaskDocument taskDoc) {
		long amount = taskDocumentDao.getAmount(taskDoc);
		List<TaskDocument> taskDocList = taskDocumentDao.findTaskDocuments(page, size, taskDoc);
		ListPage listPage = new ListPage(taskDocList, page, size, (int) amount);
		return listPage;
	}

	public TaskDocument findTaskDocumentById(String taskDocId) {
		return taskDocumentDao.findTaskDocumentById(taskDocId);
	}
}
