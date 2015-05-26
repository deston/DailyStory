package com.deston.base.network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

public abstract class Dispatcher {
    public abstract void enqueue(HttpRequest request);

    private NetworkResponse response;


    private Handler mResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HttpListener listener = (HttpListener) msg.obj;
            if (listener != null) {
                listener.onResponse(response);
            }
        }
    };

    protected void dispatchSuccess(HttpRequest request, NetworkResponse response) {
        HttpListener listener = request.getListener();
        Message message = Message.obtain();
        message.obj = listener;
        this.response = response;
        mResponseHandler.sendMessage(message);
    }

}
