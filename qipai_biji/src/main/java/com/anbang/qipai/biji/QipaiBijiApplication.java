package com.anbang.qipai.biji;

import com.anbang.qipai.biji.cqrs.c.service.disruptor.ProcessCoreCommandEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.anbang.qipai.biji.cqrs.c.repository.SingletonEntityFactoryImpl;
import com.anbang.qipai.biji.init.InitProcessor;
import com.dml.users.UserSessionsManager;
import com.highto.framework.ddd.SingletonEntityRepository;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableEurekaClient
public class QipaiBijiApplication {

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
		SpringApplication.run(QipaiBijiApplication.class, args);
	}
}
