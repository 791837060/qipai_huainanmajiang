package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberTypeSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberType;
import com.anbang.qipai.admin.plan.service.membersservice.MemberTypeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @Description:
 */
@EnableBinding(MemberTypeSink.class)
public class MemberTypeMsgReceiver {

    @Autowired
    private MemberTypeService memberTypeService;

    private Gson gson = new Gson();

    @StreamListener(MemberTypeSink.MEMBERTYPE)
    public void memberType(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if ("saveMemberType".equals(msg)) {
                MemberType memberType = gson.fromJson(json, MemberType.class);
                memberTypeService.saveMemberType(memberType);
            }

            // 不删除，设置为无效
            if ("removeMemberType".equals(msg)) {
                String id = gson.fromJson(json, String.class);
                memberTypeService.removeMemberType(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
