package com.anbang.qipai.admin.msg.receiver.memberreceiver;

import com.anbang.qipai.admin.msg.channel.sink.MemberOrdersSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.members.MemberOrder;
import com.anbang.qipai.admin.plan.service.membersservice.MemberOrderService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MemberOrdersSink.class)
public class MemberOrdersMsgReceiver {
    @Autowired
    private MemberOrderService orderService;

    private Gson gson = new Gson();

    @StreamListener(MemberOrdersSink.ORDERS)
    public void recordOrder(CommonMO mo) {
        String json = gson.toJson(mo.getData());
        String msg = mo.getMsg();
        MemberOrder order = gson.fromJson(json, MemberOrder.class);
        try {
            if ("newOrder".equals(msg)) {
                orderService.addOrder(order);
            }
            if ("order finished".equals(msg)) {
                orderService.orderFinished(order.getId(), order.getTransaction_id(), order.getStatus(),
                        order.getDeliveTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
