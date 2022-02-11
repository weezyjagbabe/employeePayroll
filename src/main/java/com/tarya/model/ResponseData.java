package com.tarya.model;

/**
*
* @author SBortey
*/
public class ResponseData<data> {
	private data responseContent;
	private int responseCode;
	private String responseMessage;
	public data getResponseContent() {
		return responseContent;
	}
	public void setResponseContent(data responseContent) {
		this.responseContent = responseContent;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int created) {
		this.responseCode = created;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}