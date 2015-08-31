package com.deston.base.network;


import java.util.Map;

public class NetworkResponse<T> {

    public T response;
    public int httpCode;
    public Map<String, String> headers;
    public NetworkResponse(T response, int code, Map<String, String> headers) {
        this.response = response;
        this.httpCode = code;
        this.headers = headers;
    }

}
