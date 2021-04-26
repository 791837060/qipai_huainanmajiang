package com.anbang.qipai.admin.plan.service.permissionservice;


import com.anbang.qipai.admin.plan.bean.permission.Admin;
import com.anbang.qipai.admin.plan.bean.permission.AdminDiamondLimitRecord;
import com.anbang.qipai.admin.plan.dao.permissiondao.AdminDiamondLimitRecordDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDiamondLimitRecordService {

    @Autowired
    private AdminDiamondLimitRecordDao adminDiamondLimitRecordDao;

    public void updateAdminDiamondLimint(Admin admin, Integer amount) {
        AdminDiamondLimitRecord adminDiamondLimitRecord=new AdminDiamondLimitRecord();
        adminDiamondLimitRecord.setAdmin(admin);
        adminDiamondLimitRecord.setDiamondAmount(amount);
        adminDiamondLimitRecord.setAccountingTime(System.currentTimeMillis());
        adminDiamondLimitRecordDao.updateAdminDiamondLimint(adminDiamondLimitRecord);

    }

    public ListPage findByUser(String adminId, long startTime, long endTime, int page, int size) {
        int count = (int)adminDiamondLimitRecordDao.count(adminId,startTime,endTime);
        List<AdminDiamondLimitRecord> byUser = adminDiamondLimitRecordDao.findByUser(adminId, startTime, endTime, page, size);
        return new ListPage(byUser,page,size,count);
    }
}
