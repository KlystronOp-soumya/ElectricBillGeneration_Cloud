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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private int slabCount;
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

}
