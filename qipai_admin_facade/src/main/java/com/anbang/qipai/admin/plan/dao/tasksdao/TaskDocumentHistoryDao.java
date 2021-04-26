package com.anbang.qipai.admin.plan.dao.tasksdao;

import java.util.List;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistoryState;
import org.springframework.data.domain.Sort;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistory;

public interface TaskDocumentHistoryDao {

	List<TaskDocumentHistory> findTaskDocumentHistory(int page, int size, Sort sort, TaskDocumentHistory task);

	long getAmount(TaskDocumentHistory task);

	void addTaskDocumentHistory(TaskDocumentHistory task);

	void updateTaskState(String[] taskIds, TaskDocumentHistoryState state);
}
