package com.scut.originsystem.entity;

import javax.validation.constraints.Size;
import java.util.List;

public class VideoDevice {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String device_code;
    @Size(max = 100,message = "最大长度为100")
    private String device_name;
    @Size(max = 100,message = "最大长度为100")
    private String device_description;
    @Size(max = 100,message = "最大长度为100")
    private String device_location;
    @Size(max = 100,message = "最大长度为100")
    private String device_gis;
    @Size(max = 100,message = "最大长度为100")
    private String device_brand;
    @Size(max = 100,message = "最大长度为100")
    private String device_model;
    @Size(max = 100,message = "最大长度为100")
    private String purchase_date;
    @Size(max = 100,message = "最大长度为100")
    private String login_date;

    private int merchant_id;
    private int online;
    private String drop_date;
    private int i_id;

    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }


    private List<VideoInfo> videoInfoList;

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getDrop_date() {
        return drop_date;
    }

    public void setDrop_date(String drop_date) {
        this.drop_date = drop_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_description() {
        return device_description;
    }

    public void setDevice_description(String device_description) {
        this.device_description = device_description;
    }

    public String getDevice_location() {
        return device_location;
    }

    public void setDevice_location(String device_location) {
        this.device_location = device_location;
    }

    public String getDevice_gis() {
        return device_gis;
    }

    public void setDevice_gis(String device_gis) {
        this.device_gis = device_gis;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getLogin_date() {
        return login_date;
    }

    public void setLogin_date(String login_date) {
        this.login_date = login_date;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public List<VideoInfo> getVideoInfoList() {
        return videoInfoList;
    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    @Override
    public String toString() {
        return "VideoDevice{" +
                "id=" + id +
                ", device_code='" + device_code + '\'' +
                ", device_name='" + device_name + '\'' +
                ", device_description='" + device_description + '\'' +
                ", device_location='" + device_location + '\'' +
                ", device_gis='" + device_gis + '\'' +
                ", device_brand='" + device_brand + '\'' +
                ", device_model='" + device_model + '\'' +
                ", purchase_date='" + purchase_date + '\'' +
                ", login_date='" + login_date + '\'' +
                ", merchant_id=" + merchant_id +
                ", online=" + online +
                ", drop_date='" + drop_date + '\'' +
                ", i_id=" + i_id +
                ", videoInfoList=" + videoInfoList +
                '}';
    }
}
