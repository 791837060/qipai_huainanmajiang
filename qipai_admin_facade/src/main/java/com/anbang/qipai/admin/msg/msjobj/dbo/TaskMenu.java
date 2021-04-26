package com.anbang.qipai.admin.msg.msjobj.dbo;

/**
 * 任务按钮
 * 
 * @author lsc
 *
 */
public class TaskMenu {

	private String name;// 名称
	private TaskAction action;// 动作

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskAction getAction() {
		return action;
	}

	public void setAction(TaskAction action) {
		this.action = action;
	}

}
