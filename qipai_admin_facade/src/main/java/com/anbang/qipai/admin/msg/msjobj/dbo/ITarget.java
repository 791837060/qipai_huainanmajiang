package com.anbang.qipai.admin.msg.msjobj.dbo;

import com.anbang.qipai.admin.plan.bean.members.MemberDbo;

/**
 * 所有任务均实现此接口
 * 
 * @author lsc
 *
 */
public interface ITarget {

	/**
	 * 初始化
	 */
	void init(Task task, MemberDbo member);

	/**
	 * 重置
	 */
	void reset(Task task, MemberDbo member);

	/**
	 * 更新任务
	 */
	void updateTask(Task task, MemberDbo member, int finishNum);

	/**
	 * 完成任务
	 */
	void finishTask(Task task);
}
