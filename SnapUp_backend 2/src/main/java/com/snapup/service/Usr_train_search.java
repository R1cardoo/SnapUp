package com.snapup.service;

import java.util.List;

public interface Usr_train_search {
    /*
     * 目的：根据用户选择起点，终点，返回满足条件的车次(沿线也算)
     * 输入：用户选择的起始站，终点站
     * 输出：满足条件的车次信息
     */
    public List<String> search_detail(String start, String end);

    /*
     * 目的：根据用户选择起点，终点，返回满足条件的车次(必须严格是起点、终点)
     * 输入：用户选择的起始站，终点站
     * 输出：满足条件的车次信息
     */
    public List<String> search(String start, String end);
}
