package com.gurpster.cordova.pagarme.mpos.entity.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Data{

	@JSONField(name="data")
	private Data data;

	@JSONField(name="message")
	private String message;

	@JSONField(name="referer")
	private String referer;

	@JSONField(name="metadata")
	private Metadata metadata;

	@JSONField(name="discount")
	private Object discount;

	@JSONField(name="card_last_digits")
	private String cardLastDigits;

	@JSONField(name="tid")
	private int tid;

	@JSONField(name="billing")
	private Object billing;

	@JSONField(name="subscription_id")
	private Object subscriptionId;

	@JSONField(name="shipping")
	private Object shipping;

	@JSONField(name="local_time")
	private Object localTime;

	@JSONField(name="soft_descriptor")
	private Object softDescriptor;

	@JSONField(name="installments")
	private int installments;

	@JSONField(name="split_rules")
	private Object splitRules;

	@JSONField(name="boleto_expiration_date")
	private Object boletoExpirationDate;

	@JSONField(name="authorized_amount")
	private int authorizedAmount;

	@JSONField(name="reference_key")
	private Object referenceKey;

	@JSONField(name="payment")
	private Object payment;

	@JSONField(name="id")
	private int id;

	@JSONField(name="acquirer_response_code")
	private String acquirerResponseCode;

	@JSONField(name="payment_method")
	private String paymentMethod;

	@JSONField(name="addition")
	private Object addition;

	@JSONField(name="capture_method")
	private String captureMethod;

	@JSONField(name="card_pin_mode")
	private String cardPinMode;

	@JSONField(name="cvm_pin")
	private boolean cvmPin;

	@JSONField(name="private_label")
	private Object privateLabel;

	@JSONField(name="refuse_reason")
	private Object refuseReason;

	@JSONField(name="antifraud_metadata")
	private AntifraudMetadata antifraudMetadata;

	@JSONField(name="ip")
	private String ip;

	@JSONField(name="card_first_digits")
	private String cardFirstDigits;

	@JSONField(name="refunded_amount")
	private int refundedAmount;

	@JSONField(name="antifraud_score")
	private Object antifraudScore;

	@JSONField(name="phone")
	private Object phone;

	@JSONField(name="acquirer_id")
	private String acquirerId;

	@JSONField(name="paid_amount")
	private int paidAmount;

	@JSONField(name="receipt")
	private List<ReceiptItem> receipt;

	@JSONField(name="card_emv_response")
	private String cardEmvResponse;

	@JSONField(name="items")
	private List<Object> items;

	@JSONField(name="device")
	private Object device;

	@JSONField(name="order_id")
	private Object orderId;

	@JSONField(name="card")
	private Card card;

	@JSONField(name="object")
	private String object;

	@JSONField(name="status")
	private String status;

	@JSONField(name="date_updated")
	private String dateUpdated;

	@JSONField(name="card_holder_name")
	private String cardHolderName;

	@JSONField(name="boleto_barcode")
	private Object boletoBarcode;

	@JSONField(name="fraud_covered")
	private boolean fraudCovered;

	@JSONField(name="local_transaction_id")
	private String localTransactionId;

	@JSONField(name="nsu")
	private int nsu;

	@JSONField(name="receipt_url")
	private Object receiptUrl;

	@JSONField(name="authorization_code")
	private String authorizationCode;

	@JSONField(name="card_magstripe_fallback")
	private boolean cardMagstripeFallback;

	@JSONField(name="card_brand")
	private String cardBrand;

	@JSONField(name="status_reason")
	private String statusReason;

	@JSONField(name="amount")
	private int amount;

	@JSONField(name="cost")
	private int cost;

	@JSONField(name="address")
	private Object address;

	@JSONField(name="postback_url")
	private Object postbackUrl;

	@JSONField(name="date_created")
	private String dateCreated;

	@JSONField(name="boleto_url")
	private Object boletoUrl;

	@JSONField(name="acquirer_name")
	private String acquirerName;

	@JSONField(name="risk_level")
	private String riskLevel;

	@JSONField(name="customer")
	private Object customer;

	@JSONField(name="fraud_reimbursed")
	private Object fraudReimbursed;

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

	public void setReferer(String referer){
		this.referer = referer;
	}

	public String getReferer(){
		return referer;
	}

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setDiscount(Object discount){
		this.discount = discount;
	}

	public Object getDiscount(){
		return discount;
	}

	public void setCardLastDigits(String cardLastDigits){
		this.cardLastDigits = cardLastDigits;
	}

	public String getCardLastDigits(){
		return cardLastDigits;
	}

	public void setTid(int tid){
		this.tid = tid;
	}

	public int getTid(){
		return tid;
	}

	public void setBilling(Object billing){
		this.billing = billing;
	}

	public Object getBilling(){
		return billing;
	}

	public void setSubscriptionId(Object subscriptionId){
		this.subscriptionId = subscriptionId;
	}

	public Object getSubscriptionId(){
		return subscriptionId;
	}

	public void setShipping(Object shipping){
		this.shipping = shipping;
	}

	public Object getShipping(){
		return shipping;
	}

	public void setLocalTime(Object localTime){
		this.localTime = localTime;
	}

	public Object getLocalTime(){
		return localTime;
	}

	public void setSoftDescriptor(Object softDescriptor){
		this.softDescriptor = softDescriptor;
	}

	public Object getSoftDescriptor(){
		return softDescriptor;
	}

	public void setInstallments(int installments){
		this.installments = installments;
	}

	public int getInstallments(){
		return installments;
	}

	public void setSplitRules(Object splitRules){
		this.splitRules = splitRules;
	}

	public Object getSplitRules(){
		return splitRules;
	}

	public void setBoletoExpirationDate(Object boletoExpirationDate){
		this.boletoExpirationDate = boletoExpirationDate;
	}

	public Object getBoletoExpirationDate(){
		return boletoExpirationDate;
	}

	public void setAuthorizedAmount(int authorizedAmount){
		this.authorizedAmount = authorizedAmount;
	}

	public int getAuthorizedAmount(){
		return authorizedAmount;
	}

	public void setReferenceKey(Object referenceKey){
		this.referenceKey = referenceKey;
	}

	public Object getReferenceKey(){
		return referenceKey;
	}

	public void setPayment(Object payment){
		this.payment = payment;
	}

	public Object getPayment(){
		return payment;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAcquirerResponseCode(String acquirerResponseCode){
		this.acquirerResponseCode = acquirerResponseCode;
	}

	public String getAcquirerResponseCode(){
		return acquirerResponseCode;
	}

	public void setPaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public void setAddition(Object addition){
		this.addition = addition;
	}

	public Object getAddition(){
		return addition;
	}

	public void setCaptureMethod(String captureMethod){
		this.captureMethod = captureMethod;
	}

	public String getCaptureMethod(){
		return captureMethod;
	}

	public void setCardPinMode(String cardPinMode){
		this.cardPinMode = cardPinMode;
	}

	public String getCardPinMode(){
		return cardPinMode;
	}

	public void setCvmPin(boolean cvmPin){
		this.cvmPin = cvmPin;
	}

	public boolean isCvmPin(){
		return cvmPin;
	}

	public void setPrivateLabel(Object privateLabel){
		this.privateLabel = privateLabel;
	}

	public Object getPrivateLabel(){
		return privateLabel;
	}

	public void setRefuseReason(Object refuseReason){
		this.refuseReason = refuseReason;
	}

	public Object getRefuseReason(){
		return refuseReason;
	}

	public void setAntifraudMetadata(AntifraudMetadata antifraudMetadata){
		this.antifraudMetadata = antifraudMetadata;
	}

	public AntifraudMetadata getAntifraudMetadata(){
		return antifraudMetadata;
	}

	public void setIp(String ip){
		this.ip = ip;
	}

	public String getIp(){
		return ip;
	}

	public void setCardFirstDigits(String cardFirstDigits){
		this.cardFirstDigits = cardFirstDigits;
	}

	public String getCardFirstDigits(){
		return cardFirstDigits;
	}

	public void setRefundedAmount(int refundedAmount){
		this.refundedAmount = refundedAmount;
	}

	public int getRefundedAmount(){
		return refundedAmount;
	}

	public void setAntifraudScore(Object antifraudScore){
		this.antifraudScore = antifraudScore;
	}

	public Object getAntifraudScore(){
		return antifraudScore;
	}

	public void setPhone(Object phone){
		this.phone = phone;
	}

	public Object getPhone(){
		return phone;
	}

	public void setAcquirerId(String acquirerId){
		this.acquirerId = acquirerId;
	}

	public String getAcquirerId(){
		return acquirerId;
	}

	public void setPaidAmount(int paidAmount){
		this.paidAmount = paidAmount;
	}

	public int getPaidAmount(){
		return paidAmount;
	}

	public void setReceipt(List<ReceiptItem> receipt){
		this.receipt = receipt;
	}

	public List<ReceiptItem> getReceipt(){
		return receipt;
	}

	public void setCardEmvResponse(String cardEmvResponse){
		this.cardEmvResponse = cardEmvResponse;
	}

	public String getCardEmvResponse(){
		return cardEmvResponse;
	}

	public void setItems(List<Object> items){
		this.items = items;
	}

	public List<Object> getItems(){
		return items;
	}

	public void setDevice(Object device){
		this.device = device;
	}

	public Object getDevice(){
		return device;
	}

	public void setOrderId(Object orderId){
		this.orderId = orderId;
	}

	public Object getOrderId(){
		return orderId;
	}

	public void setCard(Card card){
		this.card = card;
	}

	public Card getCard(){
		return card;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setDateUpdated(String dateUpdated){
		this.dateUpdated = dateUpdated;
	}

	public String getDateUpdated(){
		return dateUpdated;
	}

	public void setCardHolderName(String cardHolderName){
		this.cardHolderName = cardHolderName;
	}

	public String getCardHolderName(){
		return cardHolderName;
	}

	public void setBoletoBarcode(Object boletoBarcode){
		this.boletoBarcode = boletoBarcode;
	}

	public Object getBoletoBarcode(){
		return boletoBarcode;
	}

	public void setFraudCovered(boolean fraudCovered){
		this.fraudCovered = fraudCovered;
	}

	public boolean isFraudCovered(){
		return fraudCovered;
	}

	public void setLocalTransactionId(String localTransactionId){
		this.localTransactionId = localTransactionId;
	}

	public String getLocalTransactionId(){
		return localTransactionId;
	}

	public void setNsu(int nsu){
		this.nsu = nsu;
	}

	public int getNsu(){
		return nsu;
	}

	public void setReceiptUrl(Object receiptUrl){
		this.receiptUrl = receiptUrl;
	}

	public Object getReceiptUrl(){
		return receiptUrl;
	}

	public void setAuthorizationCode(String authorizationCode){
		this.authorizationCode = authorizationCode;
	}

	public String getAuthorizationCode(){
		return authorizationCode;
	}

	public void setCardMagstripeFallback(boolean cardMagstripeFallback){
		this.cardMagstripeFallback = cardMagstripeFallback;
	}

	public boolean isCardMagstripeFallback(){
		return cardMagstripeFallback;
	}

	public void setCardBrand(String cardBrand){
		this.cardBrand = cardBrand;
	}

	public String getCardBrand(){
		return cardBrand;
	}

	public void setStatusReason(String statusReason){
		this.statusReason = statusReason;
	}

	public String getStatusReason(){
		return statusReason;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setCost(int cost){
		this.cost = cost;
	}

	public int getCost(){
		return cost;
	}

	public void setAddress(Object address){
		this.address = address;
	}

	public Object getAddress(){
		return address;
	}

	public void setPostbackUrl(Object postbackUrl){
		this.postbackUrl = postbackUrl;
	}

	public Object getPostbackUrl(){
		return postbackUrl;
	}

	public void setDateCreated(String dateCreated){
		this.dateCreated = dateCreated;
	}

	public String getDateCreated(){
		return dateCreated;
	}

	public void setBoletoUrl(Object boletoUrl){
		this.boletoUrl = boletoUrl;
	}

	public Object getBoletoUrl(){
		return boletoUrl;
	}

	public void setAcquirerName(String acquirerName){
		this.acquirerName = acquirerName;
	}

	public String getAcquirerName(){
		return acquirerName;
	}

	public void setRiskLevel(String riskLevel){
		this.riskLevel = riskLevel;
	}

	public String getRiskLevel(){
		return riskLevel;
	}

	public void setCustomer(Object customer){
		this.customer = customer;
	}

	public Object getCustomer(){
		return customer;
	}

	public void setFraudReimbursed(Object fraudReimbursed){
		this.fraudReimbursed = fraudReimbursed;
	}

	public Object getFraudReimbursed(){
		return fraudReimbursed;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",referer = '" + referer + '\'' + 
			",metadata = '" + metadata + '\'' + 
			",discount = '" + discount + '\'' + 
			",card_last_digits = '" + cardLastDigits + '\'' + 
			",tid = '" + tid + '\'' + 
			",billing = '" + billing + '\'' + 
			",subscription_id = '" + subscriptionId + '\'' + 
			",shipping = '" + shipping + '\'' + 
			",local_time = '" + localTime + '\'' + 
			",soft_descriptor = '" + softDescriptor + '\'' + 
			",installments = '" + installments + '\'' + 
			",split_rules = '" + splitRules + '\'' + 
			",boleto_expiration_date = '" + boletoExpirationDate + '\'' + 
			",authorized_amount = '" + authorizedAmount + '\'' + 
			",reference_key = '" + referenceKey + '\'' + 
			",payment = '" + payment + '\'' + 
			",id = '" + id + '\'' + 
			",acquirer_response_code = '" + acquirerResponseCode + '\'' + 
			",payment_method = '" + paymentMethod + '\'' + 
			",addition = '" + addition + '\'' + 
			",capture_method = '" + captureMethod + '\'' + 
			",card_pin_mode = '" + cardPinMode + '\'' + 
			",cvm_pin = '" + cvmPin + '\'' + 
			",private_label = '" + privateLabel + '\'' + 
			",refuse_reason = '" + refuseReason + '\'' + 
			",antifraud_metadata = '" + antifraudMetadata + '\'' + 
			",ip = '" + ip + '\'' + 
			",card_first_digits = '" + cardFirstDigits + '\'' + 
			",refunded_amount = '" + refundedAmount + '\'' + 
			",antifraud_score = '" + antifraudScore + '\'' + 
			",phone = '" + phone + '\'' + 
			",acquirer_id = '" + acquirerId + '\'' + 
			",paid_amount = '" + paidAmount + '\'' + 
			",receipt = '" + receipt + '\'' + 
			",card_emv_response = '" + cardEmvResponse + '\'' + 
			",items = '" + items + '\'' + 
			",device = '" + device + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",card = '" + card + '\'' + 
			",object = '" + object + '\'' + 
			",status = '" + status + '\'' + 
			",date_updated = '" + dateUpdated + '\'' + 
			",card_holder_name = '" + cardHolderName + '\'' + 
			",boleto_barcode = '" + boletoBarcode + '\'' + 
			",fraud_covered = '" + fraudCovered + '\'' + 
			",local_transaction_id = '" + localTransactionId + '\'' + 
			",nsu = '" + nsu + '\'' + 
			",receipt_url = '" + receiptUrl + '\'' + 
			",authorization_code = '" + authorizationCode + '\'' + 
			",card_magstripe_fallback = '" + cardMagstripeFallback + '\'' + 
			",card_brand = '" + cardBrand + '\'' + 
			",status_reason = '" + statusReason + '\'' + 
			",amount = '" + amount + '\'' + 
			",cost = '" + cost + '\'' + 
			",address = '" + address + '\'' + 
			",postback_url = '" + postbackUrl + '\'' + 
			",date_created = '" + dateCreated + '\'' + 
			",boleto_url = '" + boletoUrl + '\'' + 
			",acquirer_name = '" + acquirerName + '\'' + 
			",risk_level = '" + riskLevel + '\'' + 
			",customer = '" + customer + '\'' + 
			",fraud_reimbursed = '" + fraudReimbursed + '\'' + 
			"}";
		}
}