package com.deston.base.network;


import com.deston.base.cache.Cache;


public class CacheRequestTask extends RequestTask implements Runnable {
    private Cache mCache;
    private CacheDispatcher mCacheDispathcer;
    private HttpDispatcher mHttpDispatcher;
    public CacheRequestTask(HttpRequest request, Cache cache, CacheDispatcher cacheDispatcher, HttpDispatcher httpDispatcher) {
        super(request);
        this.mCache = cache;
        this.mCacheDispathcer = cacheDispatcher;
        this.mHttpDispatcher = httpDispatcher;
    }

    @Override
    public NetworkResponse executeRequest(HttpRequest request) {
        NetworkResponse response = (NetworkResponse) mCache.get(request.getCacheKey());
        return response;
    }

    @Override
    public void onFinish() {
        if (mNetworkResponse == null) {
            mCacheDispathcer.removeRunnigRequest(mRequest, false);
            mHttpDispatcher.dispatchRequest(mRequest);
        } else {
            mCacheDispathcer.dispatchResponse(this);
        }
    }


}
