package com.solactive.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@SpringBootApplication(scanBasePackages = {"com.solactive.statistics"})
@ComponentScan({ "com.solactive.statistics"})
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
public class StatisticsApplication  {
	public static void main(String[] args) {
		SpringApplication.run(StatisticsApplication.class, args);
	}
	@Bean
	public Executor asyncExecutor() {
		return new ThreadPoolTaskExecutor();
	}
}
