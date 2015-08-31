package com.deston.base.network;


public abstract class RequestTask implements Runnable{
    protected HttpRequest mRequest;
    protected NetworkResponse mNetworkResponse;
    public RequestTask(HttpRequest request) {
        this.mRequest = request;
    }
    public abstract NetworkResponse executeRequest(HttpRequest request);

    public abstract void onFinish();


    @Override
    public void run() {
        try {
            mNetworkResponse = executeRequest(mRequest);
        } finally {
            onFinish();
        }
    }

}
