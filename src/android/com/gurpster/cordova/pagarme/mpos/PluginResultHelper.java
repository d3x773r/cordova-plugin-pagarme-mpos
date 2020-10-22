package com.gurpster.cordova.pagarme.mpos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;

public class ResultHelper {
    public static PluginResult makePluginResult(PluginResult.Status status, Object... args) {
        if (args.length == 0) {
            return new PluginResult(status);
        }
        if (args.length == 1) {
            Object args0 = args[0];
            if (args0 instanceof JSONObject) {
                return new PluginResult(status, (JSONObject) args0);
            }
            if (args0 instanceof JSONArray) {
                return new PluginResult(status, (JSONArray) args0);
            }
            if (args0 instanceof Integer) {
                return new PluginResult(status, (Integer) args0);
            }
            if (args0 instanceof String) {
                return new PluginResult(status, (String) args0);
            }
        }

        JSONArray result = new JSONArray();
        for (Object arg : args) {
            result.put(arg);
        }
        return new PluginResult(status, result);
    }
}