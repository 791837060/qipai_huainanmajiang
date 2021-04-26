package com.anbang.qipai.admin.plan.dao.tasksdao;

import com.anbang.qipai.admin.plan.bean.tasks.TaskReceiveRecord;
import com.anbang.qipai.admin.web.query.TaskCommonQuery;

import java.util.List;

public interface TaskReceiveRecordDao {

    void addTaskReceiveRecord (TaskReceiveRecord taskReceiveRecord);

    long countTaskReceiveRecord(TaskCommonQuery taskCommonQuery);

    List<TaskReceiveRecord> getTaskReceiveRecords(int page, int size, TaskCommonQuery taskCommonQuery);
}
