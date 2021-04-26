package com.anbang.qipai.qinyouquan.msg.receiver;


import com.anbang.qipai.qinyouquan.cqrs.q.dbo.MemberDbo;
import com.anbang.qipai.qinyouquan.cqrs.q.service.MemberService;
import com.anbang.qipai.qinyouquan.msg.msjobs.CommonMO;
import com.anbang.qipai.qinyouquan.msg.sink.MembersSink;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MembersSink.class)
public class MembersMsgReceiver {
    @Autowired
    private MemberService memberService;

    private Gson gson = new Gson();

    @StreamListener(MembersSink.MEMBERS)
    public void recordMember(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        MemberDbo member = gson.fromJson(json, MemberDbo.class);
        try {
            if ("newMember".equals(msg)) {
                memberService.saveMemberDbo(member);
            }
            if ("update member info".equals(msg)) {
                memberService.updateMember(member.getId(), member.getNickname(), member.getHeadimgurl());
            }
            if ("update member dalianmeng apply".equals(msg)){
                memberService.updateMemberDalianmengApply(member.getId(),member.isDalianmeng(),member.isQinyouquan());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
