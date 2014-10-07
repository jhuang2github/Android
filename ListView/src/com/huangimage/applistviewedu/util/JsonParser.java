package com.huangimage.applistviewedu.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {

    public JsonParser() {
    }

    public JSONObject parseJsonStringToJsonObj(String jsonStr) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return obj;
    }

    public JSONObject getJsonObjFromUrl(String url, int method) {
    	return parseJsonStringToJsonObj(new HttpConnection(url, method).execute());
    }
}
