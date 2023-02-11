package com.demo.taskdemo.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.explore.JobExplorer;

public class BillProcessJobListener implements JobExecutionListener {

	private JobExplorer jobExplorer;

	public BillProcessJobListener(JobExplorer jobExplorer) {
		super();
		this.jobExplorer = jobExplorer;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

}
