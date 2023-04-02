package com.demo.taskdemo.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import com.demo.taskdemo.exceptions.BillProcessException;

@Component("billProcessStepListener")
public class BillProcessStepListener implements StepExecutionListener {

	private static final Logger LOGGER = LogManager.getLogger(BillProcessStepListener.class);

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
			return ExitStatus.FAILED;
		}
		if (stepExecution.getRollbackCount() > 0) {
			stepExecution.addFailureException(
					new BillProcessException(new RuntimeException(), "Step was failed to execute"));
			return ExitStatus.FAILED;
		}

		return ExitStatus.COMPLETED;
	}

}
