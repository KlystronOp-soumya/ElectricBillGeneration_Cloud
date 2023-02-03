package com.demo.taskdemo.billtask.entites;

import java.io.Serializable;

public class BillDataLoadInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int currDay;
	private int currMonth;
	private int currYear;
	private String journalDate;
	private String journalTime;
	private int billQuarter;

	public int getCurrDay() {
		return currDay;
	}

	public void setCurrDay(int currDay) {
		this.currDay = currDay;
	}

	public int getCurrMonth() {
		return currMonth;
	}

	public void setCurrMonth(int currMonth) {
		this.currMonth = currMonth;
	}

	public int getCurrYear() {
		return currYear;
	}

	public void setCurrYear(int currYear) {
		this.currYear = currYear;
	}

	public String getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(String journalDate) {
		this.journalDate = journalDate;
	}

	public String getJournalTime() {
		return journalTime;
	}

	public void setJournalTime(String journalTime) {
		this.journalTime = journalTime;
	}

	public int getBillQuarter() {
		return billQuarter;
	}

	public void setBillQuarter(int billQuarter) {
		this.billQuarter = billQuarter;
	}

}
