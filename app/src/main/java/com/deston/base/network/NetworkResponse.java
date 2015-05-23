package com.deston.base.network;

import java.util.Map;

public class NetworkResponse {

    private final byte []mData;
    private final int mHttpCode;
    private final Map<String, String> mHeaders;
    public NetworkResponse(byte[] data, int code, Map<String, String> headers) {
        this.mData = data;
        this.mHttpCode = code;
        this.mHeaders = headers;
    }
    public byte[] getData() {
        return mData;
    }


}
