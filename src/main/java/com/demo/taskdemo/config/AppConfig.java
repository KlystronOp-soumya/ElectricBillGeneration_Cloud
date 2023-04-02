package com.demo.taskdemo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import com.demo.taskdemo.batch.processors.BillAmountProcessor;
import com.demo.taskdemo.batch.readers.UnitConsumptionReader;
import com.demo.taskdemo.batch.writers.ProcessedBillWriter;
import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.repo.CustomBillProcessingRepoImpl;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;
import com.demo.taskdemo.service.BillAmountCalcStartegy;
import com.demo.taskdemo.service.BillAmountProcessService;
import com.demo.taskdemo.utils.BillProcessingJobParams;

@Configuration(proxyBeanMethods = true)
@ComponentScan(basePackages = "com.demo.taskdemo")
@EnableTask
@EnableBatchProcessing
@Import(value = { BillProcessingJobConfig.class, BillProcessingBatchExecutionConfig.class })
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
	@Scope("prototype")
	public UnitConsumptionReader unitConsumptionReader() {

		return new UnitConsumptionReader(this.dataSource);
	}

	@Bean("processedBillAmountItemWriter")
	@Scope("prototype")
	public ItemWriter<ProcessedBillDetails> processedBillAmountItemWriter() {
		return new ProcessedBillWriter(this.jdbcTemplate);
	}

	@Bean("billAmountItemProcessor")
	@Scope("prototype")
	public ItemProcessor<UnitConsumptionDTO, ProcessedBillDetails> billAmountItemProcessor() {
		return new BillAmountProcessor(billAmountProcessService());
	}

	@Bean("billAmountProcessorService")
	@Scope("prototype")
	public BillAmountProcessService billAmountProcessService() {
		return new BillAmountProcessService(customBillProcessingRepo(), billAmountCalcStrategy());
	}

	@Bean("customBillProcessingRepo")
	@Scope("prototype")
	public CustomBillProcessingRepo customBillProcessingRepo() {
		return new CustomBillProcessingRepoImpl(this.jdbcTemplate);
	}

	@Bean("billAmountCalculationStrategy")
	public BillAmountCalcStartegy billAmountCalcStrategy() {
		return new BillAmountCalcStartegy(jobParams());
	}

}
