package com.deston.presenter;

import android.graphics.Path;
import com.deston.iview.IHomeListView;

public class HomeListPresenterImpl implements IHomeListPresenter {
    private IHomeListView mHomeListView;
    public HomeListPresenterImpl(IHomeListView homeListView) {
        this.mHomeListView = homeListView;
    }
    @Override
    public void loadHomeList() {

    }

}
