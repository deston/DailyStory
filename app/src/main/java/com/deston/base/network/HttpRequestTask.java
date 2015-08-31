package com.deston.base.network;


import android.util.Log;
import com.deston.base.Constants;

import java.io.IOException;

public class HttpRequestTask extends RequestTask {
    private HttpDispatcher mDispatcher;
    private HttpUrlStack mHttpStack;

    public HttpRequest getRequest() {
        return mRequest;
    }

    public HttpRequestTask(HttpRequest request, HttpDispatcher httpDispatcher, HttpUrlStack httpUrlStack) {
        super(request);
        this.mDispatcher = httpDispatcher;
        this.mHttpStack = httpUrlStack;
    }


    @Override
    public NetworkResponse executeRequest(HttpRequest request) {
        NetworkResponse response = null;
        try {
            Log.i(Constants.TAG_COMM, "HttpRequestTask : begin executeRequest");
            response = mHttpStack.performRequest(mRequest);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(Constants.TAG_COMM, "HttpRequestTask : executeRequest catch exception , e = " + e);
        }
        return response;
    }

    @Override
    public void onFinish() {
        if (mNetworkResponse != null) {
            if (mRequest.isShouldCache()) {
                mDispatcher.putToCache(mRequest.getCacheKey(), mNetworkResponse);
            }
            mDispatcher.dispatchResponse(this);
            mDispatcher.removeRunningTask(this);
        }
    }

}
