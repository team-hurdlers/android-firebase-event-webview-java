package com.example.hurdlers_webview;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class AnalyticsWebInterface {
    public static final String TAG = "AnalyticsWebInterface";
    private FirebaseAnalytics mAnalytics;

    public AnalyticsWebInterface(Context context) {
        mAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @JavascriptInterface
    public void logEvent(String name, String jsonParams) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(jsonParams);
            Bundle params = iterateJsonAndAddToBundle(jsonObject);
            LOGD(String.valueOf(params));

            mAnalytics.logEvent(name, params);

        } catch (JSONException e) {
            LOGD(e.getMessage());
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void setUserProperty(String name, String value) {
        LOGD("setUserProperty:" + name);
        mAnalytics.setUserProperty(name, value);
    }

    private void LOGD(String message) {
        Log.d(TAG, message);
    }
    private Bundle iterateJsonAndAddToBundle(JSONObject jsonObject) throws JSONException {
        Iterator<String> keys = jsonObject.keys();
        Bundle item = new Bundle();

        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals("items")) {
                Bundle items = googleAnalyticsItemParamToBundle(jsonObject.getJSONArray("items"));
                item.putBundle("items", items);
            } else if(key.equals("value")) {
                double value = Double.parseDouble(jsonObject.getString(key));
                item.putDouble("value", value);
            } else {
                String value = jsonObject.getString(key);
                item.putString(key,value);
            }
        }

        return item;
    }

    private Bundle googleAnalyticsItemParamToBundle(JSONArray itemsArray) throws JSONException {
        Bundle item = new Bundle();

        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject itemJson = itemsArray.getJSONObject(i);
            item.putString(FirebaseAnalytics.Param.ITEM_ID, itemJson.getString("item_id"));
            item.putString(FirebaseAnalytics.Param.ITEM_NAME, itemJson.getString("item_name"));
            item.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemJson.getString("item_category"));
            item.putString(FirebaseAnalytics.Param.ITEM_VARIANT, itemJson.getString("item_variant"));
            item.putString(FirebaseAnalytics.Param.ITEM_BRAND, itemJson.getString("item_brand"));
            item.putDouble(FirebaseAnalytics.Param.PRICE, Double.parseDouble(itemJson.getString("price")));
            item.putDouble(FirebaseAnalytics.Param.QUANTITY, Double.parseDouble(itemJson.getString("quantity")));
        }
        return item;
    }
}
