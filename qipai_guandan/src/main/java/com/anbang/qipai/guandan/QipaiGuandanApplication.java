package com.anbang.qipai.guandan;

import com.anbang.qipai.guandan.cqrs.c.repository.SingletonEntityFactoryImpl;
import com.anbang.qipai.guandan.cqrs.c.service.disruptor.ProcessCoreCommandEventHandler;
import com.anbang.qipai.guandan.init.InitProcessor;
import com.dml.users.UserSessionsManager;
import com.highto.framework.ddd.SingletonEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QipaiGuandanApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public UserSessionsManager userSessionsManager() {
        return new UserSessionsManager();
    }

    @Bean
    public ProcessCoreCommandEventHandler processCoreCommandEventHandler() {
        return new ProcessCoreCommandEventHandler();
    }

    @Bean
    public SingletonEntityRepository singletonEntityRepository() {
        SingletonEntityRepository singletonEntityRepository = new SingletonEntityRepository();
        singletonEntityRepository.setEntityFactory(new SingletonEntityFactoryImpl());
        return singletonEntityRepository;
    }

    @Bean
    public InitProcessor initProcessor() {
        InitProcessor initProcessor = new InitProcessor(singletonEntityRepository(), applicationContext);
        initProcessor.init();
        return initProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(QipaiGuandanApplication.class, args);
    }
}
