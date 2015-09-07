package com.deston.base.business;

import com.deston.base.model.HomeItemModel;

import java.util.ArrayList;
import java.util.List;

public class GetDailyStoryListResponse extends BusinessResponse{
    public int number;
    public List<HomeItemModel> homeItemModels = new ArrayList<HomeItemModel>();
}
