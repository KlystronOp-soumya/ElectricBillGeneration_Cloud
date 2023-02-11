package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.configuration.SimpleTaskAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.demo.taskdemo.repo.CustomBillProcessingRepoImpl;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.demo.taskdemo")
@EnableTask
@EnableBatchProcessing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.demo.taskdemo.repo")
@Import(value = { BatchExecutionConfig.class, BillProcessingJobConfig.class, ElectricBillProcessingTaskConfig.class })
@EnableAutoConfiguration(exclude = { SimpleTaskAutoConfiguration.class })
public class AppConfig {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public AppConfig(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Bean("cusotmBillProcessingRepo")
	public CustomBillProcessingRepo billProcessingRepo(final JdbcTemplate jdbcTemplate) {
		return new CustomBillProcessingRepoImpl(this.jdbcTemplate);
	}

}
