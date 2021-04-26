package com.anbang.qipai.members.web.controller;


import com.anbang.qipai.members.msg.service.ActivityMsgService;
import com.anbang.qipai.members.plan.bean.Activity;
import com.anbang.qipai.members.plan.bean.ActivityState;
import com.anbang.qipai.members.plan.service.ActivityService;
import com.anbang.qipai.members.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 活动管理
 * 
 * @author 林少聪 2018.8.6
 *
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityMsgService activityMsgService;

	/**
	 * 添加活动
	 * 
	 * @param activity
	 * @return
	 */
	@RequestMapping("/addactivity")
	public CommonVO addActivity(@RequestBody Activity activity) {
		CommonVO vo = new CommonVO();
		activity.setState(ActivityState.STOP);
		activity.setCreateTime(System.currentTimeMillis());
		activityService.addActivity(activity);
		activityMsgService.addActivity(activity);
		return vo;
	}

	/**
	 * 启用活动
	 * 
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/startactivity")
	public CommonVO startActivity(@RequestParam(value = "activityId") String activityId) {
		CommonVO vo = new CommonVO();
		Activity activity = activityService.startActivity(activityId);
		activityMsgService.startActivity(activity);
		return vo;
	}

	/**
	 * 终止活动
	 * 
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/stopactivity")
	public CommonVO stopActivity(@RequestParam(value = "activityId") String activityId) {
		CommonVO vo = new CommonVO();
		Activity activity = activityService.stopActivity(activityId);
		activityMsgService.stopActivity(activity);
		return vo;
	}

	/**
	 * 查询所有活动
	 * 
	 * @return
	 */
	@RequestMapping("/queryactivity")
	public CommonVO queryActivity() {
		CommonVO vo = new CommonVO();
		List<Activity> activities = activityService.findActivity();
		vo.setSuccess(true);
		vo.setMsg("activities");
		vo.setData(activities);
		return vo;
	}

	/**
	 * 删除活动
	 * 
	 * @param activityId
	 * @return
	 */
	@RequestMapping("/deleteactivity")
	public CommonVO activity_delete(@RequestParam(value = "activityId") String activityId) {
		CommonVO vo = new CommonVO();
		activityService.deleteActivity(activityId);
		activityMsgService.deleteActivity(activityId);
		return vo;
	}
}
