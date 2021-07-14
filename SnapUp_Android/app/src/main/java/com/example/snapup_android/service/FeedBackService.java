package com.example.snapup_android.service;

import com.example.snapup_android.pojo.FeedBack;

import java.util.List;

public interface FeedBackService {
    public boolean createFeedBack(FeedBack feedBack);
    public List<FeedBack> getAllFeedBack();
    public boolean deleteFeedBack(FeedBack feedBack);
}
