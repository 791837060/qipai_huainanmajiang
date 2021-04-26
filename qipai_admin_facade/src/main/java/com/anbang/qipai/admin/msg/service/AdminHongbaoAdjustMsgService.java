package com.anbang.qipai.admin.msg.service;

import com.anbang.qipai.admin.msg.channel.source.AdminHongbaoAdjustSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Description: 玩家邀请的用户状态变更
 */
@EnableBinding(AdminHongbaoAdjustSource.class)
public class AdminHongbaoAdjustMsgService {

    @Autowired
    private AdminHongbaoAdjustSource adminHongbaoAdjustSource;

    public void updateInvitationState(String invitationMemberId) {
        CommonMO mo = new CommonMO();
        mo.setMsg("update_invitation_state");
        mo.setData(invitationMemberId);
        adminHongbaoAdjustSource.adminHongbaoAdjust().send(MessageBuilder.withPayload(mo).build());
    }
}
