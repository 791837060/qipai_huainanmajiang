package com.anbang.qipai.admin.web.vo.juprize;

import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRecordDetail;

/**
 * @Description:
 */
public class JuPrizeRecordDetailQuery extends JuPrizeRecordDetail {
    private Long startTime;
    private Long endTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
