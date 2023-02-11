package com.demo.taskdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SUBSCRIBER_ADDRESS")
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID")
	private Long id;
	@Column(name = "LOCALITY")
	private String locality;
	@Column(name = "PINCODE")
	private String pincode;
	@Column(name = "ZILLA")
	private String zilla;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CONSUMER_ID", referencedColumnName = "CONSUMER_ID"),
			@JoinColumn(name = "METER_ID", referencedColumnName = "METER_ID") })
	private Consumers consumers;

	public Address(Long id, String locality, String pincode, String zilla) {
		super();
		this.id = id;
		this.locality = locality;
		this.pincode = pincode;
		this.zilla = zilla;
	}

	public Address(Long id, String locality, String pincode, String zilla, Consumers consumers) {
		super();
		this.id = id;
		this.locality = locality;
		this.pincode = pincode;
		this.zilla = zilla;
		this.consumers = consumers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getZilla() {
		return zilla;
	}

	public void setZilla(String zilla) {
		this.zilla = zilla;
	}

	public Consumers getConsumers() {
		return consumers;
	}

	public void setConsumers(Consumers consumers) {
		this.consumers = consumers;
	}

}
