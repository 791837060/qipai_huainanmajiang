package com.anbang.qipai.admin.msg.service;

import com.anbang.qipai.admin.msg.channel.source.MemberYushiSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberYushi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/12/9 11:06
 */
@EnableBinding(MemberYushiSource.class)
public class MemberYushiMsgService {
    @Autowired
    private MemberYushiSource memberYushiSource;

    public void addYushi(MemberYushi yushi) {
        CommonMO mo = new CommonMO();
        mo.setMsg(MemberYushiSource.addYushi);
        mo.setData(yushi);
        memberYushiSource.memberYushi().send(MessageBuilder.withPayload(mo).build());
    }

    public void deleteYushi(String[] yushiIds) {
        CommonMO mo = new CommonMO();
        mo.setMsg(MemberYushiSource.deleteYushi);
        mo.setData(yushiIds);
        memberYushiSource.memberYushi().send(MessageBuilder.withPayload(mo).build());
    }

    public void updateYushi(MemberYushi yushi) {
        CommonMO mo = new CommonMO();
        mo.setMsg(MemberYushiSource.updateYushi);
        mo.setData(yushi);
        memberYushiSource.memberYushi().send(MessageBuilder.withPayload(mo).build());
    }
}
