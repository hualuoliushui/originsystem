package com.scut.originsystem.returnentity;

public class QRCodeCount {
    private String area_code;
    private String province;
    private String city;
    private String district;
    private int qrcode_total;
    private int qrcode_left;

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getQrcode_total() {
        return qrcode_total;
    }

    public void setQrcode_total(int qrcode_total) {
        this.qrcode_total = qrcode_total;
    }

    public int getQrcode_left() {
        return qrcode_left;
    }

    public void setQrcode_left(int qrcode_left) {
        this.qrcode_left = qrcode_left;
    }
}
