package com.deston.base.business;

import com.deston.base.Constants;

public class GetDailyStoryListRequest extends BusinessRequest {
    public GetDailyStoryListRequest() {
        this.businessCode = Constants.BusinessCode.GET_DAILY_STORY_LIST;
    }

}
