package com.anbang.qipai.doudizhu.init;

import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.context.ApplicationContext;

public class InitProcessor {

    private SingletonEntityRepository singletonEntityRepository;

    private ApplicationContext applicationContext;

    public InitProcessor(SingletonEntityRepository singletonEntityRepository,
                         ApplicationContext applicationContext) {
        this.singletonEntityRepository = singletonEntityRepository;
        this.applicationContext = applicationContext;
    }

    public void init() {

    }
}
