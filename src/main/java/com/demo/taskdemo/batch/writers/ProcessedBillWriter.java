package com.demo.taskdemo.batch.writers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.demo.taskdemo.entities.ProcessedBillDetails;

public class ProcessedBillWriter implements ItemWriter<ProcessedBillDetails> {

	private static final Logger LOGGER = LogManager.getLogger(ProcessedBillWriter.class);

	@Override
	public void write(List<? extends ProcessedBillDetails> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writer");
	}

}
