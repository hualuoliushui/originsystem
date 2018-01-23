package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class QRCodeForCheck {
    private int id;
    private int check_state;
    private int merchant_id;
    @Size(max = 100,message = "最大长度为100")
    private String merchant_name;
    private int good_id;
    private int qrcode_buy_number;
    private String create_date;
    private String check_date;
    private String check_detail;
    private Good good;

    public String getCheck_detail() {
        return check_detail;
    }

    public void setCheck_detail(String check_detail) {
        this.check_detail = check_detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheck_state() {
        return check_state;
    }

    public void setCheck_state(int check_state) {
        this.check_state = check_state;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getQrcode_buy_number() {
        return qrcode_buy_number;
    }

    public void setQrcode_buy_number(int qrcode_buy_number) {
        this.qrcode_buy_number = qrcode_buy_number;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
