package com.deston.base.network;

import java.util.Map;

public class HttpJsonRequest extends HttpRequest{

    public HttpJsonRequest(String url) {
        super(url);
    }

    public HttpJsonRequest(String url, Map<String, String> additionHeaders) {
        super(url, additionHeaders);
    }

    public void parse() {

    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
