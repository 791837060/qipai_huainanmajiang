package com.anbang.qipai.admin.plan.service.tasksservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbang.qipai.admin.plan.bean.tasks.RewardType;
import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anbang.qipai.admin.plan.bean.tasks.TaskDocument;
import com.anbang.qipai.admin.plan.bean.tasks.TaskDocumentHistory;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskDocumentHistoryDao;
import com.highto.framework.web.page.ListPage;

@Service
public class TaskDocumentHistoryService {

	@Autowired
	private TaskDocumentHistoryDao taskDocumentHistoryDao;

	public ListPage queryTaskDocumentHistory(int page, int size, Sort sort, TaskDocumentHistory task) {
		long amount = taskDocumentHistoryDao.getAmount(task);
		List<TaskDocumentHistory> taskList = taskDocumentHistoryDao.findTaskDocumentHistory(page, size, sort, task);
		List<Map> data = new ArrayList<>();
		for (TaskDocumentHistory list : taskList) {
			Map map = new HashMap();
			map.put("id", list.getId());
			map.put("taskDocId", list.getTaskDocId());
			map.put("name", list.getName());
			map.put("desc", list.getDesc());
			map.put("type", list.getType());
			map.put("taskName", list.getTaskName());
			map.put("rewardType", RewardType.toMap().get(list.getRewardType().name()));
			map.put("rewardNum", list.getRewardNum());
			map.put("vip", list.getVip());
			map.put("targetNum", list.getTargetNum());
			map.put("limitTime", list.getLimitTime());
			map.put("taskType", list.getTaskType());
			map.put("state", list.getState());
			map.put("promulgator", list.getPromulgator());
			map.put("weight", list.getWeight());
			data.add(map);
		}
		ListPage listPage = new ListPage(data, page, size, (int) amount);
		return listPage;
	}

	public void addTaskDocumentHistory(TaskDocumentHistory task) {
		taskDocumentHistoryDao.addTaskDocumentHistory(task);
	}

	public void withdrawTaskDocumentHistory(String[] taskIds) {
		taskDocumentHistoryDao.updateTaskState(taskIds, TaskDocumentHistoryState.STOP);
	}

	public TaskDocumentHistory releaseTaskDocumentHistory(TaskDocument taskDoc, TaskDocumentHistory task) {
		task.setName(taskDoc.getName());
		task.setDesc(taskDoc.getDesc());
		task.setType(taskDoc.getType());
		task.setTaskName(taskDoc.getTaskName());
		return task;
	}
}
