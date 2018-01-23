package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class Good {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String good_code;
    @Size(max = 100,message = "最大长度为100")
    private String good_name;
    @Size(max = 100,message = "最大长度为100")
    private String basic_info;
    @Size(max = 100,message = "最大长度为100")
    private String good_batch;
    @Size(max = 100,message = "最大长度为100")
    private String produce_date;
    @Size(max = 100,message = "最大长度为100")
    private String pack_type;
    @Size(max = 100,message = "最大长度为100")
    private String produce_place;
    private int life_time;
    @Size(max = 100,message = "最大长度为100")
    private String expiry_date;
    private int good_number;
    private int qrcode_totle;
    private int merchant_id;
    private int type_id;
    private String type_code;
    private QRCodeForCheck qrCodeForCheck;
    private GoodManager goodManager;

    public int getLife_time() {
        return life_time;
    }

    public void setLife_time(int life_time) {
        this.life_time = life_time;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Good() {
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public GoodManager getGoodManager() {
        return goodManager;
    }

    public void setGoodManager(GoodManager goodManager) {
        this.goodManager = goodManager;
    }

    public QRCodeForCheck getQrCodeForCheck() {
        return qrCodeForCheck;
    }

    public void setQrCodeForCheck(QRCodeForCheck qrCodeForCheck) {
        this.qrCodeForCheck = qrCodeForCheck;
    }

    public String getProduce_place() {
        return produce_place;
    }

    public void setProduce_place(String produce_place) {
        this.produce_place = produce_place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBasic_info() {
        return basic_info;
    }

    public void setBasic_info(String basic_info) {
        this.basic_info = basic_info;
    }

    public String getGood_batch() {
        return good_batch;
    }

    public void setGood_batch(String good_batch) {
        this.good_batch = good_batch;
    }

    public String getProduce_date() {
        return produce_date;
    }

    public void setProduce_date(String produce_date) {
        this.produce_date = produce_date;
    }

    public String getPack_type() {
        return pack_type;
    }

    public void setPack_type(String pack_type) {
        this.pack_type = pack_type;
    }

    public int getGood_number() {
        return good_number;
    }

    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

    public int getQrcode_totle() {
        return qrcode_totle;
    }

    public void setQrcode_totle(int qrcode_totle) {
        this.qrcode_totle = qrcode_totle;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}
