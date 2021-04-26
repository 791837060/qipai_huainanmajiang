
package com.anbang.qipai.admin.msg.msjobj.dbo;

/**
 * 任务包装类
 * 
 * @author lsc
 *
 */
public class Task {
	private String id;
	private String taskId;// 任务历史id
	private String memberId;// 会员id
	private String name;// 任务主题
	private String desc;// 任务描述
	private String type;// 任务类型，前台分类
	private String taskName;// 任务种类,后台
	private RewardType rewardType;// 奖励类型
	private double rewardNum;// 奖励数量
	private TaskMenu menu;// 任务按钮
	private TaskState taskState;// 任务状态
	private int targetNum;// 总进度
	private int finishNum;// 完成进度
	private String rewardUrl;// 领奖地址
	private long deadTime;// 终止时间
	private TaskType taskType;// 任务分类：每日，单次
	private ITarget target;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
	}

	public double getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(double rewardNum) {
		this.rewardNum = rewardNum;
	}

	public String getRewardUrl() {
		return rewardUrl;
	}

	public void setRewardUrl(String rewardUrl) {
		this.rewardUrl = rewardUrl;
	}

	public TaskMenu getMenu() {
		return menu;
	}

	public void setMenu(TaskMenu menu) {
		this.menu = menu;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public int getTargetNum() {
		return targetNum;
	}

	public void setTargetNum(int targetNum) {
		this.targetNum = targetNum;
	}

	public int getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}

	public long getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(long deadTime) {
		this.deadTime = deadTime;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public ITarget getTarget() {
		return target;
	}

	public void setTarget(ITarget target) {
		this.target = target;
	}

}
