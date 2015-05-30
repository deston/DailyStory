package com.deston.base.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class Dispatcher {
    protected DispatcherHandler mHandler = new DispatcherHandler(Looper.getMainLooper());

    public static final int MSG_DISPATCH_REQUEST = 1;
    public static final int MSG_DISPATCH_RESPONSE = 2;
    public static final int MSG_DISPATCH_CANCEL = 3;

    public void dispatchRequest(HttpRequest request) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_DISPATCH_REQUEST, request));
    }

    public void dispatchResponse(RequestTask requestTask) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_DISPATCH_RESPONSE, requestTask));
    }

    public void dispatchCancel(HttpRequest request) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_DISPATCH_CANCEL, request));
    }

    public abstract void performRequest(HttpRequest request);

    public abstract void performResponse(RequestTask requestTask);

    public abstract void performCancel(HttpRequest request);


    public class DispatcherHandler extends Handler {

        public DispatcherHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DISPATCH_CANCEL:
                    performCancel((HttpRequest) msg.obj);
                    break;
                case MSG_DISPATCH_REQUEST:
                    performRequest((HttpRequest) msg.obj);
                    break;
                case MSG_DISPATCH_RESPONSE:
                    performResponse((RequestTask) msg.obj);
                    break;
            }
        }
    }

}
