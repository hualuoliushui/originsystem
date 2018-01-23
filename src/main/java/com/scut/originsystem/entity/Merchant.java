package com.scut.originsystem.entity;

import javax.validation.constraints.Size;
import java.util.List;

public class Merchant {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String merchant_name;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int user_id;
    private int activation_code;
    private String username;

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", merchant_name='" + merchant_name + '\'' +
                ", create_date='" + create_date + '\'' +
                ", user_id=" + user_id +
                ", activation_code=" + activation_code +
                ", username='" + username + '\'' +
                ", company=" + company +
                ", videoDeviceList=" + videoDeviceList +
                ", detectionDeviceList=" + detectionDeviceList +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Company company;
    private List<VideoDevice> videoDeviceList;
    private List<DetectionDevice> detectionDeviceList;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<VideoDevice> getVideoDeviceList() {
        return videoDeviceList;
    }

    public void setVideoDeviceList(List<VideoDevice> videoDeviceList) {
        this.videoDeviceList = videoDeviceList;
    }

    public List<DetectionDevice> getDetectionDeviceList() {
        return detectionDeviceList;
    }

    public void setDetectionDeviceList(List<DetectionDevice> detectionDeviceList) {
        this.detectionDeviceList = detectionDeviceList;
    }

    public int getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(int activation_code) {
        this.activation_code = activation_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
