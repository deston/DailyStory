package com.deston.iview;

import com.deston.model.HomeItemModel;
import com.deston.model.StoryModel;

import java.util.List;

public interface IHomeListView extends IView{
    public void initList(List<HomeItemModel> models);
}
