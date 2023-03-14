package com.demo.taskdemo.entities.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitConsumptionDTO {

	private String consumerId;// pk

	private String meterId;

	private String meterNo;// pk

	private int billingMonth; // pk

	private int billingYear;// pk

	private int unitConsumed;

	private String tariffType;

	private String tariffId;

	private String tariffStartDate;

	private Date billingInsertionDate;
}
