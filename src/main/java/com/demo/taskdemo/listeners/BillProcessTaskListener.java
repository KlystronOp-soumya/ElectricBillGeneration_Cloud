package com.demo.taskdemo.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

@Component("billProcessTaskListener")
public class BillProcessTaskListener implements TaskExecutionListener {

	private static final Logger LOGGER = LogManager.getLogger(BillProcessTaskListener.class);

	@Override
	public void onTaskStartup(TaskExecution taskExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("Task: " + taskExecution.getTaskName() + " with ID: " + taskExecution.getExecutionId()
				+ " started: " + taskExecution.getStartTime());
	}

	@Override
	public void onTaskEnd(TaskExecution taskExecution) {
		// TODO Auto-generated method stub
		LOGGER.info("Task: " + taskExecution.getTaskName() + " with ID: " + taskExecution.getExecutionId()
				+ " finished: " + taskExecution.getEndTime() + "\nSuccessfully launched the Job ");
	}

	@Override
	public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
		// TODO Auto-generated method stub
		LOGGER.error(
				"The task was failed: " + taskExecution.getErrorMessage() + " with: " + taskExecution.getExitCode(),
				throwable.getMessage());

	}

}
