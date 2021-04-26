package com.anbang.qipai.admin.plan.service;


import com.anbang.qipai.admin.plan.bean.Complaint;
import com.anbang.qipai.admin.plan.dao.ComplaintDao;
import com.highto.framework.web.page.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    public ListPage find(int page, int size, String memberId) {
        int count=(int)complaintDao.countBymemberId(memberId);
        List<Complaint> complaintList=complaintDao.find(page,size,memberId);
        return new ListPage(complaintList, page, size, count);
    }

    public void addComplaint(Complaint complaint) {
        complaintDao.save(complaint);
    }
}
