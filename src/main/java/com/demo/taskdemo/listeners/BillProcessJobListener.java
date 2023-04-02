package com.demo.taskdemo.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component(value = "billProcessingJobExecListerner")
@Primary
public class BillProcessJobListener implements JobExecutionListener {

	private static final Logger LOGGER = LogManager.getLogger(BillProcessJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("The job was created: " + jobExecution.getCreateTime() + " with Current Status: "
				+ jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

		if (jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED) == 0) {
			System.out
					.println("The job : " + jobExecution.getJobId() + "\nwas completed@: " + jobExecution.getEndTime());
			System.out.println("Completed steps:");
			jobExecution.getStepExecutions().stream()
					.forEach((eachStep) -> System.out.println(eachStep.getStepName() + ":" + eachStep.getStatus()));
		} else if (jobExecution.getExitStatus().compareTo(ExitStatus.STOPPED) == 0) {
			System.out
					.println("The job : " + jobExecution.getJobId() + "\nwas completed@: " + jobExecution.getEndTime());
			System.out.println("Completed steps:");
			jobExecution.getStepExecutions().stream()
					.forEach((eachStep) -> System.out.println(eachStep.getStepName() + ":" + eachStep.getStatus()));
			jobExecution.getAllFailureExceptions().forEach((eachExceps) -> System.out.println(eachExceps.getCause()));

		} else if (jobExecution.getExitStatus().compareTo(ExitStatus.FAILED) == 0) {
			System.err.println("Job Failed");
			jobExecution.getStepExecutions().stream()
					.forEach((eachStep) -> System.out.println(eachStep.getStepName() + ":" + eachStep.getExitStatus()));
			jobExecution.getAllFailureExceptions().forEach((eachExceps) -> System.out.println(eachExceps.getCause()));
			jobExecution.setStatus(BatchStatus.FAILED);
		}

	}

}
