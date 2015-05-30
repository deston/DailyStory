package com.deston.base.network;


public abstract class RequestTask implements Runnable{
    protected HttpRequest mRequest;
    protected ResponseEntity responseEntity;
    public RequestTask(HttpRequest request) {
        this.mRequest = request;
    }
    public abstract ResponseEntity executeRequest(HttpRequest request);

    public abstract void onFinish(RequestTask requestTask);


    @Override
    public void run() {
        try {
            responseEntity = executeRequest(mRequest);
        } finally {
            onFinish(this);
        }
    }

}
