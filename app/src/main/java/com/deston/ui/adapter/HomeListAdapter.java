package com.deston.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.NetworkResponse;
import com.deston.dailylaugh.app.R;
import com.deston.base.model.HomeItemModel;

import java.util.List;


public class HomeListAdapter extends BaseAdapter {
    private List<HomeItemModel> mItemModels;
    private LayoutInflater mInflater;

    public HomeListAdapter(Context context, List<HomeItemModel> models) {
        this.mItemModels = models;
        this.mInflater = LayoutInflater.from(context);
    }
    public void setItems(List<HomeItemModel> items) {
        this.mItemModels = items;
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
            viewHolder.diplayDateTv = (TextView) convertView.findViewById(R.id.home_item_display_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeItemModel itemModel = getItem(position);
        viewHolder.titleTv.setText(itemModel.title);
        viewHolder.diplayDateTv.setText(itemModel.displayDate);
        final ImageView imageView = viewHolder.imageView;
        HttpRequest httpRequest = new HttpRequest(Bitmap.class, itemModel.imageUrl);
        httpRequest.setShouldCache(true);
        HttpEngine.getInstance().execute(httpRequest, new HttpListener() {
            @Override
            public void onResponse(NetworkResponse response) {
                if (response.response instanceof Bitmap) {
                    imageView.setImageBitmap((Bitmap) response.response);
                }
            }

            @Override
            public void onCancel() {

            }
        }, true);
        return convertView;
    }

    public class ViewHolder {
        private TextView titleTv;
        private ImageView imageView;
        private TextView diplayDateTv;
    }
}
