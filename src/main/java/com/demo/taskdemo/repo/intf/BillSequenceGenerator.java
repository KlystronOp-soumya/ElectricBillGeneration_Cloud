package com.demo.taskdemo.repo.intf;

public interface BillSequenceGenerator {

	String generate(int calMonth, int calYear, String tariffId, String consumerId, String meterId); // concats and
																									// generates a
																									// sequence number
}
