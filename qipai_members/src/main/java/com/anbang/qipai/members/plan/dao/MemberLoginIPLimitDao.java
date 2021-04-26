package com.anbang.qipai.members.plan.dao;


import com.anbang.qipai.members.plan.bean.MemberLoginIPLimit;

import java.util.List;

/**
 * @Description:
 */
public interface MemberLoginIPLimitDao {
    void save(MemberLoginIPLimit record);

    void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient);

    List<MemberLoginIPLimit> findMemberLoginLimitRecordByLoginIp(int page, int size, String loginIp);

    long getAmountByLoginIp(String loginIp);

    MemberLoginIPLimit getByLoginIp(String loginIp);
}
