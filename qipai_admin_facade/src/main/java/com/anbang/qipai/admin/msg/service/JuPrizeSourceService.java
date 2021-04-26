package com.anbang.qipai.admin.msg.service;

import com.anbang.qipai.admin.msg.channel.source.JuPrizeSource;
import com.anbang.qipai.admin.msg.channel.source.MemberClubCardsSource;
import com.anbang.qipai.admin.msg.channel.source.SignInPrizeSource;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.juprize.JuPrizeRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

/**
 * 局奖励发布
 */
@EnableBinding(JuPrizeSource.class)
public class JuPrizeSourceService {

    @Autowired
    private JuPrizeSource juPrizeSource;

    public void juPrizeRelease(List<JuPrizeRelease> juPrizeReleases) {
        CommonMO mo = new CommonMO();
        mo.setMsg("juPrizeRelease");
        mo.setData(juPrizeReleases);
        juPrizeSource.JuPrize().send(MessageBuilder.withPayload(mo).build());
    }
}
