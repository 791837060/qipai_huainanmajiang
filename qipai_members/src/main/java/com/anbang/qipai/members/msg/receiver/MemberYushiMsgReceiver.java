package com.anbang.qipai.members.msg.receiver;


import com.anbang.qipai.members.msg.channel.sink.MemberYushiSink;
import com.anbang.qipai.members.msg.msjobj.CommonMO;
import com.anbang.qipai.members.plan.bean.MemberYushi;
import com.anbang.qipai.members.plan.service.MemberYushiService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author cxy
 * @program: qipai
 * @Date: Created in 2019/12/9 11:54
 */
@EnableBinding(MemberYushiSink.class)
public class MemberYushiMsgReceiver {
    @Autowired
    private MemberYushiService memberYushiService;

    private Gson gson = new Gson();

    @StreamListener(MemberYushiSink.channel)
    public void memberYushi(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        if (MemberYushiSink.addYushi.equals(msg)) {
            MemberYushi yushi = gson.fromJson(json, MemberYushi.class);
            memberYushiService.addYushi(yushi);
        }
        if (MemberYushiSink.deleteYushi.equals(msg)) {
            String[] YushiIds = gson.fromJson(json, String[].class);
            memberYushiService.deleteYushiByIds(YushiIds);
        }
        if (MemberYushiSink.updateYushi.equals(msg)) {
            MemberYushi card = gson.fromJson(json, MemberYushi.class);
            memberYushiService.updateYushi(card);
        }
    }
}
