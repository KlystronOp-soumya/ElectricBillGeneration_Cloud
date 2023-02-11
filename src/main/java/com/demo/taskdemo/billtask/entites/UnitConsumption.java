package com.demo.taskdemo.billtask.entites;

import java.io.Serializable;
import java.util.Date;

public class UnitConsumption implements Serializable {

	private static final long serialVersionUID = 1L;

	private String consumerId;// pk
	private String meterId;
	private String meterNo;// pk
	private int billingMonth; // pk
	private int billingYear;// pk
	private int unitConsumed;
	private String tariffType;

	private Date billingInsertionDate;

	public UnitConsumption() {
		// TODO Auto-generated constructor stub
	}

	public UnitConsumption(String consumerId, String meterId, String meterNo, int billingMonth, int billingYear,
			int unitConsumed, Date billingInsertionDate) {
		super();
		this.consumerId = consumerId;
		this.meterId = meterId;
		this.meterNo = meterNo;
		this.billingMonth = billingMonth;
		this.billingYear = billingYear;
		this.unitConsumed = unitConsumed;
		this.billingInsertionDate = billingInsertionDate;
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

	public int getUnitConsumed() {
		return unitConsumed;
	}

	public void setUnitConsumed(int unitConsumed) {
		this.unitConsumed = unitConsumed;
	}

	public String getTariffType() {
		return tariffType;
	}

	public void setTariffType(String tariffType) {
		this.tariffType = tariffType;
	}

	public Date getBillingInsertionDate() {
		return billingInsertionDate;
	}

	public void setBillingInsertionDate(Date billingInsertionDate) {
		this.billingInsertionDate = billingInsertionDate;
	}

	@Override
	public String toString() {
		return "UnitConsumption [consumerId=" + consumerId + ", meterId=" + meterId + ", meterNo=" + meterNo
				+ ", billingMonth=" + billingMonth + ", billingYear=" + billingYear + ", unitConsumed=" + unitConsumed
				+ ", billingInsertionDate=" + billingInsertionDate + "]";
	}

}
