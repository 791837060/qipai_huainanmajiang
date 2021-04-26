package com.anbang.qipai.qinyouquan.cqrs.c.service.disruptor;

import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoreSnapshotFactory {

	@Autowired
	private SingletonEntityRepository singletonEntityRepository;

	public CoreSnapshot createSnapshoot() {

		CoreSnapshot memberSnapshot = new CoreSnapshot();
		memberSnapshot.setCreateTime(System.currentTimeMillis());
		memberSnapshot.getContentMap().put(SingletonEntityRepository.class, singletonEntityRepository);
		return memberSnapshot;
	}

}
