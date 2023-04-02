package com.demo.taskdemo.batch.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.exceptions.BillProcessException;
import com.demo.taskdemo.service.BillAmountProcessService;

public class BillAmountProcessor implements ItemProcessor<UnitConsumptionDTO, ProcessedBillDetails> {

	private static final Logger LOGGER = LogManager.getLogger(BillAmountProcessor.class);
	// service class
	private BillAmountProcessService billAmountProcessService;

	public BillAmountProcessor(final BillAmountProcessService billAmountProcessService) {
		// TODO Auto-generated constructor stub
		this.billAmountProcessService = billAmountProcessService;
	}

	@Override
	public ProcessedBillDetails process(UnitConsumptionDTO item) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Processor-an entry with Consumer ID: " + item.getConsumerId());
		// declare DTO object

		// declare processed bill amount object
		ProcessedBillDetails processedBillDetails = new ProcessedBillDetails();
		try {
			processedBillDetails.setUnitConsumptionDTO(item);

			// pass the reference to service class
			processedBillDetails = this.billAmountProcessService.doBillAmountGeneration(processedBillDetails); // returns
																												// the
																												// reference
		} catch (Exception e) {
			// TODO: handle exception
			throw new BillProcessException(e.getCause(), e.getMessage());
		}

		return processedBillDetails;

	}

}
