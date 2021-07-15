package com.snapup.service;

import com.snapup.dao.FeedBackMapper;
import com.snapup.pojo.FeedBack;

import java.util.List;

public interface FeedBackService {
    public boolean createFeedBack(FeedBack feedBack);

    public List<FeedBack> getAllFeedBack();

    public boolean deleteFeedBack(FeedBack feedBack);

    public void setFeedBackMapper(FeedBackMapper feedBackMapper);
}

