package com.deston.base.business;

import com.deston.base.Constants;

public class GetDailyStoryRequest extends BusinessRequest {
    public GetDailyStoryRequest() {
        this.businessCode = Constants.BusinessCode.GET_NEW_DAILY_STORY;
    }

}
