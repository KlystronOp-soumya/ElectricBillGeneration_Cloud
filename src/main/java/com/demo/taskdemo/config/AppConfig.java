package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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

import com.demo.taskdemo.batch.processors.BillAmountProcessor;
import com.demo.taskdemo.batch.readers.UnitConsumptionReader;
import com.demo.taskdemo.batch.writers.ProcessedBillWriter;
import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.listeners.BillProcessJobListener;
import com.demo.taskdemo.listeners.BillProcessStepListener;
import com.demo.taskdemo.repo.CustomBillProcessingRepoImpl;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;
import com.demo.taskdemo.service.BillAmountCalcStartegy;
import com.demo.taskdemo.service.BillAmountProcessService;
import com.demo.taskdemo.utils.BillProcessingJobParams;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.demo.taskdemo")
@EnableTask
@EnableBatchProcessing
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.demo.taskdemo.repo")
@Import(value = { BatchExecutionConfig.class, BillProcessingJobConfig.class, ElectricBillProcessingTaskConfig.class,
		AppRedisConfig.class })
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

	@Bean("billProcessingJobParams")
	public BillProcessingJobParams jobParams() {
		return new BillProcessingJobParams();
	}

	@Bean("unitConsumptionReader")
	public UnitConsumptionReader unitConsumptionReader() {

		return new UnitConsumptionReader(this.dataSource);
	}

	@Bean("processedBillAmountItemWriter")
	public ItemWriter<ProcessedBillDetails> processedBillAmountItemWriter() {
		return new ProcessedBillWriter();
	}

	@Bean("billAmountItemProcessor")
	public ItemProcessor<UnitConsumptionDTO, ProcessedBillDetails> billAmountItemProcessor() {
		return new BillAmountProcessor(billAmountProcessService());
	}

	@Bean("billAmountProcessorService")
	public BillAmountProcessService billAmountProcessService() {
		return new BillAmountProcessService(customBillProcessingRepo(), billAmountCalcStrategy());
	}

	@Bean("customBillProcessingRepo")
	public CustomBillProcessingRepo customBillProcessingRepo() {
		return new CustomBillProcessingRepoImpl(this.jdbcTemplate);
	}

	@Bean("billAmountCalculationStrategy")
	public BillAmountCalcStartegy billAmountCalcStrategy() {
		return new BillAmountCalcStartegy();
	}

	@Bean("billDataProcessStepExecListener")
	public StepExecutionListener billDataProcesStepExecutionListener() {
		return new BillProcessStepListener();
	}

	@Bean("billDataProcessJobExecutionListener")
	public JobExecutionListener billDataProcessJobExecutionListener() {
		return new BillProcessJobListener();
	}

}
