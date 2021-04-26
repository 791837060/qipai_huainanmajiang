package com.anbang.qipai.members.plan.dao;

import com.anbang.qipai.members.plan.bean.Activity;
import com.anbang.qipai.members.plan.bean.ActivityState;

import java.util.List;

public interface ActivityDao {
	void addActivity(Activity activity);

	void updateActivityStateById(String activityId, ActivityState state);

	List<Activity> findActivity();

	Activity findActivityById(String activityId);

	void deleteActivityById(String activityId);
}
