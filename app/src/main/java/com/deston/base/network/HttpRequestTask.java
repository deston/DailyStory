package com.deston.base.network;

import android.app.DownloadManager;

import java.io.IOException;

public class HttpRequestTask implements Runnable {
    private HttpRequest mRequest;
    private HttpDispatcher mDispatcher;

    public HttpRequestTask(HttpRequest request, HttpDispatcher httpDispatcher) {
        this.mRequest = request;
    }

    @Override
    public void run() {
        if (mRequest.isCanceled()) {
            mDispatcher.finish(mRequest);
            mDispatcher.onCancel(mRequest);
            return;
        }
        try {
            NetworkResponse response = mDispatcher.execute(mRequest);
            if (mRequest.isShouldCache()) {
                mHttpEngine.getCache().put(mRequest.getCacheKey(), response);
            }
            mDispatcher.onSuccess(mRequest, response);
        } catch (IOException e) {
            mDispatcher.onFailed(mRequest);
        } finally {
            mDispatcher.finish(mRequest);
        }
    }
}
