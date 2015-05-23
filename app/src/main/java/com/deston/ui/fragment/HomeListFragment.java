package com.deston.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.deston.dailylaugh.app.R;
import com.deston.iview.IHomeListView;
import com.deston.base.model.HomeItemModel;
import com.deston.presenter.HomeListPresenterImpl;
import com.deston.presenter.IHomeListPresenter;
import com.deston.ui.adapter.HomeListAdapter;

import java.util.List;

public class HomeListFragment extends BaseFragment implements IHomeListView{
    private ListView mListView;
    private HomeListAdapter mAdapter;
    private IHomeListPresenter mHomeListPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static HomeListFragment newInstance() {
        return new HomeListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeListPresenter = new HomeListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list, null);
        initViews(view);
        mHomeListPresenter.loadHomeList();
        return view;
    }

    private void initViews(View view) {
        mListView = (ListView) view.findViewById(R.id.home_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_list_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3003);
            }
        });
    }

    @Override
    public void initList(List<HomeItemModel> models) {
        mAdapter = new HomeListAdapter(getActivity(), models);
        mListView.setAdapter(mAdapter);
    }

}
