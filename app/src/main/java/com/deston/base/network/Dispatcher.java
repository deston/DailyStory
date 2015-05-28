package com.deston.base.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

public abstract class Dispatcher {
    public abstract void enqueue(HttpRequest request);

    private Handler mResponseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkHolder holder = (NetworkHolder) msg.obj;
            HttpRequest request = holder.request;
            ResponseEntity responseEntity = holder.responseEntity;
            if (request != null && request.getListener() != null) {
                request.getListener().onResponse(responseEntity);
            }
        }
    };

    protected void dispatchResponse(HttpRequest request, ResponseEntity responseEntity) {
        Message message = Message.obtain();
        NetworkHolder holder = new NetworkHolder();
        holder.request = request;
        holder.responseEntity = responseEntity;
        message.obj = holder;
        mResponseHandler.sendMessage(message);
    }

    private class NetworkHolder {
        HttpRequest request;
        ResponseEntity responseEntity;
    }
}
