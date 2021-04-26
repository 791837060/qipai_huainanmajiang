package com.anbang.qipai.admin.msg.receiver.tasksreceiver;

import com.anbang.qipai.admin.msg.channel.sink.ActivitySink;
import com.anbang.qipai.admin.msg.msjobj.CommonMO;
import com.anbang.qipai.admin.plan.bean.tasks.Activity;
import com.anbang.qipai.admin.plan.service.tasksservice.ActivityService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ActivitySink.class)
public class ActivityMsgReceiver {

    @Autowired
    private ActivityService activityService;

    private Gson gson = new Gson();

    @StreamListener(ActivitySink.ACTIVITY)
    public void activity(CommonMO mo) {
        String msg = mo.getMsg();
        String json = gson.toJson(mo.getData());
        Activity activity = gson.fromJson(json, Activity.class);
        try {
            if ("add activity".equals(msg)) {
                activityService.addActivity(activity);
            }
            if ("start activity".equals(msg)) {
                activityService.updateActivityState(activity.getId(), activity.getState());
            }
            if ("stop activity".equals(msg)) {
                activityService.updateActivityState(activity.getId(), activity.getState());
            }
            if ("delete activity".equals(msg)) {
                activityService.deleteActivity(activity.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
