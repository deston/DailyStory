package com.deston.base.network;


import android.util.Log;
import com.deston.base.Constants;

import java.io.IOException;

public class HttpRequestTask extends RequestTask{
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
    public ResponseEntity executeRequest(HttpRequest request)  {
        ResponseEntity responseEntity = new ResponseEntity();
        NetworkResponse response = null;
        try {
            Log.i(Constants.TAG_COMM, "HttpRequestTask : begin executeRequest");
            response = mHttpStack.performRequest(mRequest);
        } catch (IOException e) {
            e.printStackTrace();
            responseEntity.code = -1;
            Log.i(Constants.TAG_COMM, "HttpRequestTask : executeRequest catch exception , e = " + e);
        }
        Log.i(Constants.TAG_COMM, "HttpRequestTask : finish executeRequest, response = " + response);
        responseEntity.response = response;
        return responseEntity;
    }

    @Override
    public void onFinish() {
        ResponseEntity entity = mResponseEntity;
        if (entity != null) {
            if (entity.code != -1) {
                if (mRequest.isShouldCache()) {
                    mDispatcher.putToCache(mRequest.getCacheKey(), entity.response);
                }
            }
            mDispatcher.dispatchResponse(this);
            mDispatcher.removeRunningTask(this);
        }
    }

}
