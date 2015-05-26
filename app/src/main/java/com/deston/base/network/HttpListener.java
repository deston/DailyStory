package com.deston.base.network;

public interface HttpListener {
    public void onResponse(ResponseEntity entity);
    public void onCancel();
}
