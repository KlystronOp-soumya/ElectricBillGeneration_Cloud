package com.demo.taskdemo.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.demo.taskdemo.entities.ConstantCharges;
import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.ProcessedBilling;
import com.demo.taskdemo.entities.ProcessedSlabDetails;
import com.demo.taskdemo.entities.DTO.TariffDTO;
import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.repo.intf.CreateMapKey;
import com.demo.taskdemo.utils.BillProcessingJobParams;

public class BillAmountCalcStartegy {

	private static final Logger LOGGER = LogManager.getLogger(BillAmountCalcStartegy.class);

	private BillProcessingJobParams billProcessingJobParams;

	public BillAmountCalcStartegy(BillProcessingJobParams jobParams) {
		// TODO Auto-generated constructor stub
		this.billProcessingJobParams = jobParams;
	}

	public ProcessedBillDetails calculateFinalBillAmount(final UnitConsumptionDTO unitConsumptionDTO,
			final Map<String, List<TariffDTO>> tariffSlabMap, final ConstantCharges constantCharge,
			ProcessedBillDetails processedBillDetailsRef) throws ParseException {
		LOGGER.info("Calculate Final Bill Amount");

		BigDecimal totalEnergyCharges = BigDecimal.ZERO, appliedVarChrg = BigDecimal.ZERO,
				fixedRate = constantCharge.getFixedCharge(), meterCharge = constantCharge.getMeterCharge(),
				finalAmount = BigDecimal.ZERO;
		BigDecimal dutyRate = constantCharge.getDutyRate(), dutyCharge;
		int totalUnitConsumed = unitConsumptionDTO.getUnitConsumed();

		List<ProcessedSlabDetails> currentEntrySlabDtlLst = new ArrayList<>();
		ProcessedBilling currentEntryBilling = new ProcessedBilling();
		// generate key
		CreateMapKey generateKey = () -> {
			return unitConsumptionDTO.getTariffId().concat("|").concat(unitConsumptionDTO.getTariffType()).concat("|")
					.concat(unitConsumptionDTO.getTariffStartDate());
		};

		// get the list
		List<TariffDTO> tariffSlabRecords = tariffSlabMap.get(generateKey.get());

		if (!tariffSlabRecords.isEmpty()) {
			LOGGER.info("Slab records found for : " + generateKey.get());

			appliedVarChrg = new BigDecimal(totalUnitConsumed).multiply(constantCharge.getMVCA_rate());

			for (TariffDTO eachSlabRow : tariffSlabRecords) {
				if (totalUnitConsumed >= eachSlabRow.getSlabCount()) {

					// slabAmount = slab_count*slab_rate
					BigDecimal slabAmount = new BigDecimal(eachSlabRow.getSlabCount())
							.multiply(eachSlabRow.getSlabRate());
					totalEnergyCharges = totalEnergyCharges.add(slabAmount);
					totalUnitConsumed -= eachSlabRow.getSlabCount();
					currentEntrySlabDtlLst.add(new ProcessedSlabDetails(eachSlabRow.getSlabStart(),
							eachSlabRow.getSlabEnd(), eachSlabRow.getSlabRate(), slabAmount));
				}
			}

			// sum up everything as final amount
			dutyCharge = totalEnergyCharges.multiply(dutyRate).divide(BigDecimal.valueOf(100L)).setScale(3,
					BigDecimal.ROUND_HALF_UP);
			finalAmount = totalEnergyCharges.add(fixedRate).add(appliedVarChrg).add(dutyCharge).add(meterCharge);
			finalAmount = finalAmount.setScale(3, BigDecimal.ROUND_HALF_UP);

			currentEntryBilling.setDutyCharge(dutyCharge);
			currentEntryBilling.setFixedCharge(fixedRate);
			currentEntryBilling.setVarCharge(appliedVarChrg);
			currentEntryBilling.setMeterCharge(meterCharge);
			currentEntryBilling.setTotalAmount(finalAmount);
			currentEntryBilling.setBillSequenceNumber(getBillSequenceNum(unitConsumptionDTO.getConsumerId()));
			currentEntryBilling.setCalDay(billProcessingJobParams.getCurrDay());
			currentEntryBilling.setCalMonth(billProcessingJobParams.getCurrMon());
			currentEntryBilling.setCalYear(billProcessingJobParams.getCurrYear());
			currentEntryBilling.setJournalDate(getJournalDate());

		} else {
			throw new NullPointerException("Slab rate can not be null");
		}
		processedBillDetailsRef.setProcessedSlabDetailsList(currentEntrySlabDtlLst);
		processedBillDetailsRef.setProcessedBilling(currentEntryBilling);
		LOGGER.info("Generated amount for: consumer->" + unitConsumptionDTO.getConsumerId() + " total amount: "
				+ finalAmount);
		return processedBillDetailsRef;
	}

	protected String getBillSequenceNum(final String consumerId) {// could be an fucntional interface

		String seqNum = consumerId.concat(String.valueOf(billProcessingJobParams.getCurrYear()))
				.concat(String.valueOf(billProcessingJobParams.getCurrMon()))
				.concat(String.valueOf(billProcessingJobParams.getCurrDay()));
		LOGGER.info("Generated sequence number: " + seqNum);
		return seqNum;

	}

	protected Date getJournalDate() throws ParseException { // could be an functional interface
		SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d");
		String date = sdf.format(Calendar.getInstance().getTime());
		return sdf.parse(date);
	}

}
