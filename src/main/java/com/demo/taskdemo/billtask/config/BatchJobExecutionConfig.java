package com.demo.taskdemo.billtask.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.cloud.task.repository.TaskRepository;
import org.springframework.cloud.task.repository.support.SimpleTaskExplorer;
import org.springframework.cloud.task.repository.support.SimpleTaskRepository;
import org.springframework.cloud.task.repository.support.TaskExecutionDaoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.transaction.PlatformTransactionManager;

import com.demo.taskdemo.billtask.utils.DataLoadTaskConfigurer;

@Configuration(value = "batchJobExecutionConfiguration", proxyBeanMethods = false)
@PropertySource(value = "classpath:db-config.properties")
public class BatchJobExecutionConfig {

	@Autowired
	private Environment env; // this will be removed -> setter

	@Bean("dataSource")
	@Primary
	public DataSource dataSource() {
		// TODO Auto-generated method stub

		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("mysql.driver"));
		dataSource.setUrl(env.getProperty("mysql.url"));
		dataSource.setSchema("CYOLASBCOM");
		dataSource.setUsername(env.getProperty("mysql.user"));
		dataSource.setPassword(env.getProperty("mysql.password"));
		return dataSource;

	}

	@Bean("jobRepository")
	@Primary
	public JobRepository jobRepository() throws Exception {

		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDatabaseType("MYSQL");
		jobRepositoryFactoryBean.setDataSource(dataSource());
		jobRepositoryFactoryBean.setTransactionManager(transactionManager());
		jobRepositoryFactoryBean.afterPropertiesSet();
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean("jobLauncher")
	@Primary
	public JobLauncher jobLauncher() throws Exception {
		final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor("BillDataLoadThread"));

		return jobLauncher;
	}

	@Bean("jobRegistry")
	@Primary
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}

	@Bean("jobRegistryBeanPostProcessor")
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		final JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry());
		return jobRegistryBeanPostProcessor;
	}

	@Bean(name = "transactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() { // TODO Auto-generated method
																// stub final
		JdbcTransactionManager transactionManager = new JdbcTransactionManager(dataSource());
		transactionManager.setDatabaseProductName("MySQL");
		transactionManager.afterPropertiesSet();
		return transactionManager;
	}

	@Bean("jobOperator")
	@Primary
	public JobOperator jobOperator(final JobExplorer jobExplorer, final JobRepository jobRepository,
			final JobRegistry jobRegistry) throws Exception {
		final SimpleJobOperator jobOperator = new SimpleJobOperator();

		jobOperator.setJobExplorer(jobExplorer);
		jobOperator.setJobRepository(jobRepository);
		jobOperator.setJobRegistry(jobRegistry);
		jobOperator.setJobLauncher(jobLauncher());
		return jobOperator;

	}

	@Bean
	public SQLErrorCodeSQLExceptionTranslator sqlErrorCodeSQLExceptionTranslator() {
		final SQLErrorCodeSQLExceptionTranslator exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator();

		exceptionTranslator.setDatabaseProductName("MySQL");
		exceptionTranslator.setDataSource(dataSource());
		return exceptionTranslator;
	}

	@Bean
	public TaskExplorer taskExplorer() {
		return new SimpleTaskExplorer(new TaskExecutionDaoFactoryBean(dataSource()));
	}

	@Bean
	public TaskRepository taskRepository() {
		return new SimpleTaskRepository(new TaskExecutionDaoFactoryBean(dataSource()));
	}

	@Bean("customTaskConfigurer")
	public TaskConfigurer taskConfigurer() {
		DataLoadTaskConfigurer customTask = new DataLoadTaskConfigurer(dataSource(), transactionManager(),
				new TaskExecutionDaoFactoryBean(dataSource()));
		return customTask;
	}

	/*
	 * @Bean("taskBatchDao") public TaskBatchDao taskBatchDao() { return new
	 * JdbcTaskBatchDao(dataSource()); }
	 */
}
