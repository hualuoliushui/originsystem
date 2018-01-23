package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class DetectSample {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String detect_code;
    @Size(max = 100,message = "最大长度为100")
    private String detect_project;
    @Size(max = 100,message = "最大长度为100")
    private String detect_var;
    @Size(max = 100,message = "最大长度为100")
    private String detect_unit;
    private double detect_ceiling;
    private double detect_bottom;
    @Size(max = 100,message = "最大长度为100")
    private String detect_method;
    private int merchant_id;

    @Override
    public String toString() {
        return "DetectSample{" +
                "id=" + id +
                ", detect_code='" + detect_code + '\'' +
                ", detect_project='" + detect_project + '\'' +
                ", detect_var='" + detect_var + '\'' +
                ", detect_unit='" + detect_unit + '\'' +
                ", detect_ceiling=" + detect_ceiling +
                ", detect_bottom=" + detect_bottom +
                ", detect_method='" + detect_method + '\'' +
                ", merchant_id=" + merchant_id +
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

    public String getDetect_code() {
        return detect_code;
    }

    public void setDetect_code(String detect_code) {
        this.detect_code = detect_code;
    }

    public String getDetect_project() {
        return detect_project;
    }

    public void setDetect_project(String detect_project) {
        this.detect_project = detect_project;
    }

    public String getDetect_var() {
        return detect_var;
    }

    public void setDetect_var(String detect_var) {
        this.detect_var = detect_var;
    }

    public String getDetect_unit() {
        return detect_unit;
    }

    public void setDetect_unit(String detect_unit) {
        this.detect_unit = detect_unit;
    }

    public double getDetect_ceiling() {
        return detect_ceiling;
    }

    public void setDetect_ceiling(double detect_ceiling) {
        this.detect_ceiling = detect_ceiling;
    }

    public double getDetect_bottom() {
        return detect_bottom;
    }

    public void setDetect_bottom(double detect_bottom) {
        this.detect_bottom = detect_bottom;
    }

    public String getDetect_method() {
        return detect_method;
    }

    public void setDetect_method(String detect_method) {
        this.detect_method = detect_method;
    }
}
