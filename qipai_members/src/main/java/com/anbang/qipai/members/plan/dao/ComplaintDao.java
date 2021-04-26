package com.anbang.qipai.members.plan.dao;

import com.anbang.qipai.members.plan.bean.Complaint;

import java.util.List;

public interface ComplaintDao {


    void save(Complaint complaint);

    long countBymemberId(String memberId);

    List<Complaint> find(int page, int size, String memberId);

}
