package com.gurpster.cordova.pagarme.mpos.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

public class Response{

	@JSONField(name="data")
	private Data data;

	@JSONField(name="message")
	private String message;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}