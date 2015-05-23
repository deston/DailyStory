package com.deston.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.deston.dailylaugh.app.R;
import com.deston.base.model.StoryModel;

import java.util.List;

public class StoryListAdapter extends BaseAdapter {
    private List<StoryModel> mStoryModels;
    private LayoutInflater mInflater;

    public StoryListAdapter(Context context, List<StoryModel> models) {
        this.mStoryModels = models;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mStoryModels != null) {
            return mStoryModels.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mStoryModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.story_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.answerTv = (TextView) convertView.findViewById(R.id.story_item_answer_tv);
            viewHolder.questionTv = (TextView) convertView.findViewById(R.id.story_item_question_tv);
            viewHolder.headIconIv = (ImageView) convertView.findViewById(R.id.story_item_head_iv);
            viewHolder.remarkTv = (TextView) convertView.findViewById(R.id.story_item_remark_tv);
            viewHolder.authorTv = (TextView) convertView.findViewById(R.id.story_item_name_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StoryModel item = (StoryModel) getItem(position);
        viewHolder.answerTv.setText(item.answer);
        viewHolder.authorTv.setText(item.author);
        viewHolder.remarkTv.setText(item.remark);
        viewHolder.questionTv.setText(item.question);
        return convertView;
    }

    public class ViewHolder {
        public TextView questionTv;
        public ImageView headIconIv;
        public TextView authorTv;
        public TextView answerTv;
        public TextView remarkTv;
    }
}
