package com.demo.taskdemo.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.demo.taskdemo.entities.ConstantCharges;
import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.DTO.TariffDTO;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.exceptions.BillProcessException;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;

public class BillAmountProcessService {
	private static final Logger LOGGER = LogManager.getLogger(BillAmountProcessService.class);
	// DAO class
	private CustomBillProcessingRepo billProcessDao;

	// calculation strategy class
	private BillAmountCalcStartegy calcStartegy;

	public BillAmountProcessService(final CustomBillProcessingRepo billProcessDao,
			final BillAmountCalcStartegy calcStartegy) {
		// TODO Auto-generated constructor stub
		this.billProcessDao = billProcessDao;
		this.calcStartegy = calcStartegy;
	}
	// method to calculate the bill amounts

	public ProcessedBillDetails doBillAmountGeneration(ProcessedBillDetails processedBillDtl)
			throws BillProcessException {
		/*
		 * Fetch the slabs Fetch the constant charges pass the slab list , constant
		 * charge list and processed object to strategy class and calculate the bill
		 * there
		 * 
		 */
		LOGGER.info("Processing bill amount for " + processedBillDtl.getUnitConsumptionDTO().getConsumerId());
		// store the DTO reference
		UnitConsumptionDTO tempUnitConsumptionDTO = processedBillDtl.getUnitConsumptionDTO();
		String tariffType = tempUnitConsumptionDTO.getTariffType();
		try {
			// fetch the slab rate as hashmap
			Map<String, List<TariffDTO>> tariffSlabMap = this.billProcessDao.getLatestTariffSlabDetails(tariffType);
			// fetch the object for constant charge
			// this should be a unique object with latest value/date only
			ConstantCharges constantCharge = this.billProcessDao.getConstantChargeByTariffType(tariffType);

			// pass the references for processbill details , unitconsumption details
			processedBillDtl = this.calcStartegy.calculateFinalBillAmount(tempUnitConsumptionDTO, tariffSlabMap,
					constantCharge, processedBillDtl);

		} catch (Exception e) {
			// TODO: handle exception
			throw new BillProcessException(e.getCause(), e.getMessage());
		}
		LOGGER.info("Bill was processed for : " + processedBillDtl.getUnitConsumptionDTO().getConsumerId());
		return processedBillDtl;

	}

}
