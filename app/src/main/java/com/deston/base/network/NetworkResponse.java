package com.deston.base.network;

import android.util.Log;

import java.io.InputStream;
import java.util.Map;

public class NetworkResponse {

    private InputStream mInputStream;
    private int mHttpCode;
    private Map<String, String> mHeaders;
    public NetworkResponse(InputStream stream, int code, Map<String, String> headers) {
        this.mInputStream = stream;
        this.mHttpCode = code;
        this.mHeaders = headers;
    }

}
