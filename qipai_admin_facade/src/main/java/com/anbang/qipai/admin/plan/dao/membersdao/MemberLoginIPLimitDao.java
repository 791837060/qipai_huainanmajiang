package com.anbang.qipai.admin.plan.dao.membersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberLoginIPLimit;

import java.util.List;

/**
 * @Description:
 */
public interface MemberLoginIPLimitDao {
    void save(MemberLoginIPLimit record);

    void updateMemberLoginLimitRecordEfficientById(String[] ids, boolean efficient);

    List< MemberLoginIPLimit> findMemberLoginLimitRecordByLoginIp(int page, int size, String loginIp);

    long getAmountByLoginIp(String loginIp);
}
