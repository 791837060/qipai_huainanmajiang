package com.anbang.qipai.admin.plan.dao.permissiondao;

import com.anbang.qipai.admin.plan.bean.permission.AdminDiamondLimitRecord;

import java.util.List;

public interface AdminDiamondLimitRecordDao {
    void updateAdminDiamondLimint(AdminDiamondLimitRecord adminDiamondLimitRecord);

    List<AdminDiamondLimitRecord> findByUser(String adminId, long startTime, long endTime, int page, int size);

    long count(String adminId, long startTime, long endTime);
}
