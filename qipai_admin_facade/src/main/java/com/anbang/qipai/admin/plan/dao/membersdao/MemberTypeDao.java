package com.anbang.qipai.admin.plan.dao.membersdao;

import com.anbang.qipai.admin.plan.bean.members.MemberType;
import com.anbang.qipai.admin.web.vo.reportvo.MemberRatioVo;

import java.util.List;

public interface MemberTypeDao {

    void save(MemberType memberType);

    void remove(String id);

    List<MemberType> listByBeanAndTime(MemberType memberType, Long time);

    int countPayPlayer();

    /**
     * @param ids ID list ,为空默认分类所有
     */
    List<MemberRatioVo> queryRatio(List<String> ids);
}
