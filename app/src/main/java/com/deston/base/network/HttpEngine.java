package com.deston.base.network;

import com.deston.base.cache.Cache;
import com.deston.base.cache.LruMemoryCache;


public class HttpEngine {
    private HttpDispatcher mHttpDispatcher;
    private CacheDispatcher mCacheDispatcher;
    private Cache mCache;
    private static class SingletonHolder {
        private static HttpEngine instance = new HttpEngine();
    }
    public static HttpEngine getInstance() {
        return SingletonHolder.instance;
    }

    public Cache getCache() {
        return mCache;
    }

    private HttpEngine() {
        mCache = new LruMemoryCache();
        mHttpDispatcher = new HttpDispatcher(mCache);
        mCacheDispatcher = new CacheDispatcher(mCache, mHttpDispatcher);
    }

    public HttpDispatcher getHttpDispatcher() {
        return mHttpDispatcher;
    }
    public void execute(HttpRequest request, HttpListener listener, boolean getCache) {
        request.setListener(listener);
        if (getCache) {
            mCacheDispatcher.dispatchRequest(request);
        } else {
            mHttpDispatcher.dispatchRequest(request);
        }
    }

    public void execute(HttpRequest request, HttpListener listener) {
        execute(request, listener, false);
    }

    public void cancel(HttpRequest request) {
        mCacheDispatcher.dispatchCancel(request);
        mHttpDispatcher.dispatchCancel(request);
    }

}
