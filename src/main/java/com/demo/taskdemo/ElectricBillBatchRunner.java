package com.demo.taskdemo;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("billProcessingTaskBatchRunner")
public class ElectricBillBatchRunner implements CommandLineRunner {

	private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ElectricBillBatchRunner.class);

	private ApplicationContextProvider contextProvider;
	private Job job;
	private JobLauncher jobLauncher;
	private JobOperator jobOperator;

	public ElectricBillBatchRunner(ApplicationContextProvider contextProvider, Job job, JobLauncher jobLauncher,
			JobOperator jobOperator) {
		super();
		this.contextProvider = contextProvider;
		this.job = job;
		this.jobLauncher = jobLauncher;
		this.jobOperator = jobOperator;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Execting the job");
		try {
			Map<String, JobParameter> jobParaMap = new HashMap<>();
			jobParaMap.put("executionTime", new JobParameter(System.currentTimeMillis()));
			LOGGER.info("Test execution");
			JobExecution jobExecution = this.jobLauncher.run(job, new JobParameters(jobParaMap));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/*
	 * protected void populateDatabase() throws NoSuchBeanException { if
	 * (applicationContextProvider.getApplicationContext().containsBean(
	 * "initDBBean") &&
	 * applicationContextProvider.getApplicationContext().containsBean("dataSource")
	 * ) {
	 * 
	 * DatabasePopulator databasePopulator =
	 * applicationContextProvider.getApplicationContext() .getBean("initDBBean",
	 * ResourceDatabasePopulator.class); DataSource dataSource =
	 * applicationContextProvider.getApplicationContext().getBean("dataSource",
	 * HikariDataSource.class);
	 * 
	 * try { DatabasePopulatorUtils.execute(databasePopulator, dataSource); } catch
	 * (ScriptException e) { // TODO: handle exception throw new
	 * RuntimeException("Issue with the script execution"); }
	 * 
	 * LOGGER.info("Beans found, populatinf database"); } else { throw new
	 * NoSuchBeanException("Check For the Bean alias name", new RuntimeException());
	 * }
	 * 
	 * }
	 */

}
