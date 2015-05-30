package com.deston.base.network;


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
            response = mHttpStack.performRequest(mRequest);
        } catch (IOException e) {
            e.printStackTrace();
            responseEntity.code = -1;
        }
        responseEntity.response = response;
        return responseEntity;
    }

    @Override
    public void onFinish(RequestTask requestTask) {
        ResponseEntity entity = requestTask.responseEntity;
        if (entity != null) {
            if (entity.code != -1) {
                if (mRequest.isShouldCache()) {
                    mDispatcher.putToCache(mRequest.getCacheKey(), entity.response);
                }
            }
            mDispatcher.dispatchResponse(this);
            mDispatcher.removeRunningTask(this);
            mDispatcher.promoteTask();
        }
    }

}
