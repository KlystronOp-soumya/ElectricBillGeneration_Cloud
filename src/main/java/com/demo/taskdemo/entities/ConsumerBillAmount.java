package com.demo.taskdemo.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * A consumer can have only a single entry for current month and year
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CONSUMER_BILL_AMOUNT", uniqueConstraints = @UniqueConstraint(columnNames = { "CONSUMER_ID", "METER_ID",
		"METER_NO", "BILLING_MNTH", "BILLING_YR" }))
public class ConsumerBillAmount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "CONSUMER_ID")
	private String consumerId;
	@Column(name = "METER_ID")
	private String meterId;
	@Column(name = "METER_NO")
	private String meterNo;

	@Column(name = "BILL_AMOUNT")
	private BigDecimal totalBillAmount;
	@Column(name = "UNIT_CONSUMED")
	private BigDecimal totalUnitConsumed;

	@Column(name = "BILLING_DATE")
	private String billingDate;

	@Column(name = "BILLING_MNTH")
	private int billGeneratingMnth;

	@Column(name = "BILLING_YR")
	private int billGeneratingYr;

	// to join this table on fist three attributes
	private BillingHistory billingHistory;

}
