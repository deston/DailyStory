package com.deston.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.deston.base.model.AnswerModel;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.NetworkResponse;
import com.deston.dailylaugh.app.R;
import com.deston.base.model.StoryDetailModel;

import java.util.List;

public class StoryDetailListAdapter extends BaseAdapter {
    private List<StoryDetailModel> mStoryModels;
    private LayoutInflater mInflater;

    public StoryDetailListAdapter(Context context, List<StoryDetailModel> models) {
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
    public StoryDetailModel getItem(int position) {
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
            viewHolder.questionTv = (TextView) convertView.findViewById(R.id.story_item_question_tv);
            viewHolder.avatarIv = (ImageView) convertView.findViewById(R.id.story_item_avatar_iv);
            viewHolder.authorTv = (TextView) convertView.findViewById(R.id.story_item_author_tv);
            viewHolder.answerTv = (TextView) convertView.findViewById(R.id.story_item_answer_tv);
            viewHolder.bioTv = (TextView) convertView.findViewById(R.id.story_item_bio_tv);
            viewHolder.viewmoreBtn = (Button) convertView.findViewById(R.id.story_item_viewmore_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StoryDetailModel storyDetailModel = getItem(position);
        for (int i = 0; i < storyDetailModel.answers.size(); i++) {
            AnswerModel answerModel = storyDetailModel.answers.get(i);
            HttpRequest httpRequest = new HttpRequest(Bitmap.class, answerModel.avatar);
            final ImageView imageView =  viewHolder.avatarIv;
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
            viewHolder.answerTv.setText(answerModel.answer);
            viewHolder.authorTv.setText(answerModel.author);
            viewHolder.bioTv.setText(answerModel.bio);
        }
        viewHolder.questionTv.setText(storyDetailModel.questionTitle);
        return convertView;
    }

    public class ViewHolder {
        TextView questionTv;
        ImageView avatarIv;
        TextView authorTv;
        TextView answerTv;
        TextView bioTv;
        Button viewmoreBtn;
    }
}
