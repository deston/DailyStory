package com.deston.base.network;


import android.os.Handler;
import android.os.Message;

public class CacheDispatcher extends Dispatcher {
    private HttpEngine mHttpEngine;

    public CacheDispatcher(HttpEngine httpEngine) {
        this.mHttpEngine = httpEngine;
    }

    private Handler mWorkerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj instanceof HttpRequest) {
                HttpRequest request = (HttpRequest) msg.obj;
                if (request.isCanceled()) {
                    onCancel(request);
                    return;
                }
                NetworkResponse response = execute(request);
                if (response != null) {
                    onSuccess(request, response);
                } else {
                    mHttpEngine.getHttpDispatcher().enqueue(request);
                }
            }
        }
    };

    @Override
    public void enqueue(HttpRequest request) {
        Message msg = Message.obtain();
        msg.obj = request;
        mWorkerHandler.sendMessage(msg);
    }

    @Override
    public NetworkResponse execute(HttpRequest request) {
        NetworkResponse response = (NetworkResponse) mHttpEngine.getCache().get(request.getCacheKey());
        return response;
    }


    @Override
    public void finish(HttpRequest request) {

    }
}
