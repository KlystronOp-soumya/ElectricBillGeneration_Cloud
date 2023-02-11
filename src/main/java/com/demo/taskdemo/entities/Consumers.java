package com.demo.taskdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONSUMERS")
public class Consumers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConsumersPK consueConsumersPK;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	/*
	 * @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	 * 
	 * @JoinColumns({
	 * 
	 * @JoinColumn(name = "ADDRESS_ID", columnDefinition = "ADDRESS_ID", insertable
	 * = true, nullable = false, updatable = true) })
	 * 
	 * @Column(name = "ADDRESS_ID") private Address address;
	 */

	@Column(name = "CONSUMER_EMAIL")
	private String email;

	public Consumers(ConsumersPK consueConsumersPK, String consumerName, String email) {
		super();
		this.consueConsumersPK = consueConsumersPK;
		this.consumerName = consumerName;
		this.email = email;
	}

	public ConsumersPK getConsueConsumersPK() {
		return consueConsumersPK;
	}

	public void setConsueConsumersPK(ConsumersPK consueConsumersPK) {
		this.consueConsumersPK = consueConsumersPK;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
