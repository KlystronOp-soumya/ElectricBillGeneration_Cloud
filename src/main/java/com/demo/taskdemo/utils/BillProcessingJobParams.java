package com.demo.taskdemo.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

public class BillProcessingJobParams {

	private static Logger LOGGER = LogManager.getLogger(BillProcessingJobParams.class);
	private JobParameters jobParameters;
	private Map<String, JobParameter> jobParamMap;
	private Long currDay, currMon, currYear;

	public BillProcessingJobParams() {
		// TODO Auto-generated constructor stub
		this.jobParamMap = new HashMap<>();
	}

	public JobParameters getBillProcessingJobParameters() {

		populateJobParamMap();
		this.jobParameters = new JobParameters(jobParamMap);
		return this.jobParameters;
	}

	@SuppressWarnings("deprecation")
	private void populateJobParamMap() { // can be used through functional interface
		LOGGER.info("populating the Job Parameter Map");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
		java.util.Date date = calendar.getTime();
		this.currDay = Integer.toUnsignedLong(date.getDate());
		this.currMon = Integer.toUnsignedLong(date.getMonth() + 1);
		this.currYear = Integer.toUnsignedLong(date.getYear() + 1900);

		LOGGER.info("Current Job Parameters: " + currDay + "," + currMon + "," + currYear);
		this.jobParamMap.put("currDay", new JobParameter(currDay));
		this.jobParamMap.put("currYear", new JobParameter(currYear));
		this.jobParamMap.put("currMon", new JobParameter(currMon));
		this.jobParamMap.put("time", new JobParameter(calendar.getTime()));// to be omitted later
	}

	public int getCurrDay() {
		return currDay.intValue();
	}

	public int getCurrMon() {
		return currMon.intValue();
	}

	public int getCurrYear() {
		return currYear.intValue();
	}
}
