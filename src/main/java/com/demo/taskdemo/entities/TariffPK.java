package com.demo.taskdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TariffPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "TARIFF_TYPE")
	private String tariffType;
	@Column(name = "TARIFF_START_DATE")
	private String tariffStartDate;

	public TariffPK(String tariffType, String tariffStartDate) {
		super();
		this.tariffType = tariffType;
		this.tariffStartDate = tariffStartDate;
	}

	public String getTariffType() {
		return tariffType;
	}

	public void setTariffType(String tariffType) {
		this.tariffType = tariffType;
	}

	public String getTariffStartDate() {
		return tariffStartDate;
	}

	public void setTariffStartDate(String tariffStartDate) {
		this.tariffStartDate = tariffStartDate;
	}

}
