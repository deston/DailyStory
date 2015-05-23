package com.deston.base.network;

import java.io.IOException;
import java.util.concurrent.*;

public class HttpDispatcher extends Dispatcher {
    private int mMaxRequest = 64;
    private BlockingQueue<HttpRequest> mRunningQueue = new PriorityBlockingQueue<HttpRequest>();
    private BlockingQueue<HttpRequest> mReadyQueue = new PriorityBlockingQueue<HttpRequest>();
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
        if (mRunningQueue.size() < mMaxRequest) {
            mRunningQueue.add(request);
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    if (request.isCanceled()) {
                        finish(request);
                        onCancel(request);
                        return;
                    }
                    try {
                        NetworkResponse response = execute(request);
                        if (request.isShouldCache()) {
                            mHttpEngine.getCache().put(request.getCacheKey(), response);
                        }
                        onSuccess(request, response);
                    } catch (IOException e) {
                        onFailed(request);
                    } finally {
                        finish(request);
                    }
                }
            });
        } else {
            mReadyQueue.add(request);
        }
    }



    @Override
    public NetworkResponse execute(HttpRequest request) throws IOException {
        NetworkResponse response = null;
        if (mHttpStack != null) {
            response = mHttpStack.performRequest(request);
        }
        return response;
    }


    @Override
    public void finish(final HttpRequest request) {
        mRunningQueue.remove(request);
        promoteRequest();
    }

    private void promoteRequest() {
        if (mRunningQueue.size() < mMaxRequest && !mReadyQueue.isEmpty()) {
            HttpRequest request = mReadyQueue.poll();
            enqueue(request);
        }
    }

}
