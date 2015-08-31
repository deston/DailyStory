package com.deston.base.business;

public class Business {
    public static void getDailyStoryList(BusinessListener listener) {
        GetDailyStoryListRequest request = new GetDailyStoryListRequest();
        RequestManager.getInstance().addRequest(request, listener);
    }
}
