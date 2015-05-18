package com.deston.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import com.deston.model.StoryModel;

import java.util.List;

public class HomeListAdapter extends BaseAdapter{
    private List<StoryModel> mStoryModels;
    public HomeListAdapter(List<StoryModel> models) {
        this.mStoryModels = models;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
