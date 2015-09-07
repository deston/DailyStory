package com.deston.iview;

import com.deston.base.model.HomeItemModel;

import java.util.List;

public interface IHomeListView extends IView{
    public void initList(List<HomeItemModel> models);
    public void stopProgress();
    public void startProgress();
}
