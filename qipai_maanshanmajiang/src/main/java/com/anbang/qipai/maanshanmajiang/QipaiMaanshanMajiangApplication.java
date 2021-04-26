package com.anbang.qipai.maanshanmajiang;

import com.anbang.qipai.maanshanmajiang.cqrs.c.repository.SingletonEntityFactoryImpl;
import com.anbang.qipai.maanshanmajiang.cqrs.c.service.disruptor.ProcessCoreCommandEventHandler;
import com.anbang.qipai.maanshanmajiang.init.InitProcessor;
import org.eclipse.jetty.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dml.users.UserSessionsManager;
import com.highto.framework.ddd.SingletonEntityRepository;

@SpringBootApplication
@EnableScheduling
public class QipaiMaanshanMajiangApplication {

	@Autowired
	private ApplicationContext applicationContext;
    @Bean
    public HttpClient httpClient() {
        HttpClient client = new HttpClient();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
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
		SpringApplication.run(QipaiMaanshanMajiangApplication.class, args);
	}
}
