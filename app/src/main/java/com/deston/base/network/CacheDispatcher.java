package com.deston.base.network;


import com.deston.base.cache.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheDispatcher extends Dispatcher {
    private Cache mCache;
    private Map<String, Runnable> runningMap = new HashMap<String, Runnable>();
    public CacheDispatcher(Cache cache) {
        this.mCache = cache;
    }


    @Override
    public void performRequest(HttpRequest request) {
        CacheRequestTask task = new CacheRequestTask(request, mCache, this);
        runningMap.put(request.getCacheKey(), task);
        mHandler.post(task);
    }

    @Override
    public void performResponse(RequestTask requestTask) {
        HttpRequest request = requestTask.mRequest;
        if (request != null) {
            HttpListener listener = request.getListener();
            if (listener != null) {
                listener.onResponse(requestTask.responseEntity);
            }
        }
    }


    @Override
    public void performCancel(HttpRequest request) {
        mHandler.removeCallbacks(runningMap.get(request.getCacheKey()));
    }

}
