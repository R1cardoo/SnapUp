package com.snapup.service;

import java.util.List;

public class Usr_train_search_impl {
    private StationService stationService;
    private StationOnLineService stationOnLineService;

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setStationOnLineService(StationOnLineService stationOnLineService) {
        this.stationOnLineService = stationOnLineService;
    }

    public List<String> search_detail(String start, String end) {
        String st_code = stationService.getStationByName(start).getCode();
        String ed_code = stationService.getStationByName(end).getCode();
        List<String> all_train = stationOnLineService.getTrainLine2(st_code, ed_code);
        return all_train;
    }

    public List<String> search(String start, String end) {
        String st_code = stationService.getStationByName(start).getCode();
        String ed_code = stationService.getStationByName(end).getCode();
        List<String> all_train = stationOnLineService.getTrainLine(st_code, ed_code);
        return all_train;
    }

}
