package com.anbang.qipai.admin.msg.msjobj.dbo;

public enum TaskState {
	CANNOTACCEPT, // 不可接
	CANACCEPT, // 可接
	DOINGTASK, // 进行中
	COMPLETETASK, // 完成，未领奖
	FINISHTASK// 完成，已领奖
}
