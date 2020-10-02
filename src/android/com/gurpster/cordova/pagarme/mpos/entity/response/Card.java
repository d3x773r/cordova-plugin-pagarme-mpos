package com.gurpster.cordova.pagarme.mpos.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

public class Card{

	@JSONField(name="valid")
	private boolean valid;

	@JSONField(name="country")
	private String country;

	@JSONField(name="date_updated")
	private String dateUpdated;

	@JSONField(name="date_created")
	private String dateCreated;

	@JSONField(name="fingerprint")
	private String fingerprint;

	@JSONField(name="first_digits")
	private String firstDigits;

	@JSONField(name="id")
	private String id;

	@JSONField(name="expiration_date")
	private String expirationDate;

	@JSONField(name="brand")
	private String brand;

	@JSONField(name="last_digits")
	private String lastDigits;

	@JSONField(name="object")
	private String object;

	@JSONField(name="holder_name")
	private String holderName;

	public void setValid(boolean valid){
		this.valid = valid;
	}

	public boolean isValid(){
		return valid;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setDateUpdated(String dateUpdated){
		this.dateUpdated = dateUpdated;
	}

	public String getDateUpdated(){
		return dateUpdated;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setFingerprint(String fingerprint){
		this.fingerprint = fingerprint;
	}

	public String getFingerprint(){
		return fingerprint;
	}

	public void setFirstDigits(String firstDigits){
		this.firstDigits = firstDigits;
	}

	public String getFirstDigits(){
		return firstDigits;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}

	public String getExpirationDate(){
		return expirationDate;
	}

	public void setBrand(String brand){
		this.brand = brand;
	}

	public String getBrand(){
		return brand;
	}

	public void setLastDigits(String lastDigits){
		this.lastDigits = lastDigits;
	}

	public String getLastDigits(){
		return lastDigits;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}

	public void setHolderName(String holderName){
		this.holderName = holderName;
	}

	public String getHolderName(){
		return holderName;
	}

	@Override
 	public String toString(){
		return
			"Card{" +
			"valid = '" + valid + '\'' +
			",country = '" + country + '\'' +
			",date_updated = '" + dateUpdated + '\'' +
			",date_created = '" + dateCreated + '\'' +
			",fingerprint = '" + fingerprint + '\'' +
			",first_digits = '" + firstDigits + '\'' +
			",id = '" + id + '\'' +
			",expiration_date = '" + expirationDate + '\'' +
			",brand = '" + brand + '\'' +
			",last_digits = '" + lastDigits + '\'' +
			",object = '" + object + '\'' +
			",holder_name = '" + holderName + '\'' +
			"}";
		}
}