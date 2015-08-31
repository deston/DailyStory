package com.deston.base.network;

import android.util.Log;
import com.deston.base.Constants;
import com.deston.base.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HttpDispatcher extends Dispatcher {
    private BlockingQueue<HttpRequestTask> mRunningQueue = new LinkedBlockingQueue<HttpRequestTask>();
    private ExecutorService mExecutorService;
    private HttpUrlStack mHttpStack;
    private Cache mCache;
    private Map<String, Future> mRunningMap = new HashMap<String, Future>();

    public HttpDispatcher(Cache cache) {
        this.mCache = cache;
        mHttpStack = new HttpUrlStack();
        mExecutorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    @Override
    public void performRequest(HttpRequest request) {
        Log.i(Constants.TAG_COMM, "HttpDispatcher : begin performRequest");
        HttpRequestTask task = new HttpRequestTask(request, this, mHttpStack);
        mRunningQueue.add(task);
        Future future = mExecutorService.submit(task);
        mRunningMap.put(request.getCacheKey(), future);
    }

    @Override
    public void performResponse(RequestTask requestTask) {
        HttpRequest request = requestTask.mRequest;
        mRunningMap.remove(request.getCacheKey());
        if (request != null) {
            HttpListener listener = request.getListener();
            if (listener != null) {
                listener.onResponse(requestTask.mNetworkResponse);
            }
        }
    }


    @Override
    public void performCancel(HttpRequest request) {
        mRunningMap.get(request.getCacheKey()).cancel(true);
        mRunningMap.remove(request.getCacheKey());
        request.getListener().onCancel();
    }


    public void putToCache(String key, NetworkResponse response) {
        mCache.put(key, response);
    }


    public void removeRunningTask(RequestTask task) {
        mRunningQueue.remove(task);
    }

}
