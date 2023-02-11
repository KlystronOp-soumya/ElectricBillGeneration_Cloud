package com.demo.taskdemo.entities;

import java.math.BigDecimal;

public class ProcessedBillAmount {

	private String consumerId;
	private String meterId;
	private String meterNo;

	private int slabStart;
	private int slabEnd;
	private BigDecimal slabRate;
	private BigDecimal amountPerSlab;

	private BigDecimal totalAmount;

	private String billSequenceNumber;

	private int calDay;
	private int calMonth;
	private int calYear;

}
