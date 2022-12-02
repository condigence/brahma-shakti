package com.condigence.utils;

public class CustomErrorType {

	private String errorMessage;

	private String errorType;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private Object data;

	public CustomErrorType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

}