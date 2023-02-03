package com.demo.taskdemo.billtask.utils;

import javax.sql.DataSource;

import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.cloud.task.repository.support.SimpleTaskExplorer;
import org.springframework.cloud.task.repository.support.SimpleTaskRepository;
import org.springframework.cloud.task.repository.support.TaskExecutionDaoFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class DataLoadTaskConfigurer implements TaskConfigurer {

	private DataSource dataSource;

	private PlatformTransactionManager platformTransactionManager;

	private SimpleTaskRepository taskRepository;
	private SimpleTaskExplorer taskExplorer;
	TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean;

	public DataLoadTaskConfigurer() {
		// TODO Auto-generated constructor stub

	}

	public DataLoadTaskConfigurer(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
			TaskExecutionDaoFactoryBean taskExecutionDaoFactoryBean) {
		super();
		this.dataSource = dataSource;
		this.platformTransactionManager = platformTransactionManager;
		this.taskExecutionDaoFactoryBean = taskExecutionDaoFactoryBean;
	}

	@Override
	public TaskRepository getTaskRepository() {
		// TODO Auto-generated method stub
		taskRepository = new SimpleTaskRepository(taskExecutionDaoFactoryBean);
		taskRepository.createTaskExecution("BillDataLoadTask");
		return taskRepository;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		// TODO Auto-generated method stub
		return this.platformTransactionManager;
	}

	@Override
	public TaskExplorer getTaskExplorer() {
		// TODO Auto-generated method stub
		taskExplorer = new SimpleTaskExplorer(taskExecutionDaoFactoryBean);
		return taskExplorer;
	}

	@Override
	public DataSource getTaskDataSource() {
		// TODO Auto-generated method stub
		return this.dataSource;
	}

}
