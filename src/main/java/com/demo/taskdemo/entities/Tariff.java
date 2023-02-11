package com.demo.taskdemo.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "TARIFF")
@DynamicInsert
@DynamicUpdate
public class Tariff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TARIFF_ID", length = 10)
	private String tariffId;

	@EmbeddedId
	private TariffPK traiffPK;

	@Column(name = "EXTRA_CHRG")
	private BigDecimal extraCharge;
	@Column(name = "VARIABLE_CHRG")
	private BigDecimal variableCharge;
	@Column(name = "METER_CHRG")
	private BigDecimal meterCharge;

	public Tariff(String tariffId, TariffPK traiffPK) {
		super();
		this.tariffId = tariffId;
		this.traiffPK = traiffPK;
	}

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}

	public TariffPK getTraiffPK() {
		return traiffPK;
	}

	public void setTraiffPK(TariffPK traiffPK) {
		this.traiffPK = traiffPK;
	}

	public BigDecimal getExtraCharge() {
		return extraCharge;
	}

	public void setExtraCharge(BigDecimal extraCharge) {
		this.extraCharge = extraCharge;
	}

	public BigDecimal getVariableCharge() {
		return variableCharge;
	}

	public void setVariableCharge(BigDecimal variableCharge) {
		this.variableCharge = variableCharge;
	}

	public BigDecimal getMeterCharge() {
		return meterCharge;
	}

	public void setMeterCharge(BigDecimal meterCharge) {
		this.meterCharge = meterCharge;
	}

}
