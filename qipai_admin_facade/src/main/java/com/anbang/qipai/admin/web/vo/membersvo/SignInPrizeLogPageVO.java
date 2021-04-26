package com.anbang.qipai.admin.web.vo.membersvo;

import com.anbang.qipai.admin.plan.bean.signin.SignInPrizeLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 吴硕涵
 * @Date: 2018/12/27 5:03 PM
 * @Version 1.0
 */
public class SignInPrizeLogPageVO {
    List<SignInPrizeLog> list = new ArrayList<>();
    int count ;
    int page;
    int size;

    public List<SignInPrizeLog> getList() {
        return list;
    }

    public void setList(List<SignInPrizeLog> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}
