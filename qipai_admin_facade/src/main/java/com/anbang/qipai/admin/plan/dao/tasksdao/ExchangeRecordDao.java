package com.anbang.qipai.admin.plan.dao.tasksdao;

import com.anbang.qipai.admin.plan.bean.tasks.ExchangeRecord;
import com.anbang.qipai.admin.web.query.TaskCommonQuery;

import java.util.List;

public interface ExchangeRecordDao {
    void addExchangeRecord (ExchangeRecord exchangeRecord);

    void updateExchangeRecord (ExchangeRecord exchangeRecord);

    long countExchangeRecord(TaskCommonQuery taskCommonQuery);

    List<ExchangeRecord> getExchangeRecords(int page, int size, TaskCommonQuery taskCommonQuery);
}
