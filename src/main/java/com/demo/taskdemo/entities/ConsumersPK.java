package com.demo.taskdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConsumersPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CONSUMER_ID")
	private String consumerId;
	@Column(name = "METER_ID")
	private String meterId;

	public ConsumersPK(String consumerId, String meterId) {
		super();
		this.consumerId = consumerId;
		this.meterId = meterId;
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

}
