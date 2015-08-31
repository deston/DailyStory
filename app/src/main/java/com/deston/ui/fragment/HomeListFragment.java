package com.deston.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.deston.base.business.BusinessListener;
import com.deston.base.business.BusinessResponse;
import com.deston.base.business.Business;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.NetworkResponse;
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
    public static final String TAG = "HomeListFragment";
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
        final String url = "https://api.douban.com/v2/book/1220562";
        mListView = (ListView) view.findViewById(R.id.home_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_list_refresh_layout);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HttpRequest request = new HttpRequest(String.class, url);
                HttpEngine.getInstance().execute(request, new HttpListener() {
                    @Override
                    public void onResponse(NetworkResponse entity) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "get daily list begin");
                Business.getDailyStoryList(new BusinessListener() {
                    @Override
                    public void onResponse(BusinessResponse response) {
                        Log.i("TAG", "get dayly list end");
                    }
                });
            }
        });
    }

    @Override
    public void initList(List<HomeItemModel> models) {
        mAdapter = new HomeListAdapter(getActivity(), models);
        mListView.setAdapter(mAdapter);
    }

}
