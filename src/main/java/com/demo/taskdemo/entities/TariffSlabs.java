package com.demo.taskdemo.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TARIFF_SLABS")
public class TariffSlabs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tariff_slab_id")
	private int id;

	@Column(name = "slab_start")
	private int slabStart;
	@Column(name = "slab_end")
	private int slabEnd;
	@Column(name = "slab_count")
	private int slab_count;
	@Column(name = "slab_rate")
	private BigDecimal slabRate;
	@Column(name = "rate")
	private BigDecimal fixedRate;

	@ManyToOne(targetEntity = Tariff.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumns({
			@JoinColumn(name = "TARIFF_ID", referencedColumnName = "TARIFF_ID", insertable = false, updatable = false)
	// @JoinColumn(name = "TARIFF_TYPE", referencedColumnName = "TARIFF_TYPE",
	// insertable = false, updatable = false),
	// @JoinColumn(name = "TARIFF_START_DATE", referencedColumnName =
	// "TARIFF_START_DATE", insertable = false, updatable = false)
	})
	private Tariff tariff;

	public TariffSlabs(int id, int slabStart, int slabEnd, int slab_count, BigDecimal slabRate, BigDecimal fixedRate,
			Tariff tariff) {
		super();
		this.id = id;
		this.slabStart = slabStart;
		this.slabEnd = slabEnd;
		this.slab_count = slab_count;
		this.slabRate = slabRate;
		this.fixedRate = fixedRate;
		this.tariff = tariff;
	}

	public TariffSlabs(int slabStart, int slabEnd, int slab_count, BigDecimal slabRate, BigDecimal fixedRate,
			Tariff tariff) {
		super();
		this.slabStart = slabStart;
		this.slabEnd = slabEnd;
		this.slab_count = slab_count;
		this.slabRate = slabRate;
		this.fixedRate = fixedRate;
		this.tariff = tariff;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSlabStart() {
		return slabStart;
	}

	public void setSlabStart(int slabStart) {
		this.slabStart = slabStart;
	}

	public int getSlabEnd() {
		return slabEnd;
	}

	public void setSlabEnd(int slabEnd) {
		this.slabEnd = slabEnd;
	}

	public int getSlab_count() {
		return slab_count;
	}

	public void setSlab_count(int slab_count) {
		this.slab_count = slab_count;
	}

	public BigDecimal getSlabRate() {
		return slabRate;
	}

	public void setSlabRate(BigDecimal slabRate) {
		this.slabRate = slabRate;
	}

	public BigDecimal getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(BigDecimal fixedRate) {
		this.fixedRate = fixedRate;
	}

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

}
