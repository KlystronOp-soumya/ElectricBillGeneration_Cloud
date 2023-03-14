package com.demo.taskdemo.entities;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedBilling {
	private BigDecimal totalAmount;

	private String billSequenceNumber;

	private int calDay;
	private int calMonth;
	private int calYear;

	private Date journalDate;
}
