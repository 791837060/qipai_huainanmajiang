package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberClubCardsSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberClubCard;
import com.anbang.qipai.admin.plan.service.membersservice.MemberClubCardService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MemberClubCardsSink.class)
public class MemberClubCardsMsgReceiver {

    @Autowired
    private MemberClubCardService memberClubCardService;

    private Gson gson = new Gson();

    @StreamListener(MemberClubCardsSink.MEMBERCLUBCARDS)
    public void memberClubCard(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if ("add memberclubcard".equals(msg)) {
                MemberClubCard card = gson.fromJson(json, MemberClubCard.class);
                memberClubCardService.addClubCard(card);
            }
            if ("delete memberclubcard".equals(msg)) {
                String[] clubCardIds = gson.fromJson(json, String[].class);
                memberClubCardService.deleteClubCardByIds(clubCardIds);
            }
            if ("update memberclubcard".equals(msg)) {
                MemberClubCard card = gson.fromJson(json, MemberClubCard.class);
                memberClubCardService.updateClubCard(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
