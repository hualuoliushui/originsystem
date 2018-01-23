package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class DetectInfo {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String detect_code;
    @Size(max = 100,message = "最大长度为100")
    private String detect_name;
    @Size(max = 100,message = "最大长度为100")
    private String detect_date;
    @Size(max = 100,message = "最大长度为100")
    private String detect_unit;
    @Size(max = 100,message = "最大长度为100")
    private String detect_explain;
    @Size(max = 100,message = "最大长度为100")
    private String detect_operation;
    private int device_id;
    private int good_id;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int merchant_id;
    @Size(max = 100,message = "最大长度为100")
    private String detect_result;
    private int detect_sample_id;
    private int deleted_code;

    public int getDeleted_code() {
        return deleted_code;
    }

    public void setDeleted_code(int deleted_code) {
        this.deleted_code = deleted_code;
    }

    @Override
    public String toString() {
        return "DetectInfo{" +
                "id=" + id +
                ", detect_code='" + detect_code + '\'' +
                ", detect_name='" + detect_name + '\'' +
                ", detect_date='" + detect_date + '\'' +
                ", detect_unit='" + detect_unit + '\'' +
                ", detect_explain='" + detect_explain + '\'' +
                ", detect_operation='" + detect_operation + '\'' +
                ", device_id=" + device_id +
                ", good_id=" + good_id +
                ", create_date='" + create_date + '\'' +
                ", merchant_id=" + merchant_id +
                ", detect_result='" + detect_result + '\'' +
                ", detect_sample_id=" + detect_sample_id +
                '}';
    }

    public int getDetect_sample_id() {
        return detect_sample_id;
    }

    public void setDetect_sample_id(int detect_sample_id) {
        this.detect_sample_id = detect_sample_id;
    }

    public String getDetect_result() {
        return detect_result;
    }

    public void setDetect_result(String detect_result) {
        this.detect_result = detect_result;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetect_code() {
        return detect_code;
    }

    public void setDetect_code(String detect_code) {
        this.detect_code = detect_code;
    }

    public String getDetect_name() {
        return detect_name;
    }

    public void setDetect_name(String detect_name) {
        this.detect_name = detect_name;
    }

    public String getDetect_date() {
        return detect_date;
    }

    public void setDetect_date(String detect_date) {
        this.detect_date = detect_date;
    }

    public String getDetect_unit() {
        return detect_unit;
    }

    public void setDetect_unit(String detect_unit) {
        this.detect_unit = detect_unit;
    }

    public String getDetect_explain() {
        return detect_explain;
    }

    public void setDetect_explain(String detect_explain) {
        this.detect_explain = detect_explain;
    }

    public String getDetect_operation() {
        return detect_operation;
    }

    public void setDetect_operation(String detect_operation) {
        this.detect_operation = detect_operation;
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
