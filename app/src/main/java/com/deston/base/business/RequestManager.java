package com.deston.base.business;

import android.util.Log;
import com.deston.base.Constants;
import com.deston.base.model.AnswerModel;
import com.deston.base.model.HomeItemModel;
import com.deston.base.model.StoryDetailModel;
import com.deston.base.network.HttpEngine;
import com.deston.base.network.HttpListener;
import com.deston.base.network.HttpRequest;
import com.deston.base.network.NetworkResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestManager {
    public static final String TAG = "RequestManager";

    private static class SingletonHolder {
        private static RequestManager instance = new RequestManager();
    }

    public static RequestManager getInstance() {
        return SingletonHolder.instance;
    }

    public void addRequest(final BusinessRequest businessRequest, final BusinessListener listener) {
        String url = getUrlByCode(businessRequest);
        HttpRequest request = new HttpRequest(String.class, url);
        HttpEngine.getInstance().execute(request, new HttpListener() {
            @Override
            public void onResponse(NetworkResponse response) {
                BusinessResponse businessResponse = parseResponse(response, businessRequest.businessCode);
                listener.onResponse(businessResponse);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private String getUrlByCode(BusinessRequest request) {
        String url = "";
        switch (request.businessCode) {
            case Constants.BusinessCode.GET_DAILY_STORY_LIST:
                url = "http://104.128.85.9:8001/api/gethomelist";
                break;
            case Constants.BusinessCode.GET_DETAIL_STORY:
                int id = ((GetDetailStoryRequest) request).id;
                url = "http://104.128.85.9:8001/api/getstorydetail?id=" + id;
            default:
                break;
        }
        return url;
    }

    private BusinessResponse parseResponse(NetworkResponse response, int businessCode) {
        BusinessResponse businessResponse = null;
        if (response.httpCode == 200) {
            try {
                JSONObject jsonObject = new JSONObject(response.response.toString());
                Log.i(TAG, "response = " + jsonObject);
                switch (businessCode) {
                    case Constants.BusinessCode.GET_DAILY_STORY_LIST:
                            businessResponse = new GetDailyStoryListResponse();
                            GetDailyStoryListResponse getDailyStoryListResponse = (GetDailyStoryListResponse) businessResponse;
                            getDailyStoryListResponse.number = jsonObject.getInt("number");
                            JSONArray jsonArray = jsonObject.getJSONArray("stories");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                HomeItemModel homeItemModel = new HomeItemModel();
                                homeItemModel.id = jsonItem.getInt("id");
                                homeItemModel.title = jsonItem.getString("title");
                                homeItemModel.displayDate = jsonItem.getString("display_date");
                                homeItemModel.imageUrl = jsonItem.getString("images");
                                homeItemModel.timestamp = jsonItem.getLong("timestamp");
                                getDailyStoryListResponse.homeItemModels.add(homeItemModel);
                            }
                            Log.i(Constants.TAG_COMM, response.response.toString());
                        break;

                    case Constants.BusinessCode.GET_DETAIL_STORY:
                            businessResponse = new GetDetailStoryResponse();
                            GetDetailStoryResponse getDetailStoryResponse = (GetDetailStoryResponse) businessResponse;
                            getDetailStoryResponse.questionNum = jsonObject.getInt("question_num");
                            jsonArray = jsonObject.getJSONArray("stories");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonItem = jsonArray.getJSONObject(i);
                                StoryDetailModel storyDetailModel = new StoryDetailModel();
                                storyDetailModel.answerNum = jsonItem.getInt("answer_num");
                                storyDetailModel.questionTitle = jsonItem.getString("question_title");
                                storyDetailModel.viewMore = jsonItem.getString("viewmore");
                                JSONArray answers = jsonItem.getJSONArray("answers");
                                for (int j = 0; j < answers.length(); j++) {
                                    AnswerModel answerModel = new AnswerModel();
                                    JSONObject answerJSON = answers.getJSONObject(j);
                                    answerModel.answer = answerJSON.getString("answer");
                                    answerModel.author = answerJSON.getString("author");
                                    answerModel.bio = answerJSON.getString("bio");
                                    answerModel.avatar = answerJSON.getString("avatar");
                                    storyDetailModel.answers.add(answerModel);
                                }
                                getDetailStoryResponse.stories.add(storyDetailModel);
                            }
                        }
                if (businessResponse != null) {
                    businessResponse.result = jsonObject.getInt("ret");
                    businessResponse.error = jsonObject.getString("err");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return businessResponse;
    }
}
