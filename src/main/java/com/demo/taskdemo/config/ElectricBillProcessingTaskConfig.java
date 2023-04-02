package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.cloud.task.repository.support.TaskExecutionDaoFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

//@Component("electricBillProcessingTaskConfigurer")
public class ElectricBillProcessingTaskConfig extends DefaultTaskConfigurer {

	private DataSource dataSource;

	private PlatformTransactionManager platformTransactionManager;

	private TaskRepository taskRepository;
	private TaskExplorer taskExplorer;
	// private TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean;

	public ElectricBillProcessingTaskConfig() {
		super();
	}

	public ElectricBillProcessingTaskConfig(final DataSource dataSource,
			final PlatformTransactionManager platformTransactionManager,
			final TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean, final TaskRepository taskRepository,
			final TaskExplorer taskExplorer) {
		super();
		this.dataSource = dataSource;
		this.platformTransactionManager = platformTransactionManager;
		this.taskExplorer = taskExplorer;
		this.taskRepository = taskRepository;
	}

	/*
	 * public ElectricBillProcessingTaskConfig(DataSource dataSource,
	 * PlatformTransactionManager transactionManager, TaskExecutionDaoFactoryBean
	 * taskExecutionDaoFactoryBean) { // TODO Auto-generated constructor stub
	 * this.dataSource = dataSource; this.platformTransactionManager =
	 * transactionManager; this.taskExecutionDaoFactoryBean =
	 * taskExecutionDaoFactoryBean; }
	 */

	@Override
	public PlatformTransactionManager getTransactionManager() {
		// TODO Auto-generated method stub
		return this.platformTransactionManager;
	}

	@Override
	public TaskRepository getTaskRepository() {
		// TODO Auto-generated method stub
		return this.taskRepository;
	}

	@Override
	public TaskExplorer getTaskExplorer() {
		// TODO Auto-generated method stub
		return this.taskExplorer;
	}

	@Override
	public DataSource getTaskDataSource() {
		// TODO Auto-generated method stub
		return this.dataSource;
	}

}
