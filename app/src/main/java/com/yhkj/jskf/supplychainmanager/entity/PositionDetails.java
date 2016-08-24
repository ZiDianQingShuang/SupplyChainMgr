package com.yhkj.jskf.supplychainmanager.entity;

import com.yhkj.jskf.supplychainmanager.utils.GsonUtils;

/**
 * Created by duanhongbo on 2016/4/28.
 */
public class PositionDetails {
    private  int positionId;
    private  Double	longitude;//经度
    private  Double    latitude;//纬度
    private  Double    altitude;//海拔
    private  int     positionType;//定位类型
    private  String     positionTime;//定位时间

    public int getPositionType() {
        return positionType;
    }

    public void setPositionType(int positionType) {
        this.positionType = positionType;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }


    public String getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
    }

    @Override
    public String toString() {
        String jsonString = GsonUtils.toJson(this);
        return jsonString;
    }
}
