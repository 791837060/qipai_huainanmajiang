package com.anbang.qipai.admin.plan.service.tasksservice;

import com.anbang.qipai.admin.plan.bean.tasks.ExchangeRecord;
import com.anbang.qipai.admin.plan.dao.tasksdao.ExchangeRecordDao;
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
public class ExchangeRecordService {

    @Autowired
    private ExchangeRecordDao exchangeRecordDao;

    public void addExchangeRecord(ExchangeRecord exchangeRecord) {
        exchangeRecordDao.addExchangeRecord(exchangeRecord);
    }

    public void updateExchangeRecord(ExchangeRecord exchangeRecord) {
        exchangeRecordDao.updateExchangeRecord(exchangeRecord);
    }

    public ListPage getExchangeRecords(int page, int size, TaskCommonQuery taskCommonQuery) {
        long count = exchangeRecordDao.countExchangeRecord(taskCommonQuery);
        List<ExchangeRecord> exchangeRecords = exchangeRecordDao.getExchangeRecords(page, size, taskCommonQuery);
        ListPage result = new ListPage(exchangeRecords, page, size, (int) count);
        return result;
    }

}
