package com.example.snapup_android.service;

import com.example.snapup_android.dao.FeedBackMapper;
import com.example.snapup_android.pojo.FeedBack;

import java.util.List;

public class FeedBackServiceImpl implements FeedBackService{
    private FeedBackMapper feedBackMapper;

    public void setFeedBackMapper(FeedBackMapper feedBackMapper) {
        this.feedBackMapper = feedBackMapper;
    }

    public boolean createFeedBack(FeedBack feedBack) {
        return feedBackMapper.createFeedBack(feedBack);
    }

    public List<FeedBack> getAllFeedBack() {
        return feedBackMapper.getAllFeedBack();
    }

    public boolean deleteFeedBack(FeedBack feedBack) {
        return feedBackMapper.deleteFeedBack(feedBack);
    }
}
