package com.deston.base.business;

import android.util.Log;
import com.deston.base.Constants;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.ResponseEntity;

public class RequestManager {
    public static final String TAG = "RequestManager";
    private static class SingletonHolder {
        private static RequestManager instance = new RequestManager();
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public void addRequest(BusinessRequest businessRequest, final BusinessListener listener) {
        String url = "";

        if (Constants.BusinessCode.GET_DAILY_STORY_LIST.equals(businessRequest.businessCode)) {
            url = Constants.URL.URL_FOR_DAILY_STORY_LIST;
        }

        HttpRequest request = new HttpRequest(url);
        Log.i(TAG, "url = " + url);
        HttpEngine.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(ResponseEntity entity) {
                Log.i(TAG, "get daily list end" + entity.response);
                listener.onResponse(null);
            }

            @Override
            public void onCancel() {

            }
        });

    }
}
