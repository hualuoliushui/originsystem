package com.scut.originsystem.entity;

public class OperationLog {
    private int id;
    private String operation_content;
    private String operation_date;
    private int merchant_id;
    private int good_id;
    private String operation_file;
    private String operation_title;
    private String operation_picture;
    private int deleted_code;

    public int getDeleted_code() {
        return deleted_code;
    }

    public void setDeleted_code(int deleted_code) {
        this.deleted_code = deleted_code;
    }
    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", operation_content='" + operation_content + '\'' +
                ", operation_date='" + operation_date + '\'' +
                ", merchant_id=" + merchant_id +
                ", good_id=" + good_id +
                ", operation_file='" + operation_file + '\'' +
                ", operation_title='" + operation_title + '\'' +
                ", operation_picture='" + operation_picture + '\'' +
                '}';
    }

    public String getOperation_title() {
        return operation_title;
    }

    public void setOperation_title(String operation_title) {
        this.operation_title = operation_title;
    }

    public String getOperation_picture() {
        return operation_picture;
    }

    public void setOperation_picture(String operation_picture) {
        this.operation_picture = operation_picture;
    }

    public String getOperation_file() {
        return operation_file;
    }

    public void setOperation_file(String operation_file) {
        this.operation_file = operation_file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation_content() {
        return operation_content;
    }

    public void setOperation_content(String operation_content) {
        this.operation_content = operation_content;
    }

    public String getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(String operation_date) {
        this.operation_date = operation_date;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }
}
