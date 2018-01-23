package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class VideoInfo {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String video_code;
    @Size(max = 100,message = "最大长度为100")
    private String video_description;
    @Size(max = 100,message = "最大长度为100")
    private String video_location;
    @Size(max = 100,message = "最大长度为100")
    private String video_name;
    @Size(max = 100,message = "最大长度为100")
    private String video_url;
    private int device_id;
    private int good_id;
    private int merchant_id;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int deleted_code;

    public int getDeleted_code() {
        return deleted_code;
    }

    public void setDeleted_code(int deleted_code) {
        this.deleted_code = deleted_code;
    }
    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "id=" + id +
                ", video_code='" + video_code + '\'' +
                ", video_description='" + video_description + '\'' +
                ", video_location='" + video_location + '\'' +
                ", video_name='" + video_name + '\'' +
                ", video_url='" + video_url + '\'' +
                ", device_id=" + device_id +
                ", good_id=" + good_id +
                ", merchant_id=" + merchant_id +
                ", create_date='" + create_date + '\'' +
                '}';
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_code() {
        return video_code;
    }

    public void setVideo_code(String video_code) {
        this.video_code = video_code;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_location() {
        return video_location;
    }

    public void setVideo_location(String video_location) {
        this.video_location = video_location;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }
}
