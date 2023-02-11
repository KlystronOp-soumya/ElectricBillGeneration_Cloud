package com.demo.taskdemo.listeners;

import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;

public class BillProcessTaskListener implements TaskExecutionListener {

	@Override
	public void onTaskStartup(TaskExecution taskExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskEnd(TaskExecution taskExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
		// TODO Auto-generated method stub

	}

}
