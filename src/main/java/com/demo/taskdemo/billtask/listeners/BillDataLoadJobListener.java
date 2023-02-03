package com.demo.taskdemo.billtask.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("billDataLoadJobListener")
@Primary
public class BillDataLoadJobListener implements JobExecutionListener {

	private static final Logger LOGGER = LogManager.getLogger(BillDataLoadJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("The job was created: " + jobExecution.getCreateTime() + " with Current Status: "
				+ jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("The job was end at: " + jobExecution.getEndTime() + " with current status: "
				+ jobExecution.getStatus());

	}

}
