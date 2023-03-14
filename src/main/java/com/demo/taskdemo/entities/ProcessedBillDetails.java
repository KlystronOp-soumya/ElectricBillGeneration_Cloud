package com.demo.taskdemo.entities;

import java.util.List;

import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedBillDetails {

	private UnitConsumptionDTO unitConsumptionDTO;
	private List<ProcessedSlabDetails> processedSlabDetailsList; // this should be list of objects since each qualified
																	// row will
	// be recorded
	private ProcessedBilling processedBilling;

}
