package com.deston.base.network;


public abstract class RequestTask implements Runnable{
    protected HttpRequest mRequest;
    protected ResponseEntity mResponseEntity;
    public RequestTask(HttpRequest request) {
        this.mRequest = request;
    }
    public abstract ResponseEntity executeRequest(HttpRequest request);

    public abstract void onFinish();


    @Override
    public void run() {
        try {
            mResponseEntity = executeRequest(mRequest);
        } finally {
            onFinish();
        }
    }

}
