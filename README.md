# cordova-plugin-pagarme-mpos

## Uso

###### Pagamento com fluxo em interface nativa (ANDROID)

```js
pagarme.Mpos.payWithInterface({
	encryptionKey: string,
	amount: string,
	remoteApi: {
	  url: string,
	  type: 'POST'|'PUT'|'PATCH',
	  headers: {
	  },
	  params: {
	  }
	}
}, function (success) {
	console.log(success);
}, function (error) {
	console.log(error);
});
```

###### Usando as funções da SDK do pagar.me

```js
pagarme.Mpos.initialize({
	encryptionKey: string,
  	enableSafeTransactionConditions: true, // default false
  	alwaysUpdateTables: false, // default true
  	remoteApi: {
		url: string,
		type: 'POST'|'PUT'|'PATCH',
		headers: {
			'X-FOO': 'bar'
		},
		params: {
			foo: 'bar' 
		}
	}
}, function(success) {
    console.log(success);
}, function(error) {
    console.log(error);
});

pagarme.Mpos.getConnectedPinPad(function(data) {
	console.log(data);
}, function(error) {
	console.log(error);
});

pagarme.Mpos.listDevices(function(success) {
	console.log(success.devices);
}, function(error) {
	console.log(error);
});

pagarme.Mpos.connectPinPad({
	macAddress: pinPad.macAddress
}, function(data) {
	console.log(data);
}, function(error) {
	console.log(error);
});

pagarme.Mpos.disconnectPinPad(function(success) {
	console.log(success);
}, function(error) {
	console.log(error);
});

pagarme.Mpos.pay({
	amount: string, 
    paymentMethod: pagarme.Mpos.PAYMENT_METHOD_CREDIT_CARD, // pagarme.Mpos.PAYMENT_METHOD_DEBIT_CARD
}, function(success) {
    console.log(success);
}, function(error) {
    console.log(error);
});

pagarme.Mpos.downloadTables({
	forceUpdate: true,
	feedbackMessage: "atualizando dados..."
}, function(success) {
    console.log(success);
}, function(error) {
    console.log(error);
});

pagarme.Mpos.display("transacao Aprovada")
```