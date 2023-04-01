package com.demo.taskdemo.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sequenceNum;

	private String consumerId;
	private String meterId;
	private String meterNo;
	private String tariffId;
	private int tariffType;
	private int slabStart;
	private int slabEnd;
	private BigDecimal slabRate;
	private BigDecimal slabAmount;

	private int calDay;
	private int calMonth;
	private int calYear;

	private String journalDate;

}
