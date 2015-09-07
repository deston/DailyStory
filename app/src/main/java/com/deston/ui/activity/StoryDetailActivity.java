package com.deston.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.deston.base.business.Business;
import com.deston.base.business.BusinessListener;
import com.deston.base.business.BusinessResponse;
import com.deston.base.business.GetDetailStoryResponse;
import com.deston.dailylaugh.app.R;
import com.deston.ui.adapter.StoryDetailListAdapter;

public class StoryDetailActivity extends Activity {
    public static final String KEY_STORY_ID = "KEY_STORY_ID";
    private ListView mStoryDetailList;
    private int storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        initVariables();
        initViews();
    }

    private void initVariables() {
        Intent intent = getIntent();
        storyID = intent.getIntExtra(KEY_STORY_ID, -1);
    }


    private void initViews() {
        mStoryDetailList = (ListView) findViewById(R.id.story_detail_listview);
        Business.getDetailStory(storyID, new BusinessListener() {
            @Override
            public void onResponse(BusinessResponse response) {
                if (response instanceof GetDetailStoryResponse) {
                    StoryDetailListAdapter adapter = new StoryDetailListAdapter(StoryDetailActivity.this
                            , ((GetDetailStoryResponse) response).stories);
                    mStoryDetailList.setAdapter(adapter);
                }
            }
        });
    }
}
