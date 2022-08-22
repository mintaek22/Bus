package com.example.bus;

public class data {
    String st_id;
    String direction;
    String stationNm;

    public data(String st_id, String direction, String stationNm) {
        this.st_id = st_id;
        this.direction = direction;
        this.stationNm = stationNm;
    }

    public String getSt_id() {
        return st_id;
    }

    public String getDirection() {
        return direction;
    }

    public String getStationNm() { return stationNm; }
}
