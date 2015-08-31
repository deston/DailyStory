package com.deston.base.business;

import android.util.Log;
import com.deston.base.Constants;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.NetworkResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestManager {
    public static final String TAG = "RequestManager";
    private static class SingletonHolder {
        private static RequestManager instance = new RequestManager();
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public void addRequest(final BusinessRequest businessRequest, final BusinessListener listener) {
        String url = getUrlByCode(businessRequest.businessCode);
        HttpRequest request = new HttpRequest(String.class, url);
        HttpEngine.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(NetworkResponse response) {
                BusinessResponse businessResponse = parseResponse(response, businessRequest.businessCode);
                listener.onResponse(businessResponse);
            }
            @Override
            public void onCancel() {

            }
        });
    }
    private String getUrlByCode(int businessCode) {
        String url = "";
        switch (businessCode) {
            case Constants.BusinessCode.GET_DAILY_STORY_LIST:
                url = "http://104.128.85.9:8001/api/gethomelist";
                break;
            default:
                break;
        }
        return url;
    }
    private BusinessResponse parseResponse(NetworkResponse response, int businessCode) {
        BusinessResponse businessResponse = null;
        switch (businessCode) {
            case Constants.BusinessCode.GET_DAILY_STORY_LIST:
                if (response.httpCode == 200) {
                    businessResponse = new GetDailyStoryListResponse();
                    try {
                        JSONObject jsonObject = new JSONObject(response.response.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i(Constants.TAG_COMM, response.response.toString());
                }
                break;
        }
        return businessResponse;
    }
}
