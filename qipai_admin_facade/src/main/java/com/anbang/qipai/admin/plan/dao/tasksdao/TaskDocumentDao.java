package com.anbang.qipai.admin.plan.dao.tasksdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocument;

public interface TaskDocumentDao {

	long getAmount(TaskDocument taskDoc);

	void addTaskDocument(TaskDocument taskDoc);

	void deleteTaskDocuments(String[] taskDocIds);

	void updateTaskDocument(TaskDocument taskDoc);

	List<TaskDocument> findTaskDocuments(int page, int size, TaskDocument taskDoc);

	TaskDocument findTaskDocumentById(String taskDocId);

}
