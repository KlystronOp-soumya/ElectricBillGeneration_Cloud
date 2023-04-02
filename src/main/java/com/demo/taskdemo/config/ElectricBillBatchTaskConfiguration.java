package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.cloud.task.repository.TaskNameResolver;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.cloud.task.repository.support.SimpleTaskExplorer;
import org.springframework.cloud.task.repository.support.SimpleTaskNameResolver;
import org.springframework.cloud.task.repository.support.SimpleTaskRepository;
import org.springframework.cloud.task.repository.support.TaskExecutionDaoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration("electricBillBatchTaskConfiguration")
public class ElectricBillBatchTaskConfiguration {

	private PlatformTransactionManager transactionManager;
	private DataSource dataSource;

	public ElectricBillBatchTaskConfiguration(PlatformTransactionManager transactionManager, DataSource dataSource) {
		super();
		this.transactionManager = transactionManager;
		this.dataSource = dataSource;
	}

	@Bean(name = "springCloudTaskTransactionManager")
	public PlatformTransactionManager springCloudTaskTransactionManager() {

		return this.transactionManager;

	}

	@Bean("taskNameResolver")
	public TaskNameResolver taskNameResolver() {
		SimpleTaskNameResolver taskNameResolver = new SimpleTaskNameResolver();
		return taskNameResolver;
	}

	@Bean("taskRepository")
	public TaskRepository taskRepository() {
		return new SimpleTaskRepository(taskExecutionDaoFactoryBean(this.dataSource));
	}

	@Bean("taskExecutionFactory")
	public TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean(final DataSource dataSource) {
		return new TaskExecutionDaoFactoryBean(dataSource, "TASK_");
	}

	@Bean("taskExplorer")
	public TaskExplorer taskExplorer() {
		return new SimpleTaskExplorer(taskExecutionDaoFactoryBean(this.dataSource));
	}

}
