package com.deston.base.business;

import com.deston.base.model.StoryDetailModel;

import java.util.ArrayList;
import java.util.List;

public class GetDetailStoryResponse extends BusinessResponse{
    public int questionNum;
    public List<StoryDetailModel> stories = new ArrayList<StoryDetailModel>();

}
