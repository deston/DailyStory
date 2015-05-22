package com.deston.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.deston.dailylaugh.app.R;
import com.deston.model.HomeItemModel;

import java.util.List;


public class HomeListAdapter extends BaseAdapter{
    private List<HomeItemModel> mItemModels;
    private LayoutInflater mInflater;
    public HomeListAdapter(Context  context, List<HomeItemModel> models) {
        this.mItemModels = models;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mItemModels.size();
    }

    @Override
    public HomeItemModel getItem(int position) {
        return mItemModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.titleTv = (TextView) convertView.findViewById(R.id.home_item_title);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.home_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeItemModel itemModel = getItem(position);
        viewHolder.titleTv.setText(itemModel.title);
        return convertView;
    }
    public class ViewHolder {
        private TextView titleTv;
        private ImageView imageView;
    }
}
