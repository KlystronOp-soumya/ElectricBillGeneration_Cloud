package com.demo.taskdemo.billtask.utils;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.demo.taskdemo.billtask.entites.BillDataLoadInfo;

public class DataLoadProcessCheckTasklet implements Tasklet {

	private static final Logger LOGGER = LogManager.getLogger(DataLoadProcessCheckTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		BillDataLoadInfo billDataLoadInfo = BillDataLoadTaskUtils.getBillDataLoadInfo();
		// This check has been commented out for testing purpose
		/*
		 * if (!BillDataLoadTaskUtils.isBillingQuarter(billDataLoadInfo.getCurrMonth()))
		 * { LOGGER.error("Not billing quarter");
		 * contribution.setExitStatus(ExitStatus.STOPPED); throw new
		 * BillLoadingException("Execution month is not a billing quarter"); }
		 */
		// Check for the resource existence
		if (!BillDataLoadTaskUtils.isCsvExists()) {
			LOGGER.error("Data CSV not present in the path");
			contribution.setExitStatus(ExitStatus.FAILED);
			throw new FileNotFoundException("Data CSV not present in the path");

		}

		return RepeatStatus.FINISHED;
	}

}
