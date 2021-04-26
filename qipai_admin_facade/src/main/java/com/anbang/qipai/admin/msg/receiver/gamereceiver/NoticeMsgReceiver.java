package com.anbang.qipai.admin.msg.receiver.gamereceiver;

import com.anbang.qipai.admin.msg.channel.sink.NoticeSink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.games.SystemNotice;
import com.anbang.qipai.admin.plan.service.gameservice.SystemNoticeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 接收系统公告返回消息类
 *
 * @author 程佳 2018.6.1 这里的类的注解是使一个或多个接口作为参数
 **/
@EnableBinding(NoticeSink.class)
public class NoticeMsgReceiver {

    @Autowired
    private SystemNoticeService systemNoticeService;

    private Gson gson = new Gson();

    /**
     * 接收信息的方法
     *
     * @param payload 接收的json数据 这里的方法的注解做消息监听
     **/
    @StreamListener(NoticeSink.notice)
    public void notice(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        try {
            if ("newNotice".equals(msg)) {
                List<SystemNotice> list = new ArrayList<>();
                List notices = gson.fromJson(json, ArrayList.class);
                notices.forEach((notice) -> {
                    list.add(new SystemNotice((Map) notice));
                });
                systemNoticeService.addSystemNotices(list);
            }
            if ("start notice".equals(msg)) {
                SystemNotice notice = gson.fromJson(json, SystemNotice.class);
                systemNoticeService.updateSystemNoticeState(notice.getId(), notice.getAdminName(), notice.getState());
            }
            if ("stop notice".equals(msg)) {
                SystemNotice notice = gson.fromJson(json, SystemNotice.class);
                systemNoticeService.updateSystemNoticeState(notice.getId(), notice.getAdminName(), notice.getState());
            }
            if ("remove notice".equals(msg)) {
                SystemNotice notice = gson.fromJson(json, SystemNotice.class);
                systemNoticeService.updateSystemNoticeValid(notice.getId(), notice.getAdminName(), notice.isValid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
