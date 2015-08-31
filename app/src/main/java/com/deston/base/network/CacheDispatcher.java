package com.deston.base.network;


import android.os.Handler;
import android.os.Looper;
import com.deston.base.cache.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheDispatcher extends Dispatcher {
    private Cache mCache;
    private HttpDispatcher mHttpDispatcher;
    private Handler mWorkHandler = new Handler(Looper.getMainLooper());
    private Map<String, Runnable> mRunningMap = new HashMap<String, Runnable>();
    public CacheDispatcher(Cache cache, HttpDispatcher httpDispatcher) {
        this.mCache = cache;
        this.mHttpDispatcher = httpDispatcher;
    }


    @Override
    public void performRequest(HttpRequest request) {
        CacheRequestTask task = new CacheRequestTask(request, mCache, this, mHttpDispatcher);
        mRunningMap.put(request.getCacheKey(), task);
        mWorkHandler.post(task);
    }

    @Override
    public void performResponse(RequestTask requestTask) {
        HttpRequest request = requestTask.mRequest;
        removeRunnigRequest(request, false);
        if (request != null) {
            HttpListener listener = request.getListener();
            if (listener != null) {
                listener.onResponse(requestTask.mNetworkResponse);
            }
        }
    }


    @Override
    public void performCancel(HttpRequest request) {
        removeRunnigRequest(request, true);
    }

    public void removeRunnigRequest(HttpRequest request, boolean cancel) {
        if (cancel) {
            mWorkHandler.removeCallbacks(mRunningMap.get(request.getCacheKey()));
        }
        mRunningMap.remove(request.getCacheKey());
    }
}
