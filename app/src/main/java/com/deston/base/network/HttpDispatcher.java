package com.deston.base.network;

import com.deston.base.cache.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HttpDispatcher extends Dispatcher {
    private int mMaxRequest = 64;
    private BlockingQueue<HttpRequestTask> mRunningQueue = new PriorityBlockingQueue<HttpRequestTask>();
    private BlockingQueue<HttpRequestTask> mReadyQueue = new PriorityBlockingQueue<HttpRequestTask>();
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
        HttpRequestTask task = new HttpRequestTask(request, this, mHttpStack);
        if (mRunningQueue.size() < mMaxRequest) {
            mRunningQueue.add(task);
            Future future = mExecutorService.submit(task);
            mRunningMap.put(request.getCacheKey(), future);
        } else {
            mReadyQueue.add(task);
        }
    }

    @Override
    public void performResponse(RequestTask requestTask) {
        HttpRequest request = requestTask.mRequest;
        mRunningMap.remove(request.getCacheKey());
        if (request != null) {
            HttpListener listener = request.getListener();
            if (listener != null) {
                listener.onResponse(requestTask.responseEntity);
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

    public void promoteTask() {
        if (mRunningQueue.size() < mMaxRequest && !mReadyQueue.isEmpty()) {
            HttpRequestTask task = mReadyQueue.poll();
            dispatchRequest(task.getRequest());
        }
    }
}
