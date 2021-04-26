package com.anbang.qipai.members.plan.service;



import com.anbang.qipai.members.plan.bean.Activity;
import com.anbang.qipai.members.plan.bean.ActivityState;
import com.anbang.qipai.members.plan.dao.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

	@Autowired
	private ActivityDao activityDao;

	public void addActivity(Activity activity) {
		activityDao.addActivity(activity);
	}

	public Activity startActivity(String activityId) {
		activityDao.updateActivityStateById(activityId, ActivityState.START);
		return activityDao.findActivityById(activityId);
	}

	public Activity stopActivity(String activityId) {
		activityDao.updateActivityStateById(activityId, ActivityState.STOP);
		return activityDao.findActivityById(activityId);
	}

	public List<Activity> findActivity() {
		return activityDao.findActivity();
	}

	public void deleteActivity(String activityId) {
		activityDao.deleteActivityById(activityId);
	}
}
