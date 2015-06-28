package com.deston.base.network;

import android.util.Log;
import com.deston.base.Constants;
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
            Log.i(Constants.TAG_COMM, "excute getCache = true, begin CacheDispatch");
            mCacheDispatcher.dispatchRequest(request);
        } else {
            Log.i(Constants.TAG_COMM, "excute getCache = false, begin HttpDispatch");
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
