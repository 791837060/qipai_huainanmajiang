package com.anbang.qipai.members.plan.dao;

import com.anbang.qipai.members.plan.bean.MemberYushi;

import java.util.List;

public interface MemberYushiDao {
    List<MemberYushi> findAllYushi();

    MemberYushi getYushiById(String yushiId);

    void addYushi(MemberYushi yushi);

    void deleteYushiByIds(String[] yushiId);

    void updateYushi(MemberYushi yushi);
}
