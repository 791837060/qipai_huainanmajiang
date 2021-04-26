package com.anbang.qipai.admin.plan.dao.membersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberYushi;

import java.util.List;

public interface MemberYushiDao {
    List<MemberYushi> findAllYushi();

    MemberYushi getYushiById(String yushiId);

    void addYushi(MemberYushi yushi);

    void deleteYushiByIds(String[] yushiId);

    void updateYushi(MemberYushi yushi);
}
