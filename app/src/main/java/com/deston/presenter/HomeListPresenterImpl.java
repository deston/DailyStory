package com.deston.presenter;

import com.deston.base.business.Business;
import com.deston.base.business.BusinessListener;
import com.deston.base.business.BusinessResponse;
import com.deston.base.business.GetDailyStoryListResponse;
import com.deston.iview.IHomeListView;
import com.deston.base.model.HomeItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeListPresenterImpl implements IHomeListPresenter {
    private IHomeListView mHomeListView;

    public HomeListPresenterImpl(IHomeListView homeListView) {
        this.mHomeListView = homeListView;
    }

    @Override
    public void loadHomeList() {
        mHomeListView.startProgress();
        Business.getDailyStoryList(new BusinessListener() {
            @Override
            public void onResponse(BusinessResponse response) {
                mHomeListView.stopProgress();
                if (response instanceof GetDailyStoryListResponse) {
                    mHomeListView.initList(((GetDailyStoryListResponse) response).homeItemModels);
                }
            }
        });

    }

    private void initTestData(List<HomeItemModel> models) {
        HomeItemModel model = new HomeItemModel();
        model.title = "瞎扯";
        HomeItemModel model1 = new HomeItemModel();
        model1.title = "abd";
        models.add(model);
        models.add(model1);
    }
}
