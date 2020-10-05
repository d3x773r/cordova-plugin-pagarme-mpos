package com.gurpster.cordova.pagarme.mpos.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

public class ReceiptItem{

	@JSONField(name="name")
	private String name;

	@JSONField(name="type")
	private String type;

	@JSONField(name="value")
	private int value;

	@JSONField(name="currency")
	private String currency;

	@JSONField(name="message")
	private String message;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
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
			"ReceiptItem{" + 
			"name = '" + name + '\'' + 
			",type = '" + type + '\'' + 
			",value = '" + value + '\'' + 
			",currency = '" + currency + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}