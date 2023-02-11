package com.demo.taskdemo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UNIT_CONSUMPTION")
public class UnitConsumption implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UnitConsumptionPK unitConsumptionPK;
	@Column(name = "UNIT_CONSUMED")
	private int unitConsumed;

	@Column(name = "TARIFF_TYPE")
	private String tariffType;

	@Column(name = "INSERT_DATE")
	private Date billingInsertionDate;

	@ManyToOne(targetEntity = Consumers.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumns({
			@JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID", insertable = false, updatable = false),
			@JoinColumn(name = "METER_ID", referencedColumnName = "METER_ID", insertable = false, updatable = false) })
	private Consumers consumer;

	public UnitConsumption() {
		// TODO Auto-generated constructor stub
	}

	public UnitConsumption(UnitConsumptionPK unitConsumptionPK, int unitConsumed, Date billingInsertionDate,
			Consumers consumer) {
		super();
		this.unitConsumptionPK = unitConsumptionPK;
		this.unitConsumed = unitConsumed;
		this.billingInsertionDate = billingInsertionDate;
		this.consumer = consumer;
	}

	public UnitConsumptionPK getUnitConsumptionPK() {
		return unitConsumptionPK;
	}

	public void setUnitConsumptionPK(UnitConsumptionPK unitConsumptionPK) {
		this.unitConsumptionPK = unitConsumptionPK;
	}

	public int getUnitConsumed() {
		return unitConsumed;
	}

	public void setUnitConsumed(int unitConsumed) {
		this.unitConsumed = unitConsumed;
	}

	public Date getBillingInsertionDate() {
		return billingInsertionDate;
	}

	public void setBillingInsertionDate(Date billingInsertionDate) {
		this.billingInsertionDate = billingInsertionDate;
	}

	public Consumers getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumers consumer) {
		this.consumer = consumer;
	}

	public String getTariffType() {
		return tariffType;
	}

	public void setTariffType(String tariffType) {
		this.tariffType = tariffType;
	}

}
