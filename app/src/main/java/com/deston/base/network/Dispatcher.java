package com.deston.base.network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public abstract class Dispatcher {
    public abstract void enqueue(HttpRequest request);

    public abstract NetworkResponse execute(HttpRequest request) throws IOException;
    private NetworkResponse response;
    public abstract void finish(HttpRequest request);
    private Handler mResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HttpListener listener = (HttpListener)msg.obj;
            if (listener != null) {
                listener.onResponse(response);
            }
        }
    };
    protected void onSuccess(HttpRequest request, NetworkResponse response) {
        HttpListener listener = request.getListener();
        Message message = Message.obtain();
        message.obj = listener;
        this.response = response;
        mResponseHandler.sendMessage(message);

//        if (listener != null) {
//            listener.onResponse(response);
//        }
    }

    protected void onFailed(HttpRequest request) {
        HttpListener listener = request.getListener();
        if (listener != null) {
            listener.onResponse(null);
        }
    }

    protected void onCancel(HttpRequest request) {
        HttpListener listener = request.getListener();
        if (listener != null) {
            listener.onCancel();
        }
    }
}
