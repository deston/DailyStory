package com.deston.base.network;

import java.util.Map;

public  class HttpRequest implements Comparable{

    private String mUrl;
    private Map<String, String> mAdditionnalHeaders;
    private HttpListener mListener;
    private boolean mShouldCache;
    private int mMethod = Method.GET;
    private int mTimeOuts = 3000;

    @Override
    public int compareTo(Object another) {
        return 0;
    }

    public static interface Method {
        int GET = 1;
        int POST = 2;
        int PUT = 3;
        int DELETE = 4;
        int HEAD = 5;
        int OPTIONS = 6;
        int TRACE = 7;
    }
    public int getTimeOuts() {
        return mTimeOuts;
    }
    public int getMethod() {
        return mMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public HttpRequest(String url) {
        this(url, null);
    }

    public void setMethod(int method) {
        this.mMethod = method;
    }
    public HttpRequest(String url, Map<String, String> additionHeaders) {
        this.mUrl = url;
        this.mAdditionnalHeaders = additionHeaders;
    }
    public void setListener(HttpListener listener) {
        this.mListener = listener;
    }
    public HttpListener getListener() {
        return mListener;
    }
    public String getCacheKey() {
        return "";
    }

    public void setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

}
