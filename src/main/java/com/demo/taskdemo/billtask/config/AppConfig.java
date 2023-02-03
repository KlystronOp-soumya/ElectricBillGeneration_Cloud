package com.demo.taskdemo.billtask.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableTask
@EnableBatchProcessing
@Import(value = { BatchJobConfig.class, BatchJobExecutionConfig.class })
@ComponentScan(basePackages = "com.demo.taskdemo.billtask")
public class AppConfig {

}
