package com.deston.base.network;

import java.io.IOException;

public abstract class RequestTask {
    public abstract ResponseEntity executeRequest(HttpRequest request) throws IOException;

    public abstract void onFinish(ResponseEntity responseEntity);

    public abstract void onCancel();
}
