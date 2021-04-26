package com.anbang.qipai.admin.msg.service;

import com.anbang.qipai.admin.msg.channel.source.AdminHongbaoAdjustSource;
import com.anbang.qipai.admin.msg.channel.source.LoginIPLimitSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberLoginIPLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 玩家登录IP限制
 */
@EnableBinding(LoginIPLimitSource.class)
public class LoginIPLimitMsgService {

    @Autowired
    private LoginIPLimitSource loginIPLimitSource;

    public void addIPLiimt(MemberLoginIPLimit memberLoginIPLimit) {
        CommonMO mo = new CommonMO();
        mo.setMsg("add_ip_limit");
        mo.setData(memberLoginIPLimit);
        loginIPLimitSource.loginIPLimit().send(MessageBuilder.withPayload(mo).build());
    }

    public void deleteIPLimitByIds(String[] ids) {
        CommonMO mo = new CommonMO();
        mo.setMsg("delete_ip_limits");
        mo.setData(ids);
        loginIPLimitSource.loginIPLimit().send(MessageBuilder.withPayload(mo).build());
    }
}
