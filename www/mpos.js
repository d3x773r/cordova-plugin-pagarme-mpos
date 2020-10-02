/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var mposPluginName = "Mpos";
module.exports = {
  initialize: function (params, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'initialize', [params])
  },

  listDevices: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'listDevices', [])
  },
  findAndConnectPinPad: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'findAndConnectPinPad', [])
  },
  connectPinPad: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'connectPinPad', [])
  },
  connectPinPad: function (pinPadMacAddress, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'connectPinPad', [pinPadMacAddress])
  },
  disconnectPinPad: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'disconnectPinPad', [])
  },
  getConnectedPinPad: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'getConnectedPinPad', [])
  },

  openConnection: function (deviceName, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'openConnection', [deviceName])
  },
  closeConnection: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'closeConnection', [])
  },

  downloadTables: function (force, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'downloadTables', [force])
  },

  display: function (message, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'display', [message])
  },

  pay: function (params, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, mposPluginName, 'pay', [params])
  },

  cancel: function () {
    cordova.exec(function () {}, function () {}, mposPluginName, 'cancel', [])
  },

  DEVICE_CONNECTED: 20,
  DEVICE_NOT_CONNECTED: 21,
  BLUETOOTH_CONNECTED: 22,
  BLUETOOTH_CONNECTING: 27,
  BLUETOOTH_NOT_CONNECTED: 23,
  PAYMENT_ERROR: 24,
  PAYMENT_SUCCESSFUL: 25,
  PAYMENT_PROCESSING: 26,
  PAYMENT_METHOD_CREDIT_CARD: 1,
  PAYMENT_METHOD_DEBIT_CARD: 2,
  INITIALIZED_SUCCESSFULLY: 10,
  INITIALIZED_ERROR: 11,
  PIN_PAD_TABLES_UPDATED: 28,
  PIN_PAD_CANCELED: 29,
}
