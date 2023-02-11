package com.demo.taskdemo.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class ConstantCharges implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String chargeType;
	private BigDecimal fixedCharge;// constant value
	private BigDecimal dutyRate;// per kW basis
	private BigDecimal MVCA_rate;// 0.29 per unit basis
	private BigDecimal meterCharge; // per month basis
	private String chargeStartDate;

	public ConstantCharges(String chargeType, BigDecimal fixedCharge, BigDecimal dutyRate, BigDecimal mVCA_rate,
			BigDecimal meterCharge, String chargeStartDate) {
		super();
		this.chargeType = chargeType;
		this.fixedCharge = fixedCharge;
		this.dutyRate = dutyRate;
		this.MVCA_rate = mVCA_rate;
		this.meterCharge = meterCharge;
		this.chargeStartDate = chargeStartDate;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public BigDecimal getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(BigDecimal fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public BigDecimal getDutyRate() {
		return dutyRate;
	}

	public void setDutyRate(BigDecimal dutyRate) {
		this.dutyRate = dutyRate;
	}

	public BigDecimal getMVCA_rate() {
		return MVCA_rate;
	}

	public void setMVCA_rate(BigDecimal mVCA_rate) {
		MVCA_rate = mVCA_rate;
	}

	public BigDecimal getMeterCharge() {
		return meterCharge;
	}

	public void setMeterCharge(BigDecimal meterCharge) {
		this.meterCharge = meterCharge;
	}

	public String getChargeStartDate() {
		return chargeStartDate;
	}

	public void setChargeStartDate(String chargeStartDate) {
		this.chargeStartDate = chargeStartDate;
	}

}
