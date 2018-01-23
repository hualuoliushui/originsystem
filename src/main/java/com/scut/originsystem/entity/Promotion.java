package com.scut.originsystem.entity;

import javax.validation.constraints.Size;

public class Promotion {
    private int id;
    @Size(max = 100,message = "最大长度为100")
    private String promotion_theme;
    @Size(max = 100,message = "最大长度为100")
    private String promotion_content;
    @Size(max = 100,message = "最大长度为100")
    private String promotion_picture;
    @Size(max = 100,message = "最大长度为100")
    private String valid_start_date;
    @Size(max = 100,message = "最大长度为100")
    private String valid_end_date;
    @Size(max = 100,message = "最大长度为100")
    private String create_date;
    private int type_id;
    private int merchant_id;
    private int deleted_code;

    public String getValid_start_date() {
        return valid_start_date;
    }

    public void setValid_start_date(String valid_start_date) {
        this.valid_start_date = valid_start_date;
    }

    public String getValid_end_date() {
        return valid_end_date;
    }

    public void setValid_end_date(String valid_end_date) {
        this.valid_end_date = valid_end_date;
    }

    public int getDeleted_code() {
        return deleted_code;
    }

    public void setDeleted_code(int deleted_code) {
        this.deleted_code = deleted_code;
    }
    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
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

    public String getPromotion_theme() {
        return promotion_theme;
    }

    public void setPromotion_theme(String promotion_theme) {
        this.promotion_theme = promotion_theme;
    }

    public String getPromotion_content() {
        return promotion_content;
    }

    public void setPromotion_content(String promotion_content) {
        this.promotion_content = promotion_content;
    }

    public String getPromotion_picture() {
        return promotion_picture;
    }

    public void setPromotion_picture(String promotion_picture) {
        this.promotion_picture = promotion_picture;
    }
}
