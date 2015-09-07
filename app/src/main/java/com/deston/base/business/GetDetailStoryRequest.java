package com.deston.base.business;

import com.deston.base.Constants;

public class GetDetailStoryRequest extends BusinessRequest{
    public int id;
    public GetDetailStoryRequest() {
        this.businessCode = Constants.BusinessCode.GET_DETAIL_STORY;
    }
}
