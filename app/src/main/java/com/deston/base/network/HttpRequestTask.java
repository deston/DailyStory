package com.deston.base.network;


import java.io.IOException;

public class HttpRequestTask extends RequestTask implements Runnable {
    private HttpRequest mRequest;
    private HttpDispatcher mDispatcher;
    private HttpUrlStack mHttpStack;

    public HttpRequest getRequest() {
        return mRequest;
    }

    public HttpRequestTask(HttpRequest request, HttpDispatcher httpDispatcher, HttpUrlStack httpUrlStack) {
        this.mRequest = request;
        this.mDispatcher = httpDispatcher;
        this.mHttpStack = httpUrlStack;
    }

    @Override
    public void run() {
        ResponseEntity entity = null;
        try {
            entity = executeRequest(mRequest);
        } catch (IOException e) {
            e.printStackTrace();
            entity = new ResponseEntity();
            entity.code = -1;
        } finally {
            onFinish(entity);
        }

    }

    @Override
    public ResponseEntity executeRequest(HttpRequest request) throws IOException {
        NetworkResponse response = mHttpStack.performRequest(mRequest);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.response = response;
        return responseEntity;
    }

    @Override
    public void onFinish(ResponseEntity entity) {
        if (entity != null) {
            if (entity.code != -1) {
                if (mRequest.isShouldCache()) {
                    mDispatcher.putToCache(mRequest.getCacheKey(), entity.response);
                }
            }
            mDispatcher.dispatchResponse(mRequest, entity);
            mDispatcher.removeRunningTask(this);
            mDispatcher.promoteTask();
        }
    }

    @Override
    public void onCancel() {

    }
}
