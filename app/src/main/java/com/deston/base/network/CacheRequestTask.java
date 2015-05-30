package com.deston.base.network;


import com.deston.base.cache.Cache;


public class CacheRequestTask extends RequestTask implements Runnable {
    private Cache mCache;
    private CacheDispatcher mDispatcher;
    public CacheRequestTask(HttpRequest request, Cache cache, CacheDispatcher dispatcher) {
        super(request);
        this.mCache = cache;
        this.mDispatcher = dispatcher;
    }

    @Override
    public ResponseEntity executeRequest(HttpRequest request) {
        NetworkResponse response = (NetworkResponse) mCache.get(request.getCacheKey());
        responseEntity = new ResponseEntity();
        responseEntity.response = response;
        if (response == null) {
            responseEntity.code = -1;
        }
        return responseEntity;
    }

    @Override
    public void onFinish(RequestTask requestTask) {
        mDispatcher.dispatchResponse(this);
    }


}
