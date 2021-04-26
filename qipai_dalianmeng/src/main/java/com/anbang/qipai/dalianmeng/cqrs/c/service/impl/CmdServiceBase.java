package com.anbang.qipai.dalianmeng.cqrs.c.service.impl;

import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CmdServiceBase {

	@Autowired
	protected SingletonEntityRepository singletonEntityRepository;

}
