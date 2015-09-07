package com.deston.base.business;

public class Business {
    public static void getDailyStoryList(BusinessListener listener) {
        GetDailyStoryListRequest request = new GetDailyStoryListRequest();
        RequestManager.getInstance().addRequest(request, listener);
    }

    public static void getDetailStory(int id, BusinessListener listener) {
        GetDetailStoryRequest request = new GetDetailStoryRequest();
        request.id = id;
        RequestManager.getInstance().addRequest(request, listener);
    }
}
