package com.anbang.qipai.admin.plan.dao.tasksdao;

import com.anbang.qipai.admin.plan.bean.tasks.WhiteList;

import java.util.List;

public interface WhiteListDao {

    void addWhiteList(WhiteList whiteList);

    void deleteWhiteList(String[] ids);

    List<WhiteList> findWhiteLists(int page, int size, List<String> playerIds, boolean flag);

    long countWhiteList(List<String> playerIds, boolean flag);
}
