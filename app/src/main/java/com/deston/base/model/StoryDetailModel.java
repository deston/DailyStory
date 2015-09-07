package com.deston.base.model;


import java.util.ArrayList;
import java.util.List;

public class StoryDetailModel extends Model {
    public String questionTitle = "";
    public String viewMore = "";
    public int answerNum;
    public List<AnswerModel> answers = new ArrayList<AnswerModel>();
}
