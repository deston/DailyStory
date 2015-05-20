package com.deston.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.deston.dailylaugh.app.R;
import com.deston.iview.IHomeListView;
import com.deston.model.StoryModel;
import com.deston.presenter.HomeListPresenterImpl;
import com.deston.presenter.IHomeListPresenter;
import com.deston.view.adapter.HomeListAdapter;

import java.util.List;

public class HomeListFragment extends BaseFragment implements IHomeListView{
    private ListView mListView;
    private HomeListAdapter mAdapter;
    private IHomeListPresenter mHomeListPresenter;
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
    }

    @Override
    public void initList(List<StoryModel> storyModelList) {
        mAdapter = new HomeListAdapter(storyModelList);
        mListView.setAdapter(mAdapter);
    }

}
