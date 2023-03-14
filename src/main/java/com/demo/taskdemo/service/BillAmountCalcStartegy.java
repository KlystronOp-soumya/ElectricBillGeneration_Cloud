package com.demo.taskdemo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

public class BillAmountCalcStartegy {

	private static final Logger LOGGER = LogManager.getLogger(BillAmountCalcStartegy.class);

	public ProcessedBillDetails calculateFinalBillAmount(final UnitConsumptionDTO unitConsumptionDTO,
			final Map<String, List<TariffDTO>> tariffSlabMap, final ConstantCharges constantCharge,
			ProcessedBillDetails processedBillDetailsRef) {

		BigDecimal totalEnergyCharges = BigDecimal.ZERO, appliedVarChrg = BigDecimal.ZERO,
				fixedRate = constantCharge.getFixedCharge(), meterCharge = constantCharge.getMeterCharge(),
				finalAmount = BigDecimal.ZERO;
		BigDecimal dutyRate = constantCharge.getDutyRate(), dutyCharge;
		int totalUnitConsumed = unitConsumptionDTO.getUnitConsumed();

		List<ProcessedSlabDetails> currentEntrySlabDtlLst = new ArrayList<>();
		ProcessedBilling currentEntryBilling = new ProcessedBilling();
		// generate key
		CreateMapKey generateKey = () -> {
			return unitConsumptionDTO.getTariffId().concat("|").concat(unitConsumptionDTO.getTariffType())
					.concat(unitConsumptionDTO.getTariffStartDate());
		};

		// get the list
		List<TariffDTO> tariffSlabRecords = tariffSlabMap.get(generateKey.get());

		if (!tariffSlabRecords.isEmpty()) {
			LOGGER.info("Slab records found for : " + generateKey.get());

			appliedVarChrg = new BigDecimal(totalUnitConsumed).multiply(constantCharge.getMVCA_rate());

			for (TariffDTO eachSlabRow : tariffSlabRecords) {
				if (totalUnitConsumed >= eachSlabRow.getSlabStart() && totalUnitConsumed <= eachSlabRow.getSlabEnd()) {

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
			dutyCharge = totalEnergyCharges.multiply(dutyRate);
			finalAmount = totalEnergyCharges.add(fixedRate).add(appliedVarChrg).add(dutyCharge).add(meterCharge);
			finalAmount = finalAmount.setScale(3, BigDecimal.ROUND_HALF_UP);

			currentEntryBilling.setTotalAmount(finalAmount);

		} else {
			throw new NullPointerException("Slab rate can not be null");
		}
		processedBillDetailsRef.setProcessedSlabDetailsList(currentEntrySlabDtlLst);
		processedBillDetailsRef.setProcessedBilling(currentEntryBilling);
		return processedBillDetailsRef;
	}

	public ProcessedBillDetails calculateFinalBillAmount(UnitConsumptionDTO tempUnitConsumptionDTO,
			Map<String, List<TariffDTO>> tariffSlabMap, ProcessedBillDetails processedBillDtl) {
		// TODO Auto-generated method stub
		return null;
	}
}
