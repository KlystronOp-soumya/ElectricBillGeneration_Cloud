package com.demo.taskdemo.billtask.exceptions;

public class BillLoadingException extends Exception {

	public BillLoadingException(Exception e) {
		// TODO Auto-generated constructor stub
		super(e);
	}

	public BillLoadingException(Throwable cause, String message) {
		// TODO Auto-generated constructor stub
		super(message, cause);
	}

	public BillLoadingException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
