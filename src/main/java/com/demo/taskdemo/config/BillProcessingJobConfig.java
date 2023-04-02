package com.demo.taskdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.taskdemo.batch.readers.UnitConsumptionReader;
import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.exceptions.BillProcessException;

@Configuration(proxyBeanMethods = true)
public class BillProcessingJobConfig {

	private StepBuilderFactory stepBuilderFactory;
	private JobBuilderFactory jobBuilderFactory;
	private UnitConsumptionReader unitConsumptionReader;
	private ItemWriter<ProcessedBillDetails> processedBillAmountItemWriter;
	private ItemProcessor<UnitConsumptionDTO, ProcessedBillDetails> billAmountProcessor;

	private StepExecutionListener billProcesStepExecutionListener;
	private JobExecutionListener billProcessJobExecutionListener;

	public BillProcessingJobConfig(final StepBuilderFactory stepBuilderFactory,
			final JobBuilderFactory jobBuilderFactory, final UnitConsumptionReader unitConsumptionReader,
			final ItemWriter<ProcessedBillDetails> processedBillAmountItemWriter,
			final ItemProcessor<UnitConsumptionDTO, ProcessedBillDetails> billAmountProcessor,
			final StepExecutionListener billProcesStepExecutionListener,
			final JobExecutionListener billProcessJobExecutionListener) {
		this.stepBuilderFactory = stepBuilderFactory;
		this.jobBuilderFactory = jobBuilderFactory;
		this.unitConsumptionReader = unitConsumptionReader;
		this.processedBillAmountItemWriter = processedBillAmountItemWriter;
		this.billAmountProcessor = billAmountProcessor;
		this.billProcesStepExecutionListener = billProcesStepExecutionListener;
		this.billProcessJobExecutionListener = billProcessJobExecutionListener;
	}

	/*
	 * @Bean("helloWorldStep") public Step helloWorldStep() { return
	 * this.stepBuilderFactory.get("Step_HelloWorld").tasklet(new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception { // TODO Auto-generated method
	 * stub System.out.println("Step executed:: Hello World"); return
	 * RepeatStatus.FINISHED; } }).build(); }
	 */
	/*
	 * @Bean public Job billPorcessingJob() { return
	 * this.jobBuilderFactory.get("Job_ElectricBillProcessing").start(helloWorldStep
	 * ()).build(); }
	 */

	@Bean(value = "loadUnitConsumptionDataStep")
	public Step loadUnitConsumptionDataStep() throws BillProcessException {
		return this.stepBuilderFactory.get("loadConsumptionAndGenerateBill")
				.<UnitConsumptionDTO, ProcessedBillDetails>chunk(10).reader(this.unitConsumptionReader.getReader())
				.processor(this.billAmountProcessor).writer(this.processedBillAmountItemWriter)
				.listener(this.billProcesStepExecutionListener).build();
	}

	@Bean(value = "billDataPorcessJob")
	public Job billDataPorcessJob() throws BillProcessException {
		return this.jobBuilderFactory.get("billDataProcessJob").start(loadUnitConsumptionDataStep())
				.listener(this.billProcessJobExecutionListener).build();
	}

}
