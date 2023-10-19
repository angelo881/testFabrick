package com.fabrick.data.response;

public class Response<T> {
	//generico oggetto di risposta, può essere esteso e utilizza generics
	private ResponseCode code = ResponseCode.OK;
	private String description = "";
	private T payload;

	public Response(T payload) {
		this.payload = payload;
	}
	
	public Response(ResponseCode code, String description) {
		this.code = code;
		this.description = description;
	}
	
	
	public ResponseCode getCode() {
		return code;
	}

	public void setCode(ResponseCode code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
