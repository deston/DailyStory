package com.deston.base.business;

public class RequestManager {

    private static class SingletonHolder {
        private static RequestManager instance = new RequestManager();
    }

    public RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public void addRequest(BusinessRequest request) {

    }


}
