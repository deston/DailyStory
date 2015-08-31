package com.deston.base.network;

public interface HttpListener {
    public void onResponse(NetworkResponse response);
    public void onCancel();
}
