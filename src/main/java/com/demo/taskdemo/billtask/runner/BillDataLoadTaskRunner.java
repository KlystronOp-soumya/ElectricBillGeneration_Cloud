package com.demo.taskdemo.billtask.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.taskdemo.billtask.exceptions.BillLoadingException;
import com.demo.taskdemo.billtask.utils.BillDataLoadJobParams;

@Component("billDataLoadTaskRunner")
public class BillDataLoadTaskRunner implements CommandLineRunner {

	private static final Logger LOGGER = LogManager.getLogger(BillDataLoadTaskRunner.class);
	private Job job;
	private JobLauncher jobLauncher;
	private BillDataLoadJobParams jobParams;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		try {

			JobExecution jobExecution = this.jobLauncher.run(job, this.jobParams.getBillDataLoadJobParameters());

		} catch (Exception e) {
			LOGGER.error("Could not launch the task", e);
			throw new BillLoadingException(e);
		}
	}

	@Autowired
	public void setJob(Job job) {
		this.job = job;
	}

	@Autowired
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@Autowired
	public void setJobParams(BillDataLoadJobParams jobParams) {
		this.jobParams = jobParams;
	}

}
