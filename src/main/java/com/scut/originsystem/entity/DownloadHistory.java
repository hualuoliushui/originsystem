package com.scut.originsystem.entity;

public class DownloadHistory {
    private int id;
    private String create_date;
    private int qrcode_number;
    private int merchant_id;
    private String url;
    private String good_code;
    private String good_name;
    private String good_batch;
    private int good_id;

    public DownloadHistory(String create_date, int qrcode_number, int merchant_id, String url, String good_code, String good_name, String good_batch, int good_id) {
        this.create_date = create_date;
        this.qrcode_number = qrcode_number;
        this.merchant_id = merchant_id;
        this.url = url;
        this.good_code = good_code;
        this.good_name = good_name;
        this.good_batch = good_batch;
        this.good_id = good_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getGood_code() {
        return good_code;
    }

    public void setGood_code(String good_code) {
        this.good_code = good_code;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_batch() {
        return good_batch;
    }

    public void setGood_batch(String good_batch) {
        this.good_batch = good_batch;
    }




    public DownloadHistory(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getQrcode_number() {
        return qrcode_number;
    }

    public void setQrcode_number(int qrcode_number) {
        this.qrcode_number = qrcode_number;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}
