package com.anbang.qipai.admin.web.vo.membersvo;

import com.anbang.qipai.admin.plan.bean.members.MemberExchangeEntityDbo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/27 5:09 PM
 * @Version 1.0
 */
public class MemberExchangeEntityPageVO {
    List<MemberExchangeEntityDbo> list = new ArrayList<>();
    int page;
    int size;
    int count;

    public List<MemberExchangeEntityDbo> getList() {
        return list;
    }

    public void setList(List<MemberExchangeEntityDbo> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
