package com.scut.originsystem.returnentity;

import com.scut.originsystem.entity.DetectInfo;

/**
 * @create 2018-01-12 10:05
 * @desc
 **/
public class DetectInfo_v extends DetectInfo {
    private String device_code;
    private String good_code;

    public String getGood_code() {
        return good_code;
    }

    public void setGood_code(String good_code) {
        this.good_code = good_code;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }
}
