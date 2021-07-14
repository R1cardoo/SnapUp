package com.snapup.dao;

import com.snapup.pojo.FeedBack;

import java.util.List;

public interface FeedBackMapper {
    //创建一条反馈信息：
    public boolean createFeedBack(FeedBack feedBack);
    //查询所有的反馈信息：
    public List<FeedBack> getAllFeedBack();
    //删除一条反馈：
    public boolean deleteFeedBack(FeedBack feedBack);
}
