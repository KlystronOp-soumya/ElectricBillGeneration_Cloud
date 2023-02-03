package com.demo.taskdemo.billtask.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component("billDataLoadStepListener")
public class BillLoadDataStepListener implements StepExecutionListener {

	private static final Logger LOGGER = LogManager.getLogger(BillLoadDataStepListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("Step to start: " + stepExecution.getStepName());

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		LOGGER.info(stepExecution.getSummary());
		if (!stepExecution.getFailureExceptions().isEmpty()) {
			stepExecution.getFailureExceptions().stream()
					.forEach((eachException) -> LOGGER.error(eachException.getMessage()));
			return ExitStatus.STOPPED;
		}

		return ExitStatus.COMPLETED;
	}

}
