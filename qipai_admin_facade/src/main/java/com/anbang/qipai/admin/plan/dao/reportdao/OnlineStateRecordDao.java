package com.anbang.qipai.admin.plan.dao.reportdao;

import com.anbang.qipai.admin.plan.bean.report.OnlineStateRecord;

import java.util.List;

/**
 * @author YaphetS
 * @date 2018/11/24
 */
public interface OnlineStateRecordDao {
    /**
     * 新增一条上下线记录
     * @param onlineStateRecord
     */
    void insert(OnlineStateRecord onlineStateRecord);

    /**
     * 计算启动次数
     * @return
     */
    long countOnlineRecord();

    /**
     * 计算某个时刻后的上线记录数(根据用户id去重)
     * @param createTime
     * @return
     */
    long countOnlineRecordAfterTime(long createTime);

    /**
     * 查询某个时刻后的上线记录数
     * @param dayStartTime
     * @return
     */
    List<OnlineStateRecord> findOnlineRecordAfterTime(long dayStartTime);

    /**
     * 查询某个时刻后某个memberId的上下线记录
     * @param memberId
     * @param createTime
     * @return
     */
    List<OnlineStateRecord> findByMemberIdAfterTime(String memberId, Long createTime);

    /**
     * 最后一次记录
     */
    OnlineStateRecord lastRecord(String id);

    /**
     * 某个时间段之间的id
     */
    public List<String> listIdsByTime(long startTime, long endTime);
}
