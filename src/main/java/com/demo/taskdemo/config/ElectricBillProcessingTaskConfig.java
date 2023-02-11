package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.cloud.task.repository.support.TaskExecutionDaoFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class ElectricBillProcessingTaskConfig implements TaskConfigurer {

	private DataSource dataSource;

	private PlatformTransactionManager platformTransactionManager;

	private TaskRepository taskRepository;
	private TaskExplorer taskExplorer;
	private TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean;

	public ElectricBillProcessingTaskConfig(final DataSource dataSource,
			final PlatformTransactionManager platformTransactionManager,
			final TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean, final TaskRepository taskRepository,
			final TaskExplorer taskExplorer) {
		super();
		this.dataSource = dataSource;
		this.platformTransactionManager = platformTransactionManager;
		this.taskExecutionDaoFactoryBean = taskExecutionDaoFactoryBean;
		this.taskExplorer = taskExplorer;
		this.taskRepository = taskRepository;
	}

	@Override
	public TaskRepository getTaskRepository() {
		// TODO Auto-generated method stub
		// this.taskRepository = new SimpleTaskRepository(taskExecutionDaoFactoryBean);
		return this.taskRepository;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		// TODO Auto-generated method stub
		return this.platformTransactionManager;
	}

	@Override
	public TaskExplorer getTaskExplorer() {
		// TODO Auto-generated method stub
		// this.taskExplorer = new SimpleTaskExplorer(taskExecutionDaoFactoryBean);
		return this.taskExplorer;
	}

	@Override
	public DataSource getTaskDataSource() {
		// TODO Auto-generated method stub
		return this.dataSource;
	}

}
