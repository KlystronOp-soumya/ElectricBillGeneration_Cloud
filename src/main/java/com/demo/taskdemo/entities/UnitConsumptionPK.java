package com.demo.taskdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UnitConsumptionPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "CONSUMER_ID")
	private String consumerId;// pk
	@Column(name = "METER_ID")
	private String meterId;
	@Column(name = "METER_NO")
	private String meterNo;// pk
	@Column(name = "BILL_MONTH")
	private int billingMonth; // pk
	@Column(name = "BILL_YEAR")
	private int billingYear;// pk

	public UnitConsumptionPK(String consumerId, String meterId, String meterNo, int billingMonth, int billingYear) {
		super();
		this.consumerId = consumerId;
		this.meterId = meterId;
		this.meterNo = meterNo;
		this.billingMonth = billingMonth;
		this.billingYear = billingYear;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public int getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(int billingMonth) {
		this.billingMonth = billingMonth;
	}

	public int getBillingYear() {
		return billingYear;
	}

	public void setBillingYear(int billingYear) {
		this.billingYear = billingYear;
	}

}
