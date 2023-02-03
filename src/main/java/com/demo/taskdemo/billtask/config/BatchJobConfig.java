package com.demo.taskdemo.billtask.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.demo.taskdemo.billtask.entites.UnitConsumption;
import com.demo.taskdemo.billtask.utils.BillDataCSVReader;
import com.demo.taskdemo.billtask.utils.BillDataDBWriter;
import com.demo.taskdemo.billtask.utils.BillDataLoadJobParams;
import com.demo.taskdemo.billtask.utils.DataLoadProcessCheckTasklet;

@Configuration(value = "batchJobConfiguration")
public class BatchJobConfig {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private JobExecutionListener jobListener;

	@Autowired
	private StepExecutionListener stepListener;

	private DataSource dataSource;
	private BillDataLoadJobParams billDataLoadJobParams;

	public BatchJobConfig(final DataSource dataSource, final BillDataLoadJobParams billDataLoadJobParams) {

		this.dataSource = dataSource;
		this.billDataLoadJobParams = billDataLoadJobParams;
	}

	@Bean("billDataCSVReader")
	@Scope("singleton")
	public BillDataCSVReader billDataCSVReader() {
		return new BillDataCSVReader();
	}

	@Bean("billDataDBWriter")
	@Scope("singleton")
	public BillDataDBWriter billDataDBWriter() {
		return new BillDataDBWriter(this.billDataLoadJobParams, this.dataSource);
	}

	@Bean("checkStep")
	public Step checkStep() {
		return this.stepBuilderFactory.get("checkBatch_Step").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Tasklet executed");
				return RepeatStatus.FINISHED;
			}
		}).listener(stepListener).build();
	}

	@Bean("readBillCSVData")
	public Step readBillCSVDataStep() {
		return this.stepBuilderFactory.get("Step_readBillCSVData").<UnitConsumption, UnitConsumption>chunk(10)
				.reader(billDataCSVReader().itemReader()).writer(billDataDBWriter().itemWriter()).listener(stepListener)
				.build();

	}

	@Bean("checkConditionsBeforeDataLoad")
	public Step checkConditionsBeforeDataLoadStep() {
		return this.stepBuilderFactory.get("Step_checkConditionsBeforeDataLoad").tasklet(dataLoadPrcsChkTasklet())
				.listener(stepListener).build();

	}

	@Bean("dataLoadProcessCheck")
	public Tasklet dataLoadPrcsChkTasklet() {
		return new DataLoadProcessCheckTasklet();
	}

	/*
	 * @Bean(value = "handleFailedExec") public Step handleFailedExecStep() { return
	 * this.stepBuilderFactory.get("Step_handleFailedExec").tasklet(new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception { // TODO Auto-generated method
	 * stub contribution.setExitStatus(ExitStatus.FAILED); throw new
	 * BillLoadingException(new RuntimeException(),
	 * "The step execution was failed. Please look logs"); } }).build(); }
	 */

	@Bean("billDataLoadJob")
	public Job billDataLoadJob() {
		return this.jobBuilderFactory.get("billDataLoad_Job").listener(jobListener)
				.flow(checkConditionsBeforeDataLoadStep()).next(readBillCSVDataStep()).end().build();
	}

}
