package com.demo.taskdemo.batch.processors;

import org.springframework.batch.item.ItemProcessor;

import com.demo.taskdemo.entities.ProcessedBillAmount;
import com.demo.taskdemo.entities.UnitConsumption;

public class BillAmountProcessor implements ItemProcessor<UnitConsumption, ProcessedBillAmount> {

	// service class

	public BillAmountProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessedBillAmount process(UnitConsumption item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
