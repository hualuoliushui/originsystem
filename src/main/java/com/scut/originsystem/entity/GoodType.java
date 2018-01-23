package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class GoodType {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String good_code;
    @Size(max = 100,message = "最大长度为100")
    private String produce_place;
    @Size(max = 100,message = "最大长度为100")
    private String good_name;
    private int merchant_id;
//    @Size(max = 1,message = "最大长度为1")
    private int deleted_code;
    private String picture_1;
    private String picture_2;
    private String picture_3;
    private String picture_4;
    private String picture_5;

    public String getPicture_1() {
        return picture_1;
    }

    public void setPicture_1(String picture_1) {
        this.picture_1 = picture_1;
    }

    public String getPicture_2() {
        return picture_2;
    }

    public void setPicture_2(String picture_2) {
        this.picture_2 = picture_2;
    }

    public String getPicture_3() {
        return picture_3;
    }

    public void setPicture_3(String picture_3) {
        this.picture_3 = picture_3;
    }

    public String getPicture_4() {
        return picture_4;
    }

    public void setPicture_4(String picture_4) {
        this.picture_4 = picture_4;
    }

    public String getPicture_5() {
        return picture_5;
    }

    public void setPicture_5(String picture_5) {
        this.picture_5 = picture_5;
    }

    @Override
    public String toString() {
        return "GoodType{" +
                "id=" + id +
                ", good_code='" + good_code + '\'' +
                ", produce_place='" + produce_place + '\'' +
                ", good_name='" + good_name + '\'' +
                ", merchant_id=" + merchant_id +
                ", deleted_code=" + deleted_code +
                '}';
    }

    public GoodType(int id, String good_code, String produce_place, String good_name, int merchant_id, int deleted_code) {
        this.id = id;
        this.good_code = good_code;
        this.produce_place = produce_place;
        this.good_name = good_name;
        this.merchant_id = merchant_id;
        this.deleted_code = deleted_code;
    }

    public GoodType() {
    }

    public int getDeleted_code() {
        return deleted_code;
    }

    public void setDeleted_code(int deleted_code) {
        this.deleted_code = deleted_code;
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

    public String getProduce_place() {
        return produce_place;
    }

    public void setProduce_place(String produce_place) {
        this.produce_place = produce_place;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }
}
