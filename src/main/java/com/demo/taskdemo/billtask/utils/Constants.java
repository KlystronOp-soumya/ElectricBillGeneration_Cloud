package com.demo.taskdemo.billtask.utils;

public enum Constants {

	// INPUT_CSV_PATH("data/billData.csv");
	INPUT_CSV_PATH("D:\\Program Files\\Java Programs\\Spring Workspace\\data\\billData.csv");

	private String value;

	Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
