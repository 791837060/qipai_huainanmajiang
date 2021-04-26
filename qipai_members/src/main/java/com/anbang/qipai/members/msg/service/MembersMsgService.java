package com.anbang.qipai.members.msg.service;

import com.anbang.qipai.members.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.members.msg.channel.source.MembersSource;
import com.anbang.qipai.members.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MembersSource.class)
public class MembersMsgService {

    @Autowired
    private MembersSource membersSource;

    public void createMember(MemberDbo member) {
        CommonMO mo = new CommonMO();
        mo.setMsg("newMember");
        mo.setData(member);
        membersSource.members().send(MessageBuilder.withPayload(mo).build());
    }

    public void updateMemberPhone(MemberDbo member) {
        CommonMO mo = new CommonMO();
        mo.setMsg("update member phone");
        mo.setData(member);
        membersSource.members().send(MessageBuilder.withPayload(mo).build());
    }

    public void updateMemberBaseInfo(MemberDbo member) {
        CommonMO mo = new CommonMO();
        mo.setMsg("update member info");
        mo.setData(member);
        membersSource.members().send(MessageBuilder.withPayload(mo).build());
    }


    public void updateMemberRealUser(MemberDbo member) {
        CommonMO mo = new CommonMO();
        mo.setMsg("update member realUser");
        mo.setData(member);
        membersSource.members().send(MessageBuilder.withPayload(mo).build());
    }

    public void updateMemberDalianmengApply(MemberDbo member) {
        CommonMO mo = new CommonMO();
        mo.setMsg("update member dalianmeng apply");
        mo.setData(member);
        membersSource.members().send(MessageBuilder.withPayload(mo).build());
    }

}
