package com.deston.base.network;

import java.io.IOException;
import java.util.concurrent.*;

public class HttpDispatcher extends Dispatcher {
    private int mMaxRequest = 64;
    private BlockingQueue<HttpRequestTask> mRunningQueue = new PriorityBlockingQueue<HttpRequestTask>();
    private BlockingQueue<HttpRequestTask> mReadyQueue = new PriorityBlockingQueue<HttpRequestTask>();
    private ExecutorService mExecutorService;
    private HttpUrlStack mHttpStack;
    public HttpEngine mHttpEngine;

    public HttpDispatcher(HttpEngine httpEngine) {
        this.mHttpEngine = httpEngine;
        mHttpStack = new HttpUrlStack();
        mExecutorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    
    @Override
    public void enqueue(final HttpRequest request) {
        HttpRequestTask task = new HttpRequestTask(request, this, mHttpStack);
        if (mRunningQueue.size() < mMaxRequest) {
            mRunningQueue.add(task);
            mExecutorService.execute(task);
        } else {
            mReadyQueue.add(task);
        }
    }
    public void putToCache(String key, NetworkResponse response) {

    }
    public void removeRunningTask(RequestTask task) {
        mRunningQueue.remove(task);
    }

    public void promoteTask() {
        if (mRunningQueue.size() < mMaxRequest && !mReadyQueue.isEmpty()) {
            HttpRequestTask task = mReadyQueue.poll();
            enqueue(task.getRequest());
        }
    }
}
