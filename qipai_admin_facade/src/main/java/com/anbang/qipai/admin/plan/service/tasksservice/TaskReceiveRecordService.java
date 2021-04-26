package com.anbang.qipai.admin.plan.service.tasksservice;

import com.anbang.qipai.admin.plan.bean.tasks.TaskReceiveRecord;
import com.anbang.qipai.admin.plan.dao.tasksdao.TaskReceiveRecordDao;
import com.anbang.qipai.admin.web.query.TaskCommonQuery;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yins
 * @Description: TODO
 */
@Service
public class TaskReceiveRecordService {

    @Autowired
    private TaskReceiveRecordDao taskReceiveRecordDao;

    public void addTaskRceiveRecord(TaskReceiveRecord taskReceiveRecord) {
        taskReceiveRecordDao.addTaskReceiveRecord(taskReceiveRecord);
    }

    public ListPage getTaskReceiveRecords(int page, int size, TaskCommonQuery taskCommonQuery) {
        long count = taskReceiveRecordDao.countTaskReceiveRecord(taskCommonQuery);
        List<TaskReceiveRecord> receiveRecords = taskReceiveRecordDao.getTaskReceiveRecords(page, size, taskCommonQuery);
        ListPage result = new ListPage(receiveRecords, page, size, (int)count);
        return result;
    }
}
