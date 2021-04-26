package com.anbang.qipai.members.msg.service;



import com.anbang.qipai.members.msg.channel.source.ActivitySoure;
import com.anbang.qipai.members.msg.msjobj.CommonMO;
import com.anbang.qipai.members.plan.bean.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(ActivitySoure.class)
public class ActivityMsgService {

	@Autowired
	private ActivitySoure activitySoure;

	public void addActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("add activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}

	public void startActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("start activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}

	public void stopActivity(Activity activity) {
		CommonMO mo = new CommonMO();
		mo.setMsg("stop activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}

	public void deleteActivity(String activityId) {
		CommonMO mo = new CommonMO();
		Activity activity = new Activity();
		activity.setId(activityId);
		mo.setMsg("delete activity");
		mo.setData(activity);

		activitySoure.activity().send(MessageBuilder.withPayload(mo).build());
	}
}
