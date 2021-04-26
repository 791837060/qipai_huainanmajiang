package com.anbang.qipai.admin.plan.dao;

;

import com.anbang.qipai.admin.plan.bean.Complaint;

import java.util.List;

public interface ComplaintDao {


    void save(Complaint complaint);

    long countBymemberId(String memberId);

    List<Complaint> find(int page, int size, String memberId);

}
